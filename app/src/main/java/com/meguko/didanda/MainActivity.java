package com.meguko.didanda;

import java.lang.reflect.Method;
import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ImageView didandaView;
	int point, five_roop, upP;

	View gameOverView;
	TextView scoreText, timeText, tapText;
	long before, now;
	long timeDifference[] = new long[5];
	String lasTap = "左";
	float five_score;
	Timer timer = null;
	Timer countTimer = null;
	Handler handle = new Handler();
	boolean tap = false;
	boolean UPUP = false;
	LinearLayout backGround;
	ScrollView scrollView;
	int difficuly;

	int updown;
	int maxHeight, scrollHeight, oneupY, nowHeight;// �X�N���[������Ƃ�

	Float x = (float) 1;// ��Փx�����߂�;
	float u = (float) 1;

	boolean gameover = false;

	float time = 0;

	MediaPlayer mp = null;
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		preferences = getSharedPreferences("pref", MODE_PRIVATE);
		mp = MediaPlayer.create(this, R.raw.sen_ge_panchi04);

		//
		didandaView = (ImageView) findViewById(R.id.didandaView);
		scoreText = (TextView) findViewById(R.id.scoreText);
		timeText = (TextView) findViewById(R.id.timetext);

		gameOverView = findViewById(R.id.gameOverView);
		tapText = (TextView) findViewById(R.id.taptext);//

		gameOverView.setVisibility(View.INVISIBLE);

		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(false);
		Intent intent = getIntent();
		
		difficuly = intent.getIntExtra("なんいど", 0);
		if (difficuly == 0) {
			x = (float) 1.2;
		} else if (difficuly == 1) {
			x = (float) 1;
		} else {
			x = (float) 0.9;
		}
		backGround = (LinearLayout) findViewById(R.id.backGround);
		// ImageView backSky = new ImageView(this);
		// backSky.setImageResource(drawable.sky);
		// backSky.setScaleType(ScaleType.FIT_XY);
		// backGround.addView(backSky, new LinearLayout.LayoutParams(
		// ViewGroup.LayoutParams.MATCH_PARENT,
		// ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky1 = new ImageView(this);
		sky1.setImageResource(R.drawable.sky1);
		sky1.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky1, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky2 = new ImageView(this);
		sky2.setImageResource(R.drawable.sky2);
		sky2.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky2, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky3 = new ImageView(this);
		sky3.setImageResource(R.drawable.sky3);
		sky3.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky3, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky4 = new ImageView(this);
		sky4.setImageResource(R.drawable.sky4);
		sky4.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky4, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		Display display = getWindowManager().getDefaultDisplay();// �f�B�X�v���C�̃T�C�Y���Ƃ�
		Point size = new Point();
		overrideGetSize(display, size);
		int width = size.x;
		int height = size.y;
		// backSky.setY(-height);
		Log.d("������", "" + height * 3);
		scrollView.post(new Runnable() {
			@Override
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
				scrollHeight = scrollView.getChildAt(0).getHeight()
						- scrollView.getHeight();
				Log.d("����̂��񂽂��̂�����", "" + scrollHeight);
				maxHeight = 8000;
				oneupY = maxHeight / scrollHeight;
			}
		});

	}

	void overrideGetSize(Display display, Point outSize) {
		try {
			// test for new method to trigger exception
			Class pointClass = Class.forName("android.graphics.Point");
			Method newGetSize = Display.class.getMethod("getSize",
					new Class[] { pointClass });

			// no exception, so new method is available, just use it
			newGetSize.invoke(display, outSize);
		} catch (Exception ex) {
			// new method is not available, use the old ones
			outSize.x = display.getWidth();
			outSize.y = display.getHeight();
		}
	}

	public void rightButton(View v) {
		if (!gameover) {
			if (point == 0) {
				countUp();
			} else if (lasTap == "左") {
				countUp();
			}
			didandaView.setImageResource(R.drawable.right_didanda);
			lasTap = "右";

		}
	}

	public void leftButton(View v) {
		if (!gameover) {
			if (point == 0) {
				countUp();
			} else if (lasTap == "右") {
				countUp();
			}
			didandaView.setImageResource(R.drawable.left_didanda);
			lasTap = "左";

		}
	}

	public void countUp() {

		if (!gameover) {
			// if(mp!=null){
			// mp=null;
			// mp = MediaPlayer.create(this, R.raw.sen_ge_panchi04);
			//
			// mp.start();
			// }

			if (upP > 7 && upP < 32) {
				didandaView.setTranslationY(-2 * (upP - 6));
				nowHeight = nowHeight + (oneupY * updown);
			} else if (upP > 31) {
				scrollView.setScrollY(scrollView.getScrollY()
						- (oneupY * updown));
				nowHeight = nowHeight + (oneupY * updown);

			}

			if (timer == null) {
				timer = new Timer();
				// �^�C�}�[�̏�����
				timer.schedule(new MytimerTask(), 10, 10);
				// timer.schedule(task, when)
			}

			if (countTimer != null) {
				countTimer.cancel();
			}
			countTimer = new Timer();
			countTimer.schedule(new MyTimer(), 1000);
			if (nowHeight < 3201) {
				scoreText.setText("" + nowHeight + "mm");
			}
			now = System.currentTimeMillis();
			if (point > 0) {// 2��ڈȍ~�̃^�b�v
				timeDifference[five_roop] = now - before;
			} else { // ���������Ă݂�
			}
			before = now;

			five_score = 0;
			if (nowHeight >= 400) {
				u = (float) 1.2;
			} else if (nowHeight >= 800) {
				u = (float) 1.25;
			} else if (nowHeight >= 1200) {
				u = (float) 1.3;
			} else if (nowHeight >= 1600) {
				u = (float) 1.4;
			}
			for (int i = 0; i < 5; i++) {
				five_score = five_score + timeDifference[i];
			}
			five_score = five_score / x / u;
			Log.d("���v", "" + five_score + "  " + updown);//
			if (five_score < 300) {
				upP++;
				updown = 3;
			} else if (five_score < 400) {
				upP++;
				UPUP = true;
				updown = 2;
			} else if (five_score < 600) {
				upP++;
				UPUP = true;//
				updown = 1;
			} else if (five_score < 700) {
				UPUP = false;
				updown = 0;
			} else {

				updown = -5;
			}
			if (five_score > 1000) {// ���̐��͕ϐ��ɂ���B�������l
				if (upP < 8) {

				} else {
					gameOver();
				}
				UPUP = false;
			}

			point++;
			five_roop++;

			if (five_roop >= 5) {
				five_roop = 0;
			}
		}
	}

	class MyTimer extends TimerTask {

		@Override
		public void run() {
			handle.post(new Runnable() {
				@Override
				public void run() {
					// �ɂт傤�ɂȂ�����[
					gameOver();
				}
			});
		}
	}

	class MytimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			handle.post(new Runnable() {
				@Override
				public void run() {
					timeText.setText("" + time / 100);
					time++;
				}
			});
		}

	}

	class GameOverTask extends TimerTask {
		@Override
		public void run() {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			handle.post(new Runnable() {

				@Override
				public void run() {
					// TODO �����������ꂽ���\�b�h�E�X�^�u
					if (tap) {
						tapText.setVisibility(View.INVISIBLE);
						tap = false;
					} else {
						tapText.setVisibility(View.VISIBLE);
						tap = true;
					}
				}
			});
		}
	}

	public void gameOver() {
		// ���[�ނ��[�΁[�ɂȂ������̏����B??
		Log.d("���ނ���", "���ނ���");

		gameOverView.setVisibility(View.VISIBLE);
		tap = true;
		gameover = true;
		int high=preferences.getInt(difficuly+"", 0);
		if(nowHeight>=high){
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putInt(difficuly+"", nowHeight);
		  editor.commit();
		}
		
		if (timer != null) {
			timer.cancel();
		}
		timer = null;
		if (countTimer != null) {
			countTimer.cancel();
		}
		countTimer = null;

		timer = new Timer();
		timer.schedule(new GameOverTask(), 0, 500);
		if (upP < 7) {

		} else if (upP < 31) {
			// /���[�ނ��[�΁[�̎��̂�����view�ɂ���B
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0,
					2 * (upP - 6));// 1000
			trans.setDuration(upP - 6);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);
			TranslateAnimation trans2 = new TranslateAnimation(0, 0, 0, 0);// 1000
			trans2.setStartOffset(upP - 6);
			trans2.setDuration(upP - 6 + (upP - 6) / 2);// 500
			trans2.setFillAfter(true);
			didandaView.startAnimation(trans2);
		} else if (upP < 400) {
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0,
					50 + (upP - 31) * 5);// 1000
			trans.setDuration(upP - 6);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);

		} else if (upP < 500) {
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0,
					2 * (upP - 6));// 1000
			trans.setDuration(upP - 6);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);
		} else {
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0, 1000);// 1000
			trans.setDuration(500);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);
		}

	}

	public void fromGameover(View v) {
		if (gameover) {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
