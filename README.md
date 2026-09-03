<div align="center">
  <img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgtd35hlaQS94eu8MfZaOh2BpnmPPVQ4H7kzfz1kNoNW3ZfK3Eua0d-Yhbt_CfOfRAppyoD2mIQPSEr-FFVvCSya51wZPsmZRK-duXgbumZVsLeXSoV0-76Lti8oHtjkZfy17GZRJ_wTdLm/s1600/guernica.jpg" alt="Guernica" width="800" />

  # oeuvre (`/ˈœv.ɹə/`)

  <br />
  <p>A collection of standalone scripts, quick prototypes, experimental testbeds, and scratchpad code.</p>
  <br />
</div>

---

## Quick Start (Unified Runner)

You can run any or all environments from the project root using the provided `Makefile`:

```bash
# Run all 6 languages sequentially
make run-all

# Run individual languages
make js       # JavaScript (Node.js)
make python   # Python
make ruby     # Ruby
make bash     # Shell / Bash
make java     # Java (via Gradle)
make kotlin   # Kotlin (via Gradle)

# Install dependencies across all modules
make install
```

---

## Project Structure

```text
oeuvre/
├── .agents/skills/
│   └── commit-message/
│       └── SKILL.md        # Commit message generator skill
├── .tool-versions          # asdf versions (Node, Python, Ruby, Kotlin, Gradle)
├── .gitignore              # Multi-ecosystem ignore rules
├── Makefile                # Root-level orchestrator & task runner
├── README.md               # Documentation and usage guide
├── GEMINI.md               # Antigravity AI instructions & rules
├── AGENTS.md               # Symlink to GEMINI.md (agent instructions)
│
├── bash/                   # Shell / Bash scripts
│   ├── generateJiraReport.sh # Jira release report generator from Git tags
│   └── hello.sh            # Executable bash script
│
├── javascript/             # JavaScript (Node.js) sandbox
│   ├── package.json        # npm package manifest (ES Modules)
│   └── src/
│       └── index.js        # JavaScript entrypoint
│
├── python/                 # Python sandbox
│   ├── pyproject.toml      # Project configuration
│   ├── requirements.txt    # Pip package list
│   └── main.py             # Python entrypoint
│
├── ruby/                   # Ruby sandbox
│   ├── Gemfile             # Bundler gem list
│   └── main.rb             # Ruby entrypoint
│
├── java/                   # Java sandbox
│   ├── build.gradle.kts    # Java Gradle build file
│   └── src/main/java/oeuvre/
│       └── Main.java       # Java Main class
│
├── kotlin/                 # Kotlin sandbox
│   ├── build.gradle.kts    # Kotlin Gradle build file
│   └── src/main/kotlin/oeuvre/
│       └── Main.kt         # Kotlin Main program
│
├── settings.gradle.kts     # Root Gradle multi-project configuration
└── gradlew                 # Gradle wrapper executable
```

---

## Languages & Stack

### 1. JavaScript (Node.js)
* **Directory**: `javascript/`
* **Entrypoint**: `javascript/src/index.js`
* **How to Run**:
  ```bash
  # From root:
  make js
  # Or from directory:
  cd javascript
  npm start
  # Or live watch mode:
  npm run dev
  ```
* **Adding Packages**:
  ```bash
  cd javascript
  npm install <package-name>
  ```

---

### 2. Python
* **Directory**: `python/`
* **Entrypoint**: `python/main.py`
* **How to Run**:
  ```bash
  # From root:
  make python
  # Or from directory:
  cd python
  python3 main.py
  ```
* **Adding Packages**:
  ```bash
  cd python
  pip install <package-name>
  pip freeze > requirements.txt
  ```
  *(Optional: create and use a virtual environment with `python3 -m venv .venv && source .venv/bin/activate`)*

---

### 3. Ruby
* **Directory**: `ruby/`
* **Entrypoint**: `ruby/main.rb`
* **How to Run**:
  ```bash
  # From root:
  make ruby
  # Or from directory:
  cd ruby
  bundle exec ruby main.rb
  # Or directly:
  ruby main.rb
  ```
* **Adding Packages**:
  Add the desired gem to `ruby/Gemfile`:
  ```ruby
  gem "colorize"
  ```
  Then run:
  ```bash
  cd ruby
  bundle install
  ```

---

### 4. Shell / Bash
* **Directory**: `bash/`
* **Entrypoint**: `bash/hello.sh`
* **How to Run**:
  ```bash
  # From root:
  make bash
  # Or directly:
  ./bash/hello.sh
  ```
* **Adding Scripts**:
  Add any new `.sh` file inside `bash/` and mark it executable (`chmod +x bash/<script>.sh`).

---

### 5. Java
* **Directory**: `java/`
* **Entrypoint**: `java/src/main/java/oeuvre/Main.java`
* **How to Run**:
  ```bash
  # From root via Gradle:
  make java
  # Or via Gradle Wrapper:
  ./gradlew :java:run
  # Or directly with single-file source runner (Java 21):
  java java/src/main/java/oeuvre/Main.java
  ```
* **Adding Packages**:
  Add dependencies in `java/build.gradle.kts`:
  ```kotlin
  dependencies {
      implementation("com.google.guava:guava:33.4.0-jre")
  }
  ```

---

### 6. Kotlin
* **Directory**: `kotlin/`
* **Entrypoint**: `kotlin/src/main/kotlin/oeuvre/Main.kt`
* **How to Run**:
  ```bash
  # From root via Gradle:
  make kotlin
  # Or via Gradle Wrapper:
  ./gradlew :kotlin:run
  ```
* **Adding Packages**:
  Add dependencies in `kotlin/build.gradle.kts`:
  ```kotlin
  dependencies {
      implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
  }
  ```

---

## Tooling & Version Management

This repository uses [`asdf`](https://asdf-vm.com/) via [.tool-versions](file:///Users/pablocampos/Development/Repositories/oeuvre/.tool-versions):
- **Node.js**: `20.13.1` (or local `v22`)
- **Python**: `3.10.4`
- **Ruby**: `3.3.0`
- **Kotlin**: `2.1.20`
- **Gradle**: `8.12`
- **Java**: OpenJDK 21 (Homebrew)
