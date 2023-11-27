package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;

public class HuyChuyenKhActivity extends AppCompatActivity {

    TextView tvDiemKhoiHanh, tvDiemDen, tvTenKH, tvSDT, tvSoLuong, tvGia, tvThoiGian;
    Button btnHuy, btnOk;
    ImageView back;
    LinearLayout mapKH, mapDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huy_chuyen_kh);
        tvDiemKhoiHanh = findViewById(R.id.tvDiemKhoiHanh);
        tvDiemDen = findViewById(R.id.tvDiemDen);
        tvTenKH = findViewById(R.id.tvName);
        tvSDT = findViewById(R.id.tvPhoneNumber);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvGia = findViewById(R.id.tvPrice);
        tvThoiGian = findViewById(R.id.tvThoiZan);
        btnHuy = findViewById(R.id.huyChuyen);
        btnOk = findViewById(R.id.ok);
        mapKH = findViewById(R.id.mapKH);
        mapDD = findViewById(R.id.mapDD);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HuyChuyenKhActivity.this, DonDatKhachHangActivity.class);
                startActivity(i);
                finish();
            }
        });

        SharedPreferences sp = getSharedPreferences("DonDat", MODE_PRIVATE);
        String maDonDat = sp.getString("maDonDat", "");
        String diemKH = sp.getString("diemKhoiHanh", "");
        String diemDen = sp.getString("diemDen", "");
        String tenKH = sp.getString("tenKhachHang", "");
        String sdt = sp.getString("soDTKhachHang", "");
        int soLuong = sp.getInt("soLuong", 0);
        int gia = sp.getInt("giaCuoc", 0);
        String thoiGian = sp.getString("thoiGian", "");

        tvDiemKhoiHanh.setText(diemKH);
        tvDiemDen.setText(diemDen);
        tvSDT.setText(sdt);
        tvSoLuong.setText(String.valueOf(soLuong));
        tvGia.setText(String.valueOf(gia));
        tvThoiGian.setText(thoiGian);
        tvTenKH.setText(tenKH);
        mapKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("soe", "start");
                b.putString("startPosition", tvDiemKhoiHanh.getText().toString());
                Intent i = new Intent(HuyChuyenKhActivity.this, MapKhoiHanhActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        mapDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("soe", "end");
                b.putString("endPosition", tvDiemDen.getText().toString());
                Intent i = new Intent(HuyChuyenKhActivity.this, MapKhoiHanhActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HuyChuyenKhActivity.this, "huy chuyen", Toast.LENGTH_SHORT).show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("DonDat").document(maDonDat).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Intent intent = new Intent(HuyChuyenKhActivity.this, DonDatKhachHangActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}