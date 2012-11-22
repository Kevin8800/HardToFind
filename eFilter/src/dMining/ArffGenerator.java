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

	public void generate(int number_of_features) throws Exception
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
	    output.write("@relation Email Spam\n");
	    output.write("\n");
	    for(int i = 0 ; i < number_of_features; i ++)
	    {
	    	 output.write("@attribute " + "a"+i + " numeric\n");
	    }
	    output.write("\n");
	    output.write("@attribute " + "class" + " {spam,ham}\n");
	    output.write("\n");
	    output.write("@data");
	    output.close();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		ArffGenerator afg = new ArffGenerator();
		afg.generate(8);
	}

}
