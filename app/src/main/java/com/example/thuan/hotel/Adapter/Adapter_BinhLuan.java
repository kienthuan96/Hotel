package com.example.thuan.hotel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuan.hotel.Model.BinhLuan;
import com.example.thuan.hotel.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_BinhLuan extends BaseAdapter{
    private Context context;
    private ArrayList<BinhLuan> binhluan ;

    public Adapter_BinhLuan(Context context, ArrayList<BinhLuan> binhluan) {
        this.context = context;
        this.binhluan = binhluan;
    }


    @Override
    public int getCount() {
        return binhluan.size();
    }

    @Override
    public Object getItem(int position) {
        return binhluan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_nhanxet,parent,false);
        }
        // Ánh xạ các id vào
        TextView _tenKhachSan = (TextView) convertView.findViewById(R.id.itemTenKhachSan);
        TextView _diemKhachSan = (TextView) convertView.findViewById(R.id.itemDiemKhachSan);
        TextView _binhLuanKhachSan = (TextView) convertView.findViewById(R.id.itemBinhLuan);
        TextView _tenNguoiBL = (TextView) convertView.findViewById(R.id.itemTenBL);
        TextView _ngayDangBL  = (TextView) convertView.findViewById(R.id.itemNgayDang);


        // Đưa Bình luận vào mảng
       final  BinhLuan bl  = (BinhLuan) this.getItem(position);
        _tenKhachSan.setText(bl.TenKhachSan);
        _diemKhachSan.setText(String.valueOf(bl.DiemBL));
        _binhLuanKhachSan.setText(bl.NhanXet);
        _tenNguoiBL.setText(bl.Ten);
        _ngayDangBL.setText(String.valueOf(bl.ngayTaoBL));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Tên Khách Sạn : "+bl.Ten,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
