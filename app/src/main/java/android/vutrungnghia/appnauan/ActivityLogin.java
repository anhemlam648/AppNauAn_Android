package android.vutrungnghia.appnauan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.vutrungnghia.appnauan.Session.SessionManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ActivityLogin extends AppCompatActivity {
    private DBHelper dbHelper;
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin,btnregister;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DBHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnregister = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });
    }

//    private void LoginUser() {
//        String username = etUsername.getText().toString().trim();
//        String password = etPassword.getText().toString().trim();
//
//        if (isValidLogin(username, password)) {
//            // Insert user data into the database
//            boolean result = dbHelper.login(username, password);
//
//            if (result) {
//                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
//                startActivity(intent);
//                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
    private void LoginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        sessionManager = new SessionManager(this);
        if (isValidLogin(username, password)) {
            String userId = dbHelper.login(username, password);

            if (userId != null) {
                // Lưu userId vào SessionManager
                sessionManager.setCurrentUserId(userId);
                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean isValidLogin(String username, String password) {
        return true;
    }
}
