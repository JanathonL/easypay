package com.example.student.easypay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class mine extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    TextView textview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine);
//        new AlertDialog.Builder(mine.this).setTitle("系统消息").setMessage(username.curr_username).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        }).show();
        button1 = (Button)findViewById(R.id.button_mainpage);
        button2 = (Button)findViewById(R.id.button_pay);
        button3 = (Button)findViewById(R.id.button_my);
        button4 = (Button)findViewById(R.id.button_order);
        button5 = (Button)findViewById(R.id.button_bankcard);
        button6 = (Button)findViewById(R.id.button_btn);
//        button7 = (Button)findViewById(R.id.button_goodsinfo);
        button8 = (Button)findViewById(R.id.button_address);
        button9 = (Button)findViewById(R.id.button_logout);
        button10 = (Button)findViewById(R.id.button11);

        textview1 = (TextView)findViewById(R.id.textView8);
        textview1.setText(username.curr_username);
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, main_page.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, PayActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, mine.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, bills.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, bankcard.class);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, showbutton.class);
                startActivity(intent);
            }
        });

//        button7.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(mine.this, goodsinfo.class);
//                startActivity(intent);
//            }
//        });

        button8.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, showaddress.class);
                startActivity(intent);
            }
        });

        button9.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                username test = new username();
                test.set("");
                Intent intent = new Intent(mine.this, login.class);
                startActivity(intent);
            }
        });

        button10.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mine.this, modify_info.class);
                startActivity(intent);
            }
        });
    }
}
