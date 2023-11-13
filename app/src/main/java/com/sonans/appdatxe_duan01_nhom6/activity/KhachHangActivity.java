package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.fragment.DoanhThuFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.DonDatFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.KhachHangFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TaiXeFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TopTaiXeFragment;

public class KhachHangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

    }
}