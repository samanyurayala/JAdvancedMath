package com.therealsam.jadvancedmath.imaginary;

public class Complex {
    private double real;
    private double imaginary;

    public static final Complex I = createFinalComplex(0, 1);

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        if (real == 0.0) {
            if (imaginary == 1.0) return "i";
            return imaginary + "i";
        }
        if (imaginary == 0.0) return String.valueOf(real);
        if (imaginary == 1.0) return real + " + " + "i";
        return real + " + " + imaginary + "i";
    }

    private static Complex createFinalComplex(double real, double imaginary) {
        return new Complex(real, imaginary);
    }

    private static final class FinalComplex extends Complex {
        public FinalComplex(double real, double imaginary) {
            super(real, imaginary);
        }

        public void setReal(double n) {
            throw new UnsupportedOperationException("Cannot modify immutable complex number");
        }

        public void setImaginary(double n) {
            throw new UnsupportedOperationException("Cannot modify immutable complex number");
        }
    }
}
