package com.sys.cub360.automobile.Auto_adivice_database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class Autoviewmodel extends AndroidViewModel {
    private AutoDatabase mdatadb;
    private AutoDao medodao;

    public Autoviewmodel(@NonNull Application application) {
        super(application);
        mdatadb = AutoDatabase.getDatabase(application);
        medodao = mdatadb.medodao();
    }

    public LiveData<List<Autoentity>> getAlldata() {
        return medodao.getAlldatas();
    }


    public void inserts(Autoentity medo) {
        new Autoviewmodel.InsertsAsyncTassks(medodao).execute(medo);
    }

    public void deletes() {
        new Autoviewmodel.deleteAsynctasks(medodao).execute();
    }

    private class InsertsAsyncTassks extends AsyncTask<Autoentity, Void, Void> {
        AutoDao medodao;

        public InsertsAsyncTassks(AutoDao medodao) {
            this.medodao = medodao;
        }


        @Override
        protected Void doInBackground(Autoentity... autoentities) {
            medodao.inserts(autoentities[0]);
            return null;
        }
    }

    private class deleteAsynctasks extends AsyncTask<Autoentity, Void, Void> {
        AutoDao medodao;

        public deleteAsynctasks(AutoDao medodao) {
            this.medodao = medodao;
        }


        @Override
        protected Void doInBackground(Autoentity... autoentities) {
            medodao.deletealls();
            return null;
        }
    }
}



