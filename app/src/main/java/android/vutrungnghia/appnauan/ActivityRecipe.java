//package android.vutrungnghia.appnauan;
//
//import android.os.Bundle;
//import android.vutrungnghia.appnauan.Controller.RecipeFragment;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class ActivityRecipe extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe);
//        if (savedInstanceState == null) {
//            RecipeFragment recipeFragment = new RecipeFragment();
//            Bundle args = new Bundle();
//            args.putString("recipe_name", "Recipe Name");
//            recipeFragment.setArguments(args);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragmentContainer, recipeFragment)
//                    .commit();
//        }
//    }
//}
