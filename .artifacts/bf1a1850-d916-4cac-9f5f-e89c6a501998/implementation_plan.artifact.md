# Fix Android Resource Linking Failed (Missing Theme)

The project is failing to build because `Theme.ExpenseTracker.Starting` is not found during AAPT resource linking, even though it appears to be defined in `res/values/themes.xml`. This can happen due to missing definitions in other resource configurations (like `values-night`) or build inconsistencies. Additionally, there is a JVM target mismatch in the Gradle configuration.

## Proposed Changes

### Build Configuration

#### [MODIFY] [app/build.gradle.kts](file:///D:/Android/Tracker/app/build.gradle.kts)
- Set `jvmTarget` for Kotlin to `11` to match the Java compatibility settings, or set both to a consistent version (e.g., `17` or `11`). I will use `11` as it is already set for Java.

### Resources

#### [MODIFY] [res/values/themes.xml](file:///D:/Android/Tracker/app/src/main/res/values/themes.xml)
- Verify and ensure the definition of `Theme.ExpenseTracker.Starting` is clean.

#### [MODIFY] [res/values-night/themes.xml](file:///D:/Android/Tracker/app/src/main/res/values-night/themes.xml)
- Add the `Theme.ExpenseTracker.Starting` style definition to ensure it's available in dark mode configurations.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:processDebugResources` to verify that resource linking succeeds.
- Run `./gradlew assembleDebug` to verify the entire build passes.

### Manual Verification
- Check if the app builds and can be deployed.
