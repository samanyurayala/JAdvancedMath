package com.therealsam.jadvancedmath.vector;

import java.util.Objects;

public class Polar2D {
    private double r;
    private double theta;

    public static final Polar2D ZERO = createFinalPolar(0.0, 0.0);

    public Polar2D(double r, double theta) {
        this.r = r;
        this.theta = theta;
    }

    public double magnitude() {
        return r;
    }

    public double angle() {
        return theta;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polar2D a = (Polar2D) o;
        return r == a.r && theta == a.theta;
    }

    public boolean equals(Object o, double epsilon) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polar2D a = (Polar2D) o;
        return Math.abs(r - a.r) < epsilon && Math.abs(theta - a.theta) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, theta);
    }

    @Override
    public String toString() {
        return "(" + r + ", " + theta + ")";
    }

    public Vector2D toCartesian() {
        return new Vector2D(r * Math.cos(theta), r * Math.sin(theta));
    }

    public Polar2D scale(double n) {
        return new Polar2D(r * n, theta);
    }

    public Polar2D divide(double n) {
        return new Polar2D(r / n, theta);
    }

    public Polar2D rotate(double angle) {
        return new Polar2D(r, theta + angle);
    }

    public Polar2D rotate(double angle, double h, double k) {
        return toCartesian().rotate(angle, h, k).toPolar();
    }

    public Polar2D normalizeAngle() {
        return new Polar2D(r, theta - 2.0 * Math.PI * Math.floor(theta / (Math.PI * 2.0)));
    }

    public Polar2D normalizeAnglePI() {
        return new Polar2D(r, Math.atan2(Math.sin(theta), Math.cos(theta)));
    }

    public Polar2D normalizeR() {
        return new Polar2D(1, theta);
    }

    public double distance(Polar2D a) {
        return toCartesian().distance(a.toCartesian());
    }

    public double angleDifference(Polar2D a) {
        double difference = a.theta - theta;
        return Math.atan2(Math.sin(difference), Math.cos(difference));
    }

    public Polar2D lerp(Polar2D a, double t) {
        double raw = a.theta - theta;
        return new Polar2D(r + t * (a.r - r), theta + t * Math.atan2(Math.sin(raw), Math.cos(raw)));
    }

    public double dot(Polar2D a) {
        return r * a.r * Math.cos(theta - a.theta);
    }

    public Polar2D projection(Polar2D a) {
        return toCartesian().projection(a.toCartesian()).toPolar();
    }

    public Polar2D rejection(Polar2D a) {
        return toCartesian().rejection(a.toCartesian()).toPolar();
    }

    private static Polar2D createFinalPolar(double r, double theta) {
        return new Polar2D.FinalPolar2D(r, theta);
    }

    private static final class FinalPolar2D extends Polar2D {
        private FinalPolar2D(double r, double theta) {
            super(r, theta);
        }

        public void setR(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable polar coordinate");
        }

        public void setTheta(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable polar coordinate");
        }

    }
}

