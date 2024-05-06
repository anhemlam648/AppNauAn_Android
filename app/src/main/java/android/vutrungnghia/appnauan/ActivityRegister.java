package android.vutrungnghia.appnauan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ActivityRegister extends AppCompatActivity {
//    private byte[] imageBytes;
    TextInputLayout tilUsername, tilPassword, tilEmail;
    TextInputEditText etUsername, etPassword, etEmail;
    Button btnRegister;

    private DBHelper dbHelper;

   private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        tilEmail = findViewById(R.id.tilEmail);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Kiểm tra tính hợp lệ của dữ liệu
        if (validateInput(username, password, email)) {
            // Kiểm tra xem tên người dùng đã tồn tại chưa
            if (isUsernameExists(username)) {
                Toast.makeText(this, "Người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                    boolean isInserted = dbHelper.addUser(username, password, email);

                    if (isInserted) {
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    private boolean validateInput(String username, String password, String email) {

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Nhập tên người dùng");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Nhập mật khẩu");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Nhập email");
            return false;
        }

        return true;
    }
    private boolean isUsernameExists(String username) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users_table WHERE USERNAME=? OR EMAIL=?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
