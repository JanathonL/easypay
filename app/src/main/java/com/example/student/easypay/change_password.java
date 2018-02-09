package com.example.student.easypay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class change_password extends AppCompatActivity {
    EditText edittext1;
    EditText edittext2;
    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        edittext1 = (EditText)findViewById(R.id.editText1);
        edittext2 = (EditText)findViewById(R.id.editText2);
        button1 = (Button)findViewById(R.id.Button_return);
        button2 = (Button)findViewById(R.id.button8);

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(change_password.this, mine.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str1 = edittext1.getText().toString();
                String str2 = edittext2.getText().toString();

                if(checkPassword(str1)){
                    if(str1.equals(str2)){
                        String url = "http://106.15.198.74/app/public/index.php/index/Index/changepassword";

                        ArrayList<String> key = new ArrayList<String>();
                        ArrayList<String> value = new ArrayList<String>();
                        key.add("username");
                        value.add(username.curr_username);
                        key.add("password");
                        value.add(str1);

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
                                    new AlertDialog.Builder(change_password.this).setTitle("系统消息").setMessage("操作失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).show();
                                }
                                else{
                                    new AlertDialog.Builder(change_password.this).setTitle("系统消息").setMessage("操作成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                        new AlertDialog.Builder(change_password.this).setTitle("系统消息").setMessage("重复密码不一致！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                    }
                }
                else{
                    new AlertDialog.Builder(change_password.this).setTitle("系统消息").setMessage("密码长度至少六位！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            }
        });
    }

    boolean checkPassword(String str){
        String reg = "[0-9a-zA-Z]{6}[0-9a-zA-Z]*";
        return str.matches(reg);
    }
}
