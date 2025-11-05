package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;

public class Random implements Strategy {

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        Move[] moves = Move.values();
        int index = (int) (Math.random() * moves.length);
        return moves[index];
    }

    @Override
    public String getName() {
        return "Random";
    }

}
