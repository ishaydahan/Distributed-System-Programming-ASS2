package Job1;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Job1Sorting extends WritableComparator
{

    protected Job1Sorting() {
        super(Job1KeyPair.class, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    //we sort the keys just by 1 before 2, meaning (x, 1) (x, 2) is the list of one 2-gram in the same group
    public int compare(WritableComparable w1, WritableComparable w2)
    {
        Job1KeyPair k1 = (Job1KeyPair)w1;
        Job1KeyPair k2 = (Job1KeyPair)w2;
        
        int result = k1.getLeftWord().toString().compareTo(k2.getLeftWord().toString());
        if (result == 0)
        {
            result = k1.getRightWord().toString().compareTo(k2.getRightWord().toString());
        }
        return result;
    }
}
