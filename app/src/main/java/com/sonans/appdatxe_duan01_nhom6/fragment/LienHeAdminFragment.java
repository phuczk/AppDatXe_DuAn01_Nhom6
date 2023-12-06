package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.activity.CSKHActivity;
import com.sonans.appdatxe_duan01_nhom6.adapter.CSKHAdapter;
import com.sonans.appdatxe_duan01_nhom6.adapter.KhachHangAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.CSKH;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.util.ArrayList;

public class LienHeAdminFragment extends Fragment {
    ArrayList<CSKH> list = new ArrayList<>();
    FirebaseFirestore db;
    CSKHAdapter adapter;
    RecyclerView rcv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lien_he_admin, container, false);
        rcv = v.findViewById(R.id.rcvCSKH);
        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();
        adapter = new CSKHAdapter(list, getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        adapter.setItemClickListener(new CSKHAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SharedPreferences sp = getContext().getSharedPreferences("chamSocKH", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("sdt", list.get(position).getSdt());
                edit.putString("noiDung", list.get(position).getNoiDung());
                edit.commit();
                Intent i = new Intent(getActivity(), CSKHActivity.class);
                startActivity(i);


            }
        });
        return v;
    }

    private void ListenFirebaseFirestore(){
        db.collection("CSKH").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                CSKH newU = dc.getDocument().toObject(CSKH.class);
                                list.add(newU);
                                adapter.notifyItemInserted(list.size() - 1);

                                break;
                            }
                            case MODIFIED:{
                                CSKH update = dc.getDocument().toObject(CSKH.class);
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
                                dc.getDocument().toObject(CSKH.class);
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