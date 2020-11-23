package gameCommons;

import util.Case;
import util.Direction;

public interface IFrog {
	
	/**
	 * Donne la position actuelle de la grenouille
	 * @return
	 */
	public Case getPosition();
	
	/**
	 * Donne la direction de la grenouille, c'est a dire de son dernier mouvement
	 * @return
	 */
	public Direction getDirection();
	
	/**
	 * Deplace la grenouille dans la direction donnee et teste la fin de partie
	 * @param key
	 */
	public void move(Direction key);

	public Case getScrolledPosition();

}
