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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.fragment.DoanhThuFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.DoiMatKhauFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.DoiMatKhauTaiXeFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.DonDatFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.KhachHangFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TaiXeFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TopTaiXeFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TrangChuFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TrangChuTaiXeFragment;

public class TaiXeActivity extends AppCompatActivity {

    BottomNavigationView nav_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_xe);

        nav_menu = findViewById(R.id.nav_taixe);

        Fragment fg = new TrangChuTaiXeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fram_taixe, fg).commit();

        nav_menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId()==R.id.menu_trangchu1){
                    fragment = new TrangChuTaiXeFragment();
                } else if (item.getItemId()==R.id.menu_tinnhan1) {
                    fragment = new TrangChuTaiXeFragment();

                } else if (item.getItemId()==R.id.menu_taikhoan1) {
                    fragment = new DoiMatKhauTaiXeFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fram_taixe, fragment).commit();

                return true;
            }
        });




    }
}