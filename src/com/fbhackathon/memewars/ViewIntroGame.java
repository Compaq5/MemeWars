package com.fbhackathon.memewars;


import java.util.Date;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class ViewIntroGame extends SurfaceView {
	
	final Random myRandom = new Random();
	
	private List<Bitmap> backgrounds;
	
	private boolean loaded = false;
	
	private MainActivity activity;
	private SurfaceHolder holder;
    private GameLoopThread introGameLoopThread;
    private ViewIntroGame introGameView;
    
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);


	public ViewIntroGame(MainActivity context, List<Bitmap> backgrounds) {
		super(context);
		
		this.activity = context;
		this.backgrounds = backgrounds;
		
		int tsize;
		
		paint.setARGB(255, 220, 180, 50);
		paint.setFakeBoldText(true);
		paint.setTextSize(10);
		paint.setTextAlign(Paint.Align.RIGHT);
		
		
		introGameLoopThread = new GameLoopThread(this);
		introGameView = this;
		holder = getHolder(); /** filed... */
		holder.addCallback(new Callback() { /** annonyimus Inner type (listener). Rabis callback, ker ne ves, kdaj bo holder pripravljen.*/

			public void surfaceDestroyed(SurfaceHolder holder) 
			{
				// we have to tell thread to shut down & wait for it to finish, or else
		        // it might touch the Surface after we return and explode
		        boolean retry = true;
		        introGameLoopThread.setRunning(false);
		        while (retry) {
		            try {
		            	introGameLoopThread.join();
		                retry = false;
		            } catch (InterruptedException e) {
		            	e.printStackTrace();
		            }
		        }
			}
			
			public void surfaceCreated(SurfaceHolder holder) {				
				// added fix -->
			     if(introGameLoopThread.getState() == Thread.State.TERMINATED){
			    	 introGameLoopThread = new GameLoopThread(introGameView); //!!! ne vem kaj klicat namesto this
			    	 introGameLoopThread.setRunning(true);
			    	 introGameLoopThread.start();
			// <-- added fix
			     }else {
			    	 introGameLoopThread.setRunning(true);
			    	 introGameLoopThread.start();
			     }
			}
			
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
				// TODO Auto-generated method stub

			}
		});	
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		
		canvas.drawBitmap(backgrounds.get(0), 0, 0, null);
		//drawAndUpdate(activity.introKanglerGovoriSprite, canvas);
		
	}
	
	//public void drawAndUpdate(Sprite sprite, Canvas canvas)
	//{
	//	sprite.onDraw(canvas);
	//	sprite.onUpdate();
	//}
	
	
	public void drawResults(Canvas canvas)
	{
		//canvas.drawText("Nivo 1: " + activity.slap.getSumPointsL1() + "   Skupaj: " + activity.slap.getSumPoints(), activity.width-5, activity.height-10, paint);
	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		//backGroundScaled = Bitmap.createScaledBitmap(backGround, w, h, false);
	}
}
