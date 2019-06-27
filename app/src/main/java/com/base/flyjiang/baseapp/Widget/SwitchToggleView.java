package com.base.flyjiang.baseapp.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SwitchToggleView extends View {

	private Bitmap mSwitchBackground;// 背景的图片
	private Bitmap mSwitchSlide;// 滑块的图片

	private Paint mPaint = new Paint();

	public SwitchToggleView(Context context) {
		this(context, null);
	}

	public SwitchToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置背景的资源
	 * 
	 * @param resId
	 */
	public void setSwitchBackground(int resId) {
		mSwitchBackground = BitmapFactory.decodeResource(getResources(), resId);

	}

	/**
	 * 
	 * @param resId
	 */
	public void setSwitchSlide(int resId) {
		mSwitchSlide = BitmapFactory.decodeResource(getResources(), resId);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (mSwitchBackground != null) {
			int width = mSwitchBackground.getWidth();
			int height = mSwitchBackground.getHeight();

			setMeasuredDimension(width, height);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// 绘制背景的显示
		if (mSwitchBackground != null) {

			int left = 0;
			int top = 0;
			canvas.drawBitmap(mSwitchBackground, left, top, mPaint);
		}

		// 绘制滑块
		if (mSwitchSlide != null) {
			canvas.drawBitmap(mSwitchSlide, 0, 0, mPaint);
		}

	}
}
