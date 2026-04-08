package com.therealsam.jadvancedmath.vector;

import java.util.Objects;

public class Vector2D {
    private double x;
    private double y;

    public static final Vector2D I = createFinalVector(1.0, 0.0);
    public static final Vector2D J = createFinalVector(0.0, 1.0);

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void setX(double n) {
        this.x = n;
    }

    public void setY(double n) {
        this.y = n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D a = (Vector2D) o;
        return x == a.x && y == a.y;
    }

    public boolean equals(Object o, double epsilon) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D a = (Vector2D) o;
        return Math.abs(x - a.x) < epsilon && Math.abs(y - a.y) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Vector2D add(Vector2D a) {
        return new Vector2D(x + a.x, y + a.y);
    }

    public Vector2D subtract(Vector2D a) {
        return new Vector2D(x - a.x, y - a.y);
    }

    public Vector2D scale(double n) {
        return new Vector2D(x * n, y * n);
    }

    public Vector2D divide(double n) {
        return new Vector2D(x / n, y / n);
    }

    public Vector2D negative() {
        return new Vector2D(-x, -y);
    }

    public double dot(Vector2D a) {
        return x * a.x + y * a.y;
    }

    public Vector3D cross(Vector2D a) {
        return new Vector3D(0, 0, x * a.y - y * a.x);
    }

    public Vector2D hadamard(Vector2D a) {
        return new Vector2D(x * a.x, y * a.y);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double magnitudePow(int n) {
        return Math.pow(Math.sqrt(x * x + y * y), n);
    }

    public Vector2D normalize() {
        if (x == 0 && y == 0) return this;
        return new Vector2D(x / magnitude(), y / magnitude());
    }

    public double angle(Vector2D a) {
        return Math.acos(Math.max(-1.0, Math.min(1.0, dot(a) / (magnitude() * a.magnitude()))));
    }

    public double angleOriginX() {
        return Math.atan2(y, x);
    }

    public double angleOriginY() {
        return Math.atan2(x, y);
    }

    public Vector2D rotate(double theta) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        return new Vector2D(x * cos - y * sin, x * sin + y * cos);
    }

    public Vector2D rotate(double theta, double h, double k) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        double xDiff = x - h;
        double yDiff = y - k;
        return new Vector2D(xDiff * cos - yDiff * sin + h, xDiff * sin + yDiff * cos + k);
    }

    public double distance(Vector2D a) {
        return Math.sqrt(Math.pow(x - a.x, 2) + Math.pow(y - a.y, 2));
    }

    public double distancePow(Vector2D a, int n) {
        return Math.pow(distance(a), n);
    }

    public Vector2D projection(Vector2D a) {
        return a.scale(dot(a) / magnitudePow(2));
    }

    public Vector2D rejection(Vector2D a) {
        return subtract(projection(a));
    }

    public Vector2D lerp(Vector2D a, double t) {
        return scale(1.0 - t).add(a.scale(t));
    }

    public Vector2D perpendicular(boolean counterClockwise) {
        return (counterClockwise) ? new Vector2D(-y, x) : new Vector2D(y, -x);
    }

    public Vector2D reflect(Vector2D a) {
        Vector2D n = a.perpendicular(true).normalize();
        return subtract(n.scale((dot(n) * 2.0)));
    }

    public Vector2D reflectX() {
        return new Vector2D(x, -y);
    }

    public Vector2D reflectY() {
        return new Vector2D(-x, y);
    }

    public Polar2D toPolar() {
        return new Polar2D(magnitude(), angleOriginX());
    }

    private static Vector2D createFinalVector(double x, double y) {
        return new FinalVector2D(x, y);
    }

    private static final class FinalVector2D extends Vector2D {
        private FinalVector2D(double x, double y) {
            super(x, y);
        }

        public void setX(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable vector");
        }

        public void setY(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable vector");
        }

    }
}
