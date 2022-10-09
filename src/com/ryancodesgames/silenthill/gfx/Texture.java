
package com.ryancodesgames.silenthill.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferUShort;


public abstract class Texture 
{
    protected int width;
    
    protected int height;
    
    public Texture(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public abstract short getColor(int x, int y);
    
    public static boolean isPowerOfTwo(int n)
   {
        while(n%2==0)
        {
            n=n/2;
        }
        if(n==1)
        {
            return true;
        }
        else
        {
            return false;
        }
   }
    
        /**
     Creates a Texture from the specified image.
    */
    public static Texture createTexture(BufferedImage image) 
    {
        int type = image.getType();
        int width = image.getWidth();
        int height = image.getHeight();
        
        if (!isPowerOfTwo(width) || !isPowerOfTwo(height)) {
        throw new IllegalArgumentException(
        "Size of texture must be a power of two.");
        }
        // convert image to an 16-bit image
        if (type != BufferedImage.TYPE_USHORT_565_RGB) {
        BufferedImage newImage = new BufferedImage(
        image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_USHORT_565_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        image = newImage;
        }
        DataBuffer dest = image.getRaster().getDataBuffer();
        return new PowerOf2Texture(
        ((DataBufferUShort)dest).getData(),
        Integer.bitCount(width-1), Integer.bitCount(height-1));
    }

}
