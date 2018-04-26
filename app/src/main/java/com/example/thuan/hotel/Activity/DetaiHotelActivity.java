package com.example.thuan.hotel.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuan.hotel.Helper.Database;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetaiHotelActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    final String DATABASE_NAME = "database.sqlite";
    SQLiteDatabase databaseSQL;
    TabHost tabHost;
    Intent intent = getIntent();
    Bundle bundle = intent.getBundleExtra("goi");
    String id_hotel = bundle.getString("id");
    TextView txtTenKS,txtDiaChiKS,txtGiaKS,txtSDTKS;
    ImageView img;
    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Hotel hotel;
    RatingBar ratingBar;
    FloatingActionButton clickFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_hotel);
        hotel=new Hotel();

        myRef = database.getReference("hotel").child(id_hotel);
        id();

        tabHost.setup();

        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setIndicator("Thông tin");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setIndicator("Nhận xét");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        Toast.makeText(DetaiHotelActivity.this,"Thanh Cong",Toast.LENGTH_SHORT).show();

        load();
        //Toast.makeText(DetaiHotelActivity.this,"Load",Toast.LENGTH_SHORT).show();
        clickFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventFavorite();
            }
        });
    }

    private void load(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hotel= dataSnapshot.getValue(Hotel.class);
                txtTenKS.setText(hotel.getName());
                txtSDTKS.setText(hotel.getNumberPhone()+"");
                txtGiaKS.setText(hotel.getPrice()+"");
                txtDiaChiKS.setText(hotel.getAddress());
                ratingBar.setRating(Float.parseFloat(hotel.getStars().toString()));
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/hotel-793b0.appspot.com/o/IMG_CONTACT%2F"
                        +hotel.getImg1()+"?alt=media&token=d5f61a15-07d0-4f70-8ed8-0fa389da9e52").into(img);
                Toast.makeText(DetaiHotelActivity.this,"Thanh cong",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetaiHotelActivity.this,"That bai",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void id(){
        tabHost=findViewById(R.id.tabhost);
        txtTenKS=findViewById(R.id.txtTenKS);
        txtSDTKS=findViewById(R.id.txtSDTKS);
        txtDiaChiKS=findViewById(R.id.txtDiaChiKS);
        txtGiaKS=findViewById(R.id.txtGiaKS);
        img=findViewById(R.id.imgHotel);
        ratingBar=findViewById(R.id.rbKS);
        clickFavorite=findViewById(R.id.clickFavorite);
    }

    private void addEventFavorite() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user.getUid());
        contentValues.put("hotel_id", id_hotel);

        databaseSQL = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = databaseSQL.rawQuery("SELECT * FROM favorite where user_id = '" + user.getUid()  +"' AND hotel_id = '"+ id_hotel +"'",null);
        if(cursor.getCount() != 0) {
            Toast.makeText(DetaiHotelActivity.this, "Khách sạn này đã thêm vào danh sách yêu thích của bạn", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseSQL.insert("favorite",null, contentValues);
            Toast.makeText(DetaiHotelActivity.this, "Thêm vào danh sách yêu thích thành công", Toast.LENGTH_SHORT).show();
        }
    }
}
