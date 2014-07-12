/*
 * TCSS 305 - Autumn 2012
 * Homework 4: Power Paint 
 * Author: Levon Kechichian
 */

package powerpaint.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import powerpaint.tools.Ellipse;
import powerpaint.tools.Line;
import powerpaint.tools.Pencil;
import powerpaint.tools.Rectangle;
import powerpaint.tools.Tool;

/**
 * The driver/frame portion of my project.
 * 
 * @author Levon Kechichian
 * @version Autumn 2012
 */
@SuppressWarnings("serial")
public class PowerPaintPanel extends JPanel
{
  /**
   * The spacing of the grid tiles.
   */
  private static final int GRID_SPACING = 10;
  
  /**
   * The height of the panel.
   */
  private static final int HEIGHT = 400;
  
  /**
   * The width of the panel.
   */
  private static final int WIDTH = 300;
  
  /**
   * A string array that holds all the tools objects names.
   */
  private final String[] my_tool_names = new String[]
  {
    "Pencil", "Line", "Rectangle", "Ellipse"
  };
  
  /**
   * An array of all PowerPaint tools.
   */
  private final Tool[] my_tools_array = new Tool[]
  {
    new Pencil(), new Line(), new Rectangle(), new Ellipse()
  };
  
  /**
   * The current tool.
   */
  private Tool my_current_tool = my_tools_array[0];
  
  /**
   * The ArrayList of all drawn shapes.
   */
  private final List<DrawnShapes> my_drawn_shapes = new ArrayList<DrawnShapes>();
  
  /**
   * The ArrayList of all shapes undone.
   */
  private final List<DrawnShapes> my_undone_shapes = new ArrayList<DrawnShapes>();
  
  /**
   * The chosen shape color.
   */
  private Color my_current_color = Color.BLACK;
  
  /**
   * The stroke width to paint with.
   */
  private BasicStroke my_stroke_size =  new BasicStroke(2);
  
  /**
   * Store the state of the grid.
   */
  private boolean my_grid_state;
  
  /**
   *  Constructor to setup the powerpaint panel.
   */
  public PowerPaintPanel()
  {
    super();
    setPreferredSize(new Dimension(HEIGHT, WIDTH));
    setBackground(Color.WHITE);
    addMouseListener(new ToolListener());
    addMouseMotionListener(new ToolListener());
  }
  
  /**
   * Sets the current grid state.
   * 
   * @param the_state the grid state choice
   */
  public void setGridState(final boolean the_state)
  {
    my_grid_state = the_state;
  }
  
  /**
   * Gets the current grid state.
   * 
   * @return returns the state of the grid
   */
  public boolean isGridEnabled()
  {
    return my_grid_state;
  }
  
  /**
   * Sets the stroke size to the given integer.
   * 
   * @param the_size the desired stroke size
   */
  public void setStrokeSize(final float the_size)
  {
    my_stroke_size = new BasicStroke(the_size);
  }
  
  /**
   * Gets the current stroke size.
   * 
   * @return returns the current stroke size
   */
  public BasicStroke getStrokeSize()
  {
    return my_stroke_size;
  }
  
  /**
   * Sets the color of the next drawn shape.
   * 
   * @param the_color the pen color
   */
  public void setColor(final Color the_color)
  {
    my_current_color = the_color;
  }
  
  /**
   * Gets the color of the current shape.
   * 
   * @return returns the color of the current shape.
   */
  public Color getColor()
  {
    return my_current_color;
  }
  
  /**
   * Gets the list of drawn shapes.
   * 
   * @return the arraylist of drawnshapes
   */
  public List<DrawnShapes> getDrawnShapes()
  {
    return my_drawn_shapes;
  }
  
  /**
   * Gets the list of undone shapes.
   * 
   * @return the arralist of undone shapes
   */
  public List<DrawnShapes> getUndoneShapes()
  {
    return my_undone_shapes;
  }
  
  /**
   * Sets the tool to specified type.
   * 
   * @param the_tool the tool that the program
   * will be set to
   */
  public void setCurrentTool(final Tool the_tool)
  {
    my_current_tool = the_tool;
  }
  
  /**
   * Gets the current tool.
   * 
   * @return returns the current tool
   */
  public Tool getCurrentTool()
  {
    return my_current_tool;
  }
  
  /**
   * Gets the tool at the specified index.
   * 
   * @param the_index the specific index of the tool
   * @return returns the tool of specified at the index
   */
  public Tool getTools(final int the_index)
  {
    return my_tools_array[the_index];
  }
  
  /**
   * Paints the drawn paths.
   * 
   * @param the_graphics The graphics context to use for painting.
   */
  public void paintComponent(final Graphics the_graphics)
  {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                         RenderingHints.VALUE_ANTIALIAS_ON);
    
    for (int i = 0; i < my_drawn_shapes.size(); i++)
    {
      g2d.setPaint(my_drawn_shapes.get(i).getColor());
      g2d.setStroke(my_drawn_shapes.get(i).getStrokeSize());
      g2d.draw(my_drawn_shapes.get(i).getShape());
    }
    
    g2d.setPaint(my_current_color);
    g2d.setStroke(my_stroke_size);
    g2d.draw(my_current_tool.cloneShape());
    
    if (my_grid_state)
    {
      g2d.setPaint(Color.BLACK);
      g2d.setStroke(new BasicStroke(1));
      //draws the grid rows
      for (int i = GRID_SPACING; i < getHeight(); i += GRID_SPACING)
      {
        g2d.drawLine(0, i, getWidth(), i);
      }
      //draws the grid columns
      for (int i = GRID_SPACING; i < getWidth(); i += GRID_SPACING)
      {
        g2d.drawLine(i, 0, i, getHeight()); 
      }
    }
  }
  
  /**
   * InnerClass to listen for MouseEvent's and respond accordingly.
   * 
   * @author Daniel Zimmerman
   * @author Levon Kechichian
   * @version Autumn 2012
   */
  private class ToolListener extends MouseAdapter
  { 
    /**
     * Overrides the MouseAdapter method for the powerpaint tools.
     * 
     * @param the_mouse_event the mouse event that was fired
     */
    public void mousePressed(final MouseEvent the_mouse_event)
    {
      my_current_tool.startingCoordinates(the_mouse_event.getX(), the_mouse_event.getY());
      if (my_current_tool.getToolName().equals(my_tool_names[0]))
      {
        final DrawnShapes previous_drawing = new DrawnShapes(my_current_tool.cloneShape(),
          getColor(), new BasicStroke(my_stroke_size.getLineWidth()));
        my_drawn_shapes.add(previous_drawing);
      }
    }
    /**
     * If a mouseDragged event occurs get the
     * coordinates at the event location and move to that point.
     * 
     * @param the_mouse_event the event that was fired
     */
    public void mouseDragged(final MouseEvent the_mouse_event)
    {
      my_current_tool.endingCoordinates(the_mouse_event.getX(), the_mouse_event.getY());
      repaint();
    }
    
    /**
     * If mouseReleased event occurs clear the collection of GeneralPath's.
     * 
     * @param the_mouse_event the event that was fired
     */
    public void mouseReleased(final MouseEvent the_mouse_event)
    {
      my_current_tool.endingCoordinates(the_mouse_event.getX(), the_mouse_event.getY());
      final DrawnShapes previous_shape = new DrawnShapes(my_current_tool.cloneShape(),
        getColor(), new BasicStroke(my_stroke_size.getLineWidth()));
      my_drawn_shapes.add(previous_shape);
      repaint();
    }
  }
}
