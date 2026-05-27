TADAM — Currency Exchange App
----------------------------------------
Purpose
Show live currency rates and convert amounts.

Stack
- Kotlin
- Jetpack Compose
- MVVM
- Retrofit + Coroutines

Arch
UI → ViewModel → Repository → API

Relevant files:
- `MainActivity.kt` — entry point
- `TadamApp.kt` — navigation
- `RatesScreen.kt` — list rates
- `ConverterScreen.kt` — convert UI
- `CurrencyViewModel.kt` — state & logic
- `CurrencyRepository.kt` / `CurrencyApiService.kt` — network

```mermaid
flowchart TB
    MA[MainActivity]

    NAV[NavHost]

    RS[RatesScreen]
    CS[ConverterScreen]

    VM[ViewModel]
    REPO[Repository]
    API[Retrofit API]

    %% Navigation
    MA --> NAV
    NAV --> RS
    NAV --> CS

    %% Screens -> ViewModel
    RS --> VM
    CS --> VM

    %% Data flow
    VM --> REPO
    REPO --> API
```