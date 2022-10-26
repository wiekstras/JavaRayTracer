package com.wiekstras;

public class Triangle implements Hittable{
    public Vec3 v0, v1, v2;
    public Material matPtr;

    public Triangle(){

    }
    public Triangle(Vec3 p0, Vec3 p1, Vec3 p2, Material matPtr){
        this.v0 = p0;
        this.v1 = p1;
        this.v2 = p2;
        this.matPtr = matPtr;
    }

    @Override
    public boolean Hit(Ray r, double t_min, double t_max, HitRecord rec) {
        // Compute planes normal
        Vec3 v0v1 = v1.Substract(v0); 
        Vec3 v0v2 = v2.Substract(v0); 
        // no need to normalize
        Vec3 N = v0v1.Cross(v0v2);  //N 
        double area2 = N.Lenght();

        //Step 1: finding P
        //Check if ray and plane are parallel
        double NdotRayDirection = N.Dot(r.GetDirection());
        if(Math.abs(NdotRayDirection) < 0.00001){
            return false; // Parallel so no hit
        }
        
        //Compute d parameter using equation 2
        double d = -N.Dot(v0);

        //Compute t (equation 3)
        double t = -(N.Dot(r.GetOrigin()) + d) / NdotRayDirection;

        //Check if the triangle is behind
        if(t < 0) return false; // the triangle is behind

        // Compute the intersection point using equation 1
        Vec3 P = r.GetOrigin().Add(r.GetDirection().MultiplyByScalar(t));


        //Step 2: inside-outside test
        Vec3 C; // Vector perpendicular to triangles plane
        //Edge 0 
        Vec3 edge0 = v1.Substract(v0);
        Vec3 vp0 = P.Substract(v0);
        C = edge0.Cross(vp0);
        if (N.Dot(C) < 0 ) return false; // P is on the right side

        //Edge 1
        Vec3 edge1 = v2.Substract(v1);
        Vec3 vp1 = P.Substract(v1);
        C = edge1.Cross(vp1);
        if (N.Dot(C) < 0 ) return false; // P is on the right side
        
        //Edge 02
        Vec3 edge2 = v0.Substract(v2);
        Vec3 vp2 = P.Substract(v2);
        C = edge2.Cross(vp2);
        if (N.Dot(C) < 0 ) return false; // P is on the right side
        
        rec.t = t;
        rec.p = P;
        rec.normal = N;
        rec.matPtr = this.matPtr;
        return true;
    }
}
