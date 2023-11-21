package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;

public class TraKhachActivity extends AppCompatActivity {

    ImageView imgCall, imgMessage;
    Button btnKhachXuong;
    TextView tvEnd;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_khach);

        db = FirebaseFirestore.getInstance();

        imgCall = findViewById(R.id.imgCall);
        imgMessage = findViewById(R.id.imgMessage);
        btnKhachXuong = findViewById(R.id.btnKhachXuong);
        tvEnd = findViewById(R.id.tvEnd);

    }
}