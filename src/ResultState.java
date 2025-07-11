import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ResultState implements State{
	
	private String winnerName;
	
	private Image image;
	private Image backgroundImage;

	public ResultState(String winnerName) {
		this.winnerName = winnerName;
		
        // 画像を読み込む．画像ファイルは src においておくと bin に自動コピーされる
       image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("arm_00.png"));
       backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
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
		view.drawScaledImage(g,backgroundImage,-150,0,0.35);
		view.drawScaledImage(g,image,100,150,0.5);
		
		g.drawString(winnerName + " WIN!", 200, 150);
		
	}

}
