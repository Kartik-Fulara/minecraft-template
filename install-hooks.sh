#!/usr/bin/env bash
# install-hooks.sh

set -euo pipefail

git config core.hooksPath .githooks
chmod +x .githooks/* 2>/dev/null || true
echo "Git hooks installed."
