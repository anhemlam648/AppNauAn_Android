package android.vutrungnghia.appnauan.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.vutrungnghia.appnauan.R;

import androidx.appcompat.app.AppCompatActivity;

import java.security.Provider;

public class MyService extends Service {
    private MediaPlayer music;
    @Override
    public void onCreate(){
        super.onCreate();
        music = MediaPlayer.create(this, R.raw.tiptoe2);
        music.setLooping(true);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "Service started");
        music.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        Log.d("MyService", "Service destroyed");
        super.onDestroy();
        music.stop();
        music.release();
    }
}
