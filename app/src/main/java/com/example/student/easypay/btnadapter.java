package com.example.student.easypay;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by c on 2017/7/15.
 */

public class btnadapter extends BaseAdapter {
    private Context context;                        //运行上下文
    private List<Map<String,Object>> listItems;    //商品信息集合
    private LayoutInflater listContainer;           //视图容器
    public final class ListItemView{                //自定义控件集合
        public TextView button_number;
        public TextView goodsname;
        public TextView address;
        public TextView phone;
        public TextView price;
        public TextView number;
        public TextView receivername;
    }

    public btnadapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;
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
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.button_item, null);
            //获取控件对象
            listItemView.button_number = (TextView) convertView.findViewById(R.id.text_button_number1);
            listItemView.address = (TextView) convertView.findViewById(R.id.text_address1);
            listItemView.price = (TextView) convertView.findViewById(R.id.text_price1);
            listItemView.number = (TextView) convertView.findViewById(R.id.text_goods_number1);
            listItemView.phone = (TextView) convertView.findViewById(R.id.text_phone1);
            listItemView.goodsname = (TextView) convertView.findViewById(R.id.text_goodsname1);
            listItemView.receivername = (TextView) convertView.findViewById(R.id.text_name1);
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
        String numb = new Integer(position+1).toString();
        String a=(String) listItems.get(position).get("address");
        String b=(String) listItems.get(position).get("goodsname");
        String c=(String) listItems.get(position).get("goodsnum").toString();
        String d=(String) listItems.get(position).get("phone");
        String e=(String) listItems.get(position).get("price").toString();
        String f=(String) listItems.get(position).get("receivername");
        listItemView.button_number.setText(numb);
        listItemView.address.setText(a);
        listItemView.goodsname.setText(b);
       listItemView.number.setText(c);
        listItemView.phone.setText(d);
        listItemView.price.setText("$"+e);
        listItemView.receivername.setText(f);
        return convertView;
    }
}
