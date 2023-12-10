package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.adapter.TopTaiXeAdapter;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopTaiXeFragment extends Fragment {

    RecyclerView recyclerView;
    TopTaiXeAdapter adapter; // Khai báo Adapter ở mức Fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top_tai_xe, container, false);
        recyclerView = v.findViewById(R.id.rcvTopTX);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        adapter = new TopTaiXeAdapter(new ArrayList<>()); // Khởi tạo Adapter với danh sách trống
        recyclerView.setAdapter(adapter);

        // Gọi hàm truy vấn và hiển thị tài xế sau khi dữ liệu đã sẵn sàng
        queryAndDisplayTopDrivers();

        return v;
    }

    private void queryAndDisplayTopDrivers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Truy vấn và sắp xếp theo số lượng đơn nhận giảm dần
        Map<String, Long> donNhanCountMap = new HashMap<>();

        // Truy vấn số lượng đơn nhận cho mỗi tài xế
        // Truy vấn số lượng đơn nhận cho mỗi tài xế
        db.collection("DonNhan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Lặp qua kết quả truy vấn và tính tổng số lượng đơn nhận cho từng tài xế
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String maTaiXe = document.getString("maTaiXe");
                                Long count = document.getLong("count");
                                Log.d("TopTaiXeFragment", "maTaiXe: " + maTaiXe + ", count: " + count);

                                if (maTaiXe != null) {
                                    // Cập nhật số lượng đơn nhận cho tài xế
                                    if (donNhanCountMap.containsKey(maTaiXe)) {
                                        donNhanCountMap.put(maTaiXe, donNhanCountMap.get(maTaiXe) + 1); // Cập nhật số lượng đơn nhận bằng 1 cho mỗi đơn
                                    } else {
                                        donNhanCountMap.put(maTaiXe, (long) 1); // Nếu chưa có thì tạo mới và gán giá trị 1
                                    }
                                }
                            }

                            // Sắp xếp Map theo giảm dần của số lượng đơn nhận
                            List<Map.Entry<String, Long>> sortedList = new ArrayList<>(donNhanCountMap.entrySet());
                            sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
                            // Lấy top 3 tài xế
                            List<String> top3Drivers = new ArrayList<>();
                            for (int i = 0; i < Math.min(sortedList.size(), 3); i++) {
                                top3Drivers.add(sortedList.get(i).getKey());
                            }
                            // Hiển thị thông tin tài xế
                            displayTopDrivers(top3Drivers);
                        } else {
                            Log.e("FirebaseQuery", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void displayTopDrivers(List<String> topDrivers) {
        // Cập nhật dữ liệu cho Adapter và thông báo cập nhật
        adapter.setData(topDrivers);
        adapter.notifyDataSetChanged();
    }
}
