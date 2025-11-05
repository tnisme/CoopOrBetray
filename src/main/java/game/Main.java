package game;

import game.strategies.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Strategy> strategies = Arrays.asList(
                new AlwaysCooperate(),
                new AlwaysDefect(),
                new Random(),
                new TitForTat(),
                new Friedman(),
                new TitForTwoTat(),
                new WinStayLoseShift()
        );
        Tournament.runTournament(strategies);
    }
}
