package com.example.student.easypay;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class showbutton extends AppCompatActivity {
    List<Map<String, Object>> listItems;
    private btnadapter listViewAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_button);
        String url = "http://106.15.198.74/app/public/index.php/index/Index/getbuttons";
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
                Intent intent = new Intent(showbutton.this, main_page.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(showbutton.this, PayActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(showbutton.this, mine.class);
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
            String goodsname;
            int goodsnum;
            String address;
            String phone;
            String receivername;
            double price;
            for(int i =  0 ; i < mJSONArray.length(); i++)
            {
                JSONObject jsonItem = null;
                jsonItem = mJSONArray.getJSONObject(i);
                goodsname=jsonItem.getString("goodsname");
                goodsnum=jsonItem.getInt("goodsnum");
                address=jsonItem.getString("location");
                phone=jsonItem.getString("phone");
                receivername=jsonItem.getString("receivername");
                price=jsonItem.getDouble("price");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("goodsname",goodsname);
                map.put("goodsnum",goodsnum);              //物品标题
                map.put("address", address);     //物品名称
                map.put("phone",phone);
                map.put("receivername",receivername);
                map.put("price",price);
                listItems.add(map);
            }
            listView = (ListView)findViewById(R.id.button_list);
            listViewAdapter = new btnadapter(this, listItems); //创建适配器
            listView.setAdapter(listViewAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


