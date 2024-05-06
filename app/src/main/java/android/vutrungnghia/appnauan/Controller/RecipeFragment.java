package android.vutrungnghia.appnauan.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vutrungnghia.appnauan.AcitivityPizza;
import android.vutrungnghia.appnauan.ActivityBanhKem;
import android.vutrungnghia.appnauan.ActivitySalad;
import android.vutrungnghia.appnauan.ActivityTrungluoc;
import android.vutrungnghia.appnauan.Adapter.RecipeAdapter;
import android.vutrungnghia.appnauan.DTO.RecipeModel;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.vutrungnghia.appnauan.R;
import android.vutrungnghia.appnauan.RecyclerView.RecyclerViewItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RecipeFragment extends Fragment {
    private String recipeName;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private List<RecipeModel> recipeList;
    private ListView listView;

    private int recipeId;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    private Context context;
    View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe, container, false);
        Bundle args = getArguments();
        if (args != null) {
            String recipeName = args.getString("recipe_name", "");
            TextView recipeNameTextView = view.findViewById(R.id.textRecipeName);
            recipeNameTextView.setText(recipeName);
        }
        dbHelper = new DBHelper(getActivity());

        recyclerView = view.findViewById(R.id.recyclerViewRecipes);
        recipeAdapter = new RecipeAdapter(dbHelper.getAllRecipes());
        Log.d("RecipeFragment", "recyclerView: " + recyclerView);
        Log.d("RecipeFragment", "recipeAdapter: " + recipeAdapter);

        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Xử lý sự kiện click trực tiếp trong Fragment
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Xử lý sự kiện khi một mục được click
                RecipeModel clickedRecipe = recipeAdapter.getItem(position);
                int recipeId = clickedRecipe.getId();
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selected_recipe_id", recipeId);
                editor.apply();
                if (recipeId == 1) {
                    Toast.makeText(getActivity(), "Pizza", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), AcitivityPizza.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                } else if (recipeId == 2) {

                    Toast.makeText(getActivity(), "Salad", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ActivitySalad.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                }
                else if (recipeId == 3) {

                    Toast.makeText(getActivity(), "Trứng Luộc", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ActivityTrungluoc.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);

                }
                else if (recipeId == 4) {
                    Toast.makeText(getActivity(), "Bánh bông lan cupcake", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ActivityBanhKem.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                }
                else if (recipeId == 5) {
                    Toast.makeText(getActivity(), "Pizza", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), AcitivityPizza.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                }
                else if (recipeId == 6) {
                    Intent intent = new Intent(getActivity(), ActivityTrungluoc.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                }else if (recipeId == 7) {
                    Intent intent = new Intent(getActivity(), ActivityTrungluoc.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                }
                else if (recipeId == 8) {
                    Intent intent = new Intent(getActivity(), ActivityTrungluoc.class);
                    intent.putExtra("recipe_id", recipeId);
                    startActivity(intent);
                }

            }
        }));


        return view;
    }
//    public void onDeleteButtonClick(View view) {
//        // Perform delete operation using recipeId
//        boolean isDeleted = dbHelper.deleteRecipe(recipeId);
//
//        if (isDeleted) {
//            // Handle successful deletion, e.g., show a toast
//            Toast.makeText(context, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
//
//            // Finish the activity or navigate back to the recipe list
//
//        } else {
//            // Handle deletion failure, e.g., show a toast
//            Toast.makeText(context, "Failed to delete recipe", Toast.LENGTH_SHORT).show();
//        }
//    }

}




//    private void displayRecipeData(View view, int recipeId) {
//        byte[] imageBytes = dbHelper.getImageBytes(recipeId);
//
//        if (imageBytes != null && imageBytes.length > 0) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//
//            TextView nameTextView = view.findViewById(R.id.textRecipeName);
//            TextView authorTextView = view.findViewById(R.id.textAuthor);
//            ImageView imageView = view.findViewById(R.id.imageView2);
//
//            // Set text data
//            nameTextView.setText("Recipe Name: " + dbHelper.getRecipeName(recipeId));
//            authorTextView.setText("Author: " + dbHelper.getRecipeAuthor(recipeId));
//
//            // Set image data
//            imageView.setImageBitmap(bitmap);
//        } else {
//            // Handle the case where there is no image data
//            TextView nameTextView = view.findViewById(R.id.textRecipeName);
//            TextView authorTextView = view.findViewById(R.id.textAuthor);
//            ImageView imageView = view.findViewById(R.id.imageView2);
//
//            // Set text data
//            nameTextView.setText("Recipe Name: " + dbHelper.getRecipeName(recipeId));
//            authorTextView.setText("Author: " + dbHelper.getRecipeAuthor(recipeId));
//
//            // Set default image when there is no image data
////            imageView.setImageResource(R.drawable.default_image);
//        }
//    }




