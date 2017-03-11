package Job1;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Job1Main {

    private static final int NUM_OF_DECADES = 43;

    public static void main(String[] args) throws Exception
    {

    	@SuppressWarnings("unchecked")
		List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
    	loggers.add(LogManager.getRootLogger());
    	for ( Logger logger : loggers ) {
    	    logger.setLevel(Level.OFF);
    	}
    	
        Configuration conf = new Configuration();
        conf.set("eng",args[3]);
        conf.set("removeStopWords",args[4]);

        System.out.println("lang is english = " + args[3].equals("eng"));
        System.out.println("remove stop words = " + args[4].equals("1"));

        //Set a meaningful name to the job
        @SuppressWarnings("deprecation")
		Job job = new Job(conf, "Job1MapReduce");
        
        //set input format
        job.setInputFormatClass(SequenceFileInputFormat.class);
        
        //Specify the class that hadoop needs to look in the JAR file
        //This Jar file is then sent to all the machines in the cluster
        job.setJarByClass(Job1Main.class);
                
        //Set the Mapper and the Reducer class
        job.setMapperClass(Job1Map.MapJob1.class);
        job.setReducerClass(Job1Reduce.class);

        //Set the type of the key and value of Mapper and reducer
        job.setMapOutputKeyClass(Job1KeyPair.class);
        job.setMapOutputValueClass(Job1ValueQuadruple.class);
        job.setOutputKeyClass(Job1KeyPair.class);
        job.setOutputValueClass(Text.class);

        //set grouping method
        job.setGroupingComparatorClass(Job1Grouping.class);
        
        //set sorting method
        job.setSortComparatorClass(Job1Sorting.class);
        
        //set partition method and how many partitions
        job.setPartitionerClass(Job1Partitioner.class);
        job.setNumReduceTasks(NUM_OF_DECADES);

        //define input and output folders
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        //start working
        boolean flag = job.waitForCompletion(true);
        if(!flag)
        {
            System.err.println("Problem With Map-reduce 1");
            return;
        }
        else{
            System.out.println("job 1 finished successfully");
        }
    }




}

