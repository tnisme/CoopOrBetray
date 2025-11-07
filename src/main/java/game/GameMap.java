package game;

public class GameMap {
    private final long seed;
    private final SeedGenerator rng;
    private final double noise;

    public GameMap(long seed) {
        this.seed = seed;
        rng = new SeedGenerator(seed);
        noise = generateDesiredNoise();
    }

    public GameMap() {
        this(System.currentTimeMillis());
    }

    private double generateDesiredNoise() {
        double u = rng.nextDouble();
        double biased = Math.pow(u, 3.5);
        double noise = 0.001 + (0.1 - 0.001) * biased;
        return noise;
    }

    public double getNoise() {
        return noise;
    }

    public long getSeed() {
        return seed;
    }
}
