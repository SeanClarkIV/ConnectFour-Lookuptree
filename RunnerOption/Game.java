/**********************************************************
 *   Project: Connect 4 Learning
 *
 *   Team 4:
 *		Sean Clark
 *		Thomas Coppola
 *		Anthony Fontanetta
 *		Sanjana Thomas
 **********************************************************/
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/** Runs the Connect Four game. */
public class Game {

    private Solver player1;   // The first player
    private Solver player2;  // The second player
    private Board board;     // the board
    private Solver activePlayer;  // The possible moves to the player whose turn it is
    private GUI gui;
    private Board.Player winner;  // null
    private static int previous_move_1 = -1;
    private static int previous_move_2 = -1;
    private boolean editing_outcomes = false;
    private String outcomes = "";
    private String current_outcomes = "";
    private int outcome = -1;

    //Change this if you would like a delay between plays
    private static final long SLEEP_INTERVAL= 0; //in milliseconds

    /** Have the computer play against itself, putting output int the default
     * Java console.
     * You can also have your new Minimax player play from here, with no GUI output.
     * It may be useful for testing.
     * If you want a human to play, use GUI.main.
     * @throws UnsupportedEncodingException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        /* p1 is the first player, p2 is the second player. This is set to
         * have to Dummy players--playing randomly.
         * To play the game with your own AI object, use assignments like you
         * see in the comments after these two assignments. In those assignments,
         * the second argument of the constructor is the depth to which AI
         * searches the game space. */
        //Solver p1= new Dummy(Board.Player.RED);
        //Solver p2= new Dummy(Board.Player.YELLOW); 
         
        Solver p1= new Minimax(Board.Player.RED, 3);
        Solver p2= new Minimax(Board.Player.YELLOW, 3);
        
        Game game= new Game(p1, p2);
        game.runGame();
        
              Board b= new Board();
              State s = new State(Board.Player.RED,b,null);
              
              Minimax.createGameTree(s, 6);
              Minimax testAI = new Minimax(Board.Player.RED,6);
              Minimax.minimax(testAI,s);
              
