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

import static com.example.student.easypay.R.layout.activity_login;

public class login extends AppCompatActivity {
    EditText edittext1;
    EditText edittext2;
    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);

        edittext1 = (EditText)findViewById(R.id.editText2);
        edittext2 = (EditText)findViewById(R.id.editText3);
        button1 = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button_pay);

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str1 = edittext2.getText().toString();
                String str2 = edittext1.getText().toString();

                String url = "http://106.15.198.74/app/public/index.php/index/Index/login";

                ArrayList<String> key = new ArrayList<String>();
                ArrayList<String> value = new ArrayList<String>();
                key.add("username");
                value.add(str1);
                key.add("password");
                value.add(str2);

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
                            new AlertDialog.Builder(login.this).setTitle("系统消息").setMessage("用户名或密码错误！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                        }
                        else{
                            username user = new username();
                            user.set(str1);
                            Intent intent = new Intent(login.this, main_page.class);
                            startActivity(intent);
                        }
                    }

                } catch (InterruptedException | org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(login.this, retrieve.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }
}
