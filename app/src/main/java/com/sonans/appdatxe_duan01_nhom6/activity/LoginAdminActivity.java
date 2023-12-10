package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.sonans.appdatxe_duan01_nhom6.R;

public class LoginAdminActivity extends AppCompatActivity {

    EditText edUserName, edPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        edUserName = findViewById(R.id.ed_UserName);
        edPass = findViewById(R.id.ed_Password);

        if(edUserName.getText().equals("Sonans123") && edPass.getText().equals("Nyasu")){
            Intent i = new Intent(LoginAdminActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }else {
            Toast.makeText(this, "nhap sai tai khoan hoac mat khau", Toast.LENGTH_SHORT).show();
        }
    }
}