package Game.Entities.Dynamic;

import Game.Entities.Static.*;
import Game.GameStates.State;
import Main.Handler;
import Resources.Animation;
import Resources.Images;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends BaseDynamicEntity {
	Item item;
	public float money;
	int speed = 10;
	private Burger burger;
	private String direction = "right";
	private int interactionCounter = 0;
	private Animation playerAnim;
	
	public int costumers = 0;
	public int leavingCostumer = 0;
	
	public Player(BufferedImage sprite, int xPos, int yPos, Handler handler) {
		super(sprite, xPos, yPos,82,112, handler);
		createBurger();
		playerAnim = new Animation(120,Images.chef);
	}

	public void createBurger(){
		burger = new Burger(handler.getWidth() - 110, 100, 100, 50);

	}

	public void tick(){
		playerAnim.tick();
		if(xPos + width >= handler.getWidth()){
			direction = "left";

		} else if(xPos <= 0){
			direction = "right";
		}
		if (direction.equals("right")){
			xPos+=speed;
		} else{
			xPos-=speed;
		}
		if (interactionCounter > 15 && handler.getKeyManager().attbut){
			interact();
			interactionCounter = 0;
		} else {
			interactionCounter++;
		}
		if(handler.getKeyManager().fattbut){
			for(BaseCounter counter: handler.getWorld().Counters){
				if (counter instanceof PlateCounter && counter.isInteractable()){
					createBurger();
				}
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					ringCustomer();
				}
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					costumerLine(0);
				}
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_2)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					costumerLine(1);
				}
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_3)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					costumerLine(2);
				}
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_4)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					costumerLine(3);
				}
			}
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)){
			for(BaseCounter counter: handler.getWorld().Counters) {
				if (counter instanceof PlateCounter && counter.isInteractable()) {
					costumerLine(4);
				}
			}
		}

		//implemented shift and control buttons to decrease and increase speeds, respectively
		if(handler.getKeyManager().shiftButt) {
			this.speed = (this.speed > 0? this.speed-1 : this.speed);
		}
		if(handler.getKeyManager().ctrlButt) {
			this.speed++;
		}
		if(money >= 50) {
			State.setState(handler.getGame().winState);
		}
		else if(leavingCostumer == 10) {
			State.setState(handler.getGame().loseState);
		}
	}

	private void ringCustomer() {

		for(Client client: handler.getWorld().clients){
			boolean matched = ((Burger)client.order.food).equals(handler.getCurrentBurger());

			if(matched){

				//added check to see if patience of client is more than half (and inspector), if so, pay more!
				if(client.isInspector) {
//					System.out.println("Congrats! Inspector has left a good review!");
					handler.getWorld().isReviewed = true;
					handler.getWorld().inspectorBuff = true;

					//this adds 12% more patience to each client
					for(Client c : handler.getWorld().clients) {
//						System.out.print("This client's patience was: " + c.getCurrentPatience());
						c.setPatience(c.getCurrentPatience() + c.getCurrentPatience()*0.12);
//						System.out.println(", but is now: " + c.getCurrentPatience());
					}

					money += client.order.value;

				}else {

					//if client's order is completed before reaching half patience, tip!
					if(client.patience >= client.OGpatience/2) {
//						System.out.println("Client tipped! $" + client.order.value*0.15);
						money += client.order.value + (client.order.value*0.15);
					}else {
						money += client.order.value;
					}

				}

				//if the burger is well made, then add 12% of what the client was going to give!
//				if(this.burger.getWellness()) {
//					System.out.println("Well made burger has net you an extra: $" + (this.money*0.12));
//				}
				
				//if the burger is well made, then add 12% of what the client was going to give!
				this.money = (float) (this.burger.getWellness() ? this.money + (this.money*0.12) : this.money + 0);

				handler.getWorld().clients.remove(client);
				handler.getPlayer().createBurger();
				System.out.println("Total money earned is: " + String.valueOf(money));
				return;

			}

		}
	}

	public void render(Graphics g) {
		if(direction=="right") {
			g.drawImage(playerAnim.getCurrentFrame(), xPos, yPos, width, height, null);
		}else{
			g.drawImage(playerAnim.getCurrentFrame(), xPos+width, yPos, -width, height, null);

		}
		
		g.setColor(Color.green);
		burger.render(g);
		
		g.setColor(Color.yellow);
		g.setFont(new Font("ComicSans", Font.BOLD, 32));
		g.drawString("Money Earned: " + money, handler.getWidth()/2 -200, 30);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("ComicSans", Font.BOLD, 20));
		g.drawString("Costumers Served: " + costumers, handler.getWidth() - 225, 30);
	}

	public void interact(){
		for(BaseCounter counter: handler.getWorld().Counters){
			if (counter.isInteractable()){
				counter.interact();
			}
		}
	}
	public Burger getBurger(){
		return this.burger;
	}

	//function to get player's money
	public float getMoney() {
		return this.money;
	}

	//function to set player's money
	public void setMoney(float f) {
		this.money = f;
	}

	public void costumerLine(int lineNumber){
		
		Client client = handler.getWorld().clients.get(lineNumber);
		boolean matched = ((Burger)client.order.food).equals(handler.getCurrentBurger());

		if(matched){

			//added check to see if patience of client is more than half (and inspector), if so, pay more!
			if(client.isInspector) {
				
				System.out.println("Congrats! Inspector has left a good review!");
				handler.getWorld().isReviewed = true;
				handler.getWorld().inspectorBuff = true;

				//this adds 12% more patience to each client
				for(Client c : handler.getWorld().clients) {
					System.out.print("This client's patience was: " + c.getCurrentPatience());
					c.setPatience(c.getCurrentPatience() + c.getCurrentPatience()*0.12);
					System.out.println(", but is now: " + c.getCurrentPatience());
				}

				money += client.order.value;

			}
			else {

				//if client's order is completed before reaching half patience, tip!
				if(client.patience >= client.OGpatience/2) {
					System.out.println("Client tipped! $" + client.order.value*0.15);
					money += client.order.value + (client.order.value*0.15);
				}
				else {
					money += client.order.value;
				}
			}

			//if the burger is well made, then add 12% of what the client was going to give!
			if(this.burger.getWellness()) {
				System.out.println("Well made burger has net you an extra: $" + (this.money*0.12));
			}

			this.money = (float) (this.burger.getWellness() ? this.money + (this.money*0.12) : this.money + 0);

			handler.getWorld().clients.remove(client);
			handler.getPlayer().createBurger();
			System.out.println("Total money earned is: " + String.valueOf(money));
			costumers++;
			for(Client c : handler.getWorld().clients) {
				System.out.print("This client's patience was: " + c.getCurrentPatience());
				c.setPatience(c.getCurrentPatience()/4 + c.getCurrentPatience());
				System.out.println(", but is now: " + c.getCurrentPatience());
			}
			return;
		}
	}
}
