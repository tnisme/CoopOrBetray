package game;

import game.strategies.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Main {

    private static Properties properties = new Properties();

    public static void main(String[] args) {

        List<NPC> strategies = Arrays.asList(
                new KindheartedGirl(),
                new RuthlessBetrayer(),
                new RandomMadman(),
                new FairPerson(),
                new GrudgeHolder(),
                new ForgivingSoul(),
                new PragmaticOpportunist(),
                new SneakyDefector(),
                new AdaptiveProphet(),
                new TheHonestMerchant(),
                new TheCunningTrickster(),
                new TheWiseMadman(),
                new ThePragmaticBartender(),
                new TheMasterManipulator()
        );

        GameMap map = new GameMap();
        System.out.println(map.getSeed());
        Tournament.runTournament(
                strategies,
                getProperty("game.min.rounds", Integer.class),
                getProperty("game.max.rounds", Integer.class),
                getProperty("tournament.repetitions", Integer.class),
                map.getNoise()
        );
    }

    static {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in resources folder");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static <T> T getProperty(String key, Class<T> type) {
        String value = properties.getProperty(key);
            if (value == null) {
                throw new RuntimeException("Sorry, unable to find config.properties");
            }

            Object converted;
            if (type == Integer.class) converted = Integer.parseInt(value);
            else if (type == Boolean.class) converted = Boolean.parseBoolean(value);
            else if (type == Double.class) converted = Double.parseDouble(value);
            else converted = value;

            return type.cast(converted);
    }
}
