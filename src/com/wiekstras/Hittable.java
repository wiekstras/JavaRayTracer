package com.wiekstras;

public interface Hittable {
    public abstract boolean Hit(final Ray r, double t_min, double t_max, HitRecord rec); 
}
