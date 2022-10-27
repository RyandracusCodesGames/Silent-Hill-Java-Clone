
package com.ryancodesgames.silenthill.gameobject;

import com.ryancodesgames.silenthill.math.Matrix;
import com.ryancodesgames.silenthill.math.Mesh;
import com.ryancodesgames.silenthill.math.Transformation;
import com.ryancodesgames.silenthill.math.Triangle;
import com.ryancodesgames.silenthill.math.Vec2D;
import com.ryancodesgames.silenthill.math.Vec3D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Cube 
{
    public Mesh meshCube;
    
    public Transformation transformation;
    
    public Cube(double x, double y, double z, double width, double height, double depth)
    {
        meshCube = new Mesh(Arrays.asList(new Triangle[]{
        //SOUTH
        new Triangle(new Vec3D(x, y, z), new Vec3D(x, y + height, z), new Vec3D(x + width, y + height, z), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x, y, z), new Vec3D(x + width, y + height, z), new Vec3D(x + width, y, z), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1,0)),
        //EAST
        new Triangle(new Vec3D(x + width, y, z), new Vec3D(x + width, y + height, z), new Vec3D(x + width, y + height, z + depth), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1, 1)),
        new Triangle(new Vec3D(x + width, y, z), new Vec3D(x + width, y + height, z + depth), new Vec3D(x + width, y, z + depth),  new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1, 0)),
        //NORTH
        new Triangle(new Vec3D(x + width, y, z  + depth), new Vec3D(x  + width, y + height, z + depth), new Vec3D(x, y + height, z + depth), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y + height, z + depth), new Vec3D(x, y, z + depth), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1, 0)),
        //WEST
        new Triangle(new Vec3D(x, y, z + depth), new Vec3D(x, y + height, z + depth), new Vec3D(x, y + height, z), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x, y, z + depth), new Vec3D(x, y + height, z), new Vec3D(x, y, z),new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0)),
        //TOP
        new Triangle(new Vec3D(x, y + height, z), new Vec3D(x, y + height, z + depth), new Vec3D(x + width, y + height, z + depth), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1, 1)),
        new Triangle(new Vec3D(x, y + height, z), new Vec3D(x + width, y + height, z + depth), new Vec3D(x + width, y + height, z), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1, 0)),
        //BOTTOM
        new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y, z + depth), new Vec3D(x, y, z), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y, z), new Vec3D(x + width, y, z), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1,0))
        }));
    }
    
    public Cube()
    {
        
    }
    
    public void updateTransformations()
    {
        
    }
}
