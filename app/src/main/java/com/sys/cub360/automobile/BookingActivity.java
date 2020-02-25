package com.sys.cub360.automobile;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.sys.cub360.automobile.Bookings.bookingentity;
import com.sys.cub360.automobile.Bookings.bookingviewmodel;
import com.sys.cub360.automobile.Vehicle_history_database.VehiclereadmoreActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.exceptions.ExpiredAccessCodeException;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
    private LinearLayout bb,bbb,bbbb,bbg,paymentl;
    private  String work="";
    private Toolbar mtoolbar;
    private  Date date;
    private Card card;
    private Charge charge;
    private EditText cardnos, months, years, cvs, amounts,name, email;
    private CardView mbtn;
    private int expirymonth, expiryyears, money;
    private ProgressDialog mps;


    String[] bankNames={"Servicing(Oil Change)","Alignment","Mercedes","Wheel balancing(car)","Wheel balancing(jeep)"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mtoolbar = findViewById(R.id.toolbarapps);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mps=new ProgressDialog(this);

        date=new Date();

        mdailymodel = ViewModelProviders.of(this).get(bookingviewmodel.class);
        mavailable=findViewById(R.id.avaialbe);
        ddate=findViewById(R.id.tttime);
        price=findViewById(R.id.pprice);
        mp=findViewById(R.id.ppsignupd);
        click=findViewById(R.id.clicksignind);
        bb=findViewById(R.id.ll);
        bbb=findViewById(R.id.lll);
        bbbb=findViewById(R.id.llld);
        bbg=findViewById(R.id.llll);
        mspinner = findViewById(R.id.spinning);
        paymentl=findViewById(R.id.lkjl);
        mbtn = findViewById(R.id.trpay);
        name =findViewById(R.id.trname);
        cardnos = findViewById(R.id.trcardnumber);
        months =findViewById(R.id.trmonth);
        years = findViewById(R.id.tryr);
        cvs = findViewById(R.id.trcvv);
        email = findViewById(R.id.tremail);


        mp.setVisibility(View.VISIBLE);
        mspinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), R.layout.spinner_list, bankNames);
        aa.setDropDownViewResource(R.layout.spinner_list);
        mspinner.setAdapter(aa);


       // order();

        msend= FirebaseDatabase.getInstance().getReference().child("AdminBook");

        mentrance = FirebaseDatabase.getInstance().getReference().child("Book");
        DatabaseReference user_message_push = msend.push();
        push_id = user_message_push.getKey();



        //if the date is before 5pm get this data
        SimpleDateFormat dateformt=new SimpleDateFormat("hh:mm a");
        String m=dateformt.format(new Date());
        try {
            //if the time is before 5.00pm
            if(dateformt.parse(dateformt.format(date)).before(dateformt.parse("03:20 p.m."))){
                Toast.makeText(getApplicationContext(),"current time is before 1pm",Toast.LENGTH_SHORT).show();
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
                                //get the current date of the present date
                                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
                                String dates = DATE_FORMAT.format(date);

                                // Toast.makeText(getActivity(),mtime_of_fix,Toast.LENGTH_LONG).show();
                                mavailable.setText(mtime_of_fix);
                                ddate.setText(mpit);
                                mp.setVisibility(View.GONE);
                                bb.setVisibility(View.VISIBLE);
                                click.setVisibility(View.VISIBLE);
                                sacrifice(mcurrentdate,mtaken,mtime_endfix,mtime_of_fix ,mname,mphoneno,mpit,mprice,mtimeline,dates);
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
                    Toast.makeText(getApplicationContext(), "Internet Connection is Unavailable", Toast.LENGTH_SHORT).show();
                }




            }else {
                //if the time is after  5.00pm
                //Toast.makeText(getApplicationContext(),"current time is after 5pm",Toast.LENGTH_SHORT).show();
                final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
                final String datesdd = DATE_FORMAT.format(date);
                //call the next date method and return a string
                nextdate(datesdd);
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
                                String dates = nextdate(datesdd);
                                mavailable.setText(mtime_of_fix);
                                ddate.setText(mpit);
                                mp.setVisibility(View.GONE);
                                bb.setVisibility(View.VISIBLE);
                                click.setVisibility(View.VISIBLE);
                                sacrifice(mcurrentdate,mtaken,mtime_endfix,mtime_of_fix ,mname,mphoneno,mpit,mprice,mtimeline, dates);
                            }

                            //it means there is no avialable slot
                            //so check back by 5:00pm
                            if (mavailable.getText().toString().equals("")) {
                                mp.setVisibility(View.GONE);
                                bb.setVisibility(View.GONE);
                                bbbb.setVisibility(View.VISIBLE);
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
                    Toast.makeText(getApplicationContext(), "Internet Connection is Unavailable", Toast.LENGTH_SHORT).show();
                }



            }
        } catch (ParseException e) {
            e.printStackTrace();
        }







    }


    private void sacrifice(String mcurrentdate, String mtaken, final String mtime_endfix, final String mtime_of_fix, String mname, final String mphoneno, final String mpit, final String mprice, final String mtimeline, final String dates) {

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mupdate= FirebaseDatabase.getInstance().getReference().child("Book");
                if(prices!=null) {
                    mp.setVisibility(View.VISIBLE);
                    bb.setVisibility(View.GONE);
                    //lets check if the given spot is still free
                    mentrance.child(mtimeline).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String mtakenss = dataSnapshot.child("Taken").getValue().toString();
                            if(mtakenss.equals("NO")){
                                work="YES";
                                //okay the given spot is still available add map into admin book
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
                                            //make payment to paystack
                                            mp.setVisibility(View.GONE);
                                            //open payment ui
                                            paymentl.setVisibility(View.VISIBLE);
                                            //colect input fields for payment
                                            mbtn.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View view) {


                                            String cardno = cardnos.getText().toString().trim();
                                            String monnths = months.getText().toString().trim();
                                            String yearss = years.getText().toString().trim();
                                            String cvss = cvs.getText().toString().trim();
                                            String cardname = name.getText().toString().trim();
                                            String emails = email.getText().toString().trim();
                                            //check if non of the fields are empty
                                            if (!TextUtils.isEmpty(emails)&& !TextUtils.isEmpty(cardname) && !TextUtils.isEmpty(cardno) && !TextUtils.isEmpty(monnths) && !TextUtils.isEmpty(yearss) && !TextUtils.isEmpty(cvss) && !TextUtils.isEmpty(prices)) {
                                                expirymonth = Integer.parseInt(monnths);
                                                expiryyears = Integer.parseInt(yearss);
                                                money = Integer.parseInt(prices) * 100;
                                                card = new Card(cardno, expirymonth, expiryyears, cvss);

                                                mps.setMessage("Processing...");
                                                mps.setTitle("AutoMan Payment");
                                                mps.show();
                                                mps.setCanceledOnTouchOutside(false);
                                                 mps.setCancelable(false);

                                                //Toast.makeText(getActivity(), "All fields", Toast.LENGTH_LONG).show();
                                                if (card.isValid()) {
                                                    Toast.makeText(getApplicationContext(), "This Card is valid", Toast.LENGTH_LONG).show();
                                                    money = Integer.parseInt(prices) * 100;
                                                    //create a Charge object
                                                    charge = new Charge();
                                                    //set the card to charge
                                                    charge.setCard(card);
                                                    charge.setEmail(emails);
                                                    charge.setAmount(money); //test amount
                                                    charge.setReference("Automan" + "\n" + "name:" + cardname + "\n" + "payments for:" + ccheck + "\n" + "Pit Given:" + mpit + "\n" + "Time of Fix:" + mtime_of_fix);

                                                    PaystackSdk.chargeCard(BookingActivity.this, charge, new Paystack.TransactionCallback() {
                                                        @Override
                                                        public void onSuccess(Transaction transaction) {
                                                            // This is called only after transaction is deemed successful.
                                                            // Retrieve the transaction, and send its reference to your server
                                                            // for verification.
                                                            mps.dismiss();
                                                            String paymentReference = transaction.getReference();
                                                            Toast.makeText(getApplicationContext(), "Transaction Successful! payment reference: "
                                                                    + paymentReference, Toast.LENGTH_LONG).show();

                                                            //mcheck.setVisibility(View.VISIBLE);
                                                            //msuc.setText("Transaction Successful");


                                                            //writing to admin records
                                                            Map updatedd = new HashMap();
                                                            updatedd.put("Currentdate",dates);
                                                            updatedd.put("Taken", "YES");
                                                            updatedd.put("Time_endfix", mtime_of_fix);
                                                            updatedd.put("Time_of_fix", mtime_endfix);
                                                            updatedd.put("userid", mphoneno);
                                                            updatedd.put("pit", mpit);
                                                            updatedd.put("price", prices);
                                                            updatedd.put("timeline", mtimeline);
                                                            updatedd.put("payment", "successful");
                                                            // update.put("userid",name);

                                                            Map bd = new HashMap();
                                                            bd.put("/" + push_id, updatedd);
                                                            msend.updateChildren(bd).addOnCompleteListener(new OnCompleteListener() {
                                                                @Override
                                                                public void onComplete(@NonNull Task task) {
                                                                    if (task.isSuccessful()) {
                                                                        mps.dismiss();
                                                                        startActivity(new Intent(getApplicationContext(), VehiclereadmoreActivity.class));
                                                                        finish();

                                                                    }
                                                                }
                                                            });


                                                        }

                                                        @Override
                                                        public void beforeValidate(Transaction transaction) {
                                                            // This is called only before requesting OTP.
                                                            // Save reference so you may send to server. If
                                                            // error occurs with OTP, you should still verify on server.
                                                        }

                                                        @Override
                                                        public void onError(Throwable error, Transaction transaction) {
                                                            //handle error here
                                                            mps.dismiss();
                                                            if (error instanceof ExpiredAccessCodeException) {
                                                                return;
                                                            }

                                                            if (transaction != null) {
                                                                Toast.makeText(getApplicationContext(), "Transaction Error", Toast.LENGTH_LONG).show();
                                                               // mcheck.setVisibility(View.VISIBLE);
                                                               // msuc.setText("Transaction Failed");
                                                            } else {
                                                                //mcheck.setVisibility(View.VISIBLE);
                                                              //  msuc.setText("Transaction Failed");
                                                                Toast.makeText(getApplicationContext(), "Transaction Failed"
                                                                        , Toast.LENGTH_LONG).show();
                                                            }

                                                        }
                                                    });

                                                }else{
                                                    mps.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Invalid Card", Toast.LENGTH_LONG).show();

                                                }


                                            } else {
                                                mps.dismiss();
                                                Toast.makeText(getApplicationContext(), "Empty Field", Toast.LENGTH_LONG).show();
                                            }

                                                }
                                            });

                                        }

                                    }
                                });//


                            }
                            if(work.equals("")){
                                bbg.setVisibility(View.VISIBLE);
                                bb.setVisibility(View.GONE);
                                click.setEnabled(false);
                                Toast.makeText(getApplicationContext(),"Spot was just Taken,Please Reopen Bookings Page",Toast.LENGTH_LONG).show();
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




    //check for time
    private void order() {

        //getting the time

        SimpleDateFormat dateformt=new SimpleDateFormat("hh:mm a");
        // dateformt.format(date);
        String m=dateformt.format(new Date());
        // Toast.makeText(getApplicationContext(),m,Toast.LENGTH_SHORT).show();

        try {
            //if the time is before 5.00pm
            if(dateformt.parse(dateformt.format(date)).before(dateformt.parse("02:00 p.m."))){
                Toast.makeText(getApplicationContext(),"current time is before 1pm",Toast.LENGTH_SHORT).show();



            }else {
                //if the time is after  5.00pm
                Toast.makeText(getApplicationContext(),"current time is after 1pm",Toast.LENGTH_SHORT).show();
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
                String dates = DATE_FORMAT.format(date);
                Toast.makeText(getApplicationContext(),dates,Toast.LENGTH_SHORT).show();
                nextdate(dates);


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    private String nextdate(String dates) {
        String nextDate;
        try {
            Calendar today = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date datedd = format.parse(dates);
            today.setTime(datedd);
            today.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = format.format(today.getTime());
           // Toast.makeText(getApplicationContext(),nextDate,Toast.LENGTH_SHORT).show();
            return nextDate;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            return "error";
            //return nextDate;
        }
        //return nextDate;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), bankNames[i], Toast.LENGTH_LONG).show();
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
