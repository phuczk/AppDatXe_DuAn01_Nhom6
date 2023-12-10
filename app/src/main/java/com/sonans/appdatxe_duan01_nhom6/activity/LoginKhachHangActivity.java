package com.sonans.appdatxe_duan01_nhom6.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sonans.appdatxe_duan01_nhom6.R;

public class LoginKhachHangActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edUserName, edPassword;
    TextView tvSignUp;
    CheckBox chkRememberPass;
    private DatabaseReference databaseReference;
    public FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_khach_hang);btnLogin = findViewById(R.id.btnLogin);
        edUserName = findViewById(R.id.ed_UserName);
        edPassword = findViewById(R.id.ed_Password);
        tvSignUp = findViewById(R.id.tvSignUp);
        chkRememberPass = findViewById(R.id.chkRememberPass);


        db = FirebaseFirestore.getInstance();

        SharedPreferences sp = getSharedPreferences("USER_FILE_CUSTOMER", MODE_PRIVATE);
        String userRemember = sp.getString("USERNAME_CUSTOMER", "");
        String passwordRemember = sp.getString("PASSWORD_CUSTOMER", "");
        Boolean check = sp.getBoolean("CHECKBOX", false);

        edUserName.setText(userRemember);
        edPassword.setText(passwordRemember);
        chkRememberPass.setChecked(check);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginKhachHangActivity.this, SignUpKhachHangActivity.class);
                startActivity(i);
            }
        });
    }

    public void rememberPassword(String u, String p, boolean c){
        SharedPreferences sp = getSharedPreferences("USER_FILE_CUSTOMER", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if(!c){
            edit.clear();
        } else {
            edit.putString("USERNAME_CUSTOMER", u);
            edit.putString("PASSWORD_CUSTOMER", p);
            edit.putBoolean("CHECKBOX", c);
        }
        edit.commit();
    }


    public void checkLogin(){
        String userName = edUserName.getText().toString().trim();
        String userPass = edPassword.getText().toString().trim();
        db.collection("KhachHang")
                .whereEqualTo("tenDangNhap", userName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Toast.makeText(LoginKhachHangActivity.this, "sai ten dang nhap", Toast.LENGTH_SHORT).show();
                            }else {
                                String passwordFromDatabase = task.getResult().getDocuments().get(0).getString("matKhau");
                                if (passwordFromDatabase != null && passwordFromDatabase.equals(userPass)) {
                                    // Mật khẩu đúng
                                    Toast.makeText(LoginKhachHangActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    rememberPassword(userName, userPass, chkRememberPass.isChecked());
                                    SharedPreferences sp = getSharedPreferences("MaKHang", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putString("maKH", userName);
                                    edit.commit();
                                    Intent i = new Intent(LoginKhachHangActivity.this, KhachHangActivity.class);
                                    startActivity(i);
                                } else {
                                    // Mật khẩu không đúng
                                    Toast.makeText(LoginKhachHangActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(LoginKhachHangActivity.this, "loi khi kiem tra", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}