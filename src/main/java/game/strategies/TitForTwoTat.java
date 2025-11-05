package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;

public class TitForTwoTat implements Strategy {
    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        int size = opponentHistory.size();
        if (size < 2) return Move.COOPERATE;
        Move lastMove = opponentHistory.get(opponentHistory.size() - 1);
        Move secondLastMove = opponentHistory.get(opponentHistory.size() - 2);
        if (lastMove == Move.DEFECT && secondLastMove == Move.DEFECT) return Move.DEFECT;
        return Move.COOPERATE;
    }

    @Override
    public String getName() {
        return "Tit For 2 Tat";
    }
}
