package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;
import com.sonans.appdatxe_duan01_nhom6.model.DonNhan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class XacNhanThanhToanActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_thanh_toan);
        Button btnXacNhan = findViewById(R.id.btnXacNhan2);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SharedPreferences sp = getSharedPreferences("DonDat", MODE_PRIVATE);
        String maDonDat = sp.getString("maDonDat", "");
        String diemKH = sp.getString("diemKhoiHanh", "");
        String diemDen = sp.getString("diemDen", "");
        String tenKH = sp.getString("tenKhachHang", "");
        String sdt = sp.getString("soDTKhachHang", "");
        int soLuong = sp.getInt("soLuong", 0);
        int gia = sp.getInt("giaCuoc", 0);
        String maKH = sp.getString("maKhachHang", "");
        String thoiGian = sp.getString("thoiGian", "");

        String maDonNhan = UUID.randomUUID().toString();
        SharedPreferences sp1 = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String userRemember = sp1.getString("USERNAME", "");
        Date date = new Date();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DonNhan donNhan = new DonNhan(maDonNhan, date, diemKH, diemDen, maKH, userRemember , tenKH, sdt, soLuong, gia);
                HashMap<String, Object> map = donNhan.convertHashMap();
                db.collection("DonDat").document(maDonDat).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        db.collection("DonNhan").document(maDonNhan).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(XacNhanThanhToanActivity.this, "Hoan Thanh Chuyen Di", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(XacNhanThanhToanActivity.this, DonDatTaiXeActivity.class);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(XacNhanThanhToanActivity.this, "loi khi them vao lich su", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }
}