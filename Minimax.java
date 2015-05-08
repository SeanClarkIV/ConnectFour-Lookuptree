/**********************************************************
 *   Project: Connect 4 Learning
 *
 *   Team 4:
 *		Sean Clark
 *		Thomas Coppola
 *		Anthony Fontanetta
 *		Sanjana Thomas
 **********************************************************/
import java.util.List;

/** Claas that represents player that makes moves using algorithm Minimax. */
public class Minimax implements Solver {

	private Board.Player player; // the current player

	/** The depth of the search in the game space when evaluating moves. */
	private int depth;

	/** Searches to depth d when searching the game space for moves. */
	public Minimax(Board.Player p, int d) {
		player= p;
		depth= d;
	}

	/** 
	 * Implements a minimax solver 
	 *  
	 *  */
	public @Override Move[] getMoves(Board b) {
		Move[] preferedMoves;



		State s = new State(player,b,null);
		State[] currentLevel = s.getChildren();
		createGameTree(s,depth);
		minimax(this,s);

		if(s.getChildren() == null)
		{
			return new Move[0];
		}
		else
		{
			currentLevel = s.getChildren();

			State alpha = currentLevel[0];

			//Max
			if(currentLevel[0].getPlayer() == player.opponent())
			{
				for(int i=0;i<currentLevel.length;i++)
				{
					if(Math.max(alpha.getValue(), currentLevel[i].getValue()) != alpha.getValue())
					{
						alpha = currentLevel[i];
					}
				}
				preferedMoves = new Move[1];
				preferedMoves[0] = alpha.getLastMove();
				return preferedMoves;
			}
		}





		return null;
	}

	/** Generate the game tree with root s of depth d.
	 * The game tree's nodes are State objects that represent the state of a game
	 * and whose children are all possible States that can result from the next move.
	 * NOTE: this method runs in exponential time with respect to d.
	 * With d around 5 or 6, it is extremely slow and will start to take a very
	 * long time to run.
	 * Note: If s has a winner (4 in a row), it should be a leaf. */
	public static void createGameTree(State s, int d) {
		// Note: This method must be recursive, recursing on d,
		// which should get smaller with each recursive call
		if(d==0)
		{
			return;
		}
		else
		{
			s.initializeChildren();
			for(int i=0;i<s.getChildren().length;i++)
			{
				State[] n = s.getChildren();
				createGameTree(n[i],d-1);
			}
		}
	}

	/** Call minimax in AI with state s. */
	public static void minimax(Minimax ai, State s) {
		ai.minimax(s);
	}

	/** State s is a node of a game tree (i.e. the current State of the game).
	 * Use the Minimax algorithm to assign a numerical value to each State of the
	 * tree rooted at s, indicating how desirable that State is to this player. */
	public void minimax(State s) {
		if(s == null)
		{
			return;
		}
		else
		{
			for(int i=0;i<s.getChildren().length;i++)
			{
				State[] n = s.getChildren();
				minimax(n[i]);
			}
			s.setValue(s.getValue() + evaluateBoard(s.getBoard()));
			//MAX
			if(s.getChildren()!=null && s.getChildren().length > 0)
			{
				if(s.getPlayer() == player)
				{
					State[] n = s.getChildren();
					State alpha = n[n.length-1];
					for(int i=0;i<s.getChildren().length;i++)
					{
						if(Math.max(alpha.getValue(), n[i].getValue()) != alpha.getValue())
						{
							alpha = n[i];
						}
					}
					s.setValue(s.getValue() + alpha.getValue());
				}
				//MIN
				else
				{
					State[] n = s.getChildren();
					State beta = n[n.length-1];
					for(int i=0;i<s.getChildren().length;i++)
					{
						if(Math.min(beta.getValue(), n[i].getValue()) != beta.getValue())
						{
							beta = n[i];
						}
					}
					s.setValue(s.getValue() + beta.getValue());
				}
			}
		}

	}

	/** Evaluate the desirability of Board b for this player
	 * Precondition: b is a leaf node of the game tree (because that is most
	 * effective when looking several moves into the future). 
	 * The desireability is calculated as follows.
	 * 1. If the board does not have a winner: */
	public int evaluateBoard(Board b) {
		Board.Player winner= b.hasConnectFour();
		if (winner == null) {
			// Store in sum the value of board b. 
			int sum= 0;
			List<Board.Player[]> locs= b.winLocations();
			for (Board.Player[] loc : locs) {
				for (Board.Player p : loc) {
					sum= sum + (p == player ? 1 : p != null ? -1 : 0);
				}
			}
			return sum;
		}
		// There is a winner
		int numEmpty= 0;
		for (int r= 0; r < Board.NUM_ROWS; r= r+1) {
			for (int c= 0; c < Board.NUM_COLS; c= c+1) {
				if (b.getTile(r, c) == null) numEmpty += 1;
			}
		}
		return (winner == player ? 1 : -1) * 10000 * numEmpty;

	}

}
