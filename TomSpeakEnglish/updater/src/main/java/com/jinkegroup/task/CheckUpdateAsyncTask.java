package com.jinkegroup.task;

import android.os.AsyncTask;

import com.jinkegroup.service.UpdateService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/31
 * CopyRight:  JinkeGroup
 */

public class CheckUpdateAsyncTask extends AsyncTask<String,Integer,String> {
    private UpdateService service;

    public CheckUpdateAsyncTask(UpdateService s){
        this.service = s;
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... params){
        String dataStr = null;
        try {
            dataStr = httpGetRequest(params[0]);
        }catch (Exception e){
            return "timeout";
        }
        return dataStr;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result){
        this.service.checkUpdateResult(result);
    }

    public String httpGetRequest(String urlParam) throws IOException{
        String result ="";

        try {
            URL url = new URL(urlParam);
            HttpURLConnection conn;
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String lineStr = null;
                while ((lineStr = bufReader.readLine())!=null)
                    result+=lineStr;
                bufReader.close();
                conn.disconnect();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
