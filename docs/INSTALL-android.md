## Android installation

- cd into your project root directory.
- `yarn add @adrianso/react-native-wowza-gocoder`
- Download the latest Android Wowza Gocoder SDK from `https://www.wowza.com/pricing/installer#gocodersdk-downloads`.
- Unzip the file, and copy the `com.wowza.gocoder.sdk.aar` file into the following 2 locations:
  1. <PROJECT_ROOT>/android/app/libs
  2. <PROJECT_ROOT>/node_modules/@adrianso/react-native-wowza-gocoder/android/libs
- Add the `flatDir` block to the `allproject.repositories` block of your `<PROJECT_ROOT>/android/build.gradle` file, like so:
  ```gradle
  allprojects {
    repositories {
      flatDir {
          dirs 'libs'
      }
    }
  }
  ```
- Add the following line to the `dependencies` block of your `<PROJECT_ROOT>/android/app/build.gradle` file
  ```gradle
  dependencies {
    implementation 'com.wowza.gocoder.sdk.android:com.wowza.gocoder.sdk:2.0.0@aar'
  }
  ```
- Add the following permissions to your AndroidManifest.xml
  ```xml
  <uses-permission android:name="android.permission.CAMERA" />
  <uses-permission android:name="android.permission.RECORD_AUDIO" />
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.FLASHLIGHT" />
  <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
  ```
- Compile your Android app

## References

- https://www.wowza.com/docs/how-to-install-gocoder-sdk-for-android
