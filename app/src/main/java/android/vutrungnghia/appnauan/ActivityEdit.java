package android.vutrungnghia.appnauan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityEdit extends AppCompatActivity {
    private EditText editRecipeId, editAuthor, editDatePosted, editDifficulty,editContent;
    private Button btnEditRecipe;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editRecipeId = findViewById(R.id.editRecipeId);
        editAuthor = findViewById(R.id.editAuthor);
//        editContent = findViewById(R.id.editContent);
        editDatePosted = findViewById(R.id.editDatePosted);
        editDifficulty = findViewById(R.id.editDifficulty);
        btnEditRecipe = findViewById(R.id.btnEditRecipe);
        dbHelper = new DBHelper(this);
        btnEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performEdit();
            }
        });
    }

    private void performEdit() {
        String recipeName = editRecipeId.getText().toString().trim();
        String author = editAuthor.getText().toString().trim();
        String datePosted = editDatePosted.getText().toString().trim();
        String difficultyStr = editDifficulty.getText().toString().trim();
        if (validateInput(recipeName, author, datePosted, difficultyStr)) {
            // Nếu hàm trả về true (các trường không rỗng), thực hiện logic chỉnh sửa
            try {
                int recipeId = Integer.parseInt(recipeName);
                int difficulty = Integer.parseInt(difficultyStr);

                boolean isEdited = dbHelper.editRecipe(recipeId, author, datePosted, difficulty);

                if (isEdited) {
                    editAuthor.setText("");
                    editDifficulty.setText("");
                    editRecipeId.setText("");
                    editDatePosted.setText("");
                    Toast.makeText(ActivityEdit.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityEdit.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ActivityEdit.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(ActivityEdit.this, "Lỗi ép kiểu số nguyên", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean validateInput(String recipeName, String author, String datePosted, String difficulty) {
        boolean isValid = true;

        if (TextUtils.isEmpty(recipeName)) {
            editRecipeId.setError("Nhập id công thức");
            isValid = false;
        }
        if (TextUtils.isEmpty(author)) {
            editAuthor.setError("Nhập tên tác giả");
            isValid = false;
        }
        if (TextUtils.isEmpty(datePosted)) {
            editDatePosted.setError("Nhập ngày đăng");
            isValid = false;
        }
        if (TextUtils.isEmpty(difficulty)) {
            editDifficulty.setError("Nhập độ khó");
            isValid = false;
        }

        return isValid;
    }

}
