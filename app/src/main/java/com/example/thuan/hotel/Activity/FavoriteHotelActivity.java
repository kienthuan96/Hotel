package com.example.thuan.hotel.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thuan.hotel.Adapter.Adapter_Favorite_Hotel;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FavoriteHotelActivity extends AppCompatActivity {
    ListView lvFavorite;
    ArrayList<Hotel> arrayList;
    Adapter_Favorite_Hotel adapter_hotel;
    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        myRef = database.getReference("hotel");
        init();
        event();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Hotel hotel=dataSnapshot.getValue(Hotel.class);
                arrayList.add(hotel);
                adapter_hotel.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void init(){
        lvFavorite = findViewById(R.id.lvFavorite);
        arrayList=new ArrayList<>();
        adapter_hotel = new Adapter_Favorite_Hotel(FavoriteHotelActivity.this, arrayList);
        lvFavorite.setAdapter(adapter_hotel);
    }

    private void event(){
        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FavoriteHotelActivity.this,"Thanh cong"+arrayList.get(i).getName(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // khởi tạo dialog
                alertDialogBuilder.setMessage("Bạn có muốn thoát không");
                // thiết lập nội dung cho dialog
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //finish();
                        Toast.makeText(FavoriteHotelActivity.this,"Thanh cong",Toast.LENGTH_SHORT).show();
                        // button "Có" thoát khỏi ứng dụng
                    }
                });

                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // button "no" ẩn dialog đi
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // tạo dialog
                alertDialog.show();
                // hiển thị dialog
                //Toast.makeText(ListHotelActivity.this,"Thanh cong"+arrayList.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
