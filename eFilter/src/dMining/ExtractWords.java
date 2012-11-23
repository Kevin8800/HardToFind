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
	 * @param dir
	 * @return map of words with files it in and frequency 
	 */
	public HashMap<String, HashMap<String, Integer>> extarctWords(File dir)
	{
		 HashMap<String, HashMap<String, Integer>> words = new  HashMap<String, HashMap<String, Integer>>();
		 
		 
			File folder = dir;
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) 
			{
				if(!file.isDirectory())
				{
					BufferedReader br =null;
					try 
					{
							br = new BufferedReader(new FileReader(file));
					         String availalbe;
					         while((availalbe = br.readLine()) != null) 
					         {   
					        	 	String [] candiates = availalbe.split("[^a-zA-Z]");
			
							       for(String c: candiates)
							       {
							           if (!checkstopword(c))
							           {
							               if (words.containsKey(c) && words.get(c).containsKey(file.getName()))
							               {
							                   words.get(c).put(file.getName(), words.get(c).get(file.getName()) + 1);
							               }else if (words.containsKey(c) && !words.get(c).containsKey(file.getName()))
							               {
							                   words.get(c).put(file.getName(), 1);
							               }else
							               {
							                   words.put(c, new HashMap<String, Integer>());
							                   words.get(c).put(file.getName(), 1);
							               }
							           }
							       }
					         }
					 	}catch (Exception e) 
				        {
				    				e.printStackTrace();
				        }finally 
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
	
	private boolean checkstopword(String s)
	{
		for (String w : Constants.StopWords)
		{
			if (s.equalsIgnoreCase(w))
			{
				return true;
			}
		}
		return false;
	}
}
