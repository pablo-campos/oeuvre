#!/usr/bin/env bash

# ------------------------------------------------------------------------------
# Script: generateJiraReport.sh
# Description: Generates a release report of Git commits and Jira ticket numbers
#              between two Git tags for a single repository. Commits and tickets
#              are filtered, organized, and categorized based on the Jira
#              configuration defined below.
#
# Usage:
#   ./bash/generateJiraReport.sh <older_tag> <newer_tag_or_ref>
#
# Examples:
#   ./bash/generateJiraReport.sh v1.0.0 v1.1.0
#   ./bash/generateJiraReport.sh v1.0.0 HEAD
#
# Note:
#   If you want to check all tickets included since a specific tag up to the
#   tip of the current branch, you can pass 'HEAD' as the second parameter.
#
# If you encounter permission issues running this script, run:
#   chmod +x ./bash/generateJiraReport.sh
# ------------------------------------------------------------------------------

# ------------------------------------------------------------------------------
# CONFIGURATION & CONSTANTS
# The report filters, extracts, and organizes commits strictly based on the Jira
# configuration provided below. Customize these settings (base URLs, tracked
# project keys, and metric buckets) to match your team's Jira instance.
# ------------------------------------------------------------------------------

# Base URL for your Jira instance (do not include a trailing slash).
# Examples:
#   - Jira Cloud:       "https://your-domain.atlassian.net"
#   - Jira Server / DC: "https://jira.yourcompany.com"
readonly JIRA_BASE_URL="https://your-domain.atlassian.net"

# Git repository web comparison URL template.
# Variables ${BASE_TAG} (older) and ${TARGET_TAG} (newer) are substituted at runtime.
# Standard templates for common providers:
#   - GitHub:       "https://github.com/{owner}/{repo}/compare/${BASE_TAG}...${TARGET_TAG}"
#   - GitLab:       "https://gitlab.com/{owner}/{repo}/-/compare/${BASE_TAG}...${TARGET_TAG}"
#   - Bitbucket:    "https://bitbucket.org/{owner}/{repo}/branches/compare/${TARGET_TAG}%0D${BASE_TAG}"
#   - Azure DevOps: "https://dev.azure.com/{org}/{project}/_git/{repo}/branchCompare?baseVersion=GT${BASE_TAG}&targetVersion=GT${TARGET_TAG}&_a=commits"
readonly REPO_COMPARE_URL_TEMPLATE="https://github.com/organization/repository/compare/\${BASE_TAG}...\${TARGET_TAG}"

# List of Jira project keys to scan for in commit messages.
# Any commit referencing <PROJECT_KEY>-<NUMBER> (e.g. PROJ-123) will be captured.
# Add or remove keys to match the active projects in your Jira instance.
# If left empty, all standard uppercase Jira ticket formats ([A-Z][A-Z0-9]+-[0-9]+) are matched.
readonly JIRA_PROJECT_KEYS=(
    "PROJ"
    "MAINT"
    "PM"
    "FEAT"
)

# Optional project key categorization for report metrics.
# Configure which project keys correspond to each category for bug & feature breakdowns.
readonly PROD_BUG_PROJECTS=("MAINT")
readonly POST_IMPL_BUG_PROJECTS=("PM")
readonly FEATURE_PROJECTS=("FEAT" "PROJ")

# ------------------------------------------------------------------------------
# INPUT VALIDATION
# ------------------------------------------------------------------------------

