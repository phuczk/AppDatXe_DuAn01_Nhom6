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
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("ThongTinTX", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("ten", edTen.getText().toString());
                edit.putInt("tuoi", Integer.parseInt(edTuoi.getText().toString()));
                edit.putString("sdt", edSDT.getText().toString());
                edit.commit();
                Intent i = new Intent(SignUpTaiXeActivity.this, SignUpTX1_1Activity2.class);
                startActivity(i);
            }
        });

    }
}