package Job4;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Job4Reduce extends Reducer<Job4KeyPair, Job4Value, Text, Job4KeyPair>
{
    private double minPmi;
    private double relMinPmi;
	NumberFormat formatter = new DecimalFormat("#0.0000");

    @Override
    public void setup(Context context)
    {
        // read the values from the configuration
        minPmi = Double.parseDouble(context.getConfiguration().get("minPmi"));
        relMinPmi = Double.parseDouble(context.getConfiguration().get("relMinPmi"));
    }
    protected void reduce(Job4KeyPair key, Iterable<Job4Value> values, Context context) throws IOException, InterruptedException
    {
        //accumulate all the values of this key
        double sumOfnpmi = 0;
                
        for (Job4Value value : values)
        {
            // key -> (*, *)
            // value -> (2-gram, year, normalizedPmi)
            if (key.getLeftWord().compareTo(new Text("*")) == 0)
            {
            	sumOfnpmi += value.getNormalizedPmi().get();

            }
            // key -> (2-gram)
            // value -> (2-gram, year, normalizedPmi)
            else
            {
                if (value.getNormalizedPmi().get() >= minPmi && ((value.getNormalizedPmi().get() / sumOfnpmi) >= relMinPmi))
                {
                    context.write(new Text("coll & " + formatter.format(value.getNormalizedPmi().get()) ) , key);
                }else if (value.getNormalizedPmi().get() >= minPmi && !((value.getNormalizedPmi().get() / sumOfnpmi) >= relMinPmi))
                {
                    context.write(new Text("coll 1 " + formatter.format(value.getNormalizedPmi().get()) ) , key);
                }else if (!(value.getNormalizedPmi().get() >= minPmi) && ((value.getNormalizedPmi().get() / sumOfnpmi) >= relMinPmi))
                {
                    context.write(new Text("coll 2 " + formatter.format(value.getNormalizedPmi().get()) ) , key);
                }else{
                	
                }
            }
        }
    }
}

