package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.activity.ThongTinKHActivity;
import com.sonans.appdatxe_duan01_nhom6.adapter.KhachHangAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KhachHangFragment extends Fragment {

    ArrayList<KhachHang> userList = new ArrayList<>();
    FirebaseFirestore db;
    KhachHangAdapter adapter;
    RecyclerView rcv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_khach_hang, container, false);
        db = FirebaseFirestore.getInstance();
        CollectionReference khachHangRef = db.collection("KhachHang");
        ListenFirebaseFirestore();
        // anh xa

        adapter = new KhachHangAdapter(userList, getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

        adapter.setItemClickListener(new KhachHangAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("iou", "update");
                bundle.putString("ten_khach_hang", userList.get(position).getTenKhachHang());
                bundle.putString("soDT", userList.get(position).getSoDT());
                bundle.putString("tenDN_khach_hang", userList.get(position).getTenDangNhap());
                bundle.putString("matKhau_khach_hang", userList.get(position).getMatKhau());
                Intent i = new Intent(getActivity(), ThongTinKHActivity.class);
                i.putExtras(bundle);
                startActivity(i);

            }
        });
        return v;
    }

    private void ListenFirebaseFirestore(){
        db.collection("KhachHang").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("TAG", "fail", error);
                    return;
                }
                if(value != null){
                    for (DocumentChange dc: value.getDocumentChanges()){
                        switch (dc.getType()){
                            case ADDED:{
                                KhachHang newU = dc.getDocument().toObject(KhachHang.class);
                                userList.add(newU);
                                adapter.notifyItemInserted(userList.size() - 1);
                                break;
                            }
                            case MODIFIED:{
                                KhachHang update = dc.getDocument().toObject(KhachHang.class);
                                if(dc.getOldIndex() == dc.getNewIndex()){
                                    userList.set(dc.getOldIndex(), update);
                                    adapter.notifyItemChanged(dc.getOldIndex());

                                } else {
                                    userList.remove(dc.getOldIndex());
                                    userList.add(update);
                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                }
                                break;
                            }
                            case REMOVED:{
                                dc.getDocument().toObject(KhachHang.class);
                                userList.remove(dc.getOldIndex());
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