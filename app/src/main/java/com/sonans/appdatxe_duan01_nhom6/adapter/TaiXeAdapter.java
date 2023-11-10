package com.sonans.appdatxe_duan01_nhom6.adapter;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.database.DbHelper;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.HashMap;
import java.util.List;

public class TaiXeAdapter extends RecyclerView.Adapter<TaiXeAdapter.ViewHolder>{

    List<TaiXe> list;
    Context context;

    FirebaseFirestore database;

    public TaiXeAdapter(List<TaiXe> list, Context context, FirebaseFirestore database) {
        this.list = list;
        this.context = context;
        this.database = database;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tai_xe, parent, false);
        return new ViewHolder(view);
    }

    Dialog dialog;
    EditText tenTX, tuoiTX, sdtTX, tenDNTX, matKhauTX;
    Button btnOK, btnCancle;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tenTaiXe.setText(list.get(position).getTenTaiXe()+"");
        holder.tuoiTaiXe.setText(list.get(position).getTuoiTaiXe()+"");
        holder.sdtTaiXe.setText(list.get(position).getSdtTaiXe()+"");
        holder.tenDNTaiXe.setText(list.get(position).getTenDN_TaiXe()+"");
        holder.matKhauTaiXe.setText(list.get(position).getMatKhauTaiXe()+"");
//        holder.btnUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog = new Dialog(v.getContext());
//                dialog.setContentView(R.layout.dialog_ql_tai_xe);
//                tenTX = dialog.findViewById(R.id.ed_tenTX_dialog);
//                tuoiTX = dialog.findViewById(R.id.ed_tuoiTX_dialog);
//                sdtTX = dialog.findViewById(R.id.ed_sdtTX_dialog);
//                tenDNTX = dialog.findViewById(R.id.ed_tenDNTX_dialog);
//                matKhauTX = dialog.findViewById(R.id.ed_matKhauTX_dialog);
//                btnOK = dialog.findViewById(R.id.btnOK_TX);
//                btnCancle = dialog.findViewById(R.id.btnCancle_TX);
//                int itemCount = list.size();
//                Toast.makeText(v.getContext(), "///" + position, Toast.LENGTH_SHORT).show();
//                tenTX.setText(list.get(position).getTenTaiXe()+"");
//                tuoiTX.setText(list.get(position).getTuoiTaiXe()+"");
//                sdtTX.setText(list.get(position).getSdtTaiXe()+"");
//                tenDNTX.setText(list.get(position).getTenDN_TaiXe()+"");
//                matKhauTX.setText(list.get(position).getMatKhauTaiXe()+"");
//                btnOK.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String maTaiXe = list.get(position).getMaTaiXe();
//                        String tenTaiXe = tenTX.getText().toString();
//                        int tuoiTaiXe = Integer.parseInt(tuoiTX.getText().toString());
//                        String sdtTaiXe = sdtTX.getText().toString();
//                        String tenDNTaiXe = tenDNTX.getText().toString();
//                        String matKhauTaiXe = matKhauTX.getText().toString();
//
//                        TaiXe taiXe = new TaiXe(maTaiXe, tenTaiXe, tuoiTaiXe , sdtTaiXe, tenDNTaiXe, matKhauTaiXe);
//                        HashMap<String, Object> map = taiXe.convertHashMap();
//                        database.collection("TaiXe").document(maTaiXe).update(map)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(context, "update thanh cong", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        dialog.dismiss();
//                    }
//                });
//                btnCancle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
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

        TextView tenTaiXe, sdtTaiXe, tuoiTaiXe, tenDNTaiXe, matKhauTaiXe;
        ImageView btnUp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenTaiXe = itemView.findViewById(R.id.tv_tenTX);
            tuoiTaiXe = itemView.findViewById(R.id.tv_tuoiTX);
            sdtTaiXe = itemView.findViewById(R.id.tv_sdtTX);
            tenDNTaiXe = itemView.findViewById(R.id.tv_tenDNTX);
            matKhauTaiXe = itemView.findViewById(R.id.tv_matKhauTX);
//            btnUp = itemView.findViewById(R.id.btnUpTX);
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
