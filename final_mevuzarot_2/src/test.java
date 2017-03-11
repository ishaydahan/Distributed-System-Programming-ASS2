import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Job1.Job1Main;
import Job2.Job2Main;
import Job3.Job3Main;
import Job4.Job4Main;

public class test {

    public static void main(String[] args) throws Exception
    {
    	folderdel("two_eng");
    	folderdel("three_eng");
    	folderdel("four_eng");
    	folderdel("final_eng");

    	String [] args1 = {"a" , "input_eng" , "two_eng" , "eng", args[2]};
    	Job1Main.main(args1);
    	
    	String [] args2 = {"a" , "two_eng", "three_eng"};
    	Job2Main.main(args2);

    	String [] args3 = {"a" , "three_eng", "four_eng"};
    	Job3Main.main(args3);

    	String [] args4 = {"a" , "four_eng" , "final_eng" , args[0], args[1]};
    	Job4Main.main(args4);

    	folderdel("two_heb");
    	folderdel("three_heb");
    	folderdel("four_heb");
    	folderdel("final_heb");

    	String [] args11 = {"a" , "input_heb" , "two_heb" , "heb", args[2]};
    	Job1Main.main(args11);
    	
    	String [] args22 = {"a" , "two_heb", "three_heb"};
    	Job2Main.main(args22);

    	String [] args33 = {"a" , "three_heb", "four_heb"};
    	Job3Main.main(args33);

    	String [] args44 = {"a" , "four_heb" , "final_heb" , args[0], args[1]};
    	Job4Main.main(args44);

    	
    	try{
    	    PrintWriter writer = new PrintWriter("list.txt", "UTF-8");
    	    
  	       writer.println("@@@@@@@@@@@@@@@    ENGLISH    @@@@@@@@@@@@@@@@");

  	       
    	    for (int i=8; i<10 ; i++){
    	    	writer.print("*******************");
	    	    writer.print(1580 +(i-8)*10);
    	    	writer.println("*******************");

    	    	try (BufferedReader br = new BufferedReader(new FileReader("final_eng/part-r-0000"+i))) {
    	    	    String line;
    	    	    while ((line = br.readLine()) != null) {
     	    	       writer.println(line);
    	    	    }
    	    	}catch(Exception e){
    	    		e.printStackTrace();
    	    	}
	    	    writer.println("");
    	    }

    	    for (int i=10; i<43 ; i++){
    	    	writer.print("*******************");
	    	    writer.print(1580 +(i-8)*10);
    	    	writer.println("*******************");
    	    	try (BufferedReader br = new BufferedReader(new FileReader("final_eng/part-r-000"+i))) {
    	    	    String line;
    	    	    while ((line = br.readLine()) != null) {
    	    	       writer.println(line);
    	    	    }
    	    	}catch(Exception e){
    	    		e.printStackTrace();
    	    	}
	    	    writer.println("");
    	    }

    	    for (int i=0; i<8 ; i++){
    	    	writer.print("*******************");
	    	    writer.print(1930 +i*10);
    	    	writer.println("*******************");
    	    	try (BufferedReader br = new BufferedReader(new FileReader("final_eng/part-r-0000"+i))) {
    	    	    String line;
    	    	    while ((line = br.readLine()) != null) {
     	    	       writer.println(line);
    	    	    }
    	    	}catch(Exception e){
    	    		e.printStackTrace();
    	    	}
	    	    writer.println("");
    	    }

   	       writer.println("@@@@@@@@@@@@@@@    HEBREW    @@@@@@@@@@@@@@@@");

    	    
    	    for (int i=8; i<10 ; i++){
    	    	writer.print("*******************");
	    	    writer.print(1580 +(i-8)*10);
    	    	writer.println("*******************");	    	    FileInputStream fis = new FileInputStream("final_heb/part-r-0000"+i);
	    	    InputStreamReader in = new InputStreamReader(fis, "UTF-8");
    	    	try (BufferedReader br = new BufferedReader(in)) {
    	    	    String line;
    	    	    while ((line = br.readLine()) != null) {
     	    	       writer.println(line);
    	    	    }
    	    	}catch(Exception e){
    	    		e.printStackTrace();
    	    	}
	    	    writer.println("");
    	    }

    	    for (int i=10; i<43 ; i++){
    	    	writer.print("*******************");
	    	    writer.print(1580 +(i-8)*10);
    	    	writer.println("*******************");
	    	    FileInputStream fis = new FileInputStream("final_heb/part-r-000"+i);
	    	    InputStreamReader in = new InputStreamReader(fis, "UTF-8");
    	    	try (BufferedReader br = new BufferedReader(in)) {
    	    	    String line;
    	    	    while ((line = br.readLine()) != null) {
    	    	       writer.println(line);
    	    	    }
    	    	}catch(Exception e){
    	    		e.printStackTrace();
    	    	}
	    	    writer.println("");
    	    }

    	    for (int i=0; i<8 ; i++){
    	    	writer.print("*******************");
	    	    writer.print(1930 +i*10);
    	    	writer.println("*******************");
	    	    FileInputStream fis = new FileInputStream("final_heb/part-r-0000"+i);
	    	    InputStreamReader in = new InputStreamReader(fis, "UTF-8");
    	    	try (BufferedReader br = new BufferedReader(in)) {
    	    	    String line;
    	    	    while ((line = br.readLine()) != null) {
     	    	       writer.println(line);
    	    	    }
    	    	}catch(Exception e){
    	    		e.printStackTrace();
    	    	}
	    	    writer.println("");
    	    }
    	    writer.close();

    	    
    	}catch(Exception e){
    		e.printStackTrace();	
    	}

    }
    
    
    public static void folderdel(String path){
        File f= new File(path);
        if(f.exists()){
            String[] list= f.list();
            if(list.length==0){
                if(f.delete()){
                    System.out.println("folder deleted");
                    return;
                }
            }
            else {
                for(int i=0; i<list.length ;i++){
                    File f1= new File(path+"\\"+list[i]);
                    if(f1.isFile()&& f1.exists()){
                        f1.delete();
                    }
                    if(f1.isDirectory()){
                        folderdel(""+f1);
                    }
                }
                folderdel(path);
            }
        }
    }

}
