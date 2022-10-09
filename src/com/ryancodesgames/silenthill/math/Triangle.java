
package com.ryancodesgames.silenthill.math;

import java.awt.Color;


public class Triangle 
{
    public Vec3D vec3d, vec3d2, vec3d3;
    
    public Vec2D vec2d, vec2d2, vec2d3;
    
    public Color col;
    
    public Triangle(Vec3D vec3d, Vec3D vec3d2, Vec3D vec3d3)
    {
        this.vec3d = vec3d;
        this.vec3d2 = vec3d2;
        this.vec3d3 = vec3d3;
    }  
    
    public Triangle(Vec3D vec3d, Vec3D vec3d2, Vec3D vec3d3, Color col)
    {
        this.vec3d = vec3d;
        this.vec3d2 = vec3d2;
        this.vec3d3 = vec3d3;
        this.col = col;
    }   
    
    public Triangle(Vec3D vec3d, Vec3D vec3d2, Vec3D vec3d3, Vec2D vec2d, Vec2D vec2d2, Vec2D vec2d3)
    {
        this.vec3d = vec3d;
        this.vec3d2 = vec3d2;
        this.vec3d3 = vec3d3;
        this.vec2d = vec2d;
        this.vec2d2 = vec2d2;
        this.vec2d3 = vec2d3;
    }
    
    public Color color(int r, int g, int b)
    {
        col = new Color(r, g, b);
        
        return col;
    }
    
}
