package Job1;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Job1Reduce extends Reducer<Job1KeyPair, Job1ValueQuadruple, Job1KeyPair, Text>
{

    protected void reduce(Job1KeyPair key, Iterable<Job1ValueQuadruple> values, Context context) throws IOException, InterruptedException
    {
        //key = (x,1) (x,2)
    	//value = ((x,y), Occurrences, year, left) || ((t,x), Occurrences, year, right)
        long sumOfLeftOcc = 0;
        long sumOfRightOcc = 0;

        for (Job1ValueQuadruple value : values)
        {
        	//we start by working on 1 words, we know that 1 are orders before 2
            if (key.getRightWord().compareTo(new Text("1")) == 0)
            {
            	//if the key word was left word for the value
                if (value.isLeft().get() == true)
                {
                    sumOfLeftOcc += value.getOccurrences().get();
                }
                else
                {
                    sumOfRightOcc += value.getOccurrences().get();
                }
            }
            else
            {
            	//for each 2-gram we write at file ((x,y) 0, num of right y, year, Occurrences) + ((x,y) num of left x, 0, year, Occurrences)
            	long left = 0, right =0;
            	if (value.isLeft().get()) left = sumOfLeftOcc;
            	else right = sumOfRightOcc;

                context.write(value.getPair(), new Text(left + " " + right + " " + value.getYear() + " " + value.getOccurrences()));
            }
        }
    }
}

