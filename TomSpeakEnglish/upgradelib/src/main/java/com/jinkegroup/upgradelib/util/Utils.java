package com.jinkegroup.upgradelib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.HandlerThread;

import com.jinkegroup.upgradelib.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class Utils {
    public Utils() {
        // TODO Auto-generated constructor stub
    }

    public static HandlerThread getHandlerThread(String handlerName) {
        HandlerThread handlerThread = new HandlerThread(handlerName);
        handlerThread.start();
        while (!handlerThread.isAlive()) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {

            }
        }
        return handlerThread;
    }

    public static String bytetoString(byte[] digest) {
        String str = "";
        String tempStr = "";

        for (int i = 1; i < digest.length; i++) {
            tempStr = (Integer.toHexString(digest[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            }
            else {
                str = str + tempStr;
            }
        }
        return str.toLowerCase();
    }

    public static String readUTF16BE(DataInputStream dataStream, int len)
            throws IOException {
        byte[] temp = new byte[len];
        dataStream.readFully(temp, 0, len);
        return new String(temp, Charset.forName("UTF-16BE"));
    }

    public static HashMap<String, String> getProps(String propStr) {
        HashMap<String, String> props = new HashMap<String, String>();
        if (propStr == null || propStr.length() <= 0) {
            return props;
        }

        try {
            JSONObject json = new JSONObject(propStr);
            Iterator<String> keys = json.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                props.put(key, json.getString(key));
            }
        } catch (JSONException e) {
			/*if (Logger.isError()) {
				mLogger.error("Can't get props:", e);
			}*/
        }

        return props;
    }

    public static int currentTimeMinutes() {
        long misec = System.currentTimeMillis();
        long minutes = misec / 60000;
        return (int)minutes;
    }

    public static int getIntFromString(String str, int defValue) {
        if (str == null)
            return defValue;

        int retValue = defValue;
        try {
            retValue = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            retValue = defValue;
        }

        return retValue;
    }


    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (URISyntaxException e) {
            return "unknown";
        }
    }

    public static String getFriedlyTime(Context context, long epochSecond) {
        long now = System.currentTimeMillis() / 1000;
        long diff = now - epochSecond;

        if (diff <= 84600) {
            //今天
            return context.getString(R.string.friendly_time_today);
        } else if (diff <= 2592000){
            //x天前
            return String.format(context.getString(R.string.friendly_time_xday), diff / 84600);
        } else if (diff <= 31536000) {
            //x月前
            return String.format(context.getString(R.string.friendly_time_xmonth), diff / 2592000);
        } else {
            //x年前
            return String.format(context.getString(R.string.friendly_time_xyear), diff / 31536000);
        }
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
