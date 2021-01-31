package com.android.animeguide.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.animeguide.database.dao.AppDao;
import com.android.animeguide.database.entity.AppEntity;

@Database(entities = {AppEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao DaoTest();
}
