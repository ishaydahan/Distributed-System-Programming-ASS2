package Job3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Job3Sorting extends WritableComparator
{

    protected Job3Sorting() {
        super(Job3KeyPair.class, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        Job3KeyPair k1 = (Job3KeyPair)w1;
        Job3KeyPair k2 = (Job3KeyPair)w2;
        
        return k1.compareTo(k2);
    }
}

