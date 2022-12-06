#!/usr/bin/env bash

set -Eeuo pipefail

readarray -t REPOSITORIES <"./repositories"
JSON='repositories={"repository":['
for REPOSITORY in "${REPOSITORIES[@]}"; do
  JSON="$JSON\"$REPOSITORY\","
done
JSON="${JSON%?}]}"
echo "$JSON" >>"$GITHUB_OUTPUT"
