package moms.app.android.ui;

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

    public PollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // creating new gesture detector
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    // delegate the event to the gesture detector
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
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
