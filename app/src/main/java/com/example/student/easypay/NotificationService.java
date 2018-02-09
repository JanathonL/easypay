package com.example.student.easypay;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationService extends Service {

    //更新周期
    private int UpdateInterval = 5000;

    //目标URL
    private final String Url = "http://106.15.198.74/app/public/index.php/index/Index/getMsg";

    // 获取消息线程
    private MessageThread messageThread = null;

    // 点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;

    // 通知栏消息
    private int messageNotificationID = 1000;
    private Notification messageNotification = null;
    private NotificationManager messageNotificationManager = null;

    //大图标
    private Bitmap LargeIcon;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 初始化
        messageNotification = new Notification();
        messageNotification.icon = R.drawable.logo;
        messageNotification.tickerText = "新消息";
        messageNotification.defaults = Notification.DEFAULT_SOUND;
        messageNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        LargeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.icon);

        //TODO:在这里修改页面跳转的intent
        messageIntent = new Intent(this, bills.class);
        messagePendingIntent = PendingIntent.getActivity(this, 0,
                messageIntent, 0);

        // 开启线程
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 从服务器端获取消息
     *
     */
    class MessageThread extends Thread {
        // 设置是否循环推送
        public boolean isRunning = true;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void run() {
             while (isRunning) {
            try {
                // 间隔时间
                Thread.sleep(UpdateInterval);
                // 获取服务器消息
                ArrayList<String> serverMessage = getServerMessage();
                if (serverMessage != null && !"".equals(serverMessage.get(0))) {
                    for(int i=0; i<serverMessage.size(); i++){
                        JSONObject mJSONObject = new JSONObject(serverMessage.get(i));
                        // 更新通知栏
                        messageNotification = new Notification.Builder(getApplicationContext())
                                .setContentTitle("交易成功")
                                .setContentText("交易金额："+mJSONObject.getString("sumprice"))
                                .setWhen(System.currentTimeMillis())
                                .setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_VIBRATE)
                                .setSmallIcon(R.mipmap.icon)
                                .setLargeIcon(LargeIcon)
                                .setContentIntent(messagePendingIntent)
                                .build();
                        messageNotificationManager.notify(messageNotificationID,
                                messageNotification);
                        // 每次通知完，通知ID递增一下，避免消息覆盖掉
                        messageNotificationID++;
                    }
                }
            } catch (InterruptedException | JSONException e) {
                e.printStackTrace();
            }
             }
        }
    }

    @Override
    public void onDestroy() {
        // System.exit(0);
        messageThread.isRunning = false;
        super.onDestroy();
    }

    /**
     * 模拟发送消息
     *
     * @return 返回服务器要推送的消息，否则如果为空的话，不推送
     */
    //TODO:获取服务器端的消息
    public ArrayList<String> getServerMessage() {
        return newMsg(username.curr_username, Url);
    }

    public static ArrayList<String> newMsg(String user,String url){
        if("".equals(user)){
            return null;
        }
        ArrayList<String> mArray = new ArrayList<String>();
        ArrayList<String> key=new ArrayList<String>();
        ArrayList<String> value=new ArrayList<String>();
        key.add("username");
        value.add(user);
        RequestThread t=new RequestThread(url,key,value);
        try {
            Thread T1=new Thread(t);
            T1.start();
            T1.join();
            JSONArray mJSONArray=t.mJSONArray;
            if(mJSONArray!=null)
                if(mJSONArray.length() > 0)
                {
                    JSONObject jsonItem = mJSONArray.getJSONObject(0);
                    JSONArray messages = jsonItem.getJSONArray("data");
                    for(int j=0; j<messages.length(); j++){
                        mArray.add(messages.getString(j));
                    }
                    return mArray;
                }
        } catch (InterruptedException | org.json.JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}