package com.sys.cub360.automobile.Bookings;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.sys.cub360.automobile.Auto_adivice_database.AutoDao;
import com.sys.cub360.automobile.Auto_adivice_database.AutoDatabase;
import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;
import com.sys.cub360.automobile.Auto_adivice_database.Autoviewmodel;

import java.util.List;

public class bookingviewmodel extends AndroidViewModel {
    private bookingdatabase mdatadb;
    private bookingdao medodao;
    public bookingviewmodel(@NonNull Application application) {
        super(application);
        mdatadb = bookingdatabase.getDatabase(application);
        medodao = mdatadb.medodao();

    }

  /*public LiveData<List<bookingentity>> getAlldata() {
        return medodao.getAlldatas();
    }*/

    public LiveData<List<bookingentity>> getAlldata() {
        return medodao.getAlldatas();
    }


    public void inserts(bookingentity medo) {
        new  bookingviewmodel.InsertsAsyncTassks(medodao).execute(medo);
    }

    public void deletes() {
        new  bookingviewmodel.deleteAsynctasks(medodao).execute();
    }

    private class InsertsAsyncTassks extends AsyncTask<bookingentity, Void, Void> {
        bookingdao medodao;

        public InsertsAsyncTassks(bookingdao medodao) {
            this.medodao=medodao;
        }

        @Override
        protected Void doInBackground(bookingentity... bookingentities) {
            medodao.inserts(bookingentities[0]);
            return null;
        }
    }


    private class deleteAsynctasks extends AsyncTask<bookingentity, Void, Void> {
        bookingdao medodao;

        public deleteAsynctasks(bookingdao medodao) {
            this.medodao=medodao;
        }


        @Override
        protected Void doInBackground(bookingentity... bookingentities) {
            medodao.deletealls();
            return null;
        }
    }



    }
