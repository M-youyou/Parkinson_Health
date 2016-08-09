package cn.ac.ict.yxd.itug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText et_patient;
    Spinner sp_location;
    RadioGroup rg_distance,rg_trail;
    String patientName;
    int locationNum;
    int trailNum = 1;
    int distanceNum = 3;
    Button bt_begin;
    ImageView iv_info,iv_history,iv_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
    }
    private void initWidget()
    {
        et_patient = (EditText)findViewById(R.id.et_patient);
        sp_location = (Spinner)findViewById(R.id.spinner);
        rg_distance = (RadioGroup)findViewById(R.id.rg_distance);
        rg_trail = (RadioGroup) findViewById(R.id.rg_trail);
        bt_begin = (Button)findViewById(R.id.bt_begin);
        bt_begin.setOnClickListener(new onBeginListener());
        rg_trail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                trailNum = Integer.parseInt(rb.getText().toString());
            }
        });
        rg_distance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)MainActivity.this.findViewById(radioButtonId);
                distanceNum = Integer.parseInt(rb.getText().toString());
            }
        });
        iv_info = (ImageView)findViewById(R.id.iv_info);
        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InfoActivity.class));
            }
        });
        iv_history = (ImageView)findViewById(R.id.iv_history);
        iv_setting = (ImageView)findViewById(R.id.iv_setting);
        iv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
    class onBeginListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            patientName = et_patient.getText().toString().trim();
            if(patientName==null||patientName.equals(""))
            {
                Toast.makeText(MainActivity.this,getResources().getString(R.string.patientnotnull),Toast.LENGTH_SHORT).show();
                et_patient.requestFocus();
            }else{
            locationNum = sp_location.getSelectedItemPosition();
            Intent intent = new Intent(getApplicationContext(),GoActivity.class);
            Record record = new Record(patientName,trailNum,distanceNum,locationNum);
            Bundle bundle = new Bundle();
            bundle.putSerializable("record",record);
            intent.putExtras(bundle);
            startActivity(intent);}
        }
    }
}
