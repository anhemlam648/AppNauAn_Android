package android.vutrungnghia.appnauan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.vutrungnghia.appnauan.Service.MyService;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMusic extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Button btnplay = findViewById(R.id.btnplay);
        Button btnstop = findViewById(R.id.btnstop);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent play = new Intent(ActivityMusic.this, MyService.class);
                startService(play);
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stop = new Intent(ActivityMusic.this,MyService.class);
                stopService(stop);
            }
        });
    }
}
