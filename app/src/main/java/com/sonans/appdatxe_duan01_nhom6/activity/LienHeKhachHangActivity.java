package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.CSKH;

import java.util.HashMap;
import java.util.UUID;

public class LienHeKhachHangActivity extends AppCompatActivity {

    EditText ed1, ed2;
    Button btnSend;
    FirebaseFirestore db;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he_khach_hang);
        ed1 = findViewById(R.id.tvSDT);
        ed2 = findViewById(R.id.tvND);
        btnSend = findViewById(R.id.btnSend);
        back = findViewById(R.id.back);
        db = FirebaseFirestore.getInstance();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = UUID.randomUUID().toString();
                CSKH cskh = new CSKH(ma, ed2.getText().toString(), ed1.getText().toString(), 0);
                HashMap<String, Object> map = cskh.convertHashMap();
                db.collection("CSKH").document(ma).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(LienHeKhachHangActivity.this, "gui thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LienHeKhachHangActivity.this, KhachHangActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}