///////////////////////////////////////////////////////////////////////////////
//
// Title:            Project 1
// Files:            InteractiveDBTester.java, EmployeeDatabase.java, Employee.java, sampleInput.txt
// Semester:         CS367 Fall 2016
//
// Author:           Justin High
// Email:            
// CS Login:         high
// Lecturer's Name:  Charles Fischer
// Lab Section:      N/A
//
//////////////////////////////////////////////////////////////////////////////

package written;

import java.util.*;

import given.Employee;

import java.io.*;
import java.text.DecimalFormat;

import written.EmployeeDatabase;

public class InteractiveDBTester {
    public static void main(String[] args) {
    	boolean Debug = true;
    	
    	//Check whether exactly one command-line argument is given; if not, display "Please provide input file as command-line argument" and quit.
    	int arglen = args.length; Boolean argisgood = (arglen == 1);
    	if (!argisgood) {
    		System.out.println("Please provide input file as command-line argument.");
    		return;
    	}

    	//Check whether the input file exists and is readable; if not, display "Error: Cannot access input file" and quit.
    	String inputFileName = args[0];
    	File inputFile = new File(inputFileName);
    	Scanner inputFileScanner = null;
    	try {
			inputFileScanner = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
    		System.out.println("Error: Cannot access input file");
			if (Debug) {
				e.printStackTrace();	
			}
			return;
		}
    	
    	// Load the data from the input file
    	ArrayList<String> inputLines = new ArrayList<String>();
    	try {
    		while (inputFileScanner.hasNextLine()) {
    			inputLines.add(inputFileScanner.nextLine());
    		}
    	}
    	catch (IllegalStateException e) {
    		System.out.println("Error: File lost during processing");
    		if (Debug) {
    			e.printStackTrace();
    		}
    	}
    	inputFileScanner.close(); // close unmanaged resource
    	
    	// and use it to construct an employee database.
    	EmployeeDatabase empDb = new EmployeeDatabase();
    	Iterator<String> inputLineIterator = inputLines.iterator();
    	while (inputLineIterator.hasNext()) {
    		String currentLine = inputLineIterator.next();
    		String[] currentLinePieces = currentLine.split(",");
    		
    		// create new employee
    		String employeeName = currentLinePieces[0].toLowerCase();
    		try {
    			empDb.addEmployee(employeeName);    			
    		}
    		catch (IllegalArgumentException e) {
    			if (Debug) {
    				System.out.println(e.toString() + " while loading employee " + employeeName + " to the database.");
    				e.printStackTrace();
    			}
    		}
    		if (Debug) {
    			System.out.println("Added employee " + employeeName);
    		}
    		
    		// add destinations to new employee
    		for (int i = 1; i < currentLinePieces.length; i++) {
    			String destination = currentLinePieces[i];
    			try {
    				empDb.addDestination(employeeName, destination.toLowerCase());
        			if (Debug) {
        				System.out.println("Added destination " + destination + " to employee " + employeeName);
        			}
    			}
    			catch (IllegalArgumentException e) {
    				if (Debug) {
    					System.out.println(e.toString() + " while loading destination " + destination + " to employee " + employeeName);
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    	if (Debug) {
    		InteractiveDBTester.printDatabase(empDb);
    	}
    	
        Scanner stdin = new Scanner(System.in);  // for reading console input
        // You do not need to modify that part of the code.
        // When we test your code we will only input legal commands in the appropriate format.
        printOptions();
        boolean done = false;
        while (!done) {
            System.out.print("Enter option ( dfhisqr ): ");
            String input = stdin.nextLine();
            input = input.toLowerCase();  // convert input to lower case

            // only do something if the user enters at least one character
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                if (input.length() > 1) {
                    // trim off any leading or trailing spaces
                    remainder = input.substring(1).trim();//.toLowerCase(); 
                }

                switch (choice) {
                
	                case 'd':
	                	// If destination is not in the database, display "destination not found".
	            		if (Debug) {
	            			System.out.println("Trying to remove destination " + remainder);
	            		}
	            		// Otherwise, discontinue destination (i.e., remove the destination from all the wish lists in which it appears) and display "destination discontinued".
	            		if (empDb.removeDestination(remainder)) {
	            			System.out.println("destination discontinued");
	            			if (Debug) {
	            				InteractiveDBTester.printDatabase(empDb);
	            			}
	            		}
	            		else {
	            			System.out.println("destination not found");
	            		}
	                	break;
	
	
	                case 'f': {
	                	// If employee is not in the database, display "employee not found".
	                	// Otherwise, find employee and display the employee (on one line) in the format:
	                	// employee:destination1,destination2,destination3
	                    Iterator<Employee> empIter = empDb.iterator(); // iterate over all employees in db
	                    Boolean boolEmpFnd = false;
	                    while (empIter.hasNext()) {
	                    	Employee tempEmp = empIter.next();
	                    	String strEmpUserName = tempEmp.getUsername();
	                    	if (strEmpUserName.equals(remainder)) { // username matches request
	                    		boolEmpFnd = true;
		                    	if (Debug) {
		                    		System.out.println("Found user " + strEmpUserName);
		                    	}
	                    		String strReturn = strEmpUserName + ":";
	                    		ArrayList<String> wishList = (ArrayList<String>) tempEmp.getWishlist();
	                    		Iterator<String> wlIterator = wishList.iterator();
	                    		while (wlIterator.hasNext()) {
	                    			strReturn += wlIterator.next();
	                    			if (wlIterator.hasNext()) {
	                    				strReturn += ",";
	                    			}
	                    		}
	                    		System.out.println(strReturn);
	                    	}
	                    }
                    	if (!boolEmpFnd) {
                    		System.out.println("employee not found");
	                    }
	                    
	                }
                    break;
	
	                case 'h':
	                	// Provide help by displaying the list of command options. This command has already been implemented for you.
	                    printOptions();
	                    break;
	
	                case 'i': {
	                	// TODO: constants?
	                	int numEmp = empDb.size();
	                	int empCount = 0;
	                	int numDest = 0;
	                	int maxDestPerEmp = 0;
	                	int minDestPerEmp = 0;
	                	float avgDestPerEmp = 0;
	                	int maxEmpPerDest = 0;
	                	int minEmpPerDest = 0;
	                	float avgEmpPerDest = 0;
	                	DecimalFormat oneD = new DecimalFormat("###.#");
	                	
	                	Iterator<Employee> empIter = empDb.iterator();
	                	while (empIter.hasNext()) {
	                		Employee tempEmp = empIter.next();
	                		ArrayList<String> destList = (ArrayList<String>) empDb.getDestinations(tempEmp.getUsername());
	                		int curListSize = destList.size();
	                		empCount++;
	                		numDest += curListSize;
	                		if (maxDestPerEmp == 0) {
	                			maxDestPerEmp = curListSize;
	                		}
	                		else {
	                			maxDestPerEmp = Math.max(maxDestPerEmp, curListSize);
	                		}
	                		if (minDestPerEmp == 0) {
	                			minDestPerEmp = curListSize;
	                		}
	                		else {
	                			minDestPerEmp = Math.min(minDestPerEmp, curListSize);
	                		}
	                		if (curListSize >= 0) {
	                			avgEmpPerDest = InteractiveDBTester.movingAverage(1/curListSize, avgEmpPerDest, empCount);
	                		}
	                		avgDestPerEmp = InteractiveDBTester.movingAverage(curListSize, avgDestPerEmp, empCount);
	                	}
	                	
	                	// TODO: complete stats
	                	String dests = "";
	                	int intPopDestCount = 0;
	                	// output
	                	System.out.println("Employees: " + Integer.toString(numEmp) + ", Destinations: " + Integer.toString(numDest));
	                	System.out.println("# of destinations/employee: most " + Integer.toString(maxDestPerEmp) + ", least " + Integer.toString(minDestPerEmp) + ", average " + oneD.format(avgDestPerEmp));
	                	System.out.println("# of employees/destination: most " + Integer.toString(maxEmpPerDest) + ", least " + Integer.toString(minEmpPerDest) + ", average " + oneD.format(avgEmpPerDest));
	                	System.out.println("Most popular destination: " + dests + " [" + Integer.toString(intPopDestCount) + "]");
	                    break;
	                }
	                    
	                case 's':
	                	// 	If destination is not in the database, display "destination not found".
	                	// Otherwise, search for destination and display the destination along with the employees who have that destination in their wish list (on one line) in the format:
	                	// destination:employee1,employee2,employee3
	                	if (!empDb.containsDestination(remainder)) {
	                		System.out.println("destination not found");
	                		break;
	                	}
	                	else {
	                		String strEmplDests = remainder + ":";
	                		ArrayList<String> listEmployees = (ArrayList<String>) empDb.getEmployees(remainder);
	                		Iterator<String> strIter = listEmployees.iterator();
	                		while (strIter.hasNext()) {
	                			strEmplDests += strIter.next();
	                			if (strIter.hasNext()) {
	                				strEmplDests += ",";
	                			}
	                		}
	                		System.out.println(strEmplDests);
	                	}
	                    break;
	
	                case 'q':
	                    done = true;
	                    System.out.println("quit");
	                    break;
	
	                case 'r':
	                	// If employee is not in the database, display "employee not found".
	                	// Otherwise, remove employee and display "employee removed".
	                    if (empDb.removeEmployee(remainder)) {
	                    	System.out.println("employee removed");
	                    	if (Debug) {
	                    		InteractiveDBTester.printDatabase(empDb);
	                    	}
	                    }
	                    else {
	                    	System.out.println("employee not found");
	                    }
	                    break;
	
	                default:  // ignore any unknown commands
	                	break;
                }
            }
        }
        
        stdin.close();
    }

    /**
     * Prints the list of command options along with a short description of
     * one.  This method should not be modified.
     */
    private static void printOptions() {
         System.out.println("d <destination> - discontinue the given <destination>");
         System.out.println("f <Employee> - find the given <Employee>");
         System.out.println("h - display this help menu");
         System.out.println("i - display information about this Employee database");
         System.out.println("s <destination> - search for the given <destination>");
         System.out.println("q - quit");
         System.out.println("r <Employee> - remove the given <Employee>");
    }
    
    /**
     * Prints info about input employee database.
     * @param ed Employee database for which to print info.
     */
    private static void printDatabase(EmployeeDatabase ed) {
		System.out.println("Database contents:");
		Iterator<Employee> empCheckIter = ed.iterator();
		while (empCheckIter.hasNext()) {
			Employee e = empCheckIter.next();
			ArrayList<String> empDests = (ArrayList<String>) e.getWishlist();
			Iterator<String> empDestIter = empDests.iterator();
			String destStr = "";
			if (!empDestIter.hasNext()) {
				System.out.println("No destinations for employee " + e.getUsername());
				continue;
			}
			while (empDestIter.hasNext()) {
				destStr += empDestIter.next();
				if (empDestIter.hasNext()) {
					destStr +=",";
				}
			}
		System.out.println(e.getUsername() + "," + destStr);
		}
	}
    
    /**
     * 
     * @param newVal New data point to add.
     * @param curAvg Previous average of data point values.
     * @param curCount Previous number of data points.
     * @return Returns the moving average based on inputs.
     * @throws IllegalArgumentException
     */
    private static float movingAverage(float newVal, float curAvg, int curCount) throws IllegalArgumentException {
    	if (curCount == 0) {
    		return newVal;
    	}
    	if (curCount <  0) {
    		throw new IllegalArgumentException();
    	}
    	float newAvg = ((curAvg * curCount) + newVal)/(curCount + 1);
    	System.out.println(Float.toString(curAvg) + " " + Integer.toString(curCount) + " " + Float.toString(newVal) + " " + Float.toString(newAvg));
    	return newAvg;
    }
}