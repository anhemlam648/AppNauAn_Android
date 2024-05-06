package android.vutrungnghia.appnauan.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.vutrungnghia.appnauan.DTO.RecipeModel;


import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBname = "Huongdannauan.db";
    public static final String TABLE_NAME ="users_table";
    public static final String RECIPE_TABLE_NAME ="recipe_table";

    public static final String SESSION_TABLE_NAME = "users_table";

    public DBHelper(@Nullable Context context) {
        super(context, DBname, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, EMAIL TEXT)");
//        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, EMAIL TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + RECIPE_TABLE_NAME + " (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "AUTHOR TEXT," +
                "DATE_POSTED TEXT," +
                "CONTENT TEXT," +
                "IMAGE_BLOB BLOB," +  // Column to store the path of the image file
                "USER_ID INTEGER," +  // Foreign key column
                "DIFFICULTY INTEGER," + // Column to store the difficulty level (e.g., 1 for easy, 2 for medium, 3 for hard)
                "FOREIGN KEY (USER_ID) REFERENCES " + TABLE_NAME + "(ID)" +
                ")");
//        // Create RecipeType table
//        sqLiteDatabase.execSQL("CREATE TABLE RecipeType (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)");
//
//        // Create Recipe table
//        sqLiteDatabase.execSQL("CREATE TABLE " + RECIPE_TABLE_NAME + " (" +
//                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "NAME TEXT," +
//                "AUTHOR TEXT," +
//                "DATE_POSTED TEXT," +
//                "CONTENT TEXT," +
//                "IMAGE_BLOB BLOB," +
//                "USER_ID INTEGER," +
//                "DIFFICULTY INTEGER," +
//                "RECIPE_TYPE_ID INTEGER," +  // New column for RecipeType
//                "FOREIGN KEY (USER_ID) REFERENCES " + TABLE_NAME + "(ID)," +
//                "FOREIGN KEY (RECIPE_TYPE_ID) REFERENCES RecipeType(ID)" +
//                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ RECIPE_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", username);
        contentValues.put("PASSWORD", password);
        contentValues.put("EMAIL", email);

        // Convert hình ảnh sang byte array (byte[])
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//
//        contentValues.put("IMAGEBLOB", byteArray);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

//    public boolean login(String username, String password) {
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM users_table WHERE USERNAME = ? AND PASSWORD = ?", new String[]{username, password});
//
//        boolean result = cursor.getCount() > 0;
//
//        cursor.close();
//        return result;
//    }
        public String login(String username, String password) {
            SQLiteDatabase database = this.getReadableDatabase();
            String userId = null;

            Cursor cursor = database.rawQuery("SELECT ID FROM users_table WHERE USERNAME = ? AND PASSWORD = ?", new String[]{username, password});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy ID từ kết quả truy vấn
                userId = cursor.getString(cursor.getColumnIndex("ID"));
                cursor.close();
            }

            return userId;
        }
