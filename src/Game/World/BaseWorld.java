package Game.World;

import Game.Entities.Dynamic.Client;
import Game.Entities.Dynamic.Player;
import Game.Entities.Static.BaseCounter;
import Main.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BaseWorld {

    public BufferedImage Background;

    public BaseCounter Counters[];

    public Handler handler;

    public ArrayList<Client> clients = new ArrayList<>();
    
    //boolean checks to see if a review has been conducted, and if inspector buff is on
    public boolean isReviewed = false;
    public boolean inspectorBuff = false;

    public BaseWorld(BufferedImage Background, BaseCounter Counters[], Handler handler, Player player){
        this.Background = Background;
        this.Counters = Counters;
        this.handler=handler;
        handler.setWorld(this);
        handler.setPlayer(player);
    }

    public Client generateClient(){
    	
    	Client client = new Client(0,96,handler);
    	
    	if(this.isReviewed) {
//        	System.out.print("This client's patience was changed from: " + client.getPatience());
    		client.setPatience(this.inspectorBuff ? client.getPatience() + client.getPatience()*0.10 : client.getPatience() - client.getPatience()*0.06);
//    		if(inspectorBuff) {
//    			System.out.println(", to: " + client.getCurrentPatience() + " (this is an upgrade)");
//    		}else {
//    			System.out.println(", to: " + client.getCurrentPatience() + " (this is a downgrade)");
//    		}
    	}
    	
    	System.out.println();
    	
        this.clients.add(client);
        return client;
    }

    public void tick(){

    }

    public void render(Graphics g){

    }
    
}
