package game;

import java.util.*;

public class Game {

    public static int[] playGame(Strategy s1, Strategy s2, int rounds) {
        List<Move> history1 = new ArrayList<>();
        List<Move> history2 = new ArrayList<>();
        int score1 = 0;
        int score2 = 0;

        for (int round = 0; round < rounds; round++) {
            Move move1 = s1.decideMove(history1, history2);
            Move move2 = s2.decideMove(history2, history1);

            // Update scores based on moves
            if (move1 == Move.COOPERATE && move2 == Move.COOPERATE) {
                score1 += 3;
                score2 += 3;
            } else if (move1 == Move.DEFECT && move2 == Move.DEFECT) {
                score1 += 1;
                score2 += 1;
            } else if (move1 == Move.COOPERATE && move2 == Move.DEFECT) {
                score2 += 5;
            } else {
                score1 += 5;
            }

            // Update histories
            history1.add(move1);
            history2.add(move2);
        }

        return new int[]{score1, score2};
    }
}
