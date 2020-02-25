package com.sys.cub360.automobile.Vehicle_history_database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;

import java.util.List;

@Dao
public interface vehicledao {
    @Insert
    void inserts(vehicleentity muserentity);

    @Query("DELETE from vehicle")
    void deletealls();

    @Query("SELECT * from vehicle")
    LiveData<List<vehicleentity>> getAlldatas();
}
