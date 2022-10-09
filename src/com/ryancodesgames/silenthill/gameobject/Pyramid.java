
package com.ryancodesgames.silenthill.gameobject;

import com.ryancodesgames.silenthill.math.Mesh;
import com.ryancodesgames.silenthill.math.Triangle;
import com.ryancodesgames.silenthill.math.Vec2D;
import com.ryancodesgames.silenthill.math.Vec3D;
import java.awt.Color;
import java.util.Arrays;


public class Pyramid 
{
    public Mesh meshPyramid;
    
    public Pyramid(double x, double y, double z, float width, double height, double depth)
    {
        meshPyramid = new Mesh(Arrays.asList(new Triangle[]{
        //SOUTH
         new Triangle(new Vec3D(x, y, z), new Vec3D(x + (width/2), y + height, z + (depth/2)), new Vec3D(x + width, y, z), Color.blue),
        //EAST   
         new Triangle(new Vec3D(x + width, y, z), new Vec3D(x + (width/2), y + height, z + (depth/2)), new Vec3D(x + width, y, z + depth), Color.red),
        //NORTH
         new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x + (width/2), y + height, z + (depth)/2), new Vec3D(x, y, z + depth), Color.green),
        //WEST 
         new Triangle(new Vec3D(x, y, z + depth), new Vec3D(x + (width/2), y + height, z + (depth/2)), new Vec3D(x, y, z), Color.orange),
        //BOTTOM
         new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y, z + depth), new Vec3D(x, y, z), Color.magenta),
         new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y, z), new Vec3D(x + width, y, z), Color.WHITE)
        }));
    }
}
