package Job2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Job2Reduce extends Reducer<Job2KeyQuintuple, LongWritable, Text, Text>
{
    protected void reduce(Job2KeyQuintuple key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException
    {
        //key = (leftWord, rightWord, leftOcc, rightOcc, year)
    	//value = Occurrences
        long totalOcc = 0;
        long leftOcc = 0;
        long rightOcc = 0;
        
        for (LongWritable value : values)
        {
        	leftOcc += key.getRightOcc().get();// we sum 0+num or num+0
        	rightOcc += key.getLeftOcc().get();
            totalOcc += value.get();
        }
               
        Text pairKey = new Text(key.getLeftWord() + " " + key.getRightWord());
        totalOcc /= 2; //we summed occurrences of left & right word so we need to divide by 2.
        Text pairValue = new Text(leftOcc + " " + rightOcc + " " + totalOcc + " " + key.getYear());

        context.write(pairKey, pairValue);
    }
}