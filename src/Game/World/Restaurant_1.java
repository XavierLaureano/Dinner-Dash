package Game.World;

import Game.Entities.Dynamic.Client;
import Game.Entities.Dynamic.Player;
import Game.Entities.Static.*;
import Game.GameStates.State;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Restaurant_1 extends BaseWorld {
    private int count=0;
    private int capacity = 5;
    public Restaurant_1(BaseCounter[] Counters, Handler handler) {
        super(Images.floor,Counters, handler, new Player(null,0,650,handler));

    }

    public void tick(){
        count++;
        if(count >= 5*60 && !isFull()){
            count = 0;
            for(Client client: this.clients){
            	client.move();
            }
            this.generateClient();
        }else if(count >= 5*60 && isFull()){
            count=0;
            boolean left=false;
            Client toLeave = null;
            ArrayList<Client> toMove = new ArrayList<>();
            for (Client client : this.clients) {
                if (client.isLeaving && !left) {
                    toLeave = client;
                    left=true;
                }else if (left) {
                    toMove.add(client);
                }
            }
            if(left){
            	handler.getPlayer().leavingCostumer++;
            	if(toLeave.isInspector) {
//            		System.out.println("Oh No! Inspector placed a bad review... This has cost you: $" + handler.getPlayer().getMoney()/2);
            		handler.getPlayer().setMoney(handler.getPlayer().getMoney()/2);
            		handler.getWorld().isReviewed = true;
            		handler.getWorld().inspectorBuff = false;
            	}
                this.clients.remove(toLeave);
                for (Client client : toMove) {
                	if(client.yPos+102 != 606)
                		client.move();
                }
                this.generateClient();
            }
        }
        
         
        int n, index, newPatience, count = 0, size = this.clients.size();
        for(Client client: this.clients){
        	
        	//if the client is an anti-v, and the parameters for its effect is satisfied, then trigger event
        	if(client.isAntiV && client.activateAntiV()) {
        		
        		n = new Random().nextInt(2);
        		        		
        		//if the random number is 1, then the client influenced by anti-v is after
        		if(n > 0) {
        			
        			index = (count+1 >= size ? ((count+1) % size) : count+1);
        			
        		//if the random number is 0, then the client influenced by anti-v is before
        		}else {
        			
        			index = (count-1 < 0 ? ((((count-1) % size) + size) % size) : count-1);
        			
        		}
        		
//        		System.out.println(">>>>>START OF ANTI-V CHECK>>>>>");
//        		System.out.println("n is: " + n);
//    			System.out.println("index of anti-v: " + count + ", index of client: " + index);
//    			System.out.println("current patience of client: " + this.clients.get(index).getCurrentPatience());

    			newPatience = (int) (this.clients.get(index).getCurrentPatience() - this.clients.get(index).getCurrentPatience()*0.04);
    			this.clients.get(index).setPatience(newPatience);
    			
//    			System.out.println("new patience of client: " + this.clients.get(index).getCurrentPatience());
//    			System.out.println(">>>>>END OF ANTI-V CHECK>>>>>");
        		
        	}
        	
            client.tick();
            
            count++;
            
        }
        
        for(BaseCounter counter: Counters){
            counter.tick();
        }
        
        handler.getPlayer().tick();
        
    }

    public boolean isFull(){
        return this.clients.size() >=capacity;
    }
    public void render(Graphics g){
    	
    	//floor tile
        g.drawImage(Background,0,0,handler.getWidth(), handler.getHeight(),null);
        
        //welcome floor mat
        g.drawImage(Images.welcome,5,90,43,82,null);
        
        //carpet
        g.drawImage(Images.extraSprites[0], 190, 40, 500, 400, null);
        
        //barrels
        g.drawImage(Images.extraSprites[2], handler.getWidth()-90, 20, 100, 100, null);
        
        //top left table and chairs
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3,90,96,96,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3+96,140,52,52,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3-52,140,52,52,null);
        //plant
        g.drawImage(Images.extraSprites[1], handler.getWidth()/3+25, 90, 50, 50, null);

        //center table and chairs
//        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/6,190,96,96,null);
//        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/6+96,240,52,52,null);
//        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/6-52,240,52,52,null);

        //top right table and chairs
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/3,90,96,96,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3+handler.getWidth()/3+96,140,52,52,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/3-52,140,52,52,null);

        //lower right table and chairs
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3+handler.getWidth()/3,292,96,96,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+handler.getWidth()/3+96,312,52,52,null);
        g.drawImage(Images.kitchenChairTable[1],handler.getWidth()/3+handler.getWidth()/3-52,312,52,52,null);
        //plant
        g.drawImage(Images.extraSprites[1], handler.getWidth()/3+280, 292, 50, 50, null);

        //lower left table and chairs
        g.drawImage(Images.kitchenChairTable[0],handler.getWidth()/3,292,96,96,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3+96,312,52,52,null);
        g.drawImage(Images.kitchenChairTable[2],handler.getWidth()/3-52,312,52,52,null);

        for(Client client: clients){
            client.render(g);
        }

        for(BaseCounter counter: Counters){
            counter.render(g);
        }
        handler.getPlayer().render(g);
    }
}
