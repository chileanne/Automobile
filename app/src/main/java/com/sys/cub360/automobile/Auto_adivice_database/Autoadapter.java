package com.sys.cub360.automobile.Auto_adivice_database;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sys.cub360.automobile.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Autoadapter extends RecyclerView.Adapter<Autoadapter.Myholder> {
    private Context mcontext;
    private List<Autoentity> autoentities;
    private DatabaseReference mdatabaseref;
    private String  miimage;

    public Autoadapter(FragmentActivity activity, List<Autoentity> autoentities) {
        mcontext=activity;
        this.autoentities=autoentities;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.autosinglelayout,viewGroup,false);
        return new Autoadapter.Myholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myholder myholder, int i) {
        Autoentity m =autoentities.get(i);
        final String writername=m.getMwritername().toString().trim();
        final String title=m.getMtitle().toString().trim();
        final String body=m.getMbody().toString().trim();
        final String thumb=m.getMthumbimage().toString().trim();
        final String date=m.getMdate().toString().trim();
        final String adminid=m.getMadminid().toString().trim();




        if(mcontext!=null){

            myholder.mwiternme.setText(writername);
            myholder.mbody.setText(body);
            myholder.mtitle.setText(title);
            myholder.md.setText(date);
            Glide.with(mcontext).load(thumb).placeholder(R.drawable.ava).into(myholder.vb);
            Glide.with(mcontext).load(adminid).placeholder(R.drawable.ava).into(myholder.mn);



            myholder.mcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, ReadmoreActivity.class);
                    intent.putExtra("writer",writername);
                    intent.putExtra("title",title);
                    intent.putExtra("body", body);
                    intent.putExtra("image",thumb);
                    intent.putExtra("date",date);
                    intent.putExtra("adinimage",adminid);
                    //intent.putExtra("thirdimages", thirdimage);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(intent);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return autoentities.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        CardView mcard;
        private TextView mtitle,mbody,mwiternme,md;
        private ImageView vb;
        private CircleImageView mn;
        CircleImageView mg;
        public Myholder(@NonNull View v) {
            super(v);
            mn=v.findViewById(R.id.coimage);
            md=v.findViewById(R.id.dda);
            mcard=v.findViewById(R.id.cardclick);
            mtitle=v.findViewById(R.id.tti);
            mbody=v.findViewById(R.id.bd);
            mwiternme=v.findViewById(R.id.writernae);
            vb=v.findViewById(R.id.autoimage);
        }
    }
}
