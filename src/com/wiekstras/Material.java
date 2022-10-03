package com.wiekstras;

import java.util.Random;

public abstract class Material {
    public abstract boolean Scatter(Ray r, HitRecord record, Wrapper wrapper);

    public static double RandomDouble() {
        Random r = new Random();
        return r.nextDouble();
    }
    public static double RandomDouble(double min, double max) {
        Random r = new Random();
        return r.nextDouble() * (max - min) + min;
    }
    public static Vec3 RandomVector3(){ 
        return new Vec3(RandomDouble(), RandomDouble(), RandomDouble());
    }
    public static Vec3 RandomVector3(double min, double max){ 
        return new Vec3(RandomDouble(min,max), RandomDouble(min, max), RandomDouble(min,max));
    }
    public static Vec3 RandomInUnitSphere(){
        while(true){
            Vec3 p = RandomVector3(-1.0, 1.0);
            if(p.SqrtLength() >= 1.0) continue;
            return p;
        }
    }
}
