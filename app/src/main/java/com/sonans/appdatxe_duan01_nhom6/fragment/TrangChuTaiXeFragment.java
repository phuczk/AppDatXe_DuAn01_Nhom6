package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.activity.DonDatTaiXeActivity;
import com.sonans.appdatxe_duan01_nhom6.activity.HoaDonActivity;
import com.sonans.appdatxe_duan01_nhom6.activity.LichSuChuyenDiTaiXeActivity;

public class TrangChuTaiXeFragment extends Fragment {

    ImageView lichSu, nhanChuyen, hoaDon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trang_chu_tai_xe, container, false);
        lichSu = v.findViewById(R.id.frag_lichsu);
        nhanChuyen = v.findViewById(R.id.frag_nhanchuyen);
        hoaDon = v.findViewById(R.id.hoaDon);

        lichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LichSuChuyenDiTaiXeActivity.class);
                startActivity(i);
            }
        });
        nhanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DonDatTaiXeActivity.class);
                startActivity(i);
            }
        });

        hoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HoaDonActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}