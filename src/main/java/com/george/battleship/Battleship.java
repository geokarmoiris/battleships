package main.java.com.george.battleship;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Battleship {
	//TODOs:
	// 1. Add Logger and put ship coordinates and coords after ship has been moved to DEBUG mode
	// 2. Show remaining tries
	// 3. Check for non negative user input
	// 4. pass grid size as argument
	public static void main(String[] args) {
		Grid grid = new Grid(5);
		grid.createShips(4);
		//grid.nonFreeGridPoints.forEach((it) -> System.out.println(it.toString()));
		grid.ships.get(0).ship_coords.forEach((it) -> System.out.println(it.toString()));
		grid.ships.get(1).ship_coords.forEach((it) -> System.out.print(it.toString()));
		System.out.println("");
		grid.ships.get(2).ship_coords.forEach((it) -> System.out.print(it.toString()));
		System.out.println("");
		breakLoop:
			while (grid.maxSteps > 0) {
				Scanner xCoordReader = new Scanner(System.in);
				System.out.println("Enter x coordinate: ");
				int x = xCoordReader.nextInt(); // throws an exception if the input isn't integer
				
				Scanner yCoordReader = new Scanner(System.in);
				System.out.println("Enter y coordinate: ");
				int y = yCoordReader.nextInt();
				
				grid.acceptChoice(x, y);
				System.out.println(grid.toString());
				if (areYouMaster(grid.ships)) {
					System.out.println("You, Master of the Sea!");
					break breakLoop;
				}
			}
		System.out.println("Game Over! " + grid.score);
	}
	
	private static boolean areYouMaster(List<Ship> ships) {
		boolean master = true;
		breakLoop:
			for (Ship ship : ships) {
				if (! ship.isSunk) {
					master = false;
					break breakLoop;
				} 
			}
		return master;
	}

}
