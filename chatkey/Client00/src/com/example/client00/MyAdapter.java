package com.example.client00;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	 private LayoutInflater mInflater;
	 public  List<String>   mPersonList;
	 
	 public MyAdapter(Activity activity, List persons){
		 
		 //XML'i alýp View'a çevirecek inflater
	        mInflater = (LayoutInflater) activity.getSystemService(
	                Context.LAYOUT_INFLATER_SERVICE);
	        //gösterilecek liste
	        mPersonList = persons;
		 
	 }
	 
	@Override
	public int getCount() {

		return mPersonList.size();
	}

	@Override
	public Object getItem(int position) {
		return mPersonList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView;
		 
        rowView = mInflater.inflate(R.layout.online, null);
        TextView textView = (TextView) rowView.findViewById(R.id.textView1); 
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
 
        textView.setText(mPersonList.get(position));
        imageView.setImageResource(R.drawable.kisi);
		return rowView;
	}

}
