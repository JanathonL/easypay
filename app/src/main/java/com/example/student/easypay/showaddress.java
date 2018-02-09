package com.example.student.easypay;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class showaddress  extends AppCompatActivity {

    ConstraintLayout card;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_address);
        card = (ConstraintLayout) findViewById(R.id.layout_address1);
        card.setVisibility(View.GONE);
        card = (ConstraintLayout) findViewById(R.id.layout_address2);
        card.setVisibility(View.GONE);
        card = (ConstraintLayout) findViewById(R.id.layout_address3);
        card.setVisibility(View.GONE);
        card = (ConstraintLayout) findViewById(R.id.layout_address4);
        card.setVisibility(View.GONE);
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
                Intent intent = new Intent(showaddress.this, main_page.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(showaddress.this, PayActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(showaddress.this, mine.class);
                startActivity(intent);
            }
        });
        String url="http://106.15.198.74/app/public/index.php/index/Index/showaddress";
        ArrayList<String> key =new ArrayList<String>();
        ArrayList<String> value=new ArrayList<String>();
        key.add("username");
        value.add(username.get());
        RequestThread t=new RequestThread(url,key,value);
        try {
            Thread T1=new Thread(t);
            T1.start();
            T1.join();
            JSONObject mObject=t.mObject;
            JSONArray mJSONArray;
            mJSONArray = mObject.getJSONArray("result");
            ArrayList<HashMap<String, String>> mArray = new ArrayList<HashMap<String,String>>();
            String address="";
            String name="";
            String phone="";
            if(mJSONArray.length()==0)
            {

            }
            else
            {
                TextView e1;
                TextView e2;
                TextView e3;
                TextView e4;
                for(int i =  0 ; i < mJSONArray.length(); i++)
                {
                    if(i>3)
                        break;
                    JSONObject jsonItem = null;
                    jsonItem = mJSONArray.getJSONObject(i);
                    address = jsonItem.getString("location");
                    name = jsonItem.getString("receivername");
                    phone= jsonItem.getString("phone");
                    if(i==0)
                    {
                        card = (ConstraintLayout)findViewById(R.id.layout_address1);
                        e1=(TextView)findViewById(R.id.text_number1);
                        e2=(TextView)findViewById(R.id.text_goodsname1);
                        e3=(TextView)findViewById(R.id.text_name1);
                        e4=(TextView)findViewById(R.id.text_phone1);
                        String str = new String("1");
                        e1.setText(str.toCharArray(),0,str.length());
                        e2.setText(address.toCharArray(),0,address.length());
                        e3.setText(name.toCharArray(),0,name.length());
                        e4.setText(phone.toCharArray(),0,phone.length());
                        card.setVisibility(View.VISIBLE);
                    }
                    else if(i==1)
                    {
                        card = (ConstraintLayout)findViewById(R.id.layout_address2);
                        e1=(TextView)findViewById(R.id.text_number2);
                        e2=(TextView)findViewById(R.id.text_goodsname2);
                        e3=(TextView)findViewById(R.id.text_name2);
                        e4=(TextView)findViewById(R.id.text_phone2);
                        String str = new String("2");
                        e1.setText(str.toCharArray(),0,str.length());
                        e2.setText(address.toCharArray(),0,address.length());
                        e3.setText(name.toCharArray(),0,name.length());
                        e4.setText(phone.toCharArray(),0,phone.length());
                        card.setVisibility(View.VISIBLE);
                    }
                    else if(i==2)
                    {
                        card = (ConstraintLayout)findViewById(R.id.layout_address3);
                        e1=(TextView)findViewById(R.id.text_number3);
                        e2=(TextView)findViewById(R.id.text_goodsname3);
                        e3=(TextView)findViewById(R.id.text_name3);
                        e4=(TextView)findViewById(R.id.text_phone3);
                        String str = new String("3");
                        e1.setText(str.toCharArray(),0,str.length());
                        e2.setText(address.toCharArray(),0,address.length());
                        e3.setText(name.toCharArray(),0,name.length());
                        e4.setText(phone.toCharArray(),0,phone.length());
                        card.setVisibility(View.VISIBLE);
                    }
                    else if(i==3)
                    {
                        card = (ConstraintLayout)findViewById(R.id.layout_address4);
                        e1=(TextView)findViewById(R.id.text_number4);
                        e2=(TextView)findViewById(R.id.text_goodsname4);
                        e3=(TextView)findViewById(R.id.text_name4);
                        e4=(TextView)findViewById(R.id.text_phone4);
                        String str = new String("4");
                        e1.setText(str.toCharArray(),0,str.length());
                        e2.setText(address.toCharArray(),0,address.length());
                        e3.setText(name.toCharArray(),0,name.length());
                        e4.setText(phone.toCharArray(),0,phone.length());
                        card.setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}