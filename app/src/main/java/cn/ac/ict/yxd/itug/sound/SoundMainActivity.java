package cn.ac.ict.yxd.itug.sound;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ac.ict.yxd.itug.R;

public class SoundMainActivity extends Activity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private Button btn_start_recorder;
    private EditText etUsername, etAge;

    private MediaRecorder recorder;
    private File path;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_main);

        findView();

        setListener();

        createDirctory();


    }

    private void createDirctory() {
        path = new File(Environment.getExternalStorageDirectory() + "/中科院录音");
        if (!path.exists()) {
            path.mkdir();
        }
    }

    private void setListener() {
        btn_start_recorder.setOnClickListener(this);
    }

    private void findView() {
        btn_start_recorder = (Button) findViewById(R.id.btn_start_recorder);
        etUsername = (EditText) findViewById(R.id.et_name);
        etAge = (EditText) findViewById(R.id.et_age);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_recorder:
                if (btn_start_recorder.isEnabled()) {
                    prepareRecorder();
                }
                setEnable(false);
                new MyCount(10000, 1000).start();
                break;
        }
    }

    private void prepareRecorder() {
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            String username = etUsername.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            fileName = simpleDateFormat.format(new Date()) + ".3gp";
            if (!TextUtils.isEmpty(age))
                fileName = age + "_" + fileName;
            if (!TextUtils.isEmpty(username))
                fileName = username + "_" + fileName;
            recorder.setOutputFile(path + File.separator + fileName);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                recorder.prepare();
                recorder.start();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void releaseRecorder() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    private void setEnable(boolean enabled) {
        btn_start_recorder.setEnabled(enabled);
        etUsername.setEnabled(enabled);
        etAge.setEnabled(enabled);
    }

    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            btn_start_recorder.setText("录音倒计时:" + (l / 1000));
        }

        @Override
        public void onFinish() {
            btn_start_recorder.setText("开始录音!");
            setEnable(true);
            releaseRecorder();
        }
    }

}
