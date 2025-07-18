package me.secondairy.standardeyebreaks.random;

import java.util.SplittableRandom;

public class StandardRandomManager {
    private static SplittableRandom random = null;
    private static long seed = 0L;
    private static boolean initialized = false;

    public static void init(long worldSeed) {
        if (!initialized) {
            random = new SplittableRandom(worldSeed);
            seed = worldSeed;
            initialized = true;
        }
    }

    public static SplittableRandom get() {
        if (!initialized || random == null) {
            throw new IllegalStateException("StandardRandomManager is not initialized.");
        }
        return random;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static long getSeed() {
        if (!initialized) {
            throw new IllegalStateException("StandardRandomManager has not been initialized.");
        }
        return seed;
    }

    public static void close() {
        random = null;
        seed = 0L;
        initialized = false;
    }
}
