import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class HowToPlayState implements State{
	
	private Image backgroundImage;
	
	

	public HowToPlayState() {
		backgroundImage =  Toolkit.getDefaultToolkit().getImage(getClass().getResource("background.jpg"));
	}

	@Override
	public State processTimeElapsed(int msec) {
		
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
		
		if(typed.equals(" ")) {
			return new TitleState();
		}
		
		return this;
	}

	@Override
	public void paintComponent(Graphics g, View view) {
		g.drawImage(backgroundImage,0,0,view);
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		g.drawString("基本操作説明", 0, 30);
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("ENTER : 決定", 0, 100);
		g.drawString("上矢印キー : 上を選択", 0, 150);
		g.drawString("下矢印キー : 下を選択", 0, 200);
		g.drawString("SPACE : 戻る",0,250);
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		g.drawString("ゲーム操作説明", 0, 350);
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("ENTER : 左プレイヤーパワーゲージを貯める", 0, 400);
		g.drawString("SPACE : 右プレイヤーパワーゲージを貯める", 0, 450);
		
		
	}

}
