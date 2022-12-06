#!/usr/bin/env bash

set -Eeuo pipefail

readarray -t REPOSITORIES <repositories
JSON='repositories-and-lessons={"repository":['
for REPOSITORY in "${REPOSITORIES[@]}"; do
  JSON="$JSON\"$REPOSITORY\","
done
JSON="${JSON%?}],\"lesson\":["
CURRENT_DATE=$(date +"%Y%m%d")
NO_LESSONS=true
while IFS="=" read -r LESSON LESSON_DATE_PLAIN; do
  LESSON_DATE=$(date -d "$LESSON_DATE_PLAIN" +"%Y%m%d")
  if [[ "$CURRENT_DATE" -gt "$LESSON_DATE" ]]; then
    NO_LESSONS=false
    JSON="$JSON$LESSON,"
  fi
done <"./homework-schedule"
if $NO_LESSONS; then
  echo "No homework to post"
  exit 0
fi
JSON="${JSON%?}]}"
echo "$JSON" >>"$GITHUB_OUTPUT"
echo "has-homework=true" >>"$GITHUB_OUTPUT"
