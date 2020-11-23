package gameCommons;

import java.awt.Color;
import java.util.Random;

import environment.EnvInf;
import environment.Environment;
import frog.FrogInf;
import frog.Frog;
import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import graphicalElements.MenuGraphic;
import util.State;

public class Game {

	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;

	// Lien aux objets utilises
	private IEnvironment environment;
	private IFrog frog;
	private IFroggerGraphics graphic;
	private MenuGraphic menu;

	public int nbOfScroll;
	private State state;
	private int nbOfUpdate;
	private int tempo;

	/**
	 * 
	 * @param graphic
	 *            l'interface graphique
	 * @param width
	 *            largeur en cases
	 * @param height
	 *            hauteur en cases
	 * @param minSpeedInTimerLoop
	 *            Vitesse minimale, en nombre de tour de timer avant deplacement
	 * @param defaultDensity
	 *            densite de voiture utilisee par defaut pour les routes
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity, int tempo) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.nbOfScroll = 0;
		this.state=State.mainMenu;
		this.tempo=tempo;

	}

	public void newGame(){
		//Creation et liason de la grenouille
		//IFrog frog = new Frog(game);
		IFrog frog = new Frog(this);
		this.setFrog(frog);
		graphic.setFrog(frog);
		//Creation et liaison de l’environnement
		//IEnvironment env = new Environment(game);
		IEnvironment env = new Environment(this);
		setEnvironment(env);
		nbOfUpdate=0;
	}

	public void newGameInf() {
		//Creation et liason de la grenouille
		//IFrog frog = new Frog(game);
		IFrog frog = new FrogInf(this);
		this.setFrog(frog);
		graphic.setFrog(frog);
		//Creation et liaison de l’environnement
		//IEnvironment env = new Environment(game);
		IEnvironment env = new EnvInf(this);
		setEnvironment(env);
		nbOfUpdate=0;
	}
	/**
	 * Lie l'objet frog a la partie
	 * 
	 * @param frog
	 */
	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	/**
	 * Lie l'objet environment a la partie
	 * 
	 * @param environment
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	public void setMenu(MenuGraphic menu) { this.menu = menu; }

	/**
	 * 
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	/**
	 * Teste si la partie est perdue et lance un ecran de fin approprie si tel
	 * est le cas
	 * 
	 * @return true si le partie est perdue
	 */
	public boolean testLose() {
		//////////////////////////////////////////////////

		if (!this.environment.isSafe(frog.getScrolledPosition())){
			//graphic.endGameScreen("Perdu !\n " + "Score: " + this.frog.getScrolledPosition().ord);
			menu.setScore(this.frog.getScrolledPosition().ord, (nbOfUpdate*tempo)/1000);
			if (State.simpleGame == state) {
				menu.setScreen(3);
			} else {
				menu.setScreen(1);
			}
			state = State.endScreen;
			graphic.showScreen(false);
			menu.setVisible(true);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Teste si la partie est gagnee et lance un ecran de fin approprie si tel
	 * est le cas
	 * 
	 * @return true si la partie est gagnee
	 */
	public boolean testWin() {
		///////////////////////////////////////////////

		if (this.environment.isWinningPosition(frog.getPosition())){
			//graphic.endGameScreen("Gagne !");
			menu.setScreen(2);
			state= State.endScreen;
			graphic.showScreen(false);
			menu.setVisible(true);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		switch (state) {
			case simpleGame:
				environment.update();
				if(frog.getScrolledPosition().ord > 0) {
					nbOfUpdate++;
				}
				this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
				testLose();
				testWin();
				graphic.repaint();
				break;
			case infinite:
				environment.update();
				if(frog.getScrolledPosition().ord > 0) {
					nbOfUpdate++;
				}
				this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
				testLose();
				graphic.repaint();
				break;
			case mainMenu:
				graphic.showScreen(false);
				if (menu.isEnterPressed()) {
					if (menu.getChoice() == 0) {
						state = State.simpleGame;
						newGame();
						menu.setVisible(false);
						graphic.showScreen(true);
					} else if (menu.getChoice() == 1) {
						state = State.infinite;
						newGameInf();
						menu.setVisible(false);
						graphic.showScreen(true);
					} else if (menu.getChoice() == 2) {
						state = State.scores;
						menu.setScreen(4);
						menu.setChoice(0);
						menu.setVisible(true);
					}
					menu.clearEnter();
				}
				menu.repaint();
				break;
			case endScreen:
				if (menu.isEnterPressed()) {
					state = State.mainMenu;
					graphic.showScreen(false);
					menu.setChoice(0);
					menu.setScreen(0);
					menu.clearEnter();
					menu.setVisible(true);

				}
				menu.repaint();
				break;
			case scores:
				if (menu.isEnterPressed()) {
					state = State.mainMenu;
					menu.setVisible(true);
					graphic.showScreen(false);
					menu.setChoice(0);
					menu.setScreen(0);
					menu.clearEnter();
				}
				menu.repaint();
				break;

		}


	}
}
