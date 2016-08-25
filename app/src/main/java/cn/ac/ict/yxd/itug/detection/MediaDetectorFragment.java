package cn.ac.ict.yxd.itug.detection;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ac.ict.yxd.itug.R;

/**
 * Created by Shaobo on 8/23/16.
 */
public class MediaDetectorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mediadetector, container, false);
        RecyclerView rv_sensor = (RecyclerView) view.findViewById(R.id.rv_cameraList);
        rv_sensor.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        rv_sensor.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        rv_sensor.setLayoutManager(layoutManager);

        MediaAdapter cameraAdapter = new MediaAdapter();
        rv_sensor.setAdapter(cameraAdapter);

        return view;
    }
}
