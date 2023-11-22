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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.HashMap;
import java.util.UUID;

public class ThongTinTXActivity extends AppCompatActivity {

    EditText edTen,edTuoi, edSDT, edTenDN, edMatKhau;
    Button btnCancel, btnOk;

    String ma,ten,tuoi, sdt, tenDN, matKhau, iou;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_txactivity);
        edTen = findViewById(R.id.edTen);
        edSDT = findViewById(R.id.edSDT);
        edTuoi = findViewById(R.id.edTuoi);
        edMatKhau = findViewById(R.id.edMatKhau);
        edTenDN = findViewById(R.id.edTenDN);

        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancle);

        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ma = bundle.getString("ma_tai_xe");
            ten = bundle.getString("ten_tai_xe");
            sdt = bundle.getString("soDT_tx");
            tuoi = String.valueOf(bundle.getInt("tuoi_tai_xe"));
            tenDN = bundle.getString("tenDN_tai_xe");
            matKhau = bundle.getString("matKhau_tai_xe");
            iou = bundle.getString("iouTX");
        }
        if(iou.equals("update")){
            edTen.setText(ten);
            edSDT.setText(sdt);
            edTuoi.setText(tuoi);
            edTenDN.setText(tenDN);
            edMatKhau.setText(matKhau);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ten = edTen.getText().toString();
                    String sdt = edSDT.getText().toString();
                    int tuoi = Integer.parseInt(edTuoi.getText().toString());
                    String tenDN = edTenDN.getText().toString();
                    String matKhau = edMatKhau.getText().toString();
                    TaiXe taiXe = new TaiXe(ma, ten,tuoi, sdt, tenDN, matKhau);
                    HashMap<String, Object> map = taiXe.convertHashMap();
                    db.collection("TaiXe").document(ma)
                            .update(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(ThongTinTXActivity.this, MainActivity.class);
                                    intent.putExtra("back_to_fragment2", true);
                                    startActivity(intent);
                                    Toast.makeText(ThongTinTXActivity.this, "cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ThongTinTXActivity.this, "cap nhat that bai", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        }else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newName = edTenDN.getText().toString();
                    db.collection("TaiXe")
                            .whereEqualTo("tenDN_TaiXe", newName)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                        if (task.getResult().isEmpty()) {
                                            String maTX = UUID.randomUUID().toString();
                                            String ten = edTen.getText().toString();
                                            int tuoi = Integer.parseInt(edTuoi.getText().toString());
                                            String sdt = edSDT.getText().toString();
                                            String tenDN = edTenDN.getText().toString();
                                            String matKhau = edMatKhau.getText().toString();
                                            TaiXe taiXe = new TaiXe(maTX, ten,tuoi, sdt, tenDN, matKhau);
                                            HashMap<String, Object> map = taiXe.convertHashMap();
                                            db.collection("TaiXe").document(maTX)
                                                    .set(map)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Intent intent = new Intent(ThongTinTXActivity.this, MainActivity.class);
                                                            intent.putExtra("back_to_fragment2", true);
                                                            startActivity(intent);
                                                            Toast.makeText(ThongTinTXActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(ThongTinTXActivity.this, "them that bai", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                            Toast.makeText(ThongTinTXActivity.this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Xử lý khi truy vấn không thành công
                                        Toast.makeText(ThongTinTXActivity.this, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ThongTinTXActivity.this, MainActivity.class);
                    intent.putExtra("back_to_fragment2", false);
                    startActivity(intent);
                }
            });
        }
    }
}