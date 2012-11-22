package dMining;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
public class Launch {
	static double percentage = 0.01;
	static int spam_training = 117;
	static int ham_training = 283;
	static int feature_size = 100;
	public Launch() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		ArrayList<FileBean> files = new ArrayList<FileBean>();
		
		if (args.length != 1)
		{
			System.out.print("Usage : java -jar ExtractWords folder_name");
		}
		else
		{
			ExtractWords ex = new ExtractWords();

			File folder = new File(args[0]);
			File[] listOfFiles = folder.listFiles();
			int TrainingSize = listOfFiles.length;
			for (File i : listOfFiles) 
			{
				if(!i.isDirectory())
				{
					String classlabel = i.getName().split("_")[0];
					//Step1 : extarct keyword from file 
					HashMap<String, Integer> map = ex.extarct(i);
					
					FileBean file = new FileBean(i.getName(),classlabel,map);
					files.add(file);
				}
			}
			
			//Step2 : Feature selection : Document Frequency : If word appears in less than 1% of the document we will remove it.
			
			// get total word to remove  
			double theshold = TrainingSize * percentage ;
			Iterator<String> iterator  = ex.getTotalwords().keySet().iterator();
			HashSet<String> wordToRemove = new HashSet<String>();
			while (iterator.hasNext()) 
			{  
			   String key = iterator.next();
			   if (ex.getTotalwords().get(key) < theshold)
			   {
					wordToRemove.add(key);
			   }
			}
			
			for(String s: wordToRemove)
			{
				if (ex.getHam().containsKey(s))
				{
					ex.getHam().remove(s);
				}
				if (ex.getSpam().containsKey(s))
				{
					ex.getSpam().remove(s);
				}
				if ( ex.getTotalwords().containsKey(s))
				{
					ex.getTotalwords().remove(s);
				}
			}
			
			
			for (FileBean f : files)
			{	
				for(String s: wordToRemove)
				{
					if (f.getWords().containsKey(s))
					{
						f.getWords().remove(s);
					}
				}
			}
			
			/**
			 * Maybe we can check if some word that is only in ham /spam and use these words to discriminate
			 */
			
			
			
			//Step2 : Feature selection : information gain : Since the Entropy for the total set is the same just compute the later part
			HashMap<String, Integer> feature = new HashMap<String, Integer>();
			
			iterator = ex.getTotalwords().keySet().iterator();
			double max = 0;
			while ( iterator.hasNext())
			{
				String word = iterator.next();
				double number_of_w = 0.0 ; 
				double spam_given_number_of_w = 0.0 ; 
				double number_of_not_w = 0.0 ; 
				double spam_given_number_of_not_w = 0.0 ; 
				
				for (FileBean f : files)
				{
					if (f.getWords().containsKey(word))
					{
						number_of_w = number_of_w + 1;
						if(f.getClasslabel().equalsIgnoreCase("spam"))
						{
							spam_given_number_of_w = spam_given_number_of_w+1;
						}
					}
					else
					{
						number_of_not_w = number_of_not_w + 1;
						if (f.getClasslabel().equalsIgnoreCase("spam"))
						{
							spam_given_number_of_not_w = spam_given_number_of_not_w+1;
						}
					}
				}
				double info = InfoGain.claculate(spam_given_number_of_w/number_of_w,  spam_given_number_of_not_w/number_of_not_w , number_of_w);
				
				//sore_by_Entropy
				
				
				
				
			}
			
			
			
			
			
			
			//testing :
			
			/*
			for (FileBean f : files)
			{
				System.out.println("--------------------------------------");
				System.out.println("Filename : " + f.getFilename());
				System.out.println("Class : " + f.getClasslabel());
				iterator =  f.getWords().keySet().iterator();  
				while(iterator.hasNext())
				{
					String key = iterator.next().toString(); 
					int value =f.getWords().get(key);
					System.out.println(key +" " + f.getFilename() + " "+ value );
				}
				System.out.println("--------------------------------------");

			}
			*/
			
			
			/*
			iterator  = ex.getHam().keySet().iterator();
			System.out.println("!!!!!!ham FD");
			System.out.println("--------------------------------------");
			while (iterator.hasNext()) 
				{  
				String key = iterator.next().toString(); 
				int value =ex.getHam().get(key);
				System.out.println(key +" "+ value );
				}
			System.out.println("--------------------------------------");
			System.out.println("!!!!!!ham FD");
			System.out.println("--------------------------------------");
			*/
			//System.out.println( ex.getHam().size());
			//System.out.println( ex.getSpam().size());
		}
	}
}
