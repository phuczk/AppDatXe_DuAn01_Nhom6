package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.ArrayList;
import java.util.UUID;

public class SignUpTaiXeActivity extends AppCompatActivity {

    EditText edTen, edTuoi, edSDT, edTenDN, edMatKhau;
    Button btnSignUp;
    DatabaseReference reference;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tai_xe);
        // anh xa
        edTen = findViewById(R.id.ed_NameSignUp);
        edTuoi = findViewById(R.id.ed_AgeSignUp);
        edSDT = findViewById(R.id.ed_NumberPhoneSignUp);
        edTenDN = findViewById(R.id.ed_UserName);
        edMatKhau = findViewById(R.id.ed_Password);
        btnSignUp = findViewById(R.id.btnSignUp);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference taiXeRef = db.collection("TaiXe");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện truy vấn để kiểm tra xem tên đăng nhập đã tồn tại trong Firebase chưa
                String tenDangNhapMoi = edTenDN.getText().toString();

                db.collection("TaiXe")
                        .whereEqualTo("tenDN_TaiXe", tenDangNhapMoi)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                    if (task.getResult().isEmpty()) {
                                        String maTaiXe = UUID.randomUUID().toString();
                                        String tenTaiXe = edTen.getText().toString();
                                        int tuoi = Integer.parseInt(edTuoi.getText().toString());
                                        String soDT = edSDT.getText().toString();
                                        String tenDN = edTenDN.getText().toString();
                                        String matKhau = edMatKhau.getText().toString();
                                        TaiXe taiXe = new TaiXe(maTaiXe, tenTaiXe, tuoi, soDT, tenDN, matKhau);

                                        database = FirebaseDatabase.getInstance();
                                        reference = database.getReference("TaiXe");
                                        reference.child(tenDangNhapMoi).setValue(taiXe);
                                        taiXeRef.document(maTaiXe)
                                                .set(taiXe)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(SignUpTaiXeActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(SignUpTaiXeActivity.this, LoginActivity.class);
                                                        startActivity(i);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(SignUpTaiXeActivity.this, "dang ky that bai", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                        Toast.makeText(SignUpTaiXeActivity.this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Xử lý khi truy vấn không thành công
                                    Toast.makeText(SignUpTaiXeActivity.this, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}