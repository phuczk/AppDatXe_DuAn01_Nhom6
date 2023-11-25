package com.sonans.appdatxe_duan01_nhom6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;
import com.sonans.appdatxe_duan01_nhom6.model.DonNhan;

import java.text.SimpleDateFormat;
import java.util.List;

public class DonNhanAdapter extends RecyclerView.Adapter<DonNhanAdapter.ViewHolder>{

    List<DonNhan> list;
    FirebaseFirestore database;
    Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DonNhanAdapter(List<DonNhan> list, Context context, FirebaseFirestore database) {
        this.list = list;
        this.context = context;
        this.database = database;
    }
    @NonNull
    @Override
    public DonNhanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_don_dat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.diemKhoiHanh.setText(list.get(position).getDiemBatDau());
        holder.diemDen.setText(list.get(position).getDiemDen());
        holder.thoiGian.setText(sdf.format(list.get(position).getNgayKhoiHanh()));
        holder.soDT.setText(list.get(position).getSdtKhachHang());
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

        TextView diemKhoiHanh, diemDen, thoiGian, soDT;
        ImageView btnUp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diemKhoiHanh = itemView.findViewById(R.id.donDat_diemKhoiHanh);
            diemDen = itemView.findViewById(R.id.donDat_diemDen);
            thoiGian = itemView.findViewById(R.id.donDat_thoiGian);
            soDT = itemView.findViewById(R.id.donDat_sdt);

        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


    private DonNhanAdapter.ItemClickListener itemClickListener;
    public void setItemClickListener(DonNhanAdapter.ItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