//    public void clearUserSession() {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.delete(SESSION_TABLE_NAME, null, null);
//    }
    public boolean addRecipe(String recipeName, String author, String datePosted, String content, byte[] imageBytes, int userId, int difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", recipeName);
        values.put("AUTHOR", author);
        values.put("DATE_POSTED", datePosted);
        values.put("CONTENT", content);
        values.put("IMAGE_BLOB", imageBytes);
        values.put("USER_ID", userId);
        values.put("DIFFICULTY", difficulty);
        long result = db.insert(RECIPE_TABLE_NAME, null, values);
        return result != -1;
    }
    public Cursor searchRecipes(String recipeName) {
        SQLiteDatabase db = this.getReadableDatabase();
//        String[] projection = {
//                "ID AS _id",
//                "NAME",
//                "AUTHOR",
//                "CONTENT",
//                "DIFFICULTY",
//                "DATE_POSTED",
//                "IMAGE_BLOB"
//
//        };
        String[] projection = {
                "ID AS _id",
                "NAME",
                "AUTHOR",
                "CONTENT",
                "DIFFICULTY",
                "DATE_POSTED",
                "IMAGE_BLOB"
        };
        String selection = "NAME LIKE ? COLLATE NOCASE";
        String[] selectionArgs = {"%" + recipeName + "%"};
        String sortOrder = "NAME ASC";

        Cursor cursor = db.query(
                RECIPE_TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return cursor;
    }


//    public Cursor getAllRecipesCursor() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + RECIPE_TABLE_NAME;
//        return db.rawQuery(query, null);
//    }
    public boolean deleteRecipe(int recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "ID = ?";
        String[] selectionArgs = {String.valueOf(recipeId)};
        int deletedRows = db.delete(RECIPE_TABLE_NAME, selection, selectionArgs);
        return deletedRows > 0;
    }

    public boolean editRecipe(int recipeId, String author, String datePosted,int difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String selection = "ID = ?";
            String[] selectionArgs = {String.valueOf(recipeId)};
            ContentValues values = new ContentValues();
            values.put("AUTHOR", author);
            values.put("DATE_POSTED", datePosted);
//            values.put("CONTENT", content);
            values.put("DIFFICULTY", difficulty);

            int result = db.update(RECIPE_TABLE_NAME, values, selection, selectionArgs);

            return result > 0;
        } finally {
            db.close();
        }
    }

