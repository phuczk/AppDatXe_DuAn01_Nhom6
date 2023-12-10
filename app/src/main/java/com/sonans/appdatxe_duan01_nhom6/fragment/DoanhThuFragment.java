package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;

import java.util.ArrayList;
import java.util.List;

public class DoanhThuFragment extends Fragment {

    private int soLuongTaiXe, soLuongDonNhan;
    private int soLuongKhachHang, soLuongDonHuy;
    TextView tvKH, tvTX, tv1, tv2, tvDN, tvDH, tv3, tv4, tvkh, tvtx, tvdt;

    int totalRevenue;
    int revenue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        tvKH = v.findViewById(R.id.soLieuXuat);
        tvTX = v.findViewById(R.id.soLieuNhap);
        tv1 = v.findViewById(R.id.tv1);
        tv2 = v.findViewById(R.id.tv2);
        tvDN = v.findViewById(R.id.soLieuNhap2);
        tvDH = v.findViewById(R.id.soLieuXuat2);
        tv3 = v.findViewById(R.id.tv3);
        tv4 = v.findViewById(R.id.tv4);






        tvkh = v.findViewById(R.id.kh);
        tvtx = v.findViewById(R.id.tx);
        tvdt = v.findViewById(R.id.dt);

        tinhDoanhThu();
        queryFirebaseDataDiverAndCustomer();
        queryFirebaseDonNhanVaDonHuyTX();
        return v;
    }

    private void tinhDoanhThu(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Truy vấn tất cả các bản ghi trong bảng DonNhan
        db.collection("DonNhan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            totalRevenue = 0;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Lấy giá trị doanh thu từ mỗi bản ghi
                                revenue = document.getLong("giaCuoc").intValue();

                                // Cộng vào tổng doanh thu
                                totalRevenue += revenue;
                            }

                            tvdt.setText(String.valueOf(totalRevenue));
                            // Ở đây bạn có thể làm gì đó với tổng doanh thu (totalRevenue)
                            Log.d("TotalRevenue", "Tổng doanh thu: " + totalRevenue);
                        } else {
                            // Xử lý khi truy vấn thất bại
                            Log.e("QueryError", "Lỗi khi truy vấn bảng DonNhan", task.getException());
                        }
                    }
                });
    }

    private void queryFirebaseDataDiverAndCustomer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Truy vấn số lượng tài xế
        db.collection("TaiXe")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        soLuongTaiXe = queryDocumentSnapshots.size();
                        // Gọi createPieChart với dữ liệu đã có
                        tvTX.setText(String.valueOf(soLuongTaiXe));
                        tvtx.setText(String.valueOf(soLuongTaiXe));
                        tv1.setText("so luong tai xe: ");
                        createPieChart1();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TruyVanTaiXe", "Truy vấn tài xế thất bại", e);
                    }
                });

        // Truy vấn số lượng khách hàng
        db.collection("KhachHang")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        soLuongKhachHang = queryDocumentSnapshots.size();
                        tvKH.setText(String.valueOf(soLuongKhachHang));
                        tvkh.setText(String.valueOf(soLuongKhachHang));
                        tv2.setText("so luong khach hang: ");
                        // Gọi createPieChart với dữ liệu đã có
                        createPieChart1();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TruyVanKhachHang", "Truy vấn khách hàng thất bại", e);
                    }
                });
    }



    private void createPieChart1() {
        // Kiểm tra xem cả hai giá trị đã được cập nhật chưa
        if (soLuongTaiXe == 0 || soLuongKhachHang == 0) {
            return;
        }

        // Tạo đối tượng PieChart
        PieChart pieChart = new PieChart(getContext());

        // Thiết lập kích thước và các thuộc tính khác cho PieChart
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        pieChart.setLayoutParams(layoutParams);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        // Tạo danh sách PieEntries (dữ liệu cho đồ thị Pie Chart)
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(soLuongTaiXe, "tài xế"));
        entries.add(new PieEntry(soLuongKhachHang, "khách hàng"));

        // Tạo PieDataSet và cấu hình các thuộc tính
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{Color.rgb(220,103,206), Color.rgb(103,183,220)});
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.rgb(225, 225, 225));

        // Tạo PieData từ PieDataSet
        PieData pieData = new PieData(dataSet);

        // Thiết lập PieData cho PieChart
        pieChart.setData(pieData);

        // Thêm PieChart vào LinearLayout
        LinearLayout chartContainer = getActivity().findViewById(R.id.pieChartContainer);
        // Xóa bỏ các PieChart trước đó (nếu có)
        chartContainer.removeAllViews();
        chartContainer.addView(pieChart);
    }


    private void queryFirebaseDonNhanVaDonHuyTX() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Truy vấn số lượng tài xế
        db.collection("DonNhan")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        soLuongDonNhan = queryDocumentSnapshots.size();
                        // Gọi createPieChart với dữ liệu đã có
                        tvDN.setText(String.valueOf(soLuongDonNhan));
                        tv3.setText("so luong don nhan: ");
                        createPieChart2();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TruyVanTaiXe", "Truy vấn tài xế thất bại", e);
                    }
                });

        // Truy vấn số lượng khách hàng
        db.collection("DonHuyTX")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        soLuongDonHuy = queryDocumentSnapshots.size();
                        tvDH.setText(String.valueOf(soLuongDonHuy));
                        tv4.setText("so luong don huy: ");
                        // Gọi createPieChart với dữ liệu đã có
                        createPieChart2();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TruyVanDNDH", "Truy vấn thất bại", e);
                    }
                });
    }

    private void createPieChart2() {
        // Kiểm tra xem cả hai giá trị đã được cập nhật chưa
        if (soLuongDonNhan == 0 || soLuongDonHuy == 0) {
            return;
        }

        // Tạo đối tượng PieChart
        PieChart pieChart = new PieChart(getContext());

        // Thiết lập kích thước và các thuộc tính khác cho PieChart
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        pieChart.setLayoutParams(layoutParams);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        // Tạo danh sách PieEntries (dữ liệu cho đồ thị Pie Chart)
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(soLuongDonNhan, "don nhan"));
        entries.add(new PieEntry(soLuongDonHuy, "don huy"));

        // Tạo PieDataSet và cấu hình các thuộc tính
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{Color.rgb(220,103,225), Color.rgb(103,183,220)});
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.rgb(0, 0, 0));

        // Tạo PieData từ PieDataSet
        PieData pieData = new PieData(dataSet);

        // Thiết lập PieData cho PieChart
        pieChart.setData(pieData);

        // Thêm PieChart vào LinearLayout
        LinearLayout chartContainer = getActivity().findViewById(R.id.pie1ChartContainer);
        // Xóa bỏ các PieChart trước đó (nếu có)
        chartContainer.removeAllViews();
        chartContainer.addView(pieChart);
    }
}
