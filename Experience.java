import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Experience {
	
	static final int WIN = 2;
	static final int LOSS = 1;
	static final int UNKNOWN = 0;
	static final int DRAW = 3;
	static final int ILLEGAL = 4;
	static final int PLAYER_1 = 1;
	static final int PLAYER_2 = 2;
	static final int EMPTY = 0;
        static String folderName = "BoardStates";
	
	
	public static void writeFile(String name, String contents) throws FileNotFoundException, UnsupportedEncodingException
	{
            PrintWriter writer = new PrintWriter (folderName+"/"+name+".txt", "UTF-8");
            writer.println(contents);
            writer.close();
	}
	
	public static String readFile(String name)
	{
            String returner = "";
            FileReader fr = null;
            try {
                fr = new FileReader(folderName+"/"+name+".txt");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Experience.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader br = new BufferedReader(fr);
            try {
                returner = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Experience.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Experience.class.getName()).log(Level.SEVERE, null, ex);
            }
            return returner;
	}
	
	public static String generateOldBoardState(String state, int moves, int player)
	{
            String returner = "";
            for (int x = (state.length()/7) - 1; x >= 0; x--) {
                System.out.println(state.charAt((x*7)+moves));
                if (state.charAt((x*7)+moves) == Integer.toString(player).charAt(0)) {
                    if ((x*7)+moves > 0) returner = state.substring(0,(x*7)+moves);
                    returner += 0;
                    returner += state.substring((x*7)+moves+1, state.length());
                    x = -1;
                }
            }
            
            int zero_counter = 0;
            
            for (int x = returner.length()-7; x < returner.length(); x++) {
                if (returner.charAt(x) == '0') zero_counter++;
                else zero_counter = 0;
            }
            
            if (zero_counter == 7) returner = returner.substring(0, returner.length() - 7);
            
            return returner;
	}
     /*
      * Takes a given string and modifies a specified character of that string 
      * to a specified value.
      * 
      * @param which the character of the string to modify; Zero based indexing.
      * @param result the character that replaces character at the specified index.
      * @param outcomes the original outcomes before modification. 
      * @return the modified string.
      */
    public static String editOutcomes(int which, int result, String outcomes)
	{
	       String modifiedOutcomes="";
	       // Bounds Checking
	       if(which >= outcomes.length() || which < 0)
	       {
	    	   //modifiedOutcomes = "The specifed character is outside of the string.";
	       }
	       else
	       {
	    	   modifiedOutcomes += outcomes.substring(0,which); 
	    	   modifiedOutcomes += String.valueOf(result);
	    	   modifiedOutcomes += outcomes.substring(which+1);
	       }
           return modifiedOutcomes;
	}
}
