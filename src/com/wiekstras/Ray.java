package com.wiekstras;

public class Ray {
    Vec3 origin;
    Vec3 direction;

    public Ray(){}
    public Ray(Vec3 origin, Vec3 direction){
        this.origin = origin;
        this.direction = direction;
    }
    public Vec3 GetOrigin(){
        return this.origin;
    }
    public Vec3 GetDirection(){
        return this.direction;
    }
    public Vec3 At(double t){
        return this.origin.Add(this.direction.MultiplyByScalar(t));
    }
    
}
