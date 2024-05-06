package android.vutrungnghia.appnauan.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vutrungnghia.appnauan.AcitivityPizza;
import android.vutrungnghia.appnauan.ActivityMusic;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.vutrungnghia.appnauan.MainActivity;
import android.vutrungnghia.appnauan.R;
import android.vutrungnghia.appnauan.SearchActivity;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;




import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vutrungnghia.appnauan.AcitivityPizza;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.vutrungnghia.appnauan.R;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private ImageButton btnsearch,btnmusic;
    private View view;
    private ListView listViewSearchResults;  // Assuming you have a ListView in your layout
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_, container, false);
        btnsearch = view.findViewById(R.id.btnsearch);
        btnmusic = view.findViewById(R.id.btnmusic);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        btnmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ActivityMusic.class);
                startActivity(intent1);
            }
        });
        return view;
    }


}
