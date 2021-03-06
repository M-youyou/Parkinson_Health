package cn.ac.ict.yxd.itug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.ac.ict.yxd.itug.stride.MainActivity;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MenuActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        },2500);
    }
}
