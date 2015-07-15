/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class SimpleNotificationsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentLayout.addView(View.inflate(this, R.layout.activity_simple_notifications, null));
        ButterKnife.bind(this);

        mNotificationManager = NotificationManagerCompat.from(this);

        final int intExtra = getIntent().getIntExtra(SimpleNotificationsActivity.NOTIFICATION_ID, 0);
        mNotificationManager.cancel(intExtra);

        final String extra = getIntent().getStringExtra(SimpleNotificationsActivity.NOTIFICATION_TYPE);
        if (extra != null) {
            Snackbar.make(mContentLayout, extra, Snackbar.LENGTH_LONG)
                    .show();
        }
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
        Intent viewIntent = new Intent(this, SimpleNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_SIMPLE);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("simple notification")
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
        Intent viewIntent = new Intent(this, SimpleNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_SIMPLE);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        // Build intent for notification content
        Intent actionIntent = new Intent(this, SimpleNotificationsActivity.class);
        actionIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_ACTION_BUTTON);
        actionIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("action button")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .addAction(R.drawable.abc_btn_rating_star_off_mtrl_alpha, "Action Button",
                                PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    @Nullable
    @OnClick(R.id.btnWearableActionsNotification)
    void triggerWearableActionNotification(View v) {
        int notificationId = 3;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, SimpleNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_SIMPLE);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        // Build intent for notification content
        Intent actionIntent = new Intent(this, SimpleNotificationsActivity.class);
        actionIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_ACTION_BUTTON);
        actionIntent.putExtra(NOTIFICATION_ID, notificationId);

        // Build intent for notification content
        Intent wearableAction = new Intent(this, SimpleNotificationsActivity.class);
        wearableAction.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_WEARABLE_ACTION);
        wearableAction.putExtra(NOTIFICATION_ID, notificationId);

        NotificationCompat.Action wearAction = new NotificationCompat.Action(
                R.drawable.abc_ic_go_search_api_mtrl_alpha, "wear only",
                PendingIntent.getActivity(this, 0, wearableAction, PendingIntent.FLAG_UPDATE_CURRENT));

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("wearable action")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .addAction(R.drawable.abc_btn_rating_star_off_mtrl_alpha, "Action Button",
                                PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .extend(new NotificationCompat.WearableExtender().addAction(wearAction));

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    @Nullable
    @OnClick(R.id.btnBigViewNotification)
    void triggerBigViewNotification(View v) {
        int notificationId = 4;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText("THis is just a bunch of text. I only want to see how long text is displayed on a wearable device.");

        // Build intent for notification content
        Intent viewIntent = new Intent(this, SimpleNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_BIG_VIEW);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("big view notification")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setStyle(bigStyle);

        mNotificationManager.notify(notificationId, notificationBuilder.build());
    }

    @Nullable
    @OnClick(R.id.btnWearableFeatureNotification)
    void triggerWearableFeatureNotification(View v) {
        int notificationId = 5;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, SimpleNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_WEARABLE_FEATURE);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(decodeSampledBitmapFromResource(getResources(), R.drawable.header, 400, 400));

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("wearable features")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .extend(wearableExtender);

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    private Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                   int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
