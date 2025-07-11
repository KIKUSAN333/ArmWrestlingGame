import java.awt.Color;
import java.awt.Graphics;

public class ReadyState implements State{
	
	private Model model; 
	
	private int startCount;
	private int elapsedCount;

	public ReadyState(Model m) {
		model = m;

		startCount = 3;
		elapsedCount = 0;
	}

	@Override
	public State processTimeElapsed(int msec) {
		elapsedCount++;
		
		if(elapsedCount >= 50) {
			startCount--;
			elapsedCount = 0;
		}
		
		if(startCount <= 0) {
			return new PVEState(model);
		}
		
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
	public void paintComponent(Graphics g, View view) {
		
		g.setColor(Color.WHITE);
		g.drawString("" + startCount, 250, 400);
		
		
	}

}
