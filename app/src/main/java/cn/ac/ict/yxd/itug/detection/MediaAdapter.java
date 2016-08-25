package cn.ac.ict.yxd.itug.detection;

import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.ac.ict.yxd.itug.R;

/**
 * Created by Shaobo on 8/23/16.
 */
public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder>{
    ArrayList<MediaInformation> listData;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
            cameraLabelView = (TextView) v.findViewById(R.id.tv_cameraLabel);
            cameraResolutionView = (TextView) v.findViewById(R.id.tv_cameraResolution);
        }
        TextView cameraLabelView;
        TextView cameraResolutionView;
    }

    public class MediaInformation {
        public String mediaName;
        public String mediaDescription;
        public MediaInformation(String name, String description) {
            mediaName = name;
            mediaDescription = description;
        }
    }

    private Camera.Size getMaxResolution(List<Camera.Size> parametersList) {
        long maxResolution = -1;
        Camera.Size maxParameters = parametersList.get(0);
        for (Camera.Size parameters: parametersList){
            if (parameters.height * parameters.width > maxResolution) {
                maxResolution = parameters.height * parameters.width;
                maxParameters = parameters;
            }
        }
        return maxParameters;
    }

    public MediaAdapter() {
        // Check camera resolution.
        // TODO: exception handler when no camera or camera initial failed.
        listData = new ArrayList<>();
        Camera  camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        List<Camera.Size> parametersList = camera.getParameters().getSupportedPictureSizes();
        Camera.Size parameters = getMaxResolution(parametersList);
        listData.add(new MediaInformation("前置摄像头", String.format(Locale.US, "%dx%d", parameters.width, parameters.height)));
        camera.release();
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        parametersList = camera.getParameters().getSupportedPictureSizes();
        parameters = getMaxResolution(parametersList);
        listData.add(new MediaInformation("后置摄像头", String.format(Locale.US, "%dx%d", parameters.width, parameters.height)));
        camera.release();

        // Check microphone.
        int audioSource = MediaRecorder.AudioSource.MIC;
        int baseSampleRate = 44100;
        int channel = AudioFormat.CHANNEL_IN_MONO;
        int format = AudioFormat.ENCODING_PCM_16BIT;
        int buffSize = AudioRecord.getMinBufferSize(baseSampleRate, channel, format );
        AudioRecord audioRecord = new AudioRecord(audioSource, 44100, channel, format, buffSize);

        String microphoneState = (audioRecord.getState() == AudioRecord.STATE_INITIALIZED)?"支持":"不支持";
        listData.add(new MediaInformation("麦克风", microphoneState));

    }

    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_media, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.cameraLabelView.setText(listData.get(position).mediaName);
        holder.cameraResolutionView.setText(listData.get(position).mediaDescription);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
