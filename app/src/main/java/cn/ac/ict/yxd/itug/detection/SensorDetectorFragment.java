package cn.ac.ict.yxd.itug.detection;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;

import cn.ac.ict.yxd.itug.R;

/**
 * Created by Shaobo on 8/23/16.
 */

public class SensorDetectorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensordetector, container, false);
        RecyclerView rv_sensor = (RecyclerView) view.findViewById(R.id.rv_sensorList);
        rv_sensor.setHasFixedSize(true);

        ArrayList<SensorItem> sensorArray = new ArrayList<>();
        SensorManager mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        rv_sensor.addItemDecoration(new DividerItemDecoration(dividerDrawable));
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
        SensorAdapter sensorAdapter = new SensorAdapter(sensorArray);
        rv_sensor.setAdapter(sensorAdapter);

        return view;
    }
}
