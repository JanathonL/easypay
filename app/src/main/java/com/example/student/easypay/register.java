package com.example.student.easypay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class register extends AppCompatActivity {
    EditText edittext1;
    EditText edittext2;
    EditText edittext3;
    EditText edittext4;
    EditText edittext5;

    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edittext1 = (EditText)findViewById(R.id.editText);
        edittext2 = (EditText)findViewById(R.id.editText1);
        edittext3 = (EditText)findViewById(R.id.editText2);
        edittext4 = (EditText)findViewById(R.id.editText4);
        edittext5 = (EditText)findViewById(R.id.editText5);
        button1 = (Button)findViewById(R.id.Button_return);
        button2 = (Button)findViewById(R.id.button4);
        button3 = (Button)findViewById(R.id.button5);

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkPhone(edittext4.getText().toString())){
                    edittext5.setText("123456");
                }
                else{
                    new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("请输入合法的手机号！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
            String str1 = edittext1.getText().toString();
            String str2 = edittext2.getText().toString();
            String str3 = edittext3.getText().toString();
            String str4 = edittext4.getText().toString();
            String str5 = edittext5.getText().toString();

            if(checkUsername(str1)){
                if(checkPassword(str2)){
                    if(str2.equals(str3)){
                        if(checkPhone(str4)){
                            if(str5.equals("123456")){
                                String url = "http://106.15.198.74/app/public/index.php/index/Index/register";

                                ArrayList<String> key = new ArrayList<String>();
                                ArrayList<String> value = new ArrayList<String>();
                                key.add("username");
                                value.add(str1);
                                key.add("password");
                                value.add(str2);
                                key.add("phone");
                                value.add(str4);

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
                                            new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("用户名已被注册！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            }).show();
                                        }
                                        else{
                                            new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("注册成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                            else{
                                new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("验证码错误！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();
                            }
                        }
                        else{
                            new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("手机号格式错误！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                    }
                    else{
                        new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("重复密码不一致！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                    }
                }
                else{
                    new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("密码长度至少六位！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            }
            else{
                new AlertDialog.Builder(register.this).setTitle("系统消息").setMessage("用户名错误！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
            }
        });
    }

    boolean checkUsername(String str){
        return !str.equals("") && !(str == null);
    }

    boolean checkPassword(String str){
        String reg = "[0-9a-zA-Z]{6}[0-9a-zA-Z]*";
        return str.matches(reg);
    }

    boolean checkPhone(String str){
        String reg = "1(3|5|7|8)[0-9]{9}";
        return str.matches(reg);
    }
}
