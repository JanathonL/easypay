package com.example.student.easypay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;
import static com.example.student.easypay.R.id.text_card;


public class bindbankcard extends AppCompatActivity {
    public EditText card;
    public EditText code;
    public Button getcode;
    public Button submitcode;
    public String phone;
    private TimerTask tt;
    private int TIME = 60;
    private static final int CODE_REPEAT = 1; //重新发送
    private Timer tm;
    Handler hd;
    public ArrayList<Map<String, Object>> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_bank_card);
        card = (EditText) findViewById(R.id.text_card);
        code = (EditText) findViewById(R.id.text_code);
        getcode = (Button) findViewById(R.id.button_getcode);
        submitcode = (Button) findViewById(R.id.button_submitcode);
        getcode.setOnClickListener(new getcodelistener());
        submitcode.setOnClickListener(new submitlistener());
        String url = "http://106.15.198.74/app/public/index.php/index/Index/getphone";
        ArrayList<String> key = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
        key.add("username");
        value.add(username.get());
        RequestThread t = new RequestThread(url, key, value);
        listItems = new ArrayList<Map<String, Object>>();
        ((Button)findViewById(R.id.Button_return)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        hd = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == CODE_REPEAT) {
                    getcode.setEnabled(true);
                    submitcode.setEnabled(true);
                    tm.cancel();//取消任务
                    tt.cancel();//取消任务
                    TIME= 60;//时间重置
                    getcode.setText("重新发送验证码");
                }else {
                    getcode.setText(TIME + "重新发送验证码");
                }
            }
        };
        //回调
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        toast("验证成功");
                        bindcard();

                        SMSSDK.unregisterEventHandler(this);
                        startActivity(new Intent(bindbankcard.this, bankcard.class));
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){       //获取验证码成功
                        toast("获取验证码成功");
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//如果你调用了获取国家区号类表会在这里回调
                        //返回支持发送验证码的国家列表
                    }
                }else{//错误等在这里（包括验证失败）
                    //错误码请参照http://wiki.mob.com/android-api-错误码参考/这里我就不再继续写了
                    ((Throwable)data).printStackTrace();
                    String str = data.toString();
                    toast(str);
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
        try {
            Thread T1 = new Thread(t);
            T1.start();
            T1.join();
            JSONObject mObject = t.mObject;
            JSONArray mJSONArray;
            mJSONArray = mObject.getJSONArray("result");
            JSONObject jsonItem = null;
            jsonItem = mJSONArray.getJSONObject(0);
            phone = jsonItem.getString("phone");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
        class getcodelistener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            getVerificationCode("86",phone);
            Toast.makeText(bindbankcard.this, "验证码已发送", Toast.LENGTH_SHORT).show();
            getcode.setEnabled(false);
            submitcode.setEnabled(true);
            tm = new Timer();
            tt = new TimerTask() {
                @Override
                public void run() {
                    hd.sendEmptyMessage(TIME--);
                }
            };
            tm.schedule(tt,0,1000);
        }
    }
    class submitlistener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String c=code.getText().toString();
            if(c.isEmpty())
            {
                toast("请输入验证码后再提交");
            }
            else
                submitVerificationCode("86", phone,c );
        }
    }
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(bindbankcard.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void bindcard()
    {
        String number=card.getText().toString();
        String url = "http://106.15.198.74/app/public/index.php/index/Index/bindbankcard";
        ArrayList<String> key = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
        key.add("username");
        value.add(username.get());
        key.add("cardid");
        value.add(number);
        RequestThread t = new RequestThread(url, key, value);
        listItems = new ArrayList<Map<String, Object>>();
        try {
            Thread T1 = new Thread(t);
            T1.start();
            T1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}