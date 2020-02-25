package com.sys.cub360.automobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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

public class LoginActivity extends AppCompatActivity {
private EditText memail,mpass;
private CardView clickologin;
private TextView mt;
    private FirebaseAuth firebaseAuth;
    private ProgressBar mp;
    private DatabaseReference mdatabaseref;
    private LinearLayout ml;
    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        memail= findViewById(R.id.emsignin);
        mpass = findViewById(R.id.passignin);
        clickologin = findViewById(R.id.clicksignin);
        mt= findViewById(R.id.forgotsignin);
        mp=findViewById(R.id.ppsignin);
        ml=findViewById(R.id.signuplls);


        //initialize firebaseauth
        firebaseAuth =FirebaseAuth.getInstance();

        mdatabaseref= FirebaseDatabase.getInstance().getReference().child("Users");

        clickologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =  memail.getText().toString().trim();
                String password = mpass.getText().toString().trim();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    mp.setVisibility(View.VISIBLE);
                    ml.setVisibility(View.GONE);
                    loginuser(email,password);
                }

            }
        });

    }

    private void loginuser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    FirebaseUser muser=FirebaseAuth.getInstance().getCurrentUser();
                    final String uid=muser.getUid();
                    String token_id= FirebaseInstanceId.getInstance().getToken();
                    HashMap usermaps=new HashMap<>();
                    usermaps.put("devicetoken",token_id);
                    mdatabaseref.child(uid).updateChildren(usermaps).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mp.setVisibility(View.INVISIBLE);
                                // progressDialog.dismiss();

                                Intent main_intent=new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(main_intent);
                                finish();
                            }else{
                                String m=task.getException().getMessage().toString();
                                mp.setVisibility(View.INVISIBLE);
                                ml.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this,m,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}
