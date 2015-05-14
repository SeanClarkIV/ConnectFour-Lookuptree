/**********************************************************
 *   Project: Connect 4 Learning
 *
 *   Team 4:
 *		Sean Clark
 *		Thomas Coppola
 *		Anthony Fontanetta
 *		Sanjana Thomas
 **********************************************************/
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.Scanner;

public class GUI extends JFrame implements ActionListener 
{

    private GamePanel gamePanel;
    private JLabel msgLabel;
    private JButton[] columnButtons;
    private Game game;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
    {
        
    	Scanner user_input = new Scanner( System.in );
    	int player1;
    	int player2;
    	Solver p1 = null;
    	Solver p2 = null;
    	System.out.print("Welcome to Connect Four!!\nPlease choose player 1 by corresponding number:\n1: Minimax Player \n"
    			+ "2: Random Player\n3: Human Player\n4: DatabasePlayer\n");
    	
    	player1 = user_input.nextInt( );
    	
    	if(player1 == 1)
    		{
    			/* The depth is used to recurse when searching the game space. */
    			p1= new Minimax(Board.Player.RED, 6);
    		}
    	else if(player1 == 2)
    		{
    			p1= new RandomPlayer(Board.Player.RED);
    		}
    	else if(player1 == 3)
    		{
    			p1= new DatabasePlayer(Board.Player.RED);
    		}
    	else if (player1 == 4)
    	{
    			p1 = new DatabasePlayer(Board.Player.RED);
    	}
    	else
    		{
    			System.out.println("You have not entered a valid number, please restart and enter valid number.");
    		}
    	
    	
    	System.out.print("Please choose player 2 by corresponding number:\n1: Minimax Player\n"
    			+ "2: Random Player\n3: Human Player\n");
    	
    	player2 = user_input.nextInt( );
    	
    	if(player2 == 1)
    	{
    		/* The depth is used to recurse when searching the game space. */
    		p2= new Minimax(Board.Player.YELLOW, 6);
    	}
    	else if(player2 == 2)
    	{
    		p2= new RandomPlayer(Board.Player.YELLOW);
    	}
    	else if(player2 == 3)
    	{
    		p2= new Human(Board.Player.YELLOW);
    	}
    	else
    	{
    		System.out.println("You have not entered a valid number, please restart and enter valid number.");
    	}
    	
    	
    	
    	
    	
    	
    	
    	
   
        //Solver p1= new Minimax(Board.Player.RED, 6);
        // Solver p2= new Minimax(Board.Player.YELLOW,6);
        //Solver p1= new RandomPlayer(Board.Player.RED);
       // Solver p2= new RandomPlayer(Board.Player.YELLOW);
        // Solver p1 = new Human(Board.Player.RED);
        //Solver p2 = new Dummy(Board.Player.YELLOW);

        Game game= new Game(p1, p2);
        game.setGUI(new GUI(game));
        game.runGame();
    }


    public GUI(Game game) {
        super("Connect Four AI");

        this.game= game;

        setLayout(new BorderLayout());

        gamePanel= new GamePanel(this,new Board());
        columnButtons= new JButton[Board.NUM_COLS];

        //Message Panel
        JPanel msgPanel= new JPanel();
        msgPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        msgPanel.setPreferredSize(new Dimension(getWidth(),18));
        msgPanel.setLayout(new GridLayout(1,4));
        msgLabel= new JLabel("");
        msgPanel.add(msgLabel);	

        //Toolbar of Column Buttons
        JToolBar toolbar= setUpToolBar();

        add(toolbar,BorderLayout.NORTH);
        add(gamePanel,BorderLayout.CENTER);
        add(msgPanel,BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /** Set up the action buttons at the top of the board for human interaction. */
    private JToolBar setUpToolBar() {
        for (int i= 0; i < columnButtons.length; i++) {
            columnButtons[i]= new JButton("Column " + i);
            columnButtons[i].addActionListener(this);
        }

        JToolBar toolBar= new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        for (int i= 0; i < columnButtons.length; i= i+1) {
            toolBar.add(columnButtons[i]);
        }
        return toolBar;
    }

    /** Tell the GUI that the game is over and that winner has won
     * (it's a tie if winner = null). */
    public void notifyGameOver(Board.Player winner) {
        if (winner == null) {
            setMsg("Tie game!");
        } else {
            setMsg(winner + " won the game!!!");
        }
    }

    /** Display message s at the bottom of the GUI. */
    public void setMsg(String s) {
        msgLabel.setText(s);
    }

    @Override
    /** Process a click of a column button, given in e. */
    public void actionPerformed(ActionEvent e) {
        Object s= e.getSource();
        for (int i= 0; i < columnButtons.length; i= i+1) {
            if (s == columnButtons[i]) {
                game.columnClicked(i);
            }
        }
    }

    /** Update this GUI to make Move m and display Board b. */
    public void updateGUI(Board b, Move m) {
        gamePanel.playColumn(m.getPlayer(), m.getColumn()); // responsible for animation
        gamePanel.updateBoard(b); // must be called after (not before) playColumn
        repaint();
    }
    
    public void updateGUI(Board b) {
        gamePanel.updateBoard(b); // must be called after (not before) playColumn
        repaint();
    }

}
