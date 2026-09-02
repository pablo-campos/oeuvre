# Antigravity AI Rules & Project Instructions

This repository defines guidelines and contextual information for Antigravity AI tooling operating within the `oeuvre` workspace.

---

## 1. Project Overview & Architecture

`oeuvre` (`/ˈœv.ɹə/`) is a polyglot playground and experimental sandbox designed for quick prototypes, standalone scripts, and scratchpad explorations across multiple programming languages.

### Core Architecture Principles:
- **Sandbox Isolation**: Each language environment resides in its own isolated subfolder with its native configuration, manifest, and dependencies.
- **Unified Root Orchestration**: A root-level [Makefile](file:///Users/pablocampos/Development/Repositories/oeuvre/Makefile) provides standardized commands to run, install dependencies, and clean across all environments.
- **Version Pinning**: Runtime versions are standardized via [.tool-versions](file:///Users/pablocampos/Development/Repositories/oeuvre/.tool-versions) (using `asdf`).
- **Clean Workspace**: Build artifacts, virtual environments, and caches are kept out of version control via [.gitignore](file:///Users/pablocampos/Development/Repositories/oeuvre/.gitignore).

---

## 2. Directory Structure & Environments

```text
oeuvre/
├── .tool-versions          # Pinned runtime versions (asdf)
├── .gitignore              # Multi-ecosystem ignore rules
├── Makefile                # Root-level orchestrator & task runner
├── README.md               # User documentation & setup guide
├── GEMINI.md               # Antigravity AI instructions & rules
│
├── bash/                   # Shell / Bash scripts (executable scripts)
│   └── hello.sh            # Bash entrypoint
│
├── javascript/             # Node.js (v20+) sandbox with ES Modules
│   ├── package.json        # npm package manifest ("type": "module")
│   └── src/index.js        # JavaScript entrypoint
│
├── python/                 # Python 3.10 sandbox
│   ├── pyproject.toml      # Project configuration
│   ├── requirements.txt    # Pip dependencies list
│   └── main.py             # Python entrypoint
│
├── ruby/                   # Ruby 3.3 sandbox
│   ├── Gemfile             # Bundler gem list
│   └── main.rb             # Ruby entrypoint
│
├── java/                   # Java 21 sandbox (Gradle multi-project)
│   ├── build.gradle.kts    # Java Gradle build script
│   └── src/main/java/oeuvre/Main.java
│
├── kotlin/                 # Kotlin 2.1 sandbox (Gradle multi-project)
│   ├── build.gradle.kts    # Kotlin Gradle build script
│   └── src/main/kotlin/oeuvre/Main.kt
│
├── settings.gradle.kts     # Root Gradle multi-project definition (:java, :kotlin)
└── gradlew / gradlew.bat   # Gradle 8.12 wrapper
```

---

## 3. Build & Execution Guidelines

AI tooling should leverage the root [Makefile](file:///Users/pablocampos/Development/Repositories/oeuvre/Makefile) whenever possible:

| Command | Action |
| :--- | :--- |
| `make run-all` | Runs entrypoints sequentially across all 6 environments. |
| `make js` | Runs JavaScript sandbox (`node javascript/src/index.js`). |
| `make python` | Runs Python sandbox (`python3 python/main.py`). |
| `make ruby` | Runs Ruby sandbox (`ruby ruby/main.rb`). |
| `make bash` | Runs Bash sandbox (`./bash/hello.sh`). |
| `make java` | Runs Java application via Gradle (`./gradlew :java:run --quiet`). |
| `make kotlin` | Runs Kotlin application via Gradle (`./gradlew :kotlin:run --quiet`). |
| `make install` | Resolves and installs dependencies for all subprojects. |
| `make clean` | Cleans JVM build artifacts and Python cache directories. |

### Dependency Management Rules:
- Keep dependencies scoped strictly to the respective language folder:
  - JavaScript: `javascript/package.json`
  - Python: `python/requirements.txt` and `python/pyproject.toml`
  - Ruby: `ruby/Gemfile`
  - Java: `java/build.gradle.kts`
  - Kotlin: `kotlin/build.gradle.kts`
- Never install dependencies or generate caches in the root repository folder.

---

## 4. Git Commit Message Generation Rules

When generating commit messages (such as when tapping the **"Generate"** button in the Source Control panel or when asked to produce a commit message), strictly follow this template and rules:

### Template
```text
Type: description in 1-2 sentences.
```

### Allowed Types
The commit `Type` must be exactly one of the following:

- **`Global`**: Various changes across multiple languages or project-wide infrastructure (e.g., changes touching multiple sandboxes, root `Makefile`, multi-environment orchestration).
- **`Documentation`**: Documentation updates (e.g., `README.md`, guide files, comments, docstrings).
- **`Configuration`**: Language version updates, build tool changes, and environment setup (e.g., `.tool-versions`, Gradle build files, `package.json` scripts/metadata, Gemfile updates).
- **`Feature`**: Specific to adding new functionality or new scripts (e.g., adding a new script, new algorithm, or new capability within a language sandbox).
- **`Fix`**: Bug fixes, runtime error corrections, and resolving broken scripts or unexpected behavior.
- **`Refactor`**: Code restructuring, modularization, or cleanup without adding features or fixing bugs.
- **`Style`**: Formatting, whitespace adjustments, indentation, or console output styling without affecting logic.

### Formatting Constraints
1. **Prefix**: Must start with `Type: ` (e.g., `Feature: `, `Fix: `, `Global: `, `Configuration: `, `Documentation: `, `Style: `, `Refactor: `). Do NOT format the prefix with backticks, markdown, or brackets.
2. **Length**: Exactly 1 to 2 sentences describing the change and its intent.
3. **Clarity**: Concisely summarize what changed and why.
4. **No Extra Bloat**: Do NOT add markdown headers, bullet point lists, or footer metadata unless explicitly requested.

### Examples

- **Global**:
  `Global: Standardized error logging across the Python, JavaScript, and Ruby sandboxes. Updated the root Makefile clean target to handle new temporary log files.`

- **Documentation**:
  `Documentation: Updated the README with instructions for configuring custom Gradle toolchains and running Java single-file entrypoints.`

- **Configuration**:
  `Configuration: Updated Node.js and Ruby versions in .tool-versions and refreshed package manifests.`

- **Feature**:
  `Feature: Added a prime number generation script in the Kotlin sandbox with interactive CLI arguments.`

- **Fix**:
  `Fix: Resolved a nil pointer exception in the Ruby script when parsing optional command-line arguments.`

- **Refactor**:
  `Refactor: Modularized the Python sandbox entrypoint by extracting helper functions into separate utility modules.`

- **Style**:
  `Style: Adjusted trailing newline in the Ruby script output to align with the formatting of other language runners.`
