package com.jinkegroup.upgradelib;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public abstract class JobCheckService extends IntentService {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String ACTION_JOB_FINISHED = "jinkegroup.update.FINISHED";
    public static final String ACTION_JOB_FORCE_RUN = "jinkegroup.update.FORCE_RUN";


    public JobCheckService(String name) {
        super(name);
    }


    /**
     *
     * @param intent
     * @return true if the intent to be handled, false if the intent to be ignored.
     */
    protected boolean intentCheck(Intent intent) {
        //overrided in subclass...
        return true;
    }

    protected abstract String getCheckAction();
    protected abstract String getLastJobTimeKey();
    protected abstract String getPendingKey();
    protected abstract long getNextRunTime(long lastCheckTime);
    protected abstract long getMinimalJobInterval();
    protected abstract void runJob();


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intentCheck(intent)) {
            String action = intent.getAction();
            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                onCheckTriggered();
            }
            else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                onConnectivityEvent(intent);
            }
            else if (getCheckAction().equals(action)) {
                onCheckTriggered();
            }
            else if (ACTION_JOB_FINISHED.equals(action)) {
                onJobFinished(intent);
            }
            else if (ACTION_JOB_FORCE_RUN.equals(action)) {
                onForceRun(intent);
            }
            else {

            }
        }
    }

    private void onForceRun(Intent intent) {
        onCheckTriggered();
    }

    private void onJobFinished(Intent intent) {
        scheduleNextCheck(getNextRunTime(Calendar.getInstance().getTimeInMillis()));
    }

    private void onCheckTriggered() {
        if (hasNetworkConnectivity()) {
            doCheck(this);
        } else {
            setCheckPending(true);
        }
    }

    private void doCheck(Context context) {

        setCheckPending(false);


        long currentTime = Calendar.getInstance().getTimeInMillis();
        long lastCheckTime = loadLastJobTime();

        if (currentTime - lastCheckTime > getMinimalJobInterval()) {
            runJob();
        } else {
            scheduleNextCheck(getNextRunTime(lastCheckTime));
        }

    }


    private void scheduleNextCheck(long triggerAtMillis) {

        AlarmManager mgr = (AlarmManager)getBaseContext()
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getCheckAction());
        PendingIntent operation = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        mgr.cancel(operation);
        mgr.set(AlarmManager.RTC, triggerAtMillis, operation);
    }


    private boolean hasNetworkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    private void onConnectivityEvent(Intent intent) {
        if (hasNetworkConnectivity()) {
            if (isCheckPending()) {
                doCheck(this);
            }
        }
    }

    public long loadLastJobTime() {
        return getPreferences().getLong(getLastJobTimeKey(), 0);
    }

    public boolean saveLastPushCheckTime(long checkTime) {
        return getPreferences().edit().putLong(getLastJobTimeKey(), checkTime)
                .commit();
    }

    private boolean isCheckPending() {
        // set default as "true", for first time or data cleared, to check
        return getPreferences().getBoolean(getPendingKey(), true);
    }

    private void setCheckPending(boolean flag) {
        getPreferences().edit().putBoolean(getPendingKey(), flag)
                .commit();
    }


    private SharedPreferences getPreferences() {
        return getApplicationContext().getSharedPreferences(
                "job_check", Context.MODE_PRIVATE);
    }
}
