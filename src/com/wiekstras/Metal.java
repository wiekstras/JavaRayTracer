package com.wiekstras;

public class Metal extends Material{
    public Color albedo;
    public double f;
    public Metal(Color albedo, double fuzz) {
        this.albedo = albedo;
        this.f = (fuzz < 1 ? fuzz : 1);
    }

    @Override
    public boolean Scatter(Ray r, HitRecord record, Wrapper wrapper) {
        Vec3 reflected = Reflect((r.GetDirection().UnitVector()), record.normal);
        wrapper.scattered = new Ray(record.p, reflected.Add(RandomInUnitSphere().MultiplyByScalar(f)));
        wrapper.attenuation = albedo;
        return (reflected.Dot(wrapper.scattered.GetDirection() )> 0);
        // return (new Vec3().Dot(scattered.GetDirection(), record.normal) > 0);
    }
    public Vec3 Reflect(Vec3 v, Vec3 n){
        return v.Substract(n.MultiplyByScalar(v.Dot(n) * 2));
    }
    
}
