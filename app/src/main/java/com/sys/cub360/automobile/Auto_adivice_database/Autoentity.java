package com.sys.cub360.automobile.Auto_adivice_database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Auto")
public class Autoentity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int ids;

    @NonNull
    @ColumnInfo(name = "date")
    private String mdate;

    @NonNull
    @ColumnInfo(name = "writername")
    private String mwritername;

    @NonNull
    @ColumnInfo(name = "title")
    private String mtitle;

    @NonNull
    @ColumnInfo(name = "body")
    private String mbody;

    @NonNull
    @ColumnInfo(name = "thumbimage")
    private String mthumbimage;

    @NonNull
    @ColumnInfo(name = "adminid")
    private String madminid;


    public Autoentity(@NonNull int ids, @NonNull String mdate, @NonNull String mwritername, @NonNull String mtitle, @NonNull String mbody, @NonNull String mthumbimage, @NonNull String madminid) {
        this.ids = ids;
        this.mdate = mdate;
        this.mwritername = mwritername;
        this.mtitle = mtitle;
        this.mbody = mbody;
        this.mthumbimage = mthumbimage;
        this.madminid = madminid;
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
    public String getMwritername() {
        return mwritername;
    }

    @NonNull
    public String getMtitle() {
        return mtitle;
    }

    @NonNull
    public String getMbody() {
        return mbody;
    }

    @NonNull
    public String getMthumbimage() {
        return mthumbimage;
    }

    @NonNull
    public String getMadminid() {
        return madminid;
    }
}