
package com.ryancodesgames.silenthill.math;

public class Transformation 
{
   //ROTATION TRANSFORMATION ANGLES
   private double rotationAngleX;
   private double rotationAngleY;
   private double rotationAngleZ;
   //TRANSLATION MATRIX DATA
   private double transX;
   private double transY;
   private double transZ;
   //POINT TO UNIFORMALLY APPLY TRANSFORMATIONS TO
   private Vec3D origin;
   //TRANSLATION MATRIX
   private Matrix translationMatrix;
   //ACCUMULATION OF ALL TRANSFORMATIONS
   private Matrix matWorld;
   
   //MUTATORS
   public void setRotAngleX(double angle)
   {
       rotationAngleX = angle;
   }
   
   public void setRotAngleY(double angle)
   {
       rotationAngleY = angle;
   }
   
   public void setRotAngleZ(double angle)
   {
       rotationAngleZ = angle;
   }
   
   public void setTransX(double x)
   {
       transX = x;
   }
   
   public void setTransY(double y)
   {
       transY = y;
   }
   
   public void setTransZ(double z)
   {
       transZ = z;
   }
   
   public void setOriginPoint(Vec3D center)
   {
       center = origin;
   }

   public Matrix getWorldMatrix()
   {
       Matrix m = new Matrix();
       
       Matrix matRotZ = m.rotationMatrixZ(getRotAngleZ());
       Matrix matRotZX = m.rotationMatrixX(getRotAngleX());
       Matrix matYaw  = m.rotationMatrixY(getRotAngleY());
       
       Matrix matWorld = new Matrix();
       matWorld = matWorld.identityMatrix();
       matWorld = matWorld.matrixMatrixMultiplication(matRotZ, matRotZX);
       matWorld = matWorld.matrixMatrixMultiplication(matWorld, matYaw);
       matWorld = matWorld.matrixMatrixMultiplication(matWorld, translationMatrix());
       
       return matWorld;
   }
   
   public Matrix translationMatrix()
   {
       Matrix m = new Matrix();
       translationMatrix = m.translationMatrix(getTransX(), getTransY(), getTransZ());
       
       return translationMatrix;
   }

   //BASIC GETTERS
   public double getRotAngleX()
   {
       return rotationAngleX;
   }
   
   public double getRotAngleY()
   {
       return rotationAngleY;
   }
   
   public double getRotAngleZ()
   {
       return rotationAngleZ;
   }
   
   public double getTransX()
   {
       return transX;
   }
   
   public double getTransY()
   {
       return transY;
   }
   
   public double getTransZ()
   {
       return transZ;
   }
   
   public Vec3D getOrigin()
   {
       return origin;
   }
}
