
---

## 2️⃣ `/docs/adr/ADR-001-module-strategy.md`

```markdown
# ADR-001: Module Strategy

## Context
To create a scalable modern Android app, the code is split into multiple Gradle modules:

- **Core modules:** reusable UI, theming, utilities  
- **Data modules:** Room DB, Retrofit network  
- **Feature modules:** business logic and UI  
- **App module:** entrypoint, wiring features together

## Decision
- `:core:designsystem` → theming  
- `:core:ui` → reusable UI components  
- `:core:common` → utilities and shared logic  
- `:data:db` → Room database  
- `:data:network` → Retrofit/network  
- `:feature:tasks` → task feature screens and logic  
- `:app` → glue everything together

## Module Dependencies
- `:app` → depends on all modules  
- `:feature:*` → depends on `:data:*` and `:core:*`  
- `:data:*` → depends only on `:core:common`  
- `:core:*` → no dependencies (except `:core:ui` can depend on `:core:designsystem`)  

This ensures **one-way dependencies** and avoids circular references.

## Consequences
- Clear separation of concerns  
- One-way dependencies (avoids circular dependencies)  
- Easier testing and modularization
