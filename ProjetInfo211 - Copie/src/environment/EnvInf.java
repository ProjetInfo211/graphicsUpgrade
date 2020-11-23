package environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;

public class EnvInf extends Environment implements IEnvironment {

    public EnvInf(Game game){
      super(game);
    }


    @Override
    public void update(){
        scrolling();
        for (Lane lane : grid){
            lane.update();
        }
    }

    public void scrolling(){
        int nbOfLaneToAdd= game.height+game.nbOfScroll - grid.size();
        for (int i =0; i < nbOfLaneToAdd; i++) {
            grid.add(new Lane(game, grid.size()));
        }
    }
}

