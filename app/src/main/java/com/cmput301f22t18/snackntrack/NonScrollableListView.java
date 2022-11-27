package com.cmput301f22t18.snackntrack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A custom ListView that is not scrollable
 *
 * @author Casper Nguyen
 */
public class NonScrollableListView extends ListView {

    /**
     * Constructor for NonScrollableListView
     * @param context Context
     */
    public NonScrollableListView(Context context) {
        super(context);
    }

    /**
     * Constructor for NonScrollableListView
     * @param context Context
     * @param attrs AttributeSet
     */
    public NonScrollableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor for NonScrollableListView
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyleAttr an int
     */
    public NonScrollableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
