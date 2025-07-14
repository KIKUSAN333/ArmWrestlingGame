public class LevelSelectState extends MenuBaseState {
    
    public LevelSelectState() {
        super(new String[]{"Level 1", "Level 2", "Level 3"});
    }
    
    @Override
    protected String getTitle() {
        return "PVE LevelSelect";
    }
    
    @Override
    protected State onEnterPressed() {
        switch(selectID) {
            case 0:
                return new ReadyState(1, "", "");
            case 1:
                return new ReadyState(2, "", "");
            case 2:
                return new ReadyState(3, "", "");
            default:
                return this;
        }
    }
    
    @Override
    protected State onBackPressed() {
        return new TitleState();
    }
}