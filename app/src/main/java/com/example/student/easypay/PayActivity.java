package com.example.student.easypay;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@TargetApi(18)
public class PayActivity extends Activity {
    private final String targetDeviceName = "shop1"; //服务端蓝牙名
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> bluetoothDevices = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//随便定义一个UUID
    private BluetoothSocket clientSocket;   //客户端socket
    private BluetoothGatt gatt;     //客户端gatt
    private int rssi;   //客户端rssi
    private BluetoothDevice device; //目标服务端设备

    private AcceptThread acceptThread;  //服务端监听线程
    private final String NAME = "Bluetooth_Socket"; //服务名
    private BluetoothServerSocket serverSocket; //服务端监听socket
    private OutputStream os;//输出流
    private InputStream is;
    private final String TAG="BT:";
    private Context context;
    private boolean isListening = false;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_page);
        hint = (TextView)findViewById(R.id.text_hint);
        hint.setText("扫描中");
        //动态蓝牙授权
        requestBluetoothPermission();

        //开启蓝牙
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.enable();

        //获取已经配对的蓝牙设备
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice curDevice : pairedDevices) {
                //对于每一个发现的蓝牙设备，检查是否为目标蓝牙
                String name = curDevice.getName();
                if(targetDeviceName.equals(curDevice.getName())){
                    device = curDevice;
                }
            }
        }

        // 设置广播信息过滤
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED); //注册开始发现广播
        filter.setPriority(Integer.MAX_VALUE);//设置优先级
        // 注册蓝牙搜索广播接收者，接收并处理搜索结果
        this.registerReceiver(receiver, filter);

                //如果配对列表中已经存在，则直接配对无需搜索
                if(device != null){
                    bondBlueTooth();
                }
                else {
                    //如果当前在搜索，就先取消搜索
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    //开启搜索
                    mBluetoothAdapter.startDiscovery();
                }
        Button button1, button2, button3;
        button1 = (Button)findViewById(R.id.button_mainpage);
        button2 = (Button)findViewById(R.id.button_pay);
        button3 = (Button)findViewById(R.id.button_my);

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PayActivity.this, main_page.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PayActivity.this, PayActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PayActivity.this, mine.class);
                startActivity(intent);
            }
        });


    }

    private static final int REQUEST_BLUETOOTH_PERMISSION=10;
    /*
     * 动态蓝牙授权
     */
    private void requestBluetoothPermission(){
        //判断系统版本
        if (Build.VERSION.SDK_INT >= 23) {
            //检测当前app是否拥有某个权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            //判断这个权限是否已经授权过
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                //判断是否需要 向用户解释，为什么要申请该权限
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION))
                    Toast.makeText(this,"Need bluetooth permission.",
                            Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this ,new String[]
                        {Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_BLUETOOTH_PERMISSION);
                return;
            }else{
            }
        } else {
        }
    }

    /**
     * 定义广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice curDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (curDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //对于每一个搜索到的蓝牙
                    //Toast.makeText(context, "name:"+curDevice.getName()+"\taddress:"+curDevice.getAddress(),Toast.LENGTH_LONG).show();
                    //获取目标蓝牙设备
                    String name = curDevice.getName();
                    if(targetDeviceName.equals(name)){
                        device = curDevice;
                        hint.setText("连接中");
                    }
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已搜素完成，进行配对
                bondBlueTooth();
            }
        }
    };

    /*
     * 客户端连接服务端
     */
    private void bondBlueTooth(){
        if(device==null){
            Toast.makeText(context, "找不到指定设备",Toast.LENGTH_LONG).show();
            return;
        }
        String address=device.getAddress();
        try{
            if(clientSocket==null){
                //创建gatt连接
                gatt = device.connectGatt(context, true, new BluetoothGattCallback() {
                    public void onReadRemoteRssi (BluetoothGatt gatt, int r, int status){
                        if(status == BluetoothGatt.GATT_SUCCESS){
                            rssi = r;
                            //TODO:限制rssi
                            try{
                                if(os!=null){
                                    //往服务器端写信息
                                    //TODO: 在这里传输客户端信息
                                    os.write(username.curr_username.getBytes());
                                    isListening = true;
                                }
                                if(is!=null){
                                        //TODO:在这里实现客户端接收逻辑
                                        byte[] buffer =new byte[1024];
                                        int count = is.read(buffer);
                                        Message msg = new Message();
                                        //msg.obj = "支付成功";
                                        msg.obj = new String(buffer, 0, count, "utf-8");
                                        handler.sendMessage(msg);
                                }
                                //完成传输，断开socket
                                os=null;
                                is=null;
                                clientSocket.close();
                                clientSocket = null;
                            }
                            catch (Exception e){

                            }
                        }
                    }
                });
                //创建客户端蓝牙Socket
                clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                clientSocket.connect();
                Toast.makeText(context, "支付中",Toast.LENGTH_LONG).show();
                //获得输出流（客户端指向服务端输出文本）
                os = clientSocket.getOutputStream();
                //获取输入流
                is = clientSocket.getInputStream();
            }
            //TODO:设置距离判定条件,使用线程判断距离
            gatt.readRemoteRssi();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String str=String.valueOf(msg.obj);
            int index=str.indexOf("type:");
            char type=String.valueOf(msg.obj).charAt(index+5);
            if (type=='0'){
                hint.setText("支付成功");
            }
            else{
                Intent intent = new Intent(PayActivity.this, goodsinfo.class);
                intent.putExtra("data", str);
                startActivity(intent);
            }
            //hint.setText(String.valueOf(msg.obj));
            isListening = false;
        }
    };

    //服务端监听客户端的线程类
    private class AcceptThread extends Thread {
        private BluetoothSocket socket; //服务端socket
        public boolean flag;
        public AcceptThread() {
            try {
                flag = true;
                serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (Exception e) {
            }
        }
        @Override
        public void run() {
            try {
                while(flag){
                    socket = serverSocket.accept();
                    Toast.makeText(context, "已连接",Toast.LENGTH_LONG).show();
                    new ServiceThread(socket).run();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //服务端与客户端的通信线程类
    private class ServiceThread extends Thread {
        private BluetoothSocket socket; //服务端socket
        private InputStream is;//输入流
        public ServiceThread(BluetoothSocket socket){
            this.socket = socket;

            try{
                is = this.socket.getInputStream();
                os = this.socket.getOutputStream();

            } catch (IOException e){
                e.printStackTrace();
            }
        }
        @Override
        public void run(){
            try{
                while(true) {
                    //TODO:在这里实现服务端业务逻辑
                    byte[] buffer =new byte[1024];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    handler.sendMessage(msg);
                }
            }
            catch (IOException e){

            }
        }
    }

}

