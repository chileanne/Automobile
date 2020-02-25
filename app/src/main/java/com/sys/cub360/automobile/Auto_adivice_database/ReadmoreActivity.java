package com.sys.cub360.automobile.Auto_adivice_database;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sys.cub360.automobile.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReadmoreActivity extends AppCompatActivity {
    private TextView body,name,date,title;
    private ImageView mimage;
    private String mdate, mwritername, mtitle,mbody, mthumbimage,madminimage;
    private CircleImageView mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readmore);

        mdate=getIntent().getStringExtra("date");
        mwritername=getIntent().getStringExtra("writer");
        mtitle=getIntent().getStringExtra("title");
        mbody=getIntent().getStringExtra("body");
        mthumbimage=getIntent().getStringExtra("image");
        madminimage=getIntent().getStringExtra("adinimage");

        body=findViewById(R.id.bd);
        mimage=findViewById(R.id.df);
        mc=findViewById(R.id.coimage);
        name=findViewById(R.id.writernae);
        date=findViewById(R.id.dda);
        title=findViewById(R.id.bdtitle);


        title.setText(mtitle);
        body.setText(mbody);
        name.setText(mwritername);
        date.setText(mdate);
        Glide.with(this).load(mthumbimage).placeholder(R.drawable.csds).into(mimage);
        Glide.with(this).load(madminimage).placeholder(R.drawable.csds).into(mc);


      /*  Typeface roboto = Typeface.createFromAsset(this.getAssets(),
                "font/roboto-Bold.ttf"); //use this.getAssets if you are calling from an Activity
        body.setTypeface(roboto);*/


    }
}
