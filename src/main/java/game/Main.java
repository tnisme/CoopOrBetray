package game;

import game.strategies.*;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static final int TOURNAMENT_ROUNDS = 5;

    public static void main(String[] args) {
        List<Strategy> strategies = Arrays.asList(
                new AlwaysCooperate(),
                new AlwaysDefect(),
                new Random(),
                new TitForTat(),
                new Friedman(),
                new TitForTwoTat(),
                new WinStayLoseShift(),
                new SneakyDefector()
        );
        Tournament.runTournament(strategies, TOURNAMENT_ROUNDS);
    }
}
