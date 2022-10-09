
package com.ryancodesgames.silenthill;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
   public boolean upPressed, rightPressed, downPressed, leftPressed, frontPressed, backPressed, rightTurn, leftTurn;
   
   @Override
   public void keyPressed(KeyEvent e)
   {
       if(e.getKeyCode() == KeyEvent.VK_A)
       {
           leftPressed = true;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_D)
       {
           rightPressed = true;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_SPACE)
       {
           upPressed = true;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_C)
       {
           downPressed = true;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_W)
       {
           frontPressed = true;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_S)
       {
           backPressed = true;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_Q)
       {
           leftTurn = true;
       }
         
       if(e.getKeyCode() == KeyEvent.VK_E)
       {
           rightTurn = true;
       }
   }
   
   @Override
   public void keyTyped(KeyEvent e){
       
   }
   
   @Override
   public void keyReleased(KeyEvent e)
   {
       if(e.getKeyCode() == KeyEvent.VK_A)
       {
           leftPressed = false;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_D)
       {
           rightPressed = false;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_SPACE)
       {
           upPressed = false;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_C)
       {
           downPressed = false;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_W)
       {
           frontPressed = false;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_S)
       {
           backPressed = false;
       }
       
       if(e.getKeyCode() == KeyEvent.VK_Q)
       {
           leftTurn = false;
       }
         
       if(e.getKeyCode() == KeyEvent.VK_E)
       {
           rightTurn = false;
       }
   }
}
