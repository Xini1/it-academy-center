#!/usr/bin/env bash

set -Eeuo pipefail

REPOSITORY_PATH="$PWD/repository"
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
    git config user.name "Xini1"
    git config user.email "tereshchenko.xini@gmail.com"
    git commit -m "GitHub workflows"
    git push
  else
    echo "Repository is up to date"
  fi
}
