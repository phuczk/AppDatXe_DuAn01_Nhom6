package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sonans.appdatxe_duan01_nhom6.R;

public class PhanQuyenActivity2 extends AppCompatActivity {

    TextView btnDriver;
    Button btnCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_quyen2);
        btnCustomer = findViewById(R.id.btnCustomer);
        btnDriver = findViewById(R.id.btnDriver);
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PhanQuyenActivity2.this, LoginKhachHangActivity.class);
                startActivity(i);
            }
        });
        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PhanQuyenActivity2.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}