import java.io.File;
import java.util.Random;

public class DatabasePlayer implements Solver{
	private Board.Player myColor;
	
	// Default Constructor
	public DatabasePlayer(Board.Player color)
	{
		myColor = color;
	}
	// Returns the move to play.
    public @Override Move[] getMoves(Board b) {
		// First Knowledge Perfect Play begins with the 3rd column on a standard board.
		if(b.getBoardPosition().equals(""))
		{
			Move[] move= { new Move(myColor, 3) };
			return move;
		}
		
		Random rand= new Random();
    	String filename = Experience.folderName+"/"+b.getBoardPosition()+".txt";
    	File f = new File(filename);
    	if(f.exists()) 
    	{
    		String knowledge = Experience.readFile(b.getBoardPosition());
    		knowledge = knowledge.substring(2);
    		// Play a winning move
    		if(knowledge.contains(String.valueOf(Experience.WIN)))
    		{
    			int column = knowledge.indexOf(String.valueOf(Experience.WIN));
    			Move[] move= { new Move(myColor, column) };
    			return move;
    		}
    		// We don't know about the correct move so play any move that doesn't make us lose.
    		else
    		{
        		int column= rand.nextInt(Board.NUM_COLS);
        		while (b.getTile(0, column) != null && !(knowledge.substring(column, column+1).equals(String.valueOf(Experience.LOSS))) ) {
        			column= rand.nextInt(Board.NUM_COLS);
        		}
        		Move[] move= { new Move(myColor, column) };
        		return move;
    		}
    	}
    	// Play randomly we have absolutely no knowledge.
    	else
    	{
    		int column= rand.nextInt(Board.NUM_COLS);
    		while (b.getTile(0, column) != null) {
    			column= rand.nextInt(Board.NUM_COLS);
    		}
    		Move[] move= { new Move(myColor, column) };
    		return move;
    	}
	}
}
