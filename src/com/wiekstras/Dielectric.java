package com.wiekstras;

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
        Vec3 refracted = refract(unitDirection, record.normal, refractionRatio);

        wrapper.scattered = new Ray(record.p, refracted);
        
        return true;
    }

    public Vec3 refract(Vec3 uv, Vec3 n, double etaiOverEtat){

        // auto cos_theta = fmin(dot(-uv, n), 1.0);
        double cosTheta = Math.min(Dot(uv.MultiplyByScalar(-1), n), 1.0);
        
        // vec3 r_out_perp =  etai_over_etat * (uv + cos_theta*n);
        Vec3 rOutPerpTemp = uv.Add(n.MultiplyByScalar(cosTheta));
        Vec3 rOutPerp = rOutPerpTemp.MultiplyByScalar(etaiOverEtat);
        
        // vec3 r_out_parallel = -sqrt(fabs(1.0 - r_out_perp.length_squared())) * n;
        double temp = -Math.sqrt(Math.abs(1.0 - rOutPerp.SqrtLength()));
        Vec3 rOutParallel = n.MultiplyByScalar(temp);
        

        // return r_out_perp + r_out_parallel;
        return rOutPerp.Add(rOutParallel);


        // double cosTheta = Double.min(uv.Substract(uv.MultiplyByScalar(2)).Dot(n), 1.0);        
        // Vec3 rOutParallel = n.MultiplyByScalar(temp);
    }
  
    public double Dot(Vec3 v, Vec3 n){
        return (n.e[0] * v.e[0] + n.e[1] * v.e[1] + n.e[2] * v.e[2]);
    }

}
