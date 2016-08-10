package cn.ac.ict.yxd.itug.stride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ac.ict.yxd.itug.R;

public class DetailActivity extends Activity {
    ImageView iv_chart;
    TextView tv_patient,tv_date,tv_time,tv_numtr,tv_dis;
    Record record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        record = (Record)getIntent().getExtras().get("record");
        initWidget();
    }
    private void initWidget()
    {
        iv_chart = (ImageView)findViewById(R.id.iv_detail_chart);
        iv_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,DrawActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record",record);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tv_patient = (TextView)findViewById(R.id.tv_detail_patient);
        tv_patient.setText(record.getName());
        tv_date = (TextView)findViewById(R.id.tv_detail_date);
        tv_date.setText(record.getDate());
        tv_time = (TextView)findViewById(R.id.tv_detail_time);
        tv_time.setText(String.format("%.2f 秒", record.getAvgTime()));
        tv_numtr = (TextView)findViewById(R.id.tv_detail_numtr);
        tv_numtr.setText(record.getTrail()+"");
        tv_dis = (TextView)findViewById(R.id.tv_detail_dis);
        tv_dis.setText(record.getDistance()+"");

    }
}
