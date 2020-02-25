package com.sys.cub360.automobile.Bookings;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;

import java.util.List;

@Dao
public interface bookingdao {
    @Insert
    void inserts(bookingentity muserentity);

    @Query("DELETE from Booking")
    void deletealls();

    @Query("SELECT * from Booking")
    LiveData<List<bookingentity>> getAlldatas();


   /* @Query("SELECT * from Booking WHERE taken= :taken LIMIT 1")
    LiveData<List<bookingentity>> getAlldatas(String taken);*/


}
