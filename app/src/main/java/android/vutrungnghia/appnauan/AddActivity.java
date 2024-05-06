package android.vutrungnghia.appnauan;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.vutrungnghia.appnauan.Controller.RecipeFragment;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.vutrungnghia.appnauan.Session.SessionManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private byte[] imageBytes;
    private DBHelper dbHelper;
    private static final int NOTIFICATION_ID = 1;
    EditText txtrecipename, txtauthor, txtdatepost, txtcontent, txtimage, txtdifficulty;

    TextView txtuserid;

    private SessionManager sessionManager;
    Button btnadd;
    ImageButton btnChooseImage;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper = new DBHelper(this);
        txtrecipename = findViewById(R.id.editRecipeName);
        txtauthor = findViewById(R.id.editAuthor);
        txtdatepost = findViewById(R.id.editDatePosted);
        txtcontent = findViewById(R.id.editContent);
        txtimage = findViewById(R.id.editImagePath);
        txtuserid = findViewById(R.id.textUserId);
        txtdifficulty = findViewById(R.id.editDifficulty);
        btnadd = findViewById(R.id.btnAddRecipe);
        dbHelper = new DBHelper(this);
        sessionManager = new SessionManager(this);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // lấy UserId từ sessionManager
                String userIdString = sessionManager.getCurrentUserId();
                if (!TextUtils.isEmpty(userIdString)) {
                    userId = Integer.parseInt(userIdString);
                    txtuserid.setText(String.valueOf(userId));
//                    txtuserid.setVisibility(View.GONE);
//                    Cursor userDataCursor = dbHelper.getUserData(userId);
//                    txtimage.setEnabled(false);
                    String recipeName = txtrecipename.getText().toString();
                    String author = txtauthor.getText().toString();
                    String datePosted = txtdatepost.getText().toString();
                    String content = txtcontent.getText().toString();
                    String difficultyText = txtdifficulty.getText().toString();

                    if (userId != 0 && validateInput(recipeName, author, datePosted, content, String.valueOf(userId), difficultyText)) {
                        int difficulty = difficultyText.isEmpty() ? 0 : Integer.parseInt(difficultyText);
                        boolean result = dbHelper.addRecipe(recipeName, author, datePosted, content, imageBytes, userId, difficulty);

                        if (result) {
                            showNotification("Đã thêm công thức mới");
                            txtauthor.setText("");
                            txtcontent.setText("");
                            txtdatepost.setText("");
                            txtdifficulty.setText("");
                            txtrecipename.setText("");
                            Toast.makeText(AddActivity.this, "Thêm công thức mới thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddActivity.this, "Thêm công thức mới thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddActivity.this, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddActivity.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Mơ
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }

        });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            Uri selectedImageUri = data.getData();
//
//            if (selectedImageUri != null) {
//                try {
//                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
//                    byte[] imageBytes = getBytes(inputStream);
//
//                    // Get other recipe details from your UI elements
//                    String recipeName = txtrecipename.getText().toString();
//                    String author = txtauthor.getText().toString();
//                    String datePosted = txtdatepost.getText().toString();
//                    String content = txtcontent.getText().toString();
//
//                    // Check if txtdifficulty has a non-empty text
//                    String difficultyText = txtdifficulty.getText().toString();
//                    int difficulty = difficultyText.isEmpty() ? 0 : Integer.parseInt(difficultyText);
//
//                    // Save imageBytes and other details to the database using dbHelper.addRecipe() method
//                    boolean result = dbHelper.addRecipe(recipeName, author, datePosted, content, imageBytes, userId, difficulty);
//
//                    if (result) {
//                        Toast.makeText(AddActivity.this, "Recipe added successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(AddActivity.this, "Failed to add recipe", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                Uri selectedImageUri = data.getData();

                if (selectedImageUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        // Cập nhật giá trị hình ảnh
                        imageBytes = getBytes(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        private byte[] getBytes(InputStream inputStream) throws IOException {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();
        }
    private void showNotification(String message) {
       // tạo ý định hoạt động cho 1 thiết bị
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // tạo thông báo với android phiên bản 8.0 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "CHANNEL_ID",
                    "Recipe App Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    private boolean validateInput(String recipename, String author, String datepost, String content, String userId, String difficulty) {
        boolean isValid = true;

        if (TextUtils.isEmpty(recipename)) {
            txtrecipename.setError("Nhập tên công thức");
            isValid = false;
        }
        if (TextUtils.isEmpty(author)) {
            txtauthor.setError("Nhập tên tác giả");
            isValid = false;
        }
        if (TextUtils.isEmpty(datepost)) {
            txtdatepost.setError("Nhập ngày đăng");
            isValid = false;
        }
        if (TextUtils.isEmpty(content)) {
            txtcontent.setError("Nhập nội dung");
            isValid = false;
        }
        if (TextUtils.isEmpty(difficulty)) {
            txtdifficulty.setError("Nhập độ khó");
            isValid = false;
        }

        return isValid;
    }

}