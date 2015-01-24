package com.meguko.didanda;

import com.meguko.didanda.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class StartActivity extends Activity {
	Button startButton;
	ViewFlipper flipper;
	Animation inFromRightAnimation;
	Animation inFromLeftAnimation;
	Animation outToRightAnimation;
	Animation outToLeftAnimation;
	Animation didandajumpAnimation;
	ImageView didanda;
	TextView highScore;

	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		startButton = (Button) findViewById(R.id.startButton);
		highScore = (TextView) findViewById(R.id.highscore);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

		inFromRightAnimation = AnimationUtils.loadAnimation(this,
				R.anim.right_in);
		inFromLeftAnimation = AnimationUtils
				.loadAnimation(this, R.anim.left_in);
		outToRightAnimation = AnimationUtils.loadAnimation(this,
				R.anim.right_out);
		outToLeftAnimation = AnimationUtils
				.loadAnimation(this, R.anim.left_out);
		didanda = (ImageView) findViewById(R.id.chara);
		didandajumpAnimation = AnimationUtils.loadAnimation(this,
				R.anim.didanda_jump);
		didanda.startAnimation(didandajumpAnimation);
		preferences = getSharedPreferences("pref", MODE_PRIVATE);
		highScore.setText(""
				+ preferences.getInt("" + flipper.getDisplayedChild(), 0));
	}

	public void touchDidanda(View v) {
		// didandajumpAnimation.setRepeatMode(Animation.REVERSE);
		// didandajumpAnimation.setRepeatCount(Animation.INFINITE);
		// didanda.startAnimation(didandajumpAnimation);
	}

	public void fromStart(View v) {
		Intent intent = new Intent(StartActivity.this, MainActivity.class);
		intent.putExtra("なんいど", flipper.getDisplayedChild());
		startActivity(intent);
		Log.d("����", "" + flipper.getDisplayedChild());
		highScore.setText(""
				+ preferences.getInt("" + flipper.getDisplayedChild(), 0));

	}

	public void left(View v) {
		flipper.setInAnimation(inFromRightAnimation);
		flipper.setOutAnimation(outToLeftAnimation);
		flipper.showNext();
		highScore.setText(""
				+ preferences.getInt("" + flipper.getDisplayedChild(), 0));

	}

	public void right(View v) {
		flipper.setInAnimation(inFromLeftAnimation);
		flipper.setOutAnimation(outToRightAnimation);
		flipper.showPrevious();
		// 0�����񂽂�A1���ނ�������

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
