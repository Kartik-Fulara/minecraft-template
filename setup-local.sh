#!/usr/bin/env bash
# setup-local.sh
# Automates local environment setup and links the pipeline.

set -euo pipefail

ENV_FILE="workflow_setup.env"
TEMPLATE_ENV="workflow_setup.env.template"

echo "Setting up local environment..."

# 1. Try to find existing env in parent or sibling directories to auto-fill
PARENT_ENV="../../workflow_setup.env"
SISTER_ENV="../WorldResetPlugin/workflow_setup.env"

# 2. Create actual env file
if [[ ! -f "$ENV_FILE" ]]; then
  if [[ -f "$PARENT_ENV" ]]; then
    cp "$PARENT_ENV" "$ENV_FILE"
    echo "Found existing environment in parent directory. Copied to $ENV_FILE."
  elif [[ -f "$SISTER_ENV" ]]; then
    cp "$SISTER_ENV" "$ENV_FILE"
    echo "Found existing environment in WorldResetPlugin. Copied to $ENV_FILE."
  else
    # Create from scratch
    cat > "$ENV_FILE" <<EOF
# --- REPOSITORY NAMES ---
SOURCE_REPO="\$(git remote get-url origin 2>/dev/null | sed -E 's|https://github.com/||;s|git@github.com:||;s|.git$||')"
HUB_REPO="KartikFulara/worker-hub"
DEPLOY_REPO="Kartik-Fulara-2003/deploy-worker"
NOTIFY_REPO="kf-dev-id/notify-worker"

# --- SECRETS ---
HUB_PAT=""
MODRINTH_TOKEN=""
DISCORD_WEBHOOK=""
CF_WORKER_URL=""
CF_AUTH_TOKEN=""
TIGRIS_STORAGE_BUCKET=""
EOF
    echo "Created $ENV_FILE from template. Please fill in your secrets if not already set."
  fi
else
  echo "$ENV_FILE already exists."
fi

# 3. Update SOURCE_REPO in the env file to match the current git repo
CURRENT_REPO="$(git remote get-url origin 2>/dev/null | sed -E 's|https://github.com/||;s|git@github.com:||;s|.git$||')"
if [[ -n "$CURRENT_REPO" ]]; then
  sed -i "s|^SOURCE_REPO=.*|SOURCE_REPO=\"$CURRENT_REPO\"|" "$ENV_FILE"
fi

# 4. Install hooks
bash install-hooks.sh

echo "Local setup complete!"
