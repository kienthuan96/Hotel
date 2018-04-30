package com.example.thuan.hotel;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thuan.hotel.Activity.PostActivity;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.Model.Service;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    EditText edtTenEdit,edtThanhPhoEdit,edtQuanEdit,edtDiaChiEdit,edtSDTEdit,edtGiaEdit;
    ImageView img1Edit,img2Edit,img3Edit;
    CheckBox chkWifiEdit,chkPetEdit,chkSwimmingPoolEdit,chkBarEdit,chkRestaurantEdit;
    Spinner spnRateEdit;

    InputStream inputStream_img;
    Uri uri_1,uri_2,uri_3;
    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String id_hotel;
    ArrayList<Integer> arrayListRate;
    Hotel hotel;
    Integer img=0;
    private int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        id();

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("goi");
        id_hotel=bundle.getString("id");
        myRef = database.getReference("hotel").child(id_hotel);
        readData();
        event();
    }

    private void readData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hotel=dataSnapshot.getValue(Hotel.class);
                edtTenEdit.setText(hotel.getName());
                edtSDTEdit.setText(hotel.getNumberPhone()+"");
                edtGiaEdit.setText(hotel.getPrice()+"");
                edtDiaChiEdit.setText(hotel.getAddress());
                edtThanhPhoEdit.setText(hotel.getCity());
                edtQuanEdit.setText(hotel.getDistrict());

                Service service=hotel.getService();
                if(service.getWifi()) chkWifiEdit.setChecked(true);
                if(service.getPet()) chkPetEdit.setChecked(true);
                if(service.getRestaurant()) chkRestaurantEdit.setChecked(true);
                if(service.getBar()) chkBarEdit.setChecked(true);
                if(service.getSwimmingPool()) chkSwimmingPoolEdit.setChecked(true);

                FirebaseStorage storage1 = FirebaseStorage.getInstance();
                StorageReference storageRef1 = storage1.getReference();
                StorageReference pathReference1 = storageRef1.child("IMG_CONTACT/"+hotel.getImg1());
                Glide.with(EditActivity.this).using(new FirebaseImageLoader()).load(pathReference1).into(img1Edit);
                StorageReference pathReference2 = storageRef1.child("IMG_CONTACT/"+hotel.getImg2());
                Glide.with(EditActivity.this).using(new FirebaseImageLoader()).load(pathReference2).into(img2Edit);
                StorageReference pathReference3 = storageRef1.child("IMG_CONTACT/"+hotel.getImg3());
                Glide.with(EditActivity.this).using(new FirebaseImageLoader()).load(pathReference3).into(img3Edit);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void id(){
        edtTenEdit=findViewById(R.id.edtTenHotelEdit);
        edtThanhPhoEdit=findViewById(R.id.edtThanhPhoHotelEdit);
        edtQuanEdit=findViewById(R.id.edtQuanHotelEdit);
        edtDiaChiEdit=findViewById(R.id.edtDiaChiHotelEdit);
        edtSDTEdit=findViewById(R.id.edtSDTHotelEdit);
        edtGiaEdit=findViewById(R.id.edtGiaEdit);
        img1Edit=findViewById(R.id.imgHotel_1Edit);
        img2Edit=findViewById(R.id.imgHotel_2Edit);
        img3Edit=findViewById(R.id.imgHotel_3Edit);
        chkWifiEdit=findViewById(R.id.chkWifiEdit);
        chkPetEdit=findViewById(R.id.chkPetEdit);
        chkRestaurantEdit=findViewById(R.id.chkResaurantEdit);
        chkBarEdit=findViewById(R.id.chkBarEdit);
        chkSwimmingPoolEdit=findViewById(R.id.chkSwimmingPoolEdit);
        spnRateEdit=findViewById(R.id.spRateEdit);
        hotel=new Hotel();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            try {
//                uri=data.getData();
                inputStream_img = EditActivity.this.getContentResolver().openInputStream(data.getData());
                if(img==1)
                {
                    uri_1=data.getData();
                    img1Edit.setImageBitmap(BitmapFactory.decodeStream(inputStream_img));
                }
                if(img==2)
                {
                    uri_2=data.getData();
                    img2Edit.setImageBitmap(BitmapFactory.decodeStream(inputStream_img));
                }
                if(img==3)
                {
                    uri_3=data.getData();
                    img3Edit.setImageBitmap(BitmapFactory.decodeStream(inputStream_img));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void save(){
        String temp=myRef.push().getKey();
        Service service=new Service(chkWifiEdit.isChecked(),
                chkPetEdit.isChecked(),
                chkRestaurantEdit.isChecked(),
                chkBarEdit.isChecked(),
                chkSwimmingPoolEdit.isChecked());
        hotel.setService(service);
        hotel.setImg1(uploadIMG(uri_1));
        hotel.setImg2(uploadIMG(uri_2));
        hotel.setImg3(uploadIMG(uri_3));
        hotel.setName(edtTenEdit.getText().toString());
        hotel.setCity(edtThanhPhoEdit.getText().toString());
        hotel.setDistrict(edtThanhPhoEdit.getText().toString());
        hotel.setAddress( edtDiaChiEdit.getText().toString());
        hotel.setNumberPhone(Integer.parseInt(edtSDTEdit.getText().toString()));
        hotel.setPrice(Float.parseFloat(edtGiaEdit.getText().toString()));
        hotel.setId(temp);

        DatabaseReference myRef1=database.getReference().child(id_hotel);
        myRef1.setValue(hotel);

    }

    private void event(){
        arrayListRate=new ArrayList<>();
        arrayListRate.add(1);
        arrayListRate.add(2);
        arrayListRate.add(3);
        arrayListRate.add(4);
        arrayListRate.add(5);
        ArrayAdapter arrayAdapter=new ArrayAdapter(EditActivity.this,android.R.layout.simple_spinner_item,arrayListRate);
        spnRateEdit.setAdapter(arrayAdapter);
        spnRateEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hotel.setStars(arrayListRate.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img1Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img=1;
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        img2Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img=2;
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        img3Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img=3;
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });
    }
    public String uploadIMG(Uri uri){

        Calendar calendar=Calendar.getInstance();
        String ten=calendar.getTimeInMillis()+"";

        StorageReference filepath=storageRef.child("IMG_CONTACT").child(ten);
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditActivity.this,"Thêm Thành Công !!!" , Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditActivity.this,"Thêm Thất Bại !!!" , Toast.LENGTH_SHORT).show();
            }
        });
        return ten;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuSave:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
