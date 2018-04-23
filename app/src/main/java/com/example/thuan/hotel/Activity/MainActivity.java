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
import com.example.thuan.hotel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegister, btnPost, btnList, btnFavorite;
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

    private void anhXa() {
        btnLogin =  findViewById(R.id.btnLogin);
        btnRegister =  findViewById(R.id.btnRegister);
        btnPost=findViewById(R.id.btnPost);
        btnList=findViewById(R.id.btnList);
        btnFavorite = findViewById(R.id.btnFavorite);

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
    }
}
