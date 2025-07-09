import java.awt.Graphics;
import java.awt.Color;

public class PVEState implements State{
	private Model model;
	private Player player;
	private Enemy enemy;
	
	private int elapsedCount;
	
	public PVEState(Model m) {
		model = m;
		player = new Player(m,"test",0,"ENTER");
		enemy = new Enemy(m,"test",0,1);
		
		elapsedCount = 0;
	}

	@Override
	public State processTimeElapsed(int msec) {
		// TODO 自動生成されたメソッド・スタブ
		elapsedCount++;
		
		//タイマーが呼ばれた回数が一定以上だと
		if(elapsedCount >= 5) {
			enemy.doAction();
			
			//カウントをリセット
			elapsedCount = 0;
		}
		
		
		
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
		g.drawString("Player :" + player.getPower(),0,100);
		g.drawString("Enemy  :" + enemy.getPower(),200,100);
	}

}
