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
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.adapter.DonNhanAdapter;
import com.sonans.appdatxe_duan01_nhom6.adapter.HoaDonAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.DonNhan;
import com.sonans.appdatxe_duan01_nhom6.model.HoaDonTX;

import java.util.ArrayList;

public class HoaDonActivity extends AppCompatActivity {

    RecyclerView rcv;
    ImageView img;

    ArrayList<HoaDonTX> list = new ArrayList<>();
    FirebaseFirestore db;
    HoaDonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        rcv = findViewById(R.id.rcvHoaDon);
        img = findViewById(R.id.back);

        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();
        adapter = new HoaDonAdapter(list, this,db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoaDonActivity.this, TaiXeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void ListenFirebaseFirestore() {
        db.collection("HoaDon").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("zzzzz", "fail", error);
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        HoaDonTX hoaDonTX = dc.getDocument().toObject(HoaDonTX.class);
                        SharedPreferences sp = getSharedPreferences("maTX", MODE_PRIVATE);
                        String userRemember = sp.getString("id", "");
                        // Kiểm tra điều kiện
                        if (userRemember.equals(hoaDonTX.getMaTX())) {
                            switch (dc.getType()) {
                                case ADDED:
                                    list.add(hoaDonTX);
                                    adapter.notifyItemInserted(list.size() - 1);
                                    break;
                                case MODIFIED:
                                    if (dc.getOldIndex() == dc.getNewIndex()) {
                                        list.set(dc.getOldIndex(), hoaDonTX);
                                        adapter.notifyItemChanged(dc.getOldIndex());
                                    } else {
                                        list.remove(dc.getOldIndex());
                                        list.add(hoaDonTX);
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