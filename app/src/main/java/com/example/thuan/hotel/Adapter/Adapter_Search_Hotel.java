package com.example.thuan.hotel.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.thuan.hotel.R;
import com.bumptech.glide.Glide;
import com.example.thuan.hotel.Model.Hotel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Search_Hotel extends BaseAdapter {
    private Context mContext;
    private List<Hotel> mKhachSanList;

    public Adapter_Search_Hotel (Context mContext, List<Hotel> mKhachSanList) {
        this.mContext = mContext;
        this.mKhachSanList = mKhachSanList;
    }

    @Override
    public int getCount() {
        return mKhachSanList.size();
    }

    @Override
    public Object getItem(int i) {
        return mKhachSanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext,R.layout.listkhachsan,null);
        ImageView Imview = (ImageView) v.findViewById(R.id.imageKhachSan);
        TextView tvTenKhachSan = (TextView)v.findViewById(R.id.txtTenKhachSan);
        TextView tvDiaChi = (TextView)v.findViewById(R.id.txtDiaChi);
        TextView tvGiaTien = (TextView)v.findViewById(R.id.txtGiaTien);
        TextView tvDanhGia = (TextView)v.findViewById(R.id.txtDanhGia);
        RatingBar rbDanhGia = (RatingBar)v.findViewById(R.id.rbXepHang);
        tvTenKhachSan.setText(mKhachSanList.get(i).getName());
        tvDiaChi.setText(String.valueOf(mKhachSanList.get(i).getAddress()));
        tvGiaTien.setText(String.valueOf(mKhachSanList.get(i).getPrice()));
        //  Imview.setImageResource(R.drawable.khachsan);
        Log.d("nana",String.valueOf(R.drawable.khachsan));
        rbDanhGia.setNumStars(5);
        rbDanhGia.setRating(mKhachSanList.get(i).getStars());
        if(mKhachSanList.get(i).getRate()>=8)
        {
            tvDanhGia.setText("Xuất sắc");

        }
        if(mKhachSanList.get(i).getRate()>=7&&mKhachSanList.get(i).getRate()<8)
        {
            tvDanhGia.setText("Rất Tốt");
        }
        if(mKhachSanList.get(i).getRate()>=6&&mKhachSanList.get(i).getRate()<7)
        {
            tvDanhGia.setText("Tốt");
        }
        if(mKhachSanList.get(i).getRate()>=5&&mKhachSanList.get(i).getRate()<6)
        {
            tvDanhGia.setText("Rất khá");
        }
        if(mKhachSanList.get(i).getRate()>=4&&mKhachSanList.get(i).getRate()<5)
        {
            tvDanhGia.setText("Khá");
        }
      /*  Glide.with(mContext)
                .load("https://firebasestorage.googleapis.com/v0/b/khachsanseach.appspot.com/o/KhachSan1.jpg?alt=media&token=d3e56926-a166-4b3a-b003-c35fd4b1198c")
                .into(Imview);*/
        //v.setTag(mChuyenXeList.get(i).get);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("IMG_CONTACT/"+mKhachSanList.get(i).getImg1());
        //Glide.with(context).using(new FirebaseImageLoader()).load(pathReference).into(imageView);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/hotel-793b0.appspot.com/o/IMG_CONTACT%2F"+mKhachSanList.get(i).getImg1()+"?alt=media&token=d5f61a15-07d0-4f70-8ed8-0fa389da9e52").into(Imview);


        return v;

    }
}