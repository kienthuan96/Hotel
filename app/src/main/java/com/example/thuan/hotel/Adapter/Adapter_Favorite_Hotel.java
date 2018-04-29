package com.example.thuan.hotel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Favorite_Hotel extends BaseAdapter {
    Activity context;
    ArrayList<Hotel> arrayList;

    public Adapter_Favorite_Hotel(Activity context, ArrayList<Hotel> list) {
        this.context = context;
        this.arrayList = list;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_item_favorite, null);

        ImageView imgHotelFavorite  = row.findViewById(R.id.imgHotelFavorite);
        TextView txtNameFavorite    = row.findViewById(R.id.txtNameFavorite);
        TextView txtAddressFavorite = row.findViewById(R.id.txtAddressFavorite);
        TextView txtPriceFavorite   = row.findViewById(R.id.txtPriceFavorite);
        TextView txtRateFavorite    = row.findViewById(R.id.txtRateFavorite);

        Hotel hotel = arrayList.get(position);
        //Log.d("Image", hotel.getName());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("IMG_CONTACT/"+hotel.getImg1());
        Glide.with(context).using(new FirebaseImageLoader()).load(pathReference).into(imgHotelFavorite);

        txtNameFavorite.setText(hotel.getName());
        txtAddressFavorite.setText("Địa chỉ: " + hotel.getAddress());
        txtPriceFavorite.setText("Giá: " + hotel.getPrice());
        txtRateFavorite.setText(hotel.getRate().toString());
        return row;
    }
}
