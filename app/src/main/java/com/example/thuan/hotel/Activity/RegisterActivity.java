package com.example.thuan.hotel.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thuan.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseUser user;
    EditText edtEmail, edtPassword, edtFullName;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();

        anhXa();
        init();
    }

    public void anhXa() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtFullName = (EditText) findViewById(R.id.edtFullName);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    public void init() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                final String fullname = edtFullName.getText().toString();
                if(email.equals("") || password.equals("") || fullname.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Bạn phải nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                }

                else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        user = mAuth.getCurrentUser();
                                        myRef.child("users").child(user.getUid()).setValue(fullname);
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}