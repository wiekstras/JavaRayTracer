package com.wiekstras;

public class Sphere implements Hittable {
    private Point3 center;
    private double radius;
    private Material matPtr;

    public Sphere() {}
    public Sphere(Point3 center, double radius, Material m){
        this.center = center;
        this.radius = radius;
        this.matPtr = m;
    }

    @Override
    public boolean Hit(Ray r, double t_min, double t_max, HitRecord rec) {
        Vec3 oc = r.GetOrigin().Substract(center);
        double a = r.GetDirection().SqrtLength();
        double half_b = oc.Dot(r.GetDirection());
        double c = oc.SqrtLength() - radius * radius;
        
        double discriminant = half_b * half_b - a * c;
        if(discriminant<0) return false;
        double sqrtDisc = Math.sqrt(discriminant);
        
        double root = (-half_b - sqrtDisc) / a;
        if(root < t_min || t_max < root){
            root = (-half_b + sqrtDisc) / a;
            if(root < t_min || t_max < root){
                return false;
            }
        }
        rec.t = root;
        rec.p = r.At(rec.t);
        // rec.normal = (rec.p.Substract(center)).DivideByScalar(radius);
        Vec3 outWardNormal = (rec.p.Substract(center)).DivideByScalar(this.radius);
        rec.SetFaceNormal(r, outWardNormal);
        rec.matPtr = this.matPtr;
        
        return true;
        
    }
    
}
