package game.strategies;

import game.Move;
import game.Strategy;

import java.util.List;
import java.util.Random;

public class SneakyDefector implements Strategy {

    private Random random = new Random();
    private int cooperateStreak = 0;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) return Move.COOPERATE;

        cooperateStreak++;
        if (cooperateStreak > 10 + random.nextInt(5)) {
            cooperateStreak = 0;
            return Move.DEFECT;
        }
        return Move.COOPERATE;
    }

    @Override
    public String getName() {
        return "Sneaky Defector";
    }
}
