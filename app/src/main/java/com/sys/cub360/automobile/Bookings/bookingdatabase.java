package com.sys.cub360.automobile.Bookings;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sys.cub360.automobile.Auto_adivice_database.AutoDao;
import com.sys.cub360.automobile.Auto_adivice_database.AutoDatabase;
import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;

@Database(entities = bookingentity.class, version = 1)
public abstract class bookingdatabase extends RoomDatabase {
    public abstract bookingdao medodao();

    private static volatile bookingdatabase INSTANCE;

    static bookingdatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized ( bookingdatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            bookingdatabase.class, "booking_database")
                            .fallbackToDestructiveMigration()
                            // .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
