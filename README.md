# 🛠️ Minecraft Plugin Template

![Java Version](https://img.shields.io/badge/Java-17-orange.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Build](https://img.shields.io/badge/build-Maven-red.svg)

This is a professional Minecraft plugin template integrated with a Cloudflare-backed CI/CD pipeline. It provides a robust starting point for developers who want automated builds, cross-repository dispatching, and secure secret management.

---

## 🚀 Getting Started

Follow these steps to link your repository to the pipeline:

### 1. Initialize the Pipeline
Go to the **Actions** tab in your GitHub repository and run the **Initialize Plugin Pipeline** workflow. This automated process will:
*   **Register** your repository in the Cloudflare Routing system.
*   **Configure** necessary secrets (Cloudflare, Database/Tigris).
*   **Unlock** the Maven build process by creating the `.pipeline-ready` file.

### 2. Manual Secrets Configuration
For security reasons, some secrets must be added manually. You **MUST** add the following to your GitHub Repository Secrets:
*   `HUB_PAT`: A Personal Access Token with `contents:write` permission on your Central Hub repository.

### 3. Local Development
Once the pipeline is initialized, you can build your project locally:
```bash
mvn clean package
```
> [!IMPORTANT]
> The build will fail locally if the initialization workflow hasn't been run yet. This is enforced by the `maven-enforcer-plugin`.

---

## 🏗️ Pipeline Architecture

This template is designed to work within a distributed ecosystem:

*   **Source Repo:** This repository. It runs `build-reporter.yml` on every push to track changes.
*   **Central Hub:** A central repository that receives dispatch events and routes them to specialized deployment or notification workers.
*   **Cloudflare Worker:** Acts as a secure KMS (Key Management Service) and routing registry.
*   **Tigris:** (Optional) An S3-compatible database used for storing and retrieving dashboard states.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).
