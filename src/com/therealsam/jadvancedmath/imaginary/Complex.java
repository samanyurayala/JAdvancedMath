package com.therealsam.jadvancedmath.imaginary;

import java.util.Objects;

public class Complex {
    private double r;
    private double i;

    public static final Complex I = createFinalComplex(0, 1);

    public Complex(double real, double imaginary) {
        this.r = real;
        this.i = imaginary;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    @Override
    public String toString() {
        String s = "";
        if (r != 0.0) {
            if (r % 1.0 == 0.0) s += String.valueOf((int) r);
            else s += String.valueOf(r);
        }
        if (i == 0.0) return (s.isEmpty()) ? "0" : s;
        if (r != 0.0) {
            if (i < 0.0) s += " - ";
            else s += " + ";
        }
        if (r == 0.0 && i < 0.0) s += "-";
        if (Math.abs(i) != 1.0) {
            if (i % 1.0 == 0.0) s += (int) (Math.abs(i));
            else s += Math.abs(i);
        }
        s += "i";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex a = (Complex) o;
        return r == a.r && i == a.i;
    }

    public boolean equals(Object o, double epsilon) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex a = (Complex) o;
        return Math.abs(r - a.r) < epsilon && Math.abs(i - a.i) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, i);
    }

    public Complex add(Complex a) {
        return new Complex(r + a.r, i + a.i);
    }

    public Complex add(double a, boolean real) {
        if (real) return new Complex(r + a, i);
        return new Complex(r, i + a);
    }

    public Complex subtract(Complex a) {
        return new Complex(r - a.r, i - a.i);
    }

    public Complex subtract(double a, boolean real) {
        if (real) return new Complex(r - a, i);
        return new Complex(r, i - a);
    }

    public Complex multiply(double a) {
        return new Complex(r * a, i * a);
    }

    public Complex divide(double a) {
        return new Complex(r / a, i / a);
    }

    public Complex multiply(Complex a) {
        return new Complex(r * a.r - i * a.i, r * a.i + i * a.r);
    }

    public Complex divide(Complex a) {
        return multiply(a.conjugate()).divide(a.r * a.r + a.i * a.i);
    }

    public Complex conjugate() {
        return new Complex(r, -i);
    }

    public double modulus() {
        return Math.hypot(r, i);
    }

    public Complex negate() {
        return new Complex(-r, -i);
    }

    public double arg() {
        return Math.atan2(i, r);
    }

    public double arg2PI() {
        double theta = Math.atan2(i, r);
        return theta - 2.0 * Math.PI * Math.floor(theta / (Math.PI * 2.0));
    }

    private static Complex createFinalComplex(double r, double i) {
        return new Complex(r, i);
    }

    private static final class FinalComplex extends Complex {
        public FinalComplex(double r, double i) {
            super(r, i);
        }

        public void setR(double n) {
            throw new UnsupportedOperationException("Cannot modify immutable complex number");
        }

        public void setI(double n) {
            throw new UnsupportedOperationException("Cannot modify immutable complex number");
        }
    }
}
