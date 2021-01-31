package com.android.animeguide.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.animeguide.database.entity.AppEntity;

@Dao
public abstract class AppDao  {
    @Query("select id from AppEntity order by id desc limit 1")
    public abstract int getMaxId();

    @Query("SELECT title FROM AppEntity WHERE year = :year AND period = :period")
    public abstract String getTitle(int year, int period);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insert(AppEntity appEntity);

    @Query("delete from appEntity where id = :id")
    abstract void delete(long id);
}