/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.HapticFeedbackConstants;
import android.view.View;

import com.stelianmorariu.wearme.Constants;
import com.stelianmorariu.wearme.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stelian Morariu on 7/7/2015.
 */
public class WearNotificationsActivity extends BaseActivity {

    public static final String NOTIFICATION_TYPE = "com.stelianmorariu.wearme.NOTIFICATION_TYPE";
    public static final String NOTIFICATION_ID = "com.stelianmorariu.wearme.NOTIFICATION_ID";

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
        int notificationId = 1;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationInteractionActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_SIMPLE);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent,PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("testing bitch")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    @Nullable
    @OnClick(R.id.btnActionButtonNotification)
    void triggerActionButtonNotification(View v) {
        int notificationId = 2;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationInteractionActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_SIMPLE);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        // Build intent for notification content
        Intent actionIntent = new Intent(this, NotificationInteractionActivity.class);
        actionIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_ACTION_BUTTON);
        actionIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("testing bitch")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .addAction(R.drawable.abc_btn_rating_star_off_mtrl_alpha, "Action Button",
                                PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT ));

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    @Nullable
    @OnClick(R.id.btnWearableActionsNotification)
    void triggerWearableActionNotification(View v) {
        int notificationId = 3;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, NotificationInteractionActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_SIMPLE);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        // Build intent for notification content
        Intent actionIntent = new Intent(this, NotificationInteractionActivity.class);
        actionIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_ACTION_BUTTON);
        actionIntent.putExtra(NOTIFICATION_ID, notificationId);

        // Build intent for notification content
        Intent wearableAction = new Intent(this, NotificationInteractionActivity.class);
        wearableAction.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_WEARABLE_ACTION);
        wearableAction.putExtra(NOTIFICATION_ID, notificationId);

        NotificationCompat.Action wearAction = new NotificationCompat.Action(
                R.drawable.abc_ic_go_search_api_mtrl_alpha, "wear only",
                PendingIntent.getActivity(this, 0, wearableAction, 0));

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("testing bitch")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .addAction(R.drawable.abc_btn_rating_star_off_mtrl_alpha, "Action Button",
                                PendingIntent.getActivity(this, 0, actionIntent,PendingIntent.FLAG_UPDATE_CURRENT ))
                        .extend(new NotificationCompat.WearableExtender().addAction(wearAction));

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    @Nullable
    @OnClick(R.id.btnBigViewNotification)
    void triggerBigViewNotification(View v) {
        int notificationId = 4;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);


    }

    @Nullable
    @OnClick(R.id.btnWearableFeatureNotification)
    void triggerWearableFeatureNotification(View v) {
        int notificationId = 5;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);


    }
}
