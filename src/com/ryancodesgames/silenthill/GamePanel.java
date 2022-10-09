
package com.ryancodesgames.silenthill;

import com.ryancodesgames.silenthill.gameobject.Cube;
import com.ryancodesgames.silenthill.gameobject.Pyramid;
import static com.ryancodesgames.silenthill.gfx.DrawUtils.drawTriangle;
import static com.ryancodesgames.silenthill.gfx.DrawUtils.texturedTriangle;
import com.ryancodesgames.silenthill.math.Matrix;
import com.ryancodesgames.silenthill.math.Mesh;
import com.ryancodesgames.silenthill.math.Triangle;
import com.ryancodesgames.silenthill.math.Vec2D;
import com.ryancodesgames.silenthill.math.Vec3D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable
{
    Thread gameThread;
    //CLASS THAT HANDLES KEYBOARD INPUT
    KeyHandler keyH = new KeyHandler();
    //FRAMES PER SECOND
    double fps = 240;
    //PROJECTION MATRIX DATA
    double fNear = 0.1; //NEAREAST CLIP PLANE
    double fFar = 1000.00; //FURTHERST CLIP PLANE
    double fov = 90.0; //FIELD OF VIEW
    double a = 600.00/800.00; //ASPECT RATIO OF HEIGHT/WIDTH  
    Matrix mat = new Matrix();
    //PROJECTION MATRIX
    Matrix matProj = mat.projectionMatrix(fNear, fFar, a, fov);
    //ROTATION MATRICES
    Matrix matZ, matZX;
    //ANGLE USED IN ROTATION MATRICES
    double fTheta;
    //COLLECTION OF TRIANGLES THAT FORM AN OBJECT
    Mesh meshCube;
    Mesh meshCube2;
    Mesh meshCube3;
    Mesh meshCube4;
    Mesh meshCube5;
    Mesh meshDoor;
    
    Mesh m =  new Mesh();
    //STATIONARY POSTION VECTOR THAT WILL SERVE AS THE CAMERA
    Vec3D vCamera = new Vec3D(0,0,0);
    //CAMERA THAT FOLLOWS ALONG THE LOOK AT DIRECTION
    Vec3D vLookDir = new Vec3D(0,0,1);
    //ROTATION AROUND Y-AXIS FOR CAMERA
    double fYaw = 0;
    
    BufferedImage img;
    
    double[] pDepthBuffer;
    
    Pyramid pyramid = new Pyramid(-5, 10, 0, 10, 10, 10);
    
    public GamePanel()
    {
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        initializeMesh();
        getRGB();
    }
    
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run()
    {
        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        int timer = 0;
        int drawCount = 0;
  
        while(gameThread != null)
        {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            
            lastTime = currentTime;
            
            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000)
            {
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void initializeMesh()
    {
        //LOCAL CACHE OF VERTICES
        List<Vec3D> verts = new ArrayList<>();
        
        List<Triangle> tris = new ArrayList<>();
        
        tris = m.ReadOBJFile("m.txt", true);
//        
       meshCube = new Mesh(tris);
//        
            Cube cube = new Cube(-5, 0, 0, 0.2, 10, 10);
            Cube cube2 = new Cube(4.8, 0, 0, 0.2, 10, 10);
            Cube cube3 = new Cube(-5, 0, 0, 10, 10, 0.2);
            Cube cube4 = new Cube(-5, 0, 10, 10, 10, 0.2);
            Cube cube5 = new Cube(-1, 0, 11, 2, 5, 0.1);
            
      //  meshCube = new Mesh(pyramid.meshPyramid.triangles);
        meshCube2 = new Mesh(cube2.meshCube.triangles);
        meshCube3 = new Mesh(cube.meshCube.triangles);
        meshCube4 = new Mesh(cube3.meshCube.triangles);
        meshCube5 = new Mesh(cube4.meshCube.triangles);
        meshDoor = new Mesh(cube5.meshCube.triangles);
//
		// meshCube = new Mesh(Arrays.asList(
//                new Triangle[]{
//                    //SOUTH
//                    new Triangle(new Vec3D(0,0,0), new Vec3D(0,1,0), new Vec3D(1,1,0), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
//                    new Triangle(new Vec3D(0,0,0), new Vec3D(1,1,0), new Vec3D(1,0,0), new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0)),
//                    //EAST
//                    new Triangle(new Vec3D(1,0,0), new Vec3D(1,1,0), new Vec3D(1,1,1), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
//                    new Triangle(new Vec3D(1,0,0), new Vec3D(1,1,1), new Vec3D(1,0,1), new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0)),
//                    //NORTH
//                    new Triangle(new Vec3D(1,0,1), new Vec3D(1,1,1), new Vec3D(0,1,1), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
//                    new Triangle(new Vec3D(1,0,1), new Vec3D(0,1,1), new Vec3D(0,0,1), new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0)),
//                    //WEST
//                    new Triangle(new Vec3D(0,0,1), new Vec3D(0,1,1), new Vec3D(0,1,0), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
//                    new Triangle(new Vec3D(0,0,1), new Vec3D(0,1,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0)),
//                    //TOP
//                    new Triangle(new Vec3D(0,1,0), new Vec3D(0,1,1), new Vec3D(1,1,1), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
//                    new Triangle(new Vec3D(0,1,0), new Vec3D(1,1,1), new Vec3D(1,1,0), new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0)),
//                    //BOTTOM
//                    new Triangle(new Vec3D(1,0,1), new Vec3D(0,0,1), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
//                    new Triangle(new Vec3D(1,0,1), new Vec3D(0,0,0), new Vec3D(1,0,0), new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0))
//                }));
                 
    }
    
     public void getRGB()
    {
        try
        {
            img = ImageIO.read(getClass().getResource("/com/ryancodesgames/silenthill/gfx/t_1.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
   
    }
    
    public void update()
    {
        if(keyH.leftPressed)
        {
            vCamera.x -= 0.25;
        }
        
        if(keyH.rightPressed)
        {
            vCamera.x += 0.25;
        }
        
        if(keyH.upPressed)
        {
            vCamera.y -= 0.25;
        }
        
        if(keyH.downPressed)
        {
            vCamera.y += 0.25;
        }
        
        Vec3D vFoward = new Vec3D(0,0,0);
        vFoward = vFoward.multiplyVector(vLookDir, 1);
        
        if(keyH.frontPressed)
        {
            vCamera = vFoward.addVector(vCamera, vFoward);
        }
        
        if(keyH.backPressed)
        {
            vCamera = vFoward.subtractVector(vCamera, vFoward);
        }
        
        if(keyH.rightTurn)
        {
            fYaw -= 0.008;
        }
        
        if(keyH.leftTurn)
        {
            fYaw += 0.008;
        }
        
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //FILL SCREEEN BLACK
        Color backgroundColor = Color.black;
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 800, 600);
        
        Graphics2D g2 = (Graphics2D)g;
        
        fTheta += 0.02;
        
        //ROTATION MATRICES
        matZ = mat.rotationMatrixZ(fTheta * 0.5);
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
        mesh.add(meshCube);
       // mesh.add(meshCube2);
      //  mesh.add(meshCube3);
      //  mesh.add(meshCube4);
      //  mesh.add(meshCube5);
      //  mesh.add(meshDoor);
        
        List<Triangle> vecTrianglesToRaster = new ArrayList<>();
        
        for(Mesh meshCube: mesh)
        {
                for(Triangle tri: meshCube.triangles)
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

                
                nClippedTriangles = line1.triangleClipAgainstPlane(new Vec3D(0,0,0.1), new Vec3D(0,0,1
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
            Triangle[] clipped = new Triangle[]{new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0)),
                new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0))};;
            
            LinkedList<Triangle> listTriangles = new LinkedList<>();
            listTriangles.add(t);
            int nNewTriangles = 1;
            
            for(int p = 0; p < 4; p++)
            {
                int trisToAdd = 0;
                
                while(nNewTriangles > 0)
                {
                    Triangle test = new Triangle(new Vec3D(0,0,0),new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0));
                    test = listTriangles.peek();
                    listTriangles.pollFirst();
                    nNewTriangles--;
                    
                    Vec3D vec = new Vec3D(0,0,0);  
                    
                    switch(p)
                    {
                        case 0:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(0,0,0),new Vec3D(0,1,0),test,clipped);}break;
                        case 1:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(0,600-1,0),new Vec3D(0,-1,0),test,clipped);}break;
                        case 2:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(0,0,0),new Vec3D(1,0,0),test,clipped);}break;
                        case 3:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(800-1,0,0),new Vec3D(-1,0,0),test,clipped);}break;
                    }
                      
                    for (int w = 0; w < trisToAdd; w++)
                    {
                        listTriangles.add(clipped[w]);
                    }
                }
                 nNewTriangles = listTriangles.size();
            }
     
            for(Triangle tt: listTriangles)
            {
                Vec3D v = new Vec3D(0,0,0);
                
                double density = 0.0035;
                double gradient = 2.0;
                    
                Vec3D distFromCamera = new Vec3D(0,0,0);
                distFromCamera = v.subtractVector(tt.vec3d, vCamera);
                    
                double distance = v.vectorLength(distFromCamera);
                    
                double visibility = Math.exp(-Math.pow(distance*density,gradient));
                visibility = Math.min(Math.max(visibility, 0.0), 1.0);
                
               // tt.col = blend(backgroundColor, tt.col, (float)visibility);
             
               texturedTriangle(g2, (int)tt.vec3d.x,(int)tt.vec3d.y, tt.vec2d.u, tt.vec2d.v,(int)tt.vec3d2.x,(int)tt.vec3d2.y,
                tt.vec2d2.u, tt.vec2d2.v,(int)tt.vec3d3.x,(int)tt.vec3d3.y, tt.vec2d3.u, tt.vec2d3.v,
                img, visibility, false);
               
                
                g2.setColor(Color.white); 
                drawTriangle(g2, tt.vec3d.x, tt.vec3d.y, tt.vec3d2.x,
                tt.vec3d2.y, tt.vec3d3.x, tt.vec3d3.y
               );
//                
//                //TURN 3D VECTOR X AND Y COORDINATES INTO A POLYGON THAT WILL FILL EACH SURFACE
//                Polygon triangle = new Polygon();
//                triangle.addPoint((int)tt.vec3d.x,(int)tt.vec3d.y);
//                triangle.addPoint((int)tt.vec3d2.x,(int)tt.vec3d2.y);
//                triangle.addPoint((int)tt.vec3d3.x,(int)tt.vec3d3.y);
//                
//
//                g.setColor(tt.col);
//                g.fillPolygon(triangle);
//  

            }
        }
        }
        
        
        g.dispose();
    
    }
    
public Color blend( Color c1, Color c2, float ratio ) 
{
    if ( ratio > 1f ) ratio = 1f;
    else if ( ratio < 0f ) ratio = 0f;
    float iRatio = 1.0f - ratio;

    int i1 = c1.getRGB();
    int i2 = c2.getRGB();

    int a1 = (i1 >> 24 & 0xff);
    int r1 = ((i1 & 0xff0000) >> 16);
    int g1 = ((i1 & 0xff00) >> 8);
    int b1 = (i1 & 0xff);

    int a2 = (i2 >> 24 & 0xff);
    int r2 = ((i2 & 0xff0000) >> 16);
    int g2 = ((i2 & 0xff00) >> 8);
    int b2 = (i2 & 0xff);

    int a = (int)((a1 * iRatio) + (a2 * ratio));
    int r = (int)((r1 * iRatio) + (r2 * ratio));
    int g = (int)((g1 * iRatio) + (g2 * ratio));
    int b = (int)((b1 * iRatio) + (b2 * ratio));

    return new Color( a << 24 | r << 16 | g << 8 | b );
}
}