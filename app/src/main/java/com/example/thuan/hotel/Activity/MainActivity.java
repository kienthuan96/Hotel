package com.example.thuan.hotel.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thuan.hotel.DeleteActivity;
import com.example.thuan.hotel.Model.Oder;
import com.example.thuan.hotel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegister, btnPost, btnList, btnSearch, btnFavorite,btnDetail,btnDatPhong, btnDelete,btnDanhsachdat, btnGoogleMap;
    FirebaseUser user;
    Context context;
    public static final int REQUEST_CODE_REGISTER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        user = FirebaseAuth.getInstance().getCurrentUser();
        anhXa();

    }
    private void danhsachdath() {
        Intent intent = new Intent(MainActivity.this, ListOderActivity.class);
        startActivity(intent);
    }
    private void search() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    private void Datphong() {
        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    private void register() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void post(){
        Intent intent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(intent);
    }

    private void list(){
        Intent intent = new Intent(MainActivity.this, ListHotelActivity.class);
        startActivity(intent);
    }

    private void favorite(){
        Intent intent = new Intent(MainActivity.this, FavoriteHotelActivity.class);
        startActivity(intent);
    }
    private void detail(){
        Intent intent = new Intent(MainActivity.this, DetaiHotelActivity.class);
        startActivity(intent);
    }
    private void delete(){
        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
        startActivity(intent);
    }

    private void googleMap(){
        Intent intent = new Intent(MainActivity.this, GoogleMapActivity.class);
        startActivity(intent);
    }

    private void anhXa() {
        btnLogin =  findViewById(R.id.btnLogin);
        btnRegister =  findViewById(R.id.btnRegister);
        btnPost=findViewById(R.id.btnPost);
        btnList=findViewById(R.id.btnList);
        btnDetail=findViewById(R.id.btnDetail);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnSearch = findViewById(R.id.btnSearch);
        btnGoogleMap = findViewById(R.id.btnGoogleMap);
        btnDatPhong = findViewById(R.id.btnDatPhong);
        btnDanhsachdat = findViewById(R.id.btnDanhsachdat);
        btnDanhsachdat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                danhsachdath();
            }
        });
        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Datphong();
            }
        });

        btnDelete=findViewById(R.id.btnDelete);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favorite();
            }
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        btnGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap();
            }
        });
    }
}
