#!/usr/bin/env bash
# bump-version.sh

set -euo pipefail

NEW_VERSION="${1-}"
[[ -z "$NEW_VERSION" ]] && { echo "Usage: ./bump-version.sh <version>"; exit 1; }

echo "$NEW_VERSION" > VERSION
sed -i "s/<version>.*<\/version>/<version>$NEW_VERSION<\/version>/" pom.xml
sed -i "s/^version:.*/version: '$NEW_VERSION'/" src/main/resources/plugin.yml

echo "Version bumped to $NEW_VERSION"
