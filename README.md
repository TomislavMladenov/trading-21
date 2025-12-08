# Trading 21

This is an Android application built to demonstrate modern Android application development.

## 🛠️ Built With

This project takes advantage of many popular and modern libraries in the Android ecosystem:

*   **Core & Language:**
    *   [Kotlin](https://kotlinlang.org/): The official language for Android development.
    *   [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html): For asynchronous programming.
    *   [Hilt](https://dagger.dev/hilt/): A most common and adopted dependency injection framework for Android.

*   **UI (Jetpack Compose):**
    *   [Jetpack Compose](https://developer.android.com/jetpack/compose): Android'''s modern toolkit for building native UI.
    *   [Material 3](https://m3.material.io/): The latest version of Google'''s open-source design system.
    *   [Lifecycle-aware components](https://developer.android.com/jetpack/compose/state#lifecycle-aware): For observing data streams in a lifecycle-conscious way.

*   **Architecture:**
    *   MVI (Model-View-Intent): A unidirectional data flow design pattern for the presentation layer.
    *   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): To store and manage UI-related data in a lifecycle conscious way.
    *   [Navigation Component](https://developer.android.com/guide/navigation): To handle navigation between screens.

*   **Networking:**
    *   [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor): For logging HTTP request and response data.
    *   [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization): For converting JSON data to Kotlin objects.

*   **Logging:**
    *   [Timber](https://github.com/JakeWharton/timber): A logger with a small, extensible API which provides utility on top of Android'''s normal `Log`.

## 🏗️ Project Structure

The project is structured by feature, with a `base` module containing common components like `BaseViewModel`, navigation, and UI atoms. Each feature follows the MVI pattern, containing:

*   `mvi`
    *   `Action`: Sealed class representing user intents.
    *   `State`: Data class representing the UI state.
*   `ViewModel`: Extends `BaseViewModel` and handles the feature's logic.
*   `Content`: The main Composable UI for the feature.
*   `Screen`: The entry point Composable for the feature, responsible for instantiating the ViewModel and collecting state.

## 🚀 How to build

1.  Clone this repository.
2.  Open the project in Android Studio.
3.  Wait for Gradle to sync the project.
4.  Build and run the `app` configuration on an Android emulator or a physical device.

## 👉 Area to improve

1. Add loading states to the UI.
2. Proper error handling mechanism with UI actions if needed
3. Unit test coverage 
4. Add static code analysis such as Detekt, Klint
5. CI/CD 
