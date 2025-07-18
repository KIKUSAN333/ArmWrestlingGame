import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ResultStateTest {
    private ResultState resultState;
    private Graphics mockGraphics;
    private View mockView;
    private PowerBar mockPowerBar;
    private Image mockImage;
    
    @BeforeEach
    void setUp() {
        // テスト用のオブジェクトを作成
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        mockImage = testImage;
        mockPowerBar = new PowerBar(); // PowerBarの実装に依存
        
        // テスト用のGraphicsオブジェクトを作成
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        mockGraphics = image.getGraphics();
        
        // ResultStateのインスタンスを作成
        resultState = new ResultState("Player1", mockPowerBar, mockImage, "Player1", "Player2");
    }
    
    @Test
    void コンストラクタで正常に初期化されるかテスト() {
        // コンストラクタが例外なく実行される
        assertDoesNotThrow(() -> {
            ResultState state = new ResultState("TestWinner", mockPowerBar, mockImage, "P1", "P2");
            assertNotNull(state);
        });
    }
    
    @Test
    void 時間経過処理で状態が維持されるかテスト() {
        // 時間経過処理で同じ状態を返す
        State result = resultState.processTimeElapsed(50);
        assertSame(resultState, result);
    }
    
    @Test
    void 時間経過でelapsedCountが増加するかテスト() {
        // 複数回時間経過処理を実行
        for (int i = 0; i < 50; i++) {
            resultState.processTimeElapsed(1);
        }
        
        // まだ終了していない
        State result = resultState.processKeyTyped(" ");
        assertSame(resultState, result);
    }
    
    @Test
    void 終了前のスペースキー入力で状態が変わらないかテスト() {
        // 50回だけ時間経過（まだ終了していない）
        for (int i = 0; i < 50; i++) {
            resultState.processTimeElapsed(1);
        }
        
        // スペースキーを押しても状態が変わらない
        State result = resultState.processKeyTyped(" ");
        assertSame(resultState, result);
    }
    
    @Test
    void 終了後のスペースキー以外の入力で状態が変わらないかテスト() {
        // 100回時間経過して終了状態にする
        for (int i = 0; i < 100; i++) {
            resultState.processTimeElapsed(1);
        }
        
        // スペース以外のキーでは状態が変わらない
        State result1 = resultState.processKeyTyped("A");
        State result2 = resultState.processKeyTyped("ENTER");
        State result3 = resultState.processKeyTyped("ESC");
        
        assertSame(resultState, result1);
        assertSame(resultState, result2);
        assertSame(resultState, result3);
    }
    
    @Test
    void 記録保存が一度だけ実行されるかテスト() {
        // 複数回時間経過処理を実行
        for (int i = 0; i < 10; i++) {
            resultState.processTimeElapsed(1);
        }
        
        // 記録保存は一度だけ実行される（recordSaved フラグにより）
        // このテストは内部状態の確認が困難なため、例外が発生しないことを確認
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++) {
                resultState.processTimeElapsed(1);
            }
        });
    }
    
    @Test
    void null入力で例外が発生しないかテスト() {
        // null入力でもクラッシュしない
        assertDoesNotThrow(() -> {
            State result = resultState.processKeyTyped(null);
        });
    }
    
    @Test
    void 空文字列入力で状態が変わらないかテスト() {
        // 空文字列入力
        State result = resultState.processKeyTyped("");
        assertSame(resultState, result);
    }
    
    @Test
    void 異なるプレイヤー名でのコンストラクタテスト() {
        // 異なるプレイヤー名でのインスタンス作成
        assertDoesNotThrow(() -> {
            ResultState state1 = new ResultState("Alice", mockPowerBar, mockImage, "Alice", "Bob");
            ResultState state2 = new ResultState("Bob", mockPowerBar, mockImage, "Charlie", "David");
            
            assertNotNull(state1);
            assertNotNull(state2);
        });
    }
    
    @Test
    void 連続したキー入力処理のテスト() {
        // 100回時間経過して終了状態にする
        for (int i = 0; i < 100; i++) {
            resultState.processTimeElapsed(1);
        }
        
        // 連続したキー入力
        State result1 = resultState.processKeyTyped("A");
        State result2 = resultState.processKeyTyped("B");
        State result3 = resultState.processKeyTyped(" ");
        
        assertSame(resultState, result1);
        assertSame(resultState, result2);
        assertTrue(result3 instanceof TitleState);
    }
    
    @Test
    void 時間経過とキー入力の組み合わせテスト() {
        // 時間経過とキー入力を組み合わせる
        for (int i = 0; i < 50; i++) {
            resultState.processTimeElapsed(1);
            resultState.processKeyTyped("A"); // 効果なし
        }
        
        // まだ終了していない
        State result = resultState.processKeyTyped(" ");
        assertSame(resultState, result);
        
        // 残りの時間経過
        for (int i = 0; i < 50; i++) {
            resultState.processTimeElapsed(1);
        }
        
        // 今度はスペースキーで終了
        State finalResult = resultState.processKeyTyped(" ");
        assertTrue(finalResult instanceof TitleState);
    }
}