package game;

public class SeedGenerator {

    private long state;

    public SeedGenerator(long seed) {
        this.state = mixSeed(seed);
    }

    private static long mixSeed(long seed) {
        seed ^= (seed >>> 33);
        seed *= 0xff51afd7ed558ccdL;
        seed ^= (seed >>> 33);
        seed *= 0xc4ceb9fe1a85ec53L;
        seed ^= (seed >>> 33);
        return seed;
    }

    public long nextLong() {
        long z = (state += 0x9E3779B97F4A7C15L);
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }

    public double nextDouble() {
        return (nextLong() >>> 11) * (1.0 / (1L << 53));
    }
}
