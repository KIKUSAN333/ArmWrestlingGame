import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Toolkit;

public class ReadyState implements State{
	
	private Model model; 
	
	private int startCount;
	private int elapsedCount;
	private Image image;
	private Image backgroundImage;

	public ReadyState(Model m) {
		model = m;

		startCount = 5;
		elapsedCount = 0;
		
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
		view.drawScaledImage(g,backgroundImage,-150,0,0.35);
		view.drawScaledImage(g,image,100,150,0.5);
		g.setColor(Color.WHITE);
		
		Font font = new Font("Arial", Font.BOLD, 100);
		g.setFont(font);
		g.drawString("" + startCount, 270, 100);
		
		
	}

}
