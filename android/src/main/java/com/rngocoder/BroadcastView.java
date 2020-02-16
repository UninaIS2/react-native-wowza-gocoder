package com.rngocoder;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.wowza.gocoder.sdk.api.broadcast.WOWZBroadcast;
import com.wowza.gocoder.sdk.api.broadcast.WOWZBroadcastConfig;
import com.wowza.gocoder.sdk.api.devices.WOWZAudioDevice;
import com.wowza.gocoder.sdk.api.devices.WOWZCameraView;
import com.wowza.gocoder.sdk.api.status.WOWZBroadcastStatus;
import com.wowza.gocoder.sdk.support.status.WOWZState;
import com.wowza.gocoder.sdk.support.status.WOWZStatus;
import com.wowza.gocoder.sdk.support.status.WOWZStatusCallback;
import com.wowza.gocoder.sdk.api.status.WOWZBroadcastStatusCallback;

import androidx.core.view.GestureDetectorCompat;
import com.wowza.gocoder.sdk.api.devices.WOWZCamera;


/**
 * Created by hugonagano on 11/7/16.
 */

public class BroadcastView extends FrameLayout implements LifecycleEventListener {
    public enum Events {
        EVENT_BROADCAST_START("onBroadcastStart"),
        EVENT_BROADCAST_STATUS_CHANGE("onBroadcastStatusChange"),
        EVENT_BROADCAST_FAIL("onBroadcastFail"),
        EVENT_BROADCAST_EVENT("onBroadcastEventReceive"),
        EVENT_BROADCAST_ERROR("onBroadcastErrorReceive"),
        EVENT_BROADCAST_VIDEO_ENCODED("onBroadcastVideoEncoded"),
        EVENT_BROADCAST_STOP("onBroadcastStop");

        private final String mName;

