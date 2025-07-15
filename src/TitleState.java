public class TitleState extends MenuBaseState {
    
    public TitleState() {
        super(new String[]{"PVE", "PVP", "RANKING", "HOW TO PLAY"});
    }
    
    @Override
    protected String getTitle() {
        return "MENU";
    }
    
    @Override
    protected State onEnterPressed() {
        switch(selectID) {
            case 0:
                return new LevelSelectState();
            case 1:
                return new EnterNameState();
            case 2:
                // RANKINGの処理を追加
                return this;
            case 3:
                return new HowToPlayState();
            default:
                return this;
        }
    }
    
    @Override
    protected State onBackPressed() {
        // タイトル画面では戻る処理なし
        return this;
    }
}