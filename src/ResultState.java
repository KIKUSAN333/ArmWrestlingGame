import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ResultState implements State {
    private String winnerName;
    private String player1Name;
    private String player2Name;
    private Image image;
    private Image backgroundImage;
    private int elapsedCount;
    private boolean isEnd;
    private PowerBar powerBar;
    private boolean recordSaved = false;
    private static final String RECORD_FILE = "game_records.txt";
    
    public ResultState(String winnerName, PowerBar powerBar, Image image, 
                      String player1Name, String player2Name) {
        isEnd = false;
        this.winnerName = winnerName;
        this.powerBar = powerBar;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        
        // 画像を読み込む
        this.image = image;
        backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pxfuel.jpg"));
    }
    
    @Override
    public State processTimeElapsed(int msec) {
        elapsedCount++;
        
        // 記録をまだ保存していない場合は保存
        if (!recordSaved) {
            saveGameRecord(player1Name, player2Name, winnerName);
            recordSaved = true;
        }
        
        if (elapsedCount >= 100) {
            isEnd = true;
        }
        return this;
    }
    
    @Override
    public State processKeyTyped(String typed) {
        if (isEnd && typed.equals(" ")) {
            return new TitleState();
        }
        return this;
    }
    
    @Override
    public void paintComponent(Graphics g, View view) {
        view.drawScaledImage(g, backgroundImage, -150, 0, 0.35);
        
        // アーム画像
        g.drawImage(image, -70, 0, view);
        
        g.setColor(Color.WHITE);
        
        g.drawString(winnerName + " WIN!", 200, 150);
        powerBar.showBar(g);
        
        if (isEnd) {
            g.setColor(Color.WHITE);
            g.drawString("Exit by typed SPACE", 200, 450);
        }
    }
    
    /**
     * ゲーム記録をテキストファイルに保存
     */
    private void saveGameRecord(String player1Name, String player2Name, String winner) {
        try {
            // 現在の日時を取得
            String currentDateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            // 記録文字列を作成
            String recordLine = String.format("%s vs %s | Winner: %s | %s", 
                                             player1Name, player2Name, winner, currentDateTime);
            
            // 既存の記録を読み込む
            List<String> records = loadExistingRecords();
            
            // 新しい記録を先頭に追加
            records.add(0, recordLine);
            
            // 記録数を10件に制限
            if (records.size() > 10) {
                records = records.subList(0, 10);
            }
            
            // ファイルに書き込み
            writeRecordsToFile(records);
            
            System.out.println("ゲーム記録を保存しました: " + recordLine);
            
        } catch (Exception e) {
            System.err.println("ゲーム記録の処理に失敗しました: " + e.getMessage());
        }
    }
    
    /**
     * 既存の記録をファイルから読み込む
     */
    private List<String> loadExistingRecords() {
        List<String> records = new ArrayList<>();
        
        try {
            URL textURL = getClass().getResource(RECORD_FILE);
            if (textURL != null) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(textURL.openStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        records.add(line);
                    }
                }
            }
        } catch (Exception e) {
            // ファイルが存在しない場合は新規作成
            System.out.println("記録ファイルが見つかりません。新規作成します。");
        }
        
        return records;
    }
    
    /**
     * 記録をファイルに書き込む
     */
    private void writeRecordsToFile(List<String> records) throws IOException {
        URL textURL = getClass().getResource(RECORD_FILE);
        String filePath = textURL != null ? textURL.getPath() : RECORD_FILE;
        
        try (PrintStream out = new PrintStream(new FileOutputStream(filePath))) {
            for (String gameRecord : records) {
                out.println(gameRecord);
            }
        } catch (Exception e) {
            System.err.println("ゲーム記録の保存に失敗しました: " + e.getMessage());
            throw new IOException("ファイル書き込みエラー", e);
        }
    }
}