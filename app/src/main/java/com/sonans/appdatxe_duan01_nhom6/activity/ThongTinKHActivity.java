package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sonans.appdatxe_duan01_nhom6.R;

public class ThongTinKHActivity extends AppCompatActivity {

    EditText edTen, edSDT, edTenDN, edMatKhau;
    Button btnCancel, btnOk;

    String ten, sdt, tenDN, matKhau, iou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khactivity);
// anh xa


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ten = bundle.getString("ten_khach_hang");
            sdt = bundle.getString("soDT");
            tenDN = bundle.getString("tenDN_khach_hang");
            matKhau = bundle.getString("matKhau_khach_hang");
            iou = bundle.getString("iou");
        }
        if(iou.equals("update")){
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinKHActivity.this, MainActivity.class);
                intent.putExtra("back_to_fragment1", true);
                startActivity(intent);
            }
        });
    }
}