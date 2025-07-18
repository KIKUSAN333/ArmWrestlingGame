import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.net.URL;
import java.util.*;

public class RankingState implements State {
    private List<String> records;
    private int displayOffset = 0; // スクロール用のオフセット
    private static final String RECORD_FILE = "game_records.txt";
    private Image backgroundImage;
    
    public RankingState() {
        // 記録を読み込む
        records = loadGameRecords();
        backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("background.jpg"));
    }
    
    /**
     * ゲーム記録をテキストファイルから読み込み
     */
    private List<String> loadGameRecords() {
        List<String> recordList = new ArrayList<>();
        
        try {
            URL textURL = getClass().getResource(RECORD_FILE);
            if (textURL != null) {
                // try-with-resourcesを使用してリソースを自動的に閉じる
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(textURL.openStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        recordList.add(line);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("記録ファイルが見つかりません。");
        }
        
        return recordList;
    }
    
    @Override
    public State processTimeElapsed(int msec) {
        // 特に処理なし
        return this;
    }
    
    @Override
    public State processKeyTyped(String typed) {
        // スペースキーでメニューに戻る
        if (typed.equals(" ")) {
            return new TitleState(); // メニュー画面に戻る
        }
        
        // 上下キーでスクロール（記録が多い場合）
        if (typed.equals("UP") && displayOffset > 0) {
            displayOffset--;
        } else if (typed.equals("DOWN") && displayOffset < Math.max(0, records.size() - 8)) {
            displayOffset++;
        }
        
        return this;
    }
    
    @Override
    public void paintComponent(Graphics g, View view) {
        // 背景色
        g.drawImage(backgroundImage, 0, 0, view);
        
        // タイトル
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.drawString("=== GAME RANKING ===", 100, 50);
        
        // 記録がない場合
        if (records.isEmpty()) {
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("No records found.", 150, 150);
            g.drawString("Play some games to see rankings!", 150, 180);
        } 
        else {
            // 記録を表示
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            int yPos = 100;
            int maxDisplay = Math.min(8, records.size()); // 最大8件表示
            
            for (int i = displayOffset; i < displayOffset + maxDisplay && i < records.size(); i++) {
                String record = records.get(i);
                
                // 順位を表示
                String rankText = String.format("%d. %s", i + 1, record);
                
                g.drawString(rankText, 50, yPos);
                yPos += 30;
            }
        }
    }
}