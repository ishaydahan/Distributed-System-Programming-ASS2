package Job3;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Job3Main {

    private static final int NUM_OF_DECADES = 43;

    public static void main(String[] args) throws Exception
    {

        Configuration conf = new Configuration();

        //Set a meaningful name to the job
        @SuppressWarnings("deprecation")
		Job job = new Job(conf, "Job3MapReduce");
        
        //set input format
        job.setInputFormatClass(TextInputFormat.class);
        
        //Specify the class that hadoop needs to look in the JAR file
        //This Jar file is then sent to all the machines in the cluster
        job.setJarByClass(Job3Main.class);
        
        //Set the Mapper and the Reducer class
        job.setMapperClass(Job3Map.MapJob3.class);
        job.setReducerClass(Job3Reduce.class);

        //Set the type of the key and value of Mapper and reducer
        job.setMapOutputKeyClass(Job3KeyPair.class);
        job.setMapOutputValueClass(Job3ValueQuintuple.class);
        job.setOutputKeyClass(Job3KeyPair.class);
        job.setOutputValueClass(Text.class);

        //set grouping method
        job.setGroupingComparatorClass(Job3Grouping.class);
        
        //set sorting method
        job.setSortComparatorClass(Job3Sorting.class);
        
        //set partition method and how many partitions
        job.setPartitionerClass(Job3Partitioner.class);
        job.setNumReduceTasks(NUM_OF_DECADES);

        //define input and output folders
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        boolean flag = job.waitForCompletion(true);
        if (!flag)
        {
            System.err.println("Problem With Map-reduce 3");
            return;
        }
        else
         {
            System.out.println("job 3 finished successfully");
        }
    }


}

