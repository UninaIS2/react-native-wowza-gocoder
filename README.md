[![npm version](https://badge.fury.io/js/%40adrianso%2Freact-native-wowza-gocoder.svg)](https://badge.fury.io/js/%40adrianso%2Freact-native-wowza-gocoder)

# @adrianso/react-native-wowza-gocoder

This is a Native Module for React Native that integrates with Wowza GoCoder.

It is a fork of the https://github.com/medlmobileenterprises/react-native-wowza-gocoder repository, and has been updated to support the following:

- React Native 0.60 and above
- Wowza GoCoder SDK 1.9

## Installation

- [iOS instructions](./docs/INSTALL-iOS.md)
- [Android instructions](./docs/INSTALL-android.md)

## Usage

1. Import the module
   `import BroadcastView from '@adrianso/react-native-wowza-gocoder';`

2. Set a config

```javascript
const config = {
  hostAddress: '',
  applicationName: '',
  username: '',
  password: '',
  streamName: '',
  sdkLicenseKey: '',
};
```

3. Add functions for debug, testing

```javascript
const handleBroadcastStart = () => {
  console.log('Broadcast start');
};

const handleBroadcastFail = () => {
  console.log('Broadcast fail');
};

const handleBroadcastStatusChange = () => {
  console.log('Broadcast status change');
};

const handleBroadcastEventReceive = () => {
  console.log('Broadcast event receive');
};

const handleBroadcastErrorReceive = () => {
  console.log('Broadcast error receive');
};

const handleBroadcastVideoEncoded = () => {
  console.log('Broadcast encoded');
};

const handleBroadcastStop = () => {
  console.log('Broadcast stop');
};
```

4. Use the `<BroadcastView>` component in render

```javascript
<BroadcastView
  style={styles.videoContainer}
  hostAddress={config.hostAddress}
  applicationName={config.applicationName}
  broadcastName={config.streamName}
  username={config.username}
  password={config.password}
  sdkLicenseKey={config.sdkLicenseKey}
  sizePreset={3}
  videoOrientation="portrait"
  port={1935}
  broadcasting={false}
  muted={false}
  flashOn={false}
  frontCamera={false}
  onBroadcastStart={handleBroadcastStart}
  onBroadcastFail={handleBroadcastFail}
  onBroadcastStatusChange={handleBroadcastStatusChange}
  onBroadcastEventReceive={handleBroadcastEventReceive}
  onBroadcastErrorReceive={handleBroadcastErrorReceive}
  onBroadcastVideoEncoded={handleBroadcastVideoEncoded}
  onBroadcastStop={handleBroadcastStop}
/>
```

5. Be sure to use absolute positioning on your styles otherwise the video may not show correctly

```javascript
const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  videoContainer: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 40,
  },
});
```

- Optional: If you are familiar with controlling state then you could trigger start/stop of streaming by passing state in the BroadcastView component prop broadcasting. example broadcasting = {false} to broadcasting = {this.state.brodcasting} \*
  //Using the broadcast module

  `var BroadcastManager = NativeModules.BroadcastModule;`

  `BroadcastManager.startTimer(1, 3600);`

- Android only - first argument - timer interval, second argument time to timeout timer in seconds

- Stop Timer when stopping the broadcast - Android only  
   `BroadcastManager.stopTimer();`
