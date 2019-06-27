package com.carking.quotationlibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class BottomScrollView extends ScrollView {
  
    private OnScrollToBottomListener onScrollToBottom;  
    private float xDistance, yDistance, xLast, yLast;  
      
    public BottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
  
    public BottomScrollView(Context context) {
        super(context);  
    }  
  
    @SuppressLint("NewApi")
	@Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,  
            boolean clampedY) {  
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);  
        if(scrollY != 0 && null != onScrollToBottom){  
            onScrollToBottom.onScrollBottomListener(clampedY);  
        }  
    }  
   
      @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    	// TODO Auto-generated method stub
    	super.onScrollChanged(l, t, oldl, oldt);
    }
    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener){  
        onScrollToBottom = listener;  
    }  
  
    public interface OnScrollToBottomListener{  
        public void onScrollBottomListener(boolean isBottom);  
    }  
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	// TODO Auto-generated method stub
        switch (ev.getAction()) {  
        case MotionEvent.ACTION_DOWN:
        xDistance = yDistance = 0f;  
        xLast = ev.getX();  
        yLast = ev.getY();  
        break;  
        case MotionEvent.ACTION_MOVE:
        final float curX = ev.getX();  
        final float curY = ev.getY();  
  
        xDistance += Math.abs(curX - xLast);
        yDistance += Math.abs(curY - yLast);
        xLast = curX;  
        yLast = curY;  
  
        if (xDistance > yDistance) {  
        return false;  
        }  
        }  
        return super.onInterceptTouchEvent(ev);  
    }
}  