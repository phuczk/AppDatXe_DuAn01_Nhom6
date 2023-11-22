package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.Manifest;
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

import java.io.IOException;
import java.util.List;

public class DonKhachActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView imgCall, imgMessage, imgCancel;
    Button btnDonKhach;
    private GoogleMap googleMap;
    TextView tvStart;
    String maDon;
    FirebaseFirestore db;
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
        SharedPreferences sp = getSharedPreferences("NUMBER_PHONE", MODE_PRIVATE);
        String startPosition = sp.getString("START", "");
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
                db.collection("DonDat").document(maDon).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
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

        btnDonKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DonKhachActivity.this, TraKhachActivity.class);
                startActivity(i);
                finish();
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
        String location = tvStart.getText().toString();
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
}