#!/usr/bin/env bash
set -euo pipefail

echo "========================================"
echo " Hello, World from Bash!"
echo "========================================"
echo "Bash Version: ${BASH_VERSION}"
echo "Running User: $(whoami)"
echo "Current Shell: ${SHELL:-bash}"
echo "Operating System: $(uname -s) $(uname -m)"
echo "Timestamp: $(date -u +"%Y-%m-%dT%H:%M:%SZ")"
echo "----------------------------------------"
echo ""
