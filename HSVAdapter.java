package com.github.shchurov.horizontalwheelview.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HSVAdapter extends BaseAdapter {
 
    private static final String TAG = "HSV";
 
    private List<Map<String,Object>> lstAvatars;
    private Context context;
    private int layoutWidthPerAvatar;
 
    public HSVAdapter(Context context, int layoutWidthPerAvatar){
        this.context=context;
        this.lstAvatars =new ArrayList<Map<String,Object>>();
        this.layoutWidthPerAvatar = layoutWidthPerAvatar;
    }
    @Override
    public int getCount() {
        return lstAvatars.size();
    }
 
    @Override
    public Map<String,Object> getItem(int location) {
        return lstAvatars.get(location);
    }
 
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
 
    public void addObject(Map<String,Object> map){
        lstAvatars.add(map);
        notifyDataSetChanged();
    }
 
    @Override
    public View getView(int location, View arg1, ViewGroup arg2) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_avatar,null);
        return view;
    }
}