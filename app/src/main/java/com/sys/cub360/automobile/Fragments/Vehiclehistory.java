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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sys.cub360.automobile.Auto_adivice_database.Autoadapter;
import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;
import com.sys.cub360.automobile.Auto_adivice_database.Autoviewmodel;
import com.sys.cub360.automobile.R;
import com.sys.cub360.automobile.Vehicle_history_database.vehicleadapter;
import com.sys.cub360.automobile.Vehicle_history_database.vehicleentity;
import com.sys.cub360.automobile.Vehicle_history_database.vehicleviewmodel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Vehiclehistory extends android.support.v4.app.Fragment{
    private boolean connected = false;
    private DatabaseReference mentrance,mcheck;
    private vehicleviewmodel mdailymodel;
    private LiveData<List<vehicleentity>> Alldata;
    private ProgressBar mp;
    private RecyclerView mrecy;
    private String mdate, mcost,msummary,mcardmodel,  mworkername;
    private vehicleadapter mentranceadapter;



    public Vehiclehistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_vehiclehistory, container, false);
        mdailymodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(vehicleviewmodel.class);
        mp = v.findViewById(R.id.ppsigninss);
        mrecy = v.findViewById(R.id.rrs);

        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
        String uid=currentuser.getUid();

        mrecy.setLayoutManager(new LinearLayoutManager(getActivity()));

        mrecy.setVisibility(View.GONE);
        mp.setVisibility(View.VISIBLE);

        mentrance = FirebaseDatabase.getInstance().getReference().child("Vehiclehistory").child(uid);
        mcheck = FirebaseDatabase.getInstance().getReference().child("Vehiclehistory").child(uid);




        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            mcheck.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mdailymodel.deletes();
                        mentrance.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                mdate = dataSnapshot.child("date").getValue().toString();
                                mcost = dataSnapshot.child("cost").getValue().toString();
                                msummary = dataSnapshot.child("summary").getValue().toString();
                                mcardmodel = dataSnapshot.child("cardmodel").getValue().toString();
                                mworkername = dataSnapshot.child("workername").getValue().toString();

                                mp.setVisibility(View.GONE);
                                mrecy.setVisibility(View.VISIBLE);
                                vehicleentity m = new vehicleentity(
                                        0, mdate, mcost, msummary, mcardmodel, mworkername
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

                    } else if (!dataSnapshot.exists()) {
                        mp.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No Current Articles", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {
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
        Alldata.observe(this, new Observer<List<vehicleentity>>() {
            @Override
            public void onChanged(@Nullable List<vehicleentity> vehicleentities) {
                mentranceadapter = new vehicleadapter(getActivity(),vehicleentities);
                mrecy.setAdapter(mentranceadapter);
                mentranceadapter.notifyDataSetChanged();
            }
        });

    }

}
