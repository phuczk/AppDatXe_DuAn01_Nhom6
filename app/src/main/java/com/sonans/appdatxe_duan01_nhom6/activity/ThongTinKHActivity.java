package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.adapter.KhachHangAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ThongTinKHActivity extends AppCompatActivity {

    EditText edTen, edSDT, edTenDN, edMatKhau;
    Button btnCancel, btnOk;

    String ma,ten, sdt, tenDN, matKhau, iou;
    ArrayList<KhachHang> userList = new ArrayList<>();
    FirebaseFirestore db;
    KhachHangAdapter adapter;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khactivity);
// anh xa
        edTen = findViewById(R.id.edTen);
        edSDT = findViewById(R.id.edSDT);
        edMatKhau = findViewById(R.id.edMatKhau);
        edTenDN = findViewById(R.id.edTenDN);

        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancle);

        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ma = bundle.getString("ma_khach_hang");
            ten = bundle.getString("ten_khach_hang");
            sdt = bundle.getString("soDT");
            tenDN = bundle.getString("tenDN_khach_hang");
            matKhau = bundle.getString("matKhau_khach_hang");
            iou = bundle.getString("iou");
        }
        if(iou.equals("update")){
            edTen.setText(ten);
            edSDT.setText(sdt);
            edTenDN.setText(tenDN);
            edMatKhau.setText(matKhau);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ten = edTen.getText().toString();
                    String sdt = edSDT.getText().toString();
                    String tenDN = edTenDN.getText().toString();
                    String matKhau = edMatKhau.getText().toString();
                    KhachHang khachHang = new KhachHang(ma, ten, sdt, tenDN, matKhau);
                    HashMap<String, Object> mapKhachHang = khachHang.convertHashMap();
                    db.collection("KhachHang").document(ma)
                            .update(mapKhachHang)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(ThongTinKHActivity.this, MainActivity.class);
                                    intent.putExtra("back_to_fragment1", true);
                                    startActivity(intent);
                                    Toast.makeText(ThongTinKHActivity.this, "cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ThongTinKHActivity.this, "cap nhat that bai", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        }else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String maKH = UUID.randomUUID().toString();
                    String ten = edTen.getText().toString();
                    String sdt = edSDT.getText().toString();
                    String tenDN = edTenDN.getText().toString();
                    String matKhau = edMatKhau.getText().toString();
                    KhachHang khachHang = new KhachHang(maKH, ten, sdt, tenDN, matKhau);
                    HashMap<String, Object> mapKhachHang = khachHang.convertHashMap();
                    db.collection("KhachHang").document(maKH)
                            .set(mapKhachHang)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(ThongTinKHActivity.this, MainActivity.class);
                                    intent.putExtra("back_to_fragment1", true);
                                    startActivity(intent);
                                    Toast.makeText(ThongTinKHActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ThongTinKHActivity.this, "them that bai", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

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
}