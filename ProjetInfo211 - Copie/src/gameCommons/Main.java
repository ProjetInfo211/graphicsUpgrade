package gameCommons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import environment.EnvInf;
import frog.Frog;
import frog.FrogInf;
import environment.Environment;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import graphicalElements.MenuGraphic;

public class Main {

	public static void main(String[] args) {

		//Caracteristiques du jeu
		int width = 26;
		int height = 20;
		int tempo = 100;
		int minSpeedInTimerLoops = 3;
		double defaultDensity = 0.2;
		
		//Creation de l'interface graphique
		IFroggerGraphics graphic = new FroggerGraphic(width, height);
		//Creation de la partie
		Game game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity, tempo);
		MenuGraphic menu= new MenuGraphic(width, height);
		game.setMenu(menu);
				
		//Boucle principale : l'environnement s'acturalise tous les tempo milisecondes
		Timer timer = new Timer(tempo, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.update();
				//graphic.repaint();

			}
		});
		timer.setInitialDelay(0);
		timer.start();
	}
}
