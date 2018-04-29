package com.example.thuan.hotel.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thuan.hotel.Adapter.Adapter_Image_Hotel;
import com.example.thuan.hotel.Helper.Database;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.Model.Service;
import com.example.thuan.hotel.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetaiHotelActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    final String DATABASE_NAME = "database.sqlite";
    SQLiteDatabase databaseSQL;
    TabHost tabHost;
    String id_hotel;

    ImageView imgWifi,imgBar,imgRestaurant,imgSwimmingPool,imgPet;
    TextView txtTenKS,txtDiaChiKS,txtGiaKS,txtSDTKS;
    ImageView img;
    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Hotel hotel;
    RatingBar ratingBar;
    FloatingActionButton clickFavorite;
    ArrayList<String> arrayListHinhAnh;
    ListView lstHinhAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detai_hotel);

        // INIT
        Intent intent = this.getIntent();
//        if(intent !=null) {
//
            Bundle bundle = intent.getBundleExtra("goi");
            id_hotel = bundle.getString("id");
//        }
//        else {
//            id_hotel = "-LAqUFdTyh6UXVtnASG9";
//        }

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

        TabHost.TabSpec tab3=tabHost.newTabSpec("t3");
        tab3.setIndicator("Hình ảnh");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

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
//                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/hotel-793b0.appspot.com/o/IMG_CONTACT%2F"
//                        +hotel.getImg1()+"?alt=media&token=d5f61a15-07d0-4f70-8ed8-0fa389da9e52").into(img);

//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageRef = storage.getReference();
//                StorageReference pathReference = storageRef.child("IMG_CONTACT/"+hotel.getImg1());
//                Glide.with(DetaiHotelActivity.this).using(new FirebaseImageLoader()).load(pathReference).into(img);

                Service service=hotel.getService();
                if(service.getWifi())   imgWifi.setVisibility(View.VISIBLE); else imgWifi.setVisibility(View.INVISIBLE);
                if(service.getPet())   imgPet.setVisibility(View.VISIBLE); else imgPet.setVisibility(View.INVISIBLE);
                if(service.getRestaurant())   imgRestaurant.setVisibility(View.VISIBLE); else imgRestaurant.setVisibility(View.INVISIBLE);
                if(service.getSwimmingPool())   imgSwimmingPool.setVisibility(View.VISIBLE); else imgSwimmingPool.setVisibility(View.INVISIBLE);
                if(service.getWifi())   imgWifi.setVisibility(View.VISIBLE); else imgWifi.setVisibility(View.INVISIBLE);

                arrayListHinhAnh.clear();
                arrayListHinhAnh.add(hotel.getImg1());
                arrayListHinhAnh.add(hotel.getImg2());
                arrayListHinhAnh.add(hotel.getImg3());
                Toast.makeText(DetaiHotelActivity.this, "So luong hinh: "+arrayListHinhAnh.get(2),Toast.LENGTH_SHORT).show();
                Adapter_Image_Hotel adapter_image_hotel=new Adapter_Image_Hotel(DetaiHotelActivity.this,R.layout.layout_item_image,arrayListHinhAnh);
                lstHinhAnh.setAdapter(adapter_image_hotel);
                adapter_image_hotel.notifyDataSetChanged();
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
        imgWifi=findViewById(R.id.imgWifi);
        imgPet=findViewById(R.id.imgPet);
        imgBar=findViewById(R.id.imgBar);
        imgRestaurant=findViewById(R.id.imgRestaurant);
        imgSwimmingPool=findViewById(R.id.imgSwimmingPool);
        arrayListHinhAnh=new ArrayList<>();
        lstHinhAnh=findViewById(R.id.lstImage);
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
