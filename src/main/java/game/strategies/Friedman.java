package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;

public class Friedman implements Strategy {

    private boolean trust = true;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) return Move.COOPERATE;
        if (opponentHistory.get(opponentHistory.size() - 1) == Move.DEFECT && trust) trust = false;
        return trust ? Move.COOPERATE : Move.DEFECT;
    }

    @Override
    public String getName() {
        return "Friedman";
    }
}
