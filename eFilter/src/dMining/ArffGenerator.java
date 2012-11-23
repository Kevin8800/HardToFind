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
	
	
	public static void trainGenerate(int number_of_features, HashMap<String,HashMap<String, Double>> files) throws Exception
	{
		String prefix = Constants.train_File_Prefix;
		
		generate(prefix, number_of_features, files);
	}
	
	public static void testGenerate(int number_of_features, HashMap<String,HashMap<String, Double>> files) throws Exception
	{
		String prefix = Constants.test_File_Prefix;
		
		generate(prefix, number_of_features, files);
	}

	public static void generate(String prefix, int number_of_features, HashMap<String,HashMap<String, Double>> files) throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		String filename = prefix + dateFormat.format(date) + Constants.File_Extension;
		String filepath = Constants.DeskTop_location + "/" + filename;

		Writer output = null;
	    File file = new File(filepath);
	    output = new BufferedWriter(new FileWriter(file));
	    output.write("@relation Email_"+prefix+"\n");
	    output.write("\n");
	    
	    Iterator <String> iterator  = files.keySet().iterator();
	    if (!iterator.hasNext() )
	    {
	    	output.close();
	    	throw new Exception("Empty file hashmap");
	    }
	    
    	Iterator <String> words = files.get(iterator.next()).keySet().iterator();
    	while (words.hasNext())
    	{
	    	 output.write("@attribute " + words.next() + " numeric\n");
	    }
    	
	    output.write("\n");
	    output.write("@attribute " + "class" + " {spam,ham}\n");
	    output.write("\n");
	    output.write("@data");
	    output.write("\n");
	    output.write("\n");

	    iterator  = files.keySet().iterator();
	    while(iterator.hasNext() )
	    {
	    	String name_of_file = iterator.next();
	    	Iterator <String> wordIterator  = files.get(name_of_file).keySet().iterator();
	    	//output.write (name_of_file + " ");
	    	while (wordIterator.hasNext())
	    	{
	    		String word = wordIterator.next();
	    		output.write(files.get(name_of_file).get(word) +",");
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
		System.out.println("Done, File is generated at : " + filepath);

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		ArffGenerator afg = new ArffGenerator();
	//	afg.generate(8);
	}

}
