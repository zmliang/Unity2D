package com.goldplusgold.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.goldplusgold.widget.R;


public class HorizontalProgressBar extends ProgressBar {

	String text;
	Paint mPaint;
	private int progress;
	private double mPercent;
	private String mIncome;
	private float textSize;
	private float rightDistance;

	public HorizontalProgressBar(Context context) {
		super(context);
		System.out.println("1");
		initText();
	}

	public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		System.out.println("2");
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
		textSize = mTypedArray.getDimension(R.styleable.HorizontalProgressBar_size, 0);
		rightDistance = mTypedArray.getDimension(R.styleable.HorizontalProgressBar_rightDistance, 0);
		initText();
	}

	public HorizontalProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
		textSize = mTypedArray.getDimension(R.styleable.HorizontalProgressBar_size, 0);
		rightDistance = mTypedArray.getDimension(R.styleable.HorizontalProgressBar_rightDistance, 0);
		System.out.println("3");
	}

	public void setProgress(int progress, Context context) {
		// setText(progress);
		initText();
		super.setProgress(progress);

	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Rect rect = new Rect();
		mPaint.getTextBounds(this.mIncome, 0, this.mIncome.length(), rect);
		int x = (int) ((getWidth() * mPercent) - 2 * rect.centerX() - rightDistance);
		int y = (getHeight() / 2) - rect.centerY();
		canvas.drawText(this.mIncome, x, y, this.mPaint);

	}

	// 初始化，画笔
	private void initText() {
		this.mPaint = new Paint();
		this.mPaint.setColor(Color.WHITE);
		this.mPaint.setTextSize(textSize);
	}

	private void setText() {
		setText(this.getProgress());
	}

	// 设置文字内容
	private void setText(int progress) {
		int i = (progress * 100) / this.getMax();
		this.text = String.valueOf(i) + "%";
		this.progress = progress / 100;
		invalidate();
	}

	public void setValue(String income, double percent) {
		initText();
		mIncome = income;
		mPercent = percent;
		postInvalidate();
	}
}
