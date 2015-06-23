package howgoyeah.shake;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;

import com.example.howgoyeah.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShakeActivity extends Activity{
	private SensorManager mSensorManager; // 體�??(Sensor)使用管�??
	private Sensor mSensor; // 體�??(Sensor)類別
	private float mLastX; // x軸�?��??(Sensor)??�移
	private float mLastY; // y軸�?��??(Sensor)??�移
	private float mLastZ; // z軸�?��??(Sensor)??�移
	private double mSpeed; // ?��??��?��?�數�?
	private long mLastUpdateTime; // 觸發??��??
	
	//private TextView show;
	private int counter = 0;
	public static int condition = 0;

	// ?��??��?��?�數度設定�?? (?��?��?�大???��??��?�大??��?�數?��?��?��?��?�甩??�即??�觸?��)
	private static final int SPEED_SHRESHOLD = 4000;

	// 觸發??��?��?��??
	private static final int UPTATE_INTERVAL_TIME = 70;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ShakeCanvas canvas = new ShakeCanvas(this);
		setContentView(canvas);
		//setContentView(R.layout.activity_shake);	

		// ??��?��?��??(Sensor)??��?�使?��權�??
		mSensorManager = (SensorManager) this
				.getSystemService(Context.SENSOR_SERVICE);

		// ??��?��?��?�Sensor????�設�?
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		// 註�?��?��??(Sensor)?��??�觸?��Listener
		mSensorManager.registerListener(SensorListener, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
		
		//show = (TextView) findViewById(R.id.show);
	}
	

	private SensorEventListener SensorListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent mSensorEvent) {
			// ?��??�觸?��??��??
			long mCurrentUpdateTime = System.currentTimeMillis();

			// 觸發??��?��?��?? = ?��??�觸?��??��?? - 上次觸發??��??
			long mTimeInterval = mCurrentUpdateTime - mLastUpdateTime;

			// ?��觸發??��?��?��??< 70 ??�return;
			if (mTimeInterval < UPTATE_INTERVAL_TIME)
				return;

			mLastUpdateTime = mCurrentUpdateTime;

			// ??��?�xyz體�??(Sensor)??�移
			float x = mSensorEvent.values[0];
			float y = mSensorEvent.values[1];
			float z = mSensorEvent.values[2];

			// ?��??��?�移?�度 = xyz體�??(Sensor)??�移 - 上次xyz體�??(Sensor)??�移
			float mDeltaX = x - mLastX;
			float mDeltaY = y - mLastY;
			float mDeltaZ = z - mLastZ;

			mLastX = x;
			mLastY = y;
			mLastZ = z;

			// 體�??(Sensor)?��??��?��?��?�度?���?
			mSpeed = Math.sqrt(mDeltaX * mDeltaX + mDeltaY * mDeltaY + mDeltaZ
					* mDeltaZ)
					/ mTimeInterval * 10000;

			// ?��體�??(Sensor)?��??��?�度大於等於?��??�設定�?��?��?�入 (??�到?��??��?��?��?��?�度)
			if (mSpeed >= SPEED_SHRESHOLD) {
				// ??�到??��???�甩??��?��?��?��?��?��??
				counter++;
				//Log.e(String.valueOf(counter), "shake");
				//show.setText(String.valueOf(counter));
				condition = counter;
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
	@Override
	protected void onDestroy() 
	{
	        super.onDestroy();
	        //?��程�?��?��?��?�移?��體�??(Sensor)觸發
	        mSensorManager.unregisterListener(SensorListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
