import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class EnterNameState implements State{

    private String player1Name;
    private String player2Name;
    private int currentPlayer; // 0: player1, 1: player2
    private static final int MAX_NAME_LENGTH = 10; // 名前の最大長

    public EnterNameState() {
        player1Name = "";
        player2Name = "";
        currentPlayer = 0;
    }

    @Override
    public State processTimeElapsed(int msec) {
        return this;
    }

    @Override
    public State processKeyTyped(String typed) {
        // エンターキーの処理
        if (typed.equals("ENTER")) {
            return handleEnterKey();
        }
        
        // バックスペースの処理
        if (typed.equals("BS")) {
            handleBackspace();
            return this;
        }
        
        // 通常の文字入力の処理
        if (isValidCharacter(typed)) {
            addCharacterToCurrentPlayer(typed);
        }
        
        return this;
    }

    /**
     * エンターキー押下時の処理
     */
    private State handleEnterKey() {
        if (currentPlayer == 0 && !player1Name.isEmpty()) {
            // プレイヤー1の入力完了、プレイヤー2の入力へ
            currentPlayer = 1;
            return this;
        }
        
        if (currentPlayer == 1 && !player2Name.isEmpty()) {
            // 両プレイヤーの入力完了、次のステートへ
            return new ReadyState(0, player1Name, player2Name);
        }
        
        return this;
    }

    /**
     * バックスペース処理
     */
    private void handleBackspace() {
        if (currentPlayer == 0 && player1Name.length() > 0) {
            player1Name = player1Name.substring(0, player1Name.length() - 1);
        } else if (currentPlayer == 1 && player2Name.length() > 0) {
            player2Name = player2Name.substring(0, player2Name.length() - 1);
        }
    }

    /**
     * 入力文字が有効かどうかを判定
     * 英数字、アンダースコア、ハイフン、日本語文字を許可
     */
    private boolean isValidCharacter(String typed) {
        if (typed.length() != 1) {
            return false;
        }
        
        char c = typed.charAt(0);
        
        // 英数字をチェック
        if (isAlphanumericOrSymbol(c)) {
            return true;
        }
        
        
        return false;
    }

    /**
     * 英数字と許可された記号かどうかを判定
     */
    private boolean isAlphanumericOrSymbol(char c) {
        return (c >= 'a' && c <= 'z') || 
               (c >= 'A' && c <= 'Z') || 
               (c >= '0' && c <= '9');
    }

    /**
     * 現在のプレイヤーに文字を追加
     */
    private void addCharacterToCurrentPlayer(String typed) {
        if (currentPlayer == 0 && player1Name.length() < MAX_NAME_LENGTH) {
            player1Name += typed;
        } else if (currentPlayer == 1 && player2Name.length() < MAX_NAME_LENGTH) {
            player2Name += typed;
        }
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
        g.drawString("最大" + MAX_NAME_LENGTH + "文字", 100, 410);
    }
    
}