package com.jinkegroup.upgradelib.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class AlarmService {
    private static final String ALARM_ACTION_PREFIX = "mobi.suishi.netassist.ALARM_ACTION";

    private Context mContext;
    private Handler mHandler;
    private AlarmCallback mCallback;

    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private BroadcastReceiver mBroadcastReceiver;

    public AlarmService(Context context, Handler handler) {
        mContext = context;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mHandler = handler;
        mPendingIntent = getPendingIntent();
    }


    public void set(long after, final AlarmCallback callback) {
        long alarmTime = SystemClock.elapsedRealtime() + after;
        registerBroadcastReceiver();
        mCallback = callback;
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME, alarmTime, mPendingIntent);
    }


    public void cancel() {
        mAlarmManager.cancel(mPendingIntent);
        unregisterBroadcastReceiver();
        mCallback = null;
    }


    protected void handleAlarmReceived() {
        unregisterBroadcastReceiver();
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.alarmTriggered();
                }
            }
        });
    }


    private void registerBroadcastReceiver() {
        unregisterBroadcastReceiver();
        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                handleAlarmReceived();
            }
        };
        mContext.registerReceiver(mBroadcastReceiver, new IntentFilter(ALARM_ACTION_PREFIX + this));
    }

    private void unregisterBroadcastReceiver() {
        if (mBroadcastReceiver != null) {
            mContext.unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(ALARM_ACTION_PREFIX + this);
        return PendingIntent.getBroadcast(mContext, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
