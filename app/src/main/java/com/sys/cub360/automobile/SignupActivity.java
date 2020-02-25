package com.sys.cub360.automobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private EditText memail,mpass,mname,mphone;
    private ProgressBar pd;
    private CardView msignup;
    private TextView mtxtv;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabaseref,mpostdabaseref;
    private LinearLayout ml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        memail= findViewById(R.id.emsignup);
        mname = findViewById(R.id.fulsignup);
        mpass = findViewById(R.id.passignup);
        pd= findViewById(R.id.ppsignup);
        msignup=findViewById(R.id.clicksignin);
        mtxtv =findViewById(R.id.forgotsignin);
        mphone =findViewById(R.id.phonsignup);
        ml=findViewById(R.id.signupll);


        //intalize firebaseauthobject
        firebaseAuth = FirebaseAuth.getInstance();
        //check if the user is currently logged in if yes go to the profile ativity
        if (firebaseAuth.getCurrentUser() != null) {   //start userinfo activity
            finish();//close current profile
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));


        }

        mtxtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(main_intent);
                finish();

            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_user=mname.getText().toString().trim();
                String email =memail.getText().toString().trim();
                String password =mpass.getText().toString().trim();
                String phone = mphone.getText().toString().trim();

                if(!TextUtils.isEmpty(display_user) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone)){
                   pd.setVisibility(View.VISIBLE);
                   ml.setVisibility(View.GONE);
                    register_user(display_user,email,password,phone);

                }

            }
        });
    }

    private void register_user(final String display_user, final String email, String password, final String phone) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //get firebase user uid
                    FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
                    String uid=currentuser.getUid();

                    //instatiating the databaseref and ading a users and curent user uid node to the rootview
                    mdatabaseref= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    //intitiating another databaseref in order to store data for the posting database
                   // mpostdabaseref=FirebaseDatabase.getInstance().getReference().child("Posts").child(uid);

                    //if this task is successful i want to
                    //add the display user into firebase databse and uses it on the profile activity




                    String token_id= FirebaseInstanceId.getInstance().getToken();

                    //input uservalues into firebase using Hasmap
                    HashMap<String, String> usermap=new HashMap<>();
                    usermap.put("username", display_user);
                    usermap.put("online", "default");
                    usermap.put("devicetoken",token_id);
                    usermap.put("userid", uid);
                    usermap.put("image", "default");
                    usermap.put("thumbimage","default");
                    usermap.put("email",email);
                    usermap.put("phone number",phone);


                    //progressDialog.dismiss();
                    //setting the inputed values and checking of the task is succesful
                    mdatabaseref.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pd.setVisibility(View.GONE);
                                ml.setVisibility(View.VISIBLE);
                                Intent main_intent=new Intent(SignupActivity.this,HomeActivity.class);
                                startActivity(main_intent);
                                finish();
                            }
                        }
                    });

                }
                else{
                    String m=task.getException().getMessage().toString();
                    pd.setVisibility(View.GONE);
                    ml.setVisibility(View.VISIBLE);
                    Toast.makeText(SignupActivity.this,m,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
