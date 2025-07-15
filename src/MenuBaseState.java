import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class MenuBaseState implements State {
    protected int selectID;
    protected final int maxOptions;
    protected final String[] menuOptions;
    private Image backgroundImage;
    private Image logoImage;
    
    public MenuBaseState(String[] menuOptions) {
        this.menuOptions = menuOptions;
        this.maxOptions = menuOptions.length;
        this.selectID = 0;
        
        backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("background.jpg"));
        logoImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Logo.png"));
    }
    
    @Override
    public State processTimeElapsed(int msec) {
        return this;
    }
    
    @Override
    public State processKeyTyped(String typed) {
        if(typed.equals("UP")) {
            selectID = (selectID - 1 + maxOptions) % maxOptions;
        }
        if(typed.equals("DOWN")) {
            selectID = (selectID + 1) % maxOptions;
        }
        
        if(typed.equals(" ")) {
            return onBackPressed();
        }
        
        if(typed.equals("ENTER")) {
            return onEnterPressed();
        }
        
        return this;
    }
    
    @Override
    public void paintComponent(Graphics g, View view) {
    	g.drawImage(backgroundImage,0,0,view);
    	view.drawScaledImage(g, logoImage, 0, 150, 0.4);
    	
        // タイトルを描画
        g.setColor(Color.WHITE);
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        g.setFont(titleFont);
        g.drawString(getTitle(), 100, 100);
        
        // メニュー選択肢を描画
        Font menuFont = new Font(Font.SANS_SERIF, Font.BOLD, 32);
        g.setFont(menuFont);
        
        for(int i = 0; i < maxOptions; i++) {
            // 選択中の項目は色を変える
            if(i == selectID) {
                g.setColor(Color.YELLOW); // 選択中は黄色
                g.drawString("▶ " + menuOptions[i], 300, 200 + i * 50);
            } else {
                g.setColor(Color.WHITE); // 非選択は白色
                g.drawString("  " + menuOptions[i], 300, 200 + i * 50);
            }
        }
        
    }
    
    // 各サブクラスで実装する抽象メソッド
    protected abstract String getTitle();
    protected abstract State onEnterPressed();
    protected abstract State onBackPressed();
}