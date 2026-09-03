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

## 4. Git Commit Message Conventions

When generating commit messages (including via the Source Control panel or chat requests):
- **Template**: `Type: description in 1-2 sentences.`
- **Allowed Types**: `Global`, `Documentation`, `Configuration`, `Feature`, `Fix`, `Refactor`, `Style`.
- **Reference**: For detailed rules, constraints, and examples, refer to the [commit-message](file:///Users/pablocampos/Development/Repositories/oeuvre/.agents/skills/commit-message/SKILL.md) skill (`.agents/skills/commit-message/SKILL.md`).
