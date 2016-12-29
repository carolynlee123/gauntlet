package puzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang3.time.StopWatch;

public class TimerPanel extends JLabel implements Runnable{
	long startTime;
	int minute;
	final StopWatch stopWatch;
	boolean done;
	public void initializeTime() {
		Calendar cal = Calendar.getInstance();
		
		startTime = cal.getTimeInMillis();
		startTime = System.currentTimeMillis();
	}
	public TimerPanel() {
		stopWatch = new StopWatch();
	}
	public void start() {
		stopWatch.start();
		new Thread(this).start();
	}
	private String getTimeAsString(int minute, int second) {
		String time = "";
		if (minute < 10) {
			time += "0";
		}
		time += minute;
		time += ":";
		if (second < 10) {
			time += "0";
		}
		time += second;
		return time;
	}
	
	public void pauseTimer() {
		if (stopWatch.isSuspended()) {
			return;
		}
		stopWatch.suspend();
	}
	public void unPauseTimer() {
		stopWatch.resume();
	}
	public String getTime() {
		long time = stopWatch.getTime();
		int second = (int) ((time/ 1000) % 60);
		int minute = (int) ((time/1000) / 60 ) % 60;
		String strTime = getTimeAsString(minute,second);
		return strTime;
	}
	public int getScoreTime() {
		long time = stopWatch.getTime();
		int second = (int) ((time/ 1000) % 60);
		int minute = (int) ((time/1000) / 60 ) % 60;
		int totalSecs = (minute * 60) + second;
		return totalSecs;
		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		/*Calendar cal = Calendar.getInstance();
		//long newTime = cal.getTimeInMillis();
		long newTime = System.currentTimeMillis();
		newTime = newTime - startTime;
		int second = (int) ((newTime / 1000) % 60);
		int minute = (int) ((newTime /1000) / 60) % 60;
		System.out.println("Here updating the time as " + minute + " " + second);
		String strTime = getTimeAsString(minute, second);*/
		//System.out.println(strTime);
		//this.setText(strTime);
		long time = stopWatch.getTime();
		int second = (int) ((time / 1000) % 60);
		int minute = (int) ((time /1000) / 60) % 60;
		String strTime = getTimeAsString(minute, second);
		this.setText(strTime);
		//this.setFont;
		this.setForeground(Color.white);
	}
	@Override
	public void run() {
		while (!done) {
			this.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				System.out.println("IE: "+ ie.getMessage());
			}
		}	
	}
	public void shutDown() {
		done = true;
		
	}
}

