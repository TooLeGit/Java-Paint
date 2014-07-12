/*
 * TCSS 305 - Autumn 2012
 * Homework 4: Power Paint 
 * Author: Levon Kechichian
 */

package powerpaint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import powerpaint.tools.Tool;

/**
 * The driver class that creates the PowerPaint program.
 * 
 * @author Levon Kechichian
 * @version Autumn 2012
 */
@SuppressWarnings("serial")
public class PowerPaintFrame extends JFrame
{
  /**
   * The text on the Color button.
   */
  private static final String COLOR_BUTTON_TEXT = "Color...";
  
  /**
   * The number of thickness buttons.
   */
  private static final int NUM_BUTTONS = 3;
  
  /**
   * The number of powerpaint tools.
   */
  private static final int NUM_TOOLS = 4;
  
  /**
   * The grid item menu item.
   */
  private final JCheckBoxMenuItem my_grid_item = new JCheckBoxMenuItem("Grid");
  
  /**
   * The "Extras" menu, from the menu bar.
   */
  private JMenu my_extras_menu;
  
  /**
   * The "File" menu, from the menu bar.
   */
  private JMenu my_file_menu;
  
  /**
   * The "Help" menu, from the menu bar.
   */
  private JMenu my_help_menu;
  
  /**
   * The "Options" menu, from the menu bar.
   */
  private JMenu my_options_menu;
  
  /**
   * The tool bar color button.
   */
  private final JButton my_tool_bar_color = new JButton(COLOR_BUTTON_TEXT);
  
  /**
   * The "Tools" menu, from the menu bar.
   */
  private JMenu my_tools_menu = new JMenu();
  
  /**
   * The toolbar for the tools.
   */
  private final JToolBar my_tool_bar = new JToolBar();
  
  /**
   * The menu button group.
   */
  private final ButtonGroup my_menu_group = new ButtonGroup();
  
  /**
   * The tool button group.
   */
  private final ButtonGroup my_tool_group = new ButtonGroup();
  
  /**
   * The thickness button group.
   */
  private final ButtonGroup my_thickness_group = new ButtonGroup();
  
  /**
   * The PowerPaint panel used for displaying "art".
   */
  private final PowerPaintPanel my_paint_panel = new PowerPaintPanel();
  
  /**
   * Private constructor so that there are no derived classes.
   */
  public PowerPaintFrame()
  {
    super("PowerPaint");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(my_paint_panel, BorderLayout.CENTER);
    add(my_tool_bar, BorderLayout.SOUTH);
    pack();
  }
  
  /**
   * Method to create the menu bar safely.
   */
  public void run()
  {
    setJMenuBar(createJMenuBar());
  }
  
  /**
   * Method to create the Toolbar for the GUI.
   * 
   * @return returns a toolbar with all the menu items.
   */
  public JMenuBar createJMenuBar()
  {
    final JMenuBar powerpaint_bar = new JMenuBar();
     
    createFileMenu();
    powerpaint_bar.add(my_file_menu);
    createOptionsMenu();
    powerpaint_bar.add(my_options_menu);
    createToolsMenu();
    powerpaint_bar.add(my_tools_menu);
    createHelpMenu();
    powerpaint_bar.add(my_help_menu);
    createExtrasMenu();
    powerpaint_bar.add(my_extras_menu);
    
    return powerpaint_bar;
  }
  
