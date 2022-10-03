package com.wiekstras;

import java.util.List;
import java.util.ArrayList;

public class HittableList implements Hittable{

    List<Hittable> list;

    public HittableList() {
        list = new ArrayList<Hittable>();
    }
    public HittableList(List<Hittable> list) {
        this.list = list;
    }


    @Override
    public boolean Hit(Ray r, double t_min, double t_max, HitRecord rec) {
        HitRecord tempRecord = new HitRecord();
        boolean hitAnything = false;
        double closestPoint = t_max;

        for (Hittable hittable : list){
            if(hittable.Hit(r, t_min, closestPoint, tempRecord)){
                hitAnything = true;
                closestPoint = tempRecord.t;
                rec.t = tempRecord.t;
                rec.normal = tempRecord.normal;
                rec.p = tempRecord.p;
                rec.matPtr = tempRecord.matPtr;
            }
        }
        return hitAnything;
    }
    
}
