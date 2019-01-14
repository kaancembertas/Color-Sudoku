package sudoku;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class SudokuWindow extends JFrame{
	
	private Container pane = this.getContentPane();
	private JPanel pnlSudoku = new JPanel(new GridLayout(3, 3));
	private JPanel pnlBottom = new JPanel();
	
	private JButton btnCheck = new JButton("Check");		
	private JButton btnClearAll = new JButton("Clear All");		
	private JButton btnShowAnswer = new JButton("Show Answer");	
	private JButton btnNew = new JButton("New Sudoku");
	
	private final int NUMBER_OF_HINTS = 5; //Per square
	private final int [][] patternSudoku ={ 
			{8,1,2,7,5,3,6,4,9},
			{9,4,3,6,8,2,1,7,5},
			{6,7,5,4,9,1,2,8,3},
			
			{1,5,4,2,3,7,8,9,6},
			{3,6,9,8,4,5,7,2,1},
			{2,8,7,1,6,9,5,3,4},
			
			{5,2,1,9,7,4,3,6,8},
			{4,3,8,5,2,6,9,1,7},
			{7,9,6,3,1,8,4,5,2} 
		};
	
	private ArrayList<JColor> cells = new ArrayList<JColor>();
	 
	
	
	public SudokuWindow(String title)
	{

		this.setTitle(title);
		this.setSize(500, 600);
		pane.setLayout(null);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		//Set starting location center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		
		//Set icon
		ImageIcon icon = new ImageIcon(getClass().getResource("img/icon.png"));
		this.setIconImage(icon.getImage());		
		
		setSudoku();
		addBottom();
		
		
		
		btnCheck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				
				boolean status = true;
				int correctAnswers = 0;
				
				for(int i =0; i<cells.size();i++)
				{			
					if(cells.get(i).check() == 2)
					{
						status = false;
					}
					else if(cells.get(i).check() == 1)
					{
						correctAnswers++;
					}
				}
				
				if(status && correctAnswers == 81)
				{
					JOptionPane.showMessageDialog(SudokuWindow.this, "You have been solved this Sudoku!", "Congratulations!",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(status && correctAnswers-NUMBER_OF_HINTS*9 != 0)
				{
					JOptionPane.showMessageDialog(SudokuWindow.this, "Everything is ok!","Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		
		btnShowAnswer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i =0; i<cells.size();i++)
				{					
					cells.get(i).showAnswer();
				}		
			}
		});
		
		btnClearAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i =0; i<cells.size();i++)
				{
					cells.get(i).clearAnswer();
				}
			}
		});
		
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlSudoku.removeAll();
				pnlSudoku.revalidate();
				pnlSudoku.repaint();
				cells.clear();
				System.gc();
				setSudoku();
				
			}
		});
		
	}
	
	/*
	
	private boolean checkRow(int [] arr, int currentColumn,int num)
	{
		for(int i = 0; i<currentColumn;i++)
		{
			if(arr[i] == num)
				return true;
		}		
		return false;
	}
	
	private boolean checkColumn(int [][] arr,int column,int currentRow,int num)
	{
		for(int i = 0;i<currentRow;i++)
		{
			if(arr[i][column] == num)
				return true;
		}
		
		return false;
	}
	
	
	
	private int[][] sudoku()
	{
		return null;
	}
	
	*/
	
	private int[] randomNum() //Creates two different number between 1-3
	{
		int num[] = new int[2];
		
		num[0] = (int)(Math.random()*3);
		do 
		{
			num[1] = (int)(Math.random()*3);
		}
		while(num[0] == num[1]);
		
		
		return num;
	}

	
	private int[][] InterchangeColumns(int [][] pattern,int column1,int column2)
	{	
		int temp;
		
		for(int i=0;i<9;i++)
		{
			temp = pattern[i][column1];
			pattern[i][column1] = pattern[i][column2];
			pattern[i][column2] = temp;
		}
		
		return pattern;
	}
	
	private int[][] InterchangeRows(int [][] pattern,int row1,int row2)
	{
	
		int tempRow[] = pattern[row1];		
		pattern[row1] = pattern[row2];
		pattern[row2] = tempRow;		
		return pattern;		
	}
	
	
	private int[][] InterchangeOuterColumns(int [][] pattern,int column1,int column2)
	{
		
		for(int i=0;i<3;i++)
		{
			pattern = InterchangeColumns(pattern, (3*column1)+i, (3*column2)+i);
		}		
		return pattern;
	}
	
	private int[][] InterchangeOuterRows(int [][] pattern,int row1,int row2)
	{
		
		for(int i=0;i<3;i++)
		{
			pattern = InterchangeRows(pattern, (3*row1)+i, (3*row2)+i);
		}		
		return pattern;
	}
	
	private int[][] Rotate(int[][] pattern)
	{
		int rotatedSudoku[][] = new int[9][9];
		
		for(int i=8;i>=0;i--)
		{
			for(int j=0;j<9;j++)
			{
				rotatedSudoku[j][8-i] = pattern[i][j];
			}
		
		}
		
		return rotatedSudoku;
	}
	
	
	private int[][] generateSudoku()
	{
	
		int random;
		this.Rotate(patternSudoku);
		int [][] generatedSudoku = patternSudoku;
		int num[] = new int[2];
		
		for(int i=0;i<20;i++)
		{

			random = (int)(Math.random()*5);			
			
			
			switch(random)
			{
				case 0:	//Interchanging Rows
					
					for(int j = 0; j<3 ; j++)
					{						
					num = randomNum();					
					generatedSudoku = InterchangeRows(generatedSudoku, 3*j+num[0],(3*j+num[1]));					
					System.out.println("Interchanged Row-"+(3*j+num[0])+" and Row-"+(3*j+num[1]));					
					}	
					
				break;
				
				case 1://Interchanging Columns
					
					for(int j = 0; j<3 ; j++)
					{								
					num = randomNum();					
					generatedSudoku = InterchangeColumns(generatedSudoku, 3*j+num[0],(3*j+num[1]));					
					System.out.println("Interchanged Column-"+(3*j+num[0])+" and Column-"+(3*j+num[1]));					
					}	
					
					break;
					
				case 2: //Interchanging outer columns
					
					num = randomNum();					
					generatedSudoku = InterchangeOuterColumns(generatedSudoku, num[0], num[1]);				
					System.out.println("Interchanged Outer Column-"+num[0]+" and Column-"+num[1]);						
					break;
					
				case 3: //Interchanging outer rows					
					num = randomNum();					
					generatedSudoku = InterchangeOuterRows(generatedSudoku, num[0], num[1]);				
					System.out.println("Interchanged Outer Row-"+num[0]+" and Row-"+num[1]);	
					
					break;
				case 4: //Rotating					
					generatedSudoku = Rotate(generatedSudoku);
					System.out.println("Rotated");
					break;
					
			}
			
			
		}
		
		
		return generatedSudoku;
	}
	
	
	private void setSudoku()
	{
		int [][] generatedSudoku = new int[9][9];		
		pnlSudoku.setBounds(0, 0, 500, 500);		
		generatedSudoku = generateSudoku();		

			for(int k=0;k<3;k++)
			{				
				for(int l=0;l<3;l++)
				{
					JPanel pnl = new JPanel(new GridLayout(3, 3));
					pnl.setBorder(JColor.BLACK_BORDER);		
					
					ArrayList<String> rnd  = new ArrayList<String>();
					
					
				while(rnd.size()!=NUMBER_OF_HINTS)
				{
					int rnd1 = (int)(Math.random()*3);
					int rnd2 = (int)(Math.random()*3);
					
					if(!rnd.contains(rnd1+","+rnd2))
					{
						rnd.add(rnd1+","+rnd2);
					}					
				}						
				
					for(int i=0;i<3;i++)
						for(int j=0;j<3;j++)
						{
							JColor JC = new JColor(generatedSudoku[3*k+i][3*l+j], this);
							
							if(rnd.contains(i+","+j))
							{
								JC.setHint();
							}
							
							cells.add(JC);
							pnl.add(JC);
						}
					
					pnlSudoku.add(pnl);	
				}			
			}
		
	}
	
	private void addBottom()
	{
		
		JLabel lblInfo = new JLabel();
		lblInfo.setText("<html>Click LEFT Mouse button to select a color<br>"
						+ "Click RIGHT Mouse button to clear your selection");
		
		pnlBottom.setBounds(0,501,500,100);		
		pnlBottom.add(btnCheck);
		pnlBottom.add(btnClearAll);
		pnlBottom.add(btnShowAnswer);
		pnlBottom.add(btnNew);
		pnlBottom.add(lblInfo);		
		
		pane.add(pnlSudoku);
		pane.add(pnlBottom);
	}

}
