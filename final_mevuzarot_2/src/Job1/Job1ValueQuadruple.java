package Job1;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;

public class Job1ValueQuadruple implements WritableComparable<Job1ValueQuadruple>
{
    private Job1KeyPair pair;
    private IntWritable year;
    private LongWritable occurrences;
    private BooleanWritable isLeft;

    public Job1ValueQuadruple()
    {
        pair = new Job1KeyPair();
        year = new IntWritable();
        occurrences = new LongWritable();
        isLeft = new BooleanWritable();
    }

    public Job1ValueQuadruple(Job1KeyPair pair, IntWritable year, LongWritable occurrences, BooleanWritable isLeft)
    {
        this.pair = pair;
        this.year = year;
        this.occurrences = occurrences;
        this.isLeft = isLeft;
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year = new IntWritable(year);
    }

    public Job1KeyPair getPair() {
        return pair;
    }

    public void setPair(Job1KeyPair pair) {
        this.pair = pair;
    }

    public LongWritable getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(LongWritable occurrences) {
        this.occurrences = occurrences;
    }

    public BooleanWritable isLeft() {
        return isLeft;
    }

    @Override
    public String toString()
    {
        return pair.getLeftWord() + " " + pair.getRightWord() + " " + year + " " + occurrences + " " + isLeft;
    }

    public void write(DataOutput out) throws IOException {

        pair.write(out);
        year.write(out);
        occurrences.write(out);
        isLeft.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        pair.readFields(in);
        year.readFields(in);
        occurrences.readFields(in);
        isLeft.readFields(in);
    }

	@Override
	public int compareTo(Job1ValueQuadruple o) {
		// TODO Auto-generated method stub
		return 0;
	}

}