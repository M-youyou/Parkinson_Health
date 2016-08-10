package cn.ac.ict.yxd.itug.stride;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import cn.ac.ict.yxd.itug.R;

public class GyroActivity extends Activity {
    TextView yaw, pitch, roll,
            rx,ry,rz,
            ax,ay,az,compass;
    SensorManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);
        yaw = (TextView)findViewById(R.id.tv_gyro_yaw);
        pitch = (TextView)findViewById(R.id.tv_gyro_pitch);
        roll = (TextView)findViewById(R.id.tv_gyro_roll);
        rx = (TextView)findViewById(R.id.tv_gyro_X);
        ry = (TextView)findViewById(R.id.tv_gyro_y);
        rz  =(TextView)findViewById(R.id.tv_gyro_z);
        ax = (TextView)findViewById(R.id.tv_gyro_Ax);
        ay = (TextView)findViewById(R.id.tv_gyro_Ay);
        az = (TextView)findViewById(R.id.tv_gyro_Az);
        compass = (TextView)findViewById(R.id.tv_gyro_compass);
        sm = (SensorManager)GyroActivity.this.getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(new SensorListener(),sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(new SensorListener(),sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(new SensorListener(),sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(new SensorListener(),sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_GAME);
    }
    class SensorListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            int type = event.sensor.getType();
            switch(type)
            {

                case Sensor.TYPE_GYROSCOPE:
                    rx.setText(event.values[0]+"");
                    ry.setText(event.values[1]+"");
                    rz.setText(event.values[2]+"");
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:

                    yaw.setText(event.values[0]+"");
                    pitch.setText(event.values[1]+"");
                    roll.setText(event.values[2]+"");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    compass.setText(event.values[0]+"");
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    ax.setText(event.values[0]+"");
                    ay.setText(event.values[1]+"");
                    az.setText(event.values[2]+"");
                    break;
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
