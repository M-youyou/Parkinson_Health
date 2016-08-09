package cn.ac.ict.yxd.itug;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.ac.ict.yxd.itug.R.id.tv_go;

public class GoActivity extends Activity {
    TextView goContent;
    Button btnGo;
    Vibrator vibrator;
    long[] pattern = {100, 400};
    boolean flag = false;
    boolean start = false;
    MediaPlayer mp;
    SensorManager sm;
    AccEventListener accEventListener;
    GyroEventListener gyroEventListener;
    List<Vector> accVectors;
    List<Vector> gyroVectors;
    AlertDialog.Builder builder;
    Record record;
    int currentTrail = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);
        Bundle bundle = getIntent().getExtras();
        record = (Record) bundle.get("record");
        goContent = (TextView) findViewById(tv_go);
        goContent.setText(getResources().getString(R.string.parparing));
        btnGo = (Button) findViewById(R.id.btn_go);
        btnGo.setOnClickListener(new onBtnClickListener());
        btnGo.setBackgroundColor(Color.BLUE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.cd);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                flag = true;
                btnGo.setText(getResources().getString(R.string.stop));
                btnGo.setBackgroundColor(Color.RED);
                btnGo.setVisibility(View.VISIBLE);
                vibrator.vibrate(pattern, -1);
                goContent.setText(getResources().getString(R.string.testing));
                start=true;

            }
        });
        sm = (SensorManager) GoActivity.this.getSystemService(Context.SENSOR_SERVICE);

        accEventListener = new AccEventListener();
        gyroEventListener = new GyroEventListener();
        flag = false;

    }

    private void register() {
        sm.registerListener(accEventListener,
                sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(gyroEventListener,
                sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_GAME);
    }

    private void stop() {
        start = false;
        sm.unregisterListener(accEventListener);
        sm.unregisterListener(gyroEventListener);
    }

    class AccEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
        if(start) {
            Vector vector = new Vector(event.values[0], event.values[1], event.values[2]);
            accVectors.add(vector);
        }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class GyroEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(start) {
                Vector gyroVector = new Vector(event.values[0], event.values[1], event.values[2]);
                gyroVectors.add(gyroVector);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class onBtnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!flag) {
                prepare(currentTrail);
            } else {
                stop();
                builder = new AlertDialog.Builder(GoActivity.this);
                builder.setTitle(getResources().getString(R.string.dialogTitle));
                builder.setMessage(getResources().getString(R.string.dialogContent));
                builder.setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        record.addAccData(accVectors);
                        record.addGyroData(gyroVectors);
                        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd-HH:mm");
                        Date curDate = new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        record.setDate(str);
                        currentTrail++;
                        if (currentTrail > record.getTrail()) {
                            RecordDB.add(getApplicationContext(), record);
                            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("record", record);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            GoActivity.this.finish();
                        } else {
                            prepare(currentTrail);
                        }
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.discard), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prepare(currentTrail);
                    }
                });
                builder.show();
                flag = false;
            }


        }
    }

    private void prepare(int currentTrail) {

        mp.start();
        goContent.setText(String.format("即将检测: 第 %d 个来回,共 %d 个来回", currentTrail, record.getTrail()));
        btnGo.setVisibility(View.GONE);
        accVectors = new ArrayList<>();
        gyroVectors = new ArrayList<>();
        register();
    }

    @Override
    protected void onPause() {
        if(mp!=null)
        {
            mp.stop();
            mp.release();
            mp=null;

        }
        stop();
        super.onPause();
    }
}
