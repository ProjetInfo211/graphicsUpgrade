package graphicalElements;

import javax.imageio.ImageIO;
import javax.swing.*;

import gameCommons.IFrog;
import util.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver; ////////////////
import java.awt.image.BufferedImage;////////////////
import javax.imageio.ImageIO;//////////////////////
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
	private ArrayList<Element> elementsToDisplay;
	private int pixelByCase = 36;
	private int width;
	private int height;
	private IFrog frog;
	private JFrame frame;
	private final BufferedImage imageFrog = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\frog.png");
	private final BufferedImage Scarleft = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\Scarleft.png");
	private final BufferedImage Scarright = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\Scarright.png");
	private final BufferedImage Mcarleft = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\Mcarleft.png");
	private final BufferedImage Mcarright = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\Mcarright.png");
	private final BufferedImage Busleft = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\Busleft.png");
	private final BufferedImage Busright = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\Busright.png");









	public FroggerGraphic(int width, int height) {
		this.width = width;
		this.height = height;
		elementsToDisplay = new ArrayList<Element>();

		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

		JFrame frame = new JFrame("Frogger");
		this.frame = frame;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);

	}
///////////////////////////////////////////////////////////////////////////////////////////
	public static BufferedImage readImage(String filename) {

		try {
			return ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("Erreur lors du chargement de " + filename);
			System.exit(1);
		}

		return null;

	}
////////////////////////////////////////////////////////////////////////////////////////


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//for (Element e : elementsToDisplay) {
			//g.setColor(e.color);

			//added
			int size = 0;
			int i = 0;
			Element e;
			 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			while (i < elementsToDisplay.size()){
				e = elementsToDisplay.get(i);
				int invertedOrd = ((height-1) - e.ord )* pixelByCase;
			  	if (e.color==Color.GREEN) {
					  g.drawImage(imageFrog, e.absc * pixelByCase, invertedOrd, pixelByCase, pixelByCase, null);
					  i++;
			  	}
			  	else if(e.color == Color.BLUE){
				 	 size = 1;
				 	 i	++;
				 	 while (elementsToDisplay.get(i).color == Color.BLUE && increaseSize(i)){
					  	size++;
					  	i++;
					}

					switch (size){
					  case 1:
						g.drawImage(Scarleft, e.absc * pixelByCase, invertedOrd,  pixelByCase,  + pixelByCase, null);
						break;
					  case 2 :
					      g.drawImage(Mcarleft, e.absc * pixelByCase, invertedOrd, pixelByCase*2, pixelByCase, null);
						break;
					  case 3 :
					  	  g.drawImage(Busleft, e.absc * pixelByCase , invertedOrd, pixelByCase*3, pixelByCase, null);
					  	break;
					  default:
					  	  g.drawImage(Busleft, e.absc * pixelByCase , invertedOrd, pixelByCase*size, pixelByCase, null);
					  	break;
					}
			  	} else if (e.color == Color.BLACK) {
					size = 1;
					i++;
					while (elementsToDisplay.get(i).color == Color.BLACK && increaseSize(i) ) {
						size++;
						i++;
					}
					boolean a = (size >= 4);
					if (a == true){
					System.out.println("voitures confondues");
					}
					switch (size){
						case 1 :
						g.drawImage(Scarright, e.absc * pixelByCase, invertedOrd, pixelByCase, pixelByCase, null);
						break;
						case 2 :
						g.drawImage(Mcarright, e.absc * pixelByCase , invertedOrd, pixelByCase*2, pixelByCase, null);
						break;
						case 3 :
						g.drawImage(Busright, e.absc * pixelByCase , invertedOrd, pixelByCase*3, pixelByCase, null);
						break;
						default:
						g.drawImage(Busright, e.absc * pixelByCase , invertedOrd, pixelByCase*size, pixelByCase, null);
						break;

					}
		      }
		  }
			 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}

	public boolean increaseSize(int i){
		return elementsToDisplay.get(i-1).absc == (elementsToDisplay.get(i).absc)-1 && elementsToDisplay.get(i-1).ord ==elementsToDisplay.get(i).ord;
	}
	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				frog.move(Direction.up);
				break;
			case KeyEvent.VK_DOWN:
				frog.move(Direction.down);
				break;
			case KeyEvent.VK_LEFT:
				frog.move(Direction.left);
				break;
			case KeyEvent.VK_RIGHT:
				frog.move(Direction.right);
		}
	}

	public void keyPressed(KeyEvent e) {

	}

	public void clear() {
		this.elementsToDisplay.clear();
	}

	public void add(Element e) {
		this.elementsToDisplay.add(e);
	}

	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	public void endGameScreen(String s) {
		frame.remove(this);
		JLabel label = new JLabel(s);
		label.setFont(new Font("Verdana", 1, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());
		frame.getContentPane().add(label);
		frame.repaint();

	}

	public void showScreen(boolean show) {
		frame.setVisible(show);
	}


}
