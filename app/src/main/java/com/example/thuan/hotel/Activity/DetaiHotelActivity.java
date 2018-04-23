package com.example.thuan.hotel.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.thuan.hotel.R;

public class DetaiHotelActivity extends AppCompatActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_hotel);

        tabHost.setup();

        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setIndicator("Thông tin");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t1");
        tab2.setIndicator("Nhận xét");
        tab2.setContent(R.id.tab1);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3=tabHost.newTabSpec("t1");
        tab3.setIndicator("Giá");
        tab3.setContent(R.id.tab1);
        tabHost.addTab(tab3);

    }
}
