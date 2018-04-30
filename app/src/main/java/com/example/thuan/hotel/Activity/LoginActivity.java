package com.example.thuan.hotel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuan.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    public String getEmail;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseUser user;
    EditText edtEmail, edtPassword;
    TextView txtRegister;
    Button btnLogin;
    Animation aniLogin;
    ImageView imgAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        anhXa();

        init();
    }

    public void anhXa() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtRegister=findViewById(R.id.txt_link_register);
        imgAvatar=findViewById(R.id.imgAvatar);
        aniLogin= AnimationUtils.loadAnimation(this,R.anim.login);
    }

    public void init() {
        imgAvatar.setAnimation(aniLogin);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lay email gan qua cho Comment
                getEmail = edtEmail.getText().toString();


                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if(email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Bạn phải nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                }

                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, ListHotelActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putString("id",user.getUid());
                                        intent.putExtra("goi",bundle);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
