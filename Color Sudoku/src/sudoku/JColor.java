package sudoku;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class JColor extends JPanel implements MouseListener{
	
	  private SudokuWindow parentWindow;
	  
	  public static final Color WHITE = new Color(247,248,249); // Color 0 Default
	  public static final Color RED = new Color(244,67,54); 	//Color 1
	  public static final Color YELLOW = new Color(253,216,53); //Color 2
	  public static final Color GREEN = new Color(124,179,66);  //Color 3
	  public static final Color BLUE = new Color(3,155,229);    //Color 4
	  public static final Color PURPLE = new Color(142,36,170); //Color 5
	  public static final Color PINK = new Color(236,64,122);   //Color 6
	  public static final Color GRAY = new Color(117,117,117);  //Color 7
	  public static final Color ORANGE = new Color(251,140,0);  //Color 8
	  public static final Color BROWN = new Color(109,76,65);   //Color 9
	  
	  public static final Border BLUE_BORDER = BorderFactory.createMatteBorder(2,2,2,2,Color.BLUE);
	  public static final Border BLACK_BORDER = BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK);
	  public static final Border GRAY_BORDER = BorderFactory.createLineBorder(new Color(202, 204, 206));
	  public static final Border RED_BORDER = BorderFactory.createMatteBorder(5,5,5,5,Color.RED);
	  public static final Border GREEN_BORDER = BorderFactory.createMatteBorder(3,3,3,3,new Color(34, 61, 0));
	  
	  public static final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	  public static final Cursor CLICK_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
	  

	  private Color cellColor;	  //Current color of cell
	  private int IntColor;		//Correct color of cell
	  private int SelectedColor;	//User selected color of cell
	  
	  
	public JColor(int colorId,SudokuWindow parentWindow) //Constructor
	{
		this.setSelectedColor(0); //Default all the cells are white
		this.setIntColor(colorId);

		this.addMouseListener(this);		
		this.setBorder(JColor.GRAY_BORDER);		
		this.parentWindow = parentWindow;		
		this.setCursor(JColor.CLICK_CURSOR);		
		//this.add(new JLabel(colorId+""));
		
	}
	
	public JColor(int colorId, SudokuWindow parentWindow, boolean picker) //For color picker
	{
		this(colorId,parentWindow);		
		this.setSelectedColor(colorId);		
	}
	


	private void setIntColor(int colorId) //Setting correct color of cell
	{
		if(colorId <=9 && colorId>=1)
		{
			this.IntColor = colorId;
		}
		else
		{
			this.IntColor = 1;
		}
	}
	
	public int getIntColor()
	{
		return IntColor;
	}	
	
	private void setSelectedColor(int colorId) //Setting user selected color
	{
		
		this.SelectedColor = colorId;
		
		switch(colorId) //Setting color
		{
		
		case 1: this.cellColor = JColor.RED; break;
		case 2: this.cellColor = JColor.YELLOW; break;
		case 3: this.cellColor = JColor.GREEN; break;
		case 4: this.cellColor = JColor.BLUE; break;
		case 5: this.cellColor = JColor.PURPLE; break;
		case 6: this.cellColor = JColor.PINK; break;
		case 7: this.cellColor = JColor.GRAY; break;
		case 8: this.cellColor = JColor.ORANGE; break;
		case 9: this.cellColor = JColor.BROWN; break;
		default: //Error
			this.SelectedColor = 0;
			this.cellColor = JColor.WHITE;
		}		
		
		this.setBackground(this.cellColor);
	}
	
	public void showAnswer()
	{
		if(this.isEnabled())
		{
		this.setSelectedColor(this.IntColor);
		this.setBorder(JColor.GRAY_BORDER);
		}
	}
	
	public void clearAnswer()
	{
		if(this.isEnabled())
		{
		this.setSelectedColor(0);
		this.setBorder(JColor.GRAY_BORDER);
		}
	
	}
	
	public int check()
	{
		if(this.IntColor == this.SelectedColor)
		{
			this.setBorder(JColor.GRAY_BORDER);
			return 1; //Correct
		}
		else if(this.SelectedColor != 0)
		{
			this.setBorder(JColor.RED_BORDER);
			return 2; //Incorrect
		}
		return 3;	//Empty

	}
	
	public void setHint() //Visible for hint
	{
		this.setEnabled(false);
		this.setSelectedColor(this.getIntColor());
		this.setCursor(JColor.DEFAULT_CURSOR);
	}

	
	// STARTING MOUSE LISTENER METHODS
	@Override
	public void mousePressed(MouseEvent e) {
		
		if(!this.isEnabled())
			return;
		
		if(e.getButton() == MouseEvent.BUTTON1) //This is left click
		{
			this.setBorder(JColor.BLUE_BORDER);		
			ColorPicker picker = new ColorPicker(this.parentWindow);		
			int value = picker.showDialog();
			
			if(value!=0)
			{
				this.setSelectedColor(value);		
			}
			
			this.setBorder(JColor.GRAY_BORDER);
		}
		else if(e.getButton() == MouseEvent.BUTTON3) // This is right click
		{
			this.clearAnswer();
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	// FINISH MOUSE LISTENER METHODS

}
