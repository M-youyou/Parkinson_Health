package cn.ac.ict.yxd.itug.detection;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Shaobo on 8/23/16.
 */
public class SensorItem {
    private String sensorName, sensorLabel;
    private boolean isOK;
    public SensorItem(SensorManager sensorManager, int sensorType){
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        isOK = !(sensor == null);
        sensorName = (sensor == null)?"不支持":sensor.getName();
        sensorLabel = RequiredSensor.getSenorLabelFromType(sensorType);
    }

    public boolean getSensorState() {
        return isOK;
    }

    public String getSensorName(){
        return sensorName;
    }

    public String getSensorLabel(){
        return sensorLabel;
    }

}
