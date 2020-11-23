package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import frog.Frog;
import util.Case;
import util.Direction;

public class FrogInf extends Frog implements IFrog {

    public FrogInf(Game game) {
        super(game);
    }

    @Override
    public Case getScrolledPosition(){
        return new Case(location.absc, location.ord + game.nbOfScroll);
    }

    public boolean isInScrollingArea() {
        return (this.location.ord < (game.height+1)*(1/3) || this.location.ord > (game.height+1)*(2/3));
    }

    @Override
    public void move(Direction key){   //coder cas sortie tableau !
        Case c;
        switch (key){
            case up :
                if (location.ord > game.height/2) {
                    c = location;
                    game.nbOfScroll++;
                } else {
                    c = new Case(this.location.absc, this.location.ord + 1);
                }
                break;
            case down :
                if (game.nbOfScroll>0) {
                    game.nbOfScroll--;
                    c = location;
                } else {
                    if (this.location.ord > 0) {
                        c = new Case(this.location.absc, this.location.ord - 1);
                    } else {
                        c = new Case(this.location.absc, this.location.ord);
                    }
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

}
