package howgoyeah.slide;

import com.example.howgoyeah.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SlideActivity extends Activity {

	ScrollView scrollView;
	LinearLayout linearLayout;
	TextView textTime, textDis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide);
		getActionBar().hide();
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		init();
		textDis = (TextView) findViewById(R.id.textDis);
		textTime = (TextView) findViewById(R.id.textTime);
		new CountDownTimer(30000, 1000) {

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				textTime.setText("Done!");
				finish();
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				textTime.setText("�ɶ��˼�: " + millisUntilFinished / 1000);
			}

		}.start();
	}

	public void init() {
		for (int i = 0; i <= 2000; i++) {
			TextView text = new TextView(this);
			text.setText(String.valueOf(i * 5 + "M"));
			text.setMinHeight(500);
			text.setTextColor(Color.WHITE);
			linearLayout.addView(text);
		}
		scrollView.setOnTouchListener(scroll);
	}

	public OnTouchListener scroll = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			int x = scrollView.getScrollY();
			x = x / 500;
			textDis.setText("�Ʀ�Z��: " + x);
			return false;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slide, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			int x = scrollView.getScrollY();
			Toast.makeText(SlideActivity.this, String.valueOf(x),
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
