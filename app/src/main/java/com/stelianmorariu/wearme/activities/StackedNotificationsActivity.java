/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.app.Notification;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StackedNotificationsActivity extends BaseActivity {

    public static final String GROUP_NOTIFICATIONS_KEY = "com.stelianmorariu.wearme.GROUP_NOTIFICATIONS_KEY";
    private List<SampleNotification> mNotifications = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentLayout.addView(View.inflate(this, R.layout.activity_stacked_notifications, null));
        ButterKnife.bind(this);

        mNotificationManager = NotificationManagerCompat.from(this);

        final int intExtra = getIntent().getIntExtra(VoiceNotificationsActivity.NOTIFICATION_ID, 0);
        mNotificationManager.cancel(intExtra);

        final String extra = getIntent().getStringExtra(VoiceNotificationsActivity.NOTIFICATION_TYPE);
        if (extra != null) {
            Snackbar.make(mContentLayout, extra, Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    protected int getNavigationItemIndex() {
        return 3;
    }

    @Nullable
    @OnClick(R.id.btnStackedNotification)
    void triggerStackedNotification(View v) {
        int notificationId = mNotifications.size() + 1;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        if (mNotifications.isEmpty()) {
            SampleNotification sampleNotification = new SampleNotification();

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.abc_ic_menu_selectall_mtrl_alpha)
                            .setContentTitle("New sample notification ")
                            .setContentText(sampleNotification.getMessage())
                            .setGroup(GROUP_NOTIFICATIONS_KEY)
                            .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(notificationId, notificationBuilder.build());
            mNotifications.add(sampleNotification);
        } else {
            SampleNotification sampleNotification = new SampleNotification();

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.abc_ic_menu_selectall_mtrl_alpha)
                            .setContentTitle("New sample notification ")
                            .setContentText(sampleNotification.getMessage())
                            .setGroup(GROUP_NOTIFICATIONS_KEY)
                            .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(notificationId, notificationBuilder.build());
            mNotifications.add(sampleNotification);

            updateSummaryNotification(mNotificationManager);
        }

    }

    private void updateSummaryNotification(NotificationManagerCompat notificationManager) {
        // Create an InboxStyle notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (SampleNotification n : mNotifications) {
            inboxStyle.addLine(n.getMessage());
        }
        inboxStyle.setBigContentTitle(String.format("%d new sample notifications", mNotifications.size()))
                .setSummaryText("testing");

        Notification summaryNotification = new NotificationCompat.Builder(this)
                .setContentTitle(String.format("%d new sample notifications", mNotifications.size()))
                .setSmallIcon(android.R.drawable.sym_action_email)
                .setStyle(inboxStyle)
                .setGroup(GROUP_NOTIFICATIONS_KEY)
                .setGroupSummary(true)
                .build();

        notificationManager.notify(6876876, summaryNotification);
    }

    class SampleNotification {
        String message;

        public SampleNotification() {
            this.message = getString(R.string.sample_notification_message, getFormatedDate());
        }

        public String getMessage() {
            return message;
        }

        private String getFormatedDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(new Date(System.currentTimeMillis()));
        }
    }
}
