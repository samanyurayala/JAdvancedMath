package com.therealsam.jadvancedmath.vector;

import java.util.Arrays;

public final class Matrix {

    private double[][] m;

    public Matrix(int height, int width) {
        m = new double[height][width];
    }

    public Matrix(double[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) throw new IllegalArgumentException("Double[][] must have at least one row");
        this.m = new double[m.length][m[0].length];
        int cols = m[0].length;
        for (int i = 0; i < this.m.length; i++) {
            if (m[i] == null || m[i].length != cols) throw new IllegalArgumentException("All rows must have the same length");
            System.arraycopy(m[i], 0, this.m[i], 0, cols);
        }
    }

    public double[][] getM() {
        return m;
    }

    public int getRows() {
        return m.length;
    }
    public int getCols() {
        return m[0].length;
    }

    public void setM(double[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) throw new IllegalArgumentException("Double[][] must have at least one row");
        this.m = new double[m.length][m[0].length];
        int cols = m[0].length;
        for (int i = 0; i < this.m.length; i++) {
            if (m[i] == null || m[i].length != cols) throw new IllegalArgumentException("All rows must have the same length");
            System.arraycopy(m[i], 0, this.m[i], 0, cols);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (double[] r: m) {
            for (double n: r) {
                s.append(n).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        double[][] a = ((Matrix) o).m;
        if (a.length != m.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != m[i].length) return false;
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != m[i][j]) return false;
            }
        }
        return true;
    }

    public boolean equals(Object o, double epsilon) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        double[][] a = ((Matrix) o).m;
        if (a.length != m.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != m[i].length) return false;
            for (int j = 0; j < a[i].length; j++) {
                if (Math.abs(a[i][j] - m[i][j]) >= epsilon) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(m);
    }

