package cn.ac.ict.yxd.itug.detection;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import android.hardware.Sensor;

/**
 * Created by Shaobo on 8/23/16.
 */
public class RequiredSensor {
    public static final ImmutableList<String> sensorLabelList = ImmutableList.of(
            "加速度传感器",
            "磁力传感器",
            "方向传感器",
            "陀螺仪传感器",
            "光线感应传感器",
            "压力传感器",
            "温度传感器",
            "近距离传感器",
            "重力传感器",
            "线性加速度传感器",
            "旋转矢量传感器"
    );

    public static final ImmutableList<Integer> requiredSensor = ImmutableList.of(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_ROTATION_VECTOR
    );

    public RequiredSensor(){

    }

    public static String getSenorLabelFromType(int sensorType){
        String sensorLabel;
        if (sensorType <= sensorLabelList.size()) {
            sensorLabel = sensorLabelList.get(sensorType - 1);
        } else {
            sensorLabel = "其他传感器";
        }
        return sensorLabel;
    }

    public static ImmutableList<Integer> getRequiredSensor(){
        return requiredSensor;
    }

}
