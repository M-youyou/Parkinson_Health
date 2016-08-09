package cn.ac.ict.yxd.itug;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DrawActivity extends Activity {
    Record record;
    TextView tv_draw_title,tv_draw_acc,tv_draw_gyro;
    //SurfaceView sv_acc,sv_gyro;
    LineChart chart_acc,chart_gyro;
    RadioGroup rg_trail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Bundle bundle = getIntent().getExtras();
        record = (Record)bundle.get("record");
        initWidget();

      //  mydraw(record.getGyroVectors(),chart_acc);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void initWidget(){
        rg_trail = (RadioGroup)findViewById(R.id.rg_draw_trail);
        int[] rbIds  =new int[record.getTrail()];
        for(int i=0;i<record.getTrail();i++)
        {
            RadioButton rb = new RadioButton(this);
            rb.setText((i+1)+"");
            rg_trail.addView(rb,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rbIds[i] = rb.getId();
        }
        rg_trail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tempButton = (RadioButton)findViewById(checkedId);
                setdata(record.getAccData(),Integer.parseInt(tempButton.getText().toString()),chart_acc);
                setdata(record.getGryoData(),Integer.parseInt(tempButton.getText().toString()),chart_gyro);
                chart_acc.animateX(2000);
                Legend l = chart_acc.getLegend();
                l.setForm(Legend.LegendForm.LINE);
                l.setTextSize(11f);
                l.setTextColor(Color.BLACK);
                l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
                l.setDrawInside(true);
                chart_acc.setDescription("");

                chart_gyro.animateX(2000);
                Legend l2 = chart_gyro.getLegend();
                l2.setForm(Legend.LegendForm.LINE);
                l2.setTextSize(11f);
                l2.setTextColor(Color.BLACK);
                l2.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
                l2.setDrawInside(true);
                chart_gyro.setDescription("");

            }
        });
        chart_acc = (LineChart)findViewById(R.id.chart_acc);
        chart_acc.setBackgroundColor(Color.WHITE);

        chart_gyro = (LineChart)findViewById(R.id.chart_gyro);
        chart_gyro.setBackgroundColor(Color.WHITE);

        tv_draw_acc = (TextView)findViewById(R.id.tv_draw_acc);
        tv_draw_gyro = (TextView)findViewById(R.id.tv_draw_gyro);
        tv_draw_title = (TextView)findViewById(R.id.tv_draw_title);
        String patientName = record.getName();
        tv_draw_title.setText(patientName+"  "+record.getDate());
        String accStr = getResources().getString(R.string.Acc);
        tv_draw_acc.setText(accStr);
        tv_draw_gyro.setText(getResources().getString(R.string.Gyro));



        rg_trail.check(rbIds[0]);
    }
    private void setdata(List<List> lists,int currentTrail,LineChart chart)
    {
        List<Vector> vectors = lists.get(currentTrail-1);
        ArrayList<Entry> yValsX = new ArrayList<Entry>();
        ArrayList<Entry> yValsY = new ArrayList<Entry>();
        ArrayList<Entry> yValsZ = new ArrayList<Entry>();
        int num = vectors.size();
        for(int i=0;i<num;i++)
        {
            Vector v = vectors.get(i);
            yValsX.add(new Entry(i, v.x));
            yValsY.add(new Entry(i, v.y));
            yValsZ.add(new Entry(i, v.z));
        }
        LineDataSet setX,setY,setZ;
        setX = new LineDataSet(yValsX,"X");
        setX.setAxisDependency(YAxis.AxisDependency.LEFT);
        setX.setColor(Color.RED);
        setX.setLineWidth(2f);
        setX.setHighLightColor(Color.rgb(244, 117, 117));
        setX.setDrawCircles(false);
        setX.setDrawValues(false);

        setY = new LineDataSet(yValsY,"Y");
        setY.setAxisDependency(YAxis.AxisDependency.LEFT);
        setY.setColor(Color.BLUE);
        setY.setLineWidth(2f);
        setY.setHighLightColor(Color.rgb(244, 117, 117));
        setY.setDrawCircles(false);
        setY.setDrawValues(false);

        setZ = new LineDataSet(yValsZ,"Z");
        setZ.setAxisDependency(YAxis.AxisDependency.LEFT);
        setZ.setColor(Color.GREEN);
        setZ.setLineWidth(2f);
        setZ.setHighLightColor(Color.rgb(244, 117, 117));
        setZ.setDrawCircles(false);
        setZ.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(setX); // add the datasets
        dataSets.add(setY);
        dataSets.add(setZ);

        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);
        data.setDrawValues(false);

        // set data
        chart.setData(data);

    }
}
