package android.vutrungnghia.appnauan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vutrungnghia.appnauan.Adapter.RecipeAdapter;
import android.vutrungnghia.appnauan.Controller.RecipeFragment;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {
    private Context context;
    private int recipeId;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private String searchKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        context = this;
        adapter = new RecipeAdapter();
        recyclerView.setAdapter(adapter);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchKeyword = query;
                Cursor cursor = searchRecipes(query);
                adapter.swapCursor(cursor, searchKeyword);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
    private Cursor searchRecipes(String recipeName) {
        DBHelper dbHelper = new DBHelper(this);
        return dbHelper.searchRecipes(recipeName);
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
        private Cursor cursor;
        private boolean isSearchResultVisible;
        public void swapCursor(Cursor newCursor, String searchKeyword) {
            if (cursor != null) {
                cursor.close();
            }
            cursor = newCursor;
            isSearchResultVisible = !TextUtils.isEmpty(searchKeyword);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_item, parent, false);
            return new RecipeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
            if (cursor != null && cursor.moveToPosition(position)) {
                String recipeName = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
                String author = cursor.getString(cursor.getColumnIndexOrThrow("AUTHOR"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("CONTENT"));
                int difficulty = cursor.getInt(cursor.getColumnIndexOrThrow("DIFFICULTY"));
                String datePosted = cursor.getString(cursor.getColumnIndexOrThrow("DATE_POSTED"));
                // Kiểm tra xem tên công thức có khớp với từ khóa tìm kiếm không
                if (isSearchResultVisible && !recipeName.toLowerCase().contains(searchKeyword.toLowerCase())) {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                } else {
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

                    holder.textRecipeName.setText("Tên công thức: " + recipeName);
                    holder.textAuthor.setText("Người tạo: " + author);
//                    holder.textcontent.setText("Content: " + content);
                    holder.textDifficulty.setText("Độ khó: " + getStars(String.valueOf(difficulty)));
                    holder.textDatePosted.setText("Ngày đăng: " + datePosted);
                    //color
                    holder.textRecipeName.setTextColor(Color.parseColor("#8A2BE2"));
                    holder.textAuthor.setTextColor(Color.parseColor("#8A2BE2"));
                    holder.textDifficulty.setTextColor(Color.parseColor("#8A2BE2"));
                    holder.textDatePosted.setTextColor(Color.parseColor("#8A2BE2"));
                    byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("IMAGE_BLOB"));
                    if (imageBytes != null && imageBytes.length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        holder.imageView.setImageBitmap(bitmap);
                    } else {
                        holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                            saveSelectedRecipeId(recipeId);
                            openRecipeDetails(recipeId);
                        }
                    });
                }
            }
        }
        private String getStars(String difficultyString) {
            int difficulty = Integer.parseInt(difficultyString);
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < difficulty; i++) {
                stars.append("★");
            }
            return stars.toString();
        }
        @Override
        public int getItemCount() {
            return cursor != null ? cursor.getCount() : 0;
        }

        public class RecipeViewHolder extends RecyclerView.ViewHolder {
            public TextView textRecipeName;
            public TextView textAuthor;
            public TextView textcontent;
            public TextView textDifficulty;
            public TextView textDatePosted;
            public ImageView imageView;
            public RecipeViewHolder(View itemView) {
                super(itemView);
                textRecipeName = itemView.findViewById(R.id.textRecipeName);
                textAuthor = itemView.findViewById(R.id.textAuthor);
//                textcontent = itemView.findViewById(R.id.textContent);
                textDifficulty = itemView.findViewById(R.id.textDifficulty);
                textDatePosted = itemView.findViewById(R.id.textDatePosted);
                imageView = itemView.findViewById(R.id.imageView2);
            }
        }
    }
    private void openRecipeDetails(int recipeId) {
        Intent intent;
        switch (recipeId) {
            case 1:
                intent = new Intent(this, AcitivityPizza.class);
                break;
            case 2:
                intent = new Intent(this, ActivitySalad.class);
                break;
            case 3:
                intent = new Intent(this, ActivityTrungluoc.class);
                break;
            case 4:
                intent = new Intent(this, ActivityBanhKem.class);
                break;
            default:
                intent = new Intent(this, ActivitySalad.class);
                break;
        }

        intent.putExtra("selected_recipe_id", recipeId);
        startActivity(intent);
    }
    private void saveSelectedRecipeId(int recipeId) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selected_recipe_id", recipeId);
        editor.apply();
    }

}
