#!/usr/bin/env bash

set -Eeuo pipefail

source "$PWD/scripts/common.sh"

REPOSITORY_GITHUB_WORKFLOWS_PATH="$REPOSITORY_PATH/.github/workflows"
GITHUB_WORKFLOWS_PATH="$PWD/workflows"

git clone "$1" "$REPOSITORY_PATH"
rm -rf "$REPOSITORY_GITHUB_WORKFLOWS_PATH"
mkdir -p "$REPOSITORY_GITHUB_WORKFLOWS_PATH"
cp -r "$GITHUB_WORKFLOWS_PATH/." "$REPOSITORY_GITHUB_WORKFLOWS_PATH"
{
  cd "$REPOSITORY_PATH"
  git add "$REPOSITORY_GITHUB_WORKFLOWS_PATH"
  if [[ $(git diff --name-only --cached) ]]; then
    echo "has-files-to-commit=true" >>"$GITHUB_OUTPUT"
  fi
}
