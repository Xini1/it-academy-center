#!/usr/bin/env bash

set -Eeuo pipefail

source "$PWD/scripts/common.sh"

{
  cd "$REPOSITORY_PATH"
  git config user.name "Xini1"
  git config user.email "tereshchenko.xini@gmail.com"
  git commit -m "GitHub workflows"
  git push
}
