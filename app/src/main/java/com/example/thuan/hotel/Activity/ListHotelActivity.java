package com.example.thuan.hotel.Activity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

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
    String id_user;
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

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("goi");
        id_user=bundle.getString("id");

        id();

        readData();
        event();

    }

//    private void show(int position) {
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.custom_dialog);
//        btnDialogCo = dialog.findViewById(R.id.btn_dialogok);
//        btnDiaalogKhong= dialog.findViewById(R.id.btn_dialogkhong);
//        dialog.show();
//        btnDialogCo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ListHotelActivity.this, "pick co", Toast.LENGTH_SHORT).show();
//            }
//        });
//        btnDiaalogKhong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ListHotelActivity.this, "pick khong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    private void readData(){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Hotel hotel=dataSnapshot.getValue(Hotel.class);
                if(id_user.equals(hotel.getId_user()))  arrayList.add(hotel);
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

    private void id(){
        lstHotel=findViewById(R.id.lstHotel);
        arrayList=new ArrayList<>();
        adapter_hotel=new Adapter_Hotel(ListHotelActivity.this,R.layout.layout_item_hotel,arrayList);
        lstHotel.setAdapter(adapter_hotel);
    }

//    private void event(){
//        lstHotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent=new Intent(ListHotelActivity.this,DetaiHotelActivity.class);
////                startActivity(intent);
//                Toast.makeText(ListHotelActivity.this,"Thanh cong"+arrayList.get(i).getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuPost:
                Intent intent=new Intent(ListHotelActivity.this,PostActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("id",id_user);
                intent.putExtra("goi",bundle1);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void event(){
        lstHotel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                luachon(i);
                return false;
            }
        });
    }
    private void luachon(final int stt){
        String[] luachon={"Chỉnh Sửa","Xóa"};
        AlertDialog.Builder alBuilder=new AlertDialog.Builder(this);
        alBuilder.setTitle("Lựa chọn chức năng")
                .setItems(luachon, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                            {
                                break;
                            }
                            case 1:
                            {
                                delete(stt);
                                break;
                            }
                        }
                    }
                });
        alBuilder.show();
    }

    public void delete(final int stt){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo")
                .setMessage("Bạn có muốn xóa không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myRef.child(arrayList.get(stt).getId()).removeValue();
                        readData();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}
