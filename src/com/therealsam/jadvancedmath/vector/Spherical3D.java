package com.therealsam.jadvancedmath.vector;

import java.util.Objects;

public class Spherical3D {
    private double r;
    private double theta;
    private double phi;

    public static final Spherical3D ZERO = createFinalPolar(0.0, 0.0, 0.0);

    public Spherical3D(double r, double theta, double phi) {
        this.r = r;
        this.theta = theta;
        this.phi = phi;
    }

    public double magnitude() {
        return r;
    }

    public double angle() {
        return theta;
    }

    public double inclination() {
        return phi;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spherical3D a = (Spherical3D) o;
        return r == a.r && theta == a.theta && phi == a.phi;
    }

    public boolean equals(Object o, double epsilon) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spherical3D a = (Spherical3D) o;
        return Math.abs(r - a.r) < epsilon && Math.abs(theta - a.theta) < epsilon && Math.abs(phi - a.phi) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, theta, phi);
    }

    @Override
    public String toString() {
        return "(" + r + ", " + theta + ", " + phi + ")";
    }

    public Vector3D toCartesian() {
        double sin = Math.sin(phi);
        return new Vector3D(r * sin * Math.cos(theta), r * sin * Math.sin(theta), r * Math.cos(phi));
    }

    public Spherical3D scale(double n) {
        return new Spherical3D(r * n, theta, phi);
    }

    public Spherical3D divide(double n) {
        return new Spherical3D(r / n, theta, phi);
    }

    public Spherical3D rotateAzimuth(double angle) {
        return new Spherical3D(r, theta + angle, phi);
    }

    public Spherical3D rotateInclination(double angle) {
        return new Spherical3D(r, theta, phi + angle);
    }

    public Spherical3D rotate(double rotationXY, double rotationYZ, double rotationXZ, Vector3D point) {
        return toCartesian().subtract(point).rotateXY(rotationXY).rotateYZ(rotationYZ).rotateXZ(rotationXZ).add(point).toSpherical();
    }

    public Spherical3D normalizeAngle() {
        return new Spherical3D(r, theta - 2.0 * Math.PI * Math.floor(theta / (Math.PI * 2.0)), phi);
    }

    public Spherical3D normalizeAnglePI() {
        return new Spherical3D(r, Math.atan2(Math.sin(theta), Math.cos(theta)), phi);
    }

    public Spherical3D normalizeR() {
        return new Spherical3D(1, theta, phi);
    }

    public Spherical3D normalizeInclination() {
        return new Spherical3D(r, theta, Math.acos(Math.cos(phi)));
    }

    public Spherical3D normalizeInclination2PI() {
        return new Spherical3D(r, theta, phi - 2.0 * Math.PI * Math.floor(phi / (Math.PI * 2.0)));
    }

    public Spherical3D normalizeInclinationPI() {
        return new Spherical3D(r, theta, Math.atan2(Math.sin(phi), Math.cos(phi)));
    }

    public double distance(Spherical3D a) {
        return toCartesian().distance(a.toCartesian());
    }

    public double azimuthDifference(Spherical3D a) {
        double difference = a.theta - theta;
        return Math.atan2(Math.sin(difference), Math.cos(difference));
    }

    public double inclinationDifference(Spherical3D a) {
        double difference = a.phi - phi;
        return Math.atan2(Math.sin(difference), Math.cos(difference));
    }

    public double smallestAngleBetween(Spherical3D a) {
        double angle = Math.cos(phi) * Math.cos(a.phi) + Math.sin(phi) * Math.sin(a.phi) * Math.cos(theta - a.theta);
        return Math.acos(Math.max(-1.0, Math.min(1.0, angle)));
    }

    public Spherical3D lerp(Spherical3D a, double t) {
        double raw = a.theta - theta;
        return new Spherical3D(r + t * (a.r - r), theta + t * Math.atan2(Math.sin(raw), Math.cos(raw)), phi + t * (a.phi - phi));
    }

    public double dot(Spherical3D a) {
        return r * a.r * (Math.cos(phi) * Math.cos(a.phi) + Math.sin(phi) * Math.sin(a.phi) * Math.cos(theta - a.theta));
    }

    public Spherical3D projection(Spherical3D a) {
        return toCartesian().projection(a.toCartesian()).toSpherical();
    }

    public Spherical3D rejection(Spherical3D a) {
        return toCartesian().rejection(a.toCartesian()).toSpherical();
    }

    private static Spherical3D createFinalPolar(double r, double theta, double phi) {
        return new Spherical3D.FinalSpherical3D(r, theta, phi);
    }

    private static final class FinalSpherical3D extends Spherical3D {
        private FinalSpherical3D(double r, double theta, double phi) {
            super(r, theta, phi);
        }

        public void setR(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable polar coordinate");
        }

        public void setTheta(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable polar coordinate");
        }

        public void setPhi(double phi) {
            throw new UnsupportedOperationException("Unable to modify immutable polar coordinate");
        }
    }
}
