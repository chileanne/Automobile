package com.sys.cub360.automobile.Vehicle_history_database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sys.cub360.automobile.Auto_adivice_database.AutoDao;
import com.sys.cub360.automobile.Auto_adivice_database.AutoDatabase;
import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;

@Database(entities = vehicleentity.class, version = 1)
public abstract class vehicledatabase extends RoomDatabase {
    public abstract vehicledao medodao();

    private static volatile vehicledatabase INSTANCE;

    static vehicledatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized ( vehicledatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            vehicledatabase.class, "vehicle_database")
                            .fallbackToDestructiveMigration()
                            // .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
