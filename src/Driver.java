/*
 * TCSS 305 - Autumn 2012
 * Homework 4: Power Paint 
 * Author: Levon Kechichian
 */

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import powerpaint.gui.PowerPaintFrame;

/**
 * The driver class.
 * 
 * @author Levon Kechichian
 * @version Autumn 2012
 */
public final class Driver
{
  /**
   * Disables this Driver from being a parent.
   */
  private Driver()
  {
    //DO NOTHING!
  }
  
  
  /**
   * The main method to start my program.
   * 
   * @param the_args the command-line arguments
   */
  public static void main(final String[] the_args)
  {
    try
    {
      // set cross-platform Java look and feel
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (final UnsupportedLookAndFeelException e)
    {
      assert false;
    }
    catch (final ClassNotFoundException e)
    {
      assert false;
    }
    catch (final InstantiationException e)
    {
      assert false;
    }
    catch (final IllegalAccessException e)
    {
      assert false;
    }
    
    final PowerPaintFrame frame = new PowerPaintFrame();
    frame.run();
    frame.setVisible(true);
  }

}
