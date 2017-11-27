package com.jinkegroup.upgradelib.downloadservice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.jinkegroup.upgradelib.util.Constant;
import com.jinkegroup.upgradelib.util.MapQuery;
import com.jinkegroup.upgradelib.util.QueryParameter;
import com.jinkegroup.upgradelib.util.SystemFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class DownloaderProvider {
    public static String DOWNLOAD_DIR = "download";

    private Context mContext;
    private RandomAccessFile mWritingFile = null;

    private String mUrl;
    private String mFileName;
    private String mTempFileName;
    private String mEtagFileName;
    private Bundle mParameters;


    public DownloaderProvider(Context context, String url, String fileName, Bundle parameters) {
        mContext = context;
        mUrl = url;
        mFileName = fileName;
        mTempFileName = mFileName + ".tmp";
        mEtagFileName = mFileName + ".etag";
        mParameters = parameters;
    }


    public static File getDownloadDirectory(Context context) {
        File root = SystemFile.getApplicationRootFolder(context);

        File folder = new File(root, Constant.UPGRADE_DIRECTORY);
        if (folder.isFile()) {
            folder.delete();
        }
        if (!folder.exists()) {
            folder.mkdir();
        }

        return folder;
    }


    public static File getFile(Context context, String fileName, boolean create) {
        //File tempFile = new File(context.getDir(DOWNLOAD_DIR, Context.MODE_PRIVATE), fileName);
        File tempFile = new File(getDownloadDirectory(context), fileName);
        if (tempFile.isDirectory()) {
            SystemFile.deleteDirectory(tempFile);
        }
        if (create && !tempFile.exists()) {
            try {
                tempFile.createNewFile();
            } catch (IOException e) {

            }
        }
        return tempFile;
    }


    public String getUrl() {
        return mUrl;
    }

    public String getEtag() {
        Scanner scanner = null;
        String content = "";
        try {
            scanner = new Scanner(getFile(mContext, mEtagFileName, false));
            content = scanner.useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            //
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return content;
    }


    public void setEtag(String etag) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(deleteFile(mEtagFileName));
            out.println(etag);
        } catch (FileNotFoundException e) {

        } finally {
            if (out != null) {
                out.close();
            }
        }


    }


    public long getRange() {
        return getFile(mContext, mTempFileName, false).length();
    }


    public String getQuery() {
        return MapQuery.urlEncodeUTF8(constructParameterMap());
    }


    public void writeData(byte[] buffer, int len) throws Exception{
        try {
            if (mWritingFile == null) {
                File tempFile = getFile(mContext, mTempFileName, true);
                mWritingFile = new RandomAccessFile(tempFile, "rw");
                mWritingFile.seek(tempFile.length());
            }

            mWritingFile.write(buffer, 0, len);
        } catch (Exception e) {
            throw e;
        }
    }


    private void closeWritingFile() {
        if (mWritingFile != null) {
            try {
                mWritingFile.close();
            } catch (Exception e) {

            }
            mWritingFile = null;
        }
    }


    private File deleteFile(String fileName) {
        File tempFile = getFile(mContext, fileName, false);
        if (tempFile.exists()) {
            if (tempFile.isFile()) {
                tempFile.delete();
            } else if (tempFile.isDirectory()) {
                SystemFile.deleteDirectory(tempFile);
            }
        }
        return tempFile;
    }


    public void clearData() {
        closeWritingFile();
        deleteFile(mTempFileName);
        deleteFile(mEtagFileName);
    }


    public void finishData() {
        closeWritingFile();
        File tempFile = getFile(mContext, mTempFileName, false);
        if (tempFile.exists()) {
            tempFile.renameTo(deleteFile(mFileName));
        }
        deleteFile(mEtagFileName);
    }


    public void stopData() {
        closeWritingFile();
    }


    public Uri getFileLocation() {
        File file = getFile(mContext, mFileName, false);
        if (file.exists()) {
            return Uri.fromFile(file);
        }

        return Uri.EMPTY;
    }


    private Map<String, String> constructParameterMap() {

        Map<String, String> parameters = QueryParameter.getFullParameters();

        if (mParameters != null) {
            for (String key : mParameters.keySet())
            {
                parameters.put(key, mParameters.getString(key));
            }
        }

        return parameters;
    }
}
