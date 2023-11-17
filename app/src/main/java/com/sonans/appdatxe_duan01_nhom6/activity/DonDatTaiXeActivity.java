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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.adapter.DonDatAdapter;
import com.sonans.appdatxe_duan01_nhom6.adapter.TaiXeAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonDatTaiXeActivity extends AppCompatActivity {

    ArrayList<DonDat> list = new ArrayList<>();
    FirebaseFirestore db;
    DonDatAdapter adapter;
    RecyclerView rcv;
    FloatingActionButton fab;
    ImageView btnBack;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_dat_tai_xe);
        rcv = findViewById(R.id.rcvDonDat_TaiXe);
        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();
        adapter = new DonDatAdapter(list, this,db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DonDatTaiXeActivity.this, TaiXeActivity.class);
                startActivity(i);
                finish();
            }
        });

        adapter.setItemClickListener(new DonDatAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SharedPreferences sp = getSharedPreferences("DonDat", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("maDonDat", list.get(position).getMaDonDat());
                edit.putString("tenKhachHang", list.get(position).getTenKhachHang());
                edit.putString("soDTKhachHang", list.get(position).getSdtKhachHang());
                edit.putString("diemKhoiHanh", list.get(position).getDiemBatDau());
                edit.putString("diemDen", list.get(position).getDiemDen());
                edit.putString("thoiGian", sdf.format(list.get(position).getNgayKhoiHanh()));
                edit.putInt("giaCuoc", list.get(position).getGiaCuoc());
                edit.putString("maKhachHang", list.get(position).getMaKhachDat());
                edit.putInt("soLuong", list.get(position).getSoLuongKhach());
                edit.commit();
                Intent i = new Intent(DonDatTaiXeActivity.this, ThongTinChuyenDiActivity.class);
//                i.putExtras(bundle);
                startActivity(i);

            }
        });
    }

    private void ListenFirebaseFirestore(){
        db.collection("DonDat").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("zzzzz", "fail", error);
                    return;
                }
                if(value != null){
                    for (DocumentChange dc: value.getDocumentChanges()){
                        switch (dc.getType()){
                            case ADDED:{
                                DonDat newU = dc.getDocument().toObject(DonDat.class);
                                list.add(newU);
                                adapter.notifyItemInserted(list.size() - 1);

                                break;
                            }
                            case MODIFIED:{
                                DonDat update = dc.getDocument().toObject(DonDat.class);
                                if(dc.getOldIndex() == dc.getNewIndex()){
                                    list.set(dc.getOldIndex(), update);
                                    adapter.notifyItemChanged(dc.getOldIndex());

                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(update);
                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                }
                                break;
                            }
                            case REMOVED:{
                                dc.getDocument().toObject(DonDat.class);
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