package com.therealsam;

import com.therealsam.jadvancedmath.calculus.Limits;

import java.util.function.Function;

public class Temp {
    public static void main(String[] args) {
        double[] limits = {
                Limits.limit(x -> x + 2, 5), // 7
                Limits.limit(x -> x * x, 3), // 9
                Limits.limit(x -> x * x * x - 2 * x + 1, 2), // 5
                Limits.limit(x -> Math.sqrt(x + 1), 3), // 2
                Limits.limit(x -> 1 / x, 2), // 0.5
                Limits.limit(x -> Math.abs(x - 3), 3), // 0
                Limits.limit(x -> (x * x - 1)/(x - 1), 1), // 2
                Limits.limit(x -> (x * x - 4)/(x - 2), 2), // 4
                Limits.limit(x -> Math.sin(x)/x, 0), // 1
                Limits.limit(x -> 1/(x - 2), 2, "left"), // -Infinity
                Limits.limit(x -> 1/(x - 2), 2, "right"), // Infinity
                Limits.limit(x -> 1/(x * x), 0, "left"), // Infinity
                Limits.limit(x -> 1/(x * x), 0, "right"), // Infinity
                Limits.limit(x -> Math.log10(x), 0, "right"), // -Infinity
                Limits.limit(x -> Math.exp(x), Double.POSITIVE_INFINITY), // Infinity
                Limits.limit(x -> Math.exp(-x), Double.POSITIVE_INFINITY), // 0
                Limits.limit(x -> Math.sin(1 / x), 0), // NaN
                Limits.limit(x -> Math.sin(x), Double.POSITIVE_INFINITY), // NaN
                Limits.limit(x -> Math.pow(-1, Math.floor(x)), Double.POSITIVE_INFINITY), // NaN
                Limits.limit(x -> 1 / x, Double.POSITIVE_INFINITY), // 0
                Limits.limit(x -> 1 / x, Double.NEGATIVE_INFINITY), // 0
                Limits.limit(x -> x * x, Double.POSITIVE_INFINITY), // Infinity
                Limits.limit(x -> -(x * x), Double.POSITIVE_INFINITY), // -Infinity
                Limits.limit(x -> 1/(x + 1), Double.NEGATIVE_INFINITY), // 0
                Limits.limit(x -> 1/(x * x * x), 0, "left"), // -Infinity
                Limits.limit(x -> 1/(x * x * x), 0, "right"), // Infinity
                Limits.limit(x -> Math.sqrt(x), 0, "right"), // 0
                Limits.limit(x -> Math.sqrt(x), 0, "left"), // NaN
                Limits.limit(x -> 0.0/0.0, 0) // NaN
        };
        for (int i = 0; i < limits.length; i++) {
            System.out.println("l" + (i + 1) + ": " + limits[i]);
        }
    }
}
