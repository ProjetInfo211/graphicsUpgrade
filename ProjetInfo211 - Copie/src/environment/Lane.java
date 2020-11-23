package environment;

import java.util.ArrayList;

import util.Case;
import gameCommons.Game;

public class Lane {
	private Game game;
	private final int ord;
	private int speed;
	private ArrayList<Car> cars;
	private final boolean leftToRight;
	private final double density;
	private Case[] c;
	private int timer;

	// TODO : Constructeur(s)
	Lane(Game game, int ord, int speed, ArrayList<Car> cars, boolean leftToRight, double density){
		this.game = game;
		this.ord = ord;
		this.speed = speed;
		this.cars = cars;
		this.leftToRight = leftToRight;
		this.density = density;
		this.c = new Case[game.width];
		this.timer = 0;
		if (!(this.ord == 0 || this.ord == (game.height - 1))) {
			this.mayAddCar();
			for(Case c : this.c) {
				if (this.leftToRight) {
					if (isSafe(new Case(c.absc+1, c.ord)) && isSafe(new Case(c.absc+2, c.ord))) {
						this.mayAddCar(c);
					}
				} else {
					if (isSafe(new Case(game.width-1, ord)) && isSafe(new Case(game.width-2, ord))) {
						this.mayAddCar(c);
					}
				}
			}
		}
	}

	Lane(Game game, int ord) {
		this.game = game;
		this.ord = ord;
		this.speed = game.minSpeedInTimerLoops;
		this.cars = new ArrayList<>();
		this.leftToRight = game.randomGen.nextBoolean();
		this.density = game.defaultDensity;
		this.c = new Case[this.game.width];
		for(int i = 0; i < this.game.width;i++){
			c[i] = new Case(i,this.ord);
		}
		this.timer = 0;

		if (!(this.ord == 0 || this.ord == (game.height - 1))) {
			//this.mayAddCar();
			for(Case c : this.c) {
				if (this.leftToRight) {
					if (isSafe(new Case(c.absc+1, c.ord)) && isSafe(new Case(c.absc+2, c.ord))) {
						this.mayAddCar(c);
					}
				} else {
					if (isSafe(new Case(game.width-1, ord)) && isSafe(new Case(game.width-2, ord))) {
						this.mayAddCar(c);
					}
				}
			}
		}

	}

	public void update() {

		// Toutes les voitures se deplacent d'une case au bout d'un nombre "tic
		// d'horloge" egal a leur vitesse
		// Notez que cette methode est appelee a chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut etre ajoutee

		this.timer ++;
		if (this.timer > this.speed) {
			for (Car c : this.cars) {
				c.walk();
			}
			if (!(this.ord ==0  || this.ord == (game.height-1))) {
				if (this.leftToRight) {
					if (isSafe(new Case(1, ord))) {
						this.mayAddCar();
					}
				} else {
					if (isSafe(new Case(game.width-2, ord))) {
						this.mayAddCar();
					}
				}

			}
			this.timer = 0;
		}
		for (Car car : this.cars) {
			for (int i = car.getLeftPosition().absc; i < car.getLeftPosition().absc + car.getLength(); i++) {
				if(i > 0 && i < this.game.width) c[i] = new Case(i, this.ord);
			}
		}
		for (Car c : this.cars) {
			c.addToGraphics();
		}
	}

	// TODO : ajout de methodes

	public boolean isSafe(Case c){
		for(Car car : this.cars) {
			if (car.onCase(c)) return false;
		}
		return true;
	}
	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au debut de la voie avec probabilite egale a la
	 * densite, si la premiere case de la voie est vide
	 */
	private void mayAddCar() {
		if (this.isSafe(this.getFirstCase()) && this.isSafe(this.getBeforeFirstCase())) {
			if (this.game.randomGen.nextDouble() < this.density) {
				this.cars.add(new Car(this.game, this.getBeforeFirstCase(), this.leftToRight));
			}
		}
	}

	private void mayAddCar(Case c){
		if (this.isSafe(c)){
			if (this.game.randomGen.nextDouble() < this.density) {
			this.cars.add(new Car(this.game,c,this.leftToRight));
			}
		}
	}

	private Case getFirstCase() {
		if (this.leftToRight) {
			return new Case(0, this.ord);
		} else {
			return new Case(this.game.width - 1, this.ord);
		}
	}

	private Case getBeforeFirstCase() {
		if (this.leftToRight) {
			return new Case(-1, this.ord);
		} else
			return new Case(this.game.width, this.ord);
	}

}
