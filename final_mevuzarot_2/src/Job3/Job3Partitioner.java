package Job3;

import org.apache.hadoop.mapreduce.Partitioner;

public class Job3Partitioner extends Partitioner<Job3KeyPair, Job3ValueQuintuple>
{

    @Override
    public int getPartition(Job3KeyPair key, Job3ValueQuintuple value, int numPartitions)
    {
        // set the reducer according to the decade
        return (((value.getYear().get()) / 10) % numPartitions);
    }
}

