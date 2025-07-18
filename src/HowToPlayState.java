import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class HowToPlayState implements State{
	
	private Image backgroundImage;
	private Font normalFont;
	private Font titleFont;
	private Font bigFont;
	

	public HowToPlayState() {
		backgroundImage =  Toolkit.getDefaultToolkit().getImage(getClass().getResource("background.jpg"));
		
		titleFont = new Font("Arial", Font.BOLD, 32);
		normalFont = new Font("Arial", Font.PLAIN, 16);
		bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 32);
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
		
        // タイトル
        g.setColor(Color.WHITE);
        g.setFont(titleFont);
        g.drawString("=== HOW TO PLAY ===", 100, 50);
        
		
       g.setFont(normalFont);
		g.drawString("ENTER : 決定", 0, 100);
		g.drawString("上矢印キー : 上を選択", 0, 150);
		g.drawString("下矢印キー : 下を選択", 0, 200);
		g.drawString("SPACE : 戻る",0,250);
		
		g.setFont(bigFont);
		g.drawString("ゲーム操作", 0, 350);
		
        g.setFont(normalFont);
		g.drawString("ENTER : 左プレイヤーパワーゲージを貯める", 0, 400);
		g.drawString("SPACE : 右プレイヤーパワーゲージを貯める", 0, 450);
		
		
	}

}
