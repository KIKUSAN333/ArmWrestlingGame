public class Model {

    private View view;
    private Controller controller;

    // Sample instance variables:
    private int time;
    private String typedChar = "";
    private int mx;
    private int my;
    private boolean enableKeyRollover = false;
    
    private State state;
    private Character character1;
    private Character character2;

    public Model() {
        view = new View(this);
        controller = new Controller(this);
        

        character1 = new Player(this,"test1",0," ");
        character2 = new Player(this,"test2",0,"a");
        
        state = new PVEState(this);
    }

    public synchronized void processTimeElapsed() {
        time++;
        view.repaint();
    }

    public synchronized void processKeyTyped(String typed) {
        typedChar = typed;
        if (typed.equals("ESC")) { 
            enableKeyRollover = !enableKeyRollover; // 同時押し許可モード反転
            controller.setKeyRollover(enableKeyRollover);
        }
        
        state = state.processKeyTyped(typed);
        view.repaint();        
    }

    public synchronized void processMousePressed(int x, int y) {
        mx = x;
        my = y;
        view.playBombSound();
        view.repaint();
    }

    public void start() {
        controller.start();
    }

    public View getView() {
        return view;
    }

    public Controller getController() {
        return controller;
    }

    public int getTime() {
        return time;
    }

    public String getTypedChar() {
        return typedChar;
    }

    public int getMX() {
        return mx;
    }

    public int getMY() {
        return my;
    }

    public boolean getEnableKeyRollover() { 
        return enableKeyRollover;
    }
    
    public State getState() {
    	return state;
    }
    
    public Character getCharacter1() {
    	return character1;
    }
    
    public Character getCharacter2() {
    	return character2;
    }
    
    

}
