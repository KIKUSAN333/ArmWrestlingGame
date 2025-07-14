import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ResultState implements State{
	
	private String winnerName;
	
	private Image image;
	private Image backgroundImage;
	
	private int elapsedCount;
	private boolean isEnd;
	private PowerBar powerBar;

	public ResultState(String winnerName,PowerBar powerBar) {
		
		isEnd = false;
		this.winnerName = winnerName;
		this.powerBar = powerBar;
		
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
       image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("arm_00.png"));
       backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
	}

	@Override
	public State processTimeElapsed(int msec) {
		elapsedCount++;
		
		if(elapsedCount >= 100) {
			isEnd = true;
		}

		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		
		if(isEnd && typed.equals(" ")) {
			return new TitleState();
		}
		
		return this;
	}

	@Override
	public void paintComponent(Graphics g, View view) {
		view.drawScaledImage(g,backgroundImage,-150,0,0.35);
		view.drawScaledImage(g,image,100,150,0.5);
		g.setColor(Color.WHITE);
		
		g.drawString(winnerName + " WIN!", 200, 150);
		powerBar.showBar(g);
		
		if(isEnd) {
			g.setColor(Color.WHITE);
			g.drawString("Exit by typed SPACE",200, 450);
		}
		
	}

}
