package com.sys.cub360.automobile.Vehicle_history_database;

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

public class vehicleviewmodel   extends AndroidViewModel {
    private vehicledatabase mdatadb;
    private vehicledao medodao;

    public vehicleviewmodel(@NonNull Application application) {
        super(application);
        mdatadb = vehicledatabase.getDatabase(application);
        medodao = mdatadb.medodao();
    }


    public LiveData<List<vehicleentity>> getAlldata() {
        return medodao.getAlldatas();
    }


    public void inserts(vehicleentity medo) {
        new vehicleviewmodel.InsertsAsyncTassks(medodao).execute(medo);
    }

    public void deletes() {
        new vehicleviewmodel.deleteAsynctasks(medodao).execute();
    }

    private class InsertsAsyncTassks extends AsyncTask<vehicleentity, Void, Void> {
        vehicledao medodao;

        public InsertsAsyncTassks(vehicledao medodao) {
            this.medodao = medodao;
        }

        @Override
        protected Void doInBackground(vehicleentity... vehicleentities) {
            medodao.inserts(vehicleentities[0]);
            return null;
        }
    }


    private class deleteAsynctasks extends AsyncTask<vehicleentity, Void, Void> {
        vehicledao medodao;

        public deleteAsynctasks(vehicledao medodao) {
            this.medodao = medodao;
        }


        @Override
        protected Void doInBackground(vehicleentity... vehicleentities) {
            medodao.deletealls();
            return null;
        }
    }
}
