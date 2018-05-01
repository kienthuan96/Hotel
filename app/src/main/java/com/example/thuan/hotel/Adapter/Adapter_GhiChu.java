package com.example.thuan.hotel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuan.hotel.Activity.MainActivity;
import com.example.thuan.hotel.Activity.SuaGhiChu_Activity;
import com.example.thuan.hotel.Activity.ThemBinhLuanActivity;
import com.example.thuan.hotel.Model.GhiChu;
import com.example.thuan.hotel.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_GhiChu extends BaseAdapter {
    private Activity context ;
private ArrayList<GhiChu> listGhiChu ;

    public Adapter_GhiChu(Activity context, ArrayList<GhiChu> listGhiChu) {
        this.context = context;
        this.listGhiChu = listGhiChu;
    }

    @Override
    public int getCount() {
        return listGhiChu.size();
    }

    @Override
    public Object getItem(int position) {
        return listGhiChu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)  context.getSystemService(context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate(R.layout.layout_itemghichu,null);

        //Ánh Xạ
        ImageView _anhKhachSan =  (ImageView) row.findViewById(R.id.itemAnhKhachSan);
        TextView _tenGhiChu = (TextView) row.findViewById(R.id.itemTenGhiChu);
        TextView _tenKhachSan = (TextView) row.findViewById(R.id.itemTenKhachSan);
        TextView _ngayTaoGhiChu = (TextView) row.findViewById(R.id.itemNgayTaoGhiChu);
        TextView _diaChiKhachSan = (TextView) row.findViewById(R.id.itemDiaChiKhachSan);
        TextView _moTaKhachSan = (TextView) row.findViewById(R.id.itemMoTaKhachSan);
        Button _btnSuaGhiChu = (Button) row.findViewById(R.id.btnSuaGhiChu);
        Button _btnXoaGhiChu = (Button) row.findViewById(R.id.btnXoaGhiChu);

       GhiChu ghichu =  (GhiChu) listGhiChu.get(position);
        String ID;
        ID=String.valueOf( ghichu.idGhiChu);
        int ID1 = Integer.parseInt(ID);
        _tenGhiChu.setText(ghichu.tenGhiChu);
        _tenKhachSan.setText(ghichu.tenKSGhiChu);
        _ngayTaoGhiChu.setText(String.valueOf(ghichu.ngayTaoGhiChu));
        _diaChiKhachSan.setText(ghichu.diaChiKSGhiChu);
        _moTaKhachSan.setText(ghichu.moTaGhiChu);
        Bitmap bitmap = BitmapFactory.decodeByteArray(ghichu.anhKhachSan,0 ,ghichu.anhKhachSan.length);
        _anhKhachSan.setImageBitmap(bitmap);

        // Bắut ự kiện cho nút Button  Sữa và Xóa Ghi Chú
        _btnSuaGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent_SuaGhiChu = new Intent(context, SuaGhiChu_Activity.class);
                // Truyền vào ID Để sữa khách sạn
                itent_SuaGhiChu.putExtra("idNote", ID1);
                context.startActivity(itent_SuaGhiChu);
            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Tên Khách Sạn : "+ghichu.tenGhiChu,Toast.LENGTH_SHORT).show();
            }
        });
        return row;
    }
}
