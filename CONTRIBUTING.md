# Contributing

## Development Setup

1. Clone the repo:
```bash
git clone git@github.com:your-username/android-reference-arch.git
Open in Android Studio

Open the cloned folder

Let Gradle sync all modules

Build the project

bash
Copy code
./gradlew assembleDebug
(Windows users: gradlew.bat assembleDebug)

Follow module strategy

When adding new modules or features, follow the rules in docs/adr/ADR-001-module-strategy.md

Code Quality
Follow Kotlin conventions

Use ktlint for formatting

Use detekt for static analysis

Adhere to MVVM + Clean Architecture patterns