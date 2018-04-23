package com.example.thuan.hotel.Activity;
import com.example.thuan.hotel.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Search2Activity extends AppCompatActivity {
    Button ChuyenTrang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ChuyenTrang = findViewById(R.id.ChuyenTrang);

        ChuyenTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search2Activity.this,SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
