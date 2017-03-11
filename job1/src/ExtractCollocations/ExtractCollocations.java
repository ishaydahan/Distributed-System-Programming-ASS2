package ExtractCollocations;

import java.io.File;
import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class ExtractCollocations {

	public static PropertiesCredentials Credentials;
	public static AmazonS3 S3;
	public static String bucketName = "ishay";
	public static String propertiesFilePath = "src/ishay.properties";
	public static AmazonEC2 ec2;

	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		if (args.length < 4) {
			System.out.println("input error");
			return;
		}
		
		double minPmi = Double.parseDouble(args[0]);
		double relMinPmi = Double.parseDouble(args[1]);
//		String corpus = "s3://datasets.elasticmapreduce/ngrams/books/20090715/"+args[2]+"-all/2gram/data"; 
		String corpus = "s3://" + bucketName + "/input/" ; 

	    boolean removeStopWords = Boolean.parseBoolean(args[3]);
	    
	    boolean eng = args[2].contains("eng");

	    //connect to server
        System.out.println("LOCAL - connecting to servers\n");        	    
        File securityFile = new File(propertiesFilePath);
		Credentials = new PropertiesCredentials(securityFile);
		S3 = new AmazonS3Client(Credentials);
        ec2 = new AmazonEC2Client(Credentials);
	    AmazonElasticMapReduce mapReduce = new AmazonElasticMapReduceClient(Credentials);
				
		// change connection timeout to infinity to 
		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setConnectionTimeout(0);
		clientConfiguration.setSocketTimeout(0);

		//*********************************Job1 configuration***********************************************
		HadoopJarStepConfig hadoopJarStep1 = new HadoopJarStepConfig()
		    .withJar("s3://" + bucketName + "/Job1.jar")
		    .withMainClass("Job1.Job1Main")
		    .withArgs(corpus, "s3://" + bucketName + "/outputJob1/", Boolean.toString(eng), Boolean.toString(removeStopWords) );
		 
		StepConfig stepConfig1 = new StepConfig()
		    .withName("Job1")
		    .withHadoopJarStep(hadoopJarStep1)
			.withActionOnFailure("TERMINATE_JOB_FLOW");

		//*********************************Job2 configuration***********************************************
		HadoopJarStepConfig hadoopJarStep2 = new HadoopJarStepConfig()
				.withJar("s3://" + bucketName + "/Job2.jar")
				.withMainClass("Job2.Job2Main")
				.withArgs("s3://" + bucketName + "/outputJob1", "s3://" + bucketName + "/outputJob2");

		StepConfig stepConfig2 = new StepConfig()
				.withName("Job2")
				.withHadoopJarStep(hadoopJarStep2)
				.withActionOnFailure("TERMINATE_JOB_FLOW");

		//*********************************Job3 configuration***********************************************
		HadoopJarStepConfig hadoopJarStep3 = new HadoopJarStepConfig()
				.withJar("s3://" + bucketName + "/Job3.jar")
				.withMainClass("Job3.Job3Main")
				.withArgs("s3://" + bucketName + "/outputJob2/" ,"s3://" + bucketName + "/outputJob3/");

		StepConfig stepConfig3 = new StepConfig()
				.withName("Job3")
				.withHadoopJarStep(hadoopJarStep3)
				.withActionOnFailure("TERMINATE_JOB_FLOW");

		//*********************************Job4 configuration***********************************************
		HadoopJarStepConfig hadoopJarStep4 = new HadoopJarStepConfig()
				.withJar("s3://" + bucketName + "/Job4.jar")
				.withMainClass("Job4.Job4Main")
				.withArgs("s3://" + bucketName + "/outputJob3/", "s3://" + bucketName + "/outputJob4/", Double.toString(minPmi), Double.toString(relMinPmi));

		StepConfig stepConfig4 = new StepConfig()
				.withName("Job4")
				.withHadoopJarStep(hadoopJarStep4)
				.withActionOnFailure("TERMINATE_JOB_FLOW");

		//*********************************JobFlow configuration***********************************************
		JobFlowInstancesConfig instances = new JobFlowInstancesConfig()
				.withInstanceCount(5)
				.withMasterInstanceType(InstanceType.M1Small.toString())
				.withSlaveInstanceType(InstanceType.M1Small.toString())
				.withHadoopVersion("2.2.0").withEc2KeyName("ishay")
				.withKeepJobFlowAliveWhenNoSteps(false)
				.withPlacement(new PlacementType("us-east-1a"));

		RunJobFlowRequest runFlowRequest = new RunJobFlowRequest()
		    .withName("ExtractCollocations")
		    .withInstances(instances)
			.withSteps(stepConfig1, stepConfig2, stepConfig3, stepConfig4)
			.withLogUri("s3://" + bucketName + "/logs/");

		runFlowRequest.setServiceRole("EMR_DefaultRole");
		runFlowRequest.setJobFlowRole("EMR_EC2_DefaultRole");

		ClusterSummary theCluster = null;

		ListClustersRequest clustRequest = new ListClustersRequest().withClusterStates(ClusterState.WAITING);

		ListClustersResult clusterList = mapReduce.listClusters(clustRequest);
		for (ClusterSummary cluster : clusterList.getClusters()) {
			if (cluster != null)
				theCluster = cluster;
		}

		if (theCluster != null) {
			AddJobFlowStepsRequest request = new AddJobFlowStepsRequest()
					.withJobFlowId(theCluster.getId())
					.withSteps(stepConfig1, stepConfig2, stepConfig3, stepConfig4);
			AddJobFlowStepsResult runJobFlowResult = mapReduce.addJobFlowSteps(request);
			String jobFlowId = theCluster.getId();
			System.out.println("Ran job flow with id: " + jobFlowId);
		} else {
			RunJobFlowResult runJobFlowResult = mapReduce.runJobFlow(runFlowRequest);
			String jobFlowId = runJobFlowResult.getJobFlowId();
			System.out.println("Ran job flow with id: " + jobFlowId);
		}
	}

}
