package graphicalElements;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class MenuGraphic extends JPanel implements KeyListener {

    private final int pixelByCase = 36;
    private int width;
    private int height;
    private JFrame frame;
    private final BufferedImage mainMenu = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\main_menu.png");
    private final BufferedImage loseMenu = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\lose_menu.png");
    private final BufferedImage winMenu = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\win_menu.png");
    private final BufferedImage scoresMenu = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\scores_menu.png");
    private final BufferedImage curseur = readImage("C:\\Users\\UserPC\\test clone\\graphicsUpgrade\\ProjetInfo211 - Copie\\src\\graphicalElements\\Projet pngs\\arrow.png");
    private int choice;
    private boolean enter;
    private int screen;
    private int score;
    private float gameTime;

    public MenuGraphic(int width, int height) {
        this.width = width;
        this.height = height;
        this.choice = 0;
        this.enter = false;
        this.screen = 0;

        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

        JFrame frame = new JFrame("Menu");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(this);

    }

    private BufferedImage readImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de " + filename);
            System.exit(1);
        }

        return null;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (choice > 0) choice--;
                break;
            case KeyEvent.VK_DOWN:
                if (choice < 2) choice++;
                break;
            case KeyEvent.VK_ENTER:
                enter = true;
                break;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Verdana", 1, 40);
        g.setFont( font );
        if (screen == 0) {
            g.drawImage(mainMenu, 0, 0, width * pixelByCase, height * pixelByCase, null);
            if (choice==0) {
                g.drawImage(curseur, pixelByCase*6, pixelByCase*7+13, pixelByCase, pixelByCase, null);
            } else if (choice==1) {
                g.drawImage(curseur, pixelByCase*6, pixelByCase*10+20, pixelByCase, pixelByCase, null);
            } else if (choice==2) {
                g.drawImage(curseur, pixelByCase*6, pixelByCase*13+32, pixelByCase, pixelByCase, null);
            }

        } else if (screen == 1 ) {
            g.drawImage(loseMenu, 0, 0, width*pixelByCase, height*pixelByCase, null);
            g.drawString("Score: " + score , 340, (height*pixelByCase)/2);
            g.drawString(" Time: " + gameTime + "s", 340, ((height*pixelByCase)/2) + 50);
        } else if (screen == 2 ) {
            g.drawImage(winMenu, 0, 0, width*pixelByCase, height*pixelByCase, null);
            g.drawString(" Time: " +  gameTime + "s", 340, (height*pixelByCase)/2);
        } else if (screen == 3) {
            g.drawImage(loseMenu, 0, 0, width*pixelByCase, height*pixelByCase, null);
            g.drawString(" Time: " +  gameTime + "s", 340, (height*pixelByCase)/2);
        } else {
            g.drawImage(scoresMenu, 0, 0, width*pixelByCase, height*pixelByCase, null);
            java.io.File fichier = new java.io.File("scores.txt");
            try {
                Scanner in = new Scanner(fichier);
                String [] line;
                JLabel text;
                int i = 0;
                while(in.hasNextLine() && i <5) {
                    i++;
                    line = in.nextLine().split("\n");
                    g.setFont(new Font("Serif", Font.BOLD, pixelByCase));
                    g.drawString(line[0],width*pixelByCase/3, height*pixelByCase/3+i*pixelByCase);
                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public void repaint(){
        if (frame!=null)
        frame.repaint();
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int n) {
        this.choice=n;
    }

    public void setScreen(int n) {
        this.screen = n;
    }

    public boolean isEnterPressed() {
        return enter;
    }

    public void clearEnter() {
        this.enter=false;
    }

    public void setScore(int n, float time) {
        this.score = n;
        gameTime=time;
        {    try {    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("scores.txt", true)));
            out.println(score +" "+ gameTime);
            out.close();
            sortScore();
            }catch (IOException e) {e.printStackTrace();}
        }
    }

    private void sortScore(){
        try {    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("scores.txt", true)));
                 java.io.File fichier = new java.io.File("scores.txt");
                 Scanner in = new java.util.Scanner(fichier);

                 TreeMap<Integer, Float> scores = new TreeMap<>(Collections.reverseOrder());
                 int key; float value;

                 String[] line;

            while(in.hasNextLine()) {
                line = in.nextLine().split(" ");
                key = parseInt(line[0]) ;
                value =parseFloat(line[1]) ;
                scores.put(key, value);
                 }

                  out = new PrintWriter("scores.txt");

                 Set<Integer> keys = scores.keySet();
                 for(Integer k : keys){
                out.println(k+" "+scores.get(k));
            }
                out.close();
        }catch (IOException e) {e.printStackTrace();}
    }
}
