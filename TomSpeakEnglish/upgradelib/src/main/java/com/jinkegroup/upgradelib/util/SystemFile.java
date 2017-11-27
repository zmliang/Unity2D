package com.jinkegroup.upgradelib.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class SystemFile {
    public static boolean fileExists(String fileName) {
        return false;
    }

    /**
     * Get the application folder on the SD Card. Create it if not present.
     *
     * @return The application folder.
     */
    public static File getApplicationFolder(Context context, String appSpecName) {
        File root = Environment.getExternalStorageDirectory(); //context.getFilesDir();//
        if (root.canWrite()) {

            File folder = new File(root, appSpecName);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            return folder;

        } else {
            return null;
        }
    }

    public static File getApplicationRootFolder(Context context) {
        return SystemFile.getApplicationFolder(context, Constant.APP_BASE_DIRECTORY);
    }


    public static void deleteDirectory(File dir) {
        File[] fileList = dir.listFiles();

        for (File file : fileList) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteDirectory(file);
            }
        }
    }

    public static byte[] readTailFromRawSource(Context context, int id, int size) {
        InputStream is = context.getResources().openRawResource(id);
        byte[] pixels = new byte[size];
        try {
            if (is.available() > size) {
                int offset = is.available() - size;
                is.skip(offset);
                is.read(pixels, 0, size);
            }
            is.close();
            is = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        return pixels;
    }


    public static void saveDataToFile(ByteBuffer groupData, File file) throws Exception {
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = groupData.array();
        fos.write(buffer, 0,buffer.length);
        fos.close();
    }


    public static ByteBuffer readDataFromFile(File file) throws Exception {
        byte[] data = new byte[1024];
        FileInputStream fis = new FileInputStream(file);
        int readSize = 0;
        ByteBuffer bufData = ByteBuffer.allocate(1024*1024);
        while ((readSize = fis.read(data, 0, 1024)) != -1) {
            bufData.put(data);
        }

        fis.close();

        return bufData;
    }


    public static File getFile(File dir, String fileName, boolean create) {
        File tempFile = new File(dir, fileName);
        if (tempFile.isDirectory()) {
            SystemFile.deleteDirectory(tempFile);
        }
        if (create && !tempFile.exists()) {
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                tempFile = null;
            }
        }
        return tempFile;
    }
}
