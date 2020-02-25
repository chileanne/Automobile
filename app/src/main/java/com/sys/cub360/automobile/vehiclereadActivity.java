package com.sys.cub360.automobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class vehiclereadActivity extends AppCompatActivity {
    private String mcost,mcarmodel,mdate,msummary,mworkername;
    private TextView cost,carmodel,date,summary,workername;
    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleread);

        mtoolbar = findViewById(R.id.toolbarapp);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mcost=getIntent().getStringExtra("cost");
        mcarmodel=getIntent().getStringExtra("carmode");
        mdate=getIntent().getStringExtra("date");
        msummary=getIntent().getStringExtra("summary");
        mworkername=getIntent().getStringExtra("workersname");


        cost=findViewById(R.id.vhcost);
        carmodel=findViewById(R.id.vhcar);
        date=findViewById(R.id.vhdate);
        summary=findViewById(R.id.vhsummary);
        workername=findViewById(R.id.vhname);

        cost.setText(mcost);
        carmodel.setText(mcarmodel);
        date.setText(mdate);
        summary.setText(msummary);
        workername.setText(mworkername);
    }
}
