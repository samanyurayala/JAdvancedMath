package com.therealsam.jadvancedmath.calculus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public abstract class Limits {

    public static double limit(Function<Double, Double> f, double limit, String direction) {
        return eval(f, limit, direction);
    }

    public static double limit(Function<Double, Double> f, double limit) {
        return eval(f, limit, "both");
    }

    public static double eval(Function<Double, Double> f, double limit, String direction) {
        double result = f.apply(limit);
        if (!(Double.isNaN(result))) {
            if (Double.isInfinite(result)) {
                double result1 = checkBothSides(f, limit, direction);
                if (Double.isNaN(result1)) return result1;
                if (result1 < 0) return Double.NEGATIVE_INFINITY;
                return Double.POSITIVE_INFINITY;
            }
            double result1 = checkBothSides(f, limit, direction);
            if (Double.isNaN(result1)) return result1;
            if (result == -0.0) return 0.0;
            return result;
        }
        if (limit == Double.POSITIVE_INFINITY || limit == Double.NEGATIVE_INFINITY) result = f.apply(limit);
        else result = checkBothSides(f, limit, direction);
        if ((result < 1e-20 && result > -1e-20)) result = 0.0;
        return result;
    }

    private static double checkBothSides(Function<Double, Double> f, double limit, String direction) {
        double result;
        if (direction.equals("left")) result = approach(-1, limit, f);
        else if (direction.equals("right")) result = approach(1, limit, f);
        else {
            double result1 = approach(-1, limit, f);
            double result2 = approach(1, limit, f);
            if (Math.abs(result1 - result2) > 1e-8) return Double.NaN;
            result = result1;
        }
        return result;
    }

    private static double approach(int multiplier, double value, Function<Double, Double> f) {
        double step = 1e-10 * multiplier;
        ArrayList<Double> values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double test = step + value;
            values.add(f.apply(test));
            step /= 10;
            if (test == value) break;
        }
        boolean divergent = false;
        for (double n: values) {
            if (Double.isInfinite(n) || Math.abs(n) > 1e20) {
                divergent = true;
                break;
            }
        }
        if (divergent) {
            for (int i = values.size() - 1; i >= 0; i--) {
                if (Double.isInfinite(values.get(i)) || Double.isNaN(values.get(i))) continue;
                return (values.get(i) < 0) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
        }
        if (detectOscillating(values)) return Double.NaN;
        if (values.isEmpty()) return f.apply(value);
        for (int i = values.size() - 1; i >= 0; i--) {
            if (!Double.isNaN(values.get(i))) return values.get(i);
        }
        return Double.NaN;
    }

    private static boolean detectOscillating(ArrayList<Double> values) {
        ArrayList<Double> jumps = new ArrayList<>();
        double difference = 1e-10;
        Boolean prevSign = null;
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) jumps.add(values.get(i) - values.get(i - 1));
            if (Double.isNaN(values.get(i)) || Double.isInfinite(values.get(i)) || Math.abs(values.get(i)) < difference) continue;
            boolean curSign = values.get(i) > 0;
            if (prevSign != null && curSign != prevSign) return true;
            prevSign = curSign;
        }
        int signChanges = 0;
        for (int i = 1; i < jumps.size(); i++) {
            double n = jumps.get(i);
            double b = jumps.get(i - 1);
            if (Math.abs(n) < 1e-10 || Double.isNaN(n) || Double.isInfinite(n) || Double.isNaN(b) || Double.isInfinite(b)) continue;
            if ((n < 0 && b > 0) || (n > 0 && b < 0)) signChanges++;
        }
        return jumps.size() >= 4 && signChanges > jumps.size() * 0.75;
    }
}