package com.example.thuan.hotel.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thuan.hotel.Adapter.Adapter_Favorite_Hotel;
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

import java.util.ArrayList;

public class FavoriteHotelActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ArrayList<String> arrHotelID;
    final String DATABASE_NAME = "database.sqlite";
    SQLiteDatabase databaseSQL;
    ListView lvFavorite;
    ArrayList<Hotel> arrayList;
    Adapter_Favorite_Hotel adapter_hotel;
    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String hotelID;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        // Check login
        //checkLogin(user);

        myRef = database.getReference("hotel");
        init();

        readData(user);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                adapter_hotel.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Hotel hotel=dataSnapshot.getValue(Hotel.class);
//                arrayList.add(hotel);
//                adapter_hotel.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        //event();
    }

    private void init(){
        lvFavorite = findViewById(R.id.lvFavorite);
        arrayList=new ArrayList<>();
        arrHotelID = new ArrayList<>();
        adapter_hotel = new Adapter_Favorite_Hotel(FavoriteHotelActivity.this, arrayList);
        lvFavorite.setAdapter(adapter_hotel);
        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(FavoriteHotelActivity.this,"Thanh cong"+arrayList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void event(){
//        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(FavoriteHotelActivity.this,"Thanh cong"+arrayList.get(i).getName(),Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                // khởi tạo dialog
//                alertDialogBuilder.setMessage("Bạn có muốn thoát không");
//                // thiết lập nội dung cho dialog
//                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        //finish();
//                        Toast.makeText(FavoriteHotelActivity.this,"Thanh cong",Toast.LENGTH_SHORT).show();
//                        // button "Có" thoát khỏi ứng dụng
//                    }
//                });
//
//                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        // button "no" ẩn dialog đi
//                    }
//                });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                // tạo dialog
//                alertDialog.show();
                // hiển thị dialog
                //Toast.makeText(ListHotelActivity.this,"Thanh cong"+arrayList.get(i).getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void checkLogin(FirebaseUser user) {
//        if(user == null) {
//            Intent intent = new Intent(FavoriteHotelActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }
//    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds: dataSnapshot.getChildren()) {
            if(arrHotelID.contains(ds.getKey())) {
                Hotel hotel = new Hotel();
                hotel.setName((String) ds.child(ds.getKey()).child("name").getValue());
                hotel.setAddress((String) ds.child(ds.getKey()).child("address").getValue());
                hotel.setPrice((Float)ds.child(ds.getKey()).child("price").getValue());
                hotel.setRate((Integer)ds.child(ds.getKey()).child("rate").getValue());
                arrayList.add(hotel);
            }
        }

    }

    private void readData(FirebaseUser user) {
        databaseSQL = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = databaseSQL.rawQuery("SELECT * FROM favorite where user_id = '" + user.getUid()  +"'",null);
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String hotel_id = cursor.getString(0);
            arrHotelID.add(hotel_id);
        }
    }
}
