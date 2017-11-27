package com.jinkegroup.upgradelib.util;

import com.jinkegroup.upgradelib.UpgradeCheckService;

import java.util.HashMap;
import java.util.Map;

import mobi.suishi.reader.constant.SConstant;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class QueryParameter {
    public static Map<String, String> getBasicParameters() {

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(SConstant.PN_IMEI, UpgradeCheckService.DeviceInfo.getInstance().getIMEI());
        parameters.put(SConstant.PN_IMSI, UpgradeCheckService.DeviceInfo.getInstance().getIMSI());
        parameters.put(SConstant.PN_CLIENTVERSION, ""+UpgradeCheckService.DeviceInfo.getInstance().getClientVersion());
        parameters.put(SConstant.PN_CHANNEL, UpgradeCheckService.DeviceInfo.getInstance().getChannelCode());
        parameters.put(SConstant.PN_NETWORK, ""+UpgradeCheckService.DeviceInfo.getInstance().getNetworkType());

        return parameters;
    }


    public static Map<String, String> getFullParameters() {

        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put(SConstant.PN_IMEI, UpgradeCheckService.DeviceInfo.getInstance().getIMEI());
        parameters.put(SConstant.PN_IMSI, UpgradeCheckService.DeviceInfo.getInstance().getIMSI());
        parameters.put(SConstant.PN_BRAND, UpgradeCheckService.DeviceInfo.getInstance().getBrand());
        parameters.put(SConstant.PN_VENDOR, UpgradeCheckService.DeviceInfo.getInstance().getVendor());
        parameters.put(SConstant.PN_MODEL, UpgradeCheckService.DeviceInfo.getInstance().getModel());
        parameters.put(SConstant.PN_OS, UpgradeCheckService.DeviceInfo.getInstance().getOS());
        parameters.put(SConstant.PN_OSVERSION, UpgradeCheckService.DeviceInfo.getInstance().getOsVersion());
        parameters.put(SConstant.PN_CLIENTVERSION, ""+UpgradeCheckService.DeviceInfo.getInstance().getClientVersion());
        parameters.put(SConstant.PN_PACKAGENAME, UpgradeCheckService.DeviceInfo.getInstance().getPackageName());
        parameters.put(SConstant.PN_CHANNEL, UpgradeCheckService.DeviceInfo.getInstance().getChannelCode());
        parameters.put(SConstant.PN_NETWORK, ""+UpgradeCheckService.DeviceInfo.getInstance().getNetworkType());
        parameters.put(SConstant.PN_OPERATOR, ""+UpgradeCheckService.DeviceInfo.getInstance().getOperator());
        parameters.put(SConstant.PN_DPI, ""+UpgradeCheckService.DeviceInfo.getInstance().getDpi());
        parameters.put(SConstant.PN_HEIGHT, ""+UpgradeCheckService.DeviceInfo.getInstance().getHeight());
        parameters.put(SConstant.PN_WIDTH, ""+UpgradeCheckService.DeviceInfo.getInstance().getWidth());
        parameters.put(SConstant.PN_RAM, ""+UpgradeCheckService.DeviceInfo.getInstance().getRam());

        return parameters;
    }
}
