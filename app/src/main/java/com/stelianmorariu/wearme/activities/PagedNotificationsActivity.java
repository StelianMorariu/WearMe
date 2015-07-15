/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.app.Notification;
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

public class PagedNotificationsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentLayout.addView(View.inflate(this, R.layout.activity_paged_notifications, null));
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
        return 2;
    }

    @Nullable
    @OnClick(R.id.btnPagedNotification)
    void triggerPagedNotification(View v) {
        int notificationId = 1;
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        // Build intent for notification content
        Intent viewIntent = new Intent(this, VoiceNotificationsActivity.class);
        viewIntent.putExtra(NOTIFICATION_TYPE, Constants.NOTIFICATION_PAGED);
        viewIntent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                        .setContentTitle("test ")
                        .setContentText("paged notification")
                        .setContentIntent(viewPendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .extend(new NotificationCompat.WearableExtender()
                                .addPage(getBigViewNotification())
                                .addPage(getwearableFeatureNotification()));

        mNotificationManager.notify(notificationId, notificationBuilder.build());

    }

    private Notification getwearableFeatureNotification() {
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(decodeSampledBitmapFromResource(getResources(), R.drawable.header, 400, 400));

        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
                .setContentTitle("test ")
                .setContentText("wearable features")
                .extend(wearableExtender)
                .build();

    }

    private Notification getBigViewNotification() {
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText("THis is just a bunch of text. I only want to see how long text is displayed on a wearable device.");

        return new NotificationCompat.Builder(this)
                .setContentTitle("test ")
                .setContentText("big view")
                .setStyle(bigStyle).build();

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
