package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sonans.appdatxe_duan01_nhom6.R;

public class LoginAdminActivity extends AppCompatActivity {

    EditText edUserName, edPass;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        edUserName = findViewById(R.id.ed_UserName);
        edPass = findViewById(R.id.ed_Password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edUserName.getText().toString().equals("admin") && edPass.getText().toString().equals("123")){
                    Intent i = new Intent(LoginAdminActivity.this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(LoginAdminActivity.this, "dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(LoginAdminActivity.this, "nhap sai tai khoan hoac mat khau", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}