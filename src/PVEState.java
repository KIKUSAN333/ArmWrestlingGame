import java.awt.Graphics;
import java.awt.Color;

public class PVEState implements State{
	private Model model;
	private Player player;
	private Enemy enemy;
	
	public PVEState(Model m) {
		model = m;
		player = new Player(m,"test",0,"ENTER");
		enemy = new Enemy(m,"test",0,1);

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
		if(typed.equals(player.getKey())) {
			player.doAction();
		}
		
		
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		
		g.setColor(Color.WHITE);
		g.drawString("PlayerPower :" + player.getPower(),100,400);
		g.drawString("EnemyPower  :" + enemy.getPower(),100,500);
	}

}
