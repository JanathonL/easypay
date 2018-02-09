package com.example.student.easypay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Student on 2017/7/16.
 */

public class goodsinfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goods_info);
        ((Button)findViewById(R.id.Button_return)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        int beginIndex= data.indexOf("商品封面:");
        int url = data.indexOf("url");
        int endIndex= data.indexOf("详情内容:");
        String coverUrl=data.substring(beginIndex+5,endIndex-1);
        String http = coverUrl;
        ImageView goods_img = (ImageView) findViewById(R.id.goods_img); //启动异步任务，加载网络图片
        new LoadImagesTask(goods_img).execute(http);
        beginIndex=endIndex+5;
        http=data.substring(beginIndex);
        ImageView goods_content = (ImageView) findViewById(R.id.goods_content); //启动异步任务，加载网络图片
        new LoadImagesTask(goods_content).execute(http);
        TextView goodsName=(TextView)findViewById(R.id.goods_name);
        beginIndex=data.indexOf("商品名称:")+5;
        endIndex= data.indexOf("商品价格:")-1;
        String name=data.substring(beginIndex,endIndex);
        goodsName.setText(name);

    }
}
