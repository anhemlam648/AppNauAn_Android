package android.vutrungnghia.appnauan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //kiểm tra đã kết nối mạng chưa trả về hành động
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //tham chiếu và lấy dịch vụ hệ thống
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //trạng thái kết nối mạng
            NetworkInfo active = connectivityManager.getActiveNetworkInfo(); //trả về trạng thái mạng đang sử dụng
            boolean isConnected = active != null && active.isConnectedOrConnecting(); //kiểm tra thiết bị có kết nối mạng hay chưa

            if (isConnected) {
                Toast.makeText(context, "Đã kết nối mạng", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_LONG).show();

            }
        }
    }
}
