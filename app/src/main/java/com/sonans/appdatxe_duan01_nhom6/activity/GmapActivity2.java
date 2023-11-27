package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.PolyUtil;
import com.sonans.appdatxe_duan01_nhom6.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class GmapActivity2 extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener{

    GoogleMap mMap;
    LatLng currentUserLocation, searchPointLocation;

    Button btnSearch, btnRepair;
    EditText edLocation, edDistance, edDuration;
    TextView tv_currentLoc;
    Marker currentUser, searchPoint;
    List<LatLng> arrayLatLngDirection = new ArrayList<LatLng>();
    Polyline polyline1; // đối tượng vẽ chỉ đường

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_gmap);
        mapFragment.getMapAsync(this);
        //===========================================

        btnSearch = findViewById(R.id.btn_search);
        btnRepair = findViewById(R.id.btn_car_repair);
        edLocation = findViewById(R.id.ed_location);
        edDistance = findViewById(R.id.ed_distance);
        edDuration = findViewById(R.id.ed_duration);
        tv_currentLoc = findViewById(R.id.tv_currentLoc);

        // ---- khởi tạo cái chức năng tìm vị trí theo tên
        initSearchPlace();

        //=== viết code sự kiện nút bấm search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUserLocation == null) {
                    Toast.makeText(GmapActivity2.this, "Không xác định vị trí người dùng, hãy bật GPS trên điện thoại và bấm nút định vị trước khi thao tác", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (searchPointLocation == null) {
                    Toast.makeText(GmapActivity2.this, "Không xác định vị trí cần tìm, hãy chọn vị trí đích đến", Toast.LENGTH_SHORT).show();
                    return;
                }

                // có tọa độ 2 điểm rồi, gọi api lấy thông tin và vẽ lên bản đồ


                String distance;
                String duration;
                try {

                    String url_download = makeUrlGetDirectionAPI(currentUserLocation,searchPointLocation);

                    String res = (new GmapActivity2.AsyncTaskDownload()).execute(url_download).get();

                    Log.d("zzzzzzzzzzzzzzzz", "Ket quả download = " + res);

                    JSONObject response = new JSONObject(res);

                    distance = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
                    duration = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");

                    edDuration.setText(duration); // thời gian
                    edDistance.setText(distance ); // khoảng cách

                    arrayLatLngDirection.clear(); // xóa hết tọa độ lần bấm nút trước đó (nếu có)
                    arrayLatLngDirection  = PolyUtil.decode(response.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points"));



                } catch (InterruptedException e) {
                    Log.e("zzzzzzzzzzzzzzzz", "Lỗi (1) InterruptedException " + e.getMessage());
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Log.e("zzzzzzzzzzzzzzzz", "Lỗi (2)  ExecutionException" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.e("zzzzzzzzzzzzzzzz", "Lỗi (3) JSONException " + e.getMessage());
                    e.printStackTrace();
                }

                DrawPolyline(); // vẽ đường kẻ trong mảng arrayLatLngDirection


            }
        });





    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // check sử dụng quyền cái này thì alt + enter sẽ tự thêm vào
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Hãy cấp quyền truy cập GPS cho ứng dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        // hiển thị nút tọa độ của tôi trên bản đồ cho người dùng bấm chọn
        mMap.setMyLocationEnabled(true); //lệnh này yêu cầu kiểm tra quyền ở trên
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        getCurrentLocation(); // lấy tọa độ hiện tại cho vào biến và show maker trên bản đồ
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    public void getCurrentLocation() {
        //============ lấy tọa dộ vị trí hiện tại
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Hãy cấp quyền truy cập GPS cho ứng dụng", Toast.LENGTH_SHORT).show();
            return;
        }

        // khởi tạo mấy đối tượng để đọc lại vị trí định vị đã nhận diện
        // nếu app khởi động lần đầu chưa xác định vị trí thì đợi người dùng bấm nút định vị trên bản đồ
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // tạo maker hiển thị biểu tượng trên màn hình: Cần truyền vào lat, long
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            currentUserLocation = myLocation;
            tv_currentLoc.setText("Toa do hien tai: " + currentUserLocation.toString());

            // cách tạo Maker để gắn lên bản đồ
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(myLocation)
                    .title("Vị trí của tôi ở đây") // bấm vào biểu tượng sẽ hiển thị cửa sổ nhỏ
                    .snippet("Viết mô tả cái gì đó thì viết") // thích thêm dòng này thì thêm
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.diem_den)); // thích thêm dòng này thì thêm
            currentUser = mMap.addMarker(markerOptions); // lệnh này gắn biểu tượng vào bản đồ, cứ lưu tạm vào biến  currentUser sau này điều khiển

        } else {
            Toast.makeText(getBaseContext(), "Không lấy được thông tin định vị, hãy bật GPS và bấm nút định vị trên bản đồ", Toast.LENGTH_LONG).show();
        }
    }

    void initSearchPlace(){
        // Khởi tạo cái fragment AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Khởi động cái Place: Yêu cầu bật API Place trên google.
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.gmap_key), Locale.US);
        }

        // thiết lập các thuộc tính cần lấy dữ liệu: Place.Field.LAT_LNG cái này là quan trọng
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG ));
        autocompleteFragment.setHint("Nhập tên địa điểm");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // xóa maker cũ, xóa polyline cũ
                if(searchPoint != null)
                    searchPoint.remove();
                if(polyline1 != null)
                    polyline1.remove();

                Log.i("zzzzzz", "Place: " + place.getName() + ", " + place.getId() + "---" + place.getLatLng());

                searchPointLocation = place.getLatLng(); // gán lại giá trị tọa độ cho biến lưu trữ


                // Tạo maker vị trí đích đến

                searchPoint = mMap.addMarker(new MarkerOptions().position(searchPointLocation).title( "Điểm đến: "  +  place.getName()));

                // di chuyển camera tới vị trí đích đến.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchPointLocation, 17));


                // đưa nó vào textview để quan sát
                tv_currentLoc.setText( "Toa do hien tai: " +  currentUserLocation.toString() + "\nSearch: " +searchPointLocation.toString()    );

            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("zzzzzz", "An error occurred: " + status);
            }
        });
    }


    String makeUrlGetDirectionAPI(LatLng from, LatLng to){
        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + from.latitude + "," + from.longitude +
                "&destination=" + to.latitude + "," + to.longitude +
                "&sensor=false" +
                "&mode=driving" +
                "&key=" + getString(R.string.gmap_key_on_web);
    }


    void DrawPolyline(){
        if(polyline1 != null)
            polyline1.remove();


        PolylineOptions polylineOptions = new PolylineOptions() .clickable(true);

        for(int i = 0; i< arrayLatLngDirection.size(); i++){
            LatLng latLng = arrayLatLngDirection.get(i);

            polylineOptions.add(  latLng  );
        }

        polylineOptions.color(R.color.purple_500);

        polyline1 = mMap.addPolyline(polylineOptions);

    }

    private class AsyncTaskDownload extends AsyncTask<String, Object, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(params[0]);

                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url
                urlConnection.connect();

                // Reading data from url
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();

                br.close();

            } catch (Exception e) {
                Log.d("Exception while downloading url", e.toString());
                e.printStackTrace();
            } finally {
                if(iStream!= null) {
                    try {
                        iStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                urlConnection.disconnect();
            }
            Log.d("zzzzzzzzzzzzzz", "Data = "+data.toString());

            return data;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);

        }
    }



}