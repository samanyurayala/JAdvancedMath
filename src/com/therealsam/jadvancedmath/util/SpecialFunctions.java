package com.therealsam.jadvancedmath.util;

import com.therealsam.jadvancedmath.calculus.Series;

public class SpecialFunctions {

    private static final double[] LANCZOS_VALUES = {676.5203681218851, -1259.1392167224028, 771.32342877765313, -176.61502916214059, 12.507343278686905, -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7};

    public static double gamma(double z) {
        if (z < 0 && z == Math.round(z)) throw new IllegalArgumentException("z must be non-negative");
        if (z < 0.5) return Math.PI / (Math.sin(Math.PI * z) * gamma(1 - z));
        z = z - 1;
        double x = 0.99999999999980993;
        for (int i = 0; i < LANCZOS_VALUES.length; i++) {
            x += LANCZOS_VALUES[i] / (z + i + 1);
        }
        double t = z + 7.5;
        return Math.round(Math.sqrt(Math.PI * 2) * Math.pow(t, z + 0.5) * Math.exp(-t) * x * 1e12) / 1e12;
    }

    public static double lambertW(double z) {
        if (z < -1.0/Math.E) throw new IllegalArgumentException("Invalid argument z for Lambert W function");
        if (z == 0.0) return 0.0;
        double w = (z < 3.0) ? 0.5 * z : Math.log(z);
        double oldW = 0;
        int i = 0;
        while (Math.abs(w - oldW) > 1e-14 && i < 20) {
            oldW = w;
            double eW = Math.exp(w);
            double f = w * eW - z;
            double w1 = w + 1;
            double w2 = w + 2;
            double d = eW * w1 - (w2 * f) / (2 * w1);
            w = w - f / d;
            i++;
        }
        return Math.round(w * 1e12) / 1e12;
    }

    public static double zeta(double z) {
        if (Math.abs(z - 1.0) < 1e-12) return Double.POSITIVE_INFINITY;
        return eta(z) / (1.0  - Math.pow(2.0, 1.0 - z));
    }

    public static double eta(double z) {
        return Series.sum(1, Double.POSITIVE_INFINITY, x -> {
            double multiplier = (x % 2 == 0) ? -1.0 : 1.0;
            return multiplier / Math.pow(x, z);
        });
    }



}
