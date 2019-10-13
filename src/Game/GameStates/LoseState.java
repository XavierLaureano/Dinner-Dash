package Game.GameStates;

import Main.Handler;
import Resources.Images;
import Display.UI.UIImageButton;
import Display.UI.UIManager;

import java.awt.*;

public class LoseState extends State {

    private int count = 0;
    private UIManager uiManager;

    public LoseState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(100, 50, 128, 64, Images.butstart, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
        }));

        uiManager.addObjects(new UIImageButton(600, 50, 64, 64, Images.butquit, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
    }
}

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.Lose, 0, 0, 900, 800, null);
        uiManager.Render(g);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("ComicSans", Font.BOLD, 20));
        g.drawString("People Served: " + handler.getPlayer().costumers, handler.getWidth() - 800, 700);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("ComicSans", Font.BOLD, 20));
        g.drawString("You won: " + handler.getPlayer().money + " Dollars", handler.getWidth() - 400, 700);

    }
}
