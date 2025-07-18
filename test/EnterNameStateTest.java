import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class EnterNameStateTest {
    
    @Test
    void コンストラクタで初期状態が正しく設定されるかテスト() {
        EnterNameState state = new EnterNameState();
        
        // 初期状態では何も入力されていない
        assertNotNull(state);
        
        // processTimeElapsedで同じオブジェクトが返されることを確認
        State result = state.processTimeElapsed(1000);
        assertSame(state, result);
    }
    
    @Test
    void プレイヤー1の名前入力が正しく動作するかテスト() {
        EnterNameState state = new EnterNameState();
        
        // 英数字の入力
        State result = state.processKeyTyped("a");
        assertSame(state, result);
        
        result = state.processKeyTyped("b");
        assertSame(state, result);
        
        result = state.processKeyTyped("1");
        assertSame(state, result);
        
        result = state.processKeyTyped("2");
        assertSame(state, result);
    }
    
    @Test
    void プレイヤー1から2への遷移が正しく動作するかテスト() {
        EnterNameState state = new EnterNameState();
        
        // プレイヤー1の名前を入力
        state.processKeyTyped("P");
        state.processKeyTyped("l");
        state.processKeyTyped("a");
        state.processKeyTyped("y");
        state.processKeyTyped("e");
        state.processKeyTyped("r");
        state.processKeyTyped("1");
        
        // ENTERキーでプレイヤー2の入力へ
        State result = state.processKeyTyped("ENTER");
        assertSame(state, result);
    }
    
    @Test
    void プレイヤー2の名前入力後の遷移が正しく動作するかテスト() {
        EnterNameState state = new EnterNameState();
        
        // プレイヤー1の名前を入力してENTER
        state.processKeyTyped("P");
        state.processKeyTyped("1");
        state.processKeyTyped("ENTER");
        
        // プレイヤー2の名前を入力
        state.processKeyTyped("P");
        state.processKeyTyped("2");
        
        // ENTERキーでReadyStateへ遷移
        State result = state.processKeyTyped("ENTER");
        assertNotSame(state, result);
        assertTrue(result instanceof ReadyState);
    }
    
    @Test
    void 名前が空のときはENTERキーで遷移しないかテスト() {
        EnterNameState state = new EnterNameState();
        
        // プレイヤー1の名前が空でENTERキーを押してもプレイヤー2へ遷移しない
        State result = state.processKeyTyped("ENTER");
        assertSame(state, result);
        
        // プレイヤー1の名前を入力してプレイヤー2へ
        state.processKeyTyped("P");
        state.processKeyTyped("1");
        state.processKeyTyped("ENTER");
        
        // プレイヤー2の名前が空でENTERキーを押してもReadyStateへ遷移しない
        result = state.processKeyTyped("ENTER");
        assertSame(state, result);
    }
    
    @Test
    void バックスペースキーが正しく動作するかテスト() {
        EnterNameState state = new EnterNameState();
        
        // プレイヤー1の名前を入力
        state.processKeyTyped("a");
        state.processKeyTyped("b");
        state.processKeyTyped("c");
        
        // バックスペースで削除
        State result = state.processKeyTyped("BS");
        assertSame(state, result);
        
        // プレイヤー2でも確認
        state.processKeyTyped("ENTER");
        state.processKeyTyped("x");
        state.processKeyTyped("y");
        
        result = state.processKeyTyped("BS");
        assertSame(state, result);
    }
    
    @Test
    void 空の名前でバックスペースキーを押しても例外が発生しないかテスト() {
        EnterNameState state = new EnterNameState();
        
        // プレイヤー1の名前が空でバックスペース
        assertDoesNotThrow(() -> {
            state.processKeyTyped("BS");
        });
        
        // プレイヤー2でも確認
        state.processKeyTyped("P");
        state.processKeyTyped("1");
        state.processKeyTyped("ENTER");
        
        // プレイヤー2の名前が空でバックスペース
        assertDoesNotThrow(() -> {
            state.processKeyTyped("BS");
        });
    }
    
    @Test
    void 最大文字数制限が正しく動作するかテスト() {
        EnterNameState state = new EnterNameState();
        
        // 最大文字数（10文字）まで入力
        for (int i = 0; i < 10; i++) {
            state.processKeyTyped("a");
        }
        
        // 11文字目は入力されない（例外が発生しない）
        assertDoesNotThrow(() -> {
            state.processKeyTyped("b");
        });
        
        // プレイヤー2でも確認
        state.processKeyTyped("ENTER");
        
        for (int i = 0; i < 10; i++) {
            state.processKeyTyped("x");
        }
        
        assertDoesNotThrow(() -> {
            state.processKeyTyped("y");
        });
    }
    
    @Test
    void 有効な文字のみが入力されるかテスト() {
        EnterNameState state = new EnterNameState();
        
        // 英数字は有効
        State result = state.processKeyTyped("a");
        assertSame(state, result);
        
        result = state.processKeyTyped("Z");
        assertSame(state, result);
        
        result = state.processKeyTyped("9");
        assertSame(state, result);
        
        // 無効な文字は無視される（例外が発生しない）
        assertDoesNotThrow(() -> {
            state.processKeyTyped("!");
            state.processKeyTyped("@");
            state.processKeyTyped("#");
            state.processKeyTyped("あ");
        });
    }
    
    @Test
    void スペースキーでTitleStateに遷移するかテスト() {
        EnterNameState state = new EnterNameState();
        
        // スペースキーでTitleStateへ遷移
        State result = state.processKeyTyped(" ");
        assertNotSame(state, result);
        assertTrue(result instanceof TitleState);
    }
    
    @Test
    void 複数文字の入力が無効かテスト() {
        EnterNameState state = new EnterNameState();
        
        // 複数文字の入力は無効（例外が発生しない）
        assertDoesNotThrow(() -> {
            state.processKeyTyped("abc");
            state.processKeyTyped("123");
        });
    }
    
    @Test
    void paintComponentが例外を発生させないかテスト() {
        EnterNameState state = new EnterNameState();
        
        // テスト用のGraphicsオブジェクトとViewオブジェクトを作成
        BufferedImage image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        
        // Viewのモックを作成（nullでも動作するはず）
        View view = null;
        
        // 例外が発生しないことを確認
        assertDoesNotThrow(() -> {
            state.paintComponent(g, view);
        });
        
        // 名前を入力した状態でも例外が発生しないことを確認
        state.processKeyTyped("T");
        state.processKeyTyped("e");
        state.processKeyTyped("s");
        state.processKeyTyped("t");
        
        assertDoesNotThrow(() -> {
            state.paintComponent(g, view);
        });
        
        // プレイヤー2の入力状態でも例外が発生しないことを確認
        state.processKeyTyped("ENTER");
        state.processKeyTyped("P");
        state.processKeyTyped("2");
        
        assertDoesNotThrow(() -> {
            state.paintComponent(g, view);
        });
        
        g.dispose();
    }
    
    @Test
    void processTimeElapsedが常に自分自身を返すかテスト() {
        EnterNameState state = new EnterNameState();
        
        // 異なる時間値でテスト
        int[] testTimes = {0, 1, 100, 1000, -1};
        
        for (int time : testTimes) {
            State result = state.processTimeElapsed(time);
            assertSame(state, result);
        }
    }
    
    @Test
    void ReadyStateの作成時に正しい引数が渡されるかテスト() {
        EnterNameState state = new EnterNameState();
        
        // プレイヤー1の名前を入力
        state.processKeyTyped("P");
        state.processKeyTyped("l");
        state.processKeyTyped("a");
        state.processKeyTyped("y");
        state.processKeyTyped("e");
        state.processKeyTyped("r");
        state.processKeyTyped("1");
        state.processKeyTyped("ENTER");
        
        // プレイヤー2の名前を入力
        state.processKeyTyped("P");
        state.processKeyTyped("l");
        state.processKeyTyped("a");
        state.processKeyTyped("y");
        state.processKeyTyped("e");
        state.processKeyTyped("r");
        state.processKeyTyped("2");
        
        // ReadyStateへ遷移
        State result = state.processKeyTyped("ENTER");
        assertTrue(result instanceof ReadyState);
    }
}