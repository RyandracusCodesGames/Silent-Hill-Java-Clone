
package com.ryancodesgames.silenthill.math;

public class Matrix 
{
    double[][] matrix = new double[4][4];
    
    public Matrix(double[][] matrix)
    {
        this.matrix = matrix;
    }
    
    public Matrix()
    {
        
    }
    
    public Vec3D multiplyMatrixVector(Vec3D in, Matrix m)
    {
        Vec3D out = new Vec3D(0,0,0);
        
        out.x = in.x * m.matrix[0][0] + in.y * m.matrix[1][0] + in.z * m.matrix[2][0] + m.matrix[3][0];
        out.y = in.x * m.matrix[0][1] + in.y * m.matrix[1][1] + in.z * m.matrix[2][1] + m.matrix[3][1];
        out.z = in.x * m.matrix[0][2] + in.y * m.matrix[1][2] + in.z * m.matrix[2][2] + m.matrix[3][2];
        
        double w = in.x * m.matrix[0][3] + in.y * m.matrix[1][3] + in.z * m.matrix[2][3] + m.matrix[3][3];
        
        if(w != 0.0)
        {
            out.x /= w;
            out.y /= w;
            out.z /= w;
        }
        
        return out;
    }
    
    public Matrix projectionMatrix(double fNear, double fFar, double a, double fov)
    {
        double fovRad = 1.00f/Math.tan(fov*0.50/Math.PI*180.00);
        
        Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        
        mat.matrix[0][0] = a * fovRad;
	mat.matrix[1][1] = fovRad;
	mat.matrix[2][2] = fFar / (fFar - fNear);
	mat.matrix[3][2] = 1.0;
	mat.matrix[2][3] = (-fFar * fNear) / (fFar - fNear);
	mat.matrix[3][3] = 0.0;
        
	return mat;
    }
    
    public Matrix rotationMatrixX(double angle)
    {
        Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        
        mat.matrix[0][0] = 1.0;
        mat.matrix[1][1] = Math.cos(angle);
        mat.matrix[1][2] = Math.sin(angle);
        mat.matrix[2][1] = -Math.sin(angle);
        mat.matrix[2][2] = Math.cos(angle);
        mat.matrix[3][3] = 1.0;
        
        return mat;
    }
    
    public Matrix rotationMatrixY(double angle)
    {
        Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        
        mat.matrix[0][0] = Math.cos(angle);
        mat.matrix[0][2] = Math.sin(angle);
        mat.matrix[2][0] = -Math.sin(angle);
        mat.matrix[1][1] = 1.0;
        mat.matrix[2][2] = Math.cos(angle);
        mat.matrix[3][3] = 1.0;
        
        return mat;
    }
    
    public Matrix rotationMatrixZ(double angle)
    {
        Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        
        mat.matrix[0][0] = Math.cos(angle);
        mat.matrix[0][1] = Math.sin(angle);
        mat.matrix[1][0] = -Math.sin(angle);
        mat.matrix[1][1] = Math.cos(angle);
        mat.matrix[2][2] = 1.0;
        mat.matrix[3][3] = 1.0;
        
        return mat;
    }
    
    public Matrix identityMatrix()
    {
         Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
         
         mat.matrix[0][0] = 1.0;
         mat.matrix[1][1] = 1.0;
         mat.matrix[2][2] = 1.0;
         mat.matrix[3][3] = 1.0;
         
         return mat;
    }
    
    public Matrix translationMatrix(double x, double y, double z)
    {
         Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
         
         mat.matrix[0][0] = 1.0;
         mat.matrix[0][0] = 1.0;
         mat.matrix[1][1] = 1.0;
         mat.matrix[2][2] = 1.0;
         mat.matrix[3][3] = 1.0;
         mat.matrix[3][0] = x;
         mat.matrix[3][1] = y;
         mat.matrix[3][2] = z;
         
         return mat;
    }
    
    public Matrix matrixMatrixMultiplication(Matrix m1, Matrix m2)
    {
        Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        
        for(int c = 0; c < 4; c++)
        {
            for(int r = 0; r < 4; r++)
            {
                mat.matrix[r][c] = m1.matrix[r][0] * m2.matrix[0][c] + m1.matrix[r][1] * m2.matrix[1][c] +
                m1.matrix[r][2] * m2.matrix[2][c] + m1.matrix[r][3] * m2.matrix[3][c];
            }
        }
        
        return mat;
    }
    
     public Matrix pointAtMatrix(Vec3D pos, Vec3D target, Vec3D up)
    {
        //CALCULATE THE NEW FOWARD DIRECTION
        Vec3D newForward = new Vec3D(0,0,0);
        
        newForward = newForward.subtractVector(target, pos);
        newForward = newForward.normalize(newForward);
        
        //CALCULATE THE NEW UP DIRECTION
        Vec3D a = new Vec3D(0,0,0);
        Vec3D newUp = new Vec3D(0,0,0);
        
        a = a.multiplyVector(newForward, a.dotProduct(up, newForward));
        newUp = a.subtractVector(up, a);
        newUp = a.normalize(newUp);
        
        //NEW RIGHT DIRECTION JUST TAKES THE CROSS PRODUCT
        Vec3D newRight = new Vec3D(0,0,0);
        newRight = a.crossProduct(newUp, newForward);
        
        //MANUALLY CONSTRUCT POINT AT MATRIX, THE DIMENSION AND TRANSITION
        Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
        
        mat.matrix[0][0] = newRight.x;	mat.matrix[0][1] = newRight.y;	mat.matrix[0][2] = newRight.z;	mat.matrix[0][3] = 0.0;
	mat.matrix[1][0] = newUp.x;	mat.matrix[1][1] = newUp.y;	mat.matrix[1][2] = newUp.z;		mat.matrix[1][3] = 0.0;
	mat.matrix[2][0] = newForward.x; mat.matrix[2][1] = newForward.y;mat.matrix[2][2] = newForward.z;	mat.matrix[2][3] = 0.0;
	mat.matrix[3][0] = pos.x;	mat.matrix[3][1] = pos.y;	mat.matrix[3][2] = pos.z;	mat.matrix[3][3] = 1.0;
        
	return mat;
    }
    
    public Matrix inverseMatrix(Matrix m)
    {
        Matrix mat = new Matrix(new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
	mat.matrix[0][0] = m.matrix[0][0]; mat.matrix[0][1] = m.matrix[1][0]; mat.matrix[0][2] = m.matrix[2][0]; mat.matrix[0][3] = 0.0;
	mat.matrix[1][0] = m.matrix[0][1]; mat.matrix[1][1] = m.matrix[1][1]; mat.matrix[1][2] = m.matrix[2][1]; mat.matrix[1][3] = 0.0;
	mat.matrix[2][0] = m.matrix[0][2]; mat.matrix[2][1] = m.matrix[1][2]; mat.matrix[2][2] = m.matrix[2][2]; mat.matrix[2][3] = 0.0;
	mat.matrix[3][0] = -(m.matrix[3][0] * mat.matrix[0][0] + m.matrix[3][1] * mat.matrix[1][0] + m.matrix[3][2] * mat.matrix[2][0]);
	mat.matrix[3][1] = -(m.matrix[3][0] * mat.matrix[0][1] + m.matrix[3][1] * mat.matrix[1][1] + m.matrix[3][2] * mat.matrix[2][1]);
	mat.matrix[3][2] = -(m.matrix[3][0] * mat.matrix[0][2] + m.matrix[3][1] * mat.matrix[1][2] + m.matrix[3][2] * mat.matrix[2][2]);
	mat.matrix[3][3] = 1.0;
        
	return mat;
    }
}
