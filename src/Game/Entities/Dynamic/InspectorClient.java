package Game.Entities.Dynamic;

import Main.Handler;

public class InspectorClient extends Client {

	public InspectorClient(int xPos, int yPos, Handler handler) {
		super(xPos, yPos, handler);
	}
	
	public boolean goodReview() {
		
		return false;
	}

}
