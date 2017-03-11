package Job4;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * The class re represents a related pair with it's decade
 */
public class Job4KeyPair implements WritableComparable<Job4KeyPair>
{
	
    private Text rightWord;
    private Text leftWord;
    private DoubleWritable npmi;

    /**
     * default constructor
     */
    public Job4KeyPair()
    {
        this.leftWord = new Text();
        this.rightWord = new Text();
        this.npmi = new DoubleWritable();
    }

    /**
     * constructor
     * @param leftWord the left word in the pair
     * @param rightWord the right word in the pair
     */
    public Job4KeyPair(Text leftWord, Text rightWord, DoubleWritable npmi)
    {
        this.leftWord = leftWord;
        this.rightWord = rightWord;
        this.npmi = npmi;

    }

    public Text getRightWord() {
        return rightWord;
    }

    public Text getLeftWord() {
        return leftWord;
    }
    
    public DoubleWritable getNpmi() {
        return npmi;
    }

    @Override
    public String toString()
    {
        return leftWord + " " + rightWord;
    }

    public void write(DataOutput out) throws IOException {

        leftWord.write(out);
        rightWord.write(out);
        npmi.write(out);

    }

    public void readFields(DataInput in) throws IOException {
        leftWord.readFields(in);
        rightWord.readFields(in);
        npmi.readFields(in);
    }

    public int compareTo(Job4KeyPair other)
    {
        if (leftWord.compareTo(new Text("*")) == 0 && other.getLeftWord().compareTo(new Text("*")) == 0)
        {
            return 0;
        }
        else if (leftWord.compareTo(new Text("*")) == 0 && other.getLeftWord().compareTo(new Text("*")) != 0)
        {
            return -1;
        }
        else if (leftWord.compareTo(new Text("*")) != 0 && other.getLeftWord().compareTo(new Text("*")) == 0)
        {
            return 1;
        }
        else
        {
            int ans = npmi.compareTo(other.getNpmi());
            return -1* ans;
        }
    }
}