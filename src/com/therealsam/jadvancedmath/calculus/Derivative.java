package com.therealsam.jadvancedmath.calculus;

import java.util.function.Function;

public final class Derivative {

    private Derivative() {}

    public static double tangent(Function<Double, Double> f, double x) {
        return derivative(f).apply(x);
    }

    public static Function<Double, Double> derivative(Function<Double, Double> f) {
        double h = 1e-8;
        return x -> Math.round((-f.apply(x + 2 * h) + 8 * f.apply(x + h) - 8 * f.apply(x - h) + f.apply(x - 2 * h))/(12 * h) * 1e5) / 1e5;
    }
}
