package com.therealsam;

import com.therealsam.jadvancedmath.calculus.Integral;
import com.therealsam.jadvancedmath.calculus.Series;
import com.therealsam.jadvancedmath.special.SpecialFunctions;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        System.out.println(Integral.integrate(x -> 1/x, 0, 1, ""));
    }
}
