package moms.app.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Steve on 3/29/14.
 */
public class PollImageView extends ImageView {

    GestureDetector gestureDetector;
    int tapCount = 0;

    public PollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PollImageView,
                0, 0);

        try {
            tapCount = a.getInt(R.styleable.PollImageView_tapCount, 0);
        } finally {
            a.recycle();
        }

        // creating new gesture detector
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public int getTapCount(){
        return tapCount;
    }

    // delegate the event to the gesture detector
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            Toast.makeText(getContext().getApplicationContext(), "onDown() occurred", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            Toast.makeText(getContext().getApplicationContext(), "onFling() occurred", Toast.LENGTH_SHORT).show();
            return true;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(getContext().getApplicationContext(), "onDoubleTap() occurred", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
