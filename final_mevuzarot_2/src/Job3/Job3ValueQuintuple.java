package Job3;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The class re represents a related pair with it's decade
 */
public class Job3ValueQuintuple implements WritableComparable<Job3ValueQuintuple>
{
    private Job3KeyPair pair;
    private LongWritable leftOcc;
    private LongWritable rightOcc;
    private LongWritable totalOcc;
    private IntWritable year;

    /**
     * default constructor
     */
    public Job3ValueQuintuple()
    {
        pair = new Job3KeyPair();
        leftOcc = new LongWritable();
        rightOcc = new LongWritable();
        totalOcc = new LongWritable();
        year = new IntWritable();
    }

    public Job3ValueQuintuple(Job3KeyPair pair, LongWritable leftOcc, LongWritable rightOcc, LongWritable totalOcc, IntWritable year)
    {
        this.pair = pair;
        this.leftOcc = leftOcc;
        this.rightOcc = rightOcc;
        this.totalOcc = totalOcc;
        this.year = year;
    }

    public Job3KeyPair getPair() { return pair; }

    public LongWritable getLeftOcc() {
        return leftOcc;
    }

    public LongWritable getRightOcc() {
        return rightOcc;
    }

    public LongWritable getTotalOcc() {
        return totalOcc;
    }

    public IntWritable getYear() {
        return year;
    }

    @Override
    public String toString()
    {
        return pair.getLeftWord() + " " + pair.getRightWord() + " " + leftOcc + " " + rightOcc + " " + totalOcc + " " + year;
    }

    public void write(DataOutput out) throws IOException {

        pair.write(out);
        leftOcc.write(out);
        rightOcc.write(out);
        totalOcc.write(out);
        year.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        pair.readFields(in);
        leftOcc.readFields(in);
        rightOcc.readFields(in);
        totalOcc.readFields(in);
        year.readFields(in);
    }

	@Override
	public int compareTo(Job3ValueQuintuple o) {
		// TODO Auto-generated method stub
		return 0;
	}

}