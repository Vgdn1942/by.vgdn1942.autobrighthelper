package by.vgdn1942.autobrighthelper;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class BackgroundService extends Service {

    private static final boolean DBG = true;
    private static final String TAG = "autobrightnesshelper_service";

    private String txt;
    private SensorManager sensorManager;
    private Sensor pressureSensor;

    AutoRun receiver = new AutoRun();
    IntentFilter filter_boot = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
    IntentFilter filter_screen = new IntentFilter(Intent.ACTION_SCREEN_ON);
    IntentFilter filter_user = new IntentFilter(Intent.ACTION_USER_PRESENT);

    final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            txt = String.format("%.3f mbar", values[0]);
            if (DBG) Log.i(TAG, " mBar:" + txt);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    public IBinder onBind(Intent intent) { return null; }

    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(receiver, filter_boot);
        registerReceiver(receiver, filter_screen);
        registerReceiver(receiver, filter_user);
        if (DBG) Log.i(TAG, " BackgroundService - onStartCommand");
        someTask();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DBG) Log.i(TAG, " BackgroundService - onDestroy");
        super.onDestroy();
        unregisterReceiver(receiver);
        sensorManager.unregisterListener(sensorEventListener);
        stopSelf();
    }

    private void someTask() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(sensorEventListener, pressureSensor, SensorManager.SENSOR_DELAY_UI);
        SystemClock.sleep(2000);
        stopSelf();
    }
}
