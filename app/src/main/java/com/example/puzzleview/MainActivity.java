package com.example.puzzleview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.android.puzzle.PuzzleView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		PuzzleView view1 = new PuzzleView(this);
		Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask);
		view1.setMask(mask);
		view1.SetSimple(false);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.src1);
		
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		float viewWidth = mask.getWidth();
		float viewHeight = mask.getHeight();
		if (w < viewWidth || h < viewHeight) {
			float widthScale = viewWidth / w;
			float heightScale = viewHeight / h;
			float scale = Math.max(widthScale, heightScale);
			bitmap = Bitmap.createScaledBitmap(bitmap, (int) (w * scale), (int) (h * scale), false);
		}
		
		view1.setImageBitmap(bitmap);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mask.getWidth(), mask.getHeight());
		layout.addView(view1, params);
	}
}
