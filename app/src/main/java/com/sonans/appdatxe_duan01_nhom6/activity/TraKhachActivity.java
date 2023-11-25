package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sonans.appdatxe_duan01_nhom6.R;

import java.io.IOException;
import java.util.List;

public class TraKhachActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView imgCall, imgMessage;
    Button btnKhachXuong;
    TextView tvEnd;
    FirebaseFirestore db;
    private GoogleMap googleMap;
    String maDon;
    private long button2ClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_khach);

        db = FirebaseFirestore.getInstance();

        imgCall = findViewById(R.id.imgCall);
        imgMessage = findViewById(R.id.imgMessage);
        btnKhachXuong = findViewById(R.id.btnKhachXuong);
        tvEnd = findViewById(R.id.tvEnd);
        SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentStart);
        if (mapFragment1 != null) {
            mapFragment1.getMapAsync(this);
            Log.e("/////", "zzzzzz");
        }
        SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
        String startPosition = sp.getString("END", "");
        maDon = sp.getString("maDon", "");
        tvEnd.setText(startPosition);
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra quyền CALL_PHONE
                if (ActivityCompat.checkSelfPermission(TraKhachActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Nếu quyền chưa được cấp, yêu cầu quyền
                    ActivityCompat.requestPermissions(TraKhachActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
                    String sdt = sp.getString("SDT", "");
                    // Nếu quyền đã được cấp, gọi Intent
                    Toast.makeText(TraKhachActivity.this, "sdt: " + sdt, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TraKhachActivity.this, "sdt: " + sdt, Toast.LENGTH_SHORT).show();
                Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + sdt));
                startActivity(sms);
            }
        });

        btnKhachXuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                long button2ClickTime = System.currentTimeMillis();

                // Lấy thời gian từ Activity1
                long button1ClickTime = DonKhachActivity.getButton1ClickTime();

                // Tính thời gian giữa hai sự kiện và hiển thị Toast
                long elapsedTime = (button2ClickTime - button1ClickTime);
                showToast(elapsedTime);
                SharedPreferences sp = getSharedPreferences("Time", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putLong("time", elapsedTime);
                edit.commit();
                Intent intent = new Intent(TraKhachActivity.this, ThanhToanActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        // Thiết lập các thiết lập bản đồ tùy ý
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Lấy địa chỉ từ TextView và hiển thị trên bản đồ
        String location = tvEnd.getText().toString();
        if (!location.isEmpty()) {
            LatLng latLng = getLocationFromAddress(location);
            if (latLng != null) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
            }
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

    private void showToast(long elapsedTime) {
        // Chia chênh lệch thời gian thành giờ, phút và giây
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        // Tính giây, phút và giờ dư
        seconds = seconds % 60;
        minutes = minutes % 60;

        // Hiển thị Toast với thời gian theo giờ, phút, giây
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        Toast.makeText(this, "Khoảng thời gian giữa 2 lần nhấn button là " + timeString, Toast.LENGTH_SHORT).show();
    }
}