package android.vutrungnghia.appnauan.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vutrungnghia.appnauan.DTO.RecipeModel;
import android.vutrungnghia.appnauan.R;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<RecipeModel> recipeList;
//    public void updateData(List<RecipeModel> newRecipes) {
//        recipeList.clear();
//        recipeList.addAll(newRecipes);
//        notifyDataSetChanged();
//    }

    public RecipeAdapter(List<RecipeModel> recipeList) {
        this.recipeList = recipeList;
    }
    public RecipeModel getItem(int position) {
        return recipeList.get(position);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeModel recipe = recipeList.get(position);
        holder.name.setText("Tên công thức: " + recipe.getName());
        holder.author.setText("Người tạo: " + recipe.getAuthor());
//        holder.textContent.setText("Nội dung: " + recipe.getContent());
        holder.difficulty.setText("Độ khó: " + getStars(String.valueOf(recipe.getDifficulty())));
        holder.date.setText("Ngày đăng: " + recipe.getDatePosted());
        byte[] imageBytes = recipe.getImageBytes();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            //mặc định hình khi không có hình ảnh
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
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
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView author;

        TextView textContent;
        TextView date;
        TextView difficulty;
        ImageView imageView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textRecipeName);
            author = itemView.findViewById(R.id.textAuthor);
//            textContent = itemView.findViewById(R.id.textContent);
            difficulty = itemView.findViewById(R.id.textDifficulty);
            date = itemView.findViewById(R.id.textDatePosted);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }
}
