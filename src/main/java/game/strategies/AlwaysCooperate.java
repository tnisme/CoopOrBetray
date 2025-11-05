package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;

public class AlwaysCooperate implements Strategy {

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        return Move.COOPERATE;
    }

    @Override
    public String getName() {
        return "AlwaysCooperate";
    }
}
