package com.example.thuan.hotel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuan.hotel.Activity.ListHotelActivity;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Hotel extends ArrayAdapter {
    Context context;
    int layout;
    ArrayList<Hotel> arrayList;

    public Adapter_Hotel(@NonNull Context context, int resource, @NonNull ArrayList<Hotel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imgKhachSan);
        TextView txtTen= convertView.findViewById(R.id.txtTenKhachSan);
        TextView txtDiaChi= convertView.findViewById(R.id.txtDiaChi);
        TextView txtGia= convertView.findViewById(R.id.txtGiaTien);

        Hotel hotel=arrayList.get(position);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("IMG_CONTACT/"+hotel.getImg1());
        Glide.with(context).using(new FirebaseImageLoader()).load(pathReference).into(imageView);
//        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/hotel-793b0.appspot.com/o/IMG_CONTACT%2F"+hotel.getImg1()+"?alt=media&token=d5f61a15-07d0-4f70-8ed8-0fa389da9e52").into(imageView);
        Log.d("URL ",pathReference.getDownloadUrl().toString());
        txtTen.setText(hotel.getName());
        txtDiaChi.setText(hotel.getAddress());
        txtGia.setText(hotel.getPrice().toString());
        return convertView;
    }

}
