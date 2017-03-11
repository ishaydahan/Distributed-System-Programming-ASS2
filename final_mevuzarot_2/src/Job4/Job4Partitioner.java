package Job4;

import org.apache.hadoop.mapreduce.Partitioner;

public class Job4Partitioner extends Partitioner<Job4KeyPair, Job4Value>
{

    /**
     * send the record to the reducer according to the year
     */
    @Override
    public int getPartition(Job4KeyPair key, Job4Value value, int numPartitions)
    {
        // set the reducer according to the decade
        return (value.getYear().get() - 1500) / 10 % numPartitions;
    }
}

