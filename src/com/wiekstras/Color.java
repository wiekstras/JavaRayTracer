package com.wiekstras;

public class Color extends Vec3 {
    public Color(){
        //Call Vec3 constructor
        super(0,0,0);
    }
    public Color(double r, double g, double b){
        //Call Vec3 constructor
        super(r, g, b);
    }
    //Clamp to the nearest numer
    public double clamp(double x, double min, double max) {
        if( x < min) return min;
        if( x > max) return max;
        return x;
    }
    public int WriteColor(Color pixelColor, int samplesPerPixel) {
        //Map the XYZ values to rgb
        double r = pixelColor.x(), g = pixelColor.y(), b = pixelColor.z();

        // // Divide the color by the number of samples && Gamma correct for gamma 2.0.
        double scale = 1.0 / samplesPerPixel;
        r = Math.sqrt(scale * r);
        g = Math.sqrt(scale * g);
        b = Math.sqrt(scale * b);

        //Create RGB Color code
        int ir = (int)(256 * clamp(r, 0.0, 0.999));
        int ig = (int)(256 * clamp(g, 0.0, 0.999));
        int ib = (int)(256 * clamp(b, 0.0, 0.999));
        
        // //Bitshift to create int
        return (ir <<16) + (ig <<8) + ib;
    }

    //Overide function to proper return type ://
    public final Color MultiplyByScalar(double scalar) { return new Color(e[0] * scalar, e[1] * scalar, e[2] * scalar); }
    public Color Multiply(Vec3 v){return new Color(e[0] * v.e[0], e[1] * v.e[1], e[2] * v.e[2]); }
	public final Color Add(Vec3 v) { return new Color(e[0] + v.e[0], e[1] + v.e[1], e[2] + v.e[2]); }

}

