package cn.ac.ict.yxd.itug.tapping;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ac.ict.yxd.itug.R;

public class TappingMainActivity extends Activity implements View.OnClickListener {

    private TextView tvLeft, tvRight;
    private EditText etUsername, etAge;
    private Button btnStart, btnLeft, btnRight;

    private int leftCount, rightCount;
    private File path;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapping_main);

        findView();

        setListener();

        createDirctory();
    }

    private void createDirctory() {
        path = new File(Environment.getExternalStorageDirectory() + "/中科院tapping");
        if (!path.exists()) {
            path.mkdir();
        }
    }

    private void setListener() {
        btnStart.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    private void findView() {
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);

        etUsername = (EditText) findViewById(R.id.et_name);
        etAge = (EditText) findViewById(R.id.et_age);

        btnStart = (Button) findViewById(R.id.btn_start_tiper);

        tvLeft = (TextView) findViewById(R.id.tv_left_count);
        tvRight = (TextView) findViewById(R.id.tv_right_count);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_tiper:
                new MyCount(10000, 1000).start();
                setEnable(false, true);
                break;
            case R.id.btn_left:
                btnLeft.setText(String.valueOf(++leftCount));
                break;
            case R.id.btn_right:
                btnRight.setText(String.valueOf(++rightCount));
                break;
        }
    }

    private void setEnable(boolean enabled, boolean enabled2) {
        btnStart.setEnabled(enabled);
        etUsername.setEnabled(enabled);
        etAge.setEnabled(enabled);
        btnLeft.setEnabled(enabled2);
        btnRight.setEnabled(enabled2);
    }

    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            btnStart.setText("测试倒计时:" + (l / 1000));
        }

        @Override
        public void onFinish() {
            btnStart.setText("开始测试!");

            writeFile();
            initNum();
            setEnable(true, false);
        }
    }

    private void initNum() {
        leftCount = 0;
        rightCount = 0;
        btnLeft.setText(String.valueOf(0));
        btnRight.setText(String.valueOf(0));
    }

    private void writeFile() {
        FileOutputStream outputStream = null;
        try {
            tvLeft.setText(String.valueOf(leftCount));
            tvRight.setText(String.valueOf(rightCount));
            String username = etUsername.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            fileName = simpleDateFormat.format(new Date()) + ".txt";
            if (!TextUtils.isEmpty(age))
                fileName = age + "_" + fileName;
            if (!TextUtils.isEmpty(username))
                fileName = username + "_" + fileName;
            outputStream = new FileOutputStream(new File(path, fileName));
            outputStream.write(("leftCount:" + leftCount + "##rightCount:" + rightCount).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
