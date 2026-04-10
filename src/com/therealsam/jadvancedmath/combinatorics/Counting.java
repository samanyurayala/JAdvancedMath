package com.therealsam.jadvancedmath.combinatorics;

public final class Counting {

    private Counting(){}

    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Integer factorial must be non-negative");
        int answer = 1;
        for (int i = 2; i <= n; i++) {
            answer *= i;
        }
        return answer;
    }

}
