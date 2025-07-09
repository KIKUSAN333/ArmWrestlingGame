import java.awt.Graphics;
import java.awt.Color;

public class PVEState implements State{
	private Model model;
	private Player player1;
	private Character player2;
	
	public PVEState(Model m) {
		model = m;
		//Player型にキャストする
		Character character1 = model.getCharacter1();
		player1 = (Player) character1;
		
		
		player2 = model.getCharacter2();
	}

	@Override
	public State processTimeElapsed(int msec) {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		// TODO 自動生成されたメソッド・スタブ
		

		//入力したキーが対応するキーであった場合
		if(typed.equals(player1.getKey())) {
			player1.doAction();
		}
		
		
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		
		g.setColor(Color.WHITE);
		if(player1 != null)
			g.drawString("PlayerPower :" + player1.getPower(),100,400);
	}

}
