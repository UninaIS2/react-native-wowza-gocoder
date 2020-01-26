/**
 * Example React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {useState, useEffect, Component} from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Image,
  Dimensions,
  Alert,
  Platform,
} from 'react-native';
import BroadcastView from '@adrianso/react-native-wowza-gocoder';
import {check, request, PERMISSIONS} from 'react-native-permissions';
import config from './wowzaConfig';
const {width, height} = Dimensions.get('window');

export default App = () => {
  const [isBroadcasting, setBroadcasting] = useState(false);
  const [isMuted, setMuted] = useState(false);
  const [isFlashEnabled, setFlashEnabled] = useState(false);
  const [isFrontCamera, setFrontCamera] = useState(false);
  const [recordingTime, setRecordingTime] = useState('00:00:00');
  const [recordButtonImage, setRecordButtonImage] = useState(
    require('./assets/Rec.png'),
  );
  const [permissionGranted, setPermissionGranted] = useState(false);

  const requestPermissions = async () => {
    let cameraPermission = false;
    let microphonePermission = false;

    const response = await request(
      Platform.OS === 'ios'
        ? PERMISSIONS.IOS.CAMERA
        : PERMISSIONS.ANDROID.CAMERA,
    );
    if (response === 'granted') {
      cameraPermission = true;
    }

    const resp = await request(
      Platform.OS === 'ios'
        ? PERMISSIONS.IOS.MICROPHONE
        : PERMISSIONS.ANDROID.RECORD_AUDIO,
    );
    if (resp === 'granted') {
      microphonePermission = true;
    }

    if (!(cameraPermission && microphonePermission)) {
      Alert.alert(
        'Broadcast',
        'In order to broadcast We need access to your camera, microphone',
        [
          {text: 'No way', onPress: null},
          {text: 'Open Settings', onPress: Permissions.openSettings},
        ],
      );
    }

    setPermissionGranted(cameraPermission && microphonePermission);
  };

  const handleBroadcastStart = () => {
    setRecordButtonImage(require('./assets/Stop.png'));
  };

  const handleBroadcastFail = error => {
    console.warn('Failed to broadcast: ', error);
  };

  const handleBroadcastStatusChange = status => {
    console.warn('Broadcast status changed', status);
  };

  const handleBroadcastEventReceive = event => {
    console.warn('Broadcast event received', event);
  };

  const handleBroadcastErrorReceive = error => {
    console.warn('Broadcast error received', error);
  };

  const formatCurrentTime = currentTime => {
    let time = Number(currentTime);
    var h = Math.floor(time / 3600);
    var m = Math.floor((time % 3600) / 60);
    var s = Math.floor((time % 3600) % 60);
    return (
      (h > 0 ? h + ':' + (m < 10 ? '0' : '') : '00:') +
      (m > 0 ? m : '00') +
      ':' +
      (s < 10 ? '0' : '') +
      s
    );
  };

  const handleBroadcastVideoEncoded = time => {
    setRecordingTime(formatCurrentTime(time.encoded));
  };

  const handleBroadcastStop = () => {
    setRecordButtonImage(require('./assets/Rec.png'));
  };

  useEffect(() => {
    (async () => {
      const [camera, microphone] = await Promise.all([
        check(
          Platform.OS === 'ios'
            ? PERMISSIONS.IOS.CAMERA
            : PERMISSIONS.ANDROID.CAMERA,
        ),
        check(
          Platform.OS === 'ios'
            ? PERMISSIONS.IOS.MICROPHONE
            : PERMISSIONS.ANDROID.RECORD_AUDIO,
        ),
      ]);

      const granted = camera === 'granted' && microphone === 'granted';
      if (!granted) {
        requestPermissions();
      }

      setPermissionGranted(granted);
    })();
  }, []);

  if (!permissionGranted) {
    return <View style={{backgroundColor: 'black'}}></View>;
  }

  return (
    <View style={styles.container}>
      <BroadcastView
        style={styles.contentArea}
        hostAddress={config.hostAddress}
        applicationName={config.applicationName}
        sdkLicenseKey={config.sdkLicenseKey}
        broadcastName={config.streamName}
        username={config.username}
        password={config.password}
        backgroundMode={false}
        sizePreset={2}
        videoOrientation="portrait"
        broadcasting={isBroadcasting}
        muted={isMuted}
        flashOn={isFlashEnabled}
        frontCamera={isFrontCamera}
        onBroadcastStart={handleBroadcastStart}
        onBroadcastFail={handleBroadcastFail}
        onBroadcastStatusChange={handleBroadcastStatusChange}
        onBroadcastEventReceive={handleBroadcastEventReceive}
        onBroadcastErrorReceive={handleBroadcastErrorReceive}
        onBroadcastVideoEncoded={handleBroadcastVideoEncoded}
        onBroadcastStop={handleBroadcastStop}
      />

      <Text style={styles.recordingTimerLabel}>{recordingTime}</Text>
      <View style={styles.cameraControls}>
        <TouchableOpacity
          onPress={() => {
            setFrontCamera(!isFrontCamera);
          }}>
          <Image source={require('./assets/Flip.png')}></Image>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={() => {
            setBroadcasting(!isBroadcasting);
          }}>
          <Image
            source={recordButtonImage}
            style={styles.cameraControlsButton}></Image>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={() => {
            setFlashEnabled(!isFlashEnabled);
          }}>
          <Image
            source={require('./assets/Torch.png')}
            style={styles.cameraControlsButton}></Image>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
    justifyContent: 'center',
    flexDirection: 'row',
    alignItems: 'flex-end',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  contentArea: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0,0,0,0.75)',
  },
  recordingTimerLabel: {
    position: 'absolute',
    top: 36,
  },
  cameraControls: {
    marginBottom: 16,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    width: width - 32,
  },
  cameraControlsButton: {
    width: 60,
    height: 60,
  },
});
