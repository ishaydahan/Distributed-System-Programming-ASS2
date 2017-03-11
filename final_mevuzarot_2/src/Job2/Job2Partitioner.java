package Job2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class Job2Partitioner extends Partitioner<Job2KeyQuintuple, LongWritable>
{

    @Override
    public int getPartition(Job2KeyQuintuple key, LongWritable value, int numPartitions)
    {
        // set the reducer according to the decade
        return (((key.getYear().get()) / 10) % numPartitions);
    }
}
