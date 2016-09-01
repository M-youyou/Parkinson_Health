package cn.ac.ict.yxd.itug.count;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.ac.ict.yxd.itug.R;

/**
 * Created by zhongxi on 2016/8/30.
 */
public class StartActivity extends Activity {
    private Button startbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_start);
        startbtn = (Button)findViewById(R.id.startbtn);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,CountMainActivity.class));
                finish();
            }
        });



    }

}

