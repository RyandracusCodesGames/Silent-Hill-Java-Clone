
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
    
    public static void TexturedTriangle(Graphics2D g2, int x1, int y1, double u1, double v1, double w1, int x2, int y2, double
    u2, double v2, double w2, int x3, int y3, double u3, double v3, double w3, BufferedImage img, double visibility, boolean fog, int[] pix, double[] zBuffer, int[] texArray)
    {
//        short[] doubleBufferData;
//        DataBuffer dest = img.getRaster().getDataBuffer();
//        doubleBufferData = ((DataBufferUShort)dest).getData();
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
            
            double tempw = w1;
            w1 = w2;
            w2 = tempw;
            
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
            
            double tempw = w1;
            w1 = w3;
            w3 = tempw;
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
            
            double tempw = w2;
            w2 = w3;
            w3 = tempw;
            
        }
        
        int dy1 = y2 - y1;
        int dx1 = x2 - x1;
        double dv1 = v2 - v1;
        double du1 = u2 - u1;
        double dw1 = w2 - w1;
        
        int dy2 = y3 - y1;
        int dx2 = x3 - x1;
        double dv2 = v3 - v1;
        double du2 = u3 - u1;
        double dw2 = w3 - w1;
        
        double tex_u, tex_v, tex_w;
        
        double dax_step = 0, dbx_step = 0, du1_step = 0, dv1_step = 0, du2_step = 0, dv2_step = 0, dw1_step = 0, dw2_step = 0;
        
         if (dy1 != 0) dax_step = dx1 / (float)Math.abs(dy1);
         if (dy2 != 0) dbx_step = dx2 / (float)Math.abs(dy2);

	 if (dy1 != 0) du1_step = du1 / (float)Math.abs(dy1);
	 if (dy1 != 0) dv1_step = dv1 / (float)Math.abs(dy1);
         if (dy1 != 0) dw1_step = dw1 / (float)Math.abs(dy1);
 
	 if (dy2 != 0) du2_step = du2 / (float)Math.abs(dy2);
	 if (dy2 != 0) dv2_step = dv2 / (float)Math.abs(dy2);
         if (dy2 != 0) dw2_step = dw2 / (float)Math.abs(dy2);
         
         if(dy1 != 0)
         {
             for(int i = y1; i <= y2; i++)
             {
                 int ax = (int)(x1 + (i - y1) * dax_step);
		 int bx = (int)(x1 + (i - y1) * dbx_step);
                 
                 double tex_su = u1 + (float)(i - y1) * du1_step;
		 double tex_sv = v1 + (float)(i - y1) * dv1_step;
                 double tex_sw = w1 + (float)(i - y1) * dw1_step;
                 
                 double tex_eu = u1 + (float)(i - y1) * du2_step;
		 double tex_ev = v1 + (float)(i - y1) * dv2_step;
                 double tex_ew = w1 + (float)(i - y1) * dw2_step;
                 
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
                     
                     double tempw = tex_sw;
                     tex_sw = tex_ew;
                     tex_ew = tempw;
                 }
                 
                 tex_u = tex_su;
                 tex_v = tex_sv;
                 tex_w = tex_sw;
                 
                 double tstep = 1.0 / (float)(bx-ax);
                 double t = 0.0;

                 
                 for(int j = ax; j < bx; j++)
                 {
                    tex_u = (1.0 - t) * tex_su + t * tex_eu;
                    tex_v = (1.0 - t) * tex_sv + t * tex_ev;
                    tex_w = (1.0 - t) * tex_sw + t * tex_ew;

                    if(Math.abs(tex_w) > zBuffer[i * 800 + j])
                    {
                        int iu = (int)(tex_u/tex_w) & getWidthShift(img);
                        int iv = (int)(tex_v/tex_w) & getHeightShift(img);
                        //THIS IS HOW I GRABBED RGB'S FROM COLOR CLASS BUT IS A BIT INCORRECT NOW
                        //I DON'T KNOW HOW TO GRAB THE RIGHT RGB'S VALUES ANYMORE
                        int col = texArray[(int)Math.max(0,tex_u/tex_w*(img.getWidth()-1)) + (int)Math.max(0,tex_v/tex_w*(img.getHeight()-1))];


                        if(fog)
                        {
                            
                        }

                        else
                        {
                           // g2.setColor(new Color(getRGB((int)Math.max(0,tex_u*(img.getWidth()-1)),(int)Math.max(0,tex_v*(img.getHeight()-1)), width, height, pixelLength, pixels, hasAlphaChannel)));
                        }


                        draw(pix, j, i, col);
                        zBuffer[i * 800 + j] = Math.abs(tex_w);
                    }
                        
                    t += tstep;
                 }

             }

         }
         
         dy1 = y3 - y2;
             dx1 = x3 - x2;
             dv1 = v3 - v2;
             du1 = u3 - u2;
             dw1 = w3 - w2;
             
             if (dy1 != 0) dax_step = dx1 / (float)Math.abs(dy1);
	     if (dy2 != 0) dbx_step = dx2 / (float)Math.abs(dy2);

             du1_step = 0; dv1_step = 0; dw1_step = 0;
             
             if (dy1 != 0) du1_step = du1 /(float)Math.abs(dy1);
             if (dy1 != 0) dv1_step = dv1 / (float)Math.abs(dy1);
             if (dy1 != 0) dw1_step = dw1 / (float)Math.abs(dy1);
             
             if(dy1 != 0)
         {
             for(int i = y2; i <= y3; i++)
             {
                 int ax = (int)(x2 + (float)(i - y2) * dax_step);
		 int bx = (int)(x1 + (float)(i - y1) * dbx_step);
                 
                 double tex_su = u2 + (float)(i - y2) * du1_step;
		 double tex_sv = v2 + (float)(i - y2) * dv1_step;
                 double tex_sw = w2 + (float)(i - y2) * dw1_step;
                 
                 double tex_eu = u1 + (float)(i - y1) * du2_step;
		 double tex_ev = v1 + (float)(i - y1) * dv2_step;
                 double tex_ew = w1 + (float)(i - y1) * dw2_step;
                 
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
                     
                     double tempw = tex_sw;
                     tex_sw = tex_ew;
                     tex_ew = tempw;
                 }
                 
                 tex_u = tex_su;
                 tex_v = tex_sv;
                 tex_w = tex_sw;
                 
                 double tstep = 1.0/ (float)(bx-ax);
                 double t = 0.0;
                 
                 for(int j = ax; j < bx; j++)
                 {
                     tex_u = (1.0 - t) * tex_su + t * tex_eu;
                     tex_v = (1.0 - t) * tex_sv + t * tex_ev;
                     tex_w = (1.0 - t) * tex_sw + t * tex_ew;
                      if(Math.abs(tex_w) > zBuffer[i * 800 + j])
                    {
                        
                         int col = texArray[(int)Math.max(0,tex_u/tex_w*(img.getWidth()-1)) + (int)Math.max(0,tex_v/tex_w*(img.getHeight()-1))];

                        if(fog)
                        {
                            
                        }

                        else
                        {
                           // g2.setColor(new Color(getRGB((int)Math.max(0,tex_u*(img.getWidth()-1)),(int)Math.max(0,tex_v*(img.getHeight()-1)), width, height, pixelLength, pixels, hasAlphaChannel)));
                        }


                        draw(pix, j, i, col);
                        zBuffer[i * 800 + j] = Math.abs(tex_w);
                    }
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
   
   public static void draw(int[] pixels, int x, int y, int col)
   {
       if(x >= 0 && y >= 0 && x <= 800 && y <= 600)
       {
           pixels[x + y * 800] = col;
       }
   }
   
   public static void drawLine(int[] pixels, int sx, int ex, int ny, Color col)
   {
       for (int i = sx; i <= ex; i++)
       {
           draw(pixels, i, ny, col);
       }          
   }
   
   public static void swap(int x, int y)
   {
       int temp = x;
       x = y;
       y = temp;
   }
   
   public static void fillTriangle(int[] pixels, int x1, int y1, int x2, int y2, int x3, int y3, Color col)
   {
       int t1x=0, t2x=0, y=0, minx=0, maxx=0, t1xp=0, t2xp=0;
		boolean changed1 = false;
		boolean changed2 = false;
		int signx1=0, signx2=0, dx1=0, dy1=0, dx2=0, dy2=0;
		int e1=0, e2=0;
		// Sort vertices
		if (y1>y2) {int t=y1;y1=y2;y2=t;t=x1;x1=x2;x2=t;}
		if (y1>y3) {int t=y1;y1=y3;y3=t;t=x1;x1=x3;x3=t;}
		if (y2>y3) {int t=y2;y2=y3;y3=t;t=x2;x2=x3;x3=t;}

		t1x = t2x = x1; y = y1;   // Starting points
		dx1 = (int)(x2 - x1); if (dx1<0) { dx1 = -dx1; signx1 = -1; }
		else signx1 = 1;
		dy1 = (int)(y2 - y1);

		dx2 = (int)(x3 - x1); if (dx2<0) { dx2 = -dx2; signx2 = -1; }
		else signx2 = 1;
		dy2 = (int)(y3 - y1);

		if (dy1 > dx1) {   // swap values
			int t=dx1;dx1=dy1;dy1=t;
			changed1 = true;
		}
		if (dy2 > dx2) {   // swap values
            int t=dy2;dy2=dx2;dx2=t;
			changed2 = true;
		}

		e2 = (int)(dx2 >> 1);
		// Flat top, just process the second half
        boolean goNext=false;
		if (y1 == y2) goNext=true;
        if (!goNext) {
            e1 = (int)(dx1 >> 1);

            for (int i = 0; i < dx1;) {
                t1xp = 0; t2xp = 0;
                if (t1x<t2x) { minx = t1x; maxx = t2x; }
                else { minx = t2x; maxx = t1x; }
                // process first line until y value is about to change
                loop3:
                while (i<dx1) {
                    i++;
                    e1 += dy1;
                    while (e1 >= dx1) {
                        e1 -= dx1;
                        if (changed1) t1xp = signx1;//t1x += signx1;
                        else break loop3;
                    }
                    if (changed1) break;
                    else t1x += signx1;
                }
                // Move line
                // process second line until y value is about to change
                loop2:
                while (true) {
                    e2 += dy2;
                    while (e2 >= dx2) {
                        e2 -= dx2;
                        if (changed2) t2xp = signx2;//t2x += signx2;
                        else break loop2;
                    }
                    if (changed2)     break;
                    else              t2x += signx2;
                }
                if (minx>t1x) minx = t1x; if (minx>t2x) minx = t2x;
                if (maxx<t1x) maxx = t1x; if (maxx<t2x) maxx = t2x;
                drawLine(pixels,minx, maxx, y,col);    // Draw line from min to max points found on the y
                                            // Now increase y
                if (!changed1) t1x += signx1;
                t1x += t1xp;
                if (!changed2) t2x += signx2;
                t2x += t2xp;
                y += 1;
                if (y == y2) break;

            }
        }
		// Second half
		dx1 = (int)(x3 - x2); if (dx1<0) { dx1 = -dx1; signx1 = -1; }
		else signx1 = 1;
		dy1 = (int)(y3 - y2);
		t1x = x2;

		if (dy1 > dx1) {   // swap values
            int t=dy1;dy1=dx1;dx1=t;
			changed1 = true;
		}
		else changed1 = false;

		e1 = (int)(dx1 >> 1);

		for (int i = 0; i <= dx1; i++) {
			t1xp = 0; t2xp = 0;
			if (t1x<t2x) { minx = t1x; maxx = t2x; }
			else { minx = t2x; maxx = t1x; }
			// process first line until y value is about to change
            loop3:
			while (i<dx1) {
				e1 += dy1;
				while (e1 >= dx1) {
					e1 -= dx1;
					if (changed1) { t1xp = signx1; break; }//t1x += signx1;
					else break loop3;
				}
				if (changed1) break;
				else   	   	  t1x += signx1;
				if (i<dx1) i++;
			}
            // process second line until y value is about to change
            loop2:
            while (t2x != x3) {
                e2 += dy2;
                while (e2 >= dx2) {
                    e2 -= dx2;
                    if (changed2) t2xp = signx2;
                    else break loop2;
                }
                if (changed2)     break;
                else              t2x += signx2;
            }
			if (minx>t1x) minx = t1x; if (minx>t2x) minx = t2x;
			if (maxx<t1x) maxx = t1x; if (maxx<t2x) maxx = t2x;
			drawLine(pixels,minx, maxx, y,col);   										
			if (!changed1) t1x += signx1;
			t1x += t1xp;
			if (!changed2) t2x += signx2;
			t2x += t2xp;
			y += 1;
			if (y>y3) return;
		}
   }
   
    public static int countbits(int n) 
    {
        int count = 0;
        while (n > 0) {
            count+=(n & 1);
            n>>=1;
        }
        return count;
    }
    
    public static int getWidthShift(BufferedImage img)
    {
        int shift = countbits(img.getWidth());
        
        return shift;
    }
    
     public static int getHeightShift(BufferedImage img)
    {
        int shift = countbits(img.getHeight());
        
        return shift;
    }

}
