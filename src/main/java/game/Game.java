package game;

import java.util.*;

public class Game {

    public static int[] playGame(NPC s1, NPC s2, int rounds, double noise) {
        List<Move> history1 = new ArrayList<>();
        List<Move> history2 = new ArrayList<>();
        int score1 = 0;
        int score2 = 0;
        Random random = new Random();

        for (int round = 0; round < rounds; round++) {
            // Both strategies decide their moves
            Move intendedMove1 = s1.decideMove(history1, history2);
            Move intendedMove2 = s2.decideMove(history2, history1);

            // Apply noise
            Move move1 = applyMisunderstanding(intendedMove1, random, noise);
            Move move2 = applyMisunderstanding(intendedMove2, random, noise);

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
//        printResults(s1.getName(), history1, score1, s2.getName(), history2, score2);
        return new int[]{score1, score2};
    }

    private static Move applyMisunderstanding(Move intendedMove, Random random, double noise) {
        if (random.nextDouble() < noise) {
            return intendedMove == Move.COOPERATE ? Move.DEFECT : Move.COOPERATE;
        }
        return intendedMove;
    }

    private static void printResults(String strategy1, List<Move> history1, double score1, String strategy2, List<Move> history2, double score2) {
        System.out.println("==== Game Results: ====");
        System.out.printf("%-25s", strategy1);
        for (Move move : history1) {
            System.out.printf("| %-10s", move);
        }
        System.out.printf(" | Score: %.2f", score1);
        System.out.println();
        System.out.printf("%-25s", strategy2);
        for (Move move : history2) {
            System.out.printf("| %-10s", move);
        }
        System.out.printf(" | Score: %.2f", score2);
        System.out.println("\n========================\n");
    }
}
