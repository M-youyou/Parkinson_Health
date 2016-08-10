package cn.ac.ict.yxd.itug.stride;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yxd on 8/2/16.
 */

public class RecordDB {

    public static void add(Context context, Record record) {
        long time = System.currentTimeMillis();
        String filename = time + ".y";
        FileOutputStream stream = null;
        ObjectOutputStream oos = null;
        try {
            stream = context.openFileOutput(filename,Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(stream);
            oos.writeObject(record);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                    oos = null;
                }
                if (stream != null) {
                    stream.flush();
                    stream.close();
                    stream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static List<Record> readAll(Context context) throws IOException,ClassNotFoundException {
        String[] files = context.fileList();
        ArrayList<Record> records = new ArrayList<>();
        if (files != null){
            for (String s : files) {
                if (s.endsWith(".y"))
                {
                    FileInputStream stream = context.openFileInput(s);
                    ObjectInputStream ois = new ObjectInputStream(stream);
                    Record record =(Record)ois.readObject();
                    records.add(record);
                }
            }
        }
        return records;
    }
    public static boolean delete(Context context, int id)
    {
        String[] files = context.fileList();
        return context.deleteFile(files[id]);
    }
    public static void saveToDir(Context context) throws IOException,ClassNotFoundException
    {
        List<Record> records = readAll(context);
        String dir = Environment.getExternalStorageDirectory()+"/HealthData";
        File dirF = new File(dir);
        if(!dirF.exists())
            dirF.mkdirs();
        File[] files = dirF.listFiles();
        for (File f:files) {
            f.delete();
        }
        for (Record r: records) {
            String fileName = r.getName()+r.getDate();
            File f = new File(dir+"/"+fileName);
            if(f.exists())
                continue;
            else{

                FileWriter fw = new FileWriter(f, true);//
                // 创建FileWriter对象，用来写入字符流
                BufferedWriter bw = new BufferedWriter(fw); // 将缓冲对文件的输出
                bw.write(r.getName()+","+r.getDate()+","+r.getTrail()+","+r.getDistance()+","+r.getLocationNum()+"\n");
                List<List> vl = r.getAccData();
                for(int i=0;i<r.getTrail();i++)
                {
                    List vectors = vl.get(i);
                    String s ="";
                    for(int j=0;j<vectors.size();j++)
                    {
                        Vector v = (Vector) vectors.get(j);
                        s += v.getTime()+","+v.getX()+","+v.getY()+","+v.getZ()+"\n";
                    }
                    bw.write("ACC "+ (i+1)+ "\n");
                    bw.write(s);
                }
                List<List> vg = r.getGryoData();
                for(int i=0;i<r.getTrail();i++)
                {
                    List vectors = vg.get(i);
                    String s ="";
                    for(int j=0;j<vectors.size();j++)
                    {
                        Vector v = (Vector) vectors.get(j);
                        s += v.getTime()+","+v.getX()+","+v.getY()+","+v.getZ()+"\n";
                    }
                    bw.write("Gyro "+ (i+1)+ "\n");
                    bw.write(s);
                }
                bw.flush(); // 刷新该流的缓冲
                bw.close();
                fw.close();
            }

        }
    }
}
