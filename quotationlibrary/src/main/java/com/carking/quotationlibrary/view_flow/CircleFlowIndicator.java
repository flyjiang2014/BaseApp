/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.carking.quotationlibrary.view_flow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.carking.quotationlibrary.R;
import com.carking.quotationlibrary.utils.PhoneUtil;


/**
 * A FlowIndicator which draws circles (one for each view). <br/>
 * Availables attributes are:<br/>
 * <ul>
 * activeColor: Define the color used to draw the active circle (default to
 * white)
 * </ul>
 * <ul>
 * inactiveColor: Define the color used to draw the inactive circles (default to
 * 0x44FFFFFF)
 * </ul>
 * <ul>
 * inactiveType: Define how to draw the inactive circles, either stroke or fill
 * (default to stroke)
 * </ul>
 * <ul>
 * activeType: Define how to draw the active circle, either stroke or fill
 * (default to fill)
 * </ul>
 * <ul>
 * fadeOut: Define the time (in ms) until the indicator will fade out (default
 * to 0 = never fade out)
 * </ul>
 * <ul>
 * radius: Define the circle radius (default to 4.0)
 * </ul>
 */
public class CircleFlowIndicator extends View implements FlowIndicator,
        AnimationListener {
    private static final int STYLE_STROKE = 0;
    private static final int STYLE_FILL = 1;
    /**
     * 屏幕宽度
     */
    private int ScreenWidth = 720;
    // 半径
    private float radius = 4;
    // 圆心之间的间距
    private float circleSeparation = 2 * radius + radius;
    // 选中的圆点在半径基础上多加一点
    private float activeRadius = 0.5f;
    private int fadeOutTime = 0;
    private final Paint mPaintInactive = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintActive = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ViewFlow viewFlow;
    private int currentScroll = 0;
    private int flowWidth = 0;
    private FadeTimer timer;
    public AnimationListener animationListener = this;
    private Animation animation;
    @SuppressWarnings("unused")
    private boolean mCentered = false;
    private int gravity;

    /**
     * Default constructor
     *
     * @param context
     */
    public CircleFlowIndicator(Context context) {
        super(context);
        initColors(0xFFFFFFFF, 0xFFFFFFFF, STYLE_FILL, STYLE_STROKE);
        ScreenWidth = PhoneUtil.getScreenWidth(context);
    }

    /**
     * The contructor used with an inflater
     *
     * @param context
     * @param attrs
     */
    @SuppressLint("Recycle")
    public CircleFlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        ScreenWidth = PhoneUtil.getScreenWidth(context);
        // Retrieve styles attributs
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CircleFlowIndicator);

        // Gets the inactive circle type, defaulting to "fill"
        int activeType = a.getInt(R.styleable.CircleFlowIndicator_activeType,
                STYLE_FILL);

        int activeDefaultColor = 0xFFFFFFFF;

        // Get a custom inactive color if there is one
        int activeColor = a
                .getColor(R.styleable.CircleFlowIndicator_activeColor,
                        activeDefaultColor);

        // Gets the inactive circle type, defaulting to "stroke"
        int inactiveType = a.getInt(
                R.styleable.CircleFlowIndicator_inactiveType, STYLE_STROKE);

        int inactiveDefaultColor = 0x44FFFFFF;
        // Get a custom inactive color if there is one
        int inactiveColor = a.getColor(
                R.styleable.CircleFlowIndicator_inactiveColor,
                inactiveDefaultColor);

        // Retrieve the radius
        radius = a.getDimension(R.styleable.CircleFlowIndicator_radius, 4.0f);

        circleSeparation = a.getDimension(
                R.styleable.CircleFlowIndicator_circleSeparation, 2 * radius
                        + radius);
        activeRadius = a.getDimension(
                R.styleable.CircleFlowIndicator_activeRadius, 0.5f);
        // Retrieve the fade out time
        fadeOutTime = a.getInt(R.styleable.CircleFlowIndicator_fadeOut, 0);

        mCentered = a.getBoolean(R.styleable.CircleFlowIndicator_centered,
                false);

        initColors(activeColor, inactiveColor, activeType, inactiveType);
    }

    private void initColors(int activeColor, int inactiveColor, int activeType,
                            int inactiveType) {
        // Select the paint type given the type attr
        switch (inactiveType) {
            case STYLE_FILL:
                mPaintInactive.setStyle(Style.FILL);
                break;
            default:
                mPaintInactive.setStyle(Style.STROKE);
        }
        mPaintInactive.setColor(inactiveColor);

        // Select the paint type given the type attr
        switch (activeType) {
            case STYLE_STROKE:
                mPaintActive.setStyle(Style.STROKE);
                break;
            default:
                mPaintActive.setStyle(Style.FILL);
        }
        mPaintActive.setColor(activeColor);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = 5;
        if (viewFlow != null) {
            count = viewFlow.getViewsCount();
        }
        // this is the amount the first circle should be offset to make the
        // entire thing centered
        float centeringOffset = 0;

        int leftPadding = 0;
        if (count >= 1) {
            if (gravity == Gravity.RIGHT) {
                leftPadding = (int) (ScreenWidth - (2 * radius/*左右两个半径*/ + radius/*防止贴近右边，多增加一个半径*/ + (count - 1) * circleSeparation/*圆心距*/));
            } else if (gravity == Gravity.LEFT) {
                leftPadding = (int) radius;
            } else {
                leftPadding = (int) (ScreenWidth - (2 * radius + (count - 1) * circleSeparation)) / 2;
            }
        } else {
            leftPadding = getPaddingLeft();
        }
        // Draw stroked circles
        for (int iLoop = 0; iLoop < count; iLoop++) {
            canvas.drawCircle(leftPadding + radius + (iLoop * circleSeparation)
                            + centeringOffset, getPaddingTop() + radius, radius,
                    mPaintInactive);
        }
        float cx = 0;
        if (flowWidth != 0) {
            // Draw the filled circle according to the current scroll
            cx = (currentScroll * circleSeparation) / flowWidth;
        }
        // The flow width has been upadated yet. Draw the default position
        canvas.drawCircle(leftPadding + radius + cx + centeringOffset,
                getPaddingTop() + radius, radius + activeRadius, mPaintActive);
        Log.i("onDraw", "onDraw currentScroll:" + currentScroll);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.taptwo.android.widget.ViewFlow.ViewSwitchListener#onSwitched(android
     * .view.View, int)
     */
    @Override
    public void onSwitched(View view, int position) {
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.taptwo.android.widget.FlowIndicator#setViewFlow(org.taptwo.android
     * .widget.ViewFlow)
     */
    @Override
    public void setViewFlow(ViewFlow view) {
        resetTimer();
        viewFlow = view;
        flowWidth = viewFlow.getWidth();
        invalidate();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.taptwo.android.widget.FlowIndicator#onScrolled(int, int, int,
     * int)
     */
    @Override
    public void onScrolled(int h, int v, int oldh, int oldv) {

        flowWidth = viewFlow.getWidth();
        int tempcurrentScroll;
        if (viewFlow.getViewsCount() * flowWidth != 0) {
            tempcurrentScroll = h % (viewFlow.getViewsCount() * flowWidth);
        } else {
            tempcurrentScroll = h;
        }
        if (tempcurrentScroll > 0) {
            if ((tempcurrentScroll % flowWidth) < 20) {
                currentScroll = tempcurrentScroll;
                setVisibility(View.VISIBLE);
                resetTimer();
                invalidate();
                Log.i("onScrolled", "currentScroll:" + currentScroll);
            }
        } else {
            currentScroll = tempcurrentScroll;
            setVisibility(View.VISIBLE);
            resetTimer();
            invalidate();
            Log.i("onScrolled", "currentScroll:" + currentScroll);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // We were told how big to be
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // Calculate the width according the views count
        else {
            int count = 5;
            if (viewFlow != null) {
                count = viewFlow.getViewsCount();
            }
            float temp = circleSeparation - 2 * radius;
            result = (int) (getPaddingLeft() + getPaddingRight()
                    + (count * 2 * radius) + (count - 1) * temp + 1);
            if (ScreenWidth != 0) {
                result = ScreenWidth;
            }
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // We were told how big to be
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // Measure the height
        else {
            result = (int) (2 * radius + getPaddingTop() + getPaddingBottom() + 1);
            // Respect AT_MOST value if that was what is called for by
            // measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Sets the fill color
     *
     * @param color ARGB value for the text
     */
    public void setFillColor(int color) {
        mPaintActive.setColor(color);
        invalidate();
    }

    /**
     * Sets the stroke color
     *
     * @param color ARGB value for the text
     */
    public void setStrokeColor(int color) {
        mPaintInactive.setColor(color);
        invalidate();
    }

    /**
     * 设置圆点显示位置，默认中间，Gravity.LEFT居左,Gravity.RIGHT居右
     *
     * @param gravity
     */
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    /**
     * Resets the fade out timer to 0. Creating a new one if needed
     */
    private void resetTimer() {
        // Only set the timer if we have a timeout of at least 1 millisecond
        if (fadeOutTime > 0) {
            // Check if we need to create a new timer
            if (timer == null || timer._run == false) {
                // Create and start a new timer
                timer = new FadeTimer();
                timer.execute();
            } else {
                // Reset the current tiemr to 0
                timer.resetTimer();
            }
        }
    }

    /**
     * Counts from 0 to the fade out time and animates the view away when
     * reached
     */
    private class FadeTimer extends AsyncTask<Void, Void, Void> {
        // The current count
        private int timer = 0;
        // If we are inside the timing loop
        private boolean _run = true;

        public void resetTimer() {
            timer = 0;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            while (_run) {
                try {
                    // Wait for a millisecond
                    Thread.sleep(1);
                    // Increment the timer
                    timer++;

                    // Check if we've reached the fade out time
                    if (timer == fadeOutTime) {
                        // Stop running
                        _run = false;
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            animation = AnimationUtils.loadAnimation(getContext(),
                    android.R.anim.fade_out);
            animation.setAnimationListener(animationListener);
            startAnimation(animation);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }
}