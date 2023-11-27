package com.sonans.appdatxe_duan01_nhom6.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.database.DbHelper;
import com.sonans.appdatxe_duan01_nhom6.fragment.KhachHangFragment;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.util.HashMap;
import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder>{

    List<KhachHang> list;
    Context context;

    FirebaseFirestore database;

    public KhachHangAdapter(List<KhachHang> list, Context context, FirebaseFirestore database) {
        this.list = list;
        this.context = context;
        this.database = database;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_khach_hang, parent, false);
        return new ViewHolder(view);
    }

    KhachHang item;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item = list.get(position);
        holder.tenKhachHang_rcv.setText(item.getTenKhachHang()+"");
        holder.soDTKhachHang_rcv.setText(item.getSoDT()+"");
        holder.tenDNKhachHang_rcv.setText("********");
        holder.matKhauKhachHang_rcv.setText("********");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    int positionz = position;
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(positionz);
                    }

                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tenKhachHang_rcv, soDTKhachHang_rcv, tenDNKhachHang_rcv, matKhauKhachHang_rcv;
        ImageView btnUp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenKhachHang_rcv = itemView.findViewById(R.id.tv_tenKH);
            soDTKhachHang_rcv = itemView.findViewById(R.id.tv_sdtKH);
            tenDNKhachHang_rcv = itemView.findViewById(R.id.tv_tenDNKH);
            matKhauKhachHang_rcv = itemView.findViewById(R.id.tv_matKhauKH);

        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
