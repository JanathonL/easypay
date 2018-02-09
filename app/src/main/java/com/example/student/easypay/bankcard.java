package com.example.student.easypay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bankcard extends AppCompatActivity {
    List<Map<String, Object>> listItems;
    private cardadapter listViewAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bankcard);
        Button addbtn = (Button)findViewById(R.id.button_add);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bankcard.this, bindbankcard.class);
                startActivity(intent);
            }
        });
        //绑定底部导航栏按钮事件
        ((Button)findViewById(R.id.Button_return)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button button1, button2, button3;
        button1 = (Button)findViewById(R.id.button_mainpage);
        button2 = (Button)findViewById(R.id.button_pay);
        button3 = (Button)findViewById(R.id.button_my);
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(bankcard.this, main_page.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(bankcard.this, PayActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(bankcard.this, mine.class);
                startActivity(intent);
            }
        });
        listItems = new ArrayList<Map<String, Object>>();
        getdata();
        listView = (ListView) findViewById(R.id.card_list);
        listViewAdapter = new cardadapter(this, listItems); //创建适配器
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new ItemClickEvent());
    }

    private final class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        //这里需要注意的是第三个参数arg2，这是代表单击第几个选项
        public void onItemClick(final AdapterView<?> adapter, final View view, final int position,
                                long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(bankcard.this);
            builder.setIcon(R.drawable.icon5);
            builder.setTitle("选择操作");
            //    指定下拉列表的显示数据
            final String[] cities = {"删除", "设为默认"};
            //    设置一个下拉的列表选择项
            builder.setItems(cities, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0)
                    {
                        String id=listViewAdapter.values.get(position);
                        ArrayList<String> key = new ArrayList<String>();
                        ArrayList<String> value = new ArrayList<String>();
                        key.add("cardid");
                        value.add(id);
                        String url = "http://106.15.198.74/app/public/index.php/index/Index/deletecard";
                        RequestThread t = new RequestThread(url, key, value);
                        try {
                            Thread T1 = new Thread(t);
                            T1.start();
                            T1.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getdata();
                        listViewAdapter.notifyDataSetChanged();
                        Toast.makeText(bankcard.this, "操作成功！", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String id=listViewAdapter.values.get(position);
                        ArrayList<String> key = new ArrayList<String>();
                        ArrayList<String> value = new ArrayList<String>();
                        key.add("cardid");
                        value.add(id);
                        key.add("username");
                        value.add(username.get());
                        String url = "http://106.15.198.74/app/public/index.php/index/Index/setdefaultcard";
                        RequestThread t = new RequestThread(url, key, value);
                        try {
                            Thread T1 = new Thread(t);
                            T1.start();
                            T1.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getdata();
                        listViewAdapter.notifyDataSetChanged();
                        Toast.makeText(bankcard.this, "操作成功！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();
        }
    }

    private void refresh(){
        super.onResume();
        onCreate(null);
    }

    public void getdata()
    {
        String url = "http://106.15.198.74/app/public/index.php/index/Index/getbankcards";
        ArrayList<String> key = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
        key.add("username");
        value.add(username.get());
        RequestThread t = new RequestThread(url, key, value);
        try {
            Thread T1 = new Thread(t);
            T1.start();
            T1.join();
            JSONObject mObject = t.mObject;
            JSONArray mJSONArray;
            mJSONArray = mObject.getJSONArray("result");
            String id;
            int isdefault;
            listItems.clear();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonItem = null;
                jsonItem = mJSONArray.getJSONObject(i);
                id = jsonItem.getString("cardid");
                isdefault=jsonItem.getInt("default_tag");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cardid", id);
                map.put("isdefault",isdefault);
                listItems.add(map);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
