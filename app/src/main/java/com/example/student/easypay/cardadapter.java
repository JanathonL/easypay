package com.example.student.easypay;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by c on 2017/7/15.
 */

public class cardadapter extends BaseAdapter {
    private Context context;                        //运行上下文
    private List<Map<String,Object>> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器
    public HashMap<Integer,String> values;
    public final class ListItemView{                //自定义控件集合
        public TextView cardid;
    }

    public cardadapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;
        this.values=new HashMap<Integer,String>();
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return listItems.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("method", "getView");
        final int selectID = position;
        //自定义视图
        ListItemView  listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.bankcard_item, null);
            //获取控件对象
            listItemView.cardid= (TextView) convertView.findViewById(R.id.text_cardid);
            convertView.setTag(listItemView);
        }else {
            listItemView = (ListItemView)convertView.getTag();
        }
        String  str=(String) listItems.get(position).get("cardid");
        int isdefault=(int) listItems.get(position).get("isdefault");
        values.put(position,str);
        String x="**** **** **** "+str.substring(12);
        listItemView.cardid.setText(x);
        if(isdefault==1)
        {
            convertView.setBackgroundResource(R.drawable.address_background_default);
        }
        else
        {
            convertView.setBackgroundResource(R.drawable.background_bankcard);
        }
        return convertView;
    }




}
