package com.wiekstras;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    
    //JFrame
    static final double aspect_ratio = 16.0 / 9.0;
    final static int imageWidth = 1280;
    final static int imageHeight = (int)(imageWidth/aspect_ratio);


    public static void main(String[] args) {
        
        //JPanel setup
        JFrame frame = new JFrame("Raytracer");
        Canvas canvas = new Canvas();
        frame.add(canvas);
        frame.setSize(imageWidth, imageHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        Graphics g2 = canvas.getBufferStrategy().getDrawGraphics();
        BufferedImage buffer = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        
        //Antialiasing first set to 10
        final int samplesPerPixel = 4;
        
        //Max Depth
        final int maxDepth = 10;

        Material materialGround = new Lambertian(new Color(0.8, 0.8, 0.0));
        Material materialCenter = new Dielectric(1.5);
        Material materialLeft = new Dielectric(1.5);
        Material materialRight = new Metal(new Color(0.8, 0.6, 0.2), 1.0);

        //World setup
        List<Hittable> objects = new ArrayList<Hittable>();
        objects.add(new Sphere(new Point3(0,-100.5, -1), 100, materialGround));
        objects.add(new Sphere(new Point3(0,0, -1), 0.5, materialCenter));
        objects.add(new Sphere(new Point3(-1,0, -1), 0.5, materialLeft));
        objects.add(new Sphere(new Point3(1,0, -1), 0.5, materialRight));
        Hittable world = new HittableList(objects);
        
        //Create camera
        Camera camera = new Camera();
        
        //Start timing
        long last = System.nanoTime();

        //Shoot Rays trough scene
        for (int j = imageHeight-1; j >= 0; --j){
            for (int i = 0; i < imageWidth; i++){
                Color pixelColor = new Color();
                for (int s = 0; s < samplesPerPixel; s++) {
                    double u = (i + RandomDouble() ) / imageWidth;
                    double v = (j + RandomDouble() ) / imageHeight;
                    Ray r = camera.GetRay(u, v);
                    pixelColor = pixelColor.Add(RayColor(r, world, maxDepth));            
                }
                buffer.setRGB(i, imageHeight-j-1, pixelColor.WriteColor(pixelColor, samplesPerPixel)); // plot pixel to the screen.

            }
            //Show render line
            // g2.drawImage(buffer, 0, 0, frame.getWidth(), frame.getHeight(), null);
            // canvas.getBufferStrategy().show();


        }
        //End timing
        long now = System.nanoTime();

        //Calculate how long it took
        double time = ((now-last)/1000000000.0);
        
        //Set the buffer to the screen
        while(true)
        {
            g2.drawImage(buffer, 0, 0, frame.getWidth(), frame.getHeight(), null);
            frame.setTitle("Raytracer time to render: " + time);
            canvas.getBufferStrategy().show();
        }
    }

    //Color ray method
    public static Color RayColor(Ray r, Hittable world, int depth){
        HitRecord record = new HitRecord();
        if(depth <= 0)
            return new Color(0,0,0);

        if(world.Hit(r, 0.001, Double.MAX_VALUE, record)){
            Wrapper wrapper = new Wrapper();
            if(record.matPtr.Scatter(r, record, wrapper))
                return wrapper.attenuation.Multiply(RayColor(wrapper.scattered, world, depth-1));
            return new Color(0,0,0);

            // Vec3 target = record.p.Add(record.normal).Add(RandomInUnitSphere());
            // return RayColor(new Ray(record.p, target.Substract(record.p)), world, depth-1).MultiplyByScalar(0.5);
            // return (record.normal.Add(new Color(1,1,1))).MultiplyByScalar(0.5);
        }
        Vec3 unitDirection = new Vec3().UnitVector(r.GetDirection());
        double t = 0.5 * (unitDirection.y() + 1.0);
        return new Color(1.0, 1.0, 1.0)
        .MultiplyByScalar(1.0 - t)
        .Add(new Color(0.5, 0.7, 1.0).MultiplyByScalar(t));
    }
    
    // Random method
    public static double RandomDouble() {
        Random r = new Random();
        return r.nextDouble();
    }
    public static double RandomDouble(double min, double max) {
        Random r = new Random();
        return r.nextDouble() * (max - min) + min;
    }
    public static Vec3 RandomVector3(){ 
        return new Vec3(RandomDouble(), RandomDouble(), RandomDouble());
    }
    public static Vec3 RandomVector3(double min, double max){ 
        return new Vec3(RandomDouble(min,max), RandomDouble(min, max), RandomDouble(min,max));
    }
    public static Vec3 RandomInUnitSphere(){
        while(true){
            Vec3 p = RandomVector3(-1, 1);
            if(p.SqrtLength() >= 1) continue;
            return p;
        }
    }
}