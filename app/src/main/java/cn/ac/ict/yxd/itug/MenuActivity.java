package cn.ac.ict.yxd.itug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ac.ict.yxd.itug.detection.MainDetectorActivity;
import cn.ac.ict.yxd.itug.detection.SensorItem;
import cn.ac.ict.yxd.itug.face.FFmpegRecorderActivity;
import cn.ac.ict.yxd.itug.menu.MenuAdapter;
import cn.ac.ict.yxd.itug.menu.MenuItem;
import cn.ac.ict.yxd.itug.stride.MainActivity;

public class MenuActivity extends Activity {
    TextView stride, sound, face, cognition, finger, detection;
    private RecyclerView rv_menu;
    private MenuAdapter menuAdapter;
    private ArrayList<MenuItem> menuArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initWidget();
    }
    private void initWidget()
    {

        rv_menu = (RecyclerView) findViewById(R.id.rc_menu);
        rv_menu.setHasFixedSize(true);

        menuArray = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_menu.setLayoutManager(layoutManager);

        menuAdapter = new MenuAdapter(menuArray);
        rv_menu.setAdapter(menuAdapter);

        MenuItem stride = new MenuItem(R.drawable.ic_stride_50dp, "步态检测", "通过来回行走检测您的步态是否正常", "开始检测");
        stride.addMenuIntent(new Intent(MenuActivity.this, MainActivity.class));
        menuArray.add(stride);

        MenuItem face = new MenuItem(R.drawable.ic_face_50dp, "面部表情检测", "检测您的面部表情是否正常", "开始检测");
        face.addMenuIntent(new Intent(MenuActivity.this, FFmpegRecorderActivity.class));
        menuArray.add(face);

        MenuItem cognition = new MenuItem(R.drawable.ic_cognition_50dp, "认知能力检测", "..", "开始检测");
        // TODO: bind cognition activity.
        menuArray.add(cognition);

        MenuItem sound = new MenuItem(R.drawable.ic_sound_50dp, "语言能力检测", "..", "开始检测");
        // TODO: bind sound activity.
        menuArray.add(sound);

        MenuItem finger = new MenuItem(R.drawable.ic_finger_50dp, "手指灵敏度检测", "..", "开始检测");
        // TODO: bind finger activity.
        menuArray.add(finger);

        MenuItem detection = new MenuItem(R.drawable.ic_device_50dp, "设备检测", "检测您的手机是否支持我们的各项测试", "开始检测");
        detection.addMenuIntent(new Intent(MenuActivity.this, MainDetectorActivity.class));
        menuArray.add(detection);


    }
}
