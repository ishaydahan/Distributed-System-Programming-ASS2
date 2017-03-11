package Job3;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Job3Map
{
    public static class MapJob3 extends Mapper<LongWritable, Text, Job3KeyPair, Job3ValueQuintuple>
    {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String line = value.toString();

            // get the data set line values that are separated by tab
            String[] dataSetLine = line.split("\t");

            // Verify that the data set line contains 2 values
            //1 - n-gram - The actual n-gram
            //2 - left count, right count, year, occurrences
            if (dataSetLine.length == 2)
            {
                String[] pair = dataSetLine[0].split(" ");
                String[] occurrences = dataSetLine[1].split(" ");

                if (pair.length == 2 && occurrences.length == 4)
                {
                    LongWritable leftOcc = new LongWritable(Long.parseLong(occurrences[0]));
                    LongWritable rightOcc = new LongWritable(Long.parseLong(occurrences[1]));
                    LongWritable totalOcc = new LongWritable(Long.parseLong(occurrences[2]));
                    IntWritable year = new IntWritable(Integer.parseInt(occurrences[3]));

                    // key -> (2-gram)
                    // value -> (2-gram, leftOcc, rightOcc, totalOcc, year)
                    Job3KeyPair keyPairA = new Job3KeyPair(new Text(pair[0]),new Text(pair[1]));
                    Job3ValueQuintuple val = new Job3ValueQuintuple(keyPairA, leftOcc, rightOcc, totalOcc, year);
                    context.write(keyPairA, val);

                    // key -> (*, *)
                    // value -> (2-gram, leftOcc, rightOcc, totalOcc, year)
                    Job3KeyPair keyPairB = new Job3KeyPair(new Text("*"), new Text("*"));
                    context.write(keyPairB, val);


                }
            }
        }


    }
}
