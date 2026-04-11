package com.therealsam.jadvancedmath.combinatorics;

import com.therealsam.jadvancedmath.calculus.Series;
import com.therealsam.jadvancedmath.calculus.Series.SeriesResult;
import com.therealsam.jadvancedmath.calculus.Series.State;
import com.therealsam.jadvancedmath.util.SpecialFunctions;

import java.math.BigInteger;
import java.util.LinkedHashMap;

public final class Counting {

    private Counting(){}

    public static BigInteger factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Integer factorial must be non-negative");
        if (n < 2) return BigInteger.ONE;
        BigInteger answer = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            answer = answer.multiply(BigInteger.valueOf(i));
        }
        return answer;
    }

    public static long factorialLong(int n) {
        if (n < 0) throw new IllegalArgumentException("Integer factorial must be non-negative");
        if (n < 2) return 1;
        int answer = 1;
        for (int i = 2; i <= n; i++) {
            answer *= i;
        }
        return answer;
    }

    public static double factorial(double n) {
        return SpecialFunctions.gamma(n + 1);
    }

    public static long fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("Integer fibonacci must be non-negative");
        if (n == 0) return 0;
        if (n == 1) return 1;
        int last = 1, last2 = 0;
        int number = 0;
        for (int i = 2; i <= n; i++) {
            number = last2 + last;
            last2 = last;
            last = number;
        }
        return number;
    }

    public static int gcd(int a, int b) {
        if (a == 0) return Math.abs(b);
        if (b == 0) return Math.abs(a);
        long max = Math.abs((long) Math.max(a, b));
        long min = Math.abs((long) Math.min(a, b));
        long r = max % min;
        if (r == 0) return (int) min;
        while (r != 0) {
            max = min;
            min = r;
            r = max % min;
        }
        return (int) min;
    }

    public static long lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs((long) (a / gcd(a, b)) * b);
    }

    public static LinkedHashMap<Integer, Integer> pf(int n) {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        if (n == 1) return map;
        if (n < 1) throw new IllegalArgumentException("Integer must be greater than or equal to 1");
        while (n % 2 == 0) {
            map.merge(2, 1, Integer::sum);
            n = n / 2;
        }
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                map.merge(i, 1, Integer::sum);
                n = n / i;
            }
        }
        if (n != 1) map.merge(n, 1, Integer::sum);
        return map;
    }

    public static BigInteger choose(int n, int k) {
        if (k < 0 || k > n) return BigInteger.ZERO;
        if (k == 0 || k == n) return BigInteger.ONE;
        k = Math.min(k, n - k);
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= k; i++) {
            result = result.multiply(BigInteger.valueOf(n - k + i)).divide(BigInteger.valueOf(i));
        }
        return result;
    }

    public static long chooseLongApprox(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k == 0 || k == n) return 1;
        k = Math.min(k, n - k);
        int finalK = k;
        return Math.round(Series.product(1, k, x -> (n - finalK + x)/x));
    }

    public static SeriesResult chooseInfo(int n, int k) {
        if (k < 0 || k > n) return new SeriesResult(0, State.CONVERGENT);
        if (k == 0 || k == n) return new SeriesResult(1, State.CONVERGENT);
        k = Math.min(k, n - k);
        int finalK = k;
        return Series.productInfo(1, k, x -> (n - finalK + x)/x);
    }

}
