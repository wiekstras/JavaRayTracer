package com.wiekstras;

import java.util.Random;

public class Camera {
    
    public Camera(Vec3 lookFrom,
                  Vec3 lookAt,
                  Vec3 vup,
                  double vfov, //In degrees
                  double aspectRatio,
                  double aperture,
                  double focusDist){
        //Camera setup
        double theta = DegreesToRadians(vfov);
        double h = Math.tan(theta/2);
        double viewportHeight = 2.0 * h;
        double viewportWidth  = aspectRatio * viewportHeight;

        w = new Vec3().UnitVector(lookFrom.Substract(lookAt));
        u = new Vec3().UnitVector(new Vec3().Cross(vup, w));
        v = new Vec3().Cross(w, u);

        origin = lookFrom;
        horizontal = u.MultiplyByScalar(viewportWidth).MultiplyByScalar(focusDist);
        vertical = v.MultiplyByScalar(viewportHeight).MultiplyByScalar(focusDist);
        lowerLeftCorner = origin.Substract(horizontal.DivideByScalar(2))
                                .Substract(vertical.DivideByScalar(2))
                                .Substract(w.MultiplyByScalar(focusDist));

        lensRadius = aperture / 2;
    }
    //Get Ray method returns the ray
    public Ray GetRay(double s, double t){
        Vec3 rd = RandomInUnitDisk().MultiplyByScalar(lensRadius);
        Vec3 offset = u.MultiplyByScalar(rd.x())
                 .Add(v.MultiplyByScalar(rd.y()));

        return new Ray(origin.Add(offset),
                        lowerLeftCorner.Add(horizontal.MultiplyByScalar(s))
                                       .Add(vertical.MultiplyByScalar(t))
                                       .Substract(origin).Substract(offset));
    }
    private final Vec3 lowerLeftCorner;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 origin;
    private final double lensRadius;
    private final Vec3 w, u, v;

    public static double RandomDouble(double min, double max) {
        Random r = new Random();
        return r.nextDouble() * (max - min) + min;
    }
    public static Vec3 RandomInUnitDisk(){
        while(true){
            Vec3 p = new Vec3(RandomDouble(-1,1), RandomDouble(-1,1), 0);
            if(p.SqrtLength() >= 1) continue;
            return p;
        }
    }
    public double DegreesToRadians(double degrees) {
        return degrees * Math.PI/ 180.0;
    }
}
