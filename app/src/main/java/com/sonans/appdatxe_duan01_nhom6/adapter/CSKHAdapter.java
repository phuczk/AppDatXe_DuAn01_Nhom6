package com.sonans.appdatxe_duan01_nhom6.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.activity.PhanQuyenActivity;
import com.sonans.appdatxe_duan01_nhom6.activity.WelcomeActivity;
import com.sonans.appdatxe_duan01_nhom6.model.CSKH;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class CSKHAdapter extends RecyclerView.Adapter<CSKHAdapter.ViewHolder>{

    List<CSKH> list;
    FirebaseFirestore database;
    Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public CSKHAdapter(List<CSKH> list, Context context, FirebaseFirestore database) {
        this.list = list;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public CSKHAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cskh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CSKHAdapter.ViewHolder holder, int position) {
        holder.tvSDT.setText(list.get(position).getSdt());
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
        database = FirebaseFirestore.getInstance();
        if(list.get(position).getTrangThai() == 0){
            holder.btn.setChecked(false);
        }else {
            holder.btn.setChecked(true);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int boolz;
                if (list.get(position).getTrangThai() == 0) {
                    boolz = 1;
                } else {
                    boolz = 0;
                }
                CSKH cskh = new CSKH(list.get(position).getMaCSKH(), list.get(position).getNoiDung(), list.get(position).getSdt(), boolz);
                HashMap<String, Object> map = cskh.convertHashMap();
                database.collection("CSKH").document(list.get(position).getMaCSKH()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Trạng thái đã được cập nhật, hãy cập nhật ToggleButton ngay tại đây
                        holder.btn.setChecked(boolz == 1);

                        // Hiển thị Toast thông báo
                        Toast.makeText(context, "Hoàn thành nhiệm vụ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CSKHAdapter", "Lỗi khi cập nhật dữ liệu", e);
                        Toast.makeText(context, "that bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSDT;
        ToggleButton btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            btn = itemView.findViewById(R.id.btnToggle);
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
