package Job3;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Job3Reduce extends Reducer<Job3KeyPair, Job3ValueQuintuple, Job3KeyPair, Text>
{

    protected void reduce(Job3KeyPair key, Iterable<Job3ValueQuintuple> values, Context context) throws IOException, InterruptedException
    {
        //accumulate all the values of this key
        long N = 0;
        for (Job3ValueQuintuple value : values)
        {
            // key -> (*, *)
            // value -> (2-gram, leftOcc, rightOcc, totalOcc, year)
            if (key.getLeftWord().compareTo(new Text("*")) == 0) //<*,*>
            {
            	N += value.getTotalOcc().get();
            }
            // key -> (2-gram)
            // value -> (2-gram, leftOcc, rightOcc, totalOcc, year)
            else
            {
                double npmi = calculateNpmi(value, N);
                context.write(key, new Text(value.getYear().get() + " " + npmi));
            }
        }
    }
    
    private double calculateNpmi(Job3ValueQuintuple value, long N)
    {
        double pmi = Math.log(value.getTotalOcc().get()) + Math.log(N) -Math.log(value.getLeftOcc().get()) - Math.log(value.getRightOcc().get());
        double p = (double)value.getTotalOcc().get() / (double)N; 
        double npmi = pmi / -Math.log(p);
		return npmi;
    }
}
