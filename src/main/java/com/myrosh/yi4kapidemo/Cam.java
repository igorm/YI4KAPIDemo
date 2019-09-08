package com.myrosh.yi4kapidemo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.xiaoyi.action.ActionCamera;
import com.xiaoyi.action.ActionCameraListener;
import com.xiaoyi.action.SystemMode;
import com.xiaoyi.action.YICameraSDKError;

/**
 * Represents a camera.
 *
 */

enum CamState {
    Disconnected,
    Connected,
    StartRecording,
    Recording
}

class Cam extends ActionCameraListener {
    private CamRig mRig;
    private CamState mState;
    private ActionCamera mYi4k;
    private String mIp;

    Cam(CamRig rig, String ip) {
        if (rig == null) {
            throw new NullPointerException("rig cannot be null");
        }

        if (ip == null) {
            throw new NullPointerException("ip cannot be null");
        }

        mRig = rig;
        mIp = ip;
        mState = CamState.Disconnected;
    }

    public void connect() {
        mYi4k = new ActionCamera(this, mRig);
        mYi4k.connect("tcp:" + mIp + ":7878");
    }

    public void disconnect() {
        if (mYi4k != null) {
            mYi4k.disconnect();
        }
    }

    public void startRecording(Date startTime) {
        if (mState == CamState.Connected) {
            mState = CamState.StartRecording;

            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(startTime);

            mYi4k.stopViewFinder(null, null)
               .stopRecording(null, null)
               .setSystemMode(SystemMode.Record, null, null)
               .startCommandGroup()
               .setDateTime(new Date(), null, null)
               .startRecording(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), () -> {
                   System.out.println("STARTED RECORDING");
               }, null)
               .submitCommandGroup(null, (cameraSDKError) -> {
                   System.out.println("FAILED TO START RECORDING");

                   if (mState == CamState.StartRecording) {
                       mState = CamState.Connected;
                   }
               });
        }
    }

    public void stopRecording() {
        if (mState == CamState.StartRecording || mState == CamState.Recording) {
            mYi4k.stopRecording(() -> {
               System.out.println("STOPPED RECORDING");
            }, null);
            mState = CamState.Connected;
        }
    }

    public CamState getState() {
        return mState;
    }

    @Override
    public void onConnected() {
        System.out.println("CONNECTED");

        mState = CamState.Connected;
    }

    @Override
    public void onClosed(YICameraSDKError error) {
        System.out.println("DISCONNECTED");

        mState = CamState.Disconnected;
    }
}
