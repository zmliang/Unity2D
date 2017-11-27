package com.jinkegroup.upgradelib;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.jinkegroup.upgradelib.downloadservice.DownloadService;
import com.jinkegroup.upgradelib.downloadservice.DownloaderProvider;
import com.jinkegroup.upgradelib.util.BaseContext;
import com.jinkegroup.upgradelib.util.Constant;
import com.jinkegroup.upgradelib.util.MapQuery;
import com.jinkegroup.upgradelib.util.SystemFile;
import com.jinkegroup.upgradelib.util.SystemPackageTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mobi.suishi.reader.constant.SConstant;
import mobi.suishi.security.AESCrypto;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class UpgradeCheckService extends JobCheckService {


    public static final String ACTION_TIMER_EXPIRED = "jinkegroup.upgrade.TIMER";
    public static final String ACTION_FORCE_UPGRADE_CHECK = "jinkegroup.upgrade.FORCE_CHECK";


    public UpgradeCheckService() {
        super("UpgradePollingService");

    }


    @Override
    protected void runJob() {
        Intent dlIntent = new Intent(DownloadService.ACTION_DOWNLOAD_PACKAGE, null, this, DownloadService.class);
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_URL, Constant.getServiceUrl(Constant.SERVICE_URL.VERSION_CHECK));
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_FILE_NAME, DeviceInfo.getInstance().getClientApkFileName());
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_DOWNLOAD_SOURCE, DownloadService.DOWNLOAD_SOURCE_UPGRADE);

        dlIntent.putExtra(DownloadService.INTENT_EXTRA_FILE_NAME, DeviceInfo.getInstance().getClientApkFileName());
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_SHOW_PROGRESS, false);
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_AUTO_INSTALL, false);
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_NOTIFICATION_TEXT, getString(R.string.app_name) + getString(R.string.upgrade_hint));
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_NOTIFICATION_ID, Constant.NOTIFY_ID_UPGRADE_AVAILABLE);

        Bundle params = new Bundle();
        params.putString(SConstant.PN_APKVERSION, getLocalVerion());
        params.putString(SConstant.PN_UPGRADE_TYPE, SConstant.PV_UPGRADE_FORCE);
        dlIntent.putExtra(DownloadService.INTENT_EXTRA_PARAMETER, params);
        startService(dlIntent);
    }

    private String getLocalVerion() {

        int iLocalVersion = DeviceInfo.getInstance().getClientVersion();


        File apkFile = DownloaderProvider.getFile(this, DeviceInfo.getInstance().getClientApkFileName(), false);

        if (apkFile.exists()) {
            int iApkVersion = SystemPackageTools.getApkVersion(this, apkFile);

            if (iLocalVersion >= iApkVersion) {
                apkFile.delete();
            } else {
                if (!DeviceInfo.getInstance().getSignature().equals(
                        SystemPackageTools.getSignature(this, apkFile))) {
                    apkFile.delete();
                } else {
                    iLocalVersion = iApkVersion;
                }
            }
        } else {

        }

        return ""+iLocalVersion;
    }


    @Override
    protected String getCheckAction() {
        return ACTION_TIMER_EXPIRED;
    }


    @Override
    protected String getLastJobTimeKey() {
        return Constant.PREF_KEY_UPGRADE_CHECK_LAST_TIME;
    }


    @Override
    protected String getPendingKey() {
        return Constant.PREF_KEY_UPGRADE_CHECK_PENDING;
    }


    @Override
    protected long getNextRunTime(long lastCheckTime) {
        return System.currentTimeMillis() + Constant.UPGRADE_INTERVAL_MIN * 60 * 1000;
    }


    @Override
    protected long getMinimalJobInterval() {
        return Constant.UPGRADE_INTERVAL_MIN * 60 * 1000 / 10;
    }


    public static class DeviceInfo {
        private static DeviceInfo mInstance;

        private PackageInfo mPackageInfo;

        private final static String UNAVAILABLE = "unavailable";
        private final static int 	INT_UNSET = -1;

        private String 			mImsi;
        private String			mImei;

        private String 			mChannel;

        private String			mSignature;

        private int				mWidth = 0;
        private int				mHeight = 0;
        private int				mDpi	= 0;

        private int				mRamSize = 0;

        private int 			mOperator = INT_UNSET;
        private int				mNetwork = INT_UNSET;

        private Context mContext;

        private DeviceInfo() {
            mContext = BaseContext.getContext();

            try {
                mPackageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);
            } catch (Exception e) {

            }

            initDisplayMetrics();

            initRamSize();
        }

        private void initDisplayMetrics() {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowMgr = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            windowMgr.getDefaultDisplay().getMetrics(dm);
            mWidth = dm.widthPixels;
            mHeight = dm.heightPixels;
            mDpi = dm.densityDpi;
        }

        private void initRamSize() {
            String str1 = "/proc/meminfo";// 系统内存信息文件
            String str2;
            String[] arrayOfString;
            int totalSize = 0;
            try {
                FileReader localFileReader = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
                str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
                arrayOfString = str2.split("\\s+");
                // 获得系统总内存，单位是KB，除以1024转换为M
                totalSize = Integer.valueOf(arrayOfString[1]).intValue() / 1024;
                localBufferedReader.close();

            } catch (IOException e) {
                //
            }
            mRamSize = totalSize;
        }

        public static DeviceInfo getInstance() {
            synchronized(DeviceInfo.class) {
                if (mInstance == null) {
                    mInstance = new DeviceInfo();
                }
            }

            return mInstance;
        }

        public synchronized String getNormalIMEI() {
            TelephonyManager telephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyMgr.getDeviceId();
            if (imei == null)
                imei = UNAVAILABLE;

            return imei;
        }

        public synchronized String getIMEI() {
            if (mImei == null) {
                byte[] pixels = SystemFile.readTailFromRawSource(mContext, R.drawable.read_bg_yellow_dark, 16);
                String key = AESCrypto.toHex(pixels);

                String imei = getNormalIMEI();
                try {
                    mImei = AESCrypto.aesEncryptToBase64(imei, key);
                    //test...
                    //imei = AESCrypto.aesDecryptByBase64(mImei, key);
                } catch (Exception e) {
                    mImei = imei;
                }
            }
            return mImei;
        }

        public synchronized String getNormalIMSI() {
            TelephonyManager telephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi = telephonyMgr.getSubscriberId();
            if (imsi == null)
                imsi = UNAVAILABLE;

            return imsi;
        }

        public synchronized String getIMSI() {
            if (mImsi == null) {
                byte[] pixels = SystemFile.readTailFromRawSource(mContext, R.drawable.read_bg_yellow_dark, 16);
                String key = AESCrypto.toHex(pixels);

                String imsi = getNormalIMSI();
                try {
                    mImsi = AESCrypto.aesEncryptToBase64(imsi, key);
                    //test...
                    //imsi = AESCrypto.aesDecryptByBase64(mImsi, key);
                } catch (Exception e) {
                    mImsi = imsi;
                }
            }

            return mImsi;
        }


        public synchronized void unsetIMSI() {
            mImsi = null;
        }


        public String getBrand() {
            return Build.BRAND;
        }

        public String getVendor() {
            return Build.MANUFACTURER;
        }

        public String getModel() {
            return Build.MODEL;
        }

        public String getOS() {
            return "Android";
        }

        public String getOsVersion() {
            return Build.VERSION.RELEASE;
        }

        public synchronized String getSignature() {

            if (mSignature != null)
                return mSignature;

            Signature[] signatures = mPackageInfo.signatures;
            StringBuffer buffer = new StringBuffer();

            if (signatures != null) {
                for (Signature signature : signatures) {
                    buffer.append(signature.toCharsString());
                }
                mSignature = buffer.toString();
            }

            mSignature = mSignature == null ? "" : mSignature;

            return mSignature;
        }

        public int getClientVersion() {
            PackageInfo pInfo = null;

            try {
                pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            } catch (Exception e) {

            }
            int versionCode = 0;
            if (pInfo != null) {
                versionCode = pInfo.versionCode;
            }
            return versionCode;
        }

        public String getClientVersionName() {
            PackageInfo pInfo = null;

            try {
                pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            } catch (Exception e) {

            }
            String versionName = "";
            if (pInfo != null) {
                versionName = pInfo.versionName;
            }
            return versionName;
        }

        public synchronized String getChannelCode() {
            if (mChannel == null) {
                try {
                    ApplicationInfo ai = mContext.getPackageManager().getApplicationInfo(
                            mContext.getPackageName(), PackageManager.GET_META_DATA);
                    Bundle bundle = ai.metaData;
                    mChannel = bundle.getString("UMENG_CHANNEL");
                } catch (Exception e) {
                }
            }

            if (mChannel == null) {
                mChannel = UNAVAILABLE;
            }

            return mChannel;
        }

        public int getDpi() {
            return mDpi;
        }

        public int getHeight() {
            return mHeight;
        }

        public int getWidth() {
            return mWidth;
        }

        public int getRam() {
            return mRamSize;
        }


        public String getPackageName() {
            return mContext.getPackageName();
        }


        public String getClientApkFileName() {
            return mContext.getPackageName() + ".apk";
        }

        /**
         * Get IP address from first non-localhost interface
         * @param useIPv4  true=return ipv4, false=return ipv6
         * @return  address or empty string
         */
        public String getIPAddress(boolean useIPv4) {
            try {
                List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : interfaces) {
                    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                    for (InetAddress addr : addrs) {
                        if (!addr.isLoopbackAddress()) {
                            String sAddr = addr.getHostAddress();
                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            boolean isIPv4 = sAddr.indexOf(':')<0;

                            if (useIPv4) {
                                if (isIPv4)
                                    return sAddr;
                            } else {
                                if (!isIPv4) {
                                    int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                    return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) { } // for now eat exceptions

            return "";
        }

        public synchronized int getOperator() {
            if (mOperator == INT_UNSET) {
                mOperator = SConstant.PROVIDER_ID_UNKNOWN;
                PackageManager pm = mContext.getPackageManager();
                if ((pm.checkPermission(Manifest.permission.READ_PHONE_STATE,
                        mContext.getPackageName())) == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager mTelephonyMgr = (TelephonyManager) mContext
                            .getSystemService(Context.TELEPHONY_SERVICE);
                    String imsi = mTelephonyMgr.getSubscriberId();
                    if (imsi != null && !imsi.equals("")) {
					/*
					 * 获取运营商
					 */
                        int mcc = mContext.getResources().getConfiguration().mcc;// 获取mcc码
                        int mnc = mContext.getResources().getConfiguration().mnc;// 获取mnc码
                        if (mcc == SConstant.CHINA_MCC_CODE) {
                            if (mnc == 0 || mnc == 2) {
							/*
							 * 当mnc码为0或2时。确定运营商为中国移动
							 */
                                mOperator = SConstant.PROVIDER_ID_CHINA_MOBILE;
                            } else if (mnc == 1) {
							/*
							 * 当mnc码为1时。确定运营商为中国联通
							 */
                                mOperator = SConstant.PROVIDER_ID_CHINA_UNICOM;
                            } else if (mnc == 3) {
							/*
							 * 当mnc码为3时。确定运营商为中国电信
							 */
                                mOperator = SConstant.PROVIDER_ID_CHINA_TELECOM;
                            } else {
                                mOperator = SConstant.PROVIDER_ID_UNKNOWN;
                            }
                        }
                    }
                }
            }

            return mOperator;
        }


        public synchronized void unsetOperator() {
            mOperator = INT_UNSET;
        }


        public synchronized int getNetworkType() {
            if (mNetwork == INT_UNSET) {
                mNetwork = SConstant.NETWORK_STANDARD_UNKNOWN;
                ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager == null) {
                    return mNetwork;
                }

                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    int curIfTypeInt               = -1;

                    curIfTypeInt = networkInfo.getType();
                    if (curIfTypeInt == ConnectivityManager.TYPE_WIFI) {
                        mNetwork = SConstant.NETWORK_STANDARD_WIFI;
                    } else if (curIfTypeInt == ConnectivityManager.TYPE_MOBILE) {
                        mNetwork = getNetworkStandard(networkInfo.getSubtype());
                    }
                }
            }

            return mNetwork;
        }



        public String getDeviceProfileParameters() {
            String im = getIMEI();
            String sm = getIMSI();
            String ip = getIPAddress(true);
            String md = getModel();
            int op = getOperator();
            int nt = getNetworkType();

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("im", im);
            params.put("sm", sm);
            params.put("ip", ip);
            params.put("md", md);
            params.put("op", String.valueOf(op));
            params.put("nt", String.valueOf(nt));

            return MapQuery.urlEncodeUTF8(params);
        }





        public synchronized void unsetNetworkAccess() {
            mNetwork = INT_UNSET;
        }

        /*
         * http://www.binkery.com/archives/368.html
         */
        private int getNetworkStandard(int phoneType) {

            switch (phoneType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return SConstant.NETWORK_STANDARD_2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return SConstant.NETWORK_STANDARD_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return SConstant.NETWORK_STANDARD_4G;
                default:
                    return SConstant.NETWORK_STANDARD_UNKNOWN;
            }

        }


    }

}
