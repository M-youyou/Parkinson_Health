package cn.ac.ict.yxd.itug.count;

/**
 * Created by zhongxi on 2016/9/1.
 */

    import android.os.Environment;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;

    public class WriteFile {

        public static void creatRootFile() {

            File dir = new File(Environment.getExternalStorageDirectory(), "/PDAFile");
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }

        public static String creatFile() {

            File dir = new File(Environment.getExternalStorageDirectory(), "/PDFile/Cognition");

            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        }

        public static void writeInFile(String context, String fileName) {
            FileOutputStream sensorValues = null;
            try {
                sensorValues = new FileOutputStream(fileName, true);
                sensorValues.write(context.getBytes("utf-8"));
                sensorValues.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


