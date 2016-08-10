package cn.ac.ict.yxd.itug.stride;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ac.ict.yxd.itug.R;

public class HistoryActivity extends Activity {
    ListView lvHistory;
    ArrayList<Record> records;
    Button btn_save;
    List<Map<String,String>> lists;
    PullToRefreshView pullToRefreshView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }
    @Override
    protected void onResume() {
        initWidegt();
        super.onResume();
    }

    private void initWidegt()
    {
        pullToRefreshView = (PullToRefreshView)findViewById(R.id.pull_to_refresh);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    records = (ArrayList<Record>) RecordDB.readAll(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                lists = new ArrayList<>();

                for (Record r:records) {
                    Map<String,String> map = new HashMap<>();
                    map.put("name",r.getName());
                    map.put("date",r.getDate());
                    lists.add(map);
                }
                lvHistory.setAdapter(new myAdapter(HistoryActivity.this,lists));
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                    }
                }, 500);

            }
        });
        lvHistory = (ListView)findViewById(R.id.lv_history);
        btn_save = (Button)findViewById(R.id.btn_save);
        try {
            records = (ArrayList<Record>) RecordDB.readAll(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lists = new ArrayList<>();

        for (Record r:records) {
            Map<String,String> map = new HashMap<>();
            map.put("name",r.getName());
            map.put("date",r.getDate());
            lists.add(map);
        }
        lvHistory.setAdapter(new myAdapter(HistoryActivity.this,lists));
//        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Record record = records.get(position);
//                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("record",record);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RecordDB.saveToDir(HistoryActivity.this);
                    Toast.makeText(HistoryActivity.this, "保存成功",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Log.e("ddd",e.getMessage());
                    Toast.makeText(HistoryActivity.this, "IOException: 保存失败,请联系工程师",Toast.LENGTH_SHORT).show();
                } catch (ClassNotFoundException e) {
                    Toast.makeText(HistoryActivity.this, "ClassNotFoundException: 保存失败,请联系工程师",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    class myAdapter extends BaseAdapter {
        private Context context;                        //运行上下文
        private List<Map<String, String>> listItems;    //商品信息集合
        private LayoutInflater listContainer;           //视图容器
        public final class ListItemView{                //自定义控件集合
            public TextView name;
            public TextView date;
            public TextView swip;
            public LinearLayout ll;
        }
        public myAdapter(Context context, List<Map<String, String>> listItems) {
            this.context = context;
            listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
            this.listItems = listItems;
        }
        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int selectID = position;
            //自定义视图
            ListItemView  listItemView = null;
            if (convertView == null) {
                listItemView = new ListItemView();
                //获取list_item布局文件的视图
                convertView = listContainer.inflate(R.layout.item_history, null);
                //获取控件对象
                listItemView.name = (TextView) convertView.findViewById(R.id.tv_item_name);
                listItemView.date = (TextView)convertView.findViewById(R.id.tv_item_date);
                listItemView.swip = (TextView)convertView.findViewById(R.id.tv_item_swip);
                listItemView.ll = (LinearLayout)convertView.findViewById(R.id.ll_item_ll);
                //设置控件集到convertView
                convertView.setTag(listItemView);
            }else {
                listItemView = (ListItemView)convertView.getTag();
            }
            listItemView.name.setText((String)listItems.get(position).get("name"));
            listItemView.date.setText((String)listItems.get(position).get("date"));
            listItemView.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Record record = records.get(selectID);
                    Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("record",record);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            listItemView.swip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean flag = RecordDB.delete(HistoryActivity.this,selectID);
                    if(flag)
                        Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_SHORT).show();
                    lists.remove(selectID);
                    lvHistory.setAdapter(new myAdapter(HistoryActivity.this,lists));
                }
            });

            return convertView;
        }
    }

}
