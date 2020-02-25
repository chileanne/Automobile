package com.sys.cub360.automobile.Vehicle_history_database;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sys.cub360.automobile.Auto_adivice_database.Autoadapter;
import com.sys.cub360.automobile.Auto_adivice_database.Autoentity;
import com.sys.cub360.automobile.Auto_adivice_database.ReadmoreActivity;
import com.sys.cub360.automobile.R;
import com.sys.cub360.automobile.vehiclereadActivity;

import java.util.List;

public class vehicleadapter extends RecyclerView.Adapter<vehicleadapter.Myholder>{
    private Context mcontext;
    private  List<vehicleentity> vehicleentities;
    public vehicleadapter(FragmentActivity activity, List<vehicleentity> vehicleentities) {
        this.mcontext=activity;
        this.vehicleentities=vehicleentities;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vehiclehistorysinglelayout,viewGroup,false);
        return new  vehicleadapter.Myholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder myholder, int i) {
        vehicleentity m =vehicleentities.get(i);
        final String cost=m.getMcost().toString().trim();
        final String carmodel=m.getMcardmodel().toString().trim();
        final String workername=m.getMworkername().toString().trim();
        final String date=m.getMdate().toString().trim();
        final String summa=m.getMsummary().toString().trim();

        myholder.mdate.setText(date);
        myholder.msum.setText(summa);

        myholder.mcardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, vehiclereadActivity.class);
                intent.putExtra("cost",cost);
                intent.putExtra("carmodel",carmodel);
                intent.putExtra("date", date);
                intent.putExtra("summary",summa);
                intent.putExtra("workersname",workername);
                //intent.putExtra("thirdimages", thirdimage);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return vehicleentities.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView mdate,msum;
        CardView mcardclick;
        public Myholder(@NonNull View v) {
            super(v);
            mcardclick=v.findViewById(R.id.vcard);
            mdate=v.findViewById(R.id.vdate);
            msum=v.findViewById(R.id.vsum);
        }
    }
}
