package com.sys.cub360.automobile.Auto_adivice_database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AutoDao {
    @Insert
    void inserts(Autoentity muserentity);

    @Query("DELETE from Auto")
    void deletealls();

    @Query("SELECT * from Auto")
    LiveData<List<Autoentity>> getAlldatas();
}
