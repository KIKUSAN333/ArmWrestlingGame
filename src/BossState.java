import java.awt.Graphics;

public class BossState implements State{
	
	State state;

	public BossState(State state) {
		this.state = state;
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
		g.drawString("bossState", 100, 100);
		
	}

}
