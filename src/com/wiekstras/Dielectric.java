package com.wiekstras;

import java.util.Random;

public class Dielectric extends Material{
    public double ir;

    public Dielectric(double indexOfRefraction){
        this.ir = indexOfRefraction;
    }

    @Override
    public boolean Scatter(Ray r, HitRecord record, Wrapper wrapper) {
        wrapper.attenuation = new Color(1.0,1.0,1.0);
        double refractionRatio = record.frontFace ? (1.0/this.ir) : this.ir;

        Vec3 unitDirection = new Vec3().UnitVector(r.GetDirection());

        double cosTheta = Math.min(unitDirection.MultiplyByScalar(-1.0).Dot(record.normal), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta*cosTheta);

        boolean cannotRefract = refractionRatio * sinTheta > 1.0;
        Vec3 direction;
        if (cannotRefract || Reflectance(cosTheta, refractionRatio) > RandomDouble()){
            direction = Reflect(unitDirection, record.normal);
        }else{
            direction = Refract(unitDirection, record.normal, refractionRatio);
        }
        wrapper.scattered = new Ray(record.p, direction);
        return true;
    }

    public Vec3 Refract(Vec3 uv, Vec3 n, double etaiOverEtat){
        double cosTheta = Math.min(new Vec3().Dot(uv.MultiplyByScalar(-1.0), n), 1.0);
        Vec3 rOutPerp = uv.Substract(n.MultiplyByScalar(cosTheta)).MultiplyByScalar(etaiOverEtat);
        Vec3 rOutParallel = n.MultiplyByScalar(-1.0 * Math.sqrt(Math.abs(1.0 - rOutPerp.SqrtLength())));
        return rOutPerp.Add(rOutParallel);
    }

    public Vec3 Reflect(Vec3 v, Vec3 n){
        return v.Substract(n.MultiplyByScalar(v.Dot(n) * 2));
    }

    private double Reflectance(double cosine, double refIDX){
        double r0 = (1 - refIDX) / (1 + refIDX);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow((1-cosine), 5);
    }

    public static double RandomDouble() {
        Random r = new Random();
        return r.nextDouble();
    }
}
