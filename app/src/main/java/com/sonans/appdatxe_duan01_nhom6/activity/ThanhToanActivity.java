package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sonans.appdatxe_duan01_nhom6.R;

public class ThanhToanActivity extends AppCompatActivity {

    TextView tvPrice, tvSumPrice, tvTextPrice;
    Button  btnCancel, btnOk;

    private static final String[] UNITS = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    private static final String[] TEENS = {"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};
    private static final String[] THOUSANDS = {"", "nghìn", " triệu", "tỷ"};

    public static String convertToWords(int number) {
        if (number == 0) {
            return "không";
        }

        int i = 0;
        String words = "";

        while (number > 0) {
            if (number % 1000 != 0) {
                words = convertToWordsUnderThousand(number % 1000) + "" + THOUSANDS[i] + " " + words;
            }
            number /= 1000;
            i++;
        }

        return words.trim();
    }

    private static String convertToWordsUnderThousand(int number) {
        if (number == 0) {
            return "";
        }

        if (number < 10) {
            return UNITS[number];
        } else if (number < 100) {
            return TEENS[number / 10] + " " + UNITS[number % 10];
        } else {
            return UNITS[number / 100] + " trăm " + convertToWordsUnderThousand(number % 100);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        tvPrice = findViewById(R.id.tvPrice);
        tvSumPrice = findViewById(R.id.tvSumPrice);
        tvTextPrice = findViewById(R.id.tvPriceText);
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnXacNhan);

        SharedPreferences sp = getSharedPreferences("Time", MODE_PRIVATE);
        Long time = sp.getLong("time", 0);

        SharedPreferences sharedPreferences = getSharedPreferences("DonDat", MODE_PRIVATE);
        int price = sharedPreferences.getInt("giaCuoc", 0);

        tvPrice.setText(String.valueOf(price));
        tvSumPrice.setText(String.valueOf(price));
        String textPrice = convertToWords(price);
        tvTextPrice.setText(textPrice);
        SharedPreferences sp1 = getSharedPreferences("text", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp1.edit();
        edit.putString("text", textPrice);
        edit.commit();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ThanhToanActivity.this, TraKhachActivity.class);
                startActivity(i);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ThanhToanActivity.this, XacNhanThanhToanActivity.class);
                startActivity(i);
            }
        });
    }
}