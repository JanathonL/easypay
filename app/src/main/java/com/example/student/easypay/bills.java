package com.example.student.easypay;


import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class bills extends AppCompatActivity {

    List<Map<String, Object>> listItems;
    private billadapter listViewAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        String url = "http://106.15.198.74/app/public/index.php/index/Index/getorders";
        ArrayList<String> key = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
        key.add("username");
        value.add(username.get());
        RequestThread t = new RequestThread(url, key, value);
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
                Intent intent = new Intent(bills.this, main_page.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(bills.this, PayActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(bills.this, mine.class);
                startActivity(intent);
            }
        });
        listItems = new ArrayList<Map<String, Object>>();
        try {
            Thread T1 = new Thread(t);
            T1.start();
            T1.join();
            JSONObject mObject = t.mObject;
            JSONArray mJSONArray;
            mJSONArray = mObject.getJSONArray("result");
            double p;
            String dt;
            String d;
            String type;
            int img;
            int tp;
            for(int i =  0 ; i < mJSONArray.length(); i++)
            {
                JSONObject jsonItem = null;
                jsonItem = mJSONArray.getJSONObject(i);
                p= jsonItem.getDouble("sumprice");
                dt = jsonItem.getString("date");
                tp = jsonItem.getInt("type");
                d=getWeek(dt);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date",d);
                map.put("cost",p);              //物品标题
                map.put("datetime", dt);     //物品名称
                type=gettype(tp);
                img=getimg(tp);
                map.put("type",type);
                map.put("img",img);
                listItems.add(map);
            }
            listView = (ListView)findViewById(R.id.order_list);
            listViewAdapter = new billadapter(this, listItems); //创建适配器
            listView.setAdapter(listViewAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getWeek(String pTime) {
        String Week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }
        return Week;
    }
    private String gettype(int t) {
        String x="";
        switch (t)
        {
            case 1:
                x="车票/飞机票";
                break;
            case 2:
                x="数码产品";
                break;
            case 3:
                x="电影";
                break;
            case 4:
                x="购物";
                break;
            case 5:
                x="公交车";
                break;
        }
        return x;
    }
    private int getimg(int t) {
        switch (t)
        {
            case 1:
                return R.drawable.icon_6;
            case 2:
                return R.drawable.icon_7;
            case 3:
                return R.drawable.icon_8;
            case 4:
                return R.drawable.icon_9;
            case 5:
                return R.drawable.icon_10;
            default:
                return -1;
        }
    }
}