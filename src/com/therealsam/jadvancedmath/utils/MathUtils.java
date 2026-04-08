package com.therealsam.jadvancedmath.utils;

import java.util.Arrays;

public final class MathUtils {

    private MathUtils() {}

    public static double log(double base, double n) {
        return Math.log(n) / Math.log(base);
    }

    public static int max(int... a) {
        return Arrays.stream(a).max().orElse(0);
    }

    public static double max(double... a) {
        return Arrays.stream(a).max().orElse(0);
    }

    public static float max(float... a) {
        if (a.length == 0) return 0;
        float largest = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > largest) largest = a[i];
        }
        return largest;
    }

    public static long max(long... a) {
        return Arrays.stream(a).max().orElse(0);
    }

    public static int min(int... a) {
        return Arrays.stream(a).min().orElse(0);
    }

    public static double min(double... a) {
        return Arrays.stream(a).min().orElse(0);
    }

    public static float min(float... a) {
        if (a.length == 0) return 0;
        float smallest = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] < smallest) smallest = a[i];
        }
        return smallest;
    }

    public static long min(long... a) {
        return Arrays.stream(a).min().orElse(0);
    }
}
