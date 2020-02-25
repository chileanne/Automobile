package com.sys.cub360.automobile.Fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;
import com.sys.cub360.automobile.Auto_adivice_database.Autoviewmodel;
import com.sys.cub360.automobile.Bookings.bookingentity;
import com.sys.cub360.automobile.Bookings.bookingviewmodel;
import com.sys.cub360.automobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class bookingFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    private boolean connected = false;
    private DatabaseReference mentrance,msend,mupdate;
    private bookingviewmodel mdailymodel;
    private LiveData<List<bookingentity>> Alldata;
    private ProgressBar mp;
    private String mcurrentdate,mtaken,mtime_endfix,mtime_of_fix ,mname,mphoneno,mpit,mprice,mtimeline;
    private TextView mavailable,ddate,price;
    private CardView click;
    private Spinner mspinner;
    private String ccheck,push_id;
    private String prices = null;
    private LinearLayout bb,bbb,bbbb;
    private  String work="";
    private Toolbar mtoolbar;

    String[] bankNames={"Servicing(Oil Change)","Alignment","Mercedes","Wheel balancing(car)","Wheel balancing(jeep)"};


    public bookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //check the time

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_booking, container, false);
        mdailymodel = ViewModelProviders.of((FragmentActivity) getActivity()).get(bookingviewmodel.class);
        mavailable=v.findViewById(R.id.avaialbe);
        ddate=v.findViewById(R.id.tttime);
        price=v.findViewById(R.id.pprice);
        mp=v.findViewById(R.id.ppsignupd);
        click=v.findViewById(R.id.clicksignind);
        bb=v.findViewById(R.id.ll);
        bbb=v.findViewById(R.id.lll);
        mspinner = v.findViewById(R.id.spinning);



        mp.setVisibility(View.VISIBLE);
        mspinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(aa);

        order();


        msend= FirebaseDatabase.getInstance().getReference().child("AdminBook");
        DatabaseReference user_message_push = msend.push();
        push_id = user_message_push.getKey();
        mentrance = FirebaseDatabase.getInstance().getReference().child("Book");
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            mdailymodel.deletes();
            mentrance.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mcurrentdate = dataSnapshot.child("Currentdate").getValue().toString();
                    mtaken = dataSnapshot.child("Taken").getValue().toString();
                    mtime_endfix = dataSnapshot.child("Time_endfix").getValue().toString();
                    mtime_of_fix = dataSnapshot.child("Time_of_fix").getValue().toString();
                    mname = dataSnapshot.child("name").getValue().toString();
                    mphoneno = dataSnapshot.child("userid").getValue().toString();
                    mpit= dataSnapshot.child("pit").getValue().toString();
                    mprice = dataSnapshot.child("price").getValue().toString();
                   mtimeline= dataSnapshot.child("timeline").getValue().toString();
                    String check="check";
                    check=mtaken;


                    //select the non taken space and display
                    if(mtaken.equals("NO")){
                       // Toast.makeText(getActivity(),mtime_of_fix,Toast.LENGTH_LONG).show();
                        mavailable.setText(mtime_of_fix);
                        ddate.setText(mpit);
                        mp.setVisibility(View.GONE);
                        bb.setVisibility(View.VISIBLE);
                        click.setVisibility(View.VISIBLE);
                        sacrifice(mcurrentdate,mtaken,mtime_endfix,mtime_of_fix ,mname,mphoneno,mpit,mprice,mtimeline);
                    }

                    //it means there is no avialable slot
                    //so check back by 5:00pm
                    if (mavailable.getText().toString().equals("")) {
                        mp.setVisibility(View.GONE);
                        bb.setVisibility(View.GONE);
                        bbb.setVisibility(View.VISIBLE);
                       //click.setVisibility(View.GONE);
                    }






                    //of no use to me
                    mp.setVisibility(View.GONE);
                  bookingentity md = new bookingentity(
                            0, mcurrentdate,mtaken,mtime_endfix,mtime_of_fix ,mname,mphoneno,mpit,mprice
                    );

                    mdailymodel.inserts(md);
                    String taken="NO";
                    Alldata = mdailymodel.getAlldata();
                   // tunde();


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
            Toast.makeText(getActivity(), "Internet Connection is Unavailable", Toast.LENGTH_SHORT).show();
        }






        return v;
    }

    private void sacrifice(String mcurrentdate, String mtaken, final String mtime_endfix, final String mtime_of_fix, String mname, final String mphoneno, final String mpit, final String mprice, final String mtimeline) {

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mupdate= FirebaseDatabase.getInstance().getReference().child("Book");
                if(prices!=null) {
                    //lets check if the given spot is still free
                    mentrance.child(mtimeline).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String mtakenss = dataSnapshot.child("Taken").getValue().toString();
                            if(mtakenss.equals("NO")){
                                work="YES";
                                //okay add map into admin book

                                //get timeline and set it to the ref
                                HashMap update=new HashMap();
                                update.put("Taken","YES");
                                update.put("Currentdate","");
                                update.put("Time_endfix",mtime_of_fix);
                                update.put("Time_of_fix",mtime_endfix);
                                update.put("userid",mphoneno);
                                update.put("pit",mpit);
                                update.put("price",mprice);
                                update.put("timeline",mtimeline);
                                // update.put("userid",name);

                                mupdate.child(mtimeline).updateChildren(update).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){


                                            //writing to admin records
                                            Map updatedd = new HashMap();
                                            updatedd.put("Taken", "YES");
                                            updatedd.put("Time_endfix", mtime_of_fix);
                                            updatedd.put("Time_of_fix", mtime_endfix);
                                            updatedd.put("userid", mphoneno);
                                            updatedd.put("pit", mpit);
                                            updatedd.put("price", prices);
                                            updatedd.put("timeline", mtimeline);
                                            // update.put("userid",name);

                                            Map bd = new HashMap();
                                            bd.put("/" + push_id, updatedd);
                                            msend.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        //make payment
                                                    }
                                                }
                                            });
                                        }

                                    }
                                });//


                            }
                            if(work.equals("")){
                                click.setEnabled(false);
                                Toast.makeText(getActivity(),"Spot Taken,Please Reopen Bookings Page",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }//price null

            }
        });

    }


    private void order() {

        //getting the time
        Date date=new Date();
        SimpleDateFormat dateformt=new SimpleDateFormat("hh:mm a");
        // dateformt.format(date);
        String m=dateformt.format(new Date());
        // Toast.makeText(getApplicationContext(),m,Toast.LENGTH_SHORT).show();

        try {
            //if the time is before 5.00pm
            if(dateformt.parse(dateformt.format(date)).before(dateformt.parse("01:00 p.m."))){
                Toast.makeText(getActivity(),"greater",Toast.LENGTH_SHORT).show();



            }else {
                //if the time is after  5.00pm
                Toast.makeText(getActivity(),"less",Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), bankNames[i], Toast.LENGTH_LONG).show();
        String m=adapterView.getItemAtPosition(i).toString();
        ccheck=m;

        if(ccheck.equals("Servicing(Oil Change)")){
            prices="1500";
            price.setText(prices);

        }else if(ccheck.equals("Alignment")){
            prices="1000";
            price.setText(prices);
        }else if(ccheck.equals("Mercedes")){
            prices="2000";
            price.setText(prices);
        }else if(ccheck.equals("Wheel balancing(car)")){
            prices="3000";
            price.setText(prices);
        }else if(ccheck.equals("Wheel balancing(jeep)")){
        prices="4000";
        price.setText(prices);
    }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
