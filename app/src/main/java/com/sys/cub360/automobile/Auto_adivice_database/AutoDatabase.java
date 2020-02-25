package com.sys.cub360.automobile.Auto_adivice_database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Autoentity.class, version = 2)
public abstract class AutoDatabase extends RoomDatabase {
    public abstract AutoDao medodao();

    private static volatile AutoDatabase INSTANCE;

    static AutoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized ( AutoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AutoDatabase.class, "Auto_database")
                            .fallbackToDestructiveMigration()
                            // .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
