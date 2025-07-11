import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class LevelSelectState implements State{

	private int selectID;
	private static final int MAX_OPTIONS = 3; // 選択肢の数
	private String[] menuOptions = {"Level 1", "Level 2", "Level 3"}; // 選択肢のテキスト
	
	public LevelSelectState() {
		selectID = 0;
	}

	@Override
	public State processTimeElapsed(int msec) {
		return this;
	}

	@Override
	public State processKeyTyped(String typed) {
       if(typed.equals("UP")) {
            selectID = (selectID - 1 + MAX_OPTIONS) % MAX_OPTIONS;
        }

        if(typed.equals("DOWN")) {
            selectID = (selectID + 1) % MAX_OPTIONS;
        }
        
       if(typed.equals("BS")) {
        	return new TitleState();
        }
	
		if(typed.equals("ENTER")) {
			return updateState();
		}
		
		return this;
	}
	
	private State updateState() {
		
		if(selectID == 0) {
			return new ReadyState(1,"","");
		}
		else if(selectID == 1) {
			return new ReadyState(2,"","");
		}
		else if(selectID == 2) {
			return new ReadyState(3,"","");
		}
		
		return this;
	}

	@Override
	public void paintComponent(Graphics g, View view) {
        // タイトルを描画
        g.setColor(Color.WHITE);
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        g.setFont(titleFont);
        g.drawString("PVE LevelSelect", 100, 100);
        
        // メニュー選択肢を描画
        Font menuFont = new Font(Font.SANS_SERIF, Font.BOLD, 32);
        g.setFont(menuFont);
        
        for(int i = 0; i < MAX_OPTIONS; i++) {
            // 選択中の項目は色を変える
            if(i == selectID) {
                g.setColor(Color.YELLOW); // 選択中は黄色
                g.drawString("▶ " + menuOptions[i], 150, 200 + i * 50);
            } else {
                g.setColor(Color.WHITE); // 非選択は白色
                g.drawString("  " + menuOptions[i], 150, 200 + i * 50);
            }
        }
        
        // デバッグ用：選択ID表示
        g.setColor(Color.GRAY);
        g.drawString("SelectID: " + selectID, 10, 20);
		
	}

}
