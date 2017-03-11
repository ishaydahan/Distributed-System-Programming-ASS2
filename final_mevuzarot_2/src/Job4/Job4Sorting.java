package Job4;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Job4Sorting extends WritableComparator
{

    protected Job4Sorting() {
        super(Job4KeyPair.class, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        Job4KeyPair k1 = (Job4KeyPair)w1;
        Job4KeyPair k2 = (Job4KeyPair)w2;
        
        return k1.compareTo(k2);
    }
}
