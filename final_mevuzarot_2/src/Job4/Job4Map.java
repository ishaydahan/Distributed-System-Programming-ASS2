package Job4;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Job4Map
{
    public static class MapJob4 extends Mapper<LongWritable, Text, Job4KeyPair, Job4Value>
    {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String line = value.toString();

            // get the data set line values that are separated by tab
            String[] dataSetLine = line.split("\t");

            // Verify that the data set line contains 2 values
            //1 - n-gram - The actual n-gram
            //2 - year, npmi
            if (dataSetLine.length == 2)
            {
                String[] pair = dataSetLine[0].split(" ");
                String[] data = dataSetLine[1].split(" ");

                if (pair.length == 2 && data.length == 2)
                {
                    IntWritable year = new IntWritable(Integer.parseInt(data[0]));
                    DoubleWritable normalizedPmi = new DoubleWritable(Double.parseDouble(data[1]));
   
                    // key -> (2-gram)
                    // value -> (2-gram, year, normalizedPmi)
                    Job4KeyPair keyPairA = new Job4KeyPair(new Text(pair[0]),new Text(pair[1]), normalizedPmi);
                    Job4Value val = new Job4Value(keyPairA, year, normalizedPmi);
                    context.write(keyPairA, val);

                    // key -> (*, *)
                    // value -> (2-gram, year, normalizedPmi)
                    Job4KeyPair keyPairB = new Job4KeyPair(new Text("*"), new Text("*"), normalizedPmi);
                    context.write(keyPairB, val);
                }
            }
        }
    }
}
