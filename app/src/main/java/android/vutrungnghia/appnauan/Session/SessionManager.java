package android.vutrungnghia.appnauan.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.UUID;

public class SessionManager {
    private static final String KEY_USER_ID = "user_id";
    private SharedPreferences sharedPreferences;

    //lưu trữ truy xuất thông tin từ ứng dụng
    public SessionManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //lưu trữ đối tượng
    public void setCurrentUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId); //lấy giá trị khóa KEY_USER_ID đưa vào userId
        editor.apply();
    }

    public String getCurrentUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }


}
