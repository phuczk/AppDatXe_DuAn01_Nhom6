package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sonans.appdatxe_duan01_nhom6.R;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    EditText edSearchPositionStart;
    Button btnSearchPositionStart, btnExitStart, btnChoosePositionStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        edSearchPositionStart = findViewById(R.id.edPositionStart);
        btnSearchPositionStart = findViewById(R.id.btnSearchPositionStart);
        btnChoosePositionStart = findViewById(R.id.btnOk_positionStart);
        SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentStart);
        if (mapFragment1 != null) {
            mapFragment1.getMapAsync(this);
            Log.e("/////", "zzzzzz");
        }
        Bundle bundle = getIntent().getExtras();
        String soe = bundle.getString("diemKhoiHanh");
        edSearchPositionStart.setText(soe);
        btnSearchPositionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = edSearchPositionStart.getText().toString();
                if (!location.isEmpty()) {
                    LatLng latLng = getLocationFromAddress(location);
                    if (latLng != null) {
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                    }
                }
            }
        });
        btnChoosePositionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = edSearchPositionStart.getText().toString();
                if (!location.isEmpty()) {
                    LatLng latLng = getLocationFromAddress(location);
                    if (latLng != null) {
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                            Intent i = new Intent(MapActivity.this, DatChuyenActivity.class);
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("diemKhoiHanh", edSearchPositionStart.getText().toString());
                            editor.apply();

                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(MapActivity.this, "khong tim thay dia chi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private LatLng getLocationFromAddress(String location) {
        Geocoder geocoder = new Geocoder(this); // Khởi tạo đối tượng Geocoder
        List<Address> addresses;
        LatLng latLng = null;

        try {
            addresses = geocoder.getFromLocationName(location, 1); // Lấy danh sách địa chỉ dựa trên tên địa chỉ

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0); // Lấy địa chỉ đầu tiên trong danh sách

                double latitude = address.getLatitude(); // Lấy vĩ độ
                double longitude = address.getLongitude(); // Lấy kinh độ

                latLng = new LatLng(latitude, longitude); // Tạo đối tượng LatLng
            }else {
                Toast.makeText(this, "khong tim thay", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        // Thiết lập các thiết lập bản đồ tùy ý
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}