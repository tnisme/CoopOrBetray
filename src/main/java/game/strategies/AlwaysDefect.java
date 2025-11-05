package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;

public class AlwaysDefect implements Strategy {

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        return Move.DEFECT;
    }

    @Override
    public String getName() {
        return "AlwaysDefect";
    }
}
