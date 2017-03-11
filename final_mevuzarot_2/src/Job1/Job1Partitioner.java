package Job1;

import org.apache.hadoop.mapreduce.Partitioner;

public class Job1Partitioner extends Partitioner<Job1KeyPair, Job1ValueQuadruple>
{

    @Override
    public int getPartition(Job1KeyPair key, Job1ValueQuadruple value, int numPartitions)
    {
        // set the reducer according to the decade
        return (((value.getYear()) / 10) % numPartitions);
    }
}

