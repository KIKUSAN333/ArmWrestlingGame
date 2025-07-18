import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class BossState implements State{
	
	State state;
	Image backgroundImage;

	public BossState(State state) {
		this.state = state;
		backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("bossBackground.png"));
	}

	@Override
	public State processTimeElapsed(int msec) {
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		if(typed.equals("T")) {
			return state;
		}
		
		return this;
	}

	@Override
	public void paintComponent(Graphics g, View view) {
		
		view.drawScaledImage(g,backgroundImage, -150, 0, 0.35);
		g.drawString("bossState", 100, 100);
		
	}

}
