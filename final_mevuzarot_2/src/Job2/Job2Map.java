package Job2;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Job2Map
{
    public static class MapJob2 extends Mapper<LongWritable, Text, Job2KeyQuintuple, LongWritable>
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
                    Text leftWord = new Text(pair[0]);
                    Text rightWord = new Text(pair[1]);
                    LongWritable leftOcc = new LongWritable(Long.parseLong(occurrences[0]));
                    LongWritable rightOcc = new LongWritable(Long.parseLong(occurrences[1]));
                    IntWritable year = new IntWritable(Integer.parseInt(occurrences[2]));                    
                    
                    Job2KeyQuintuple keyQuintuple = new Job2KeyQuintuple(leftWord, rightWord, leftOcc, rightOcc, year);
                    
                    // key -> (leftWord, rightWord, leftOcc, rightOcc, year)
                    // value -> (occurrences)
                    context.write(keyQuintuple, new LongWritable(Long.parseLong(occurrences[3])));
                }
            }
        }
    }
}
