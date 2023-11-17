package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class MapKhoiHanhActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    Button btnSearchPositionStart;
    TextView startPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_khoi_hanh);
        startPosition = findViewById(R.id.tvDiemKhoiHanh);
        btnSearchPositionStart = findViewById(R.id.btnOk_positionStart);

        SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragmentStart);
        if (mapFragment1 != null) {
            mapFragment1.getMapAsync(this);
            Log.e("/////", "zzzzzz");
        }

        Bundle bundle = getIntent().getExtras();
        String sOe = bundle.getString("soe");
        String soe = "";
        if (sOe.equals("start")){
            soe = bundle.getString("startPosition");
        }else {
            soe = bundle.getString("endPosition");
        }
        startPosition.setText(soe);

        btnSearchPositionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapKhoiHanhActivity.this, ThongTinChuyenDiActivity.class);
                startActivity(i);
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
        String location = startPosition.getText().toString();
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
