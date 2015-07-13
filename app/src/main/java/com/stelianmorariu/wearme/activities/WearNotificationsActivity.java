/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.HapticFeedbackConstants;
import android.view.View;

import com.stelianmorariu.wearme.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stelian Morariu on 7/7/2015.
 */
public class WearNotificationsActivity extends BaseActivity {

    private NotificationManagerCompat mNotificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentLayout.addView(View.inflate(this, R.layout.activity_wearable_notifications, null));
        ButterKnife.bind(this);

        mNotificationManager = NotificationManagerCompat.from(this);

    }

    @Override
    protected int getNavigationItemIndex() {
        return 0;
    }

    @Nullable
    @OnClick(R.id.btnSimpleNotification)
    void triggerSimpleNotification(View v) {
        int notificationId = 001;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationInteractionActivity.class);

//        viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("testing bitch")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(notificationId, notificationBuilder.build());

//        Snackbar.make(mContentLayout, "Hello. I am Snackbar!", Snackbar.LENGTH_SHORT)
//                .show();
    }

    @Nullable
    @OnClick(R.id.btnActionButtonNotification)
    void triggerActionButtonNotification(View v) {
        int notificationId = 002;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);


    }

    @Nullable
    @OnClick(R.id.btnWearableActionsNotification)
    void triggerWearableActionNotification(View v) {
        int notificationId = 003;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);


    }

    @Nullable
    @OnClick(R.id.btnBigViewNotification)
    void triggerBigViewNotification(View v) {
        int notificationId = 004;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);


    }

    @Nullable
    @OnClick(R.id.btnWearableFeatureNotification)
    void triggerWearableFeatureNotification(View v) {
        int notificationId = 001;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);


    }
}
