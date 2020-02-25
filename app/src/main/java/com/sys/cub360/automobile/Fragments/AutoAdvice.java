package com.sys.cub360.automobile.Fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sys.cub360.automobile.Auto_adivice_database.Autoadapter;
import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;
import com.sys.cub360.automobile.Auto_adivice_database.Autoviewmodel;
import com.sys.cub360.automobile.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AutoAdvice extends android.support.v4.app.Fragment {
    private boolean connected = false;
    private DatabaseReference mentrance;
    private Autoviewmodel mdailymodel;
    private LiveData<List<Autoentity>> Alldata;
    private ProgressBar mp;
    private RecyclerView mrecy;
    private String mdate, mwritername, mtitle,mbody, mthumbimage,madminid;
    private Autoadapter mentranceadapter;


    public AutoAdvice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Current_date= DateFormat.getDateTimeInstance().format(new Date());
       /* SimpleDateFormat dateformts=new SimpleDateFormat("dd/MM/yyyy");
        Current_date=dateformts.format(new Date());
        SimpleDateFormat dateformt=new SimpleDateFormat("hh:mm a");
        time=dateformt.format(new Date());*/
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_auto_advice, container, false);
        mdailymodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(Autoviewmodel.class);
        mp = v.findViewById(R.id.ppsignins);
        mrecy = v.findViewById(R.id.rr);



        mrecy.setLayoutManager(new LinearLayoutManager(getActivity()));

        mrecy.setVisibility(View.GONE);
        mp.setVisibility(View.VISIBLE);

        mentrance = FirebaseDatabase.getInstance().getReference().child("AutoNews");
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            mdailymodel.deletes();
            mentrance.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mdate = dataSnapshot.child("date").getValue().toString();
                    mwritername = dataSnapshot.child("writername").getValue().toString();
                    mtitle = dataSnapshot.child("title").getValue().toString();
                    mbody = dataSnapshot.child("body").getValue().toString();
                    mthumbimage = dataSnapshot.child("thumbimage").getValue().toString();
                    madminid = dataSnapshot.child("adminid").getValue().toString();
                    //mphoneno = dataSnapshot.child("phone number").getValue().toString();


                    mp.setVisibility(View.GONE);
                    mrecy.setVisibility(View.VISIBLE);
                    Autoentity m = new Autoentity(
                            0,mdate, mwritername, mtitle,mbody, mthumbimage,madminid
                    );

                    mdailymodel.inserts(m);


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            connected = false;
            mp.setVisibility(View.GONE);
            mrecy.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Internet Connection is Unavailable", Toast.LENGTH_SHORT).show();
        }
        Alldata = mdailymodel.getAlldata();
        tunde();


        return v;


    }

    private void tunde() {
        Alldata.observe(this, new Observer<List<Autoentity>>() {
            @Override
            public void onChanged(@Nullable List<Autoentity> autoentities) {
                mentranceadapter = new Autoadapter(getActivity(),autoentities);
                mrecy.setAdapter(mentranceadapter);
                mentranceadapter.notifyDataSetChanged();
            }
        });

    }

}
