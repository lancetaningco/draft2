package project2;

import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DiseaseSpread {
	private double sqrt;
	private int steps;
	
	//Default constructor
	public DiseaseSpread() {
		this.sqrt = 0.0;
		this.steps = 0;
	}
	
	//Non-default constructor
	public DiseaseSpread(double sqrt, int steps, double infection, double recovery) {
		super();
		this.sqrt = sqrt;
		this.steps = steps;
	}
	
	//Getters and setters
	public double getSqrt() {
		return sqrt;
	}
	public void setSqrt(double sqrt) {
		this.sqrt = sqrt;
	}

	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}

	//Method to get the square root of the number of people entered and check if it's a perfect square
	public double getN(Scanner scnr) {
		while(true) {										//While loop continues to prompt the user to enter a valid number
			try {											//Loop breaks if the input is a perfect square and return the square root
				double numPeople = scnr.nextDouble();
				this.sqrt = Math.sqrt(numPeople);
				
				if(this.sqrt != Math.floor(this.sqrt)) {	//Check if the number entered is a perfect square
					System.out.println("Number of individuals entered is not a perfect square. Try again.");
				} else {
					return this.sqrt;
				}
			} catch(InputMismatchException e) {				//Exception handling if the input is not a valid number
				System.out.println("Invalid Input. Enter a valid number.");
				scnr.nextLine();
			}
		}
	}
	
	//Method to print to count susceptible, infected, and recovered individuals
	public static void countSpread(String[][] diseaseGrid, int num) {
    	int countS = 0;
        int countI = 0;
        int countR = 0;
        
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                switch(diseaseGrid[i][j]) {
                	case "S":
                		countS += 1;
                		break;
                	case "I":
                		countI += 1;
                		break;
                	case "R":
                		countR +=1;
                		break;
                }
            }
        }
        
        System.out.print("Susceptible individuals: " + countS + ", Infected individuals: "
        				+ countI + ", Recovered individuals: " + countR + ", Ratio of infection: " 
        				+ (countI / Math.pow(num, 2)) + "\n");
        System.out.println();
        
        countS = 0;
        countI = 0;
        countR = 0;
    }
	
	//Method to print out the grid
	public static void printGrid(int num, String[][] diseaseGrid) {
		for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                System.out.print(diseaseGrid[i][j] + " ");
            }
            System.out.println();
        }
	}
	
	//Method to create the two-dimensional grid and initialize all elements in the grid
    public String[][] createGrid(int num) {
    	SecureRandom scrRnd = new SecureRandom();
        String[][] newGrid = new String[num][num];		//Creates the grid using N for rows and columns

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                newGrid[i][j] = "S";					//Instantiate all elements in the grid to hold the value "S"
            }
        }

        int infectedRow = scrRnd.nextInt(num);			//Choose a random row and column to select patient zero
        int infectedColumn = scrRnd.nextInt(num);
        newGrid[infectedRow][infectedColumn] = "I";
        
        System.out.println("Patient zero initialized:");		//Print out the initial grid
        printGrid(num, newGrid);
        
        countSpread(newGrid, num);
        
        return newGrid;
    }
    
	//Method to get the number of time steps that simulation will be run
	public int getTimeSteps(Scanner scnr) {
		while (true) {		//While loop continues to prompt the user to enter a valid number
			try {			//Loop breaks if the input passes the exception handling
				this.steps = scnr.nextInt();
				return this.steps;
			} catch (InputMismatchException e) {			//Exception handling if the input is not a valid number
				System.out.println("Invalid Input. Enter a valid integer number.");
				scnr.nextLine();
			}
		}
	}
	
	//Method to get infection rate, from 0-0.25; will be multiplied by the number of infected individuals surrounding an element
	public double getInfectionRate(Scanner scnr) {
		double infectionRate = 0.0;
		
		do {
			try {
				infectionRate = scnr.nextDouble();
				if(infectionRate < 0.0 || infectionRate > 0.25) {
					System.out.println("Number out of range. Try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input. Enter a valid number.");
				scnr.nextLine();
			}
		} while(infectionRate < 0.0 || infectionRate > 0.25);
		
		return infectionRate;
	}
	
	//Method to get recovery rate
	public double getRecoveryRate(Scanner scnr) {
		double recoveryRate = 0.0;
		
		do {
			try {
				recoveryRate = scnr.nextDouble();
				if(recoveryRate < 0.0 || recoveryRate > 1.0) {
					System.out.println("Number out of range. Try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input. Enter a valid number.");
				scnr.nextLine();
			}
		} while(recoveryRate < 0.0 || recoveryRate > 1.0);
		
		return recoveryRate;
	}
	
	//Method to generate a rate chance for infection and recovery
	public static double rateChance() {
		SecureRandom scrRnd = new SecureRandom();
		return scrRnd.nextDouble();
	}
	
	//Method that begins the simulation of the disease spread from patient zero
	public void runSimulation(int num, String[][] diseaseGrid, int timeSteps, double infection, double recovery) {
	    for(int t = 1; t <= timeSteps; t++) {			//Time steps the algorithm will run for
	    	System.out.println("Time step: " + t);
	    	
	    	for (int i = 0; i < num; i++) {				//Going through each row
	            for (int j = 0; j < num; j++) {			//Going through each column in a row
	                switch(diseaseGrid[i][j]) {
	                case "S":							//If element is susceptible, checks to see if they'll become infected
	                	int count = 0;
	                	
	                	//Left neighbor
	                	if(((i - 1) >= 0) && (diseaseGrid[i-1][j].equals("I"))) {
	                		count += 1;
	                	}
	                	
	                	//Right neighbor
	                	if(((i + 1) < num) && (diseaseGrid[i+1][j].equals("I"))) {
	                		count += 1;
	                	}
	                	
	                	//Above neighbor
	                	if(((j - 1) >= 0) && (diseaseGrid[i][j-1].equals("I"))) {
	                		count += 1;
	                	}
	                	
	                	//Below neighbor
	                	if(((j + 1) < num) && (diseaseGrid[i][j+1].equals("I"))) {
	                		count += 1;
	                	}
	                	
	                	if(rateChance() < (infection * count)) {		//Counts the neighbors that are infected
	                		diseaseGrid[i][j] = "I";					//and use that to create the probability of infection
	                	}
	                	break;
	                case "I":
	                	if(rateChance() < recovery) {					//Checks probability of recovery for an infected individual
	                		diseaseGrid[i][j] = "R";
	                	}
	                	break;
	                case "R":
	                	break;											//Nothing happens to a recovered person
	                }
	            }
	        }
	    	
	    	printGrid(num, diseaseGrid);
	    	countSpread(diseaseGrid, num);
	    }
	}
	
}

