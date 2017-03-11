package Job1;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Job1Map
{
    public static class MapJob1 extends Mapper<LongWritable, Text, Job1KeyPair, Job1ValueQuadruple>
    {
        private List<String> stopWordsEng;
        private List<String> stopWordsHeb;

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
        	Boolean removeStopWords = context.getConfiguration().get("removeStopWords").equals("1");
        	Boolean eng = context.getConfiguration().get("eng").equals("eng");
        	
        	//use stop words or not
            if (removeStopWords){
            	if (eng){
                    stopWordsEng = Arrays.asList(new StopWordsEng().getStopWords());
            	}else{
                    stopWordsHeb = Arrays.asList(new StopWordsHeb().getStopWords());
            	}
            }
            
            String line = value.toString();

            // get the data set line values that are separated by tab
            String[] dataSetLine = line.split("\t");

            // Verify that the data set line contains at list 5 values
            //1 - n-gram - The actual n-gram
            //2 - year - The year for this aggregation
            //3 - occurrences - The number of times this n-gram appeared in this year
            //4 - pages - The number of pages this n-gram appeared on in this year
            //5 - books - The number of books this n-gram appeared in during this year
            if (dataSetLine.length > 4)
            {
                //get the twoGram
                String[] twoGram = dataSetLine[0].split(" ");
                
                //get the year
                IntWritable year = new IntWritable(Integer.parseInt(dataSetLine[1]));

                if (twoGram.length == 2) {
                	//filter stopwords + not letters words
                    if ( 	
                    		(
                    		removeStopWords
                    		&& eng 
                    		&& !stopWordsEng.contains(twoGram[0].toLowerCase())
                    		&& !stopWordsEng.contains(twoGram[1].toLowerCase()) 
                    		&& twoGram[0].matches("[a-zA-Z]+") 
                    		&& twoGram[1].matches("[a-zA-Z]+") 
                    		)
                    	
                    	||
                    	
	                		(
	                		removeStopWords
	                		&& !eng 
	                		&& !stopWordsHeb.contains(twoGram[0])
	                		&& !stopWordsHeb.contains(twoGram[1]) 
                    		&& twoGram[0].matches("[à-ú]+") 
                    		&& twoGram[1].matches("[à-ú]+") 
	                		)

                    	||
                    	
	                		(
	                		!removeStopWords
	                		)

                    ){
                        
                        //get the occurrences
                    	long occurrences = Long.parseLong(dataSetLine[2]);

                        Text leftWord = new Text(twoGram[0]);
                        Text rightWord = new Text(twoGram[1]);

                        // key -> (left, 1)
                        // value -> (2-gram, year, occurrences, isLeft=true
                        Job1KeyPair keyPairA = new Job1KeyPair(leftWord, new Text("1"));
                        Job1ValueQuadruple valuePairLeft = new Job1ValueQuadruple(new Job1KeyPair(leftWord, rightWord), year, new LongWritable(occurrences), new BooleanWritable(true));
                        context.write(keyPairA, valuePairLeft);

                        // key -> (left, 2)
                        // value -> (2-gram, year, occurrences, isLeft=true
                        Job1KeyPair keyPairB = new Job1KeyPair(leftWord, new Text("2"));
                        context.write(keyPairB, valuePairLeft);

                        // key -> (right, 1)
                        // value -> (2-gram, year, occurrences, isLeft=false
                        Job1KeyPair keyPairC = new Job1KeyPair(rightWord, new Text("1"));
                        Job1ValueQuadruple valuePairRight = new Job1ValueQuadruple(new Job1KeyPair(leftWord, rightWord), year, new LongWritable(occurrences), new BooleanWritable(false));
                        context.write(keyPairC, valuePairRight);

                        // key -> (right, 2)
                        // value -> (2-gram, year, occurrences, isLeft=false
                        Job1KeyPair keyPairD = new Job1KeyPair(rightWord, new Text("2"));
                        context.write(keyPairD, valuePairRight);
                        
                    }
                }
            }
        }
    }
}
