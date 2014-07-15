package com.android.puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;

import com.android.puzzle.FastBitmapDrawable;


@SuppressLint("NewApi")
public class PuzzleView extends ImageViewTouch {

	private Bitmap mMask;
	private Shader mBmpShader;
	private Paint mPaint;
	private RectF mFrame = new RectF();
	private int mLeft;
	private int mTop;
	private float mScale;

	public PuzzleView(Context context) {
		super(context);
		mScale = PxDpTransformer.getDisplayDpi(context) / 160f;
		setMinScale(0.9f);
		
		if (android.os.Build.VERSION.SDK_INT >= 11) {
		    this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
	}

	public PuzzleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setMask(Bitmap mask) {
		if (mMask != null) {
			mMask.recycle();
			mMask = null;
		}
//		if(Integer.parseInt(VERSION.SDK) < 11){
			mask = mask.extractAlpha();
//		}
		mMask = mask;
	}
	
	public void setLeftAndTop(int left, int top) {
		mLeft = left;
		mTop = top ;
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		super.setImageBitmap(bitmap);
		
		if(bitmap != null){
			mPaint = new Paint();
			mBmpShader = createShader(bitmap);
			mPaint.setShader(mBmpShader);
		}
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		mBmpShader = createShader(((FastBitmapDrawable)drawable).getBitmap());
		mPaint = new Paint();
		mPaint.setShader(mBmpShader);
		super.setImageDrawable(drawable);
	}
	
	@Override
	public void setImageDrawable(Drawable drawable, Matrix initial_matrix, float min_zoom, float max_zoom) {
		mPaint = new Paint();
		mBmpShader = createShader(((FastBitmapDrawable)drawable).getBitmap());
		mPaint.setShader(mBmpShader);
		super.setImageDrawable(drawable, initial_matrix, min_zoom, max_zoom);
	}

	private Shader createShader(Bitmap paramBitmap) {
		if (paramBitmap == null)
			return null;
		return new BitmapShader(paramBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
	}

	@Override
	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
		int i = paramInt3 - paramInt1;
		int j = paramInt4 - paramInt2;
		mFrame.set(0.0F, 0.0F, i, j);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if ((mMask != null) && (mBmpShader != null)) {
			Matrix localMatrix = new Matrix(getImageViewMatrix());
			float f = mFrame.width() / mMask.getWidth();
			localMatrix.preScale(1.0F / f, 1.0F / f);
			mBmpShader.setLocalMatrix(localMatrix);
			canvas.scale(f, f);
			canvas.drawBitmap(mMask, 0.0F, 0.0F, mPaint);
			return;
		}
		super.onDraw(canvas);
	}
	
	public Bitmap getDispalyImage(int paramInt1, int paramInt2) {
		int i = getWidth();
		float f1 = paramInt1 / i;
		Bitmap localBitmap1 = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap1);
		Matrix localMatrix1 = new Matrix(getImageViewMatrix());
		localMatrix1.preScale(f1, f1);
		localCanvas.drawARGB(0, 0, 0, 0);
		Matrix localMatrix2 = new Matrix();
		if( mScale > 1.5)
			localMatrix2.postTranslate(getLeft() + mLeft, getTop() + mTop + 49 * mScale);
		this.mBmpShader.getLocalMatrix(localMatrix2);
		if (this.mMask != null) {
			float f2 = paramInt1 / this.mMask.getWidth();
			localMatrix1.preScale(1.0F / f2, 1.0F / f2);
			this.mBmpShader.setLocalMatrix(localMatrix1);
			localCanvas.scale(f2, f2);
			localCanvas.drawBitmap(this.mMask, 0.0F, 0.0F, this.mPaint);
		}
		Bitmap localBitmap2 = ((FastBitmapDrawable) getDrawable()).getBitmap();
		Paint localPaint = new Paint();
		localPaint.setFilterBitmap(true);
		localCanvas.drawBitmap(localBitmap2, localMatrix1, localPaint);
		return localBitmap1;
	}

}