  /**
   * Method to create the file menu and its items.
   */
  public void createFileMenu()
  {
    my_file_menu = new JMenu("File");
    my_file_menu.setMnemonic(KeyEvent.VK_F);
    
    final JMenuItem clear = new JMenuItem("Clear", KeyEvent.VK_C);
    clear.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_paint_panel.getDrawnShapes().clear();
        my_paint_panel.getCurrentTool().clear();
        repaint();
      }
    });
    my_file_menu.add(clear);
    
    my_file_menu.addSeparator();
    
    final JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q);
    quit.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        dispose();
      }
    });
    my_file_menu.add(quit);
  }
  
  /**
   * Method to create the options menu and its items.
   */
  public void createOptionsMenu()
  {
    my_options_menu = new JMenu("Options");
    my_options_menu.setMnemonic(KeyEvent.VK_O);
    
    my_grid_item.addActionListener(new GridAction("Display a 2D grid",
      KeyEvent.VK_G));
    my_options_menu.add(my_grid_item);
    
    final JMenu thickness = new JMenu("Thickness");
    thickness.setMnemonic(KeyEvent.VK_T);
    
    for (int i = 1; i < Math.pow(2.0, NUM_BUTTONS); i *= 2)
    {
      final String text = Integer.toString(i);
      final JRadioButtonMenuItem button = new JRadioButtonMenuItem(text);
      final ThicknessAction action = new ThicknessAction(button.getText(), i, button);
      button.addActionListener(action);
      my_thickness_group.add(button);
      thickness.add(button);
    }
    
    my_options_menu.add(thickness);
  }
  
  /**
   * Method to create the tools menu and its items.
   */
  public void createToolsMenu()
  {
    my_tools_menu = new JMenu("Tools");
    my_tools_menu.setMnemonic(KeyEvent.VK_T);
    
    createColorButtons();
    
    my_tools_menu.addSeparator();
    
    for (int i = 0; i < NUM_TOOLS; i++)
    {
      final Tool paint_tool = my_paint_panel.getTools(i);
      final JToggleButton tool_bar_button = new 
          JToggleButton(paint_tool.getToolName());
      final JRadioButtonMenuItem menu_item_tool = new
        JRadioButtonMenuItem(paint_tool.getToolName());
      final ToolAction action = new ToolAction(paint_tool.getToolName(),
        new ImageIcon(paint_tool.getIcon()), paint_tool.getToolTip(), 
        paint_tool.getMnemonic(),
        my_paint_panel.getTools(i), tool_bar_button,
        menu_item_tool);
      menu_item_tool.setAction(action);
      tool_bar_button.setAction(action);
      if ("Pencil".equals(paint_tool.getToolName()))
      {
        tool_bar_button.setSelected(true);
        menu_item_tool.setSelected(true);
        my_paint_panel.setCurrentTool(paint_tool);
      }
      my_menu_group.add(menu_item_tool);
      my_tools_menu.add(menu_item_tool);
      my_tool_group.add(tool_bar_button);
      my_tool_bar.add(tool_bar_button);
    }
  }
  
  /**
   * Method to create the Color buttons and attach
   * their ColorAction listeners.
   * 
   */
  public void createColorButtons()
  {
    final JMenuItem color_menu_item = new JMenuItem(COLOR_BUTTON_TEXT);
    final ColorAction action = new ColorAction(COLOR_BUTTON_TEXT,
      "Choose a color...", KeyEvent.VK_C);
    color_menu_item.setAction(action);
    my_tools_menu.add(color_menu_item);
    my_paint_panel.setColor(Color.BLACK);
    my_tool_bar_color.setBackground(Color.BLACK);
    my_tool_bar_color.setAction(action);
    my_tool_bar.add(my_tool_bar_color);
  }
  
  /**
   * Method to create the help menu and its items.
   */
  private void createHelpMenu()
  {
    my_help_menu = new JMenu("Help");
    my_help_menu.setMnemonic(KeyEvent.VK_H);
    
    final JMenuItem about = new JMenuItem("About...", KeyEvent.VK_A);
    about.setToolTipText("View the about dialog");
    about.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        JOptionPane.showMessageDialog(null, 
          "TCSS 305 PowerPaint Special thanks to Dr.DMZ, v1.0");
      }
    });
    my_help_menu.add(about);
  }
  
  /**
   * Method to create the extra menu and its items.
   */
  private void createExtrasMenu()
  {
    my_extras_menu = new JMenu("Extras");
    my_extras_menu.setMnemonic(KeyEvent.VK_E);
    
    final JMenuItem undo = new JMenuItem("Undo", KeyEvent.VK_U);
    undo.setToolTipText("Undo the last drawing");
    undo.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        if (!my_paint_panel.getDrawnShapes().isEmpty())
        {
          my_paint_panel.getCurrentTool().clear();
          repaint();
          final DrawnShapes temp = my_paint_panel.getDrawnShapes().get(
            my_paint_panel.getDrawnShapes().size() - 1);
          my_paint_panel.getUndoneShapes().add(new DrawnShapes(temp.getShape(), 
            temp.getColor(), temp.getStrokeSize()));
          my_paint_panel.getDrawnShapes().remove(temp);
          repaint();
        }
      }
    });
    my_extras_menu.add(undo);
    
    final JMenuItem redo = new JMenuItem("Redo", KeyEvent.VK_R);
    redo.setToolTipText("Redo the undone drawing");
    redo.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        if (!my_paint_panel.getDrawnShapes().isEmpty() &&
          !my_paint_panel.getUndoneShapes().isEmpty())
        {
          final DrawnShapes temp = my_paint_panel.getUndoneShapes().get(
            my_paint_panel.getUndoneShapes().size() - 1);
          my_paint_panel.getUndoneShapes().add(new DrawnShapes(temp.getShape(), 
            temp.getColor(), temp.getStrokeSize()));
          my_paint_panel.getUndoneShapes().remove(temp);
          repaint();
        }
      }
    });
    my_extras_menu.add(redo);  
  }
  
  /**
   * Private inner class sets up the AbstractActions for
   * the Grid state in powerpaint.
   * 
   * @author Levon Kechichian
   * @version Autumn 2012
   */
  private class GridAction extends AbstractAction
  {
    /**
     * Constructs a GridAction object to be registered
     * to a grid button.
     * 
     * @param the_description the tool tip text
     * @param the_mnemonic the Integer representation of the mnemonic
     */
    public GridAction(final String the_description,
      final Integer the_mnemonic)
    {
      super();
      putValue(SHORT_DESCRIPTION, the_description);
      putValue(MNEMONIC_KEY, the_mnemonic);
      my_grid_item.setMnemonic(the_mnemonic);
    }

    /**
     * Overrides the AbstractAction method to
     * decide whether to display the grid or not
     * based on the event.
     * 
     * @param the_event the action event that was fired
     */
    @Override
    public void actionPerformed(final ActionEvent the_event)
    {
      if (my_grid_item.isSelected())
      {
        my_paint_panel.setGridState(true);
        repaint();
      }
      else
      {
        my_paint_panel.setGridState(false);
        repaint();
      }
    }
  }
  
  /**
   * Private inner class sets up the AbstractActions for
   * the Color's in powerpaint.
   * 
   * @author Levon Kechichian
   * @version Autumn 2012
   */
  private class ColorAction extends AbstractAction
  { 
    /**
     * Constructs a ColorAction object for the
     * Color objects.
     * 
     * @param the_text the text label of the tool
     * @param the_description the tool tip text of the tool
     * @param the_mnemonic the Mnemonic associated with the tool
     */
    public ColorAction(final String the_text,
      final String the_description, final Integer the_mnemonic)
    {
      super(the_text);
      putValue(SHORT_DESCRIPTION, the_description);
      putValue(MNEMONIC_KEY, the_mnemonic);
    }
    
    /**
     * Overrides the actionPerformed method for a ColorAction object.
     * 
     * @param the_event the action event object
     */
    @Override
    public void actionPerformed(final ActionEvent the_event)
    {
      final Color chosen_color = JColorChooser.showDialog(null,
        "Choose a new color...", Color.BLACK);
      if (chosen_color != null)
      {
        my_paint_panel.setColor(chosen_color);
        my_tool_bar_color.setBackground(chosen_color);
      }
    }
  }
  
  /**
   * Private class to declare and define how tools
   * should handle events.
   */
  public class ToolAction extends AbstractAction
  {
    /**
     * The tool object.
     */
    private final Tool my_tool;
    
    /**
     * The radio button associated with the tool.
     */
    private final JToggleButton my_radio_button;
    
    /**
     * The menu item associated with the tool.
     */
    private final JRadioButtonMenuItem my_menu_item;
    
    /**
     * Constructs a new Tool object and passes the
     * appropriate values to the super class constructor.
     * 
     * @param the_text the text label of the tool
     * @param the_icon the file path of the shapes ImageIcon
     * @param the_description the tool tip text of the tool
     * @param the_mnemonic the Mnemonic associated with the tool
     * @param the_tool the current tool object
     * @param the_radio_button the radio button associated with the tool
     * @param the_menu_item the radio button menu item associated
     * with the tool
     */
    public ToolAction(final String the_text, final ImageIcon the_icon, 
      final String the_description, final Integer the_mnemonic, 
      final Tool the_tool, final JToggleButton the_radio_button,
      final JRadioButtonMenuItem the_menu_item)
    {
      super(the_text, the_icon);
      putValue(SHORT_DESCRIPTION, the_description);
      putValue(MNEMONIC_KEY, the_mnemonic);
      my_radio_button = the_radio_button;
      my_radio_button.setIcon(the_icon);
      my_radio_button.setMnemonic(the_mnemonic);
      my_menu_item = the_menu_item;
      my_menu_item.setIcon(the_icon);
      my_menu_item.setMnemonic(the_mnemonic);
      my_tool = the_tool;
    }

    /**
     * Overrides the AbstractAction method for the tool.
     * 
     * @param the_event the event that fired
     */
    @Override
    public void actionPerformed(final ActionEvent the_event)
    {
      my_paint_panel.setCurrentTool(my_tool);
      my_radio_button.setSelected(true);
      my_menu_item.setSelected(true);
    }
  }
  
  /**
   * Private class to create ThicknessAction objects
   * to handle the thickness events.
   * 
   * @author Levon Kechichian
   * @version Autumn 2012
   */
  public class ThicknessAction extends AbstractAction
  {
    /**
     * The thickness size of the stroke.
     */
    private final float my_size;
    
    /**
     * Constructs a ThicknessAction object for the
     * thickness menu.
     * 
     * @param the_text the text label of the button
     * @param the_size the size of the stroke
     * @param the_menu_item the thickness menu item
     */
    
    public ThicknessAction(final String the_text, final int the_size, 
      final JRadioButtonMenuItem the_menu_item)
    {
      super(the_text);
      my_size = the_size;
      the_menu_item.setMnemonic((int) the_text.charAt(0));
      if (the_size == 2)
      {
        the_menu_item.setSelected(true);
        my_paint_panel.setStrokeSize(my_size);
      }
    }
    
    /**
     * Overrides the actionPerformed method to fit
     * the powerpaint specs.
     * 
     * @param the_event the event that was fired
     */
    public void actionPerformed(final ActionEvent the_event)
    {
      my_paint_panel.setStrokeSize(my_size);
    }
  }

}
