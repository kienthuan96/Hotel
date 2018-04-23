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
        RatingBar rbDanhGia = (RatingBar)v.findViewById(R.id.rbXepHang);
        tvTenKhachSan.setText(mKhachSanList.get(i).getName());
        tvDiaChi.setText(String.valueOf(mKhachSanList.get(i).getAddress()));
        tvGiaTien.setText(String.valueOf(mKhachSanList.get(i).getPrice()));
        //  Imview.setImageResource(R.drawable.khachsan);
        Log.d("nana",String.valueOf(R.drawable.khachsan));
        rbDanhGia.setNumStars(5);
        rbDanhGia.setRating(3);

        Glide.with(mContext)
                .load("https://firebasestorage.googleapis.com/v0/b/khachsanseach.appspot.com/o/KhachSan1.jpg?alt=media&token=d3e56926-a166-4b3a-b003-c35fd4b1198c")
                .into(Imview);
        //v.setTag(mChuyenXeList.get(i).get);

        return v;

    }
}