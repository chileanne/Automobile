package com.sys.cub360.automobile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sys.cub360.automobile.Fragments.AutoAdvice;
import com.sys.cub360.automobile.Fragments.ContactusFragment;
import com.sys.cub360.automobile.Fragments.Vehiclehistory;
import com.sys.cub360.automobile.Fragments.bookingFragment;
import com.sys.cub360.automobile.Vehicle_history_database.Editprofile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int storage_pemission=23;
    private String name,phoneno,image;
    private FirebaseAuth firebaseAuth;
    private String currentuseruid;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentuser;
   private CircleImageView iimages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        //open up Auto advice fragment
        AutoAdvice mpopularmoviesfragment=new AutoAdvice();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.c, mpopularmoviesfragment).commit();


        //chaging the color of the harmburg icon


        //am gonna get the current user id
        mCurrentuser = FirebaseAuth.getInstance().getCurrentUser();
        currentuseruid = mCurrentuser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuseruid);
        Toast.makeText(getApplicationContext(),currentuseruid,Toast.LENGTH_LONG).show();





        if(isReadStorageAllowed()){
            // Toast.makeText(getApplicationContext(),"permission already approved",Toast.LENGTH_SHORT).show();
        }
        //if the app dosent have the permission ,request for permission
        requestStoragePermission();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView =  navigationView.inflateHeaderView(R.layout.nav_header_home);
        final TextView names=(TextView)hView.findViewById(R.id.namesss);
        iimages =hView.findViewById(R.id.imageViews);
        final CardView mcard=hView.findViewById(R.id.clickedit);
        mcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lets move to the Edit proflie page
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });






        //retrieving all data in real time
       /* ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name = dataSnapshot.child("username").getValue().toString();
                    phoneno = dataSnapshot.child("phone number").getValue().toString().trim();
                   String imagesd = dataSnapshot.child("thumbimage").getValue().toString().trim();


                    //setting values to the views
                    //   mhoobietextview.setText(hobbies);
                    //    moccupationtextview.setText(oocupation);
                    names.setText(name);
                    Glide.with(HomeActivity.this).load(imagesd).placeholder(R.drawable.ava).into(iimages);
                   // Toast.makeText(getApplicationContext(),imagesd,Toast.LENGTH_SHORT).show();

                    //mpl.setVisibility(View.VISIBLE);
                   // mp.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {
            //connected = false;
            Toast.makeText(getApplicationContext(),"Internet Connection turned off",Toast.LENGTH_SHORT).show();
            //mpl.setVisibility(View.GONE);
           // mp.setVisibility(View.VISIBLE);

        }*/




    }


    private Boolean isReadStorageAllowed(){
        //Getting the permission status
        int result= ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        //if permission is granted returning true
        if(result== PackageManager.PERMISSION_GRANTED)
            return  true;
        //if permission not granted return false
        return  false;

    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            //give a reason y
        }
        //ask for permission
        ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},storage_pemission);
    }

    //this method will be called when the user clicks on allow or deny


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == storage_pemission) {
            //if permission is granted
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(getApplicationContext(),"permission approved",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignupActivity.class));
                finish();

            }else {
                //connected = false;
                Toast.makeText(getApplicationContext(),"Internet Connection turned off",Toast.LENGTH_SHORT).show();

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.vh) {
            Vehiclehistory mpopularmoviesfragment=new Vehiclehistory();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.c, mpopularmoviesfragment).commit();
        } else if (id == R.id.ad) {
            AutoAdvice mpopularmoviesfragment=new AutoAdvice();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.c, mpopularmoviesfragment).commit();

        } else if (id == R.id.bk) {
           /*bookingFragment mpopularmoviesfragment=new  bookingFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.c, mpopularmoviesfragment).commit();*/

            startActivity(new Intent(getApplicationContext(), BookingActivity.class));

        } else if (id == R.id.cu) {
            ContactusFragment mpopularmoviesfragment=new ContactusFragment();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.c, mpopularmoviesfragment).commit();


        }else if (id == R.id.pp) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));


        } else if (id == R.id.sh) {
            try {
                Intent sha = new Intent(Intent.ACTION_SEND);
                sha.setType("text/plain");
                sha.putExtra(Intent.EXTRA_SUBJECT, "AutoMan");
                sha.putExtra(Intent.EXTRA_TEXT, "The best app");
                startActivity(Intent.createChooser(sha, "Choose one"));
            }catch (Exception e){

            }


        } else if (id == R.id.ab) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
