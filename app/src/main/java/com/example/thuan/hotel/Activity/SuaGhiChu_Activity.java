package com.example.thuan.hotel.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.thuan.hotel.Helper.Database;
import com.example.thuan.hotel.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuaGhiChu_Activity extends AppCompatActivity {
    private   String DATABASE_NAME = "Hotel_NoteOffline.sqlite";

    private Button  btnSuaChonAnh ,btnSuaChupAnh , btnSua,btnThoat ;
    private ImageView  imgitemAnh ;
    private EditText itemTenGhiChu,itemTenKhachSan , itemMotaGhiChu ,itemDiaChiKhachSan ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suaghichu);
        loadData();
        getData();


        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Thoat = new Intent(SuaGhiChu_Activity.this, DanhSachGhiChu_Activity.class);
                startActivity(intent_Thoat);
            }
        });
    }
    private void loadData(){
        btnSuaChonAnh = (Button) findViewById(R.id.btnSuaChonAnh);
        btnSuaChupAnh = (Button) findViewById(R.id.btnSuaChupAnh);
        btnSua = (Button) findViewById(R.id.btnSuaUpdateGhiChu);
        btnThoat = (Button) findViewById(R.id.btnSuaThoatGhiChu);

        imgitemAnh =(ImageView) findViewById(R.id.itemSuaAnh);
        itemTenGhiChu = (EditText) findViewById(R.id.itemSuaTenGhiChu);
        itemTenKhachSan = (EditText) findViewById(R.id.itemSuaTenKhachSan);
        itemMotaGhiChu = (EditText) findViewById(R.id.itemSuaNhanXetKhachSan);
        itemDiaChiKhachSan = (EditText) findViewById(R.id.itemSuaDiaChiKhachSan);


    }
    private void getData(){
        Intent intent  = getIntent();
        int id = intent.getIntExtra("idNote" , -1);
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =database.rawQuery("SELECT * FROM Hotel_Note where idNote = ?",new String[]{id+""});
        cursor.moveToFirst();
        String _tenGhiChu = cursor.getString(1);
        String _tenKhachSan = cursor.getString(2);
        String _diaChiGhiChu = cursor.getString(5);
        String _moTaGhiChu = cursor.getString(3);
        byte[] _anhKhachSan = cursor.getBlob(6);
        Bitmap bitmap = BitmapFactory.decodeByteArray(_anhKhachSan,0 ,_anhKhachSan.length);
        imgitemAnh.setImageBitmap(bitmap);
        //Ánh xạ lên giao diện
        itemTenGhiChu.setText(_tenGhiChu);
        itemTenKhachSan.setText(_tenKhachSan);
        itemMotaGhiChu.setText(_moTaGhiChu);
        itemDiaChiKhachSan.setText(_diaChiGhiChu);


    }
}
