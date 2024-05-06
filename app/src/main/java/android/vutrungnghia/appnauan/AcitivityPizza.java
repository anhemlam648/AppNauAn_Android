package android.vutrungnghia.appnauan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.vutrungnghia.appnauan.DTO.RecipeModel;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AcitivityPizza extends AppCompatActivity {
    private int recipeId;

    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        dbHelper = new DBHelper(AcitivityPizza.this);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        recipeId = sharedPreferences.getInt("selected_recipe_id", 1);
        float savedRating = sharedPreferences.getFloat("recipe_rating_" + recipeId, 0);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(savedRating);
        ImageButton btnDelete = findViewById(R.id.btnDeleteRecipe);
        ImageButton btnShare = findViewById(R.id.btnshare);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = dbHelper.deleteRecipe(recipeId);
                if (isDeleted) {
                    Toast.makeText(AcitivityPizza.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AcitivityPizza.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float oldRating = sharedPreferences.getFloat("recipe_rating_" + recipeId, 0);
                float newRating = (oldRating + rating) / 2;
                // Lưu giá trị đánh giá vào SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("recipe_rating_" + recipeId, newRating);
                editor.apply();
            }
        });

    }
    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        String appLink = "https://drive.google.com/drive/folders/1LhjQ4oUEmJGI7aXJ7jAPjK5BuXQ0krnF";
        sendIntent.putExtra(Intent.EXTRA_TEXT, appLink);
        String chooserTitle = "Chia sẻ ứng dụng";
        Intent shareIntent = Intent.createChooser(sendIntent, chooserTitle);
        startActivity(shareIntent);
    }

}