//    public List<Integer> getRecipeIdsByUserId(int userId) {
//        List<Integer> recipeIds = new ArrayList<>();
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT ID FROM " + RECIPE_TABLE_NAME + " WHERE USER_ID = ?", new String[]{String.valueOf(userId)});
//
//        while (cursor.moveToNext()) {
//            int recipeId = cursor.getInt(cursor.getColumnIndex("ID"));
//            recipeIds.add(recipeId);
//        }
//
//        cursor.close();
//        return recipeIds;
//    }
//    public Cursor getRecipeData(int recipeId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String[] projection = {
//                "ID",
//                "NAME",
//                "AUTHOR",
//                "DATE_POSTED",
//                "CONTENT",
//                "IMAGE_BLOB",
//                "USER_ID",
//                "DIFFICULTY"
//        };
//
//        String selection = "ID=?";
//        String[] selectionArgs = {String.valueOf(recipeId)};
//
//        return db.query(RECIPE_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
//    }
    public Cursor getUserData(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "ID",
                "USERNAME",
                "EMAIL"

        };

        String selection = "ID=?";
        String[] selectionArgs = {String.valueOf(userId)};

        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }
    public String getuserName(int userId) {
        Cursor cursor = getUserData(userId);
        String userName = null;

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int username = cursor.getColumnIndex("USERNAME");
//                userName = cursor.getString(cursor.getColumnIndex("USERNAME"));
                if (username >= 0) {
                    userName = cursor.getString(username);
                } else {
                    userName = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return userName;
    }

    public String getuserEmail(int userId) {
        Cursor cursor = getUserData(userId);
        String userEmail = null;
        try {
            // Kiểm tra xem cột có tồn tại hay không
            if (cursor != null && cursor.moveToFirst()) {
                int email = cursor.getColumnIndex("EMAIL");
                if (email >= 0) {
                    userEmail = cursor.getString(email);
                } else {
                    userEmail = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo rằng cursor không null
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return userEmail;
    }

//    public byte[] getImageBytess(int userId) {
//        Cursor cursor = getUserData(userId);
//        byte[] imageBytess = null;
//
//        try {
//            // Check if the cursor is not null and has at least one row
//            if (cursor != null && cursor.moveToFirst()) {
//                // Retrieve the blob data
//                imageBytess = cursor.getBlob(cursor.getColumnIndex("IMAGEBLOB"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // Close the cursor after use
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//
//        return imageBytess;
//    }
//    public byte[] getImageBytes(int recipeId) {
//        Cursor cursor = getRecipeData(recipeId);
//        byte[] imageBytes = null;
//
//        if (cursor != null && cursor.moveToFirst()) {
//            imageBytes = cursor.getBlob(cursor.getColumnIndex("IMAGE_BLOB"));
//            cursor.close();
//        }
//
//        return imageBytes;
//    }

//    public String getRecipeName(int recipeId) {
//        Cursor cursor = getRecipeData(recipeId);
//        String recipeName = null;
//
//        if (cursor != null && cursor.moveToFirst()) {
//            recipeName = cursor.getString(cursor.getColumnIndex("NAME"));
//            cursor.close();
//        }
//
//        return recipeName;
//    }
//
//    public String getRecipeAuthor(int recipeId) {
//        Cursor cursor = getRecipeData(recipeId);
//        String recipeAuthor = null;
//
//        if (cursor != null && cursor.moveToFirst()) {
//            recipeAuthor = cursor.getString(cursor.getColumnIndex("AUTHOR"));
//            cursor.close();
//        }
//
//        return recipeAuthor;
//    }


//    public List<RecipeModel> getAllRecipes() {
//        List<RecipeModel> recipeList = new ArrayList<>();
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.query(RECIPE_TABLE_NAME, null, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            RecipeModel recipe = new RecipeModel();
//            recipe.setId(cursor.getInt(cursor.getColumnIndex("ID")));
//            recipe.setName(cursor.getString(cursor.getColumnIndex("NAME")));
//            recipe.setAuthor(cursor.getString(cursor.getColumnIndex("AUTHOR")));
//            recipe.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
//            recipe.setDatePosted(cursor.getString(cursor.getColumnIndex("DATE_POSTED")));
//
//            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex("IMAGE_BLOB"));
//            recipe.setImageBytes(imageBytes);
//            int difficulty = cursor.getInt(cursor.getColumnIndex("DIFFICULTY"));
//            recipe.setDifficulty(difficulty);
//
//            // Thêm các dòng khác tương ứng
//            recipeList.add(recipe);
//        }
//
//        cursor.close();
//        return recipeList;
//    }

    public List<RecipeModel> getAllRecipes() {
        List<RecipeModel> recipeList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(RECIPE_TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    RecipeModel recipe = new RecipeModel();
                    int idColumnIndex = cursor.getColumnIndex("ID");
                    int nameColumnIndex = cursor.getColumnIndex("NAME");
                    int authorColumnIndex = cursor.getColumnIndex("AUTHOR");
                    int contentColumnIndex = cursor.getColumnIndex("CONTENT");
                    int datePostedColumnIndex = cursor.getColumnIndex("DATE_POSTED");
                    int imageBlobColumnIndex = cursor.getColumnIndex("IMAGE_BLOB");
                    int difficultyColumnIndex = cursor.getColumnIndex("DIFFICULTY");

                    // Kiểm tra cột có tồn tại không
                    if (idColumnIndex >= 0) {
                        recipe.setId(cursor.getInt(idColumnIndex));
                    }

                    if (nameColumnIndex >= 0) {
                        recipe.setName(cursor.getString(nameColumnIndex));
                    }

                    if (authorColumnIndex >= 0) {
                        recipe.setAuthor(cursor.getString(authorColumnIndex));
                    }

                    if (contentColumnIndex >= 0) {
                        recipe.setContent(cursor.getString(contentColumnIndex));
                    }

                    if (datePostedColumnIndex >= 0) {
                        recipe.setDatePosted(cursor.getString(datePostedColumnIndex));
                    }

                    if (imageBlobColumnIndex >= 0) {
                        byte[] imageBytes = cursor.getBlob(imageBlobColumnIndex);
                        recipe.setImageBytes(imageBytes);
                    }

                    if (difficultyColumnIndex >= 0) {
                        int difficulty = cursor.getInt(difficultyColumnIndex);
                        recipe.setDifficulty(difficulty);
                    }
                    recipeList.add(recipe);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return recipeList;
    }
}
