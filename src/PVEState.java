import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

public class PVEState implements State{
	private Player player;
	private Enemy enemy;
	private PowerBar powerBar;
	private Time time;
	private Image image;
	private Image backgroundImage;
	
	private int elapsedCount;

	
	public PVEState(int level) {
		player = new Player("test",1,"ENTER");
		enemy = new Enemy("enemy",1,level);
		powerBar = new PowerBar();
		time = new Time();
		
		elapsedCount = 0;
		
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
       image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("arm_00.png"));
       backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
		
	}
	

	private State updateState(String typed) {

	    
	    // ゲーム終了条件をチェック
	    String winner = getWinner();
	    
	    if (winner != null) {
	        return new ResultState(winner,powerBar);
	    }
	    
	    // ボス戦への移行
	    if (typed.equals("T")) {
	        return new BossState(this);
	    }
	    
	    return this;
	}
	
	private String getWinner() {
	    double powerPercent = powerBar.getCurrentBarPercent();
		
	    // パワーバーが満タンまたは空の場合
	    if (powerPercent >= 1.0) {
	        return player.getName();
	    }
	    if (powerPercent <= 0.0) {
	        return enemy.getName();
	    }
	    
	    // 時間切れの場合パワーが大きい方が返る
	    if (time.getTime() <= 0) {
	        return powerPercent >= 0.5 ? player.getName() : enemy.getName();
	    }
	    
	    return null; // ゲーム続行
	}

	@Override
	public State processTimeElapsed(int msec) {
		elapsedCount++;
		
		//1秒経過の場合
		if(elapsedCount >= 100) {
			time.updateTime();
			
			//リセット
			elapsedCount = 0;
		}
		
		enemy.doAction();
		powerBar.updateBar(player.getPower(), enemy.getPower());
		
		//CPUがピンチになったら
		if(powerBar.getCurrentBarPercent() >= 0.7 && !enemy.getIsPowerUp()) {
			enemy.basePowerUp();
		}
		
		//プレイヤーがピンチになったら
		if(powerBar.getCurrentBarPercent() <= 0.3 && !player.getIsPowerUp()) {
			player.basePowerUp();
		}
		
		
		return updateState("");
	}

	@Override
	public State processKeyTyped(String typed) {

		//入力したキーが対応するキーであった場合
		if(typed.equals(player.getKey())) {
			player.doAction();
		}
		
		powerBar.updateBar(player.getPower(), enemy.getPower());
		
		State currentState = updateState(typed);
		return currentState;
	}

	@Override
	public void paintComponent(Graphics g,View view) {
		view.drawScaledImage(g,backgroundImage,-150,0,0.35);
		
		
		g.setColor(Color.WHITE);
		g.drawString("Player :" + player.getPower(),0,100);
		g.drawString("Enemy  :" + enemy.getPower(),200,100);
		
	    view.drawScaledImage(g,image,100,150,0.5);

	    
		powerBar.showBar(g);
		time.showTime(g);
	}
	


}
