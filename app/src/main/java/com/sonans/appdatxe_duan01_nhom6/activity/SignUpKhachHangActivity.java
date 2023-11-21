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

import java.util.UUID;

public class SignUpKhachHangActivity extends AppCompatActivity {

    EditText edTenKhachHang, edSoDT, edTenDNKhachHang, edMatKhauKhachHang;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_khach_hang);
        edTenKhachHang = findViewById(R.id.ed_NameSignUp);
        edSoDT = findViewById(R.id.ed_NumberPhoneSignUp);
        edTenDNKhachHang = findViewById(R.id.ed_UserName);
        edMatKhauKhachHang = findViewById(R.id.ed_Password);
        btnSignUp = findViewById(R.id.btnSignUp);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhapMoi = edTenDNKhachHang.getText().toString();

                db.collection("KhachHang")
                        .whereEqualTo("tenDangNhap", tenDangNhapMoi)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                    if (task.getResult().isEmpty()) {
                                        String maKH = UUID.randomUUID().toString();
                                        String tenKH = edTenKhachHang.getText().toString();
                                        String sdt = edSoDT.getText().toString();
                                        String tenDN = edTenDNKhachHang.getText().toString();
                                        String matKhau = edMatKhauKhachHang.getText().toString();
                                        KhachHang khachHang1 = new KhachHang(maKH, tenKH, sdt, tenDN, matKhau);
                                        DatabaseReference reference;
                                        FirebaseDatabase database;
                                        database = FirebaseDatabase.getInstance();
                                        reference = database.getReference("KhachHang");
                                        reference.child(tenDangNhapMoi).setValue(khachHang1);
                                        db.collection("KhachHang").document(maKH)
                                                .set(khachHang1)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(SignUpKhachHangActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                                                        Bundle b = new Bundle();
                                                        b.putString("userName", "");
                                                        b.putString("pass", "");
                                                        Intent i = new Intent(SignUpKhachHangActivity.this, LoginKhachHangActivity.class);
                                                        i.putExtras(b);
                                                        startActivity(i);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(SignUpKhachHangActivity.this, "dang ky that bai", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                        Toast.makeText(SignUpKhachHangActivity.this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Xử lý khi truy vấn không thành công
                                    Toast.makeText(SignUpKhachHangActivity.this, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}