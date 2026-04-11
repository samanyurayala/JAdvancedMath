package com.therealsam.jadvancedmath.calculus;

import org.intellij.lang.annotations.Language;

import java.util.function.Function;

public final class Series {

    private Series() {}

    public static double sum(int start, double end, Function<Double, Double> f) {
        if (end == Double.NEGATIVE_INFINITY) return 0;
        if (end == Double.POSITIVE_INFINITY) {
            SeriesResult r = infSum(start, f);
            if (!r.convergent()) throw new ArithmeticException("Infinite sum diverges");
            return r.get();
        }
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += f.apply((double)i);
        }
        return sum;
    }

    public static SeriesResult sumInfo(int start, double end, Function<Double, Double> f) {
        if (end == Double.POSITIVE_INFINITY) return infSum(start, f);
        return new SeriesResult(sum(start, end, f), State.CONVERGENT);
    }

    public static double sum(int start, double end, Function<Double, Double> f, @Language("RegExp") String flag) {
        if (end == Double.NEGATIVE_INFINITY) return 0;
        if (end == Double.POSITIVE_INFINITY) {
            if (flag.equalsIgnoreCase("divergent")) throw new ArithmeticException("Infinite sum diverges");
            if (flag.equalsIgnoreCase("convergent")) return infSumAssumedConverges(start, f);
            return infSum(start, f).get();
        }
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += f.apply((double)i);
        }
        return sum;
    }

    private static SeriesResult infSum(int start, Function<Double, Double> f) {
        double sum = 0.0;
        double prevSum = 0.0;
        int stable = 0;
        for (int i = start; i < 10000000; i++) {
            double current = f.apply((double) i);
            if (Double.isNaN(current)) return new SeriesResult(Double.NaN, State.UNKNOWN);
            if (Double.isInfinite(current)) {
                if (current == Double.POSITIVE_INFINITY) return new SeriesResult(Double.POSITIVE_INFINITY, State.DIVERGENT);
                return new SeriesResult(Double.NEGATIVE_INFINITY, State.DIVERGENT);
            }
            sum += current;
            if (Math.abs(sum) > 1e15) return new SeriesResult(Math.round(sum * 1e10) / 1e10, State.DIVERGENT);
            if (Math.abs(sum - prevSum) < 1e-12 * Math.max(1.0, Math.abs(sum))) {
                stable++;
                if (stable > 50) break;
            } else {
                stable = 0;
            }
            prevSum = sum;
        }
        return new SeriesResult(Math.round(sum * 1e10) / 1e10, State.CONVERGENT);
    }

    private static double infSumAssumedConverges(int start, Function<Double, Double> f) {
        double sum = 0.0;
        for (int i = start; i < 10000000; i++) {
            double current = f.apply((double) i);
            sum += current;
            if (Math.abs(current) < 1e-12 * Math.max(1.0, Math.abs(sum))) break;
        }
        return Math.round(sum * 1e10) / 1e10;
    }

    public static double product(int start, double end, Function<Double, Double> f) {
        if (end == Double.NEGATIVE_INFINITY) return 0;
        if (end == Double.POSITIVE_INFINITY) {
            SeriesResult r = infProduct(start, f);
            if (!r.convergent()) throw new ArithmeticException("Infinite product diverges");
            return r.get();
        }
        double product = 1;
        for (int i = start; i <= end; i++) {
            product *= f.apply((double) i);
        }
        return product;
    }

    public static SeriesResult productInfo(int start, double end, Function<Double, Double> f) {
        if (end == Double.POSITIVE_INFINITY) return infProduct(start, f);
        return new SeriesResult(product(start, end, f), State.CONVERGENT);
    }

    private static SeriesResult infProduct(int start, Function<Double, Double> f) {
        SeriesResult sum = infSum(start, x -> {
            double r = f.apply(x);
            if (r <= 0) throw new ArithmeticException("Logarithm product requires strictly positive function values");
            return Math.log(r);
        });
        if (!sum.convergent()) return new SeriesResult(Double.NaN, sum.getState());
        return new SeriesResult(Math.exp(sum.get()), State.CONVERGENT);
    }

    public enum State {
        CONVERGENT,
        DIVERGENT,
        UNKNOWN
    }

    public static final class SeriesResult {
        private final double result;
        private final State state;

        public SeriesResult(double result, State state) {
            this.result = result;
            this.state = state;
        }

        public double get() {
            return result;
        }

        public boolean convergent() {
            return state == State.CONVERGENT;
        }

        public State getState() {
            return state;
        }
    }
}
