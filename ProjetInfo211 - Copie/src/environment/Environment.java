package environment;

import util.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;

import java.util.ArrayList;


public class Environment implements IEnvironment {

		protected ArrayList<Lane>grid;
		protected Game game;

    public Environment(Game game){
		    this.game = game;
        this.grid = new ArrayList<>(game.height);
        for(int i=0; i<game.height; i++) {
            this.grid.add(i, new Lane(game, i));
        }
        this.grid.add(new environment.Lane(game, game.height - 1));
    }

    public boolean isSafe(Case c){
        return this.grid.get(c.ord).isSafe(c);
    }

    public boolean isWinningPosition(Case c){
        return c.ord == this.grid.size() - 1;
    }

    public void update(){
        for (Lane lane : grid){
            lane.update();
        }
    }

}
