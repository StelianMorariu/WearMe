package com.stelianmorariu.wearme;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WearService extends Service {
    public static final int MSG_REGISTER = 332;
    public static final int MSG_UNREGISTER = 333;
    public static final int MSG_TIMER = 334;
    private static final String LOG_TAG = WearService.class.getSimpleName();

    private final Messenger mMessenger = new Messenger(new IncomingMessageHandler());
    private Messenger mClientMessenger = null;
    private long mSavedSessionDuration;
    private ScheduledExecutorService mScheduler;
    private ScheduledFuture<?> mTimerTask;
    private Runnable mTimeTicker = new Runnable() {
        @Override
        public void run() {
            mSavedSessionDuration += 1000;
            sendDuration();
        }
    };

    public WearService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void sendDuration() {
        try {
            if (mClientMessenger != null) {
                Message msg = Message.obtain(null, MSG_TIMER);
                msg.obj = mSavedSessionDuration;
                mClientMessenger.send(msg);
            }
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, ex.toString());
        }
    }

    private class IncomingMessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER:
                    mClientMessenger = msg.replyTo;
                    mTimerTask = mScheduler.scheduleAtFixedRate(mTimeTicker, 0, 1000, TimeUnit.MILLISECONDS);
                    break;
                case MSG_UNREGISTER:
                    mTimerTask.cancel(true);
                    mClientMessenger = null;
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }
}
