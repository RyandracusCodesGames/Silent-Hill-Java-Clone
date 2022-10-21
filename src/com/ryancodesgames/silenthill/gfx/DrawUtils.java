
package com.ryancodesgames.silenthill.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class DrawUtils 
{
    public static void drawTriangle(Graphics2D g2, double x1, double y1, double x2, double y2, double x3, double y3)
    {
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        g2.draw(new Line2D.Double(x2, y2, x3, y3));
        g2.draw(new Line2D.Double(x3, y3, x1, y1));
    }
    
    public static void texturedTriangle(Graphics2D g2, int x1, int y1, double u1, double v1, int x2, int y2, double
    u2, double v2, int x3, int y3, double u3, double v3, BufferedImage img, double visibility, boolean fog, int[] pix)
    {
       // short[] doubleBufferData;
      //  DataBuffer dest = img.getRaster().getDataBuffer();
       // doubleBufferData = ((DataBufferUShort)dest).getData();
     // PowerOf2Texture power = (PowerOf2Texture)createTexture(img);
        
        byte[] pixels = ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
        int pixelLength = 3;
        int width = img.getWidth();
        int height = img.getHeight();
        boolean hasAlphaChannel = img.getAlphaRaster() != null;
        pixelLength = 3;
        if (hasAlphaChannel)
        {
            pixelLength = 4;
        }
        
        
        if(y2 < y1)
        {
            int temp = y1;
            y1 = y2;
            y2 = temp;
            
            int tempx = x1;
            x1 = x2;
            x2 = tempx;
            
            double tempu = u1;
            u1 = u2;
            u2 = tempu;
            
            double tempv = v1;
            v1 = v2;
            v2 = tempv;
        }
        
        if(y3 < y1)
        {
            int temp = y1;
            y1 = y3;
            y3 = temp;
            
            int tempx = x1;
            x1 = x3;
            x3 = tempx;
            
            double tempu = u1;
            u1 = u3;
            u3 = tempu;
            
            double tempv = v1;
            v1 = v3;
            v3 = tempv;
        }
        
        if(y3 < y2)
        {
            int temp = y2;
            y2 = y3;
            y3 = temp;
            
            int tempx = x2;
            x2 = x3;
            x3 = tempx;
            
            double tempu = u2;
            u2 = u3;
            u3 = tempu;
            
            double tempv = v2;
            v2 = v3;
            v3 = tempv;
        }
        
        int dy1 = y2 - y1;
        int dx1 = x2 - x1;
        double dv1 = v2 - v1;
        double du1 = u2 - u1;
        
        int dy2 = y3 - y1;
        int dx2 = x3 - x1;
        double dv2 = v3 - v1;
        double du2 = u3 - u1;
        
        double tex_u, tex_v;
        
        double dax_step = 0, dbx_step = 0, du1_step = 0, dv1_step = 0, du2_step = 0, dv2_step = 0;
        
         if (dy1 != 0) dax_step = dx1 / (float)Math.abs(dy1);
         if (dy2 != 0) dbx_step = dx2 / (float)Math.abs(dy2);

	 if (dy1 != 0) du1_step = du1 / (float)Math.abs(dy1);
	 if (dy1 != 0) dv1_step = dv1 / (float)Math.abs(dy1);
 
	 if (dy2 != 0) du2_step = du2 / (float)Math.abs(dy2);
	 if (dy2 != 0) dv2_step = dv2 / (float)Math.abs(dy2);
         
         if(dy1 != 0)
         {
             for(int i = y1; i <= y2; i++)
             {
                 int ax = (int)(x1 + (i - y1) * dax_step);
		 int bx = (int)(x1 + (i - y1) * dbx_step);
                 
                 double tex_su = u1 + (float)(i - y1) * du1_step;
		 double tex_sv = v1 + (float)(i - y1) * dv1_step;
                 
                 double tex_eu = u1 + (float)(i - y1) * du2_step;
		 double tex_ev = v1 + (float)(i - y1) * dv2_step;
                 
                 if(ax > bx)
                 {
                     int temp = ax;
                     ax = bx;
                     bx = temp;
                     
                     double temps = tex_su;
                     tex_su = tex_eu;
                     tex_eu = temps;
                     
                     double tempv = tex_sv;
                     tex_sv = tex_ev;
                     tex_ev = tempv;
                 }
                 
                 tex_u = tex_su;
                 tex_v = tex_sv;
                 
                 double tstep = 1.0 / (float)(bx-ax);
                 double t = 0.0;

                 
                 for(int j = ax; j < bx; j++)
                 {
                     tex_u = (1.0 - t) * tex_su + t * tex_eu;
                     tex_v = (1.0 - t) * tex_sv + t * tex_ev;
                     
                     Color background = Color.black;
                     Color col = new Color(img.getRGB(
                          (int)Math.max(0,tex_u*(img.getWidth()-1)),
                          (int)Math.max(0,tex_v*(img.getHeight()-1))
                        ));
                     
                     if(fog)
                     {
                         col = blend(background, col,(float)visibility);
                         g2.setColor(col); 
                     }
                     
                     else
                     {
                        // g2.setColor(new Color(getRGB((int)Math.max(0,tex_u*(img.getWidth()-1)),(int)Math.max(0,tex_v*(img.getHeight()-1)), width, height, pixelLength, pixels, hasAlphaChannel)));
                     }
                    
                      
                     draw(pix, j, i, col);
                     
                     t += tstep;
                 }

             }

         }
         
         dy1 = y3 - y2;
             dx1 = x3 - x2;
             dv1 = v3 - v2;
             du1 = u3 - u2;
             
             if (dy1 != 0) dax_step = dx1 / (float)Math.abs(dy1);
	     if (dy2 != 0) dbx_step = dx2 / (float)Math.abs(dy2);

             du1_step = 0; dv1_step = 0;
             
             if (dy1 != 0) du1_step = du1 /(float)Math.abs(dy1);
             if (dy1 != 0) dv1_step = dv1 / (float)Math.abs(dy1);
             
             if(dy1 != 0)
         {
             for(int i = y2; i <= y3; i++)
             {
                 int ax = (int)(x2 + (float)(i - y2) * dax_step);
		 int bx = (int)(x1 + (float)(i - y1) * dbx_step);
                 
                 double tex_su = u2 + (float)(i - y2) * du1_step;
		 double tex_sv = v2 + (float)(i - y2) * dv1_step;
                 
                 double tex_eu = u1 + (float)(i - y1) * du2_step;
		 double tex_ev = v1 + (float)(i - y1) * dv2_step;
                 
                 if(ax > bx)
                 {
                     int temp = ax;
                     ax = bx;
                     bx = temp;
                     
                     double temps = tex_su;
                     tex_su = tex_eu;
                     tex_eu = temps;
                     
                     double tempv = tex_sv;
                     tex_sv = tex_ev;
                     tex_ev = tempv;
                 }
                 
                 tex_u = tex_su;
                 tex_v = tex_sv;
                 
                 double tstep = 1.0/ (float)(bx-ax);
                 double t = 0.0;
                 
                 for(int j = ax; j < bx; j++)
                 {
                     tex_u = (1.0 - t) * tex_su + t * tex_eu;
                     tex_v = (1.0 - t) * tex_sv + t * tex_ev;
                     
                     Color background = Color.black;
                     Color col = new Color(img.getRGB(
                          (int)Math.max(0,tex_u*(img.getWidth()-1)),
                          (int)Math.max(0,tex_v*(img.getHeight()-1))
                        ));

                    if(fog)
                     {
                         col = blend(background, col,(float)visibility);
                         g2.setColor(col); 
                     }
                     
                     else
                     {
                        // g2.setColor(new Color(getRGB((int)Math.max(0,tex_u*(img.getWidth()-1)),(int)Math.max(0,tex_v*(img.getHeight()-1)), width, height, pixelLength, pixels, hasAlphaChannel)));
                     }
                      
                     draw(pix, j, i, col);

                     t += tstep;
                     
                 }
             }

         }
    }
    
    public static Color blend( Color c1, Color c2, float ratio ) 
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
    
   public static int getRGB(int x, int y, int width, int height, int pixelLength, byte[] pixels, boolean hasAlphaChannel)
    {
        int pos = (y * pixelLength * width) + (x * pixelLength);

        int argb = -16777216; // 255 alpha
        if (hasAlphaChannel)
        {
            argb = (((int) pixels[pos++] & 0xff) << 24); // alpha
        }

        argb += ((int) pixels[pos++] & 0xff); // blue
        argb += (((int) pixels[pos++] & 0xff) << 8); // green
        argb += (((int) pixels[pos++] & 0xff) << 16); // red
        return argb;
    }
   
   public static void draw(int[] pixels, int x, int y, Color col)
   {
       if(x >= 0 && y >= 0 && x <= 800 && y <= 600)
       {
           pixels[x + y * 800] = col.getRGB();
       }
   }
   
}
