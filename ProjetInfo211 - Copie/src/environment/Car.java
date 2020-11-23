package environment;

import java.awt.Color;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;
	private final Color colorRtL = Color.BLUE;

	//TODO Constructeur(s)
	Car(Game game, Case leftPosition, boolean leftToRight) {
		this.game = game;
		this.leftPosition = leftPosition;
		this.leftToRight = leftToRight;
		this.length = game.randomGen.nextInt(3) + 1;
	}

	
	//TODO : ajout de methodes
	void walk(){
		Case c;
		if(leftToRight){
			c = new Case(this.leftPosition.absc + 1, this.leftPosition.ord);
		}else{
			c = new Case(this.leftPosition.absc - 1, this.leftPosition.ord);
		}
		this.leftPosition = c;
	}

	public Case getLeftPosition() {
		return leftPosition;
	}

	public int getLength() {
		return length;
	}

	/* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
	public void addToGraphics() {
		for (int i = 0; i < length; i++) {
			Color color = colorRtL;
			if (this.leftToRight){
				color = colorLtR;
			}
			game.getGraphic()
					.add(new Element(leftPosition.absc + i, leftPosition.ord - game.nbOfScroll, color));
		}
	}

	public boolean onCase (Case c){
			return c.ord == this.leftPosition.ord && c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length;
	}
}
