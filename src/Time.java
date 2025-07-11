import java.awt.Color;
import java.awt.Graphics;

public class Time {
	private int time;
	
	public Time() {
		time = 30;
	}
	
	public void updateTime() {
		time--;
	}
	
	public int getTime() {
		return time;
	}
	
	public void showTime(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("" + time, 275, 40);
	}

}
