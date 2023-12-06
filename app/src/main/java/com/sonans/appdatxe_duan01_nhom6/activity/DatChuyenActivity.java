package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.DonDat;
import com.sonans.appdatxe_duan01_nhom6.model.KhachHang;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DatChuyenActivity extends AppCompatActivity {

    EditText edTenKhachHang, edSDT, edDiemKhoiHanh, edDiemDen, edThoiGian, edSoLuongKhach;
    TextView tvGiaCuoc;
    Button btnCancel, btnOk, btnPrice;
    Context context = this;
    private GoogleMap googleMap;
    FirebaseFirestore db;
    int giaCuoc;
    int soLuong;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_chuyen);
        db = FirebaseFirestore.getInstance();
        edTenKhachHang = findViewById(R.id.ed_TenKhachHang_DonDat);
        edSDT = findViewById(R.id.ed_SdtKhachHang_DonDat);
        edThoiGian = findViewById(R.id.ed_ThoiGian_DonDat);
        edSoLuongKhach = findViewById(R.id.ed_soLuongKhachHang_DonDat);
        edDiemDen = findViewById(R.id.ed_DiemDen_DonDat);
        edDiemKhoiHanh = findViewById(R.id.ed_DiemXuatPhat_DonDat);
        tvGiaCuoc = findViewById(R.id.tv_giaCuoc);
        btnOk = findViewById(R.id.btnOK_DatChuyen);
        btnCancel = findViewById(R.id.btnCancel_DatChuyen);
        btnPrice = findViewById(R.id.btnPrice);
        edThoiGian.setText("ngay khoi hanh: "+sdf.format(new Date()));
        edDiemDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(edDiemDen.getText().length() >= 0) {
                    bundle.putString("diemDen", edDiemDen.getText().toString());
                    Intent i = new Intent(DatChuyenActivity.this, MapEndActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(DatChuyenActivity.this, MapEndActivity.class);
                    startActivity(i);
                }
            }
        });
        edDiemKhoiHanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if(edDiemKhoiHanh.getText().toString().length() >= 0) {
                    bundle.putString("diemKhoiHanh", edDiemKhoiHanh.getText().toString());
                    Intent i = new Intent(DatChuyenActivity.this, MapActivity.class);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(DatChuyenActivity.this, MapActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String start = sharedPreferences.getString("diemKhoiHanh", "");
        String end = sharedPreferences.getString("diemDen", "");

// Sử dụng dữ liệu start và end ở đây
        edDiemKhoiHanh.setText(start);
        edDiemDen.setText(end);
        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soLuongText = edSoLuongKhach.getText().toString();
                if (!soLuongText.isEmpty()) {
                    soLuong = Integer.parseInt(soLuongText);
                    giaCuoc = 50000 * soLuong;
                    tvGiaCuoc.setText(String.valueOf(giaCuoc));
                }else {
                    edSoLuongKhach.setError("chua nhap dung du lieu");
                }
            }
        });

        SharedPreferences sp = getSharedPreferences("USER_FILE_CUSTOMER", MODE_PRIVATE);
        String userRemember = sp.getString("USERNAME_CUSTOMER", "");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate() > 0){
                    SharedPreferences esharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String maDon = UUID.randomUUID().toString();
                    String ten = edTenKhachHang.getText().toString();
                    String sdt = edSDT.getText().toString();
                    String diemKhoiHanh = edDiemKhoiHanh.getText().toString();
                    String diemDen = edDiemDen.getText().toString();
                    int soLuong = Integer.parseInt(edSoLuongKhach.getText().toString());
                    int giaCuoc = 50000* soLuong;
                    Date ngay = new Date();
                    DonDat donDat = new DonDat(maDon, ngay, diemKhoiHanh, diemDen, userRemember, ten, sdt, soLuong, giaCuoc);
                    HashMap<String, Object> map = donDat.convertHashMap();
                    db.collection("DonDat").document(maDon)
                            .set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(DatChuyenActivity.this, "dat chuyen thanh cong", Toast.LENGTH_SHORT).show();
                                    editor.remove("diemKhoiHanh");
                                    editor.remove("diemDen");
                                    editor.apply();
                                    Intent i = new Intent(DatChuyenActivity.this, DonDatKhachHangActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DatChuyenActivity.this, "them that bai", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(DatChuyenActivity.this, "dat chuyen khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DatChuyenActivity.this, MainActivity.class);
//                intent.putExtra("back_to_fragment1", true);
//                startActivity(intent);
//            }
//        });

    }

    private int validate(){
        int check = 1;
        if(edTenKhachHang.getText().length() <= 5){
            edTenKhachHang.setError("ten khach hang toi thieu 5 ki tu");
            check = -1;
        } else if (edDiemDen.getText().length() <= 0) {
            edDiemDen.setError("chua nhap diem den");
            check = -1;
        } else if (edDiemKhoiHanh.getText().length() <= 0) {
            edDiemKhoiHanh.setError("chua nhap diem khoi hanh");
            check = -1;
        } else if (edSoLuongKhach.getText().length() <= 0) {
            edSoLuongKhach.setError("chua nhap so luong khach");
            check = -1;
        } else if (edSDT.getText().length() <= 0) {
            edSDT.setError("chua nhap so dien thoai");
            check = -1;
        }
        return check;
    }
//    public void showDatePickerDialog(View v) {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                // Xử lý ngày được chọn ở đây
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, month, dayOfMonth);
//                long selectedDateInMillis = calendar.getTimeInMillis();
//
//                // Chuyển đổi ngày thành timestamp và lưu vào Firebase
//                Map<String, Object> data = new HashMap<>();
//                data.put("ngayKhoiHanh", selectedDateInMillis);
//                db.collection("DonDat").document(ma).set(data)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // Xử lý khi lưu thành công
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Xử lý khi lưu thất bại
//                            }
//                        });
//            }
//        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.show();
//    }
}