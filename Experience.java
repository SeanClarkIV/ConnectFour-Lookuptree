import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class Experience {
	
	final int WIN = 2;
	final int LOSS = 1;
	final int UNKNOWN = 0;
	final int DRAW = 3;
	final int ILLEGAL = 4;
	final int PLAYER_1 = 1;
	final int PLAYER_2 = 2;
	final int EMPTY = 0;
	static String folderName = "BoardStates";
	
	
	public static void writeFile(String name, String contents) throws FileNotFoundException, UnsupportedEncodingException
	{
		 PrintWriter writer = new PrintWriter (folderName+"/"+name+".txt", "UTF-8");
         writer.println(contents);
         writer.close();
         //System.out.println(board.getBoardPosition());
	}
	
	public static String readFile(String name)
	{
		
		File file = new File (folderName+"/"+name);
		StringBuilder line = new StringBuilder();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader (new FileReader(file));
			String text = null;

			while ((text = reader.readLine()) !=null) {
				line.append(text)
				.append(System.getProperty ("line.separator"));
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		finally
		{
			try {
				if (reader !=null){
					reader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return line.toString();
		
	}
	
	public String generateOldBoardState(String state, int moves )
	{
            String returner = "";
            for (int x = (state.length()/7) - 1; x >= 0; x--) {
                if (state.charAt((x*7)+moves) != 0) {
                    if ((x*7)+moves > 0) returner = state.substring(0,(x*7)+moves);
                    returner += 0;
                    returner += state.substring((x*7)+moves+1, state.length());
                    x = state.length();
                }
            }
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
	    	   modifiedOutcomes = "The specifed character is outside of the string.";
	       }
	       else
	       {
	    	   modifiedOutcomes += outcomes.substring(0,which); 
	    	   modifiedOutcomes += String.valueOf(result);
	    	   modifiedOutcomes += outcomes.substring(which+1);
	       }
           return modifiedOutcomes;
	} 
    	public static void main(String[] args)
    	{
    		String Test = "10010001001001";
    		System.out.println(Test);
    		System.out.println(editOutcomes(1,9,Test).equals("19010001001001"));
    		System.out.println(editOutcomes(99,0,Test).equals("The specifed character is outside of the string."));
    		System.out.println(editOutcomes(0,9,Test).equals("90010001001001"));
    		System.out.println(editOutcomes(13,9,Test).equals("10010001001009"));
    		System.out.println(readFile("Test.txt"));
    }

}
