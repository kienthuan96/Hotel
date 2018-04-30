package com.example.thuan.hotel.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.Model.Service;
import com.example.thuan.hotel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class PostActivity extends AppCompatActivity {
    EditText edtTen,edtThanhPho,edtQuan,edtDiaChi,edtSDT,edtGia;
    ImageView img1,img2,img3;
    CheckBox chkWifi,chkPet,chkSwimmingPool,chkBar,chkRestaurant;
    Spinner spnRate;
    DatabaseReference myRef;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    private int PICK_IMAGE = 1;
    InputStream inputStream_img;
    Integer img=1;
    String urlImg1,urlImg2,urlImg3;
    Uri uri,uri_1,uri_2,uri_3;
    Hotel hotel;
    ArrayList<Integer> arrayListRate;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        hotel=new Hotel();
        urlImg1=new String();
        urlImg2=new String();
        urlImg3=new String();

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("goi");
        id_user=bundle.getString("id");

        id();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference("hotel");
        StorageReference storageRef = storage.getReference();



        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img=1;
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img=2;
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img=3;
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });

        arrayListRate=new ArrayList<>();
        arrayListRate.add(1);
        arrayListRate.add(2);
        arrayListRate.add(3);
        arrayListRate.add(4);
        arrayListRate.add(5);
        ArrayAdapter arrayAdapter=new ArrayAdapter(PostActivity.this,android.R.layout.simple_spinner_item,arrayListRate);
        spnRate.setAdapter(arrayAdapter);
        spnRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(PostActivity.this,arrayListRate.get(i).toString(),Toast.LENGTH_SHORT).show();
                hotel.setStars(arrayListRate.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            try {
//                uri=data.getData();
                inputStream_img = PostActivity.this.getContentResolver().openInputStream(data.getData());
                if(img==1)
                {
                    uri_1=data.getData();
                    img1.setImageBitmap(BitmapFactory.decodeStream(inputStream_img));
                }
                if(img==2)
                {
                    uri_2=data.getData();
                    img2.setImageBitmap(BitmapFactory.decodeStream(inputStream_img));
                }
                if(img==3)
                {
                    uri_3=data.getData();
                    img3.setImageBitmap(BitmapFactory.decodeStream(inputStream_img));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


    private void id(){
        edtTen=findViewById(R.id.edtTenHotel);
        edtThanhPho=findViewById(R.id.edtThanhPhoHotel);
        edtQuan=findViewById(R.id.edtQuanHotel);
        edtDiaChi=findViewById(R.id.edtDiaChiHotel);
        edtSDT=findViewById(R.id.edtSDTHotel);
        edtGia=findViewById(R.id.edtGia);
        img1=findViewById(R.id.imgHotel_1);
        img2=findViewById(R.id.imgHotel_2);
        img3=findViewById(R.id.imgHotel_3);
        chkWifi=findViewById(R.id.chkWifi);
        chkPet=findViewById(R.id.chkPet);
        chkRestaurant=findViewById(R.id.chkResaurant);
        chkBar=findViewById(R.id.chkBar);
        chkSwimmingPool=findViewById(R.id.chkSwimmingPool);
        spnRate=findViewById(R.id.spRate);
    }

    private void save(){
        String temp=myRef.push().getKey();
        Service service=new Service(chkWifi.isChecked(),
                chkPet.isChecked(),
                chkRestaurant.isChecked(),
                chkBar.isChecked(),
                chkSwimmingPool.isChecked());
        hotel.setService(service);
        hotel.setImg1(uploadIMG(uri_1));
        hotel.setImg2(uploadIMG(uri_2));
        hotel.setImg3(uploadIMG(uri_3));
        hotel.setName(edtTen.getText().toString());
        hotel.setCity(edtThanhPho.getText().toString());
        hotel.setDistrict(edtThanhPho.getText().toString());
        hotel.setAddress( edtDiaChi.getText().toString());
        hotel.setNumberPhone(Integer.parseInt(edtSDT.getText().toString()));
        hotel.setPrice(Float.parseFloat(edtGia.getText().toString()));
        hotel.setId_user(id_user);
        hotel.setId(temp);
        myRef.child(temp).setValue(hotel);

    }
    public String uploadIMG(Uri uri){

        Calendar calendar=Calendar.getInstance();
        String ten=calendar.getTimeInMillis()+"";

        StorageReference filepath=storageRef.child("IMG_CONTACT").child(ten);
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PostActivity.this,"Thêm Thành Công !!!" , Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostActivity.this,"Thêm Thất Bại !!!" , Toast.LENGTH_SHORT).show();
            }
        });
        return ten;
    }

//    public String uploadIMG(ImageView imageView){
//        Calendar calendar=Calendar.getInstance();
//        String ten=calendar.getTimeInMillis()+"";
//
//        StorageReference filepath=storageRef.child("IMG_CONTACT").child(ten);
//        imageView.setDrawingCacheEnabled(true);
//        imageView.buildDrawingCache();
//        Bitmap bitmap = imageView.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//        UploadTask uploadTask = filepath.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        });
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//
//                }
//            });
//
//        return ten;
//    }

}
