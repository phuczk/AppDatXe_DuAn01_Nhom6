package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.activity.ThongTinKHActivity;
import com.sonans.appdatxe_duan01_nhom6.activity.ThongTinTXActivity;
import com.sonans.appdatxe_duan01_nhom6.adapter.KhachHangAdapter;
import com.sonans.appdatxe_duan01_nhom6.adapter.TaiXeAdapter;
import com.sonans.appdatxe_duan01_nhom6.database.DbHelper;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TaiXeFragment extends Fragment {

    ArrayList<TaiXe> list = new ArrayList<>();
    FirebaseFirestore db;
    TaiXeAdapter adapter;
    RecyclerView rcv;
    FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tai_xe, container, false);
        rcv = v.findViewById(R.id.rcvTaiXe);
        fab = v.findViewById(R.id.fab_TX_fr);
        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();
        adapter = new TaiXeAdapter(list, getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

        adapter.setItemClickListener(new TaiXeAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("iouTX", "update");
                bundle.putString("ma_tai_xe", list.get(position).getMaTaiXe());
                bundle.putString("ten_tai_xe", list.get(position).getTenTaiXe());
                bundle.putInt("tuoi_tai_xe", list.get(position).getTuoiTaiXe());
                bundle.putString("soDT_tx", list.get(position).getSdtTaiXe());
                bundle.putString("tenDN_tai_xe", list.get(position).getTenDN_TaiXe());
                bundle.putString("matKhau_tai_xe", list.get(position).getMatKhauTaiXe());
                Intent i = new Intent(getActivity(), ThongTinTXActivity.class);
                i.putExtras(bundle);
                startActivity(i);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("iouTX", "insert");
                Intent i = new Intent(getActivity(), ThongTinTXActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        return v;
    }

    private void ListenFirebaseFirestore(){
        db.collection("TaiXe").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                TaiXe newU = dc.getDocument().toObject(TaiXe.class);
                                list.add(newU);
                                adapter.notifyItemInserted(list.size() - 1);

                                break;
                            }
                            case MODIFIED:{
                                TaiXe update = dc.getDocument().toObject(TaiXe.class);
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
                                dc.getDocument().toObject(TaiXe.class);
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


    Dialog dialog;
    int position;
    EditText tenTX, tuoiTX, sdtTX, tenDNTX, matKhauTX;
    Button btnOK;
    public void openDialog(final Context context, final int type){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_ql_tai_xe);
        tenTX = dialog.findViewById(R.id.ed_tenTX_dialog);
        tuoiTX = dialog.findViewById(R.id.ed_tuoiTX_dialog);
        sdtTX = dialog.findViewById(R.id.ed_sdtTX_dialog);
        tenDNTX = dialog.findViewById(R.id.ed_tenDNTX_dialog);
        matKhauTX = dialog.findViewById(R.id.ed_matKhauTX_dialog);
        btnOK = dialog.findViewById(R.id.btnOK_TX);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTaiXe = UUID.randomUUID().toString();
                String tenTaiXe = tenTX.getText().toString();
                int tuoiTaiXe = Integer.parseInt(tuoiTX.getText().toString());
                String sdtTaiXe = sdtTX.getText().toString();
                String tenDNTaiXe = tenDNTX.getText().toString();
                String matKhauTaiXe = matKhauTX.getText().toString();

                TaiXe taiXe = new TaiXe(maTaiXe, tenTaiXe,tuoiTaiXe , sdtTaiXe, tenDNTaiXe, matKhauTaiXe);
                HashMap<String, Object> map = taiXe.convertHashMap();
                db.collection("TaiXe").document(maTaiXe).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "id: "+ maTaiXe, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}