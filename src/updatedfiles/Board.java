/**********************************************************
 *   Project: Connect 4 Learning
 *
 *   Team 4:
 *		Sean Clark
 *		Thomas Coppola
 *		Anthony Fontanetta
 *		Sanjana Thomas
 **********************************************************/
import java.util.*;


/** An instance represents a grid of pieces from two opposing
 *  players in a game of Connect Four. The grid is 0-indexed first by rows
 *  starting at the top, then by columns 0-indexed starting at the left. */
public class Board {
    /** The number of rows on the Connect Four board. */
    public static final int NUM_ROWS= 6;
    /** The number of columns on the Connect Four board. */
    public static final int NUM_COLS= 7;

    /** vertical, horizontal, uphill, downhill, directions from any position */
    private static final int[][] deltas= {{1, 0}, {0, 1}, {-1, 1}, {1, 1}}; 
    
    /** The two opposing players. A null Player value in board
     *  indicates an empty tile. */
    public enum Player {
        RED, YELLOW;

        /** Return the opponent of this player. */
        public Player opponent() {
            return this == RED ? YELLOW : RED;
        }
        
        /** Return the Player as a String */
        public String toString() {
            return this == RED ? "RED" : "YELLOW";
        }
    }

    /** The grid of Player pieces. */
    private Player[][] board;

    /** Constructor: an empty Board. */
    public Board() {
        board= new Player[NUM_ROWS][NUM_COLS];
    }

    /** Constructor: a duplicate of Board b. */
    public Board(Board b) {
        board= new Player[NUM_ROWS][NUM_COLS];
        for (int r= 0; r < NUM_ROWS; r++) {
            for (int c= 0; c < NUM_COLS; c++) {
                board[r][c]= b.board[r][c];
            }
        }
    }
    
    /* Constructor: duplicates a board while omitting previous moves */
    
    public Board(Board b, int move_1, int move_2) {
        
        board = new Player[NUM_ROWS][NUM_COLS];
        
        for (int r= 0; r < NUM_ROWS; r++) {
            System.arraycopy(b.board[r], 0, board[r], 0, NUM_COLS);
        }
        
        for (int r = 0; r < NUM_ROWS; r++) {
            if (board[r][move_1] != null) {
                board[r][move_1] = null;
                break;
            }
        }
        
        for (int r = 0; r < NUM_ROWS; r++) {
            if (board[r][move_2] != null) {
                board[r][move_2] = null;
                break;
            }
        }
    }

    /** Return the element in row r col c.
     * Precondition: r and c give a position on the board */
    public Player getPlayer(int r, int c) {
        assert 0 <= r  &&  r < NUM_ROWS  &&  0 <= c  &&  c < NUM_COLS;
        return board[r][c];
    }

    /** Constructor: a Board constructed by duplicating b and
     *  applying nextMove to the new Board. */
    public Board(Board b, Move nextMove) {
        this(b);
        makeMove(nextMove);
    }

    /** Return the Player at board position (row, col). Rows are
     *  0-indexed starting at the top and columns are 0-indexed starting
     *  at the left. A null return value indicates an empty tile. */
    public Player getTile(int row, int col) {
        return board[row][col];
    }

    /**
     * Finds the lowest empty position in a column.
     * 
     *  @return The index of the lowest empty position or -1 if the column is full. 
     * */
    private int lowestFreeColumn(int col)
    {
    	int ret = -1;
    	for(int i = NUM_ROWS-1;i>=0;i--)
    	{
    		if(this.board[i][col]==null)
    		{
    			ret = i;
    			break;
    		}
    	}
    	return ret;
    }
    
    
    /** Apply Move move to this Board by placing a piece from move's
     *  player into move's column on this Board. Throw an
     *  IllegalArgumentException if move's column is full on this Board. */
    public void makeMove(Move move) {
    	int c = move.getColumn();
    	// Is the column full?
    	int free=lowestFreeColumn(c);
    	if(free == -1)
    	{
    		throw new IllegalArgumentException("The move cannot be made because the column is full.");
    	}
    	else
    	{
        	this.board[free][c] = move.getPlayer();
    	}
    }

