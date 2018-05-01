package com.example.thuan.hotel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.thuan.hotel.R;

public class MenuGhiChuActivity extends AppCompatActivity {
    private Button themGhiChu ;
    private Button xemDSGhiChu;
    private Button ThoatGhiChu ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menughichu);
    loadData();
        themGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGhiChu();
            }
        });
        xemDSGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xemDSGhiChu();
            }
        });
        ThoatGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoatGhiChu();
            }
        });
    }


    private void loadData(){
        themGhiChu = (Button)findViewById(R.id.themGhiChu);
        xemDSGhiChu =  (Button) findViewById(R.id.xemGhiChu);
        ThoatGhiChu = (Button) findViewById(R.id.thoatGhiChu);
    }
    private void thoatGhiChu(){
        Intent intent = new Intent(MenuGhiChuActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void themGhiChu(){
        Intent intent = new Intent(MenuGhiChuActivity.this, ThemGhiChu_Activity.class);
        startActivity(intent);
    }
    private void xemDSGhiChu(){
        Intent intent = new Intent(MenuGhiChuActivity.this, DanhSachGhiChu_Activity.class);
        startActivity(intent);
    }
}
