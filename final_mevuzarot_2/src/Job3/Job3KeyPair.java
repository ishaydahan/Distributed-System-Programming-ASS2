package Job3;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class Job3KeyPair implements WritableComparable<Job3KeyPair>
{

    private Text rightWord;
    private Text leftWord;

    /**
     * default constructor
     */
    public Job3KeyPair()
    {
        this.leftWord = new Text();
        this.rightWord = new Text();
    }

    /**
     * constructor
     * @param leftWord the left word in the pair
     * @param rightWord the right word in the pair
     */
    public Job3KeyPair(Text leftWord, Text rightWord)
    {
        this.leftWord = leftWord;
        this.rightWord = rightWord;
    }

    public Text getRightWord() {
        return rightWord;
    }

    public Text getLeftWord() {
        return leftWord;
    }

    @Override
    public String toString()
    {
        return leftWord + " " + rightWord;
    }

    public void write(DataOutput out) throws IOException {

        leftWord.write(out);
        rightWord.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        leftWord.readFields(in);
        rightWord.readFields(in);
    }

	@Override
	public int compareTo(Job3KeyPair other) {
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
        	return other.getLeftWord().compareTo(other.getLeftWord());
        }
	}

}