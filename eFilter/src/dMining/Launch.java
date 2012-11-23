package dMining;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
public class Launch {
	static double percentage = 0.02;
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
		
		HashMap<String ,HashMap<String,Double>> trainFiles = new HashMap<String, HashMap<String,Double>>();
		HashMap<String ,HashMap<String,Double>> testFiles = new HashMap<String, HashMap<String,Double>>();
		ArrayList<String> attributes = new ArrayList<String>();
		if (args.length != 2 || !new File(args[0]).isDirectory())
		{
			System.out.print("Usage : java -jar launch.jar folder_name(for training) folder_name(for testing set)\n");
			System.exit(-1);
		}
		else
		{
			ExtractWords ex = new ExtractWords();
			
			File trainFolder = new File (args[0]);
			File[] trainListOfFiles = trainFolder.listFiles();
			for (File f : trainListOfFiles) 
			{
				//initialize files
				trainFiles.put(f.getName(), new HashMap<String,Double>());
			}
			
			File testFolder = new File (args[1]);
			File[] testListOfFiles = testFolder.listFiles();
			for (File f : testListOfFiles) 
			{
				//initialize files
				testFiles.put(f.getName(), new HashMap<String,Double>());
			}
			
			//Step1 : Extract keyword from file 
			HashMap<String, HashMap<String, Integer>> testWords = ex.extarctWords(testFolder);
			HashMap<String, HashMap<String, Integer>> trainWords = ex.extarctWords(trainFolder);
			
			//Step2a : Feature selection : Document Frequency : If word appears in less than 1% of the document we will remove it.
			
			// get total word to remove  
			double theshold = (Constants.TOTAL_HAM_TRAINNING + Constants.TOTAL_SPAM_TRAINNING) * percentage ;
			Iterator<String> iterator  = trainWords.keySet().iterator();
			HashSet<String> wordToRemove = new HashSet<String>();
			while (iterator.hasNext()) 
			{  
				int count = 0 ;
				String key = iterator.next();
			   Iterator<String> iteratorFile  = trainWords.get(key).keySet().iterator();
			   	while(iteratorFile.hasNext())
			   	{
			   		String filename = iteratorFile.next();
			   		count = count + trainWords.get(key).get(filename);
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
				trainWords.remove(s);
			}
			
			/**
			 * Maybe we can check if some word that is only in ham /spam and use these words to discriminate
			 */
			//Step2b : Feature selection : information gain : Since the Entropy for the total set is the same just compute the later part
			HashMap<String, Double> feature = new HashMap<String, Double>();
			
			iterator = trainWords.keySet().iterator();
			int n = 0;
			while ( iterator.hasNext())
			{
				String word = iterator.next();
				double spam_given_number_of_w = 0.0 ; 
				double number_of_w = trainWords.get(word).size();
				double number_of_not_w = (Constants.TOTAL_HAM_TRAINNING+ Constants.TOTAL_SPAM_TRAINNING) - number_of_w;
				Iterator<String> iteratorFile  = trainWords.get(word).keySet().iterator();
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
			double max = list.get(feature_size - 1);
			iterator = feature.keySet().iterator();
			while(iterator.hasNext())
			{
				String word = iterator.next();


				if (feature.get(word) < max)
				{
					trainWords.remove(word);
				}
			}
			iterator = trainFiles.keySet().iterator();
			while (iterator.hasNext())
			{
				String filename = iterator.next();
				Iterator<String> iteratorword = trainWords.keySet().iterator();
				while(iteratorword.hasNext())
				{
					String word = iteratorword.next();
					//add attributes to list in the same order of train data for testing data
					attributes.add(word);
					if(trainWords.get(word).containsKey(filename))
					{
						trainFiles.get(filename).put(word, Math.log10((Constants.TOTAL_HAM_TRAINNING+ Constants.TOTAL_SPAM_TRAINNING)/trainWords.get(word).size())/Math.log10(2)* trainWords.get(word).get(filename));
					}
					else
					{
						trainFiles.get(filename).put(word, 0.0);
					}
				}
			}
			
			//do testFiles
			iterator = testFiles.keySet().iterator();
			while (iterator.hasNext())
			{
				String filename = iterator.next();
				for (int i=0; i < attributes.size(); i++)
				{
					String word = attributes.get(i);
					if(testWords.get(word) != null && testWords.get(word).containsKey(filename))
					{
						testFiles.get(filename).put(word, Math.log10((Constants.TOTAL_HAM_TRAINNING+ Constants.TOTAL_SPAM_TRAINNING)/testWords.get(word).size())/Math.log10(2)* testWords.get(word).get(filename));
					}
					else
					{
						testFiles.get(filename).put(word, 0.0);
					}
				}

			}
			
			try 
			{
				ArffGenerator.trainGenerate(feature_size, trainFiles);
				ArffGenerator.testGenerate(feature_size, testFiles);
			}catch(Exception e)
			{
				System.out.println("Error: Files generation failed");
				e.printStackTrace();
			}

		
		}
	}
}
