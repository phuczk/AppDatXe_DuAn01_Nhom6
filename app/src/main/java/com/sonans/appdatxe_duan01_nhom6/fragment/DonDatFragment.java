package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.activity.DatChuyenActivity;
import com.sonans.appdatxe_duan01_nhom6.activity.ThongTinKHActivity;
import com.sonans.appdatxe_duan01_nhom6.adapter.DonDatAdapter;
import com.sonans.appdatxe_duan01_nhom6.adapter.KhachHangAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;


public class DonDatFragment extends Fragment {

    ArrayList<DonDat> list = new ArrayList<>();
    FirebaseFirestore db;
    DonDatAdapter adapter;
    RecyclerView rcv;
    FloatingActionButton fab;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    FirebaseUser currentUser = auth.getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_don_dat, container, false);
        fab = v.findViewById(R.id.fab_donDat);
        rcv = v.findViewById(R.id.rcvDonDat);

        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();
        adapter = new DonDatAdapter(list, getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        adapter.setItemClickListener(new DonDatAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("iouDonDat", "update");
                bundle.putString("maDonDat", list.get(position).getMaDonDat());
                bundle.putString("tenKhachHang", list.get(position).getTenKhachHang());
                bundle.putString("soDTKhachHang", list.get(position).getSdtKhachHang());
                bundle.putString("diemKhoiHanh", list.get(position).getDiemBatDau());
                bundle.putString("diemDen", list.get(position).getDiemDen());
                bundle.putString("thoiGian", sdf.format(list.get(position).getNgayKhoiHanh()));
                bundle.putInt("giaCuoc", list.get(position).getGiaCuoc());
                bundle.putString("maKhachHang", list.get(position).getMaKhachDat());
                bundle.putInt("soLuong", list.get(position).getSoLuongKhach());
                Intent i = new Intent(getActivity(), DatChuyenActivity.class);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("iouDonDat", "insert");
                Intent i = new Intent(getActivity(), DatChuyenActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        return v;
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