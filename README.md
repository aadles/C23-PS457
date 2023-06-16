# TasteMatch App
TasteMatch -  Indonesian Meal Recommendation with Personalized Recipe - C23-PS457

TasteMatch is an Android application that allows users to discover and share their favorite recipes. The app utilizes various features and libraries to provide a seamless and enjoyable user experience.

## Features

- Discover and explore a wide range of recipes.
- Create and manage your own recipe collection.
- Search for recipes based on specific criteria.
- View detailed information about each recipe, including ingredients and preparation steps.
- Upload and share your own recipes with the community.
- Save your favorite recipes for quick access.
- Integrate with Firebase for authentication and data storage.
- Utilize TensorFlow Lite to support machine learning capabilities.
- Use CameraX to capture and upload photos of your dishes.
- Apply Material Design principles for a visually appealing UI.
- Use Glide and Picasso for efficient image loading and caching.
- Store user preferences using AndroidX Preference library.
- Implement local data persistence with Room database.
- Leverage Retrofit for network requests and API integration.
- Include Lottie animations for enhanced visual effects.

## Requirements

- Android API level 26 or higher.
- Kotlin version 1.8 or higher.

## Installation

1. Clone the repository from GitHub:

   ```
   git clone https://github.com/your-username/TasteMatch.git
   ```

2. Open the project in Android Studio.

3. Build the project and ensure that all dependencies are successfully resolved.

4. Connect your Android device or use an emulator with minimum API level 26.

5. Run the application on your device/emulator.

## Configuration

To configure the app, you may need to set up the following:

1. Firebase Authentication and Firestore: Set up a Firebase project and replace the `google-services.json` file in the app module with your own.

2. API Keys: If the app integrates with any external APIs, such as recipe data or image hosting services, ensure that you have obtained the necessary API keys and replace them in the appropriate files.

3. Additional Configuration: Review the documentation and code comments to configure any other required settings specific to your environment.

## Dependencies

The TasteMatch app relies on the following dependencies:

- `androidx.core:core-ktx:1.10.1`
- `androidx.appcompat:appcompat:1.6.1`
- `androidx.constraintlayout:constraintlayout:2.1.4`
- `org.tensorflow:tensorflow-lite-support:0.1.0`
- `org.tensorflow:tensorflow-lite-metadata:0.1.0`
- `junit:junit:4.13.2` (for testing)
- `androidx.test.ext:junit:1.1.5` (for Android testing)
- `androidx.test.espresso:espresso-core:3.5.1` (for UI testing)
- `androidx.annotation:annotation:1.6.0`
- `androidx.navigation:navigation-fragment-ktx:2.6.0`
- `androidx.navigation:navigation-ui-ktx:2.6.0`
- `androidx.viewpager2:viewpager2:1.0.0`
- `com.google.android.material:material:1.9.0`
- `androidx.camera:camera-camera2:1.3.0-alpha06` (for CameraX)
- `androidx.camera:camera-lifecycle:1.3.0-alpha06` (for CameraX)
- `androidx.camera:camera-view:1.3.0-alpha06` (for CameraX)
- `com.google.firebase:firebase-auth:22.0.0` (for Firebase Authentication)
- `com.google.firebase:firebase-firestore:24.6.1` (for Firebase Firestore)
- `com.google.firebase:firebase-common-ktx:

20.3.2` (for Firebase Common)
- `com.squareup.picasso:picasso:2.71828` (for image loading)
- `com.github.bumptech.glide:glide:4.12.0` (for image loading)
- `androidx.preference:preference-ktx:1.2.0` (for preferences)
- `androidx.preference:preference:1.2.0` (for preferences)
- `androidx.room:room-ktx:2.5.1` (for Room database)
- `androidx.room:room-runtime:2.5.1` (for Room database)
- `androidx.room:room-compiler:2.5.1` (for Room database)
- `androidx.room:room-testing:2.5.1` (for Room testing)
- `androidx.activity:activity-ktx:1.7.2` (for Lifecycle)
- `androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1` (for Lifecycle)
- `androidx.lifecycle:lifecycle-livedata-ktx:2.6.1` (for Lifecycle)
- `androidx.lifecycle:lifecycle-runtime-ktx:2.6.1` (for Lifecycle)
- `com.squareup.retrofit2:retrofit:2.9.0` (for Retrofit)
- `com.squareup.retrofit2:converter-gson:2.9.0` (for Retrofit)
- `com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6` (for OkHttp Logging Interceptor)
- `com.airbnb.android:lottie:3.4.0` (for Lottie animations)

## Contributing

Contributions to the TasteMatch app are welcome! If you find any issues or have suggestions for improvements, please feel free to create a pull request or submit an issue on the GitHub repository.

## License

This project is licensed under the [MIT License](LICENSE). You are free to use, modify, and distribute this software as you see fit.
