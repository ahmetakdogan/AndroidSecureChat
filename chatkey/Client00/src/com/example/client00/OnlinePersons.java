package com.example.client00;

import org.w3c.dom.Text;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OnlinePersons extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kisiler);
		Typeface font;
		font = Typeface.createFromAsset(getResources().getAssets(), "AlexBrush-Regular");
		 
		TextView tx = (TextView) findViewById(R.id.textView1);
		tx.setTypeface(font);
		
	}
}
