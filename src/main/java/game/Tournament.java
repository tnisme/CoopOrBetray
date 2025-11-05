package game;

import java.util.*;

public class Tournament {

    private static final int MIN_ROUNDS = 190;
    private static final int MAX_ROUNDS = 210;

    public static void runTournament(List<Strategy> strategies, int repetitions) {
        Map<Integer, Map<String, Double>> roundScores = new HashMap<>();

        for (int rep = 1; rep <= repetitions; rep++) {
            Map<String, Double> currentRoundScores = new HashMap<>();
            int rounds = MIN_ROUNDS + new Random().nextInt(MAX_ROUNDS - MIN_ROUNDS + 1);
            for (Strategy s1 : strategies) {
                double sum = 0;
                for (Strategy s2 : strategies) {
                    int[] scores = Game.playGame(s1, s2, rounds);
                    sum += scores[0];
                }
                double averageScore = sum / strategies.size();
                currentRoundScores.put(s1.getName(), averageScore);
            }
            roundScores.put(rep, currentRoundScores);
        }

        // Calculate final averages
        Map<String, Double> finalAverages = new HashMap<>();
        for (Strategy s : strategies) {
            double total = 0;
            for (int rep = 1; rep <= repetitions; rep++) {
                total += roundScores.get(rep).get(s.getName());
            }
            finalAverages.put(s.getName(), total / repetitions);
        }
        // Sort results
        List<Map.Entry<String, Double>> sortedResults = new ArrayList<>(finalAverages.entrySet());
        sortedResults.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        // Print results
        System.out.println("===== Tournament Results =====");
        System.out.printf("%-6s", "Rank");
        System.out.printf("%-22s", "| Name");
        for (int i = 1; i <= repetitions; i++) {
            System.out.printf("| Round %-3d", i);
        }
        System.out.printf("| Average%n");
        System.out.println("---------------------------------------------------------------------------------------------");
        int rank = 1;
        for (Map.Entry<String, Double> entry : sortedResults) {
            String name = entry.getKey();
            String rankStr = (rank == 1) ? (rank + " \uD83D\uDC51") : String.valueOf(rank);
            System.out.printf("%-5s | %-20s", rankStr, name);

            for (int rep = 1; rep <= repetitions; rep++) {
                double score = roundScores.get(rep).get(name);
                System.out.printf("| %-9.2f", score);
            }

            System.out.printf("| %-8.2f%n", entry.getValue());
            rank++;
        }
    }
}
