/*
 * TCSS 305 - Autumn 2012
 * Homework 4: Power Paint 
 * Author:Levon Kechichian
 */

package powerpaint.tools;

import java.awt.Shape;

/**
 * Defines the basics for every paint tool.
 * 
 * @author Levon Kechichian
 * @version Autumn 2012
 */
public interface Tool
{
  /**
   * The starting coordinates of my shape.
   * 
   * @param the_x the starting x-coordinate
   * @param the_y the starting y-coordinate
   */
  void startingCoordinates(final int the_x, final int the_y);
  
  /**
   * The ending coordinates of my shape.
   * 
   * @param the_x the ending x-coordinate
   * @param the_y the ending y-coordinate
   */
  void endingCoordinates(final int the_x, final int the_y);
  
  /**
   * Gets the shape of the tool object.
   * 
   * @return returns a new clone of the shape
   */
  Shape cloneShape();
  
  /**
   * Constructs a reference to a new shape object or resets its values.
   */
  void clear();
  
  /**
   * Gets the name of the tool object.
   * 
   * @return returns the string representation of the tool object
   */
  String getToolName();
  
  /**
   * Gets the ImageIcon for the tool.
   * 
   * @return returns the ImageIcon for the tool
   */
  String getIcon();
  
  /**
   * Gets the string to the tool tip text.
   * 
   * @return returns the tool tip text
   */
  String getToolTip();
  
  /**
   * Gets the Integer representation of the mnemonic.
   * 
   * @return returns the mnemonic
   */
  Integer getMnemonic();
}
