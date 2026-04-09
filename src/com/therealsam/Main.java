package com.therealsam;

import com.therealsam.jadvancedmath.calculus.Derivative;
import com.therealsam.jadvancedmath.calculus.Integral;
import com.therealsam.jadvancedmath.calculus.Series;
import com.therealsam.jadvancedmath.imaginary.Complex;
import com.therealsam.jadvancedmath.matrix.Matrix;
import com.therealsam.jadvancedmath.utils.MathUtils;

import java.util.Arrays;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(new double[][]{{1, 2, -1, 4}, {2, 3, -1, 11}, {-2, 0, -3, 22}});
        System.out.println(a.rref());
    }
}
