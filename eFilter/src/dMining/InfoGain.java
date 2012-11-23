/**
 * 
 */
package dMining;

/**
 * @author cse93158
 *
 */
public class InfoGain {
	
	
	
	public static double claculate(double spamGivenW, double spamGivenNotW, double count )
	{		
		double w = (count/(Constants.TOTAL_HAM_TRAINNING+Constants.TOTAL_SPAM_TRAINNING));
		double not_w = 1- w;
		double hamGivenW= 1- spamGivenW;
		double hamGivenNotW = 1 -spamGivenNotW;
		
		
		if ( hamGivenW == 0.0 || spamGivenW == 0.0 ||  spamGivenNotW == 0.0 || hamGivenNotW ==0.0)
		{
			if (hamGivenW == 0.0 )
			{

				return w* (spamGivenW*Math.log10(spamGivenW)/Math.log10(2)) + not_w*(spamGivenNotW* Math.log10(spamGivenNotW)/Math.log10(2) +hamGivenNotW*Math.log10(hamGivenNotW)/Math.log10(2));
			}
			else if (spamGivenW == 0.0)
			{

				return w* (hamGivenW*Math.log10(hamGivenW)/Math.log10(2)) + not_w*(spamGivenNotW* Math.log10(spamGivenNotW)/Math.log10(2) +hamGivenNotW*Math.log10(hamGivenNotW)/Math.log10(2));

			}
			else if (spamGivenNotW ==0.0)
			{
				return w* (hamGivenW*Math.log10(hamGivenW)/Math.log10(2)+spamGivenW*Math.log10(spamGivenW)/Math.log10(2)) + not_w*(hamGivenNotW*Math.log10(hamGivenNotW)/Math.log10(2));
			}
			else
			{		
				return w* (hamGivenW*Math.log10(hamGivenW)/Math.log10(2)+spamGivenW*Math.log10(spamGivenW)/Math.log10(2)) + not_w*(spamGivenNotW* Math.log10(spamGivenNotW)/Math.log10(2));
			}
		}else
		{

			return w* (hamGivenW*Math.log10(hamGivenW)/Math.log10(2)+spamGivenW*Math.log10(spamGivenW)/Math.log10(2)) + not_w*(spamGivenNotW* Math.log10(spamGivenNotW)/Math.log10(2) +hamGivenNotW*Math.log10(hamGivenNotW)/Math.log10(2));
		}
	}
	
	public static void main(String[] args)
	{
		double res = InfoGain.claculate(24.0, 0.29210526315789476, 20.0);
	}
	
}
