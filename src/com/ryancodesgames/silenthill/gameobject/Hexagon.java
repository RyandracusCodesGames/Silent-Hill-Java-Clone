
package com.ryancodesgames.silenthill.gameobject;

import com.ryancodesgames.silenthill.math.Mesh;
import com.ryancodesgames.silenthill.math.Triangle;
import com.ryancodesgames.silenthill.math.Vec3D;
import java.util.Arrays;

public class Hexagon 
{
    Mesh meshHexagon;
    
    public Hexagon(double x, double y, double z, double width, double height, double depth, double sideL, double sideD)
    {
        meshHexagon = new Mesh(Arrays.asList(new Triangle[]{
        //SOUTH
      //  new Triangle(new Vec3D(x, y, z), new Vec3D(x, y + height, z), new Vec3D(x + width, y + height, z)),
       // new Triangle(new Vec3D(x, y, z), new Vec3D(x + width, y + height, z), new Vec3D(x + width, y, z)),
        //EAST 1
       // new Triangle(new Vec3D(x + width, y, z), new Vec3D(x +
        
        }));
    }
}
