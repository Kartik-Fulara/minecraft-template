# Minecraft Plugin Template Mandates

## Development Workflow
- **Local Setup:** Run `bash setup-local.sh` immediately after cloning. This installs git hooks and prepares your environment variables.
- **Versioning:** Always use `./bump-version.sh <version>` to update the project version. Never edit version strings manually in `pom.xml` or `plugin.yml`.
- **Commits:** Follow Conventional Commits. Commits that don't match the `feat|fix|docs|...` pattern will be rejected.
- **Main Branch:** You cannot commit to `main` without bumping the version first (enforced by `pre-commit`).

## Pipeline Integration
- **Initialization:** You MUST run the "Initialize Plugin Pipeline" GitHub Action before your first build. This creates the `.pipeline-ready` lock file.
- **Secrets:** Ensure `HUB_PAT` is set in GitHub Secrets. Other secrets are handled by the initialization workflow.

## Technical Standards
- **File Management:** Use `FileUtil` for all ZIP and recursive file operations to ensure Zip Slip protection.
- **Configuration:** All settings should be managed via `ConfigManager`.
