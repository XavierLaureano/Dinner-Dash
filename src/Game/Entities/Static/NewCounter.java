package Game.Entities.Static;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import Main.Handler;
import Resources.Images;

public class NewCounter extends BaseCounter {

    public NewCounter(int xPos, int yPos, Handler handler) {
    	super(Images.kitchenCounter[3], xPos, yPos + 27, 96, 112, handler);
    }
    
    private int noCounter = 5*60;
    private int yesCounter = new Random().nextInt(3) * 60 + 20 * 60 + 20;
    public static boolean moreTime = false;
    
    @Override
    public void interact() {
    	
    }
    
    @Override
    public void render(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
    		g2.setColor(Color.WHITE);
    		g.setFont(new Font("ComicSans", Font.BOLD, 20));
    		
    		if(yesCounter <= 0) {
    			moreTime = true;
    			noCounter--;
    			g.drawImage(Images.purplecount, xPos, yPos, width, height, null);
    			g2.drawString(""+((noCounter + 60)/60), xPos + width/2 - 65, yPos - 10);
    			
    			if(noCounter <=0) {
    				yesCounter = new Random().nextInt(2) * 60 + 20 * 60 + 20;
    				noCounter = 5* 60;
    		}
    	}
    		else {
    			g.drawImage(sprite, xPos, yPos, width, height, null);
    			g2.drawString("" + (yesCounter + 60)/60, xPos + width/2 - 65, yPos - 10);
    		}
    		
    		if(isInteractable()) {
    			
    		}
    		
    		yesCounter++;
    }

}