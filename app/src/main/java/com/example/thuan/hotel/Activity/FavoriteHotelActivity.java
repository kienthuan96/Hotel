package com.example.thuan.hotel.Activity;

import android.app.Dialog;
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
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

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
    Hotel hotel;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
        // Check login
//        checkLogin(user);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("goi");
        user=bundle.getString("id");

        myRef = database.getReference("hotel");
        init();

        readData(user);
        showData();
        addEvents();
    }

    private void init(){
        hotel           = new Hotel();
        lvFavorite      = findViewById(R.id.lvFavorite);
        arrayList       =  new ArrayList<>();
        arrHotelID      = new ArrayList<>();
        adapter_hotel   = new Adapter_Favorite_Hotel(FavoriteHotelActivity.this, arrayList);
        lvFavorite.setAdapter(adapter_hotel);
    }

    private void checkLogin(FirebaseUser user) {
        if(user == null) {
            Intent intent = new Intent(FavoriteHotelActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void showData() {
        arrayList.clear();
        Log.d("Size", arrHotelID.size() + "");
        if(arrHotelID.size() > 0) {
            for(int i = 0; i < arrHotelID.size(); i++) {
                myRef.child(arrHotelID.get(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        hotel = dataSnapshot.getValue(Hotel.class);
                        if(hotel != null) {
                            arrayList.add(hotel);
                            adapter_hotel.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

//    private void readData(FirebaseUser user) {
//        arrHotelID.clear();
//        databaseSQL = Database.initDatabase(this, DATABASE_NAME);
//        Cursor cursor = databaseSQL.rawQuery("SELECT * FROM favorite where user_id = '" + user.getUid()  +"'",null);
//
//        if(cursor.getCount() == 0) {
//            Toast.makeText(this, "Hiện vẫn chưa có khách sạn nào trong danh sách yêu thích", Toast.LENGTH_LONG).show();
//        }
//        else {
//            for(int i = 0; i < cursor.getCount(); i++){
//                cursor.moveToPosition(i);
//                String hotel_id = cursor.getString(0);
//                arrHotelID.add(hotel_id);
//            }
//        }
//
//    }
private void readData(String user) {
    arrHotelID.clear();
    databaseSQL = Database.initDatabase(this, DATABASE_NAME);
    Cursor cursor = databaseSQL.rawQuery("SELECT * FROM favorite where user_id = '" + user  +"'",null);

    if(cursor.getCount() == 0) {
        Toast.makeText(this, "Hiện vẫn chưa có khách sạn nào trong danh sách yêu thích", Toast.LENGTH_LONG).show();
    }
    else {
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String hotel_id = cursor.getString(0);
            arrHotelID.add(hotel_id);
        }
    }

}

    private boolean show(final String hotel_id) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        Button btnDialogYes = dialog.findViewById(R.id.btn_dialogok);
        Button btnDialogNo = dialog.findViewById(R.id.btn_dialogkhong);
        dialog.show();
        btnDialogYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deleteItem(hotel_id);
                showData();
                Toast.makeText(FavoriteHotelActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return true;
    }

    private void addEvents() {
        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
            String hotel_id =arrayList.get(position).getId();
            Intent intent = new Intent(FavoriteHotelActivity.this, DetaiHotelActivity.class);
            Bundle bundle1=new Bundle();
            bundle1.putString("id",hotel_id);
            intent.putExtra("goi",bundle1);
            startActivity(intent);
            }
        });

        lvFavorite.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String hotel_id =arrayList.get(position).getId();
                return show(hotel_id);
            }
        });
    }

//    private void deleteItem(String hotel_ID) {
//        FirebaseUser user = mAuth.getCurrentUser();
//        databaseSQL.delete("favorite", "user_id = ? and hotel_id = ?", new String[]{user.getUid(), hotel_ID});
//        readData(user);
//    }
}
