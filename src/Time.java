import java.awt.Color;
import java.awt.Graphics;

public class Time {
	private int remainingTime;
	
	public Time() {
		remainingTime = 30;
	}
	
	public void updateTime() {
		remainingTime--;
	}
	
	public int getTime() {
		return remainingTime;
	}
	
	public void showTime(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("" + remainingTime, 275, 40);
	}

}
