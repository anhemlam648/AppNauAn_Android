package android.vutrungnghia.appnauan.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.vutrungnghia.appnauan.ActivityLogin;
import android.vutrungnghia.appnauan.ActivityMusic;
import android.vutrungnghia.appnauan.AddActivity;
import android.vutrungnghia.appnauan.Database.DBHelper;
import android.vutrungnghia.appnauan.MainActivity;
import android.vutrungnghia.appnauan.R;
import android.vutrungnghia.appnauan.Service.MyService;
import android.vutrungnghia.appnauan.Session.SessionManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class InfoFragment extends Fragment {

    ImageView imageView;
    private SessionManager sessionManager;
    TextView usernameTextView, emailTextView;
    private DBHelper dbHelper;
    Button btnlogout;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_logout, container, false);
        btnlogout = view.findViewById(R.id.logoutButton);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ActivityLogin.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Đăng xuất", Toast.LENGTH_SHORT).show();
                Intent stop = new Intent(getActivity(), MyService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getActivity().stopService(stop);
                } else {
                    getActivity().stopService(stop);
                }

            }
        });
        imageView = view.findViewById(R.id.profileImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        dbHelper = new DBHelper(requireContext());
        sessionManager = new SessionManager(requireContext());

        String userId = sessionManager.getCurrentUserId();

        if (userId != null && !userId.isEmpty()) {
            int intUserId = Integer.parseInt(userId);
            displayUserData(intUserId);
        } else {
            Log.e("InfoFragment", "ID người dùng không hợp lệ: " + userId);
        }


        return view;
    }

    private void displayUserData(int userId) {
        Cursor userDataCursor = dbHelper.getUserData(userId);

        try {
            if (userDataCursor != null && userDataCursor.moveToFirst()) {
                String username = dbHelper.getuserName(userId);
                String email = dbHelper.getuserEmail(userId);
                usernameTextView.setText(username);
                emailTextView.setText(email);
            } else {
                Log.e("InfoFragment", "Không tìm thấy ID người dùng: " + userId);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (userDataCursor != null && !userDataCursor.isClosed()) {
                userDataCursor.close();
            }
        }
    }



}