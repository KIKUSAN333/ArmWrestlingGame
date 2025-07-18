public class LevelSelectState extends MenuBaseState {
    
    public LevelSelectState() {
        super(new String[]{"Level 1", "Level 2", "Level 3", "Level 4"});
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
            case 3:
            	  return new ReadyState(4, "", "");
            default:
                return this;
        }
    }
    
    @Override
    protected State onBackPressed() {
        return new TitleState();
    }
}