              s.writeToFile();
              Move[] moves= b.getPossibleMoves(Board.Player.RED);
             for (Move m : moves) {
                   System.out.println(m);
                   }
    }

    /** Construct a new Game with p1 as the first player, p2 as the second player,
     *  with b as the current Board state, and with it being p's turn to play
     *  true means player 1, false means player 2). */
    public Game(Solver p1, Solver p2, Board b, boolean p) {
        this(p1, p2);
        board= b;
        activePlayer= (p ? player1 : player2);
    }

    /** Constructor: a new Game with p1 as the first player and p2 as the second player.
     * It is p1's turn to play. 
     *  Precondition: p1 and p2 are different */
    public Game(Solver p1, Solver p2) {
        player1= p1;
        player2= p2;
        board= new Board();
        activePlayer= player1;
    }

    /** Attach GUI gui to this Game model. */
    public void setGUI(GUI gui) {
        this.gui= gui;
    }

    /** Notify this Game that column col has been clicked by a user. */
    public void columnClicked(int col) {
        if (activePlayer instanceof Human) {
            ((Human) activePlayer).columnClicked(col);
        }
    }

    /** Run the game until finished. If GUI is not initialized, the output will be 
     *  sent to the console. 
     * @throws UnsupportedEncodingException 
     * @throws FileNotFoundException */
    public void runGame() throws FileNotFoundException, UnsupportedEncodingException {
        for(int x = 0; x < 100000; x++) {
            //Checking to see that the move can be made (not overflowing a column)
            
            
            boolean moveIsSafe= false;
            Move nextMove= null;
            while (!moveIsSafe) {
                Move[] bestMoves= activePlayer.getMoves(board);
                if (bestMoves.length == 0) {
                    gui.setMsg("Game cannot continue until a Move is produced.");
                    continue;
                } else {
                    if (activePlayer == player1) {
                        if (editing_outcomes) {
                            boolean go = false;
                            
                            while (!go) {
                                if (board.getPossibleMoves(Board.Player.RED).length == 1) {
                                    if (board.getPossibleMoves(Board.Player.RED)[0].getColumn() == previous_move_1) {
                                        
                                        int holder = previous_move_1;
                                        
                                        //String board_state = Experience.readFile(board.getBoardPosition());
                                        //previous_move_1 = board_state.charAt(0) - 48;
                                        //previous_move_2 = board_state.charAt(1) - 48;
                                        
                                        String writer = "";
                                        writer += previous_move_1;
                                        writer += previous_move_2;
                                        for (x = 0; x < 7; x++) {
                                            if (x != holder) writer += Experience.ILLEGAL;
                                            else writer += outcome;
                                        }
                                        
                                        //Experience.writeFile(board.getBoardPosition(), writer);
                                        
                                        String old_board_state = Experience.generateOldBoardState(board.getBoardPosition(), previous_move_2, 2);
                                        old_board_state = Experience.generateOldBoardState(old_board_state, previous_move_1, 1);
                                        
                                        current_outcomes = Experience.readFile(old_board_state);
                                        current_outcomes = Experience.editOutcomes(previous_move_1+2, outcome, current_outcomes);
                                        previous_move_1 = current_outcomes.charAt(0);
                                        previous_move_2 = current_outcomes.charAt(1);
                                        //Experience.writeFile(old_board_state, current_outcomes);
                                        board = new Board(board, previous_move_1, previous_move_2);                                        
                                    }
                                    else go = true;                                        
                                }
                                else {
                                    
                                }
                            }
                        }
                        else nextMove= bestMoves[0];
                    }
                    else nextMove= bestMoves[0];
                }
                if (board.getTile(0,nextMove.getColumn()) == null) {
                    moveIsSafe= true; 
                } else {
                    gui.setMsg("Illegal Move: Cannot place disc in full column. Try again.");
                }
            }

            board.makeMove(nextMove);
            if (gui == null) {
                System.out.println(nextMove);
                System.out.println(board);
            } else {
                gui.updateGUI(board, nextMove);
            }
            
            if (activePlayer == player2) {                
                previous_move_2 = nextMove.getColumn();
                if (previous_move_1 != -1) {
                    outcomes = "";
                    outcomes += previous_move_1;
                    outcomes += previous_move_2;
                    if (!editing_outcomes) outcomes += "0000000";
                    //Experience.writeFile(board.getBoardPosition(), outcomes);
                }                
            }
            
            if (activePlayer == player1) {                
                previous_move_1 = nextMove.getColumn();                
            }
            
            activePlayer= (activePlayer == player1 ? player2 : player1);

            // The following code causes a delay so that you can easily view the plays
            // being made by the AIs
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                      
           if (isGameOver()) {               
               editing_outcomes = true;
               outcome = -99;
               if (winner == null) outcome = Experience.DRAW;
               else if ("RED".equals(winner.toString())) outcome = Experience.WIN;
               else if ("YELLOW".equals(winner.toString())) outcome = Experience.LOSS;
               
               String old_board_state = "";
               
               if (activePlayer == player2) {
                old_board_state = Experience.generateOldBoardState(board.getBoardPosition(), previous_move_1, 1);
               }
               else {
                   old_board_state = Experience.generateOldBoardState(board.getBoardPosition(), previous_move_2, 2);
                   old_board_state = Experience.generateOldBoardState(old_board_state, previous_move_1, 1);
               }
               
               current_outcomes = Experience.readFile(old_board_state);
               current_outcomes = Experience.editOutcomes(previous_move_1+2, outcome, current_outcomes);
               //Experience.writeFile(old_board_state, current_outcomes);
               
               board = new Board(board, previous_move_1, previous_move_2);
           }
        }

        if (gui == null) {
            if (winner == null) {
                System.out.println("Tie game!");
            } else {
                System.out.println(winner + " won the game!!!");
            }
        } else {
            gui.notifyGameOver(winner);
        }
    }

    /** Return true iff this game is over. If the game
     *  is over, set the winner field to the winner; if no winner
     *  set the winner to null. */
    public boolean isGameOver() {
        winner= board.hasConnectFour();
        if (winner != null) return true;

        // if these is an unfilled tile, return false;
        for (int r= 0; r < Board.NUM_ROWS; r= r+1) {
            for (int c= 0; c < Board.NUM_COLS; c= c+1) {
                if (board.getTile(r, c) == null)
                    return false;
            }
        }

        return true;
    }

}
