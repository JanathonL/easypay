package com.example.student.easypay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class modify_info extends AppCompatActivity {
    EditText edittext1;
    EditText edittext2;
    EditText edittext3;
    EditText edittext4;
    TextView textview;


    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;

    Button button7;
    Button button8;
    Button button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_modifyinfo);

        edittext1 = (EditText)findViewById(R.id.text_birthday);
        edittext2 = (EditText)findViewById(R.id.text_nickname);
        edittext3 = (EditText)findViewById(R.id.text_gender);
        edittext4 = (EditText)findViewById(R.id.text_phone);
        textview = (TextView)findViewById(R.id.text_password);

        String url = "http://106.15.198.74/app/public/index.php/index/Index/getInfo";

        ArrayList<String> key = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
        key.add("username");
        value.add(username.curr_username);

        RequestThread t = new RequestThread(url, key, value);
        try{
            Thread T1 = new Thread(t);
            T1.start();
            T1.join();
            JSONArray jsonArray = t.mJSONArray;

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String isOk = jsonObject.getString("isOk");

                if(isOk.equals("0")){
                    new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("系统错误！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
                else{
                    String birthday = jsonObject.getString("birthday");
                    edittext1.setText(birthday);
                    String nickname = jsonObject.getString("nickname");
                    edittext2.setText(nickname);
                    String gender = jsonObject.getString("gender");
                    if(gender.equals("1")){
                        edittext3.setText("男");
                    }
                    else{
                        edittext3.setText("女");
                    }
                    String phone = jsonObject.getString("phone");
                    edittext4.setText(phone);
                    textview.setText("******");
                }
            }

        } catch (InterruptedException | org.json.JSONException e) {
            e.printStackTrace();
        }


        //button1 = (Button)findViewById(R.id.button_return);

        button2 = (Button)findViewById(R.id.button_birthday);
        button3 = (Button)findViewById(R.id.button_nickname);
        button4 = (Button)findViewById(R.id.button_gender);
        button5 = (Button)findViewById(R.id.button_phone);
        button6 = (Button)findViewById(R.id.button_password);

        button7 = (Button)findViewById(R.id.button_mainpage);
        button8 = (Button)findViewById(R.id.button_pay);
        button9 = (Button)findViewById(R.id.button_my);

        ((Button)findViewById(R.id.Button_return)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "http://106.15.198.74/app/public/index.php/index/Index/modifyInfo";

                ArrayList<String> key = new ArrayList<String>();
                ArrayList<String> value = new ArrayList<String>();
                key.add("username");
                value.add(username.curr_username);
                key.add("key");
                value.add("birthday");
                key.add("value");
                value.add(edittext1.getText().toString());

                RequestThread t = new RequestThread(url, key, value);
                try{
                    Thread T1 = new Thread(t);
                    T1.start();
                    T1.join();
                    JSONArray jsonArray = t.mJSONArray;

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String isOk = jsonObject.getString("isOk");

                        if(isOk.equals("0")){
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                        else{
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                    }

                } catch (InterruptedException | org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "http://106.15.198.74/app/public/index.php/index/Index/modifyInfo";

                ArrayList<String> key = new ArrayList<String>();
                ArrayList<String> value = new ArrayList<String>();
                key.add("username");
                value.add(username.curr_username);
                key.add("key");
                value.add("nickname");
                key.add("value");
                value.add(edittext2.getText().toString());


                RequestThread t = new RequestThread(url, key, value);
                try{
                    Thread T1 = new Thread(t);
                    T1.start();
                    T1.join();
                    JSONArray jsonArray = t.mJSONArray;

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String isOk = jsonObject.getString("isOk");

                        if(isOk.equals("0")){
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                        else{
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                    }

                } catch (InterruptedException | org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "http://106.15.198.74/app/public/index.php/index/Index/modifyInfo";

                ArrayList<String> key = new ArrayList<String>();
                ArrayList<String> value = new ArrayList<String>();
                key.add("username");
                value.add(username.curr_username);
                key.add("key");
                value.add("gender");
                key.add("value");
                if(edittext4.getText().toString().equals("男")){
                    value.add("1");
                }
                else{
                    value.add("0");
                }


                RequestThread t = new RequestThread(url, key, value);
                try{
                    Thread T1 = new Thread(t);
                    T1.start();
                    T1.join();
                    JSONArray jsonArray = t.mJSONArray;

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String isOk = jsonObject.getString("isOk");

                        if(isOk.equals("0")){
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                        else{
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                    }

                } catch (InterruptedException | org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button5.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "http://106.15.198.74/app/public/index.php/index/Index/modifyInfo";

                ArrayList<String> key = new ArrayList<String>();
                ArrayList<String> value = new ArrayList<String>();
                key.add("username");
                value.add(username.curr_username);
                key.add("key");
                value.add("phone");
                key.add("value");
                value.add(edittext4.getText().toString());

                RequestThread t = new RequestThread(url, key, value);
                try{
                    Thread T1 = new Thread(t);
                    T1.start();
                    T1.join();
                    JSONArray jsonArray = t.mJSONArray;

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String isOk = jsonObject.getString("isOk");

                        if(isOk.equals("0")){
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                        else{
                            new AlertDialog.Builder(modify_info.this).setTitle("系统消息").setMessage("修改成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                    }

                } catch (InterruptedException | org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button6.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(modify_info.this, change_password.class);
                startActivity(intent);
            }
        });

        button7.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(modify_info.this, main_page.class);
                startActivity(intent);
            }
        });

        button8.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(modify_info.this, PayActivity.class);
                startActivity(intent);
            }
        });

        button9.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(modify_info.this, mine.class);
                startActivity(intent);
            }
        });
    }
}
