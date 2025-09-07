# android-reference-arch

Reference architecture for a modern Android Task/Notes app.

## Modules

| Module           | Purpose                                           | Namespace                              |
|-----------------|-------------------------------------------------|----------------------------------------|
| :app            | Application entrypoint, wiring features        | com.codewithparas.app                  |
| :core:designsystem | Material 3 theming tokens, preview components | com.codewithparas.core.designsystem   |
| :core:ui        | Reusable UI components (app bars, dialogs)     | com.codewithparas.core.ui             |
| :core:common    | Utilities, constants, shared logic             | com.codewithparas.core.common         |
| :data:db        | Room database module                            | com.codewithparas.data.db             |
| :data:network   | Retrofit/network module                          | com.codewithparas.data.network        |
| :feature:tasks  | Task list/edit screens and feature logic        | com.codewithparas.feature.tasks       |

## Module Dependencies

```mermaid
graph TD
    app --> feature-tasks
    app --> core-designsystem
    app --> core-ui
    app --> core-common
    feature-tasks --> data-db
    feature-tasks --> data-network
    feature-tasks --> core-ui
    feature-tasks --> core-designsystem
    feature-tasks --> core-common
    data-db --> core-common
    data-network --> core-common
    core-ui --> core-designsystem

Getting Started

Clone the repo

git clone git@github.com:your-username/android-reference-arch.git


Open in Android Studio

Open the cloned folder in Android Studio

Let Gradle sync all modules

Build the project

./gradlew assembleDebug


(Windows users: gradlew.bat assembleDebug)

Follow architecture patterns

MVVM for UI

Clean Architecture (core → data → feature → app)

Add new modules/features following the module strategy (see docs/adr/ADR-001-module-strategy.md)
