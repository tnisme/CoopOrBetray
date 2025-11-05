package game;

import java.util.List;

public interface Strategy {
    Move decideMove(List<Move> myHistory, List<Move> opponentHistory);
    String getName();
}
