/*
 * Copyright (c) 2015. Stelian Morariu
 *
 */

package com.stelianmorariu.wearme.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.HapticFeedbackConstants;
import android.view.View;

import com.stelianmorariu.wearme.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stelian Morariu on 7/7/2015.
 */
public class WearNotificationsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentLayout.addView(View.inflate(this, R.layout.activity_wearable_notifications, null));
        ButterKnife.bind(this);
    }

    @Nullable
    @OnClick(R.id.btnSimpleNotification)
    void triggerSimpleNotification(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        Snackbar.make(mContentLayout, "Hello. I am Snackbar!", Snackbar.LENGTH_SHORT)
                .show();
    }
}
