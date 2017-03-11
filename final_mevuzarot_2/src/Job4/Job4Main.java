package Job4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Job4Main
{
    private static final int NUM_OF_DECADES = 43;

    public static void main(String[] args) throws Exception
    {
    	
        Configuration conf = new Configuration();
        conf.set("minPmi", args[3]);				
        conf.set("relMinPmi", args[4]);
        conf.set("mapreduce.output.textoutputformat.separator","  ");
        
        //Set a meaningful name to the job
        @SuppressWarnings("deprecation")				
		Job job = new Job(conf, "Job4MapReduce");
        
        //set input format
        job.setInputFormatClass(TextInputFormat.class);
        
        //Specify the class that hadoop needs to look in the JAR file
        //This Jar file is then sent to all the machines in the cluster
        job.setJarByClass(Job4Map.class);
        
        //Set the Mapper and the Reducer class
        job.setMapperClass(Job4Map.MapJob4.class);
        job.setReducerClass(Job4Reduce.class);

        //Set the type of the key and value of Mapper and reducer
        job.setMapOutputKeyClass(Job4KeyPair.class);
        job.setMapOutputValueClass(Job4Value.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Job4KeyPair.class);

        //set grouping method
        job.setGroupingComparatorClass(Job4Grouping.class);
        
        //set sorting method
        job.setSortComparatorClass(Job4Sorting.class);

        //set partition method and how many partitions
        job.setPartitionerClass(Job4Partitioner.class);
        job.setNumReduceTasks(NUM_OF_DECADES);

        //define input and output folders
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        boolean flag = job.waitForCompletion(true);
        if (!flag)
        {
            System.err.println("Problem With Map-reduce 4");
            return;
        }
        else{
            System.out.println("job 4 finished successfully");
        	return;
        }
    }


}

