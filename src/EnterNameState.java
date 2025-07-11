import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class EnterNameState implements State{

    private String player1Name;
    private String player2Name;
    private int currentPlayer; // 0: player1, 1: player2
    private final int MAX_NAME_LENGTH = 10; // 名前の最大長

    public EnterNameState() {
        player1Name = "";
        player2Name = "";
        currentPlayer = 0;
    }

    @Override
    public State processTimeElapsed(int msec) {
        // TODO 自動生成されたメソッド・スタブ
        return this;
    }

    @Override
    public State processKeyTyped(String typed) {
        // エンターキーで次のプレイヤーまたは完了
        if(typed.equals("ENTER")) {
            if(currentPlayer == 0 && !player1Name.isEmpty()) {
                currentPlayer = 1; // プレイヤー2の入力に移る
            } else if(currentPlayer == 1 && !player2Name.isEmpty()) {
                // 両方の名前が入力されたら次のステートに移行
                return new ReadyState(0,player1Name,player2Name); // または適切な次のステート
            }
        }
        
        // バックスペースで文字削除
        else if(typed.equals("BS")) {
            if(currentPlayer == 0 && player1Name.length() > 0) {
                player1Name = player1Name.substring(0, player1Name.length() - 1);
            } else if(currentPlayer == 1 && player2Name.length() > 0) {
                player2Name = player2Name.substring(0, player2Name.length() - 1);
            }
        }
        
        // 通常の文字入力
        else if(typed.length() == 1) {
            char c = typed.charAt(0);
            // 英数字と一部の記号のみ許可
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || 
               (c >= '0' && c <= '9') || c == '_' || c == '-') {
                if(currentPlayer == 0 && player1Name.length() < MAX_NAME_LENGTH) {
                    player1Name += c;
                } else if(currentPlayer == 1 && player2Name.length() < MAX_NAME_LENGTH) {
                    player2Name += c;
                }
            }
        }
        
        return this;
    }

    @Override
    public void paintComponent(Graphics g, View view) {
        // 背景色設定
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        
        // タイトル表示
        g.setColor(Color.WHITE);
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        g.setFont(titleFont);
        g.drawString("名前を入力してください", 200, 100);
        
        // 通常のフォント設定
        Font normalFont = new Font("Arial", Font.PLAIN, 18);
        g.setFont(normalFont);
        
        // プレイヤー1の名前入力
        g.setColor(currentPlayer == 0 ? Color.YELLOW : Color.WHITE);
        g.drawString("Player 1: " + player1Name, 100, 200);
        if(currentPlayer == 0) {
            g.drawString("_", 100 + g.getFontMetrics().stringWidth("Player 1: " + player1Name), 200);
        }
        
        // プレイヤー2の名前入力
        g.setColor(currentPlayer == 1 ? Color.YELLOW : Color.WHITE);
        g.drawString("Player 2: " + player2Name, 100, 250);
        if(currentPlayer == 1) {
            g.drawString("_", 100 + g.getFontMetrics().stringWidth("Player 2: " + player2Name), 250);
        }
        
        // 操作説明
        g.setColor(Color.GRAY);
        Font smallFont = new Font("Arial", Font.PLAIN, 14);
        g.setFont(smallFont);
        g.drawString("ENTERキーで次へ", 100, 350);
        g.drawString("BACKSPACEキーで削除", 100, 370);
        g.drawString("英数字、_、-が入力可能", 100, 390);
        g.drawString("最大" + MAX_NAME_LENGTH + "文字", 100, 410);
    }
    
}