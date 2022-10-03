package com.wiekstras;

public class Dielectric extends Material{
    public double ir;

    public Dielectric(double indexOfRefraction){
        this.ir = indexOfRefraction;
    }

    @Override
    public boolean Scatter(Ray r, HitRecord record, Wrapper wrapper) {
        wrapper.attenuation = new Color(1.0,1.0,1.0);
        double refractionRatio = record.frontFace ? (1.0/ir) : ir;

        Vec3 unitDirection = new Vec3().UnitVector(r.GetDirection());
        Vec3 refracted = refract(unitDirection, record.normal, refractionRatio);

        wrapper.scattered = new Ray(record.p, refracted);
        
        return true;
    }

    public Vec3 refract(Vec3 uv, Vec3 n, double etaiOverEtat){
        double cosTheta = Math.min(Dot(uv.MultiplyByScalar(-1), n), 1.0);
        // double cosTheta = Double.min(uv.Substract(uv.MultiplyByScalar(2)).Dot(n), 1.0);

        Vec3 rOutPerp = (uv.Add(n.MultiplyByScalar(cosTheta))).MultiplyByScalar(etaiOverEtat);
        
        
        Vec3 temp = n.MultiplyByScalar((-1.0 *  (Math.sqrt(Math.abs(1.0 - rOutPerp.SqrtLength())))));
        // Vec3 rOutParallel = n.MultiplyByScalar(temp);
        return rOutPerp.Add(temp);
    }
  
    public double Dot(Vec3 v, Vec3 n){
        return (n.e[0] * v.e[0] + n.e[1] * v.e[1] + n.e[2] * v.e[2]);
    }

}
