package com.jinkegroup.sharesdk;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by Administrator on 2017/7/17.
 */

public class ShareSdkUtils {

    public static void registerSdk(){

        // 微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx7b4e5d4dec309ac5", "8cd1ea0509b6c9c6292050f8a86190a3");
        // 豆瓣RENREN平台目前只能在服务器端配置
        // 新浪微博
        PlatformConfig.setSinaWeibo("64127917", "a4b62a63274c8cffa23eda94bdd60f8b","http://sns.whalecloud.com");

        PlatformConfig.setQQZone("1104910014", "KEYv4cmJMEG6NDrzFX6");

    }

}
