package com.jinkegroup.upgradelib.downloadservice;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class ResumbleDownloader {
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int WAIT_TIMEOUT = 30000;
    private static final int BUFFER_SIZE = 4096;

    private DownloaderProvider mProvider;
    private DownloaderListener mListener;


    public ResumbleDownloader(DownloaderProvider provider) {
        mProvider = provider;
    }

    public void setListener(DownloaderListener listener) {
        mListener = listener;
    }

    public void download() {

        HttpURLConnection conn = null;
        try {
            String etag = mProvider.getEtag();
            long range = mProvider.getRange();
            String query = mProvider.getQuery();

            URL url = new URL(mProvider.getUrl() + "?" + query);
            conn = (HttpURLConnection) url.openConnection();

            if ((etag.length() != 0) && (range != 0)) {
                //this is committed with server side, not change!!!!!
                conn.addRequestProperty("If-Range", etag);
                conn.addRequestProperty("Range", "bytes=" + range + "-");
            }


        //    HttpParams httpParameters = new BasicHttpParams();
        //    HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECT_TIMEOUT);
        //    HttpConnectionParams.setSoTimeout(httpParameters, WAIT_TIMEOUT);

            int code = conn.getResponseCode();

            if (code != 200) {

                mListener.downloadFailed();
                return;
            }

            int length = conn.getContentLength();


            if (length == 0) {

                mListener.downloadCompleted(mProvider.getFileLocation());
                return;
            }

            String contentType = conn.getContentType();
            if (!"application/vnd.android.package-archive".equals(contentType)) {

                mListener.downloadFailed();
                return;
            }


            String newEtag = conn.getHeaderField("Etag");
            newEtag = (newEtag == null ? "" : newEtag);

            long contentStart = 0;
            String contentRange = conn.getHeaderField("Content-Range");
            if ((contentRange != null) && (contentRange.length() != 0)) {
                int index2 = contentRange.indexOf('-');
                if (index2 >= 0) {
                    int index1;
                    for (index1 = 0; index1 < index2; index1++) {
                        if (contentRange.charAt(index1) >= '0'
                                && contentRange.charAt(index1) <= '9')
                            break;
                    }
                    contentStart = Long.parseLong(contentRange.substring(index1, index2));
                }
            }

            if (contentStart == 0) {
                mProvider.clearData();
                mProvider.setEtag(newEtag);
                saveData(conn, 0, length);
            } else if (contentStart == range
                    && etag.equals(newEtag)){
                saveData(conn, (int)range, length);
            } else {

                mProvider.clearData();
                mListener.downloadFailed();
                return;
            }

        } catch (Exception e) {

            mListener.downloadFailed();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


    private void saveData(URLConnection conn, int offset, int len) {
        BufferedInputStream bis = null;
        boolean finished = false;
        long lastNotifyTime = System.currentTimeMillis();
        try {
            bis = new BufferedInputStream(conn.getInputStream());
            int read = 0, totalRead = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                read = bis.read(buffer);
                if (read > 0) {
                    mProvider.writeData(buffer, read);
                    totalRead += read;
                    if (totalRead == len) {
                        finished = true;
                        break;
                    }
                } else if (read < 0) {
                    break;
                }
                if (System.currentTimeMillis() - lastNotifyTime > 1000) {
                    //to avoid too many notification
                    mListener.downloadProgress(totalRead + offset, len + offset);
                    lastNotifyTime = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                bis.close();
            } catch (Exception e) {

            }
        }

        if (finished) {
            mProvider.finishData();
            mListener.downloadCompleted(mProvider.getFileLocation());
        } else {
            mProvider.stopData();
            mListener.downloadFailed();
        }
    }
}
