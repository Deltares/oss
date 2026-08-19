
% Copilot instructions for the `oss` workspace

Purpose
-------
This file describes the default, workspace-specific instructions that an automated coding assistant (Copilot) should follow when making suggestions or applying edits in this repository. Follow these guidelines whenever you open, modify, or create files in this workspace.

Project overview
----------------
- Multi-module Java/Gradle project located at the repository root. Main Gradle wrapper files: `gradlew`, `gradlew.bat`, `build.gradle`, `settings.gradle`.
- Submodules live under `modules/`.
- Themes live under `themes/`. These are frontend assets (a `package.json`).

High-level goals for edits
-------------------------
1. Preserve build stability: avoid changes that break a full `./gradlew build` (on Windows: `gradlew.bat build`).
2. Make the minimal change necessary to implement a fix or feature.
3. Run and prefer project-provided build/test/format tooling before proposing changes as final.
4. When in doubt, add tests and keep public APIs backwards compatible.

How to build locally (Windows)
-------------------------------
Use the Gradle wrapper to build the whole project from the repository root:

```
gradlew.bat build
```

To run tests for a single module (example):

```
cd modules/registration-service
..\..\gradlew.bat :modules:registration-service:test
```

If you need a clean build:

```
gradlew.bat clean build
```

Frontend (node) tasks
----------------------
If you need to run Node/NPM tasks for the repo root frontend:

```
npm install
npm test
```

Adjust the working directory if you operate within a module with its own `package.json`.

Docker / local Liferay environment
----------------------------------
This repository includes a Docker-based Liferay development environment that can be started from the repository root. On Windows use the Gradle wrapper batch file; on Unix-like systems use the shell wrapper.

Start Docker/Liferay (Windows - cmd.exe):

```
gradlew.bat startLiferay
```

Start Docker/Liferay (Unix/macOS):

```
./gradlew startLiferay
```

Notes:
- Wait for the containers to finish starting before deploying modules. You can check container status with `docker ps` and inspect logs with `docker-compose logs` if needed.
- Once Docker/Liferay is up you can deploy modules from the repository root using Gradle. On Windows:

```
gradlew.bat deploy
```

Or on Unix/macOS:

```
./gradlew deploy
```

You can also deploy a single module by running the module's deploy task, for example:

```
cd modules/registration-service
..\..\gradlew.bat :modules:registration-service:deploy
```

Adjust paths and task names as needed for specific modules.

Formatting / linting
--------------------
- Keep existing formatting conventions. If the repo has a formatting tool configured, prefer using that (e.g. `./gradlew fmt` if present) before changing formatting manually.
- Add or update lint fixes conservatively and run the associated checks locally.

Testing requirements
--------------------
- For any non-trivial code change, add or update automated tests (unit tests or integration tests) that demonstrate the fix/feature.
- Run `gradlew.bat test` (or module-specific tasks) and ensure tests pass locally before finalizing changes.

Dependency management
---------------------
- When adding/updating dependencies update only the smallest set of build files necessary.
- Run `gradlew.bat build` after dependency changes to verify nothing else broke.
- Prefer using existing BOMs and aligned versions used across modules when possible.

Commit and PR guidelines
------------------------
- Keep commits small and focused: one logical change per commit.
- Commit messages should have a short summary and a short body when necessary. Example:

  "Fix NPE in UserRegistrationService when email is null\n\n  Add a null-check and a unit test that covers the edge case."

- When proposing changes as a patch in this workspace, include the commands you ran to validate (build/test) and a brief summary of results.

Files and directories to avoid / sensitive files
---------------------------------------------
- Do not open or modify secrets or local credential files unless the user explicitly asks and permits it. Examples in this repository include but may not be limited to:
  - `gradle-local-with-pw.properties`
  - `gradle-local.properties`
  - `example-gradle-local.properties` (read-only example ok)
  - any `*.keystore`, `*.p12`, or other credential artifacts

- Avoid leaking values from these files into diffs or suggestions.

When making automated edits
--------------------------
- Create minimal, well-scoped patches. Use the repository coding style and preserve formatting of surrounding code.
- Add missing imports and adjust related build files only when required.
- If a refactor touches many modules, prefer splitting into multiple PRs and run a full build after each stage.

If tests or build fail after an edit
----------------------------------
1. Re-run with `--stacktrace` and capture the failing task output.
2. Try to localize failure to a single module and create a failing unit test if one does not already exist.
3. Propose a fix with the minimal change and include the failing output and the verification steps in the patch description.

Additional helpful repository hints
----------------------------------
- The repository contains many frontend dependencies in `node_modules_cache/` (cached tarballs) — prefer using the provided `package.json` scripts rather than manual changes to `node_modules_cache`.

Contact / follow-up
-------------------
If you want me to open or modify specific files, list them and I will:
 - read their current contents,
 - propose the precise minimal patch(s), and
 - run / describe the verification steps I used (build/test commands and results).

If you want stricter or different instructions (for example: prefer Kotlin DSL for Gradle, or use a specific Java style guide), update this file to reflect those preferences.

-- End of copilot-instructions.md


