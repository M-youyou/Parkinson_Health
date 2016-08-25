package cn.ac.ict.yxd.itug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.ac.ict.yxd.itug.detection.MainDetectorActivity;
import cn.ac.ict.yxd.itug.face.FFmpegRecorderActivity;
import cn.ac.ict.yxd.itug.stride.MainActivity;

public class MenuActivity extends Activity {
    TextView stride, sound, face, cognition, finger, detection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initWidget();
    }
    private void initWidget()
    {
        stride = (TextView)findViewById(R.id.stride);
        sound = (TextView)findViewById(R.id.sound);
        face = (TextView)findViewById(R.id.face);
        cognition = (TextView)findViewById(R.id.cognition);
        finger = (TextView)findViewById(R.id.finger);
        detection = (TextView)findViewById(R.id.detection);

        detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainDetectorActivity.class);
                startActivity(intent);
            }
        });

        stride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, FFmpegRecorderActivity.class);
                startActivity(intent);
            }
        });
    }
}
