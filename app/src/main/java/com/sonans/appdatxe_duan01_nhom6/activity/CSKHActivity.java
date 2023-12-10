package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sonans.appdatxe_duan01_nhom6.R;

public class CSKHActivity extends AppCompatActivity {

    TextView tv1, tv2;
    Button btn1, btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cskhactivity);
        tv1 = findViewById(R.id.tvSDT);
        tv2 = findViewById(R.id.tvND);
        btn1 = findViewById(R.id.btnOkla);
        btn2 = findViewById(R.id.btnCall);

        SharedPreferences sp = getSharedPreferences("chamSocKH", MODE_PRIVATE);
        String sdt = sp.getString("sdt", "");
        String noiDung = sp.getString("noiDung", "");

        tv1.setText(sdt);
        tv2.setText(noiDung);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CSKHActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(CSKHActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CSKHActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sdt));
                    startActivity(call);
                }
            }
        });
    }
}