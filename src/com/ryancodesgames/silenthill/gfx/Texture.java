
package com.ryancodesgames.silenthill.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture 
{
    private int width;
    private int height;
    private int widthMask;
    private int heightMask;
    private int[] texArray;
    
    public Texture(BufferedImage img)
    {
        this.width = img.getWidth();
        this.height = img.getHeight();
        
        if(!isPowerOfTwo(width) || !isPowerOfTwo(height))
        {
            throw new IllegalArgumentException("Current texture width or height is not a power of 2 which slows down performance. Resize to power of two");
        }
        
        if (img.getType() != BufferedImage.TYPE_INT_RGB) 
        {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                img = newImage;
        }
    }
    
    public void createTexture(String fileName)
    {
        BufferedImage img = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
        try
        {
            img = ImageIO.read(getClass().getResource(fileName));
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        if(!isPowerOfTwo(img.getWidth()) || !isPowerOfTwo(img.getHeight()))
        {
            throw new IllegalArgumentException("Current texture width or height is not a power of 2 which slows down performance. Resize to power of two");
        }
        
        if (img.getType() != BufferedImage.TYPE_INT_RGB) 
        {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                img = newImage;
        }
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public int[] getTexArray()
    {
        return texArray;
    }
    
    public int getWidthMask()
    {
        return widthMask;
    }
    
    public int getHeightMask()
    {
        return heightMask;
    }
    
    public int getPixel(int x, int y)
    {
        return texArray[x + y * getWidth()];
    }
    
    public int setPixel(int x, int y, int col)
    {
        return texArray[x + y * getWidth()] = col;
    }
    
     /**
        Returns true if the specified number is a power of 2.
    */
    public static boolean isPowerOfTwo(int n) 
    {
        return ((n & (n-1)) == 0);
    }
    
    /**
        Counts the number of "on" bits in an integer.
    */
    public static int countbits(int n) 
    {
        int count = 0;
        while (n > 0) {
            count+=(n & 1);
            n>>=1;
        }
        return count;
    }
    
    public int getWidthShift()
    {
        int shift = countbits(getWidth());
        
        return shift;
    }
    
    public static int getHeightShift(BufferedImage img)
    {
        int shift = countbits(img.getHeight());
        
        return shift;
    }

}
