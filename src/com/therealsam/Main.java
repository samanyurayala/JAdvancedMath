package com.therealsam;

import com.therealsam.jadvancedmath.calculus.Derivative;
import com.therealsam.jadvancedmath.calculus.Integral;
import com.therealsam.jadvancedmath.calculus.Series;
import com.therealsam.jadvancedmath.utils.MathUtils;

import java.util.Arrays;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        System.out.println(Integral.integrate(x -> Math.sin(50 * x), 0, Math.PI, "\\f+20"));
    }
}
