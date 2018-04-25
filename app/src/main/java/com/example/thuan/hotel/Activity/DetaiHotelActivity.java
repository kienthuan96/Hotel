package com.example.thuan.hotel.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetaiHotelActivity extends AppCompatActivity {
    TabHost tabHost;
    String id_hotel="-LAqUFdTyh6UXVtnASG9";
    TextView txtTenKS,txtDiaChiKS,txtGiaKS,txtSDTKS;
    ImageView img;
    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Hotel hotel;
    RatingBar ratingBar;

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
    }
}
