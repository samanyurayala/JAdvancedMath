package com.therealsam.jadvancedmath.calculus;

import org.intellij.lang.annotations.Language;

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

    public static double sum(int start, double end, Function<Double, Double> f, String flag) {
        if (end == Double.NEGATIVE_INFINITY) return 0;
        if (end == Double.POSITIVE_INFINITY) {
            if (flag.equalsIgnoreCase("divergent")) throw new ArithmeticException("Infinite series diverges");
            if (flag.equalsIgnoreCase("convergent")) return infSumConverges(start, f);
            return infSum(start, f);
        }
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += f.apply((double)i);
        }
        return sum;
    }

    private static double infSum(int start, Function<Double, Double> f) {
        double sum = 0.0;
        double prevSum = 0.0;
        int stable = 0;
        for (int i = start; i < 10000000; i++) {
            double current = f.apply((double) i);
            if (Double.isNaN(current)) throw new ArithmeticException("Series is not defined");
            if (Double.isInfinite(current)) throw new ArithmeticException("Infinite series diverges");
            if (i > 1000 && Math.abs(current) > 1e-4) throw new ArithmeticException("Infinite series diverges");
            sum += current;
            if (Math.abs(sum) > 1e15) throw new ArithmeticException("Infinite series diverges");
            if (Math.abs(sum - prevSum) < 1e-12 * Math.max(1.0, Math.abs(sum))) {
                stable++;
                if (stable > 50) break;
            } else {
                stable = 0;
            }
            prevSum = sum;
        }
        return Math.round(sum * 1e10) / 1e10;
    }

    private static double infSumConverges(int start, Function<Double, Double> f) {
        double sum = 0.0;
        for (int i = start; i < 10000000; i++) {
            double current = f.apply((double) i);
            sum += current;
            if (Math.abs(current) < 1e-12 * Math.max(1.0, Math.abs(sum))) break;
        }
        return Math.round(sum * 1e10) / 1e10;
    }
}
