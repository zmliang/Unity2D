package com.jinkegroup.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Xml;

import com.jinkegroup.listener.UpdateListener;
import com.jinkegroup.model.UpdateModel;
import com.jinkegroup.task.CheckUpdateAsyncTask;
import com.jinkegroup.task.DownloadAsyncTask;
import com.jinkegroup.utils.AndroidUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/31
 * CopyRight:  JinkeGroup
 */

public class UpdateService extends Service {
    private AsyncTask<String, Integer, String> fileDownlaodTask;
    private UpdateListener updateListener;
    private long lastUpdateTime;
    private String hostUpdateCheckUrl;

    @Override
    public IBinder onBind(Intent intent) {
        //启动检查更新异步任务
        this.hostUpdateCheckUrl = intent.getStringExtra("update_check_url");
        new CheckUpdateAsyncTask(this).execute(this.hostUpdateCheckUrl);
        return new MsgBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fileDownlaodTask!=null) {
            fileDownlaodTask.cancel(true);
        }

    }

    public void startDownLoadTask(String fileUrl, String downloadPath, String downloadFileName) {
        //启动下载任务
        fileDownlaodTask = new DownloadAsyncTask(this).execute(fileUrl, downloadPath, downloadFileName);
    }


    /**
     * 注册检查更新回调接口
     */
    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void checkUpdateResult(String hostVersionInfo) {
        if (hostVersionInfo != null && hostVersionInfo.length() != 0 && !hostVersionInfo.equals("timeout")) {
            UpdateModel appUpdateInfo = parseJson(hostVersionInfo);
            if (AndroidUtil.getAppVersionCode(this) < appUpdateInfo.getVersionCode()) {
                this.updateListener.onFoundNewVersion(appUpdateInfo);
            } else {
                this.updateListener.onNoFoundNewVersion();
            }
        } else {
            this.updateListener.onCheckError("check update error");
        }

    }

    /**
     * 更新ProgressDialog或Notification进度
     * @param values
     */
    public void updateProgress(Integer... values) {
        if(values[0]==-100) {
            updateListener.onDownloadFinish();
        } else if (values[0]==-1) {
            updateListener.onDownloadError("download error");
        } else if (System.currentTimeMillis()-lastUpdateTime>100) {
            updateListener.onDownloading(values);
            lastUpdateTime = System.currentTimeMillis();
        }
    }



    /**
     * 解析检查更新xml文件字符串为Map对象
     * @param xmlString
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public Map<String, Object> parseXML(String xmlString) throws XmlPullParserException, IOException {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        List<String> contentArray = new ArrayList<String>();

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(xmlString));
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if ("update".equals(parser.getName())) {
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            hashMap.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                        }
                    } else if ("content".equals(parser.getName())) {
                        eventType = parser.next();
                    } else if ("item".equals(parser.getName())) {
                        eventType = parser.next();
                        contentArray.add(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }
        hashMap.put("updateContent", contentArray);
        return hashMap;
    }


    public UpdateModel parseJson(String jsonStr) {
        UpdateModel appInfo = new UpdateModel();
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            appInfo.setVersionCode(jsonObj.getInt("versionCode"));
            appInfo.setVersionName(jsonObj.getString("versionName"));
            appInfo.setFileSize(jsonObj.getString("fileSize"));
            appInfo.setApkUrl(jsonObj.getString("apkUrl"));
            appInfo.setRequired(jsonObj.getBoolean("required"));
            appInfo.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObj.getString("releaseDate")));
            List<String> relaseNoteList = new ArrayList<>();
            JSONArray jsonArr = jsonObj.getJSONArray("releaseNotes");
            for (int i=0; i<jsonArr.length(); i++) {
                relaseNoteList.add(jsonArr.getString(i));
            }
            appInfo.setReleaseNoteList(relaseNoteList);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return appInfo;
    }







    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public UpdateService getService(){
            return UpdateService.this;
        }
    }
}
