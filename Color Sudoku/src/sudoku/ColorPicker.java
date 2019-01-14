package sudoku;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ColorPicker extends JDialog {
	
	private JPanel container = new JPanel(new GridLayout(3, 3));
	private int selected;
	private SudokuWindow parentWindow;
	
	
	public ColorPicker(SudokuWindow parentWindow)
	{		
		super(parentWindow,"Color Picker",true);
		
		this.parentWindow = parentWindow;

		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				parentWindow.setEnabled(true);
				dispose();
			}
		});
		
		
		
		this.setSize(200, 220);
		this.setResizable(false);
		this.setLocationRelativeTo(parentWindow);	
		
		ImageIcon icon = new ImageIcon(getClass().getResource("img/icon.png"));
		this.setIconImage(icon.getImage());	

		initialize();
		this.add(container);
		
	}
	
	public int showDialog()
	{		
		parentWindow.setEnabled(false);
		this.setVisible(true);
		return selected;		
	}
	
	private void initialize()
	{
		
		for(int i=1;i<=9;i++)
		{
			container.add(new JColor(i,parentWindow,true) {
				@Override
				public void mousePressed(MouseEvent e) {
					
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						selected = this.getIntColor();
						parentWindow.setEnabled(true);
						dispose();
					}

				}				

				@Override
				public void mouseEntered(MouseEvent e) {
					this.setBorder(JColor.BLUE_BORDER);

				}


				@Override
				public void mouseExited(MouseEvent e) {
					this.setBorder(JColor.GRAY_BORDER);					
				}
				
			});
		}
	}

}
