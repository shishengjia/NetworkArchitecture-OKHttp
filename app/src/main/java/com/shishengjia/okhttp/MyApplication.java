package com.shishengjia.okhttp;

import android.app.Application;

import com.shishengjia.download.file.FileStroageManager;

/**
 *
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStroageManager.getInstance().init(this);
    }
}
