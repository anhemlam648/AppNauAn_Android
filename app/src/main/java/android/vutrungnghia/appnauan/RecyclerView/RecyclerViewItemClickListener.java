package android.vutrungnghia.appnauan.RecyclerView;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener{
    private OnItemClickListener mListener; //lưu trữ đối tượng thực hiện trên OnItemClickListener

    // trả về view đang diễn ra và vị trí giúp linh hoạt khi người dùng tương tác với giao diện
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //xử lý các cử chỉ của người dùng trên màn hình android
    private GestureDetector mGestureDetector;

    public RecyclerViewItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener; //truyền vào tham số listener
        //tạo đối tượng mới ghi đè 1 lần chạm và trả về true
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());// tham chiếu và trả về tìm view dưới tọa độ x và y là tọa độ của sự kiện chạm
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    // xử lý các sự kiện chạm xảy ra trên RecyclerView
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    // ngăn chặn sự kiện xảy ra trên RecyclerView khi kéo xuống kéo lên
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
