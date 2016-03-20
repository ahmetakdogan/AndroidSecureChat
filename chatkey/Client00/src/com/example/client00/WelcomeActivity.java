package com.example.client00;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class WelcomeActivity extends Activity{

	
	private ImageView connect;
	private EditText ipAdress;
	private EditText clientName;
	
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
		   // TODO Auto-generated method stub
		   super.onCreate(savedInstanceState);
		   setContentView(R.layout.activity_welcome);
		   
		   
		   ipAdress = (EditText) findViewById(R.id.editIp);
		   clientName = (EditText) findViewById(R.id.editName);
		   connect = (ImageView)findViewById(R.id.imageConnect);
		   final MediaPlayer warningSound = MediaPlayer.create(WelcomeActivity.this,R.raw.warning);
		   final MediaPlayer connectSound = MediaPlayer.create(WelcomeActivity.this,R.raw.connect);
		   
		 
		   connect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			if(ipAdress.getText().toString().trim().length()> 0 && clientName.getText().toString().trim().length() > 0){
			   if(clientName.getText().toString().trim().startsWith("#")){
				   Toast.makeText(getApplicationContext(),"Kullanýcý ismi '#' ile baþlayamaz!!!",
								                          Toast.LENGTH_LONG).show();
				   warningSound.start();
				}
				else{
				  Client.name = clientName.getText().toString();
				  Client.SERVERIP = ipAdress.getText().toString();
				  Intent i = new Intent(getBaseContext(), MainActivity.class);
				  startActivity(i);
				  connectSound.start();
				}
			}
			else Toast.makeText(getApplicationContext(),"Lütfen server IP adresini ve kullanýcý adýný giriniz:", 
						                                    Toast.LENGTH_LONG).show();
			warningSound.start();
			}
		});
	  }
	  
}
