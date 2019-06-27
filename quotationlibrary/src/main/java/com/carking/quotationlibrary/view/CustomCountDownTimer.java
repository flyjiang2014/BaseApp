package com.carking.quotationlibrary.view;

import android.os.CountDownTimer;

/**
 * 倒计时功能
 */
public class CustomCountDownTimer extends CountDownTimer {
	private OnCountDownTimerListener onCountDownTimerListener;

	public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	@Override
	public void onFinish() {
		onCountDownTimerListener.OnCountDownTimerFinish();
	}

	@Override
	public void onTick(long millisUntilFinished) {
		onCountDownTimerListener.OnCountDownTimer();
	}

	/*
	 * 给倒计时定义一个接口
	 */
	public interface OnCountDownTimerListener {
		public void OnCountDownTimerFinish();

		public void OnCountDownTimer();
	}

	public void setOnCountDownTimerListener(OnCountDownTimerListener l) {
		onCountDownTimerListener = l;
	}

	/*private void doTimeDownAction(final String head, String time) {

		downTime = Integer.parseInt(time);

		if (countDownTimer == null) {
			countDownTimer = new ZCXCountDownTimer(downTime, 1000);
			countDownTimer.setOnCountDownTimerListener(new ZCXCountDownTimer.OnCountDownTimerListener() {
				@Override
				public void OnCountDownTimer() {
					downTime -= 1000;
					String[] strs = TimeUtil.msToFormat(downTime);
					tvwaittime.setText(head + strs[2] + ":" + strs[3]);
				}

				@Override
				public void OnCountDownTimerFinish() {
					countDownTimer = null;
					isTimeCounting = false;
					ensureUi();
				}

			});

			if (!isTimeCounting) {
				countDownTimer.start();
				isTimeCounting = true;
			}
		}

	}*/

}
