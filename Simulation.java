package project2;

import java.util.Scanner;

public class Simulation {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		
		DiseaseSpread disease = new DiseaseSpread();
		
		System.out.println("Enter a number of individuals that is a perfect square:");
		int N = (int) disease.getN(scnr);	//Cast to integer because the getSqrt() method returns a double
		System.out.println();
		
		//Use N to form a two-dimensional grid and have all elements in the grid initialized
		String[][] grid = disease.createGrid(N);
		
		System.out.println("Enter the number of time steps the model will be ran:");
		int T = disease.getTimeSteps(scnr);
		System.out.println();
		
		//Infection rate rate
		System.out.println("Enter the a number from 0 - 0.25 that will be the infection rate of an individual:");
		double alpha = disease.getInfectionRate(scnr);
		System.out.println();
		
		//Recovery rate
		System.out.println("Enter the a number from 0 - 1 that will be the recovery rate of an individual:");
		double beta = disease.getRecoveryRate(scnr);
		System.out.println();
		
		disease.runSimulation(N, grid, T, alpha, beta);
		
		scnr.close();
	}
}
