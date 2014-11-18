package com.example.mymp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements OnClickListener  {
	
	   public TextView songName,startTimeField,endTimeField;
	   private MediaPlayer mediaPlayer;
	   private double startTime = 0;
	   private double finalTime = 0;
	   private Handler myHandler = new Handler();
	   private int forwardTime = 5000; 
	   private int backwardTime = 5000;
	   private SeekBar seekbar;
	   private ImageButton playButton,pauseButton,rewindButton,fwdButton;
	   public static int oneTimeOnly = 0;
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);
	      
	      songName = (TextView)findViewById(R.id.textView3);
	      startTimeField =(TextView)findViewById(R.id.textView1);
	      endTimeField =(TextView)findViewById(R.id.textView2);
	      seekbar = (SeekBar)findViewById(R.id.seekBar1);
	      
	      playButton = (ImageButton)findViewById(R.id.imageButton2);
	      pauseButton = (ImageButton)findViewById(R.id.imageButton3);
	      rewindButton = (ImageButton)findViewById(R.id.imageButton4);
	      fwdButton = (ImageButton)findViewById(R.id.imageButton1);

	      songName.setText("titli.mp3");
	      
	      mediaPlayer = MediaPlayer.create(this, R.raw.titli);
	      seekbar.setClickable(false);
	      pauseButton.setEnabled(false);
	      

	      playButton.setOnClickListener(this);
	      pauseButton.setOnClickListener(this);
	      rewindButton.setOnClickListener(this);
	      fwdButton.setOnClickListener(this);

	   }
	   

	   public void play()
	   {
	   Toast.makeText(this, "Playing sound",  Toast.LENGTH_SHORT).show();
	      mediaPlayer.start();
	      finalTime = mediaPlayer.getDuration();
	      startTime = mediaPlayer.getCurrentPosition();
	      if(oneTimeOnly == 0){
	         seekbar.setMax((int) finalTime);
	         oneTimeOnly = 1;
	      } 

	      endTimeField.setText(String.format("%d min, %d sec", 
	         TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
	         TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - 
	         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	         toMinutes((long) finalTime)))
	      );
	      startTimeField.setText(String.format("%d min, %d sec", 
	         TimeUnit.MILLISECONDS.toMinutes((long) startTime),
	         TimeUnit.MILLISECONDS.toSeconds((long) startTime) - 
	         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	         toMinutes((long) startTime)))
	      );
	      seekbar.setProgress((int)startTime);
	      myHandler.postDelayed(UpdateSongTime,100);
	      pauseButton.setEnabled(true);
	      playButton.setEnabled(false);
	   }

	   private Runnable UpdateSongTime = new Runnable() {
	      public void run() {
	         startTime = mediaPlayer.getCurrentPosition();
	         startTimeField.setText(String.format("%d min, %d sec", 
	            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
	            TimeUnit.MILLISECONDS.toSeconds((long) startTime) - 
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	            toMinutes((long) startTime)))
	         );
	         seekbar.setProgress((int)startTime);
	         myHandler.postDelayed(this, 100);
	      }
	   };
	   public void pause(){
	      Toast.makeText(getApplicationContext(), "Pausing sound", 
	      Toast.LENGTH_SHORT).show();

	      mediaPlayer.pause();
	      pauseButton.setEnabled(false);
	      playButton.setEnabled(true);
	   }	
	   public void forward(){
	      int temp = (int)startTime;
	      if((temp+forwardTime)<=finalTime){
	         startTime = startTime + forwardTime;
	         mediaPlayer.seekTo((int) startTime);
	      }
	      else{
	         Toast.makeText(getApplicationContext(), 
	         "Cannot jump forward 5 seconds", 
	         Toast.LENGTH_SHORT).show();
	      }

	   }
	   public void rewind(){
	      int temp = (int)startTime;
	      if((temp-backwardTime)>0){
	         startTime = startTime - backwardTime;
	         mediaPlayer.seekTo((int) startTime);
	      }
	      else{
	         Toast.makeText(getApplicationContext(), 
	         "Cannot jump backward 5 seconds",
	         Toast.LENGTH_SHORT).show();
	      }

	   }

	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	   // Inflate the menu; this adds items to the action bar if it is present.
	   getMenuInflater().inflate(R.menu.activity_main, menu);
	   return true;
	   }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.imageButton2:
				play();
			break;
			
		case R.id.imageButton3:
			pause();
			break;
		case R.id.imageButton4:
			rewind();
			break;
			
		case R.id.imageButton1:
			forward();
			break;
		}
	}

}

	
	