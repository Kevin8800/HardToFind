/**
 * 
 */
package dMining;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Vicky
 *
 */
public class ArffGenerator {

	/**
	 * 
	 */
	public ArffGenerator() {
		
	}

	public static void generate(int number_of_features, HashMap<String,HashMap<String, Double>> files) throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String filename = Constants.File_Prefix + dateFormat.format(date) + Constants.File_Extension;
		String filepath = Constants.DeskTop_location + "/" + filename;
		System.out.println(filepath);

		Writer output = null;
	    File file = new File(filepath);
	    output = new BufferedWriter(new FileWriter(file));
	    output.write("@relation Email_spam_train\n");
	    output.write("\n");
	    for(int i = 0 ; i < number_of_features; i ++)
	    {
	    	 output.write("@attribute " + "a"+i + " numeric\n");
	    }
	    output.write("\n");
	    output.write("@attribute " + "class" + " {spam,ham}\n");
	    output.write("\n");
	    output.write("@data");
	    output.write("\n");
	    output.write("\n");

	    Iterator <String> iterator  = files.keySet().iterator();
	    while(iterator.hasNext() )
	    {
	    	String name_of_file = iterator.next();
	    	Iterator <String> wordIterator  = files.get(name_of_file).keySet().iterator();
	    	//output.write (name_of_file + " ");
	    	while (wordIterator.hasNext())
	    	{
	    		String word = wordIterator.next();
	    		if (wordIterator.hasNext())
	    		{
	    			output.write(files.get(name_of_file).get(word) +",");
	    		}
	    	}
	    	
	    	if (name_of_file.contains(Constants.HAM_NAME))
	    	{
	    		output.write(Constants.HAM_NAME);
	    	}
	    	else
	    	{
	    		output.write(Constants.SPAM_NAME);

	    	}
	    	output.write ( "\n");

	    }
	    output.close();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		ArffGenerator afg = new ArffGenerator();
	//	afg.generate(8);
	}

}
