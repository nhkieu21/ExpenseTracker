# Fix Android Resource Linking Failed: Missing Material3 Theme

The project is failing to build because it references `Theme.Material3.DayNight.NoActionBar` in `themes.xml`, but the Google Material Components library is not included in the dependencies. Additionally, `Theme.ExpenseTracker.Starting` is used in `AndroidManifest.xml` but is not defined in the resource files.

## Proposed Changes

### [Component: Build Configuration]

#### [MODIFY] [libs.versions.toml](file:///D:/Android/Tracker/gradle/libs.versions.toml)
- Add the `material` library version and definition.

#### [MODIFY] [build.gradle.kts (app)](file:///D:/Android/Tracker/app/build.gradle.kts)
- Add `implementation(libs.material)` to the dependencies block.

### [Component: Resources]

#### [MODIFY] [themes.xml (values)](file:///D:/Android/Tracker/app/src/main/res/values/themes.xml)
- Define `Theme.ExpenseTracker.Starting` which inherits from `Theme.SplashScreen`.
- Set `postSplashScreenTheme` to `@style/Theme.ExpenseTracker`.

#### [MODIFY] [themes.xml (values-night)](file:///D:/Android/Tracker/app/src/main/res/values-night/themes.xml)
- Define `Theme.ExpenseTracker.Starting` for dark mode.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:processDebugResources` to verify that resource linking succeeds.
- Run a full build: `./gradlew assembleDebug`.

### Manual Verification
- Deploy the app to a device/emulator.
- Verify that the splash screen appears correctly and transitions to the main app theme.
