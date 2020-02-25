package com.sys.cub360.automobile.Vehicle_history_database;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sys.cub360.automobile.R;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    private EditText mname,mphone;
    private String mmname,mmphone;
    private DatabaseReference muploadDatabase;
    private FirebaseUser mcurrentuser;
    private ProgressDialog mprogress;
    private  String image,thumbimage;
    private DatabaseReference  mdatabasereference;
    private CardView muploadbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        mmname=getIntent().getStringExtra("name");
        mmphone=getIntent().getStringExtra("phone");

        mcurrentuser= FirebaseAuth.getInstance().getCurrentUser();
        String userid=mcurrentuser.getUid();
        muploadDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        mdatabasereference=FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        mname=findViewById(R.id.emsignins);
        mphone=findViewById(R.id.passignins);
        muploadbtn=findViewById(R.id.clicksignin);

        //saving info to databse
        muploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = mname.getText().toString();
                String phoneno = mphone.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneno)) {

                    //input uservalues into firebase using Hasmap
                    Map usermap = new HashMap<>();
                    usermap.put("username", name);
                    usermap.put("phone number", phoneno);


                    //initalizing progressdialog
                    mprogress = new ProgressDialog(Editprofile.this);
                    mprogress.setTitle("updating changes");
                    mprogress.setMessage("Loading.......");
                    mprogress.show();


                    muploadDatabase.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mprogress.dismiss();
                                Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_LONG);

                            } else {
                                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG);
                                mprogress.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}
