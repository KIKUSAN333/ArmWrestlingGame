import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

public class ModelTest {

	// 下記のテスト関数はひな型用なので削除してよい
	
    @Test
    void モデルの管理する時刻データが時間経過とともに増えるかテスト() {
        Model model = new Model();
        
        // 時間経過イベントが発生すると時刻が１増加
        int time = model.getTime();
        model.processTimeElapsed(1);
        assertEquals(time + 1, model.getTime());

        // その他のイベントでは増えない
        model.processKeyTyped("a");
        assertEquals(time + 1, model.getTime());
        assertNotEquals(time + 2, model.getTime());
    }

    @Test
    void モデルがタイプされた文字を保持しているかテスト() {
        Model model = new Model();
        View view = model.getView();
        Controller controller = model.getController();
        // View オブジェクトが存在すること
        assertNotEquals(null, view);
        // Controler オブジェクトが存在すること
        assertNotEquals(null, controller);
        
        // a キーが押されたイベントを発生
        controller.keyPressed(new KeyEvent(view, 1, 1, 0, KeyEvent.VK_A, 'a'));
        // モデルがその文字を保持しているか確認
        assertEquals("a", model.getTypedChar());
    }
    
    @Test
    void ESCキーで同時押しモードが切り替えられるかテスト() {
        Model model = new Model();
        View view = model.getView();
        Controller controller = model.getController();
        
        // 初期状態は同時押し不許可
        assertEquals(false, model.getEnableKeyRollover());
        // ESC を押下すると同時押し許可モードへ移行
        controller.keyPressed(new KeyEvent(view, 1, 1, 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED));
        assertEquals(true, model.getEnableKeyRollover());
    }
    
    @Test
    void キーで同時押しを検出できるかテスト() {
        Model model = new Model();
        View view = model.getView();
        Controller controller = model.getController();
        
        // ESC を押下して同時押し許可モードに
        controller.keyPressed(new KeyEvent(view, 1, 1, 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED));
        // a, b の同時押しを検出できるか
        KeyEvent a = new KeyEvent(view, 1, 1, 0, KeyEvent.VK_A, 'a');
        KeyEvent b = new KeyEvent(view, 1, 1, 0, KeyEvent.VK_B, 'b');
        controller.keyPressed(a);
        controller.keyPressed(b);       
        assertEquals("a+b", model.getTypedChar());
        controller.keyReleased(a);
        controller.keyReleased(b);
        
        // b, a の同時押しを検出できるか
        controller.keyPressed(b);
        controller.keyPressed(a);       
        assertEquals("b+a", model.getTypedChar());
        controller.keyReleased(a);
        controller.keyReleased(b);
    }

}
