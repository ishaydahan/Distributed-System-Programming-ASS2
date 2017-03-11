package Job2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Job2Main {

    private static final int NUM_OF_DECADES = 43;

    public static void main(String[] args) throws Exception
    {

        Configuration conf = new Configuration();

        //Set a meaningful name to the job
        @SuppressWarnings("deprecation")
		Job job = new Job(conf, "Job2MapReduce");
        
        //set input format
        job.setInputFormatClass(TextInputFormat.class);
        
        //Specify the class that hadoop needs to look in the JAR file
        //This Jar file is then sent to all the machines in the cluster        
        job.setJarByClass(Job2Main.class);
        
        //Set the Mapper and the Reducer class
        job.setMapperClass(Job2Map.MapJob2.class);
        job.setReducerClass(Job2Reduce.class);

        //Set the type of the key and value of Mapper and reducer
        job.setMapOutputKeyClass(Job2KeyQuintuple.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        //set partition method and how many partitions
        job.setPartitionerClass(Job2Partitioner.class);
        job.setNumReduceTasks(NUM_OF_DECADES);

        //define input and output folders
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        boolean flag = job.waitForCompletion(true);
        if (!flag)
        {
            System.err.println("Problem With Map-reduce 2");
            return;
        }
        else{
            System.out.println("job 2 finished successfully");
        }
    }
}

