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

import com.sonans.appdatxe_duan01_nhom6.R;

public class ThongTinChuyenDiActivity extends AppCompatActivity {

    TextView tvDiemKhoiHanh, tvDiemDen, tvTenKH, tvSDT, tvSoLuong, tvGia, tvThoiGian;
    Button btnNhanChuyen;
    ImageView back;
    LinearLayout mapKH, mapDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chuyen_di);
        tvDiemKhoiHanh = findViewById(R.id.tvDiemKhoiHanh);
        tvDiemDen = findViewById(R.id.tvDiemDen);
        tvTenKH = findViewById(R.id.tvName);
        tvSDT = findViewById(R.id.tvPhoneNumber);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvGia = findViewById(R.id.tvPrice);
        tvThoiGian = findViewById(R.id.tvThoiZan);
        btnNhanChuyen = findViewById(R.id.btnNhanChuyen);
        mapKH = findViewById(R.id.mapKH);
        mapDD = findViewById(R.id.mapDD);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ThongTinChuyenDiActivity.this, DonDatTaiXeActivity.class);
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
                Intent i = new Intent(ThongTinChuyenDiActivity.this, MapKhoiHanhActivity.class);
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
                Intent i = new Intent(ThongTinChuyenDiActivity.this, MapKhoiHanhActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        btnNhanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("maDon", maDonDat);
                edit.putString("SDT", sdt);
                edit.putString("START", diemKH);
                edit.putString("END", diemDen);
                edit.commit();
                Intent i = new Intent(ThongTinChuyenDiActivity.this, DonKhachActivity.class);
                startActivity(i);

            }
        });
    }
}