if [ $# -lt 2 ]; then
    echo "Error: Missing required tag arguments."
    echo "Usage: $0 <older_tag> <newer_tag_or_ref>"
    echo "Examples:"
    echo "  $0 v1.0.0 v1.1.0"
    echo "  $0 v1.0.0 HEAD    # Check tickets since tag to current branch tip"
    exit 1
fi

OLDER_TAG="$1"
NEWER_TAG="$2"

# Ensure script is executed inside a Git repository
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "Error: Must be run inside a Git repository." >&2
    exit 1
fi

echo
echo ">>> Fetching latest repository tags..."
if ! git fetch --tags --quiet 2>/dev/null; then
    echo ">>> Notice: Unable to fetch remote tags (offline or remote unreachable). Continuing with local tags."
fi

# Verify tags exist in Git history
if ! git rev-parse --verify --quiet "$OLDER_TAG" >/dev/null; then
    echo "Error: Tag '$OLDER_TAG' not found in Git repository." >&2
    exit 1
fi

if ! git rev-parse --verify --quiet "$NEWER_TAG" >/dev/null; then
    echo "Error: Tag '$NEWER_TAG' not found in Git repository." >&2
    exit 1
fi

# Determine repository name for display
REPO_NAME="$(basename -s .git "$(git config --get remote.origin.url 2>/dev/null || pwd)")"

# ------------------------------------------------------------------------------
# DATA EXTRACTION & PROCESSING
# Filter commits and extract ticket numbers matching the Jira configuration.
# ------------------------------------------------------------------------------

# Build regex pattern for ticket extraction based on configured JIRA_PROJECT_KEYS
if [ ${#JIRA_PROJECT_KEYS[@]} -gt 0 ]; then
    project_keys_regex=$(IFS='|'; echo "${JIRA_PROJECT_KEYS[*]}")
    ticket_regex="(${project_keys_regex})-[0-9]+"
else
    # Fallback to standard Jira issue pattern
    ticket_regex="[A-Z][A-Z0-9]+-[0-9]+"
fi

# Extract commits containing Jira tickets
jira_commit_messages=$(git log --pretty=format:"%s" "${OLDER_TAG}..${NEWER_TAG}" | grep -E -i "(${ticket_regex})" | sort -u || true)

# Extract unique ticket IDs in uppercase
if [ -n "$jira_commit_messages" ]; then
    jira_tickets=$(echo "$jira_commit_messages" | grep -E -i -o "(${ticket_regex})" | tr '[:lower:]' '[:upper:]' | sort -u || true)
else
    jira_tickets=""
fi

# Count total unique tickets
if [ -n "$jira_tickets" ]; then
    total_count=$(echo "$jira_tickets" | grep -c . || echo 0)
else
    total_count=0
fi

# Helper function to count tickets matching a list of project keys
count_matching_tickets() {
    local target_keys=("$@")
    if [ ${#target_keys[@]} -eq 0 ] || [ -z "$jira_tickets" ]; then
        echo 0
        return
    fi
    local pattern
    pattern=$(IFS='|'; echo "${target_keys[*]}")
    echo "$jira_tickets" | grep -E "^(${pattern})-[0-9]+" | wc -l | tr -d ' '
}

prod_bugs_count=$(count_matching_tickets "${PROD_BUG_PROJECTS[@]}")
post_impl_bugs_count=$(count_matching_tickets "${POST_IMPL_BUG_PROJECTS[@]}")
features_count=$(count_matching_tickets "${FEATURE_PROJECTS[@]}")
misc_count=$(( total_count - prod_bugs_count - post_impl_bugs_count - features_count ))

# Format comparison URL
compare_url=$(echo "$REPO_COMPARE_URL_TEMPLATE" | sed -e "s/\${BASE_TAG}/$OLDER_TAG/g" -e "s/\${TARGET_TAG}/$NEWER_TAG/g")

# ------------------------------------------------------------------------------
# REPORT GENERATION
# ------------------------------------------------------------------------------

echo
echo "**************************************************"
echo "                  STARTING REPORT                 "
echo "**************************************************"
echo
echo "Repository: ${REPO_NAME}"
echo "Comparison: ${NEWER_TAG} (changes since ${OLDER_TAG})"
echo
echo "For commit comparison, see:"
echo "${compare_url}"
echo
echo "****************** # OF ISSUES *******************"
echo
if [ ${#PROD_BUG_PROJECTS[@]} -gt 0 ]; then
    echo "${prod_bugs_count} Production Bugs (e.g., $(echo "${PROD_BUG_PROJECTS[*]}" | sed 's/ /, /g'))"
fi
if [ ${#POST_IMPL_BUG_PROJECTS[@]} -gt 0 ]; then
    echo "${post_impl_bugs_count} Post Implementation Bugs (e.g., $(echo "${POST_IMPL_BUG_PROJECTS[*]}" | sed 's/ /, /g'))"
fi
if [ ${#FEATURE_PROJECTS[@]} -gt 0 ]; then
    echo "${features_count} Features / Enhancements (e.g., $(echo "${FEATURE_PROJECTS[*]}" | sed 's/ /, /g'))"
fi
if [ "$misc_count" -gt 0 ]; then
    echo "${misc_count} Other / Uncategorized Issues"
fi
if [ ${#PROD_BUG_PROJECTS[@]} -gt 0 ] || [ ${#POST_IMPL_BUG_PROJECTS[@]} -gt 0 ] || [ ${#FEATURE_PROJECTS[@]} -gt 0 ]; then
    echo "---------------------"
fi
echo "${total_count} Total Tickets"

if [ -n "$jira_tickets" ]; then
    echo
    echo "***************** JIRA DETAILS *******************"
    echo
    echo "JIRA QUERY (Bulk View):"
    # Format comma-separated tickets for JQL URL: issue in (KEY-1,%20KEY-2)
    jira_tickets_csv=$(echo "$jira_tickets" | paste -sd, - | sed 's/,/,%20/g')
    echo "${JIRA_BASE_URL}/issues/?jql=issue%20in%20(${jira_tickets_csv})"
    echo
    echo "JIRA TICKET NUMBERS:"
    echo "$jira_tickets"
    echo
    echo "JIRA URLS:"
    echo "$jira_tickets" | sed "s|^|${JIRA_BASE_URL}/browse/|"
    echo
    echo "COMMITS:"
    echo "$jira_commit_messages"
else
    echo
    echo "***************** JIRA DETAILS *******************"
    echo
    echo "No Jira tickets found in commits between ${OLDER_TAG} and ${NEWER_TAG}."
fi

echo
echo "**************************************************"
echo "                        END                       "
echo "**************************************************"
echo