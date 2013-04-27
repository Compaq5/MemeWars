package com.fbhackathon.memewars;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private List<Bitmap> backgrounds = new ArrayList<Bitmap>();
	
	public View mainView;
	public FrameLayout frameLayout;
	
	public int width;
	public int height;
	
	protected PowerManager.WakeLock mWakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // Set the hardware buttons to control the music
        //this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag"); // always On.
        
        Display display = ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();        
    	width =  display.getWidth();
		height =  display.getHeight();
        
        loadBackground();
		
		mainView = new ViewIntroGame(this, backgrounds); 
        frameLayout = new FrameLayout(this);
        frameLayout.addView(mainView);
        //View view = getAdvert();
        //if(view != null) frameLayout.addView(view);
        setContentView(frameLayout); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
        
		return true;
	}
	
	public void onPause()
    {
        super.onPause();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //!FacebookAgent.getFacebookAgent(this).logout();
        //stopIntroAnimation();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    }
    
    @Override
    public void onDestroy()
    {
        this.mWakeLock.release();
        super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
    	// do something on back.
    	return;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    	if(requestCode == 1)
    	{
    		
    	}
    	else if(requestCode == 2)
    	{
    	}
    }
	
	 //************LOADS***********
    public void loadBackground()
    {
    	backgrounds.add(null);
		try {
			Bitmap bmp =  backgrounds.get(0);
	    	backgrounds.set(0, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background1), width, height, false));
			if (bmp != null)
			{
				bmp.recycle();
			}
		} catch (Exception e) {
			programEnd(e);
		}
    }
    
    public void programEnd(Exception e)
    {
    	e.printStackTrace();
		Toast.makeText(getBaseContext(), "Out of memory!", Toast.LENGTH_LONG).show();
		sleep(1000);
		finish();
		return;
    }
    
    public void sleep(long ms)
    {
    	try
		{
			Thread.sleep(ms);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
