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
		System.out.println(w);
		double not_w = 1- w;
		
		System.out.println(not_w);
		double hamGivenW= 1- spamGivenW;
		double hamGivenNotW = 1 -spamGivenNotW;
		return w* (hamGivenW*Math.log10(hamGivenW)/Math.log10(2)+spamGivenW*Math.log10(spamGivenW)/Math.log10(2)) + not_w*(spamGivenNotW* Math.log10(spamGivenNotW)/Math.log10(2) +hamGivenNotW*Math.log10(hamGivenNotW)/Math.log10(2));
	}
	
	
	public static void main(String[] args)
	{
		double res = InfoGain.claculate(0.3, 0.29210526315789476, 20.0);
		System.out.println( res);
		System.out.println(0.940 +  res);
	}
	
}
