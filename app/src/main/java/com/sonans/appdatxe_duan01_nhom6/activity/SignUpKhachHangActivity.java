package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.util.UUID;

public class SignUpKhachHangActivity extends AppCompatActivity {

    EditText edTenKhachHang, edSoDT, edTenDNKhachHang, edMatKhauKhachHang;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_khach_hang);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference khachHangRef = db.collection("KhachHang");
        Intent i = new Intent();
        String maKhachHang = UUID.randomUUID().toString();
        String tenKhachHang = edTenKhachHang.getText().toString();
        String soDT = edSoDT.getText().toString();
        String tenDNKhachHang = edTenDNKhachHang.getText().toString();
        String matKhauKhachHang = edMatKhauKhachHang.getText().toString();
        KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, soDT, tenDNKhachHang, matKhauKhachHang);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khachHangRef.document(maKhachHang)
                        .set(khachHang)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUpKhachHangActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpKhachHangActivity.this, "dang ky that bai", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}