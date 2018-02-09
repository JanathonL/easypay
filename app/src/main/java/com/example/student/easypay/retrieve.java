package com.example.student.easypay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class retrieve extends AppCompatActivity {
    EditText edittext1;
    EditText edittext2;
    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_password);

        edittext1 = (EditText)findViewById(R.id.editText1);
        edittext2 = (EditText)findViewById(R.id.editText5);
        button1 = (Button)findViewById(R.id.Button_return);
        button2 = (Button)findViewById(R.id.button4);
        button3 = (Button)findViewById(R.id.button6);

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(retrieve.this, login.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkUsername(edittext1.getText().toString())){
                    edittext2.setText("123456");
                }
                else{
                    new AlertDialog.Builder(retrieve.this).setTitle("系统消息").setMessage("请输入正确的用户名！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                if(edittext2.getText().toString().equals("123456")){
                    username user = new username();
                    user.set(edittext1.getText().toString());
                    Intent intent = new Intent(retrieve.this, main_page.class);
                    startActivity(intent);
                }
                else{
                    new AlertDialog.Builder(retrieve.this).setTitle("系统消息").setMessage("用户名或验证码错误！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            }
        });
    }

    boolean checkUsername(String str){
        String url = "http://106.15.198.74/app/public/index.php/index/Index/checkusername";

        ArrayList<String> key = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
        key.add("username");
        value.add(str);

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
                    return false;
                }
                else{
                    return true;
                }
            }
        } catch (InterruptedException | org.json.JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    boolean checkPhone(String str){
        String reg = "1(3|5|7|8)[0-9]{9}";
        return str.matches(reg);
    }
}
