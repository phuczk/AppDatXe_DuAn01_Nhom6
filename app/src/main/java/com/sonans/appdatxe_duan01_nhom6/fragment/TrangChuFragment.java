package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sonans.appdatxe_duan01_nhom6.R;
import com.sonans.appdatxe_duan01_nhom6.activity.DonDatKhachHangActivity;
import com.sonans.appdatxe_duan01_nhom6.activity.LichSuChuyenDiKhachHangActivity;
import com.sonans.appdatxe_duan01_nhom6.activity.LichSuChuyenDiTaiXeActivity;


public class TrangChuFragment extends Fragment {


ImageView frag_lichsu,frag_datchuyen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        frag_datchuyen = view.findViewById(R.id.frag_datchuyen);
        frag_lichsu = view.findViewById(R.id.frag_lichsu);
        frag_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LichSuChuyenDiKhachHangActivity.class);
                startActivity(i);
            }
        });

        frag_datchuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DonDatKhachHangActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}