package com.example.thuan.hotel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuan.hotel.Model.BinhLuan;
import com.example.thuan.hotel.Program;
import com.example.thuan.hotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThemBinhLuanActivity extends AppCompatActivity {
    private static final String TAG = ThemBinhLuanActivity.class.getSimpleName();
   private Program pr;
    // Khai báo Button và các Text ;
    private TextView txtIDKhachSan;
    private TextView txtTenKhachSan;
    private TextView txtNgayBL;
    private EditText edtNguoiBL ;
    private EditText edtNhanXet;
    private EditText edtDiemBL;
    private Button  btnGuiBL;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thembinhluan);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        loadData();
          final Button  Thoat =  (Button) findViewById(R.id.btnThoatBL);
            Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Thoat = new Intent(ThemBinhLuanActivity.this, MainActivity.class);
                startActivity(intent_Thoat);
            }
        });
            btnGuiBL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String _IDKhachSan = txtIDKhachSan.getText().toString();
                String _tenKhachSan = txtTenKhachSan.getText().toString();
                String _Name = edtNguoiBL.getText().toString();
                String _nhanXet= edtNhanXet.getText().toString();
                   float _diemBL = Float.valueOf(edtDiemBL.getText().toString());

                    // Kiem Tra cac doi tuong
                    if(TextUtils.isEmpty(_Name)) {
                        edtNguoiBL.setError("Vui lòng Nhập Tên");
                        return;
                    }
                   else if(TextUtils.isEmpty(_nhanXet)) {
                        edtNhanXet.setError("Vui lòng nhập nhận xét");
                        return;
                    }
                   else if(_diemBL <=0 || _diemBL >10  ) {
                        edtDiemBL.setError("Nhập điểm trong khoảng 0-10");
                        return;
                    }
                    else {
                        Create_BinhLuan(_IDKhachSan, _tenKhachSan, new Date(), _Name, _nhanXet, _diemBL);
                        Toast.makeText(ThemBinhLuanActivity.this, "Đã thêm thành công !!!", Toast.LENGTH_SHORT).show();
                        edtNguoiBL.setText("");
                        edtNhanXet.setText("");
                        edtDiemBL.setText("");
                        edtNguoiBL.findFocus();
                    }
                }
            });

    }
    private void loadData(){
        txtIDKhachSan = (TextView) findViewById(R.id.idKhachSan);
        txtIDKhachSan.setText(Program.IDKhachSan);
        txtTenKhachSan=(TextView) findViewById(R.id.tenKhachSan);
        txtTenKhachSan.setText(Program.tenKhachSan);
        txtNgayBL = (TextView) findViewById(R.id.idNgayTaoBL);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        txtNgayBL.setText(date.toString());
        edtNguoiBL = (EditText) findViewById(R.id.userName);
        edtNhanXet = (EditText) findViewById(R.id.edtNhanXet);
        edtDiemBL =  (EditText) findViewById(R.id.edtDiem);
        btnGuiBL  = (Button ) findViewById(R.id.btnGuiBL);

    }
    private void Create_BinhLuan(String idKhachSan , String tenKhachSan , Date date ,String tenBL ,  String nhanXet, float diemBL){

        BinhLuan bl = new BinhLuan(idKhachSan,tenKhachSan,date,tenBL,nhanXet,diemBL);

        mFirebaseDatabase.child("hotel").child(idKhachSan).child("BinhLuan").push().setValue(bl);
        mFirebaseDatabase.child("hotel").child(idKhachSan).child("BinhLuan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BinhLuan bl = dataSnapshot.getValue(BinhLuan.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Có lỗi khi tạo  bình Luận", databaseError.toException());
            }
        });
    }
}
