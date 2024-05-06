package android.vutrungnghia.appnauan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.vutrungnghia.appnauan.Controller.HomeFragment;
import android.vutrungnghia.appnauan.Controller.InfoFragment;
import android.vutrungnghia.appnauan.Controller.RecipeFragment;
import android.vutrungnghia.appnauan.Database.DBHelper;


import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    private NetworkChange Receiver;
    private BottomNavigationView bottomNavigationView;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
       // Mặc định trả về HomeFragment
        replaceFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView); // Ensure the ID matches
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (item.getItemId() == R.id.shorts) {
                replaceFragment(new RecipeFragment());
                return true;
            }else if (item.getItemId() == R.id.add) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                return true;
            }
            else if (item.getItemId() == R.id.edit) {
                Intent intent = new Intent(MainActivity.this,ActivityEdit.class);
                startActivity(intent);
                return true;
            }else if (item.getItemId() == R.id.library) {
                replaceFragment(new InfoFragment());
                return true;
            }

            return false;
        });
        //Đăng ký BroadcastReceiver
        Receiver = new NetworkChange();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);   //Nhận sự kiện kết nối mạng
        registerReceiver(Receiver, intentFilter);

    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Hủy đăng ký BroadcastReceiver khi không còn cần
//        unregisterReceiver(Receiver);
//    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}