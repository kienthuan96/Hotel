package com.example.thuan.hotel.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thuan.hotel.Adapter.Adapter_Hotel;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListHotelActivity extends AppCompatActivity {
    ListView lstHotel;
    ArrayList<Hotel> arrayList;
    Adapter_Hotel adapter_hotel;
    DatabaseReference myRef;
    Button btnDialogCo,btnDiaalogKhong;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hotel);
        myRef = database.getReference("hotel");
        id();
        lstHotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show(position);
            }
        });
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

    private void show(int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        btnDialogCo = dialog.findViewById(R.id.btn_dialogok);
        btnDiaalogKhong= dialog.findViewById(R.id.btn_dialogkhong);
        dialog.show();
        btnDialogCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListHotelActivity.this, "pick co", Toast.LENGTH_SHORT).show();
            }
        });
        btnDiaalogKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListHotelActivity.this, "pick khong", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void id(){
        lstHotel=findViewById(R.id.lstHotel);
        arrayList=new ArrayList<>();
        adapter_hotel=new Adapter_Hotel(ListHotelActivity.this,R.layout.layout_item_hotel,arrayList);
        lstHotel.setAdapter(adapter_hotel);
    }
}
