package com.codestructure.util;

import android.os.Environment;

public class Config {

    public static String TAG = "CodeStructure";

    //Dtatabse
    // Create a directory in SD CARD
    public static String APP_HOME = Environment.getExternalStorageDirectory()
            .getPath() + "/" + TAG;

    public static String SENT_IMAGE_PATH = APP_HOME + "/SentFile";
    public static String PROFILE_IMAGE_PATH = APP_HOME + "/GroupIcon";

    // A directory to store logs
    public static String DIR_LOG = APP_HOME + "/log";
    // preference file name
    public static final String PREF_FILE = TAG + "_PREF";
    public static String DIR_USERDATA = APP_HOME + "/userdata";
    public static String DB_NAME = TAG + ".db";

    //Pref
    public static final String PREF_IS_LOGGED_IN = "PREF_IS_LOGGED_IN";
    public static final String PREF_SIGN_UP_ARRAY_LIST = "PREF_SIGN_UP_ARRAY_LIST";

    //Privacy Policy
    public static final String PRIVACY_POLICY = "https://www.techcronus.com/privacy-policy/";

}