package dMining;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
public class Launch {
	static double percentage = 0.05;
	static int spam_training = 117;
	static int ham_training = 283;
	static int feature_size = 50;
	public Launch() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		HashMap<String ,HashMap<String,Double>> files = new HashMap<String, HashMap<String,Double>>();
		if (args.length != 1 || !new File(args[0]).isDirectory())
		{
			System.out.print("Usage : java -jar launch.jar folder_name");
			System.exit(-1);
		}
		else
		{
			ExtractWords ex = new ExtractWords();
			
			File folder = new File (args[0]);
			File[] listOfFiles = folder.listFiles();
			for (File f : listOfFiles) 
			{
				//initialize files
				files.put(f.getName(), new HashMap<String,Double>());
			}
			
			//Step1 : Extract keyword from file 
			HashMap<String, HashMap<String, Integer>> words = ex.extarctWords(folder);
			
			//Step2a : Feature selection : Document Frequency : If word appears in less than 1% of the document we will remove it.
			
			// get total word to remove  
			double theshold = (Constants.TOTAL_HAM_TRAINNING + Constants.TOTAL_SPAM_TRAINNING) * percentage ;
			Iterator<String> iterator  = words.keySet().iterator();
			HashSet<String> wordToRemove = new HashSet<String>();
			while (iterator.hasNext()) 
			{  
				int count = 0 ;
				String key = iterator.next();
			   Iterator<String> iteratorFile  = words.get(key).keySet().iterator();
			   	while(iteratorFile.hasNext())
			   	{
			   		String filename = iteratorFile.next();
			   		count = count + words.get(key).get(filename);
			   	}
	   			if (count < theshold)
	   			{
	   				wordToRemove.add(key);
	   			}
	   			else
	   			{
	   				
	   			}
			}
			
			for(String s: wordToRemove)
			{
				words.remove(s);
			}
			
			/**
			 * Maybe we can check if some word that is only in ham /spam and use these words to discriminate
			 */
			//Step2b : Feature selection : information gain : Since the Entropy for the total set is the same just compute the later part
			HashMap<String, Double> feature = new HashMap<String, Double>();
			
			iterator = words.keySet().iterator();
			int n = 0;
			while ( iterator.hasNext())
			{
				String word = iterator.next();
				double spam_given_number_of_w = 0.0 ; 
				double number_of_w = words.get(word).size();
				double number_of_not_w = (Constants.TOTAL_HAM_TRAINNING+ Constants.TOTAL_SPAM_TRAINNING) - number_of_w;
				Iterator<String> iteratorFile  = words.get(word).keySet().iterator();
				while (iteratorFile.hasNext())
				{
					String filename = iteratorFile.next();
					if (filename.contains(Constants.SPAM_NAME))
					{
						spam_given_number_of_w = spam_given_number_of_w + 1;
					}
				}
				double spam_given_number_of_not_w = Constants.TOTAL_SPAM_TRAINNING - spam_given_number_of_w;
				double info ;
				if ( number_of_not_w == 0.0 )
				{
					info = InfoGain.claculate(spam_given_number_of_w/number_of_w, 0.0 , number_of_w);

				}
				else if (number_of_w == 0.0)
				{
					info = InfoGain.claculate(0.0, spam_given_number_of_not_w/number_of_not_w , number_of_w);

				}
				else
				{
					info = InfoGain.claculate(spam_given_number_of_w/number_of_w,  spam_given_number_of_not_w/number_of_not_w , number_of_w);
				}
				Double Info = new Double(info);
				
			
				if (Info.isNaN() )
				{
					System.out.println(spam_given_number_of_w+ " / " + number_of_w + " : " + spam_given_number_of_not_w + " /"+ number_of_not_w);

				}
				feature.put(word, info);
			}
			
			//Sore feature by entropy
			ArrayList<Double> list = new ArrayList<Double>(feature.values());
			Collections.sort(list);
			Collections.reverse(list);
			
			//Select feature with high Information Gain
			double max = list.get(feature_size);
			iterator = feature.keySet().iterator();
			while(iterator.hasNext())
			{
				String word = iterator.next();


				if (feature.get(word) < max)
				{
					words.remove(word);
				}
			}
			iterator = files.keySet().iterator();
			while (iterator.hasNext())
			{
				String filename = iterator.next();
				Iterator<String> iteratorword = words.keySet().iterator();
				while(iteratorword.hasNext())
				{
					String word = iteratorword.next();
					if(words.get(word).containsKey(filename))
					{
						files.get(filename).put(word, Math.log10((Constants.TOTAL_HAM_TRAINNING+ Constants.TOTAL_SPAM_TRAINNING)/words.get(word).size())/Math.log10(2)* words.get(word).get(filename));
					}
					else
					{
						files.get(filename).put(word, 0.0);
					}
				}
			}
			try 
			{
				ArffGenerator.generate(feature_size, files);
			}catch(Exception e)
			{
				System.out.println("Error: Files generation failed");
				e.printStackTrace();
			}

		
		}
	}
}
