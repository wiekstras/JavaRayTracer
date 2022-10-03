package com.wiekstras;

public class Wrapper {
    Ray scattered;
    Color attenuation;

    public Wrapper(){
        this.scattered = new Ray();
        this.attenuation = new Color();
    }
    
}
