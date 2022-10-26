package com.wiekstras;

public class Vec3 {
    //Create array for Vec3 values
    double e[] = new double[3];

    public Vec3(){
        e[0] = 0.0;
        e[1] = 0.0;
        e[2] = 0.0;
    }
    public Vec3(double x, double y, double z) {
        e[0] = x;
        e[1] = y;
        e[2] = z;
    }

    public final double x(){ return e[0]; }
    public final double y(){ return e[1]; }
    public final double z(){ return e[2]; }

    public final Vec3 Substract(Vec3 v){ 
        return new Vec3(e[0] - v.e[0], e[1] - v.e[1], e[2] - v.e[2]);
    }

    public Vec3 Add(Vec3 v){
        return new Vec3(e[0] + v.e[0], e[1] + v.e[1], e[2] + v.e[2]);
    }

    public Vec3 Multiply(Vec3 v){
        return new Vec3(e[0] * v.e[0], e[1] * v.e[1], e[2] * v.e[2]);
    }

    public Vec3 MultiplyByScalar(double scalar){
        return new Vec3(e[0] * scalar, e[1] * scalar, e[2] * scalar);
    }

    public Vec3 Divide(Vec3 v){
        return new Vec3(e[0] / v.e[0], e[1] / v.e[1], e[2] / v.e[2]);
    }

    public Vec3 DivideByScalar(double scalar){
        return new Vec3(e[0] / scalar, e[1] / scalar, e[2] / scalar);
    }

    public double Lenght(){ 
        return (double)Math.sqrt(Math.pow(e[0], 2) + Math.pow(e[1], 2) + Math.pow(e[2], 2));
    }

    public double SqrtLength(){ 
        return (double)(Math.pow(e[0], 2) + Math.pow(e[1], 2) + Math.pow(e[2], 2));

    }

    public Vec3 UnitVector(Vec3 v){
        return (v.DivideByScalar(v.Lenght()));
    }

    public Vec3 UnitVector(){
        double length = this.Lenght();
        return new Vec3(e[0] / length, e[1] / length, e[2] / length);
    }

    public double Dot(Vec3 v){
        return (e[0] * v.e[0] + e[1] * v.e[1] + e[2] * v.e[2]);
    }
    public double Dot(Vec3 v, Vec3 n){
        return (n.e[0] * v.e[0] + n.e[1] * v.e[1] + n.e[2] * v.e[2]);
    }

    public boolean NearZero(){ 
        final double s = 1e-8;
        return (Math.abs(e[0]) < s) && (Math.abs(e[1]) < s) && (Math.abs(e[2]) < s);
    }
    public Vec3 Cross(Vec3 u, Vec3 v) {
        return new Vec3(u.e[1] * v.e[2] - u.e[2] * v.e[1],
                        u.e[2] * v.e[0] - u.e[0] * v.e[2],
                        u.e[0] * v.e[1] - u.e[1] * v.e[0]);
    }
    public Vec3 Cross(Vec3 v) {
        return new Vec3(this.e[1] * v.e[2] - this.e[2] * v.e[1],
                        this.e[2] * v.e[0] - this.e[0] * v.e[2],
                        this.e[0] * v.e[1] - this.e[1] * v.e[0]);
    }
    // public Vec3 refract()
    
}    