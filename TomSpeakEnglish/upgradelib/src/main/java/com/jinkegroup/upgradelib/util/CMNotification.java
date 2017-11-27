package com.jinkegroup.upgradelib.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.jinkegroup.upgradelib.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class CMNotification {

    private final DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.icon)
            .showImageForEmptyUri(R.drawable.icon)
            .showImageOnFail(R.drawable.icon)
            .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    private int mID;
    private NotificationCompat.Builder mBuilder;
    private Context mContext;
    private String mLargeIconUrl;

    public CMNotification(Context context) {
        mContext = context;
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setAutoCancel(true)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(context.getString(R.string.app_name))
                .setTicker(context.getString(R.string.notification_hint))
                .setWhen(System.currentTimeMillis());
    }


    public CMNotification setID(int ID) {
        mID = ID;
        return this;
    }

    public int getID() {
        return mID;
    }

    public CMNotification setIcon(int icon) {
        if (icon != -1) {
            mBuilder.setSmallIcon(icon);
        }
        return this;
    }

    private CMNotification setLargeIcon(Bitmap icon) {
        mBuilder.setLargeIcon(icon);
        //override mLargeIconUrl
        mLargeIconUrl = null;
        return this;
    }

    public CMNotification setLargeIcon(String icon) {
        if (icon != null) {

            if (icon.startsWith("http://") || icon.startsWith("https://")) {
                return setLargeIconUrl(icon);
            }

            int iicon = 0;
            try {
                iicon = mContext.getResources().getIdentifier(icon, "drawable", mContext.getPackageName());
            } catch (Exception e) {

            }
            if (iicon != 0) {
                setLargeIcon(Utils.drawableToBitmap(mContext.getResources().getDrawable(iicon)));
            }
        }
        return this;
    }


    /*
     * set url for large icon
     * */
    public CMNotification setLargeIconUrl(String url) {
        mLargeIconUrl = url;
        return this;
    }

    public CMNotification setIcon(String icon) {
        if (icon != null) {
            int iicon = 0;
            try {
                iicon = mContext.getResources().getIdentifier(icon, "drawable", mContext.getPackageName());
            } catch (Exception e) {

            }
            if (iicon != 0) {
                mBuilder.setSmallIcon(iicon);
            }
        }
        return this;
    }


    public CMNotification setTickerText(String tickerText) {
        if (tickerText != null) {
            mBuilder.setTicker(tickerText);
        }
        return this;
    }

    public CMNotification setContentTitle(String title) {
        if (title != null) {
            mBuilder.setContentTitle(title);
        }
        return this;
    }

    public CMNotification setContentText(String contentText) {
        if (contentText != null) {
            mBuilder.setContentText(contentText);
        }
        return this;
    }


    public CMNotification setPendingIntent(PendingIntent pendingIntent) {
        mBuilder.setContentIntent(pendingIntent);
        return this;
    }

    public void cancel() {
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(mID);
    }

    public static void cancel(int notifyId, Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notifyId);
    }

    public void notify(int progress, int max, boolean indeterminate) {
        mBuilder.setProgress(max, progress, indeterminate);

        if (mLargeIconUrl != null) {
            ImageLoader.getInstance().loadImage(mLargeIconUrl, mOptions, new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view,
                                            FailReason failReason) {
                    NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(mID, mBuilder.build());
                }

                @Override
                public void onLoadingComplete(String imageUri, View view,
                                              Bitmap loadedImage) {
                    NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                    setLargeIcon(loadedImage);
                    mNotificationManager.notify(mID, mBuilder.build());
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }

            });
        }
        else {
            NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(mID, mBuilder.build());
        }
    }


    public PendingIntent createInstallIntent(Uri fileLocation) {
        PendingIntent localPendingIntent;
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setDataAndType(fileLocation, "application/vnd.android.package-archive");
        localPendingIntent = PendingIntent.getActivity(mContext, 0, localIntent, 0);
        return localPendingIntent;
    }

}
