
package com.ryancodesgames.silenthill.gameobject;

import static com.ryancodesgames.silenthill.gfx.DrawUtils.drawTriangle;
import com.ryancodesgames.silenthill.math.Matrix;
import com.ryancodesgames.silenthill.math.Mesh;
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
    
    public void draw(Graphics2D g2, Graphics g, float fTheta, Matrix mat, Matrix matZ, Matrix matZX, float fYaw,
    Vec3D vLookDir, Vec3D vCamera, Matrix matProj, Cube c)
    {
        // fTheta += 0.02;
        
        //ROTATION MATRICES
        matZ = mat.rotationMatrixZ(fTheta * 0.5f);
        matZX = mat.rotationMatrixX(fTheta);
        
        //TRANSLATION MATRIX
        Matrix trans = mat.translationMatrix(0, 0, 16);
        
        //MATRIX MATRIX MULTIPLICATION TO ACCUMULATE MULITPLE TRANSFORMATIONS
        Matrix matWorld = new Matrix();
        matWorld = matWorld.identityMatrix();
        matWorld = matWorld.matrixMatrixMultiplication(matZ, matZX);
        matWorld = matWorld.matrixMatrixMultiplication(matWorld, trans);
        
        Vec3D vUp = new Vec3D(0,1,0);
        Vec3D vTarget = new Vec3D(0,0,1);
        Matrix matCameraRotated = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        matCameraRotated = mat.rotationMatrixY(fYaw);
        vLookDir = mat.multiplyMatrixVector(vTarget, matCameraRotated);
        vTarget = vTarget.addVector(vCamera, vLookDir);
        
        //USING THE INFORMATION PROVIDED ABOVE TO DEFIEN A CAMERA MATRIX
        Matrix matCamera = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        matCamera = matCamera.pointAtMatrix(vCamera, vTarget, vUp);
        
        Matrix matView = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        matView = matView.inverseMatrix(matCamera);
        
        List<Mesh> mesh = new ArrayList<>();
        mesh.add(c.meshCube);
       // mesh.add(meshCube2);
        
        List<Triangle> vecTrianglesToRaster = new ArrayList<>();
        
        for(Mesh meshCube: mesh)
        {
            
        }
        
        for(Triangle tri: c.meshCube.triangles)
        {
            Triangle triProjected = new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0));
            Triangle triTrans = new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0));
            Triangle triViewed = new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0));
            
            triTrans.vec3d = mat.multiplyMatrixVector(tri.vec3d, matWorld);
            triTrans.vec3d2 = mat.multiplyMatrixVector(tri.vec3d2, matWorld);
            triTrans.vec3d3 = mat.multiplyMatrixVector(tri.vec3d3, matWorld);
            triTrans.vec2d = tri.vec2d;
            triTrans.vec2d2 = tri.vec2d2;
            triTrans.vec2d3 = tri.vec2d3;
            
            //COLLECT SURFACE NORMALS AND SEE HOW MUCH THEY MAP OVER THE CAMERA
            Vec3D normal = new Vec3D(0,0,0);
            Vec3D line1 = new Vec3D(0,0,0);
            Vec3D line2 = new Vec3D(0,0,0);
            
            line1 = line1.subtractVector(triTrans.vec3d2, triTrans.vec3d);
            line2 = line1.subtractVector(triTrans.vec3d3, triTrans.vec3d);
            
            normal = line1.crossProduct(line1, line2);
            normal = line1.normalize(normal);
            
            Vec3D vCameraRay = new Vec3D(0,0,0);
            vCameraRay = line1.subtractVector(triTrans.vec3d, vCamera);
            
            //HOW MUCH IS EACH TRIANGLE'S SURFACE NORMAL PROJECTING ONTO THE CAMERA
            if(line1.dotProduct(normal, vCameraRay) < 0.0)
            {
             //DIRECTIONAL LIGHTING THAT SPECIFIES A DIRECTION AS TO WHERE LIGHT SHOULD PROJECT FROM
             Vec3D light_direction = new Vec3D(0, 0, -1);
             light_direction = line1.normalize(light_direction);
             
             double dp = Math.max(0.1, line1.dotProduct(light_direction, normal));
             
             //CONVERT WORLD SPACE TO VIEW SPACE
             triViewed.vec3d = matView.multiplyMatrixVector(triTrans.vec3d, matView);
             triViewed.vec3d2 = matView.multiplyMatrixVector(triTrans.vec3d2, matView);
             triViewed.vec3d3 = matView.multiplyMatrixVector(triTrans.vec3d3, matView);
             triViewed.vec2d = triTrans.vec2d;
             triViewed.vec2d2 = triTrans.vec2d2;
             triViewed.vec2d3 = triTrans.vec2d3;
             
              int nClippedTriangles = 0;
                Triangle[] clipped = new Triangle[]{new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0))
                        ,new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0))};

                
                nClippedTriangles = line1.triangleClipAgainstPlane(new Vec3D(0,0,6.1f), new Vec3D(0,0,1
                ),triViewed, clipped);
                
              for(int i = 0; i < nClippedTriangles; i++)
              {
                  //PROJECT 3D GEOMETRICAL DATA TO NORMALIZED 2D SCREEN
                    triProjected.vec3d = mat.multiplyMatrixVector(clipped[i].vec3d, matProj);
                    triProjected.vec3d2 = mat.multiplyMatrixVector(clipped[i].vec3d2, matProj);
                    triProjected.vec3d3 = mat.multiplyMatrixVector(clipped[i].vec3d3, matProj);
                    triProjected.vec2d = clipped[i].vec2d;
                    triProjected.vec2d2 = clipped[i].vec2d2;
                    triProjected.vec2d3 = clipped[i].vec2d3;
          
                    //SCALE INTO VIEW
                    triProjected.vec3d.x += 1.0;
                    triProjected.vec3d2.x += 1.0;
                    triProjected.vec3d3.x += 1.0;
                    triProjected.vec3d.y += 1.0;
                    triProjected.vec3d2.y += 1.0;
                    triProjected.vec3d3.y += 1.0;
            
                    triProjected.vec3d.x *= 0.5 * 800;
                    triProjected.vec3d.y *= 0.5 * 600;
                    triProjected.vec3d2.x *= 0.5 * 800;
                    triProjected.vec3d2.y *= 0.5 * 600;
                    triProjected.vec3d3.x *= 0.5 * 800;
                    triProjected.vec3d3.y *= 0.5 * 600;
             
                    triProjected.color((int)Math.abs(dp*255),(int)Math.abs(dp*255),(int)Math.abs(dp*255));
             
                    vecTrianglesToRaster.add(triProjected);
              }
             
            }
        }    
        
        Collections.sort((ArrayList<Triangle>)vecTrianglesToRaster, new Comparator<Triangle>() {
                @Override
                public int compare(Triangle t1, Triangle t2) {
                    double z1=(t1.vec3d.z+t1.vec3d2.z+t1.vec3d3.z)/3.0;
                    double z2=(t2.vec3d.z+t2.vec3d2.z+t2.vec3d3.z)/3.0;
                    return (z1<z2)?1:(z1==z2)?0:-1;
                }
            });
  
        for(Triangle t: vecTrianglesToRaster)
        {
                 g.setColor(Color.black);
                 //drawTriangle(g2, t.vec3d.x, t.vec3d.y, t.vec3d2.x, t.vec3d2.y, t.vec3d3.x, t.vec3d3.y);
                 
                // texturedTriangle(g2, (int)t.vec3d.x,(int)t.vec3d.y, t.vec2d.u, t.vec2d.v,(int)t.vec3d2.x,(int)t.vec3d2.y,
               //t.vec2d2.u, t.vec2d2.v,(int)t.vec3d3.x,(int)t.vec3d3.y, t.vec2d3.u, t.vec2d3.v, img);
                
                 Polygon triangle = new Polygon();
                 triangle.addPoint((int)t.vec3d.x,(int)t.vec3d.y);
                 triangle.addPoint((int)t.vec3d2.x,(int)t.vec3d2.y);
                 triangle.addPoint((int)t.vec3d3.x,(int)t.vec3d3.y);

                 g.setColor(t.col);
                 g2.fill(triangle);
        } 
    }
}
