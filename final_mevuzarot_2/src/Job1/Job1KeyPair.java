package Job1;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class Job1KeyPair implements WritableComparable<Job1KeyPair>
{

    private Text rightWord;
    private Text leftWord;

    public Job1KeyPair()
    {
        leftWord = new Text();
        rightWord = new Text();
    }

    public Job1KeyPair(Text leftWord, Text rightWord)
    {
        this.leftWord = leftWord;
        this.rightWord = rightWord;
    }

    public Text getRightWord() {
        return rightWord;
    }

    public void setRightWord(Text rightWord) {
        this.rightWord = rightWord;
    }

    public Text getLeftWord() {
        return leftWord;
    }

    public void setLeftWord(Text leftWord) {
        this.leftWord = leftWord;
    }

    @Override
    public String toString()
    {
        return getLeftWord() + " " + getRightWord();
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
	public int compareTo(Job1KeyPair o) {
		// TODO Auto-generated method stub
		return 0;
	}

}