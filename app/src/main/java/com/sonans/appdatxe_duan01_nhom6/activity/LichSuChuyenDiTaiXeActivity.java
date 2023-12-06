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
import com.sonans.appdatxe_duan01_nhom6.adapter.DonNhanAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;
import com.sonans.appdatxe_duan01_nhom6.model.DonNhan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LichSuChuyenDiTaiXeActivity extends AppCompatActivity {

    ArrayList<DonNhan> list = new ArrayList<>();
    FirebaseFirestore db;
    DonNhanAdapter adapter;
    RecyclerView rcv;
    FloatingActionButton fab;
    ImageView btnBack;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Button btnDatChuyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_chuyen_di_tai_xe);
        rcv = findViewById(R.id.rcvDonNhan_TaiXe);
        btnBack = findViewById(R.id.back);
        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();
        adapter = new DonNhanAdapter(list, this,db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LichSuChuyenDiTaiXeActivity.this, TaiXeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void ListenFirebaseFirestore() {
        db.collection("DonNhan").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("zzzzz", "fail", error);
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        DonNhan donNhan = dc.getDocument().toObject(DonNhan.class);
                        SharedPreferences sp = getSharedPreferences("maTX", MODE_PRIVATE);
                        String userRemember = sp.getString("id", "");
                        // Kiểm tra điều kiện
                        if (userRemember.equals(donNhan.getMaTaiXe())) {
                            switch (dc.getType()) {
                                case ADDED:
                                    list.add(donNhan);
                                    adapter.notifyItemInserted(list.size() - 1);
                                    break;
                                case MODIFIED:
                                    if (dc.getOldIndex() == dc.getNewIndex()) {
                                        list.set(dc.getOldIndex(), donNhan);
                                        adapter.notifyItemChanged(dc.getOldIndex());
                                    } else {
                                        list.remove(dc.getOldIndex());
                                        list.add(donNhan);
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