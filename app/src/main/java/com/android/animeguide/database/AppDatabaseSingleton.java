package com.android.animeguide.database;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseSingleton {
    private static AppDatabase instance = null;
    private AppDatabaseSingleton() {}
    private static String TB_NAME = "AnimeGuide.db";

    public static AppDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }
        instance =  Room.databaseBuilder(context,
                AppDatabase.class, TB_NAME).build();
        return instance;
    }
}
