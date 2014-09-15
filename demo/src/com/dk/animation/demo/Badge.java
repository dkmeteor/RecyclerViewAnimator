package com.dk.animation.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Badge extends RelativeLayout {
	private Paint mPaint = new Paint();
	private TextView mTextView;

	public Badge(Context context) {
		super(context);
		init();
	}

	public Badge(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setText(String str) {
		mTextView.setText(str);
		postInvalidate();
	}

	public void setTextSize(int size) {
		mTextView.setTextSize(size);
	}

	@SuppressLint("NewApi")
	private void init() {
		mPaint.setAntiAlias(true);
		if (VERSION.SDK_INT > 11) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}

		mTextView = new TextView(getContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		mTextView.setTextSize(11);
		mTextView.setTextColor(0xffffffff);
		mTextView.setLayoutParams(params);
		addView(mTextView);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (mTextView != null && mTextView.getText().length() > 0) {
			mPaint.setColor(0xffff0000);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2,
					mPaint);
		}
		super.dispatchDraw(canvas);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
