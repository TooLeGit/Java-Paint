/*
 * TCSS 305 - Autumn 2012
 * Homework 4: Power Paint 
 * Author: Levon Kechichian
 */

package powerpaint.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

/**
 * This class stores / gets the shape information data currently in use
 * and awaiting to be drawn.
 * 
 * @author Levon Kechichian
 * @version Autumn 2012
 */
public class DrawnShapes
{
  /**
   * The shape of the drawn object.
   */
  private final Shape my_shape;
  
  /**
   * The stroke size of the drawn object.
   */
  private final BasicStroke my_stroke_size;
  
  /**
   * The color of the drawn object.
   */
  private final Color my_shape_color;
  
  /**
   * Constructs a newly DrawnShape used from the given information.
   * 
   * @param the_shape the shape of the object
   * @param the_shape_color the color of the object
   * @param the_stroke_size the stroke size of the object
   */
  public DrawnShapes(final Shape the_shape, 
                     final Color the_shape_color, final BasicStroke the_stroke_size)
  {
    my_shape = the_shape;
    my_shape_color = the_shape_color;
    my_stroke_size = the_stroke_size;
  }
  
  /**
   * Getter method for the shape.
   * 
   * @return returns the shape of the object
   */
  public Shape getShape()
  {
    return my_shape;
  }
  
  /**
   * Getter method for the color.
   * 
   * @return returns the color of the object
   */
  public Color getColor()
  {
    return my_shape_color;
  }
  
  /**
   * Getter method for the stroke size.
   * 
   * @return returns the stroke size of the object
   */
  public BasicStroke getStrokeSize()
  {
    return my_stroke_size;
  }
}

