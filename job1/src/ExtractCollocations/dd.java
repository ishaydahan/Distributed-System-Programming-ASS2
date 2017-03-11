package ExtractCollocations;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;


public class dd {

	public static void main(String[] args) {
		long a = 1;
		long b = 1;
		long c = 1;
		calculateNpmi ( a, b, c, 100);
	}
	
    private static double calculateNpmi(Long left, Long right, Long all, long N)
    {
        double pmi = Math.log10(all) + Math.log10(N) -Math.log10(left) - Math.log10(right);
        
        
        double p = (double)all / (double)N; 
        double npmi = pmi / -Math.log10(p); 

        System.out.println(p);
        
		return Math.floor(npmi * Math.pow(10, 6)) / Math.pow(10, 6);
    }


}
