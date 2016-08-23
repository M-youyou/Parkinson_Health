package cn.ac.ict.yxd.itug.detection;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import cn.ac.ict.yxd.itug.R;

/**
 * Created by Shaobo on 8/23/16.
 */

public class SensorDetectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensordetector);
        RecyclerView rv_sensor = (RecyclerView) findViewById(R.id.rv_sensorList);
        rv_sensor.setHasFixedSize(true);

        ArrayList<SensorItem> sensorArray = new ArrayList<>();
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_sensor.setLayoutManager(layoutManager);

        // Show only require sensors.
        ImmutableList<Integer> requiredSensor = RequiredSensor.getRequiredSensor();
        for (int sensorType: requiredSensor){
            sensorArray.add(new SensorItem(mSensorManager, sensorType));
        }

        // Show all available sensors.
        /*
        for (Sensor sensor: mSensorManager.getSensorList(Sensor.TYPE_ALL)){
            sensorArray.add(new SensorItem(mSensorManager, sensor.getType()));
        }
        */
        SensorAdapter sensorListAdapter = new SensorAdapter(sensorArray);
        rv_sensor.setAdapter(sensorListAdapter);
    }
}
