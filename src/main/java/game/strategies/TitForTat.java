package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;

public class TitForTat implements Strategy {
    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            return Move.COOPERATE;
        } else {
            return opponentHistory.get(opponentHistory.size() - 1);
        }
    }

    @Override
    public String getName() {
        return "Tit For Tat";
    }
}