    public Matrix transpose() {
        double[][] newM = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                newM[j][i] = m[i][j];
            }
        }
        return new Matrix(newM);
    }

    public Matrix add(Matrix a) {
        double[][] otherM = a.m;
        if (otherM.length != m.length || otherM[0].length != m[0].length) throw new IllegalArgumentException("Dimensions must be the same");
        double[][] newM = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                newM[i][j] = m[i][j] + otherM[i][j];
            }
        }
        return new Matrix(newM);
    }

    public Matrix subtract(Matrix a) {
        double[][] otherM = a.m;
        if (otherM.length != m.length || otherM[0].length != m[0].length) throw new IllegalArgumentException("Dimensions must be the same");
        double[][] newM = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                newM[i][j] = m[i][j] - otherM[i][j];
            }
        }
        return new Matrix(newM);
    }

    public Matrix multiply(Matrix a) {
        double[][] otherM = a.m;
        if (m[0].length != otherM.length) throw new IllegalArgumentException("Incompatible matrices to perform operation \"*\"");
        double[][] newM = new double[m.length][otherM[0].length];
        for (int i = 0; i < newM.length; i++) {
            for (int j = 0; j < newM[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < m[i].length; k++) {
                    sum += m[i][k] * otherM[k][j];
                }
                newM[i][j] = sum;
            }
        }
        return new Matrix(newM).clean();
    }

    public Matrix multiply(double n) {
        double[][] newM = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j] * n;
            }
        }
        return new Matrix(newM).clean();
    }

    public Matrix divide(double n) {
        double[][] newM = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j] / n;
            }
        }
        return new Matrix(newM).clean();
    }

    public Matrix add(double n) {
        double[][] newM = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j] + n;
            }
        }
        return new Matrix(newM);
    }

    public Matrix subtract(double n) {
        double[][] newM = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j] - n;
            }
        }
        return new Matrix(newM);
    }

    public Matrix divide(Matrix a) {
        return multiply(a.inverse());
    }

    public Matrix clone() {
        double[][] newM = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i], 0, newM[i], 0, m[i].length);
        }
        return new Matrix(newM);
    }

    public double trace() {
        if (m.length != m[0].length) throw new IllegalArgumentException("Matrix must be square to perform operation \"trace\"");
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i][i];
        }
        return sum;
    }

    public double determinant() {
        if (m.length != m[0].length) throw new IllegalArgumentException("Matrix must be square to perform operation \"determinant\"");
        if (m.length == 1) return m[0][0];
        if (m.length == 2) return m[0][0] * m[1][1] - m[0][1] * m[1][0];
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            int multiplier = (i % 2 == 0) ? 1 : -1;
            sum += multiplier * m[0][i] * minor(0, i).determinant();
        }
        return sum;
    }

    public Matrix minor(int row, int col) {
        if (m.length == 0 || m[0].length == 0) throw new IllegalArgumentException("Invalid matrix to perform operation \"minor\"");
        double[][] minor = new double[m.length - 1][m[0].length - 1];
        int r = 0;
        for (int i = 0; i < m.length; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < m[0].length; j++) {
                if (j == col) continue;
                minor[r][c] = m[i][j];
                c++;
            }
            r++;
        }
        return new Matrix(minor);
    }

    public Matrix inverse() {
        if (m.length != m[0].length) throw new IllegalArgumentException("Matrix must be square to perform operation \"inverse\"");
        double n = determinant();
        if (n == 0.0) throw new ArithmeticException("Cannot perform operation \"inverse\" on a matrix whose determinant is zero");
        return cofactor().transpose().divide(n).clean();
    }

    public Matrix cofactor() {
        if (m.length != m[0].length) throw new IllegalArgumentException("Matrix must be square to perform operation \"cofactor\"");
        if (m.length == 1) return new Matrix(m);
        double[][] cofactor = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                int multiplier = ((i + j) % 2 == 0) ? 1 : -1;
                cofactor[i][j] = multiplier * minor(i, j).determinant();
            }
        }
        return new Matrix(cofactor).clean();
    }

    public Matrix clean() {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (Math.abs(Math.round(m[i][j]) - m[i][j]) < 1e-10) m[i][j] = Math.round(m[i][j]);
            }
        }
        return this;
    }

    public static Matrix generateIdentity(int size) {
        double[][] identity = new double[size][size];
        for (int i = 0; i < size; i++) {
            identity[i][i] = 1.0;
        }
        return new Matrix(identity);
    }

    public static Matrix generateBlank(int r, int c) {
        return new Matrix(new double[r][c]);
    }

    public Matrix pow(int n) {
        if (m.length != m[0].length) throw new IllegalArgumentException("Matrix must be square to perform operation \"pow\"");
        if (n == 0) return generateIdentity(m.length);
        Matrix answer = generateIdentity(m.length);
        Matrix multiplier = (n < 0) ? new Matrix(m).inverse() : new Matrix(m);
        long exponent = Math.abs((long) n);
        while (exponent > 0) {
            if (exponent % 2 == 1) answer = answer.multiply(multiplier);
            multiplier = multiplier.multiply(multiplier);
            exponent /= 2;
        }
        return answer.clean();
    }

    public Matrix adjugate() {
        return cofactor().transpose().clean();
    }

    public Matrix rref() {
        double[][] newM = clone().m;
        int r = 0;
        int c = 0;
        while (r < newM.length && c < newM[0].length) {
            int foundRow = r;
            while (foundRow < newM.length && Math.abs(newM[foundRow][c]) < 1e-10) {
                foundRow++;
            }
            if (foundRow == newM.length) {
                c++;
                continue;
            }
            if (foundRow != r) swap(newM, r, foundRow);
            double pivot = newM[r][c];
            for (int i = 0; i < newM[0].length; i++) {
                newM[r][i] /= pivot;
            }
            for (int i = 0; i < newM.length; i++) {
                if (i != r) {
                    double factor = newM[i][c];
                    for (int j = 0; j < newM[0].length; j++) {
                        newM[i][j] -= newM[r][j] * factor;
                    }
                }
            }
            r++;
            c++;
        }
        return new Matrix(newM).clean();
    }

    public Matrix augment(Matrix a) {
        if (m.length != a.m.length) throw new IllegalArgumentException("Matrices must have the same number of rows to perform operation \"augment\"");
        double[][] newM = new double[m.length][m[0].length + a.m[0].length];
        int offset = m[0].length;
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i], 0, newM[i], 0, m[0].length);
        }
        for (int i = 0; i < a.m.length; i++) {
            System.arraycopy(a.m[i], 0, newM[i], offset, a.m[0].length);
        }
        return new Matrix(newM);
    }

    private void swap(double[][] n, int r1, int r2) {
        double[] temp = n[r1];
        n[r1] = n[r2];
        n[r2] = temp;
    }
}
