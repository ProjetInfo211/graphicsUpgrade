package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {
	
	protected Game game;
	protected Case location;
	protected Direction direction;

	public Frog(Game game) {
		Case c;
		c = new Case((game.width/2)-1,0);
		this.game = game;
		this.location = c;
		this.direction = Direction.up;
	}

	public Case getPosition(){
		return this.location;
	}
	
	public Direction getDirection(){
		return this.direction;
	}

	public void move(Direction key){   //coder cas sortie tableau !
		Case c;
		switch (key){
		case up :
			c = new Case(this.location.absc, this.location.ord + 1);
			break;
		case down :
			if (this.location.ord > 0) {
				c = new Case(this.location.absc, this.location.ord - 1);
			}else {
				c = new Case(this.location.absc, this.location.ord);
			}
			break;
		case right :
			c = new Case(this.location.absc +1,this.location.ord);
			break;
		case left :
			c = new Case(this.location.absc - 1,this.location.ord);

			break;
			default:
				throw new IllegalStateException("Unexpected value: " + key);
		}
		location = c;

	}

	public Case getScrolledPosition() {
		return getPosition();
	}

}

