import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Toolkit;

public class ReadyState implements State{
	
	
	private int startCount;
	private int elapsedCount;
	private Image image;
	private Image backgroundImage;
	
	private String player1Name;
	private String player2Name;
	
	private int enemyLevel;

	public ReadyState(int level,String player1Name,String player2Name) {

		enemyLevel = level;
		
		startCount = 5;
		elapsedCount = 0;
		
		this.player1Name = player1Name;
		this.player2Name = player2Name;
		
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
       image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("arm_00.png"));
       backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
	}

	@Override
	public State processTimeElapsed(int msec) {
		elapsedCount++;
		
		if(elapsedCount >= 50) {
			startCount--;
			elapsedCount = 0;
		}
		
		if(startCount <= 0) {
			return updateState();
		}
		
		return this;
	}

	private State updateState() {
		if(enemyLevel == 0) {
			return new PVPState(player1Name,player2Name);
		}
		return new PVEState(enemyLevel);
	}
	
	@Override
	public State processKeyTyped(String typed) {
		return this;
	}

	@Override
	public void paintComponent(Graphics g, View view) {
		view.drawScaledImage(g,backgroundImage,-150,0,0.35);
		view.drawScaledImage(g,image,100,150,0.5);
		g.setColor(Color.WHITE);
		
		Font font = new Font("Arial", Font.BOLD, 100);
		g.setFont(font);
		g.drawString("" + startCount, 270, 100);
		
		
	}

}
