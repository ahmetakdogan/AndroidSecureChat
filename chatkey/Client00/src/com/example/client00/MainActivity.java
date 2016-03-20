package com.example.client00;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.client00.Client.OnMessageReceived;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts.Intents;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends TabActivity implements TabListener {

	public MyAdapter mAdapter;
	ArrayAdapter<String> adapMessage;

	private ListView mList;
	public ListView mListMessage;
    private ArrayList<String> arrayList;
    public ArrayList<String> arrayL ;
    private Client mClient;
	public Button btnSend;
	public EditText eText;
	public MediaPlayer newUserSound;
    public MediaPlayer newMessSound;
	public  ChatActivity chat;
    public AutoCipher decipher;
	

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   
        arrayList = new ArrayList<String>();
        arrayL = new ArrayList<String>();
        newUserSound = MediaPlayer.create(MainActivity.this,R.raw.uconn);
        newMessSound = MediaPlayer.create(MainActivity.this,R.raw.rec);
        
        TabHost tabMenu = getTabHost();
        TabHost.TabSpec tSpec1;
        TabHost.TabSpec tSpec2;
        
        Intent intent  = new Intent(this,OnlinePersons.class);
        tSpec1 = tabMenu.newTabSpec("").setIndicator("",getResources()
        		                      .getDrawable(R.drawable.simge1)).setContent(R.id.listView1);
        tabMenu.addTab(tSpec1);
        
        Intent intent2 = new Intent(this,ChatActivity.class);
        tSpec2 = tabMenu.newTabSpec("").setIndicator("",getResources()
        		                      .getDrawable(R.drawable.simge2)).setContent(R.id.listView2);
        tabMenu.addTab(tSpec2);
        tabMenu.setCurrentTab(0);  // -----ilk açýldýðýnda seçili olacak olan tab-----////
       // tabMenu.getTabWidget().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70d4ee")));
        //tabMenu.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70d4ee")));
        
        mList = (ListView)findViewById(R.id.listView1);
        mAdapter = new MyAdapter(this, arrayList);
        mList.setAdapter(mAdapter);
        
        mListMessage = (ListView) findViewById(R.id.listView2);
        adapMessage = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayL);
        mListMessage.setAdapter(adapMessage);
      
        //------ servera baðlan------///
        new connectTask().execute("");
        
        final Intent i = new Intent(MainActivity.this, ChatActivity.class);
       
        
	     mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	 
	           @Override
	           public void onItemClick(AdapterView<?> parent, View view,
	                   int position, long id) {  
	        	  
	        	   Object s = mAdapter.getItem(position);
	        	   i.putExtra("name", s.toString().trim());
	        	   startActivity(i);
	        	  
	             //  Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG)
	                   //      .show();
	           
	           }
	       });

	     mListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	 
	           @Override
	           public void onItemClick(AdapterView<?> parent, View view,
	                   int position, long id) {  
	        	  
	        	   Object m = adapMessage.getItem(position);
	        	   String[] nameArray = m.toString().substring(1,m.toString().length()).trim().split(">");
	        	   i.putExtra("name", nameArray[0]);
	        	   startActivity(i);
	        	  
	             //  Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG)
	                   //      .show();
	           
	           }
	       });
       
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);

        MenuInflater myMenu = getMenuInflater();
        myMenu.inflate(R.menu.my_menu, menu);
        
        return true;
	    
	}
	
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	   super.onOptionsItemSelected(item);
	   
	   switch(item.getItemId()){
	   
	   case R.id.hakkimda:
		   Intent i = new Intent(this,Hakkimda.class);  
	       startActivity(i);
	       break;
	   case R.id.exit:
		   System.exit(0);
		   break;
	   }
	   return false;
	}


	public class connectTask extends AsyncTask<String,String,Client> {
   	 
        @Override
        protected Client doInBackground(String... message) {
 
            // Client objesi
            mClient = new Client(new Client.OnMessageReceived() {
                @Override
                //----messageReceived method implemented-----//
                public void messageReceived(String message) {
                    //------messageReceived, onProgressUpdate e atandý-----///  	 	
                 		publishProgress(message);
                 	 
                }
            });
            mClient.run();
 
            return null;
        }
 
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
 
      // -----serverdan gelen mesaj, client ismi ise buraya;
         
            if(values[0].indexOf("#") != -1){
            arrayList.add(" " + values[0].substring(1)); 
            newUserSound.start();
           }
            
            else if(values[0].indexOf("~") != -1){
            //	int index = arrayList.indexOf(values[0].substring(1));
            //	arrayList.remove(index);
            //	mAdapter.notifyDataSetChanged();
            if (arrayList.contains(values[0].substring(1))) {
				arrayList.remove(values[0].substring(1));
				mAdapter.notifyDataSetChanged();
			}
              Toast.makeText(getApplicationContext(), values[0].substring(1) + " ayrýldý!!!", Toast.LENGTH_LONG)
                     .show();
            }
             /// deðilse bu listeye at-------//
            else{
            if(values[0] != null){
              
             String[] resultRec = values[0].substring(1,values[0].length()).split(">");	
             int keyRec = resultRec[0].trim().length() + 8 ;
             ChatActivity.recMessList("<" + resultRec[0] + ">" + decipher.deAuto(resultRec[1].trim(),keyRec));
             arrayL.add("<" + resultRec[0] + ">" + decipher.deAuto(resultRec[1].trim(),keyRec));
             newMessSound.start();
           }	
          }
           //----- adapter'lar güncellenir-----////
            mAdapter.notifyDataSetChanged();
            adapMessage.notifyDataSetChanged();

        }
    }
    public interface OnMessageReceived {
		public void messageReceived(String message);
	}
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
}
