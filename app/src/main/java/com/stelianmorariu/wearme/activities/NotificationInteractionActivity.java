/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationInteractionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        final int intExtra = getIntent().getIntExtra(WearNotificationsActivity.NOTIFICATION_ID, 0);
        managerCompat.cancel(intExtra);

        final String extra = getIntent().getStringExtra(WearNotificationsActivity.NOTIFICATION_TYPE);
        if (extra != null) {
            Snackbar.make(mContentLayout, extra, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected int getNavigationItemIndex() {
        return 0;
    }


}
