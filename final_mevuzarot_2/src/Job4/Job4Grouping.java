package Job4;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Job4Grouping extends WritableComparator
{

    protected Job4Grouping() {
        super(Job4KeyPair.class, true);
    }

    //everything needs to be at the same group
    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable w1, WritableComparable w2)
    {
        return 0;
    }
}

