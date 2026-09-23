.PHONY: help run-all js python ruby bash java kotlin chat jira-report install clean

# Default variables for configurable targets
FROM ?= v0.0.1
TO ?= v0.0.2

# Default target: show help
help:
	@echo "================================================================="
	@echo "  oeuvre playground - Available Commands"
	@echo "================================================================="
	@echo "  make run-all      Run Hello World across all 6 languages"
	@echo "  make js           Run JavaScript (Node.js)"
	@echo "  make python       Run Python"
	@echo "  make ruby         Run Ruby"
	@echo "  make bash         Run Shell / Bash script"
	@echo "  make java         Run Java application (via Gradle)"
	@echo "  make kotlin       Run Kotlin application (via Gradle)"
	@echo ""
	@echo "  make chat         Run Agnostic Chat CLI (Python)"
	@echo "  make jira-report  Generate Jira release report (FROM=... TO=...)"
	@echo ""
	@echo "  make install      Install packages/dependencies across all subprojects"
	@echo "  make clean        Clean build artifacts and temporary caches"
	@echo "================================================================="

# Run all languages in sequence
run-all:
	@echo ""
	@echo "Running all language versions..."
	@echo ""
	@$(MAKE) js
	@$(MAKE) python
	@$(MAKE) ruby
	@$(MAKE) bash
	@$(MAKE) java
	@$(MAKE) kotlin
	@echo "All language runs completed successfully!"

# Individual language runners
js:
	@echo "--- [JavaScript] ---"
	@node javascript/src/index.js

python:
	@echo "--- [Python] ---"
	@python3 python/main.py

ruby:
	@echo "--- [Ruby] ---"
	@ruby ruby/main.rb

bash:
	@echo "--- [Bash] ---"
	@./bash/hello.sh

java:
	@echo "--- [Java] ---"
	@./gradlew :java:run --quiet

kotlin:
	@echo "--- [Kotlin] ---"
	@./gradlew :kotlin:run --quiet

# Standalone utilities & special cases
chat:
	@echo "--- [Agnostic Chat] ---"
	@PYTHONPATH=python python3 -m agnostic_chat.start

jira-report:
	@echo "--- [Jira Report] ---"
	@./bash/generateJiraReport.sh $(FROM) $(TO)

# Install dependencies across all modules
install:
	@echo "Installing JavaScript dependencies..."
	@cd javascript && npm install
	@echo "Installing Ruby dependencies..."
	@cd ruby && bundle check || bundle install
	@echo "Installing Python dependencies..."
	@pip3 install -r python/requirements.txt
	@echo "Installing/Resolving JVM dependencies..."
	@./gradlew build -x test

# Clean build artifacts
clean:
	@echo "Cleaning JVM build artifacts..."
	@./gradlew clean --quiet
	@echo "Cleaning Python caches..."
	@find . -type d -name "__pycache__" -exec rm -rf {} + 2>/dev/null || true
	@find . -type f -name "*.pyc" -delete 2>/dev/null || true
	@echo "Clean completed."

