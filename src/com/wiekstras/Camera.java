package com.wiekstras;

public class Camera {
    
    public Camera(){
        //Camera setup
        double aspect_ratio = 16.0 / 9.0;
        double viewportHeight = 2.0;
        double viewportWidth = aspect_ratio * viewportHeight;
        double focalLength = 1.0; 
        origin = new Vec3(0.0, 0.0, 0.0);
        horizontal = new Vec3(viewportWidth, 0.0, 0.0);
        vertical = new Vec3(0.0, viewportHeight, 0.0);
        lowerLeftCorner = origin.Substract(horizontal.DivideByScalar(2)).Substract((vertical.DivideByScalar(2))).Substract(new Vec3(0, 0, focalLength));
        
    }
    //Get Ray method returns the ray
    public Ray GetRay(double u, double v){
        return new Ray(origin, lowerLeftCorner.Add(horizontal.MultiplyByScalar(u)).Add(vertical.MultiplyByScalar(v).Substract(origin)));
    }
    private final Vec3 lowerLeftCorner;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 origin;
}
