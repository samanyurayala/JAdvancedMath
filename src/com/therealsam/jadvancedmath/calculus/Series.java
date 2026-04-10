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
        for (double i = start; i < 10000000; i++) {
            double current = f.apply(i);
            sum += current;
            if (Math.abs(current) < 1e-12 * Math.max(1.0, Math.abs(sum))) break;
        }
        return Math.round(sum * 1e10) / 1e10;
    }
}
