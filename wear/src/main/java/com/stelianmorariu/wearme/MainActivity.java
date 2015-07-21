package com.stelianmorariu.wearme;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    private final Messenger mMessenger = new Messenger(new IncomingMessageHandler());
    private Messenger mServiceMessenger = null;
    private boolean mIsBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mIsBound = true;
            mServiceMessenger = new Messenger(service);

            // register client messenger
            try {
                Message msg = Message.obtain(null, WearService.MSG_REGISTER);
                msg.replyTo = mMessenger;
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);
    }

    @Override
    public void onResume() {
        super.onResume();

        startService(new Intent(this,WearService.class));
        bindToService();

    }

    @Override
    public void onPause() {
        super.onPause();
        unbindFromService();
    }


    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private boolean bindToService() {
        return bindService(new Intent(this,WearService.class), mConnection, Context.BIND_ABOVE_CLIENT);
    }

    private void unbindFromService() {
        if (mIsBound && mServiceMessenger != null) {
            try {
                Message msg = Message.obtain(null, WearService.MSG_UNREGISTER);
                msg.replyTo = mMessenger;
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                // There is nothing special we need to do if the service has crashed.
            }
        }

        try {
            unbindService(mConnection);
            mIsBound = false;
        } catch (Exception e) {
        }

    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }

    private class IncomingMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WearService.MSG_TIMER:
                    mTextView.setText(String.valueOf(msg.obj));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }


    }
}
