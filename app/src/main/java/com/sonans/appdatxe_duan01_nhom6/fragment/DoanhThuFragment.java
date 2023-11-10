package com.sonans.appdatxe_duan01_nhom6.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonans.appdatxe_duan01_nhom6.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DoanhThuFragment extends Fragment {

    EditText edFrom, edTo;
    ImageView imgFrom, imgTo;
    Button btnSee, btnBefore, btnAfter;
    TextView tvDoanhThu, tvNhap, tvLai;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    int mDay, mMonth, mYear;
    float xuat, nhap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        return v;
    }
}