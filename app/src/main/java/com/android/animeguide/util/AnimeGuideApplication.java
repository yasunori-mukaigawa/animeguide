package com.android.animeguide.util;

import android.app.Application;

import com.android.animeguide.database.AppDatabase;
import com.android.animeguide.database.AppDatabaseSingleton;

public class AnimeGuideApplication extends Application {

    public AppDatabase mAppDatabase;
    private static AnimeGuideApplication instance = null;

    private AnimeGuideApplication() {}

    @Override
    public void onCreate() {
        super.onCreate();

        mAppDatabase = AppDatabaseSingleton.getInstance(this);
    }

    public static AnimeGuideApplication getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new AnimeGuideApplication();
        return instance;
    }
}
