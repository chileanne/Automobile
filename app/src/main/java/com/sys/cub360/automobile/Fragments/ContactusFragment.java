package com.sys.cub360.automobile.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sys.cub360.automobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactusFragment extends Fragment {
    private CardView mbut;
    private EditText meditext1;
    private EditText medit2,mphone,mname;
    private ProgressDialog mpro;



    public ContactusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_contactus, container, false);
        meditext1=(EditText) v.findViewById(R.id.editText);
        medit2=(EditText) v.findViewById(R.id.editText2);
        mbut= v.findViewById(R.id.button);
        mphone= v.findViewById(R.id.phoneno);
        mname= v.findViewById(R.id.ename);
        mpro= new ProgressDialog(getActivity());


        mbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passigmethod();
            }
        });


        return v;
    }

    private void passigmethod() {

        String email = meditext1.getText().toString().trim();
        String message = medit2.getText().toString().trim();
        String phone = mphone.getText().toString().trim();
        String name = mname.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(message) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(name) ) {
            mpro.setCanceledOnTouchOutside(false);
            mpro.setMessage("please wait.....");
            mpro.show();

            String[] TO= {"anthony4arsenal@gmail.com"};
            Intent emailintent=new Intent(Intent.ACTION_SEND);
            emailintent.setData(Uri.parse("mailto:"));
            emailintent.setType("text/plain");
            emailintent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailintent.putExtra(Intent.EXTRA_SUBJECT, "Auto Man Constumer" );
            emailintent.putExtra(Intent.EXTRA_TEXT, "email:"+email +"\n"+ "name:" +name +"\n" +"phoneno:"+ phone+"\n"+ "Message:" + message);
            // emailintent.putExtra(Intent.EXTRA_TEXT, email +"\n" +name +"\n" + phone+"\n"  + message);

            try {
                startActivity(Intent.createChooser(emailintent,"Sending message....."));
                mpro.dismiss();
                //finish();
            }catch (Exception e){
                Toast.makeText(getActivity(),"you don't have any email client",Toast.LENGTH_LONG).show();
                mpro.dismiss();
            }
        }
        else {
            Toast.makeText(getActivity(),"Empty Fields",Toast.LENGTH_LONG).show();
        }
    }

}
