package com.therealsam.jadvancedmath.calculus;

import org.intellij.lang.annotations.Language;

import java.util.Stack;
import java.util.function.Function;
import java.util.regex.Pattern;

public abstract class Integral {

    private static final double[] NODE_LIST = {0.0, -0.2077849550078985, 0.2077849550078985, -0.4058451513773972, 0.4058451513773972, -0.5860872354676911, 0.5860872354676911, -0.7415311855993945, 0.7415311855993945, -0.8648644233597691, 0.8648644233597691, -0.9491079123427585, 0.9491079123427585, -0.9914553711208126, 0.9914553711208126};
    private static final double[] WEIGHT_LIST = {0.2094821410847278, 0.2044329400752989, 0.2044329400752989, 0.1903505780647854, 0.1903505780647854, 0.1690047266392679, 0.1690047266392679, 0.1406532597155259, 0.1406532597155259, 0.1047900103222502, 0.1047900103222502, 0.0630920926299786, 0.0630920926299786, 0.0229353220105292, 0.0229353220105292};

    public static double integrate(Function<Double, Double> f, double a, double b) {
        return adaptiveSimpson(f, a, b, 1e-11);
    }

    public static double integrate(Function<Double, Double> f, double a, double b, @Language("RegExp") String regex) {
        return compile(regex, f, a, b);
    }


    private static double adaptiveSimpson(Function<Double, Double> f, double a, double b, double epsilon) {
        Stack<double[]> intervals = new Stack<>();
        intervals.push(new double[]{a, b, epsilon});
        double sum = 0;
        while (!intervals.isEmpty()) {
            double[] cur = intervals.pop();
            a = cur[0];
            b = cur[1];
            epsilon = cur[2];
            double mid = (cur[0] + cur[1]) / 2;
            double total = adaptInterval(f, a, b);
            double left = adaptInterval(f, a, mid);
            double right = adaptInterval(f, mid, b);
            if (Math.abs(left + right - total) < 15 * epsilon) {
                sum += left + right + (left + right - total)/15;
            } else {
                intervals.push(new double[]{a, mid, epsilon / 2});
                intervals.push(new double[]{mid, b, epsilon / 2});
            }
        }
        return sum;
    }

    private static double simpson(Function<Double, Double> f, double a, double b, double n) {
        if (n % 2 != 0) n++;
        double delta = (b - a) / n;
        double sum = 0;
        for (int i = 1; i < n; i++) {
            double multiplier = i % 2 == 0 ? 2 : 4;
            sum += multiplier * f.apply(a + i * delta);
        }
        sum += f.apply(a) + f.apply(b);
        return sum * delta / 3;
    }

    private static double gaussKronrod(Function<Double, Double> f, double a, double b) {
        double sum = 0;
        for (int i = 0; i < NODE_LIST.length; i++) {
            double x = (b - a) * NODE_LIST[i] / 2 + (a + b) / 2;
            sum += f.apply(x) * WEIGHT_LIST[i];
        }
        return sum * (b - a) / 2;
    }

    private static double filon(Function<Double, Double> f, double a, double b, double k, double n, boolean isSine) {
        if (n % 2 != 0) n++;
        double h = (b - a) / n;
        double sum = 0;
        double[] samples = new double[(int)n];
        double[] functionSamples = new double[(int)n];
        for (int i = 0; i < n; i++) {
            samples[i] = a + i * h;
            functionSamples[i] = f.apply(samples[i]);
        }
        for (int i = 0; i < n - 1; i += 2) {
            double f0 = functionSamples[i];
            double f1 = functionSamples[i + 1];
            double f2 = functionSamples[i];
            double[] weights = calcWeights(h, k, isSine);
            sum += f0 * weights[0] + f1 * weights[1] + f2 * weights[2];
        }
        return sum;
    }

    private static double[] calcWeights(double h, double k, boolean isSine) {
        double[] values = new double[3];
        double theta = h * k;
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        if (isSine) {
            values[0] = (2 * sinTheta - theta * cosTheta) / (theta * theta);
            values[1] = 4 * sinTheta / (theta * theta);
            values[2] = (2 * sinTheta + theta * cosTheta) / (theta * theta);
        } else {
            values[0] = (2 * sinTheta + theta * cosTheta - theta * 2) / (theta * theta);
            values[1] = 4 * sinTheta / (theta * theta);
            values[2] = (2 * sinTheta - theta * cosTheta - theta * 2) / (theta * theta);
        }
        return values;
    }

    private static double adaptInterval(Function<Double, Double>f, double a, double b) {
        return (b - a) * (f.apply(a) + 4 * f.apply((a + b) / 2) + f.apply(b))/6;
    }

    private static double compile(@Language("RegExp") String regex, Function<Double, Double> f, double a, double b) {
        if (regex.equals("\\s")) return simpson(f, a, b, 10000);
        else if (regex.equals("\\G")) return gaussKronrod(f, a, b);
        else if (regex.startsWith("\\f+")) {
            String[] frequency = regex.split("\\+");
            if (frequency.length != 2 || !frequency[1].chars().allMatch(Character::isDigit)) throw new IllegalArgumentException("Not a valid regex");
            return filon(f, a, b, Double.parseDouble(frequency[1]), 20, true);
        } else if (regex.startsWith("\\f")) {
            String[] frequency = regex.split("f");
            if (frequency.length != 2 || !frequency[1].chars().allMatch(Character::isDigit)) throw new IllegalArgumentException("Not a valid regex");
            return filon(f, a, b, Double.parseDouble(frequency[1]), 20, false);
        }
        return adaptiveSimpson(f, a, b, 1e-11);
    }
}