    /** Return an array of all moves that can possibly be made by Player p on this
     *  board. The moves must be in order of increasing column number.
     *  Note: The length of the array must be the number of possible moves.
     *  Note: If the board has a winner (4 things of the same color in a row), no
     *  move is possible because the game is over.
     *  Note: If the game is not over, the number of possible moves is the number
     *  of columns that are not full. Thus, if all columns are full, return an
     *  array of length 0. */
    public Move[] getPossibleMoves(Player p) {
    	int numPossible = 0;
    	int[] freeColumns = new int[NUM_COLS];
    	if(hasConnectFour()== Player.RED || hasConnectFour() == Player.YELLOW)
    	{
    		return new Move[0];
    	}
    	
    	for(int i=0;i<NUM_COLS;i++)
    	{
    		if(lowestFreeColumn(i)!=-1)
    		{
    			freeColumns[numPossible] = i;
    			numPossible++;
    		}
    	}
    	Move[] m = new Move[numPossible];
    	for(int i=0;i<numPossible;i++)
    	{
    		m[i] = new Move(p,freeColumns[i]);
    	}
    		
    	return m;
    }

    /** Return a representation of this board */
    public @Override String toString() {
        return toString("");
    }

    /** Return the String representation of this Board with indent
     *  prepended to each line. Typically, indent contain space characters. */
    public String toString(String indent) {
        String str = "";
        for (Player[] row : board) {
            str += indent + "|";
            for (Player spot : row) {
                if (spot == null) {
                    str += " |";
                } else if (spot == Player.RED) {
                    str += "R|";
                } else {
                    str += "Y|";
                }
            }
            str += "\n";
        }
        return str;
    }

    /** Return the Player that has four in a row (or null if no player has). */
    public Player hasConnectFour() {
        for (Player[] loc : winLocations()) {
            if (loc[0] != null && loc[0] == loc[1] && loc[0] == loc[2] && loc[0] == loc[3]) {
                return loc[0];
            }
        }
        return null;
    }

    /** Return a list of all locations where it is possible to
     *  achieve connect four. In this context, a "win location" is an
     *  array of the Player pieces on this Board from four connected tiles. */
    public List<Player[]> winLocations() {
        List<Player[]> locations= new ArrayList<Player[]>();
        for (int[] delta : deltas) {
            for (int r= 0; r < NUM_ROWS; r++) {
                for (int c= 0; c < NUM_COLS; c++) {
                    Player[] loc= possibleWin(r, c, delta);
                    if (loc != null) {
                        locations.add(loc);
                    }
                }
            }
        }
        return locations;
    }

    /** If the 4 locations in a row beginning in board[r][c] going in the direction
     * given by [delta[0]][delta[1]] are on the board, return an array of them.
     * Otherwise, return null;
     * Precondition: board[r][c] is on the board and delta is one of the elements of
     * static variable deltas.
     */
    public Player[] possibleWin(int r, int c, int[] delta) {
        Player[] location = new Player[4];
        for (int i = 0; i < 4; i++) {
            int newR = r + i*delta[0];
            int newC= c + i*delta[1];
            if (!(0 <= newR  &&  newR < NUM_ROWS  &&  0 <= newC  &&  newC <  NUM_COLS)) {
                return null;
            }
            location[i]= board[newR][newC];
        }
        return location;
    }
    
    public String getBoardPosition() {
    	String returner = "";    	
    	int empty_counter = 0;
    	
    	for (int x = NUM_ROWS-1; x > -1; x--) 
    	{
    		empty_counter = 0;
    		for (int y = 0; y < NUM_COLS; y++) {
    			if (board[x][y] == null) {
    				returner += "0";
    				empty_counter += 1;
    			}
    			if (board[x][y] == Player.RED) {
    				returner += "1";
    				empty_counter = 0;
    			}
    			if (board[x][y] == Player.YELLOW) {
    				returner += "2";
    				empty_counter = 0;
    			}
    			if (empty_counter == 7) {
    				for (int z = 0; z < 7; z++) {
    					returner = returner.substring(0, returner.length()-1);
    				}
    				return returner;
    			}
    		}
    	}
    	
    	return returner;
    	
    }
}
