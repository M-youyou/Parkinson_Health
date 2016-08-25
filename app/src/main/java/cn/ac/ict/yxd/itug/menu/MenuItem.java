package cn.ac.ict.yxd.itug.menu;

import android.content.Intent;

/**
 * Created by Shaobo on 8/25/16.
 */
public class MenuItem {
    int iconDrawableId;
    String headerString, descriptionString, executionString;
    Intent menuIntent = null;
    public MenuItem(int icon, String header, String description, String execution) {
        iconDrawableId = icon;
        headerString = header;
        descriptionString = description;
        executionString = execution;
    }

    public void addMenuIntent(Intent intent) {
        menuIntent = intent;
    }


}
