package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;

public class WinStayLoseShift implements Strategy {
    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (myHistory.isEmpty()) return Move.COOPERATE;
        Move myLastMove = myHistory.get(myHistory.size() - 1);
        Move opponentLastMove = opponentHistory.get(opponentHistory.size() - 1);
        if ((myLastMove == Move.COOPERATE && opponentLastMove == Move.COOPERATE) ||
                (myLastMove == Move.DEFECT && opponentLastMove == Move.DEFECT)) {
            return myLastMove;
        } else {
            return (myLastMove == Move.COOPERATE) ? Move.DEFECT : Move.COOPERATE;
        }
    }

    @Override
    public String getName() {
        return "Win Stay Lose Shift";
    }
}
