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
import com.sonans.appdatxe_duan01_nhom6.fragment.KhachHangFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.LienHeAdminFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TaiXeFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TopTaiXeFragment;
import com.sonans.appdatxe_duan01_nhom6.fragment.TrangChuFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolBar = findViewById(R.id.toolBar1);
        setSupportActionBar(toolBar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.nvView);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolBar, 0,0);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        Fragment fr = new DoanhThuFragment();

        if (getIntent().getBooleanExtra("back_to_fragment1", false)) {
            KhachHangFragment fragment1 = new KhachHangFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment1)
                    .commit();
        }
        if (getIntent().getBooleanExtra("back_to_fragment2", false)) {
            TaiXeFragment fragment2 = new TaiXeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment2)
                    .commit();
        }
        
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fr).commit();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new DoanhThuFragment();

                if(item.getItemId() == R.id.cskh){
                    toolBar.setTitle("Chăm sóc khách hàng");
                    fragment = new LienHeAdminFragment();
                } else if (item.getItemId() == R.id.voucher) {
                    toolBar.setTitle("Doanh Thu");
                    fragment = new DoanhThuFragment();
                }else if (item.getItemId() == R.id.sub_DoanhThu) {
                    toolBar.setTitle("Doanh Thu");
                    fragment = new DoanhThuFragment();
                }else if (item.getItemId() == R.id.sub_Top) {
                    toolBar.setTitle("Top Tài Xế");
                    fragment = new TopTaiXeFragment();
                }else if (item.getItemId() == R.id.sub_Drivers) {
                    toolBar.setTitle("QL Tài Xế");
                    fragment = new TaiXeFragment();

                }else if (item.getItemId() == R.id.sub_Costumers) {
                    toolBar.setTitle("QL Khách Hàng");
                    fragment = new KhachHangFragment();
                }else if (item.getItemId() == R.id.home) {
                    toolBar.setTitle("Trang Chủ");
                    fragment = new DoanhThuFragment();
                }else {
                    Toast.makeText(MainActivity.this, "dang dang xuat", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this, LoginAdminActivity.class);
                            Toast.makeText(MainActivity.this, "dang xuat thanh cong", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();
                        }
                    }, 2000);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                drawerLayout.close();
                return false;
            }
        });

    }
}