package Job3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Job3Grouping extends WritableComparator
{

    protected Job3Grouping() {
        super(Job3KeyPair.class, true);
    }

    //everything needs to be at the same group
    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable w1, WritableComparable w2)
    {
        return 0;
    }
}

