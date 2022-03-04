/************************************************************************************************
* CLASS: Main (Main.java)
*
*This program takes an input file and analyzes the runs up 
* and runs down from in the file. Returns a new file 
* listing the total number of runs and the level of runs 
* within that file.
*
* COURSE AND PROJECT INFORMATION
* CSE205 Object Oriented Programming and Data Structures, 
* Spring Term A 2022
* Project Number: project-1
*
* AUTHOR: Gavin Beaudry, gbeaudry, gbeaudry@asu.edu
* AUTHOR: Chavon Kattner, ckattner, ckattner@asu.edu **
************************************************************************************************/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;	 
import java.util.ArrayList;  


public class Main 
{
	//static instance variables for runs up and runs down
  private static final int RUNS_UP = 1;
  private static final int RUNS_DN = -1;			

  //main method
  public static void main (String[] pArgs) 
  {			
	  //instantiate a class object
    Main mObject = new Main();

	  //call method run() on object
    mObject.run();	

  } //End Method main
  
  /**
  * method to run the program utilizing various methods 
  *   within the class
  * Creates ArrayList: list, fills with input from a file
  * handles FileNotFoundException
  * Creates ArrayLists counting RunsUp and RunsDn
  * Creates ArrayList: mergeLists, combines RUnsUp and 
  *   RunsDn
  * Writes a new file with total number of runs and the 
  * number of each run level (i.e. run of 1, 2,..., n)
  * handles file not found exception
  */
	private void run() 
  {						            	
    //Decalre ArrayList of Integer: list
    ArrayList<Integer> list = new ArrayList<>();		
    
    //try to read input
    try
    {							                                     
		  //Create ArrayList, list, from readInputFile(“p1-in.txt”);			              
      list = readInputFile("p1-in.txt");
	  }
	
    //catch file not found  
    catch(FileNotFoundException pException)		
	  {
      System.out.println("Opps, could not open 'p1-in.txt' for reading. The program is ending.");
		  
      //exit program
      System.exit(-100);						                  
	  }

	  //new ArrayLists Runs Up and Runs Dn
    ArrayList<Integer> listRunsUpCount = new ArrayList<>();
	  ArrayList<Integer> listRunsDnCount = new ArrayList<>();

    //fill declared ArrayLists using findRuns method
	  listRunsUpCount = findRuns(list, RUNS_UP);
	  listRunsDnCount = findRuns(list, RUNS_DN);

    //new ArrayList merging data from runsUp and runsDn
	  ArrayList<Integer> listRunsCount = new ArrayList<>(mergeLists(listRunsUpCount, listRunsDnCount));

    //try to write new file from previous created ArrayList
    try 
    {
      writeOutputFile("p1_runs.txt", listRunsCount);  
    }
  
    //catch file not found
    catch(FileNotFoundException pException)			
    {
      System.out.println("Oops, could not open 'p1-runs.txt' for writing. The program is ending.");
      
      //exit program
      System.exit(-200);						                       
    }

  }

  /**
  * instance method findRuns analyzes ArrayList input and * counts number of runsUp or runsDn and level of run
  * (run of 1, 2,..., n, adds tally to new ArrayList
  * @param pList, ArrayList of type Integer
  * @param pDir, int
  * @return listRunsCount, ArrayList of type Integer 
  */
  private ArrayList<Integer> findRuns(ArrayList<Integer> pList, int pDir)
  { 
    //Use arrayListCreate method to fill new ArrayList called listRunsCount
    ArrayList<Integer> listRunsCount = new ArrayList<>(arrayListCreate(pList.size(), 0));                            
	  //place holder variables
    int i = 0, k = 0;									                     

    //while loop counts runs and adds them to listRunsCount
	  while (i<pList.size()-1)
    {
		  if ((pDir == RUNS_UP) && (pList.get(i)<=pList.get(i+1))) 
      {
			  k++;
		  }

		  else if ((pDir == RUNS_DN) && (pList.get(i)>=pList.get(i+1))) 
      {
			  k++;
		  }

		  else 
      {
			  if (k!=0) 
        {
				  listRunsCount.set(k, listRunsCount.get(k)+1);
				  k=0;
			  }
		  }

		  i++;
	  }
    if (k!=0) 
    {
			listRunsCount.set(k, listRunsCount.get(k)+1);
		}
		return listRunsCount;

  } //End method findRuns

/**
	 * Instance method to merge the runsUp and runsDown ArrayLists
	 * @param pListRunsUpCount (runsUp)
	 * @param pListRunsDnCount (runsDn)
	 * @return listRunsCount: a list of number of runs
	 */
private ArrayList<Integer> mergeLists(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDnCount)
{
  //create new ArrayList to hold merged data
  ArrayList<Integer> listRunsCount = new ArrayList<Integer>();

  //for loop starting at runsUp index 0 to end of indices.
  for (int i = 0; i < pListRunsUpCount.size() - 1; ++i)
  {
    //at each i, add the element at i for both runsUp and runsDn. 	
    listRunsCount.add(pListRunsUpCount.get(i) + pListRunsDnCount.get(i));
  }

  return listRunsCount;
} //end method mergeLists

/**
	 * Instance method to create an ArrayList of indicated size with indicated values at each index
	 * @param pSize
	 * @param pInitValue
	 * @return list: new list 
	 */
private ArrayList<Integer> arrayListCreate(int pSize, int pInitValue)
{
  //create new ArrayList "list"
  ArrayList<Integer> list = new ArrayList<Integer>();

  //for loop: start at index 0, go to pSize - 1, add pInitValue to each index
  for (int i = 0; i < pSize; ++i)
  {
    list.add(pInitValue);
  }

  return list;
} //end method arrayListCreate

/**
	 * instance method for writing final product
	 * @param pFilename: name of output file
	 * @param pListRuns: listRunsCount
	 * @throws FileNotFoundException
	 */
private void writeOutputFile(String pFileName, ArrayList<Integer> pListRuns) throws FileNotFoundException
{
  //new PrintWriter object to write a new file
  PrintWriter out = new PrintWriter(pFileName);

  //instantiate sum to be used in printing
  int sum = 0;

  //for loop to create total runs in pListRuns
  for (int i : pListRuns)
  {
    sum = sum + i;
  }

  //formatted printer, runs_total left justified, sum right justified
  String str1 = "runs_total:";
  String str2 = "runs_";
  out.printf("%-11s %5d\n", str1, sum);

  //formatted printer to add each level of runs 
  for (int k = 1; k < pListRuns.size()-1; ++k)
  {
    out.printf("%-5s%-5d: %5d\n", str2, k, pListRuns.get(k));
  }

  out.close();

} //end method writeOutputFile

/**
	 * instance method to read input files 
	 * @param pFileName: file name to be provided
	 * @return list: ArrayList created from input file
	 * @throws FileNotFoundException
	 */
private ArrayList<Integer> readInputFile(String pFileName) throws FileNotFoundException
{
  //File to be read from
  File inFile = new File(pFileName);

  //Scanner to read the file
  Scanner in = new Scanner(inFile);

  //ArrayList to be returned
  ArrayList<Integer> list = new ArrayList<Integer>();

  //Read the input file and add each value to list
  while (in.hasNextInt())
  {
    int value = in.nextInt();
    list.add(value);
  }

  in.close();

  return list;
} //end method readInputFile

} //End Class main
