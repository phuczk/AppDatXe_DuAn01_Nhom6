package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ThongTinTXActivity extends AppCompatActivity {

    EditText edTen,edTuoi, edSDT, edCCCD, edBS, edTenDN, edMatKhau;
    Button btnCancel, btnOk;

    String ma,ten,tuoi, sdt, tenDN, matKhau, iou, cccd, bienSo, loaiXe;
    FirebaseFirestore db;

    private int soLuongDonHuy;
    private int soLuongDonNhan;
    TextView tvKH, tvTX, tv1, tv2, tvDD, tvDN, tv3, tv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_txactivity);

        tvKH = findViewById(R.id.soLieuXuat);
        tvTX = findViewById(R.id.soLieuNhap);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);


        edTen = findViewById(R.id.edTen);
        edSDT = findViewById(R.id.edSDT);
        edTuoi = findViewById(R.id.edTuoi);
        edBS = findViewById(R.id.edBienSo);
        edCCCD = findViewById(R.id.edCCCD);
        edTenDN = findViewById(R.id.edTenDN);
        edMatKhau = findViewById(R.id.edMatKhau);

        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancle);

        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ma = bundle.getString("ma_tai_xe");
            ten = bundle.getString("ten_tai_xe");
            sdt = bundle.getString("soDT_tx");
            tuoi = String.valueOf(bundle.getInt("tuoi_tai_xe"));
            tenDN = bundle.getString("tenDN_tai_xe");
            matKhau = bundle.getString("matKhau_tai_xe");
            iou = bundle.getString("iouTX");
            cccd = bundle.getString("canCuoc");
            bienSo = bundle.getString("bienSo");
            loaiXe = bundle.getString("loaiXe");
        }
        if(iou.equals("update")){
            edTen.setText(ten);
            edSDT.setText(sdt);
            edTuoi.setText(tuoi);
            edCCCD.setText(cccd);
            edBS.setText(bienSo);
            edTenDN.setText(tenDN);
            edMatKhau.setText("********");
            queryFirebaseDataDiverAndCustomer();
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ten = edTen.getText().toString();
                    String sdt = edSDT.getText().toString();
                    String bienSoXe = edBS.getText().toString();
                    String CCCD = edCCCD.getText().toString();
                    int tuoi = Integer.parseInt(edTuoi.getText().toString());

                    TaiXe taiXe = new TaiXe(ma, ten,tuoi, sdt, tenDN, matKhau, bienSoXe, loaiXe, CCCD);
                    HashMap<String, Object> map = taiXe.convertHashMap();
                    db.collection("TaiXe").document(ma)
                            .update(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(ThongTinTXActivity.this, MainActivity.class);
                                    intent.putExtra("back_to_fragment2", true);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(ThongTinTXActivity.this, "cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ThongTinTXActivity.this, "cap nhat that bai", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        }else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate() > 0){
                        String newName = edCCCD.getText().toString();
                        db.collection("TaiXe")
                                .whereEqualTo("CanCuoc", newName)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                            if (task.getResult().isEmpty()) {
                                                String maTX = UUID.randomUUID().toString();
                                                String ten = edTen.getText().toString();
                                                int tuoi = Integer.parseInt(edTuoi.getText().toString());
                                                String sdt = edSDT.getText().toString();
                                                String CCCD = edCCCD.getText().toString();
                                                String BS = edBS.getText().toString();
                                                String tenDN = edTenDN.getText().toString();
                                                String matKhau = edMatKhau.getText().toString();
                                                TaiXe taiXe = new TaiXe(maTX, ten,tuoi, sdt, tenDN, matKhau, BS, "xe may", CCCD);
                                                HashMap<String, Object> map = taiXe.convertHashMap();
                                                db.collection("TaiXe").document(maTX)
                                                        .set(map)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Intent intent = new Intent(ThongTinTXActivity.this, MainActivity.class);
                                                                intent.putExtra("back_to_fragment2", true);
                                                                startActivity(intent);
                                                                Toast.makeText(ThongTinTXActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(ThongTinTXActivity.this, "them that bai", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                                Toast.makeText(ThongTinTXActivity.this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Xử lý khi truy vấn không thành công
                                            Toast.makeText(ThongTinTXActivity.this, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ThongTinTXActivity.this, MainActivity.class);
                    intent.putExtra("back_to_fragment2", false);
                    startActivity(intent);
                }
            });
        }
    }

    private void queryFirebaseDataDiverAndCustomer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("DonHuyTX")
                .whereEqualTo("maTaiXe", tenDN)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Lấy số lượng đơn từ kết quả truy vấn
                        soLuongDonHuy = queryDocumentSnapshots.size();

                        if (soLuongDonHuy == 0){
                            soLuongDonHuy = 0;
                        }
                        tvTX.setText(String.valueOf(soLuongDonHuy));
                        // Sử dụng số lượng đơn ở đây, bạn có thể làm gì đó với nó
                        Log.d("SoLuongDon", "Số lượng đơn: " + soLuongDonHuy);
                        createPieChart1();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi truy vấn thất bại
                        Log.e("TruyVanDonHuyTX", "Truy vấn đơn hủy tài xế thất bại", e);
                    }
                });


        // Truy vấn số lượng khách hàng
        db.collection("DonNhan")
                .whereEqualTo("maTaiXe", tenDN)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Lấy số lượng đơn từ kết quả truy vấn
                        soLuongDonNhan = queryDocumentSnapshots.size();

                        if (soLuongDonNhan == 0){
                            soLuongDonNhan = 0;
                        }
                        tvKH.setText(String.valueOf(soLuongDonNhan));
                        // Sử dụng số lượng đơn ở đây, bạn có thể làm gì đó với nó
                        Log.d("SoLuongDon", "Số lượng đơn: " + soLuongDonNhan);
                        createPieChart1();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi truy vấn thất bại
                        Log.e("TruyVanDonHuyTX", "Truy vấn đơn hủy tài xế thất bại", e);
                    }
                });
    }



    private void createPieChart1() {
        // Kiểm tra xem cả hai giá trị đã được cập nhật chưa
        if (soLuongDonHuy == 0 && soLuongDonNhan == 0) {
            return;
        }

        // Tạo đối tượng PieChart
        PieChart pieChart = new PieChart(ThongTinTXActivity.this);

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
        entries.add(new PieEntry(soLuongDonHuy, "Don da Huy"));
        entries.add(new PieEntry(soLuongDonNhan, "Don da chay"));

        // Tạo PieDataSet và cấu hình các thuộc tính
        PieDataSet dataSet = new PieDataSet(entries, "Biểu đồ số lượng");
        dataSet.setColors(new int[]{Color.rgb(220,103,206), Color.rgb(103,183,220)});
        dataSet.setValueTextSize(6f);
        dataSet.setValueTextColor(Color.rgb(225, 225, 225));

        // Tạo PieData từ PieDataSet
        PieData pieData = new PieData(dataSet);

        // Thiết lập PieData cho PieChart
        pieChart.setData(pieData);

        // Thêm PieChart vào LinearLayout
        LinearLayout chartContainer = findViewById(R.id.pieChartContainer);
        // Xóa bỏ các PieChart trước đó (nếu có)
        chartContainer.removeAllViews();
        chartContainer.addView(pieChart);
    }

    private int validate(){
        int check = 0;
        if(edTen.getText().toString().length() <= 5 || edSDT.getText().toString().length()<=0 || edCCCD.getText().toString().length()<=0){
            check = 1;
        }else {
            Toast.makeText(this, "vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
            check = 0;
        }
        return check;
    }
}