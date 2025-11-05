package game;

import game.strategies.AlwaysDefect;
import game.strategies.AlwaysCooperate;
import game.strategies.Random;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Strategy> strategies = Arrays.asList(
                new AlwaysCooperate(),
                new AlwaysDefect(),
                new Random()
        );
        Tournament.runTournament(strategies);
    }
}
