package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.UUID;

public class SignUpTaiXeActivity2 extends AppCompatActivity {

    EditText edUserName, edPassword;
    Button btnSignUp;
    String name, phone;
    int age;
    DatabaseReference reference;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tai_xe2);
        edUserName = findViewById(R.id.ed_UserName);
        edPassword = findViewById(R.id.ed_Password);
        btnSignUp = findViewById(R.id.btnSignUp);
        String UserName = edUserName.getText().toString().trim();
        String Password = edPassword.getText().toString().trim();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            name = bundle.getString("name");
            age = bundle.getInt("age");
            phone = bundle.getString("phone");
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference taiXeRef = db.collection("TaiXe");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDangNhapMoi = edUserName.getText().toString();

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
                                        String tenDN = edUserName.getText().toString();
                                        String matKhau = edPassword.getText().toString();
                                        TaiXe taiXe = new TaiXe(maTaiXe, name, age, phone, tenDN, matKhau);

                                        database = FirebaseDatabase.getInstance();
                                        reference = database.getReference("TaiXe");
                                        reference.child(tenDangNhapMoi).setValue(taiXe);
                                        taiXeRef.document(maTaiXe)
                                                .set(taiXe)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(SignUpTaiXeActivity2.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                                                        Bundle b = new Bundle();
                                                        b.putString("userName", "");
                                                        b.putString("pass", "");
                                                        Intent i = new Intent(SignUpTaiXeActivity2.this, LoginActivity.class);
                                                        i.putExtras(b);
                                                        startActivity(i);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(SignUpTaiXeActivity2.this, "dang ky that bai", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                        Toast.makeText(SignUpTaiXeActivity2.this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Xử lý khi truy vấn không thành công
                                    Toast.makeText(SignUpTaiXeActivity2.this, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}