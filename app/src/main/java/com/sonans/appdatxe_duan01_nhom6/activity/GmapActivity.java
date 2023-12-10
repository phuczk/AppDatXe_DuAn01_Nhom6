package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
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

public class GmapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener{
    GoogleMap mMap;

    LatLng currentUserLocation, searchResLocation; // lưu tọa độ hiện tại của user theo định vị, và tọa độ tìm kiếm
    Button btnSearch, btnRepair;
    EditText edLocation, edDistance, edDuration;
    LatLng latLng_search_res;

    TextView tv_currentLoc;
    List<LatLng> arrayDirection = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag_gmap);
        mapFragment.getMapAsync(this);



        btnSearch = findViewById(R.id.btn_search);
        btnRepair = findViewById(R.id.btn_car_repair);
        edLocation = findViewById(R.id.ed_location);
        edDistance = findViewById(R.id.ed_distance);
        edDuration = findViewById(R.id.ed_duration);
        tv_currentLoc = findViewById(R.id.tv_currentLoc);
        //================================
        // Tìm kiếm vị trí theo tên
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUserLocation == null) {
                    Toast.makeText(GmapActivity.this, "Khong xac dinh vi tri nguoi dung", Toast.LENGTH_SHORT).show();
                    return;
                }


                String loc_name = edLocation.getText().toString();  // tên vị trí người dùng nhập

                List<Address> addressList = null; // tạo ra một biến list để chứa nhiều địa chỉ nếu tìm thấy nhiều địa chỉ trùng tên

                if (loc_name != null || !loc_name.equals("")) { // nếu chuỗi người dùng nhập khác rỗng thì mới tìm

                    Geocoder geocoder = new Geocoder(GmapActivity.this); // tạo đối tượng dùng để tìm kiếm
                    try {
                        // tìm được nhiều địa chỉ sẽ thả vào danh sách
                        addressList = geocoder.getFromLocationName(loc_name, 1); // maxResult = 1: trả về cần 1 giá trị, nhưng nó vẫn trả về kiểu danh sách

                    } catch (IOException e) {
                        Toast.makeText(GmapActivity.this, "Loi tim kiem " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    // lấy ra một phần tử địa chỉ trong mảng
                    Address address = addressList.get(0);

                    // tạo đối tượng tọa độ gồm kinh độ và vĩ độ (lat, long) của địa chỉ
                    latLng_search_res = new LatLng(address.getLatitude(), address.getLongitude());

                    // Taok maker
                    mMap.addMarker(new MarkerOptions().position(latLng_search_res).title(loc_name));

                    // below line is to animate camera to that position.
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng_search_res, 17));


                    //============= Tính toán khoảng cách tới vị trí hiện tại của định vị người dùng



                    //---------------------------------------------------------------
                    // tính toán thời gian di chuyển

                    String khoangcach;
                    String duration;
                    try {

                        String url_download = makeUrlGet(currentUserLocation,latLng_search_res);
//                        String res = String.valueOf((new AsyncTaskDownload()).execute(makeUrlGet(currentUserLocation,latLng_search_res)));

                        String res = (new AsyncTaskDownload()).execute(url_download).get();

//                        String res = downloadUrl(makeUrlGet(currentUserLocation,latLng_search_res));
                        Log.d("zzzzzzzzzzzzzzzz", "onClick: res = " + res);

                        JSONObject response = new JSONObject(res);

                        khoangcach = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
                        duration = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");
                        Log.d("zzzzzzzzzzzzzzzz", "onClick: khoang cach = " + khoangcach);
                        Log.d("zzzzzzzzzzzzzzzz", "onClick: duration = " + duration);


                        edDuration.setText(duration); // thời gian
                        edDistance.setText(khoangcach );
                        arrayDirection.clear();
                        arrayDirection  = PolyUtil.decode(response.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points"));

//                        arrayDirection = (ArrayList<LatLng>) decodePolyLine(response.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points"));



                    } catch ( JSONException e) {
                        Log.e("zzzzzzzzzzzzzzzz", "onClick: Error download route...");
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                    DrawPolyline(); // vẽ bản đồ


                }


            }
        });





        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.gmap_key), Locale.US);
        }

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setHint("Nhập tên địa điểm");

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("zzzzzz", "Place: " + place.getName() + ", " + place.getId());

                latLng_search_res = place.getLatLng(); // gán lại giá trị tọa độ cho biến lưu trữ

            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("zzzzzz", "An error occurred: " + status);
            }
        });

    }

    Polyline polyline1;
    void DrawPolyline(){
        if(polyline1 != null)
            polyline1.remove();

        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(true);

        for(int i = 0; i< arrayDirection.size(); i++){

            LatLng latLng = arrayDirection.get(i);

            polylineOptions.add(  latLng  );
        }

        polylineOptions.color(R.color.teal_200);

        polyline1 = mMap.addPolyline(polylineOptions);

    }

    /**
     * Cải tiến cho nó có hiệu ứng camera và tự động hiển thị tọa độ hiện tại của người dùng
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // check sử dụng quyền cái này thì alt + enter sẽ tự thêm vào
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // hiển thị nút tọa độ của tôi trên bản đồ cho người dùng bấm chọn
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        getCurrentLocation(); // lấy tọa độ hiện tại cho vào biến và show maker trên bản đồ

    }

    public void getCurrentLocation(){
        //============ lấy tọa dộ vị trí hiện tại
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // tạo maker: Cần truyền vào lat, long
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            currentUserLocation = myLocation;
            tv_currentLoc.setText("Toa do hien tai: " + location.getLatitude() + " : " + location.getLongitude());

            mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in my location"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));


        }else{
            Toast.makeText(getBaseContext(), "location null, hãy bật chức năng định vị", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
        // sự kiện bấm vào biểu tượng market trên bản đồ; cái này chưa làm gì cả
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        getCurrentLocation(); // bấm nút định vị trên bản đồ sẽ load lại tọa độ cho vào biến

        return false;
    }


    //======================================================
    String makeUrlGet(LatLng from, LatLng to){
        return "https://maps.googleapis.com/maps/api/directions/json?origin=" + from.latitude + "," + from.longitude +
                "&destination=" + to.latitude + "," + to.longitude +
                "&sensor=true" +
                "&mode=driving" +
                "&key=" + getString(R.string.gmap_key_on_web);
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