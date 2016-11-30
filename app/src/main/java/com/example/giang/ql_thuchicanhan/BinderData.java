package com.example.giang.ql_thuchicanhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Giang on 29/10/2016.
 */
public class BinderData extends BaseAdapter {

    private static ArrayList<Itemlist> itemlsrrayList;

    private Integer[] imgid = {
            R.drawable.chi,
            R.drawable.thu,
            R.drawable.no,
            R.drawable.vay,
            R.drawable.taikhoan,
            R.drawable.danhmuc,
            R.drawable.muasam,
            R.drawable.thongke,
            R.drawable.thietlap,
            R.drawable.huongdan
    };

    private LayoutInflater l_Inflater;

    public BinderData(Context context, ArrayList<Itemlist> results) {
        itemlsrrayList = results;
        l_Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemlsrrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return itemlsrrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.itemoflist, null);
            holder = new ViewHolder();
            holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
            holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_itemName.setText(itemlsrrayList.get(position).getName());
        holder.itemImage.setImageResource(imgid[itemlsrrayList.get(position).getImageNumber() - 1]);
        return convertView;
    }

    static class ViewHolder {
        TextView txt_itemName;
        ImageView itemImage;
    }
}