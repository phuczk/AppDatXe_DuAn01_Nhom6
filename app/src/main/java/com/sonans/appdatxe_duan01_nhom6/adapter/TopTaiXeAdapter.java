package com.sonans.appdatxe_duan01_nhom6.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.List;

public class TopTaiXeAdapter extends RecyclerView.Adapter<TopTaiXeAdapter.ViewHolder> {

    private List<String> topDrivers;

    public TopTaiXeAdapter(List<String> topDrivers) {
        this.topDrivers = topDrivers;
    }

    // ... Các phương thức khác của Adapter

    // Phương thức để cập nhật dữ liệu
    public void setData(List<String> topDrivers) {
        this.topDrivers = topDrivers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_tai_xe, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String maTaiXe = topDrivers.get(position);
        // Truy vấn và hiển thị thông tin tài xế từ Firestore, ví dụ:
        // db.collection("TaiXe").document(maTaiXe).get()...
        holder.txtMaTaiXe.setText(maTaiXe);
        // Hiển thị các thông tin khác tùy thuộc vào cấu trúc của bạn
    }

    @Override
    public int getItemCount() {
        return topDrivers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaTaiXe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTaiXe = itemView.findViewById(R.id.txtMaTaiXe);
            // Khai báo và ánh xạ các View khác tại đây nếu cần
        }
    }
}
