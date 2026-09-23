#!/usr/bin/env bash

# ------------------------------------------------------------------------------
# Script: imageValidation.sh
# Description: Validates Android image assets across two stages:
#              1. MIME Type & Binary Header Verification:
#                 Detects fake or renamed images (.png, .jpg, .jpeg, .webp)
#                 where the file extension does not match the actual binary format.
#              2. Android 9-Patch Validation (Optional):
#                 Compiles .9.png images with AAPT or AAPT2 to detect malformed
#                 borders, missing guide pixels, or syntax errors.
#
# Usage:
#   ./bash/imageValidation.sh <images_path> [aapt_path]
# ------------------------------------------------------------------------------

set -euo pipefail

# ------------------------------------------------------------------------------
# Usage & Help
# ------------------------------------------------------------------------------
usage() {
  cat <<EOF
Usage:
  $(basename "$0") <images_path> [aapt_path]

Arguments:
  images_path  Path to an image file or directory of images (Required)
  aapt_path    Path to aapt/aapt2 executable or build-tools directory (Optional)

Supported Image Formats:
  - PNG  (*.png)  -> Expects MIME: image/png
  - JPEG (*.jpg, *.jpeg) -> Expects MIME: image/jpeg
  - WebP (*.webp) -> Expects MIME: image/webp
  - 9-Patch (*.9.png) -> AAPT2 compile validation

Options:
  -h, --help   Show this help message and exit
EOF
  exit "${1:-0}"
}

[[ "${1:-}" =~ ^(-h|--help)$ ]] && usage 0
[[ $# -lt 1 ]] && { echo "Error: Missing required <images_path> argument." >&2; echo ""; usage 1; }

TARGET_PATH="$1"
AAPT_INPUT="${2:-}"

[[ ! -e "$TARGET_PATH" ]] && { echo "Error: Path does not exist: $TARGET_PATH" >&2; exit 1; }

ERRORS=0

# ==============================================================================
# VALIDATION STAGE 1: Image MIME Type & Binary Magic Byte Checks
# ==============================================================================
echo "==> [1/2] Validating image MIME types in: $TARGET_PATH"

# Discover image files (single file or recursive directory walk)
find_images() {
  if [[ -f "$TARGET_PATH" ]]; then
    printf '%s\0' "$TARGET_PATH"
  else
    find "$TARGET_PATH" -type f \( \
      -name "*.[pP][nN][gG]" -o \
      -name "*.[jJ][pP][gG]" -o \
      -name "*.[jJ][pP][eE][gG]" -o \
      -name "*.[wW][eE][bB][pP]" \
    \) -print0 | sort -z
  fi
}

CHECKED_COUNT=0
while IFS= read -r -d '' file; do
  mime=$(file -b -I "$file" 2>/dev/null || echo "unknown")
  case "$file" in
    # --------------------------------------------------------------------------
    # Block 1.1: PNG Image Validation (*.png)
    # Verifies file begins with standard PNG magic header (89 50 4E 47 0D 0A 1A 0A)
    # --------------------------------------------------------------------------
    *.[pP][nN][gG])
      ((CHECKED_COUNT++))
      if [[ "$mime" != image/png* ]]; then
        echo "  [FAKE PNG] $file (actual: $mime)"
        ((ERRORS++))
      fi
      ;;

    # --------------------------------------------------------------------------
    # Block 1.2: JPEG / JPG Image Validation (*.jpg, *.jpeg)
    # Verifies file begins with standard JPEG SOI marker (FF D8 FF)
    # --------------------------------------------------------------------------
    *.[jJ][pP][gG]|*.[jJ][pP][eE][gG])
      ((CHECKED_COUNT++))
      if [[ "$mime" != image/jpeg* && "$mime" != image/jpg* ]]; then
        echo "  [FAKE JPEG] $file (actual: $mime)"
        ((ERRORS++))
      fi
      ;;

    # --------------------------------------------------------------------------
    # Block 1.3: WebP Image Validation (*.webp)
    # Verifies file begins with RIFF container and WEBP chunk header
    # --------------------------------------------------------------------------
    *.[wW][eE][bB][pP])
      ((CHECKED_COUNT++))
      if [[ "$mime" != image/webp* ]]; then
        echo "  [FAKE WEBP] $file (actual: $mime)"
        ((ERRORS++))
      fi
      ;;
  esac
done < <(find_images)

echo "    Checked $CHECKED_COUNT image(s)."

# ==============================================================================
# VALIDATION STAGE 2: Android 9-Patch (.9.png) Compilation Check (AAPT / AAPT2)
# ==============================================================================
if [[ -n "$AAPT_INPUT" ]]; then
  # Resolve AAPT / AAPT2 binary location
  AAPT_BIN=""
  if [[ -f "$AAPT_INPUT" && -x "$AAPT_INPUT" ]]; then
    AAPT_BIN="$AAPT_INPUT"
  elif [[ -d "$AAPT_INPUT" ]]; then
    for candidate in "$AAPT_INPUT/aapt2" "$AAPT_INPUT/aapt"; do
      [[ -x "$candidate" ]] && { AAPT_BIN="$candidate"; break; }
    done
  fi

  [[ -z "$AAPT_BIN" ]] && { echo "Error: AAPT binary not found at '$AAPT_INPUT'" >&2; exit 1; }

  # Temporary working directory for AAPT compilation output
  TEMP_DIR=$(mktemp -d 2>/dev/null || mktemp -d -t 'aapt_val')
  trap 'rm -rf "$TEMP_DIR"' EXIT INT TERM

  echo "==> [2/2] Validating 9-Patch (.9.png) images with: $AAPT_BIN"

  # Find .9.png files specifically
  find_nine_patches() {
    if [[ -f "$TARGET_PATH" ]]; then
      [[ "$TARGET_PATH" == *.9.png ]] && printf '%s\0' "$TARGET_PATH"
    else
      find "$TARGET_PATH" -type f -name "*.9.png" -print0 | sort -z
    fi
  }

  # --------------------------------------------------------------------------
  # Block 2.1: 9-Patch Border & Format Compilation (AAPT/AAPT2)
  # Compiles each 9-patch to verify 1-pixel guide lines, content padding,
  # and non-transparent black line constraints.
  # --------------------------------------------------------------------------
  NP_COUNT=0
  while IFS= read -r -d '' np; do
    ((NP_COUNT++))
    if [[ "$AAPT_BIN" == *aapt2* ]]; then
      set +e
      out=$("$AAPT_BIN" compile "$np" -o "$TEMP_DIR" 2>&1)
      code=$?
      set -e
    else
      set +e
      out=$("$AAPT_BIN" singleCrunch -i "$np" -o "$TEMP_DIR/$(basename "$np")" 2>&1)
      code=$?
      set -e
    fi

    if [[ $code -ne 0 ]]; then
      echo "  [INVALID 9-PATCH] $(basename "$np")"
      [[ -n "$out" ]] && echo "$out" | sed 's/^/    /'
      ((ERRORS++))
    else
      echo "  [PASS] $(basename "$np")"
    fi
  done < <(find_nine_patches)

  echo "    Validated $NP_COUNT 9-patch file(s)."
else
  echo "==> [2/2] 9-Patch AAPT validation skipped (no AAPT path provided)."
fi

# ==============================================================================
# Summary & Status
# ==============================================================================
echo "--------------------------------------------------"
if [[ $ERRORS -gt 0 ]]; then
  echo "Validation completed with $ERRORS error(s)."
  exit 1
else
  echo "All validations passed successfully!"
  exit 0
fi
