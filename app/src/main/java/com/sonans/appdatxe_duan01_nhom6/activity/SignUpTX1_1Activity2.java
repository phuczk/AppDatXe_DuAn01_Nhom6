package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;

public class SignUpTX1_1Activity2 extends AppCompatActivity {

    TextView tv1, tv2, tv3;
    Button btnNext;
    FirebaseFirestore db;
    boolean isCCCD, isLoaiXe, isBienSo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tx112);
        tv1 = findViewById(R.id.ed_CCCD);
        tv2 = findViewById(R.id.ed_BienSo);
        tv3 = findViewById(R.id.ed_LoaiXe);
        btnNext = findViewById(R.id.btnNext);
        db = FirebaseFirestore.getInstance();
        String cccd1 = tv1.getText().toString();
        String bienSo = tv2.getText().toString();
        String loaiXe = tv3.getText().toString();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("TaiXe")
                        .whereEqualTo("canCuoc", tv1.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().isEmpty()) {
                                        isCCCD = true;
                                    }else {
                                        Toast.makeText(SignUpTX1_1Activity2.this, "can cuoc cong dan da ton tai vui long nhap lai", Toast.LENGTH_SHORT).show();
                                        tv1.setError("vui long nhap lai");
                                    }
                                }else {
                                    Toast.makeText(SignUpTX1_1Activity2.this, "loi khi kiem tra", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                db.collection("TaiXe")
                        .whereEqualTo("bienSo", bienSo)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().isEmpty()) {
                                        isBienSo = true;
                                    }else {
                                        Toast.makeText(SignUpTX1_1Activity2.this, "bien so da ton tai vui long nhap lai", Toast.LENGTH_SHORT).show();
                                        tv2.setError("vui long nhap lai");
                                    }
                                }else {
                                    Toast.makeText(SignUpTX1_1Activity2.this, "loi khi kiem tra", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                if(isCCCD  && isBienSo){
                    SharedPreferences sp = getSharedPreferences("ThongTinXe", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("bienSo", tv2.getText().toString());
                    edit.putString("canCuoc", tv1.getText().toString());
                    edit.putString("loaiXe", tv3.getText().toString());
                    edit.commit();
                    Intent i = new Intent(SignUpTX1_1Activity2.this, SignUpTaiXeActivity2.class);
                    startActivity(i);
                }
            }
        });
    }

}