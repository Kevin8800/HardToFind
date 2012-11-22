package dMining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class ExtractWords {
	private HashMap<String, Integer> ham;
	private HashMap<String, Integer> spam;
	private  HashMap<String, Integer> totalwords;
	public HashMap<String, Integer> getHam() {
		return ham;
	}
	public void setHam(HashMap<String, Integer> ham) {
		this.ham = ham;
	}
	public HashMap<String, Integer> getSpam() {
		return spam;
	}
	public void setSpam(HashMap<String, Integer> spam) {
		this.spam = spam;
	}
	public ExtractWords() {
		super();
		this.ham = new HashMap<String, Integer>();
		this.spam = new HashMap<String, Integer>();
		this.totalwords = new HashMap<String, Integer>();
	}

	/**
	 * 
	 * @param filename : dir
	 * @return HashMap of words and it's count 
	 * @throws FileNotFoundException 
	 */
	public HashMap<String, Integer> extarct(File file)
	{
		 HashMap<String, Integer> words = new HashMap<String, Integer>();
		 BufferedReader br =null;
		 	try 
		 	{
			 	 br = new BufferedReader(new FileReader(file));
		         String availalbe;
		         while((availalbe = br.readLine()) != null) 
		         {
		            String[] cadidates = availalbe.split("[^a-zA-Z]");
	                for(String s : cadidates)
	                {
		                	s = s.toLowerCase();
		                	
	                			if (!words.containsKey(s))
		                		{
		                			
		                			words.put(s, 1);
		                			if (!totalwords.containsKey(s))
		                			{
		                				totalwords.put(s, 1);
		                			}
		                			else
		                			{
		                				totalwords.put(s, (totalwords.get(s) + 1));
		                			}
		                			if (file.getName().contains("ham"))
		                			{
			                			if (!ham.containsKey(s))
			                			{
			                				ham.put(s, 1);
			                			}
			                			else
			                			{
			                				ham.put(s, (ham.get(s) + 1));
			                			}
		                			}
		                			else
		                			{
			                			if (!spam.containsKey(s))
			                			{
			                				spam.put(s, 1);
			                			}
			                			else
			                			{
			                				spam.put(s, (spam.get(s) + 1));
			                			}
		                			}
		                		}
		                		else
		                		{
		                			words.put(s, words.get(s) + 1);		                		
		                		}
	                }
		         }
     
		 	}
	        catch (Exception e) 
	        {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	        }
		 	finally 
		 	{
		         if (br != null) 
		         {
		            try 
		            {
		               br.close();
		            } 
		            catch (IOException e) 
		            {
		               e.printStackTrace();
		            }
		         }
		 	}
        return words;

	}
	public HashMap<String, Integer> getTotalwords() {
		return totalwords;
	}
	public void setTotalwords(HashMap<String, Integer> totalwords) {
		this.totalwords = totalwords;
	}
	

}
