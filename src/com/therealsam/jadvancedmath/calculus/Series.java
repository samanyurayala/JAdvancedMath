package com.therealsam.jadvancedmath.calculus;

import java.util.function.Function;

public final class Series {

    private Series() {}

    public static double sum(int start, double end, Function<Double, Double> f) {
        if (end == Double.NEGATIVE_INFINITY) return 0;
        if (end == Double.POSITIVE_INFINITY) return infSum(start, f);
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += f.apply((double)i);
        }
        return sum;
    }

    private static double infSum(int start, Function<Double, Double> f) {
        double sum = 0;
        while (start < start + 1000000) {
            double current = f.apply((double)start);
            sum += current;
            if (current < 1e-10) break;
            start += 1;
        }
        return Math.round(sum * 1e5) / 1e5;
    }
}
