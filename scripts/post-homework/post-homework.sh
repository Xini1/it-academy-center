#!/usr/bin/env bash

set -Eeuo pipefail

REPOSITORY_PATH="$PWD/repository"
SOURCES_PATH="$PWD/src/main/java/by/itacademy/lesson$2"
TARGET_SOURCES_PATH="$REPOSITORY_PATH/src/main/java/by/itacademy/lesson$2"
git clone "$1" "$REPOSITORY_PATH"
mkdir -p "$TARGET_SOURCES_PATH"
{
  cd "$REPOSITORY_PATH"
  TARGET_BRANCH="homework/lesson$2"
  for BRANCH in $(git for-each-ref --format="%(refname:short)" refs/heads); do
    if [[ "$BRANCH" == "$TARGET_BRANCH" ]]; then
      echo "Homework has been already posted"
      exit 0
    fi
  done
  cp -r "$SOURCES_PATH/." "$TARGET_SOURCES_PATH"
  git checkout -b "$TARGET_BRANCH"
  git config user.name "Xini1"
  git config user.email "tereshchenko.xini@gmail.com"
  git add "$TARGET_SOURCES_PATH"
  git commit -m "$TARGET_BRANCH"
  git push -u origin "$TARGET_BRANCH"
}
