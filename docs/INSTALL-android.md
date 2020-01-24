## Android installation

1. Download the latest Android Wowza Gocoder SDK from `https://www.wowza.com/pricing/installer#gocodersdk-downloads`.
2. Unzip the file, and copy the `com.wowza.gocoder.sdk.aar` file into the following directory:
   `<PROJECT_ROOT>/android/app/libs`
3. cd into your `<PROJECT_ROOT>` directory.
4. `yarn add @adrianso/react-native-wowza-gocoder`
5. Add the `flatDir` block to the `allproject.repositories` block of your `<PROJECT_ROOT>/android/build.gradle` file, like so:

```gradle
allprojects {
  repositories {
    flatDir {
        dirs 'libs'
    }
  }
}
```

6. Add the following line to the `dependencies` block of your `<PROJECT_ROOT>/android/app/build.gradle` file

```gradle
dependencies {
  implementation 'com.wowza.gocoder.sdk.android:com.wowza.gocoder.sdk:2.0.0@aar'
}
```

7. Add the following permissions to your AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.FLASHLIGHT" />
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
```

8. Compile your Android app

## References

- https://www.wowza.com/docs/how-to-install-gocoder-sdk-for-android
