package com.hhh.mvvm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.AnyThread;

import com.hhh.mvvm.R;

/**
 * 环形进度条
 */
public class RoundProgressView extends View {
  private static final int STROKE = 0; // 样式：空心
  private static final int FILL = 1; // 样式：实心

  private Paint mPaint; // 画笔对象的引用
  private RectF mOval; // 圆弧的形状和大小的界限

  private int mRoundColor; // 圆环的颜色
  private float mRoundWidth; // 圆环的宽度

  private int mProgressColor; // 圆环进度的颜色
  private float mProgressWidth; // 圆环进度的宽度

  private int mStyle; // 进度的风格，实心或者空心
  private int mMaxValue; // 最大进度
  private int mProgress; // 当前进度
  private int mStartAngle; // 进度条起始角度

  public RoundProgressView(Context context) {
    this(context, null);
  }

  public RoundProgressView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RoundProgressView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mPaint = new Paint();

    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressView);

    mRoundColor = array.getColor(R.styleable.RoundProgressView_roundColor, Color.TRANSPARENT);
    mRoundWidth = array.getDimension(R.styleable.RoundProgressView_roundWidth,
      (int) (2.5 * getResources().getDisplayMetrics().density + 0.5f));
    mProgressColor = array.getColor(R.styleable.RoundProgressView_progressColor, 0xFFE77168);
    mProgressWidth = array.getDimension(R.styleable.RoundProgressView_progressWidth, mRoundWidth);
    mMaxValue = array.getInteger(R.styleable.RoundProgressView_maxValue, 100);
    mStyle = array.getInt(R.styleable.RoundProgressView_style, STROKE);
    mStartAngle = array.getInt(R.styleable.RoundProgressView_startAngle, -90);

    array.recycle();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int centerX = getWidth() / 2; // 获取圆心的x坐标
    int radius = (int) (centerX - mRoundWidth / 2); // 圆环的半径

    // step1 画最外层的大圆环
    mPaint.setStrokeWidth(mRoundWidth); // 设置圆环的宽度
    mPaint.setColor(mRoundColor); // 设置圆环的颜色
    mPaint.setAntiAlias(true); // 消除锯齿
    // 设置画笔样式
    switch (mStyle) {
      case STROKE:
        mPaint.setStyle(Paint.Style.STROKE);
        break;
      case FILL:
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        break;
    }
    canvas.drawCircle(centerX, centerX, radius, mPaint); // 画出圆环

    // step2 画圆弧-画圆环的进度
    mPaint.setStrokeWidth(mProgressWidth); // 设置画笔的宽度使用进度条的宽度
    mPaint.setColor(mProgressColor); // 设置进度的颜色
    // 用于定义的圆弧的形状和大小的界限
    if (mOval == null) {
      mOval = new RectF(centerX - radius, centerX - radius, centerX + radius, centerX + radius);
    }
    int sweepAngle = 360 * mProgress / mMaxValue; // 计算进度值在圆环所占的角度
    // 根据进度画圆弧
    switch (mStyle) {
      case STROKE:
        // 空心
        canvas.drawArc(mOval, mStartAngle, sweepAngle, false, mPaint);
        break;
      case FILL:
        // 实心
        canvas.drawArc(mOval, mStartAngle, sweepAngle, true, mPaint);
        break;
    }
  }

  public int getProgress() {
    return mProgress;
  }

  @AnyThread
  public void setProgress(int progress) {
    if (progress < 0) {
      mProgress = 0;
    } else if (progress > mMaxValue) {
      progress = mMaxValue;
    }
    mProgress = progress;
    postInvalidate();
  }
}
