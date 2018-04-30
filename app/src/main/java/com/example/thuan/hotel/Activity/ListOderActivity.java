package com.example.thuan.hotel.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thuan.hotel.Adapter.Adapter_Order_Hotel;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.Model.Oder;
import com.example.thuan.hotel.R;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListOderActivity   extends AppCompatActivity {
    public DatabaseReference def;
    Firebase myFB;
    private List<Oder> mOderList ;
    private Adapter_Order_Hotel adapter;
    ListView lvOrderHotel;
   // TextView txtHoTen,
   private void SetSeach()
   {
       def = FirebaseDatabase.getInstance().getReference("order");
       def.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {


               for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                   HashMap t = (HashMap) childSnapshot.getValue();
                   mOderList.add(new Oder(t.get("name").toString(),t.get("Email").toString(),t.get("Phone").toString(),t.get("DateStarOrder").toString(),t.get("DateEndOrder").toString(),Integer.parseInt(t.get("RoomOrder").toString()),Float.parseFloat(t.get("TotalMoney").toString())

                   ));



               }

               adapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listorder);
        mOderList = new ArrayList<>();
        adapter  = new Adapter_Order_Hotel(getApplicationContext(), mOderList);
        lvOrderHotel = findViewById(R.id.dsOrder);
        lvOrderHotel.setAdapter(adapter);
      /*  Firebase.setAndroidContext(this);
        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        myFB = new Firebase("https://hotel-793b0.firebaseio.com/order");
        def = FirebaseDatabase.getInstance().getReference();*/
        SetSeach();
    }

}
