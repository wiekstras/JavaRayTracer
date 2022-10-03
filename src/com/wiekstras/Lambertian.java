package com.wiekstras;

public class Lambertian extends Material{
    Color albedo;
    public Lambertian(Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public boolean Scatter(Ray r, HitRecord record, Wrapper wrapper) {
        Vec3 scatterDirection = record.normal.Add(RandomInUnitSphere());
        if(scatterDirection.NearZero())
            scatterDirection = record.normal;
        wrapper.scattered = new Ray(record.p, scatterDirection);
        wrapper.attenuation = this.albedo;
        return true;
    }
    
}
