# @adrianso/react-native-wowza-gocoder

[![npm version](https://badge.fury.io/js/%40adrianso%2Freact-native-wowza-gocoder.svg)](https://badge.fury.io/js/%40adrianso%2Freact-native-wowza-gocoder)

## About

This is a Native Module for React Native that integrates with Wowza GoCoder.

## Requirements

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
onBroadcastStart(){
  console.log("Broadcast start");
}
onBroadcastFail(){
  console.log("Broadcast fail");
}
onBroadcastStatusChange(){
  console.log("Broadcast status change");
}
onBroadcastEventReceive(){
  console.log("Broadcast event receive");
}
onBroadcastErrorReceive(){
  console.log("Broadcast error receive");
}
onBroadcastVideoEncoded(){
  console.log("Broadcast encoded");
}
onBroadcastStop(){
  console.log("Broadcast stop");
}
```

4. Use the component in render

```javascript
<BroadcastView
  style={styles.videoContainer}
  hostAddress={config.hostAddress}
  applicationName={config.applicationName}
  broadcastName={config.streamName}
  broadcasting={false}
  username={config.username}
  password={config.password}
  sizePreset={3}
  videoOrientation="portrait"
  port={1935}
  muted={false}
  flashOn={false}
  frontCamera={false}
  onBroadcastStart={this.onBroadcastStart}
  onBroadcastFail={this.onBroadcastFail}
  onBroadcastStatusChange={this.onBroadcastStatusChange}
  onBroadcastEventReceive={this.onBroadcastEventReceive}
  onBroadcastErrorReceive={this.onBroadcastErrorReceive}
  onBroadcastVideoEncoded={this.onBroadcastVideoEncoded}
  onBroadcastStop={this.onBroadcastStop}
  sdkLicenseKey={config.sdkLicenseKey}
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
