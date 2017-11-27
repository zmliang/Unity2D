package com.jinkegroup.upgradelib.util;

import java.util.Hashtable;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class Constant {
    private static final boolean USING_DEBUG_SERVER = false; //!!!Set it as false when releasing!!!

    public static final String SHELL_START_BOOK_INFO = "shell_book.info";

    private static Hashtable<String, String> SERVICE_URL_MAP = new Hashtable<String, String>();
    private static final String TEST_SERVER = "http://192.168.1.101";//"http://10.40.30.87"; //

    public interface SERVICE_URL {
        String DBOOK = "DBOOK"; //图书下载服务器地址
        String STATISTICS = "STATISTICS"; //统计服务地址
        String PAY_CHECK = "PAY_CHECK"; //计费服务地址
        String PUSH_MESSAGE = "PUSH_MESSAGE";
        String VERSION_CHECK = "VERSION_CHECK"; //版本检查
        String AD = "AD"; //

        //书城频道地址
        String STORE_CHOICE_CLIENT = "STORE_CHOICE_CLIENT";
        String STORE_DISCOVERY_CHANNEL = "STORE_DISCOVERY_CHANNEL";
        String STORE_CHANNEL_SELECT = "STORE_CHANNEL_SELECT";
        String STORE_CHANNEL_CATEGORY = "STORE_CHANNEL_CATEGORY";
        String STORE_CHANNEL_RANK = "STORE_CHANNEL_RANK";
        String STORE_CHANNEL_SEARCH = "STORE_CHANNEL_SEARCH";
        String STORE_BOOK_DATAIL = "STORE_BOOK_DATAIL";
    }

    static {
        if (USING_DEBUG_SERVER) {
            SERVICE_URL_MAP.put(SERVICE_URL.DBOOK, TEST_SERVER + ":8888/");
            SERVICE_URL_MAP.put(SERVICE_URL.STATISTICS, TEST_SERVER + ":8889/");
            SERVICE_URL_MAP.put(SERVICE_URL.PAY_CHECK, TEST_SERVER + ":9001/");
            SERVICE_URL_MAP.put(SERVICE_URL.PUSH_MESSAGE, TEST_SERVER + ":9003/");
            SERVICE_URL_MAP.put(SERVICE_URL.VERSION_CHECK, TEST_SERVER + ":9000/");
            SERVICE_URL_MAP.put(SERVICE_URL.AD, TEST_SERVER + ":9005/");

            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_SELECT, TEST_SERVER + ":8080/bstore-service/choice/page");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_CATEGORY, TEST_SERVER + ":8080/bstore-service/book/category");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_RANK, TEST_SERVER + ":8080/bstore-service/book/rank");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_SEARCH, TEST_SERVER + ":8080/bstore-service/book/search");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_BOOK_DATAIL, TEST_SERVER + ":8080/bstore-service/book/detail?bid=");

            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHOICE_CLIENT, TEST_SERVER + ":8080/bstore-service/choice/client");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_DISCOVERY_CHANNEL, TEST_SERVER + ":8080/bstore-service/discovery/channel");
        }
        else {
            SERVICE_URL_MAP.put(SERVICE_URL.DBOOK, "http://dbook.r.suishi.mobi/");
            SERVICE_URL_MAP.put(SERVICE_URL.STATISTICS, "http://sta.r.suishi.mobi:8889/");
            SERVICE_URL_MAP.put(SERVICE_URL.PAY_CHECK, "http://pay.r.suishi.mobi:9001/");
            SERVICE_URL_MAP.put(SERVICE_URL.PUSH_MESSAGE, "http://push.r.suishi.mobi:9003/");
            SERVICE_URL_MAP.put(SERVICE_URL.VERSION_CHECK, "http://upgrade.suishi.mobi/");
            SERVICE_URL_MAP.put(SERVICE_URL.AD, "http://ad.r.suishi.mobi/");

            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_SELECT, "http://bstore.r.suishi.mobi/bstore-service/choice/page");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_CATEGORY, "http://bstore.r.suishi.mobi/bstore-service/book/category");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_RANK, "http://bstore.r.suishi.mobi/bstore-service/book/rank");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHANNEL_SEARCH, "http://bstore.r.suishi.mobi/bstore-service/book/search");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_BOOK_DATAIL, "http://bstore.r.suishi.mobi/bstore-service/book/detail?bid=");

            SERVICE_URL_MAP.put(SERVICE_URL.STORE_CHOICE_CLIENT, "http://bstore.r.suishi.mobi/bstore-service/choice/client");
            SERVICE_URL_MAP.put(SERVICE_URL.STORE_DISCOVERY_CHANNEL, "http://bstore.r.suishi.mobi/bstore-service/discovery/channel");
        }
    }

    public static final boolean TEST_PAY_MOCK_RESULT_SUCCEED = false;  //should be false for release
    public static final boolean TEST_PAY_MOCK_RESULT_FAILED = false;  //should be false for release

    public static final String PREF_KEY_UPGRADE_CHECK_PENDING = "upgrade_check_pending";
    public static final String PREF_KEY_UPGRADE_ETAG = "upgrade_etag";
    public static final String PREF_KEY_UPGRADE_STATE = "upgrade_state";
    public static final String PREF_KEY_UPGRADE_TRY_COUNT = "upgrade_try_count";
    public static final String PREF_KEY_UPGRADE_HINT_TIME = "upgrade_hint_time";
    public static final String PREF_KEY_UPGRADE_CHECK_LAST_TIME = "upgrade_check_last_time";

    public static final String APP_BASE_DIRECTORY = "suishireader";
    public static final String BOOK_BASE_DIRECTORY = "books";
    public static final String CHAPTER_FILE_SUFFIX = ".cha";
    public static final String CHAPTER_TEMP_FILE_SUFFIX = ".cha.tmp";

    public static final String UPGRADE_DIRECTORY = "upgrade";

    //
    public static final int UPGRADE_INTERVAL_MIN = 24*60;  //24h

    public static final int UPGRADE_HINT_INTERVAL_MS = 60*60*1000;   //1h

    public static final long EXIT_DOBULE_CONFIRM_TIME = 2000;

    //notification id definition
    public static final int NOTIFY_ID_UPGRADE_AVAILABLE   = 1;
    public static final int NOTIFY_ID_PUSH_MSG			  = 2;
    public static final int NOTIFY_ID_BOOK_UPDATE_START   = 3;
    public static final int NOTIFY_ID_BOOK_UPDATE_END     = 7;

    public static final int NOTIFY_ID_DOWNLOAD_START	= 10000;
    public static final int NOTIFY_ID_DOWNLOAD_END		= 100000;

    public static final int LOGGER_LEVEL = 1; //error

    public static final int NEW_BOOK_BATCH_DOWNLOAD_DELAY = 10000;

    //error codes:
    public static final int EC_SUCCESS			= 0;
    public static final int EC_FAIL				= 1;
    public static final int EC_PENDING			= 2;

    public static final int EC_DB_FAIL 			= 100;
    public static final int EC_NETWORK_FAIL 	= 101;	//网络操作错误
    public static final int EC_FILE_FAIL		= 102;
    public static final int EC_CANCELLED		= 103;
    public static final int EC_NETWORK_NONE		= 104;	//无网络

    public static final int EC_BOOK_NOTFOUND 	= 1000;
    public static final int EC_BOOK_ADD_EXIST	= 1001;

    public static final int EC_CHAPTER_NOTFOUND = 2000;
    public static final int EC_CHAPTER_MALFORMAT_FROM_SERVER = 2001;

    public static final int EC_PAYCHECK_TOPAY	= 3000;
    public static final int EC_PAY_FAILED		= 3001;
    public static final int EC_PAY_USER_CANCLLED = 3002;

    public static String getServiceUrl(String serviceName) {
        return SERVICE_URL_MAP.get(serviceName);
    }

    public static final String BOOK_ADV_SOURCE_TENCENT = "tencent";
    public static final String GdtAppID = "1104855600";
    public static final String GdtBannerID = "9060200653332343";
    public static final String GdtIntersitialID = "2030500633131334";
    public static final String GdtAppWallID = "6010707623039357";
    public static final String GdtWebViewTopBannerID = "9000308624747402";
    public static final String GdtWebViewBottomBannerID = "9050803674844473";

    public static final String UucunPayAppKey = "Q0OBa6b3s1skjSTav4G6VsmSoprRCEGs";

    public static final String BOOK_ADV_SOURCE_UUCUN = "uucun";
}
