package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import com.google.android.gms.maps.model.PolylineOptions;
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

    String startPosition, endPosition;
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
    private boolean isMapReady = false;
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        isMapReady = true;
        // Thiết lập các thiết lập bản đồ tùy ý
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Lấy địa chỉ từ TextView và hiển thị trên bản đồ
        String location = tvEnd.getText().toString();
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
                    ((originLatLng.latitude + destinationLatLng.latitude) / 2)+0.001,
                    destinationLatLng.longitude+0.002
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
        SharedPreferences sp = getSharedPreferences("timez", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("timez", timeString);
        edit.commit();
        Toast.makeText(this, "Khoảng thời gian giữa 2 lần nhấn button là " + timeString, Toast.LENGTH_SHORT).show();
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
}