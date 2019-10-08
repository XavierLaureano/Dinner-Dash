package Game.World;

import Game.Entities.Dynamic.Client;
import Game.Entities.Dynamic.Player;
import Game.Entities.Static.*;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.util.ArrayList;

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
                this.clients.remove(toLeave);
                for (Client client : toMove) {
                    client.move();
                }
                this.generateClient();
            }
        }


        for(Client client: this.clients){
            client.tick();
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
        //cabinet
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
        //cabinet
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
