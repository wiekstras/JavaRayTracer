package com.wiekstras;

public class HitRecord {
    public Vec3 p;
    public Vec3 normal;
    public double t;
    public boolean frontFace;
    public Material matPtr;

    public HitRecord(){
        this.p = new Vec3();
        this.normal = new Vec3();
        this.t = 0.0;
    }

    public void SetFaceNormal(Ray r, Vec3 outwardNormal){
        frontFace = r.GetDirection().Dot(outwardNormal) < 0;
        normal = frontFace ? outwardNormal : outwardNormal.MultiplyByScalar(-1.0);
    }
}
