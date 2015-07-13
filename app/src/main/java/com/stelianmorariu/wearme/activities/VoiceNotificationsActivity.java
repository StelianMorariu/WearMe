/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.HapticFeedbackConstants;
import android.view.View;

import com.stelianmorariu.wearme.Constants;
import com.stelianmorariu.wearme.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stelian Morariu on 7/7/2015.
 */
public class VoiceNotificationsActivity extends BaseActivity {

    public static final String NOTIFICATION_TYPE = "com.stelianmorariu.wearme.NOTIFICATION_TYPE";
    public static final String NOTIFICATION_ID = "com.stelianmorariu.wearme.NOTIFICATION_ID";
    public static final String EXTRA_VOICE_REPLY = "com.stelianmorariu.wearme.EXTRA_VOICE_REPLY";

    private NotificationManagerCompat mNotificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentLayout.addView(View.inflate(this, R.layout.activity_voice_notifications, null));
        ButterKnife.bind(this);

        mNotificationManager = NotificationManagerCompat.from(this);

        final int intExtra = getIntent().getIntExtra(VoiceNotificationsActivity.NOTIFICATION_ID, 0);
        mNotificationManager.cancel(intExtra);

        final String extra = getIntent().getStringExtra(VoiceNotificationsActivity.NOTIFICATION_TYPE);
        if (extra != null) {
            Snackbar.make(mContentLayout, extra, Snackbar.LENGTH_LONG)
                    .show();
        }

        final CharSequence messageText = getMessageText(getIntent());
        if (messageText != null) {
            Snackbar.make(mContentLayout, messageText, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected int getNavigationItemIndex() {
        return 1;
    }

    @Nullable
    @OnClick(R.id.btnVoiceOnlyNotification)
    void triggerSimpleNotification(View v) {
        int notificationId = 1;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, VoiceNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_VOICE_INTERACTION);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput input = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel("Answer me")
                .build();

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.abc_ic_commit_search_api_mtrl_alpha,
                        "Answer", viewPendingIntent)
                        .addRemoteInput(input)
                        .build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("voice interaction")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    @Nullable
    @OnClick(R.id.btnVoiceAndTextNotification)
    void triggerActionButtonNotification(View v) {
        int notificationId = 2;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, VoiceNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_VOICE_TEXT_INTERACTION);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput input = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel("Answer me")
                .setChoices(getResources().getStringArray(R.array.reply_choices))
                .build();

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.abc_ic_menu_selectall_mtrl_alpha,
                        "Answer", viewPendingIntent)
                        .addRemoteInput(input)
                        .build();

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("voice and text interaction")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }


    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
        }
        return null;
    }
}
