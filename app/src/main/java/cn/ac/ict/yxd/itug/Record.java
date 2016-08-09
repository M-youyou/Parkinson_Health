package cn.ac.ict.yxd.itug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxd on 8/2/16.
 */

public class Record implements Serializable{
    String name;
    String date;
    int trail;
    int distance;
    int locationNum;
    List<List> accData;
    List<List> gryoData;
    float[] times;

    public Record(String name, int trail, int distance, int locationNum) {
        this.name = name;
        this.trail = trail;
        this.distance = distance;
        this.locationNum = locationNum;
        accData = new ArrayList<List>();
        gryoData = new ArrayList<List>();
        times = new float[trail];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTrail() {
        return trail;
    }

    public void setTrail(int trail) {
        this.trail = trail;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLocationNum() {
        return locationNum;
    }

    public void setLocationNum(int locationNum) {
        this.locationNum = locationNum;
    }

    public List<List> getAccData() {
        return accData;
    }

    public void addGyroData(List vectors)
    {
        gryoData.add(vectors);
    }

    public List<List> getGryoData() {
        return gryoData;
    }

    public void addAccData(List vectors)
    {
        accData.add(vectors);
    }
    public float[] getTimes() {
        if(getAccData().size()>=trail) {
            for (int i = 0; i < trail; i++) {
                List<Vector> vectors = getAccData().get(i);
                long start = vectors.get(0).time;
                long end = vectors.get(vectors.size()-1).time;
                times[i] = (float)(end-start)/1000f;
            }
        }
        return times;
    }
    public float getAvgTime()
    {
        float sum = 0;
        for (float f: getTimes()) {
            sum += f;
        }
        return sum/(float) trail;
    }
}
