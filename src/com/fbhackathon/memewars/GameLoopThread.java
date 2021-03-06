package com.fbhackathon.memewars;

import android.graphics.Canvas;

public class GameLoopThread extends Thread
{

	static final long FPS = 10;
	private ViewIntroGame view;
	private boolean running = false;

	public GameLoopThread(ViewIntroGame view)
	{
		this.view = view;
	}

	public void setRunning(boolean run)
	{
		running = run;
	}

	@Override
	public void run()
	{
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		while (running)
		{
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try
			{
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder())
				{
					view.onDraw(c);
				}
			} catch (Exception e)
			{

			} finally
			{
				if (c != null)
				{
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);

			try
			{
				if (sleepTime > 0)
				{

					sleep(sleepTime);
				} else
				{
					sleep(10);
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
	}
}
