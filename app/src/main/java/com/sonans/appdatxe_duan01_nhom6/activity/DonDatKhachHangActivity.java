package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.adapter.DonDatAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonDatKhachHangActivity extends AppCompatActivity {

    ArrayList<DonDat> list = new ArrayList<>();
    FirebaseFirestore db;
    DonDatAdapter adapter;
    RecyclerView rcv;
    FloatingActionButton fab;
    ImageView btnBack;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Button btnDatChuyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_dat_khach_hang);
        rcv = findViewById(R.id.rcvDonDat_KhachHang);
        btnBack = findViewById(R.id.back);
        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();
        adapter = new DonDatAdapter(list, this,db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        btnDatChuyen = findViewById(R.id.btnDatChuyenKhachHang);
        btnDatChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DonDatKhachHangActivity.this, DatChuyenActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DonDatKhachHangActivity.this, KhachHangActivity.class);
                startActivity(i);
            }
        });
    }

    private void ListenFirebaseFirestore() {
        db.collection("DonDat").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("zzzzz", "fail", error);
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        DonDat donDat = dc.getDocument().toObject(DonDat.class);
                        SharedPreferences sp = getSharedPreferences("MaKHang", MODE_PRIVATE);
                        String userRemember = sp.getString("maKH", "");
                        // Kiểm tra điều kiện
                        if (userRemember.equals(donDat.getMaKhachDat())) {
                            switch (dc.getType()) {
                                case ADDED:
                                    list.add(donDat);
                                    adapter.notifyItemInserted(list.size() - 1);
                                    break;
                                case MODIFIED:
                                    if (dc.getOldIndex() == dc.getNewIndex()) {
                                        list.set(dc.getOldIndex(), donDat);
                                        adapter.notifyItemChanged(dc.getOldIndex());
                                    } else {
                                        list.remove(dc.getOldIndex());
                                        list.add(donDat);
                                        adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());
                                    }
                                    break;
                                case REMOVED:
                                    list.remove(dc.getOldIndex());
                                    adapter.notifyItemRemoved(dc.getOldIndex());
                                    break;
                            }
                        }
                    }
                }
            }
        });
    }
}