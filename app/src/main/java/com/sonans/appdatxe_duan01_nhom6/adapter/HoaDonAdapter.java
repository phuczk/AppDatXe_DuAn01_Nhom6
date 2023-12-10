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
import com.sonans.appdatxe_duan01_nhom6.model.HoaDonTX;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.text.SimpleDateFormat;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder>{

    List<HoaDonTX> list;
    Context context;

    FirebaseFirestore database;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonAdapter(List<HoaDonTX> list, Context context, FirebaseFirestore database) {
        this.list = list;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public HoaDonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.ViewHolder holder, int position) {
        holder.date.setText(sdf.format(list.get(position).getTime()));
        holder.money.setText(String.valueOf(list.get(position).getSoTien()));
        holder.text.setText(list.get(position).getThanhTien());
        holder.time.setText(list.get(position).getThoiGian());

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

        TextView date, money, text, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.Date_Pay);
            money = itemView.findViewById(R.id.money);
            text = itemView.findViewById(R.id.money_text);
            time = itemView.findViewById(R.id.time);

        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


    private KhachHangAdapter.ItemClickListener itemClickListener;
    public void setItemClickListener(KhachHangAdapter.ItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
