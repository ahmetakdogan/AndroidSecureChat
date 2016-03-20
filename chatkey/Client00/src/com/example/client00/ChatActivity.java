package com.example.client00;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; 
import java.util.Random;

import com.example.client00.MainActivity.connectTask;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract.Colors;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ChatActivity extends Activity{

	public static ArrayAdapter<String> adapter;
	public String sMessage;
	public Button btnSend;
	public EditText editSend;
	public static ListView mListRec;
	public static ArrayList<String> mListe;
	public EditText eText;
	public String name = null;
    public AutoCipher cipher;
    public static String nameKey = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sohbet);
		
		 eText = (EditText) findViewById(R.id.edit);
		 btnSend = (Button) findViewById(R.id.btSend);
		 final MediaPlayer sendSound = MediaPlayer.create(ChatActivity.this,R.raw.send);
	
		 mListRec = (ListView) findViewById(R.id.listRec);
		// mListRec.setBackgroundColor(Color.BLACK);
		 mListe = new ArrayList<String>();
		 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mListe);
	     mListRec.setAdapter(adapter);
	     adapter.notifyDataSetChanged();
	 
	     Bundle extras = getIntent().getExtras();
	     if(extras != null && extras.getString("name") != null){
	    	 name = extras.getString("name");
	     }
	     
	   //  Random r = new Random();
	   //  int sayi= 1+r.nextInt(10000);
	     
	     
         new dataGet().execute("");
         
        btnSend.setOnClickListener(new View.OnClickListener() {
        	
            @Override
            public void onClick(View view) {
            	
                String messageGo = eText.getText().toString();  
                String resultMessage = name + cipher.autoCipher(messageGo.trim(),nameKey.length() + 8);
                
                if(resultMessage != null && resultMessage != name){
                Client.sendMessage(resultMessage);
                sendSound.start(); 
                
                  if(eText.getText().toString() != null){
                  mListe.add("<Ben> " + eText.getText().toString());
                  adapter.notifyDataSetChanged();
                }
                eText.setText("");
              }
            }
        });
  
	
	}
	public static void recMessList (String s){
		
		mListe.add(s);
		mListRec.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
    public class dataGet extends AsyncTask<String,String,String> {
      	 
        @Override
        protected String doInBackground(String... mesj) {
 	
        	if(mesj != null){
        	publishProgress(mesj);
        	}	
            return null;
        }
 
        @Override
        protected void onPostExecute(String result) {
           super.onPostExecute(result);
          // tx.setText(result);
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
 
            if(values[0] != null){
            	recMessList(values[0]);
           }
            adapter.notifyDataSetChanged();
        }   
        
    }
 
 }
