package com.myrosh.yi4kapidemo;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.xiaoyi.action.Logger;
import com.xiaoyi.action.Platform;
import com.xiaoyi.action.YICameraSDKDispatchQueue;

/**
 * Represents a camera rig.
 *
 */
class CamRig implements YICameraSDKDispatchQueue {
    private final ExecutorService mExecutorService;
    private final MessageLoop mMessageLoop;
    private Cam mCam;

    CamRig(String camIp) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mMessageLoop = new MessageLoop();
        mCam = new Cam(this, camIp);
    }

    public void initialize() {
        try {
            Platform.initialize(new Logger() {
                @Override
                public void verbose(String message) {
                    System.out.println("YiCameraPlatform V:" + message);
                }

                @Override
                public void info(String message) {
                    System.out.println("YiCameraPlatform I:" + message);
                }

                @Override
                public void warning(String message) {
                    System.out.println("YiCameraPlatform W:" + message);
                }

                @Override
                public void error(String message) {
                    System.out.println("YiCameraPlatform E:" + message);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        mExecutorService.execute(() -> {
            try {
                mMessageLoop.loop();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });

        mCam.connect();
    }

    @Override
    public void dispatch(Runnable task) {
        mMessageLoop.execute(task);
    }

    public void run() {
        while (true) {
            try {
                if (mCam.getState() == CamState.Connected) {
                    mCam.startRecording(new Date());

                    TimeUnit.SECONDS.sleep(10);

                    mCam.stopRecording();
                }

                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void uninitialize() {
        dispatch(() -> mMessageLoop.quit());

        mExecutorService.shutdown();

        try {
            Platform.uninitialize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCam.disconnect();
    }
}
