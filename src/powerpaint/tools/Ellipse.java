/*
 * TCSS 305 - Autumn 2012
 * Homework 4: Power Paint 
 * Author: Levon Kechichian
 */

package powerpaint.tools;

import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

/**
 * This class defines and constructs a Ellipse Tool
 * to be used within the PowerPaint program.
 * 
 * @author Levon Kechichian
 * @version Autumn 2012
 */
public class Ellipse implements Tool
{
  /**
   * The label of the button / menu item text.
   */
  private static final String TEXT = "Ellipse";
  
  /**
   * The tool tip text description.
   */
  private static final String DESCRIPTION = "Select the Ellipse tool";
  
  /**
   * The file name associated with the shapes ImageIcon.
   */
  private static final String ICON = "ellipse_bw.gif";
  
  /**
   * The mnemonic key value for the tool.
   */
  private static final Integer MNEMONIC = KeyEvent.VK_E;
  
  /**
   * The starting x-coordinate of the shape.
   */
  private int my_starting_x;
  
  /**
   * The starting y-coordinate of the shape.
   */
  private int my_starting_y;
  
  /**
   * An instance field for the shape object type.
   */
  private Ellipse2D my_shape;
  
  /**
   * Constructs a new Ellipse tool object.
   */
  public Ellipse()
  {
    my_shape = new Ellipse2D.Double();
  }

  /**
   * The starting coordinates of my shape.
   * 
   * @param the_x the starting x-coordinate
   * @param the_y the starting y-coordinate
   */
  @Override
  public void startingCoordinates(final int the_x, final int the_y)
  {
    my_starting_x = the_x;
    my_starting_y = the_y;
    my_shape.setFrameFromDiagonal(my_starting_x, my_starting_y, the_x, the_y);
  }

  /**
   * The ending coordinates of my shape.
   * 
   * @param the_x the ending x-coordinate
   * @param the_y the ending y-coordinate
   */
  @Override
  public void endingCoordinates(final int the_x, final int the_y)
  {
    my_shape.setFrameFromDiagonal(my_starting_x, my_starting_y, the_x, the_y);
  }

  /**
   * Gets the shape of the tool object.
   * 
   * @return returns a new clone of the shape
   */
  @Override
  public Shape cloneShape()
  {
    return (Shape) my_shape.clone();
  }

  /**
   * Constructs a new Ellipse2D.Double for the tool.
   */
  @Override
  public void clear()
  {
    my_shape = new Ellipse2D.Double();
  }

  /**
   * Gets the name of the tool object.
   * 
   * @return returns a the string representation of the tool
   */
  @Override
  public String getToolName()
  {
    return TEXT;
  }
  
  /**
   * Overrides the tool interface method to get the ImageIcon path.
   * 
   * @return returns the string path of the image
   */
  @Override
  public String getIcon()
  {
    return ICON;
  }
  
  /**
   * Overrides the tool interface method to get the tool tip text.
   * 
   * @return returns the tool tip text as a string
   */
  @Override
  public String getToolTip()
  {
    return DESCRIPTION;
  }
  
  /**
   * Overrides the tool interface method to get the mnemonic.
   * 
   * @return returns the mnemonic associated with the tool
   */
  @Override
  public Integer getMnemonic()
  {
    return MNEMONIC;
  }
}
