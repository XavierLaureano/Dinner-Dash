package Game.Entities.Dynamic;

import Game.Entities.Static.Burger;
import Game.Entities.Static.Item;
import Game.Entities.Static.Order;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Client extends BaseDynamicEntity {
	
    int patience;
    int OGpatience;
    int patienceThreshold=0;
    int patienceAntiV;
    Order order;
    public boolean isLeaving = false;
    
    //boolean to check if client is an inspector, or an anti-v
    public boolean isInspector = false;
    public boolean isAntiV = false;
    
    public Client(int xPos, int yPos, Handler handler) {
        super(Images.people[new Random().nextInt(11)], xPos, yPos,64,72, handler);
        
        //if the client created uses the inspector sprite, assign inspector to client
        if(this.sprite == Images.people[9]) {
        	this.isInspector = true;
        }
        //if the client created uses the karen sprite, assign anti-v to client
        if(this.sprite == Images.people[10]) {
        	this.isAntiV = true;
        }
        
        patience = new Random().nextInt(120*60)+60*60;
        OGpatience = patience;
        
        //8% patience and patience threshold for anti-v client
        patienceAntiV = (int) (OGpatience*0.08);
        
        int numOfIngredients = new Random().nextInt(4)+1;
        order = new Order();
        order.food = new Burger(xPos +72,yPos,52,22);
        ((Burger) order.food).addIngredient(Item.botBread);
        ((Burger) order.food).addIngredient(Item.burger);
        order.value += 1.0;
        
        for(int i = 0;i<numOfIngredients;i++){
        	
            int ingredients = new Random().nextInt(4)+1;
            order.value += 0.5;
            switch (ingredients){
            
                case 1:
                    ((Burger) order.food).addIngredient(Item.lettuce);

                    break;
                    
                case 2:
                    ((Burger) order.food).addIngredient(Item.tomato);

                    break;

                case 3:
                    ((Burger) order.food).addIngredient(Item.cheese);

                    break;
                    
                //added onions as an ingredient to client orders
                case 4:
                	((Burger) order.food).addIngredient(Item.onion);
                	
                	break;

            }

        }
        
        ((Burger) order.food).addIngredient(Item.topBread);

    }
    
    public void tick(){
    	
    	//this increases the patience threshold every tick
    	this.patienceThreshold++;
        patience--;
        
        if(patience <= 0){
            isLeaving = true;
            handler.getPlayer().leavingCostumer++;
        }
        
    }

	public void render(Graphics g){
		
		//Implementation of patience bar for visual information on Patience
		
		float newPatience = this.patience/100;
		float newOGPatience = this.OGpatience/100;
		int patienceBar = (int) ((newPatience/newOGPatience) * 80);
		
		g.setColor(Color.GREEN);
		g.setFont(new Font("TimesNewRoman", Font.BOLD, 20));
		g.drawString("Patience", xPos + 60, yPos + 30);
		
		g.setColor(Color.WHITE);
		g.fillRect(xPos + 60, yPos + 35, patienceBar, 10);

        if(!isLeaving){
            g.drawImage(Images.tint(sprite,1.0f,((float)patience/(float)OGpatience),((float)patience/(float)OGpatience)),xPos,yPos,width,height,null);
            ((Burger) order.food).render(g);
        }
        
    }

    public void move(){
        yPos+=102;
        ((Burger) order.food).y+=102;
    }
    
    //gets the original (total) patience of the client
    public int getPatience() {
    	return this.OGpatience;
    }
    
    //gets the current patience of the client
    public int getCurrentPatience() {
    	return this.patience;
    }
    
    //sets the patience of the client
    public void setPatience(double d) {
    	this.patience = (int) d;
    }
    
    //this is activates the effect of anti-v, by checking the patienceThreshold against the 8%
    public boolean activateAntiV() {
    	
    	if(this.patienceThreshold == this.patienceAntiV) {
    		this.patienceThreshold = 0;
    		return true;
    	}
    	
    	return false;
    	
    }
    
}
