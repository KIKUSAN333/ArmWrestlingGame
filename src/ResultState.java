import java.awt.Graphics;

public class ResultState implements State{
	
	private String winnerName;

	public ResultState(String winnerName) {
		this.winnerName = winnerName;
	}

	@Override
	public State processTimeElapsed(int msec) {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
	public void paintComponent(Graphics g, View view) {
		
		g.drawString(winnerName + " WIN!", 300, 150);
		
	}

}
