package com.sys.cub360.automobile.Vehicle_history_database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Vehicle")
public class vehicleentity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int ids;

    @NonNull
    @ColumnInfo(name ="date")
    private String mdate;

    @NonNull
    @ColumnInfo(name ="cost")
    private String mcost;

    @NonNull
    @ColumnInfo(name ="summary")
    private String msummary;

    @NonNull
    @ColumnInfo(name ="cardmodel")
    private String mcardmodel;

    @NonNull
    @ColumnInfo(name ="workername")
    private String mworkername;


    public vehicleentity(@NonNull int ids, @NonNull String mdate, @NonNull String mcost, @NonNull String msummary, @NonNull String mcardmodel, @NonNull String mworkername) {
        this.ids = ids;
        this.mdate = mdate;
        this.mcost = mcost;
        this.msummary = msummary;
        this.mcardmodel = mcardmodel;
        this.mworkername = mworkername;
    }

    @NonNull
    public int getIds() {
        return ids;
    }

    @NonNull
    public String getMdate() {
        return mdate;
    }

    @NonNull
    public String getMcost() {
        return mcost;
    }

    @NonNull
    public String getMsummary() {
        return msummary;
    }

    @NonNull
    public String getMcardmodel() {
        return mcardmodel;
    }

    @NonNull
    public String getMworkername() {
        return mworkername;
    }
}