        Events(final String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

    private WOWZBroadcast broadcast;
    private WOWZBroadcastConfig broadcastConfig;
    private WOWZAudioDevice audioDevice;
    private WOWZCameraView cameraView  = null;
    private ThemedReactContext localContext;
    private String sdkLicenseKey;
    private String hostAddress;
    private String applicationName;
    private String broadcastName;
    private String username;
    private String password;
    private String videoOrientation;
    private boolean audioOnly;
    private RCTEventEmitter mEventEmitter;
    private boolean broadcasting = false;
    private boolean flashOn = false;
    private boolean frontCamera = false;
    private boolean muted = false;
    private int sizePreset;
    protected GestureDetectorCompat mAutoFocusDetector = null;
    
    public BroadcastView(ThemedReactContext context){
        super(context);

        localContext = context;
        mEventEmitter = localContext.getJSModule(RCTEventEmitter.class);
        audioDevice = new WOWZAudioDevice();
        cameraView = new WOWZCameraView(context);
        broadcast = new WOWZBroadcast();
        localContext.addLifecycleEventListener(this);
        cameraView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cameraView.getCamera().setTorchOn(false);
        this.addView(cameraView);
    }

    @Override
    public void onHostDestroy() {
        if(this.cameraView != null) {
            this.stopCamera();
        }
    }

    @Override
    public void onHostPause() {
        if(this.cameraView != null){
            this.cameraView.stopPreview();
        }
    }

    @Override
    public void onHostResume() {


        if(broadcastConfig == null && this.cameraView != null) {
            broadcastConfig = BroadcastManager.initBroadcast(
                localContext, 
                getHostAddress(), 
                getApplicationName(), 
                getBroadcastName(), 
                getSdkLicenseKey(), 
                getUsername(), 
                getPassword(), 
                getSizePreset(), 
                getVideoOrientation(), 
                this.cameraView, 
                audioDevice
            );
            this.cameraView.setCameraConfig(broadcastConfig);
        }

        if(this.cameraView != null){
            this.cameraView.startPreview();
            int numCameras = WOWZCamera.getNumberOfDeviceCameras(localContext);
        }

        if (broadcastConfig != null && this.cameraView != null) {
            if (mAutoFocusDetector == null)
                mAutoFocusDetector = new GestureDetectorCompat(localContext, new AutoFocusListener(localContext, cameraView));


            WOWZCamera activeCamera = this.cameraView.getCamera();

            if (activeCamera != null && activeCamera.hasCapability(WOWZCamera.FOCUS_MODE_CONTINUOUS))
                activeCamera.setFocusMode(WOWZCamera.FOCUS_MODE_CONTINUOUS);
        }
    }


    public void setCameraType(Integer cameraType) {
        this.cameraView.setCamera(cameraType);
    }

    public void setFlash(boolean flag) {
        this.cameraView.getCamera().setTorchOn(flag);
    }

    public void stopCamera() {
        this.cameraView.stopPreview();
        this.cameraView = null;
    }


    public String getSdkLicenseKey() {
        return sdkLicenseKey;
    }

    public void setSdkLicenseKey(String sdkLicenseKey) {
        this.sdkLicenseKey = sdkLicenseKey;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public void setBroadcastName(String broadcastName) {
        this.broadcastName = broadcastName;

        if (broadcastConfig != null) {
            BroadcastManager.changeStreamName(broadcastConfig, this.broadcastName);
        }
    }

    public int getSizePreset() {
        return sizePreset;
    }

    public void setSizePreset(int sizePreset) {
        this.sizePreset = sizePreset;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVideoOrientation() { return videoOrientation; }

    public void setVideoOrientation(String videoOrientation) { this.videoOrientation = videoOrientation; }

    public boolean isBroadcasting() {
        return broadcasting;
    }
    

    public void setBroadcasting(boolean broadcasting) {

        WOWZCamera activeCamera = this.cameraView.getCamera();
        if (activeCamera != null && activeCamera.hasCapability(WOWZCamera.FOCUS_MODE_CONTINUOUS))
            activeCamera.setFocusMode(WOWZCamera.FOCUS_MODE_CONTINUOUS);
            
        if(broadcastConfig == null){
            return;
        }

        if(!broadcast.getStatus().isBroadcasting()){
            if(isNoVideo()){
                broadcastConfig.setVideoEnabled(false);
            }else{
                broadcastConfig.setVideoEnabled(true);
            }
            BroadcastManager.startBroadcast(broadcast, broadcastConfig, new WOWZBroadcastStatusCallback(){


                @Override
                public void onWZStatus(WOWZBroadcastStatus wowzBroadcastStatus) {
                    if(wowzBroadcastStatus.isBroadcasting()) {
                        WritableMap broadcastEventState = Arguments.createMap();
                        broadcastEventState.putString("status", "started");
                        mEventEmitter.receiveEvent(getId(), Events.EVENT_BROADCAST_START.toString(), broadcastEventState);
                    } else if(wowzBroadcastStatus.isIdle()) {
                        WritableMap broadcastEventState = Arguments.createMap();
                        broadcastEventState.putString("status", "stoped");
                        mEventEmitter.receiveEvent(getId(), Events.EVENT_BROADCAST_STOP.toString(), broadcastEventState);
                    }else if(wowzBroadcastStatus.isReady()) {
                        WritableMap broadcastEventState = Arguments.createMap();
                        broadcastEventState.putString("status", "ready");
                        mEventEmitter.receiveEvent(getId(), Events.EVENT_BROADCAST_STATUS_CHANGE.toString(), broadcastEventState);
                    }
                }

                @Override
                public void onWZError(WOWZBroadcastStatus wowzBroadcastStatus) {
                    WritableMap broadcastEventState = Arguments.createMap();
                    broadcastEventState.putString("status", "error");
                    mEventEmitter.receiveEvent(getId(), Events.EVENT_BROADCAST_ERROR.toString(), broadcastEventState);
                }
            });
        } else {
        if (broadcast.getStatus().isBroadcasting()) {
            broadcast.endBroadcast(new WOWZStatusCallback() {

                @Override
                public void onWZStatus(WOWZStatus wzStatus) {

                }

                @Override
                public void onWZError(WOWZStatus wzStatus) {
                    if (wzStatus.getLastError() != null) {
                        WritableMap error = Arguments.createMap();
                        error.putString("error", wzStatus.getLastError().toString());
                        mEventEmitter.receiveEvent(getId(), Events.EVENT_BROADCAST_FAIL.toString(), error);
                    }
                }
            });
        }
        }
        broadcast.endBroadcast();
    }

    public boolean isFlashOn() {
        return flashOn;
    }

    public void setFlashOn(boolean flashOn) {
        if(cameraView != null) {
            BroadcastManager.turnFlash(cameraView, flashOn);
            this.flashOn = flashOn;
        }
    }

    public boolean isFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(boolean frontCamera) {
        if(cameraView != null && cameraView.isPreviewing()) {
            int numCameras = WOWZCamera.getNumberOfDeviceCameras(localContext);

            BroadcastManager.invertCamera(cameraView);
            if(numCameras > 1 ){
                this.frontCamera = frontCamera;
            }
        }
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        if(audioDevice != null) {
            BroadcastManager.mute(audioDevice, muted);
            this.muted = muted;
        }
    }

    public boolean isNoVideo() {
        return audioOnly;
    }

    public void setAudioOnly(boolean audioOnly) {
        this.audioOnly = audioOnly;
    }

}

