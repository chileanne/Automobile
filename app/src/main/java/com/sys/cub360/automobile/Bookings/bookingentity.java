package com.sys.cub360.automobile.Bookings;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Booking")
public class bookingentity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int ids;

    @NonNull
    @ColumnInfo(name ="currentdate")
    private String mcurrentdate;

    @NonNull
    @ColumnInfo(name ="taken")
    private String mtaken;

    @NonNull
    @ColumnInfo(name ="time_endfix")
    private String mtime_endfix;

    @NonNull
    @ColumnInfo(name ="time_of_fix")
    private String mtime_of_fix;

    @NonNull
    @ColumnInfo(name ="name")
    private String mname;

    @NonNull
    @ColumnInfo(name ="phoneno")
    private String mphoneno;

    @NonNull
    @ColumnInfo(name ="pit")
    private String mpit;

    @NonNull
    @ColumnInfo(name ="price")
    private String mprice;


    public bookingentity(@NonNull int ids, @NonNull String mcurrentdate, @NonNull String mtaken, @NonNull String mtime_endfix, @NonNull String mtime_of_fix, @NonNull String mname, @NonNull String mphoneno, @NonNull String mpit, @NonNull String mprice) {
        this.ids = ids;
        this.mcurrentdate = mcurrentdate;
        this.mtaken = mtaken;
        this.mtime_endfix = mtime_endfix;
        this.mtime_of_fix = mtime_of_fix;
        this.mname = mname;
        this.mphoneno = mphoneno;
        this.mpit = mpit;
        this.mprice = mprice;
    }

    @NonNull
    public int getIds() {
        return ids;
    }

    @NonNull
    public String getMcurrentdate() {
        return mcurrentdate;
    }

    @NonNull
    public String getMtaken() {
        return mtaken;
    }

    @NonNull
    public String getMtime_endfix() {
        return mtime_endfix;
    }

    @NonNull
    public String getMtime_of_fix() {
        return mtime_of_fix;
    }

    @NonNull
    public String getMname() {
        return mname;
    }

    @NonNull
    public String getMphoneno() {
        return mphoneno;
    }

    @NonNull
    public String getMpit() {
        return mpit;
    }

    @NonNull
    public String getMprice() {
        return mprice;
    }
}
