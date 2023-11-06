package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.fragment.KhachHangFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("back_to_fragment1", false)) {
            KhachHangFragment fragment1 = new KhachHangFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment1)
                    .commit();
        }
    }
}