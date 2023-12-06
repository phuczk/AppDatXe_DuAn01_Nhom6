package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.model.DonHuyTX;
import com.sonans.appdatxe_duan01_nhom6.model.TaiXe;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DonKhachActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView imgCall, imgMessage, imgCancel;
    Button btnDonKhach;
    private GoogleMap googleMap;
    TextView tvStart;
    String maDon;
    FirebaseFirestore db;
    String startPosition, endPosition;
    private static long button1ClickTime = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_khach);

        db = FirebaseFirestore.getInstance();

        imgCall = findViewById(R.id.imgCall);
        imgMessage = findViewById(R.id.imgMessage);
        imgCancel = findViewById(R.id.imgCancel);
        btnDonKhach = findViewById(R.id.btnDonKhach);
        tvStart = findViewById(R.id.tvStart);
        SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentStart);
        if (mapFragment1 != null) {
            mapFragment1.getMapAsync(this);
            Log.e("/////", "zzzzzz");
        }
        drawPolyline();
        SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
        startPosition = sp.getString("START", "");
        endPosition = sp.getString("END", "");
        maDon = sp.getString("maDon", "");
        tvStart.setText(startPosition);
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra quyền CALL_PHONE
                if (ActivityCompat.checkSelfPermission(DonKhachActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Nếu quyền chưa được cấp, yêu cầu quyền
                    ActivityCompat.requestPermissions(DonKhachActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
                    String sdt = sp.getString("SDT", "");
                    // Nếu quyền đã được cấp, gọi Intent
                    Toast.makeText(DonKhachActivity.this, "sdt: " + sdt, Toast.LENGTH_SHORT).show();
                    Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sdt));
                    startActivity(call);
                }
            }
        });

        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
                    String sdt = sp.getString("SDT", "");
                    // Nếu quyền đã được cấp, gọi Intent
                    Toast.makeText(DonKhachActivity.this, "sdt: " + sdt, Toast.LENGTH_SHORT).show();
                    Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + sdt));
                    startActivity(sms);
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] trangThaiz = {"co viec ban dot xuat", "khong thich tiep tuc", "khac..."};
                AlertDialog.Builder builder = new AlertDialog.Builder(DonKhachActivity.this);
                builder.setTitle("vi sao ban muon huy chuyen di");
                builder.setItems(trangThaiz, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("DonDat").document(maDon).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                SharedPreferences sp = getSharedPreferences("DonDat", MODE_PRIVATE);
                                String maDonHuy = UUID.randomUUID().toString();
                                String maKh = sp.getString("maKhachHang", "");
                                String start = sp.getString("diemKhoiHanh", "");
                                String end = sp.getString("diemDen", "");
                                Date date = new Date();
                                SharedPreferences sp1 = getSharedPreferences("maTX", MODE_PRIVATE);
                                String maTX = sp1.getString("id", "");
                                DonHuyTX donHuyTX = new DonHuyTX(maDonHuy, date,start, end, maKh, maTX);
                                HashMap<String, Object> map = donHuyTX.convertHashMap();
                                db.collection("DonHuyTX").document(maDonHuy).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                                Toast.makeText(DonKhachActivity.this, "huy thanh cong", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(DonKhachActivity.this, DonDatTaiXeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DonKhachActivity.this, "huy don that bai", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        btnDonKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1ClickTime = System.currentTimeMillis();
                Intent i = new Intent(DonKhachActivity.this, TraKhachActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private boolean isMapReady = false;

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        isMapReady = true;
        // Thiết lập các thiết lập bản đồ tùy ý
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Lấy địa chỉ từ TextView và hiển thị trên bản đồ
        String location = tvStart.getText().toString();
        if (!location.isEmpty()) {
            drawPolyline();

        }
    }

    private void drawPolyline() {
        if (!isMapReady) {
            // Bản đồ chưa sẵn sàng, không thể vẽ đường đi
            Toast.makeText(this, "Bản đồ chưa sẵn sàng", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
        startPosition = sp.getString("START", "");
        endPosition = sp.getString("END", "");
        Toast.makeText(this, "" + startPosition + " " + endPosition, Toast.LENGTH_SHORT).show();

        LatLng originLatLng = getLatLngFromAddress(startPosition);
        LatLng destinationLatLng = getLatLngFromAddress(endPosition);

        if (googleMap != null && originLatLng != null && destinationLatLng != null) {
            // Tính toán các điểm điều khiển cho đường cong Bezier
            LatLng controlPoint1 = new LatLng(
                    (originLatLng.latitude + destinationLatLng.latitude) / 2,
                    originLatLng.longitude+0.001
            );

            LatLng controlPoint5 = new LatLng(
                    ((originLatLng.latitude + destinationLatLng.latitude) / 2)-0.002,
                    destinationLatLng.longitude-0.005
            );

            LatLng controlPoint4 = new LatLng(
                    ((originLatLng.latitude + destinationLatLng.latitude) / 2)+0.002,
                    destinationLatLng.longitude-0.003
            );

            LatLng controlPoint3 = new LatLng(
                    ((originLatLng.latitude + destinationLatLng.latitude) / 2)+0.003,
                    destinationLatLng.longitude+0.003
            );


            LatLng controlPoint2 = new LatLng(
                    ((originLatLng.latitude + destinationLatLng.latitude) / 2)-0.001,
                    destinationLatLng.longitude+0.004
            );
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(destinationLatLng)
                    .title("Vị trí của tôi ở đây") // bấm vào biểu tượng sẽ hiển thị cửa sổ nhỏ
                    .snippet("Viết mô tả cái gì đó thì viết") // thích thêm dòng này thì thêm
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location));
            googleMap.addMarker(markerOptions);
            // Thêm đường cong Bezier vào danh sách điểm của Polyline
            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(originLatLng, controlPoint1, controlPoint5,controlPoint4, controlPoint3, controlPoint2, destinationLatLng)
                    .width(10)
                    .color(Color.rgb(0, 0, 238));
            googleMap.addPolyline(polylineOptions);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(originLatLng);
            builder.include(destinationLatLng);
            LatLngBounds bounds = builder.build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
        } else {
            // Xử lý khi không thể lấy được LatLng từ địa điểm hoặc googleMap chưa được khởi tạo
            Toast.makeText(this, "Không thể vẽ đường đi", Toast.LENGTH_SHORT).show();
        }
    }


    private LatLng getLocationFromAddress(String location) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        LatLng latLng = null;

        try {
            addresses = geocoder.getFromLocationName(location, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                latLng = new LatLng(latitude, longitude);
            } else {
                Toast.makeText(this, "Không tìm thấy địa chỉ", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }

    private LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static long getButton1ClickTime() {
        return button1ClickTime;
    }
}