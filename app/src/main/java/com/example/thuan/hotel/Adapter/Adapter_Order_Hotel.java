package com.example.thuan.hotel.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.thuan.hotel.Model.Hotel;
import com.example.thuan.hotel.Model.Oder;
import com.example.thuan.hotel.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Order_Hotel extends BaseAdapter {
    private Context mContext;
    private List<Oder> mOrderlist;

    public Adapter_Order_Hotel (Context mContext, List<Oder> mOrderlist) {
        this.mContext = mContext;
        this.mOrderlist = mOrderlist;
    }

    @Override
    public int getCount() {
        return mOrderlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mOrderlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.listorder,null);
        TextView tvHoTEn = (TextView)v.findViewById(R.id.txtHT);
        TextView tvEmail = (TextView)v.findViewById(R.id.txtEM);
        TextView tvPhone = (TextView)v.findViewById(R.id.txtDT);
        TextView tvDateStarOder = (TextView)v.findViewById(R.id.txtND);
        TextView tvDateEndOder = (TextView)v.findViewById(R.id.txtNT);
        TextView RoomOder = (TextView)v.findViewById(R.id.txtSPD);
        TextView Totalmoney = (TextView)v.findViewById(R.id.txtTT);

        tvHoTEn.setText(mOrderlist.get(i).getName());
        tvEmail.setText(mOrderlist.get(i).getEmail());
        tvPhone.setText(mOrderlist.get(i).getPhone());
        tvDateStarOder.setText(mOrderlist.get(i).getDateStartOrder());
        tvDateEndOder.setText(mOrderlist.get(i).getDateEndOrder());
        RoomOder.setText(mOrderlist.get(i).getRoomOrder()+"");
        Totalmoney.setText(mOrderlist.get(i).getTotalMoney()+"");

        return v;

    }
}