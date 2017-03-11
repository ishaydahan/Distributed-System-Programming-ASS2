package Job1;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Job1Grouping extends WritableComparator {

    protected Job1Grouping() {
        super(Job1KeyPair.class, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    //we group the keys just by the left word, meaning (x, 1) (x, 2) of one 2-gram in the same group
    public int compare(WritableComparable w1, WritableComparable w2) {
        Job1KeyPair k1 = (Job1KeyPair)w1;
        Job1KeyPair k2 = (Job1KeyPair)w2;

        return k1.getLeftWord().toString().compareTo(k2.getLeftWord().toString());
    }
}