package com.therealsam.jadvancedmath.vector;

import java.util.Objects;

public class Vector3D {
    private double x;
    private double y;
    private double z;

    public static final Vector3D I = createFinalVector(1.0, 0.0, 0.0);
    public static final Vector3D J = createFinalVector(0.0, 1.0, 0.0);
    public static final Vector3D K = createFinalVector(0.0, 0.0, 1.0);

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public void setX(double n) {
        this.x = n;
    }

    public void setY(double n) {
        this.y = n;
    }

    public void setZ(double n) {
        this.z = n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3D a = (Vector3D) o;
        return x == a.x && y == a.y && z == a.z;
    }

    public boolean equals(Object o, double epsilon) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3D a = (Vector3D) o;
        return Math.abs(x - a.x) < epsilon && Math.abs(y - a.y) < epsilon && Math.abs(z - a.z) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public Vector3D add(Vector3D a) {
        return new Vector3D(x + a.x, y + a.y, z + a.z);
    }

    public Vector3D subtract(Vector3D a) {
        return new Vector3D(x - a.x, y - a.y, z - a.z);
    }

    public Vector3D scale(double n) {
        return new Vector3D(x * n, y * n, z * n);
    }

    public Vector3D divide(double n) {
        return new Vector3D(x / n, y / n, z / n);
    }

    public Vector3D negative() {
        return new Vector3D(-x, -y, -z);
    }

    public double dot(Vector3D a) {
        return x * a.x + y * a.y + z * a.z;
    }

    public Vector3D cross(Vector3D a) {
        return new Vector3D(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x);
    }

    public Vector3D hadamard(Vector3D a) {
        return new Vector3D(x * a.x, y * a.y, z * a.z);
    }

    public double magnitude() {
        return Math.hypot(Math.hypot(x, y), z);
    }

    public double magnitudePow(int n) {
        return Math.pow(Math.hypot(Math.hypot(x, y), z), n);
    }

    public Vector3D normalize() {
        if (x == 0 && y == 0 && z == 0) return this;
        return new Vector3D(x / magnitude(), y / magnitude(), z / magnitude());
    }

    public double angle(Vector3D a) {
        return Math.acos(Math.max(-1.0, Math.min(1.0, dot(a) / (magnitude() * a.magnitude()))));
    }

    public double azimuth() {
        return Math.atan2(y, x);
    }

    public double inclination() {
        return Math.acos(Math.max(-1.0, Math.min(1.0, z / magnitude())));
    }

    public double angleOriginY() {
        return Math.atan2(x, y);
    }

    public Vector3D rotateXY(double theta) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        return new Vector3D(x * cos - y * sin, x * sin + y * cos, z);
    }

    public Vector3D rotateYZ(double theta) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        return new Vector3D(x, y * cos - z * sin, y * sin + z * cos);
    }

    public Vector3D rotateXZ(double theta) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        return new Vector3D(x * cos - z * sin, y, x * sin + z * cos);
    }

    public Vector3D rotateXY(double theta, double h, double k) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        double xDiff = x - h;
        double yDiff = y - k;
        return new Vector3D(xDiff * cos - yDiff * sin + h, xDiff * sin + yDiff * cos + k, z);
    }

    public Vector3D rotateYZ(double theta, double k, double l) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        double yDiff = y - k;
        double zDiff = z - l;
        return new Vector3D(x, yDiff * cos - zDiff * sin + k, yDiff * sin + zDiff * cos + l);
    }

    public Vector3D rotateXZ(double theta, double h, double l) {
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        double xDiff = x - h;
        double zDiff = z - l;
        return new Vector3D(xDiff * cos - zDiff * sin + h, y, xDiff * sin + zDiff * cos + l);
    }

    public double distance(Vector3D a) {
        return Math.sqrt(Math.pow(x - a.x, 2) + Math.pow(y - a.y, 2) + Math.pow(z - a.z, 2));
    }

    public double distancePow(Vector3D a, int n) {
        return Math.pow(distance(a), n);
    }

    public Vector3D projection(Vector3D a) {
        return a.scale(dot(a) / magnitudePow(2));
    }

    public Vector3D rejection(Vector3D a) {
        return subtract(projection(a));
    }

    public Vector3D lerp(Vector3D a, double t) {
        return scale(1.0 - t).add(a.scale(t));
    }

    public Vector3D reflect(Vector3D a) {
        Vector3D n = a.normalize();
        return subtract(n.scale((dot(n) * 2.0)));
    }

    public Vector3D reflectX() {
        return new Vector3D(x, -y, -z);
    }

    public Vector3D reflectY() {
        return new Vector3D(-x, y, -z);
    }

    public Vector3D reflectZ() {
        return new Vector3D(-x, -y, z);
    }

    public Spherical3D toSpherical() {
        double r = magnitude();
        double phi = (r == 0) ? 0 : Math.acos(z / r);
        return new Spherical3D(r, azimuth(), phi);
    }

    private static Vector3D createFinalVector(double x, double y, double z) {
        return new FinalVector3D(x, y, z);
    }

    private static final class FinalVector3D extends Vector3D {
        private FinalVector3D(double x, double y, double z) {
            super(x, y, z);
        }

        public void setX(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable vector");
        }

        public void setY(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable vector");
        }

        public void setZ(double n) {
            throw new UnsupportedOperationException("Unable to modify immutable vector");
        }

    }
}
