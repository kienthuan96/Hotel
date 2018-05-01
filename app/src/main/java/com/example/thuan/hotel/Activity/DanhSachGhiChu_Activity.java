package com.example.thuan.hotel.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.thuan.hotel.Adapter.Adapter_GhiChu;
import com.example.thuan.hotel.Helper.Database;
import com.example.thuan.hotel.Model.GhiChu;
import com.example.thuan.hotel.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DanhSachGhiChu_Activity extends AppCompatActivity {
 private   String DATABASE_NAME = "Hotel_NoteOffline.sqlite";
   private  ListView listViewGhiChu;
   SQLiteDatabase database;
   private ArrayList<GhiChu> listGhiChu ;
   private Adapter_GhiChu adapter_GhiChu ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachghichu);
        KhoiTao();
        readData();
    }
    private void KhoiTao(){
        listViewGhiChu = (ListView) findViewById(R.id.listGhiChu);
        listGhiChu = new ArrayList<>();
        adapter_GhiChu = new Adapter_GhiChu(this,listGhiChu);
        listViewGhiChu.setAdapter(adapter_GhiChu);

    }
    private void readData(){
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor =database.rawQuery("SELECT * FROM Hotel_Note",null);
        listGhiChu.clear();
        for(int i = 0;  i<cursor.getCount() ; i++){
            cursor.moveToPosition(i);
            int _id = cursor.getInt(0);
                String _tenGhiChu = cursor.getString(1);
                String _tenKhachSan = cursor.getString(2);
                String _diaChiGhiChu = cursor.getString(5);

                String _date = String.valueOf(cursor.getString(4));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = formatter.parse(_date);
                } catch (ParseException e) {
                e.printStackTrace();
            }
            String _moTaGhiChu = cursor.getString(3);
            byte[] _anhKhachSan = cursor.getBlob(6);
            listGhiChu.add(new GhiChu(_id,_tenGhiChu,_tenKhachSan,_moTaGhiChu,date,_diaChiGhiChu,_anhKhachSan));
        }
        adapter_GhiChu.notifyDataSetChanged();

    }
}
