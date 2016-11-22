package main.java.com.george.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid {
	private char[][] grid;
	private char[][] shipsOngrid;
	private int i,j;
	protected int gridSize;
	protected double maxSteps;
	protected List<Pair<Integer, Integer>> nonFreeGridPoints = new ArrayList<Pair<Integer,Integer>>();
	protected List<Ship> ships = new ArrayList<Ship>();
	private Random random = new Random();
	protected int score;
	
	public Grid(int n) {
		gridSize = n;
		score = 0;
		double base = n;
		maxSteps = Math.round(0.75 * Math.pow(base, 2));
		//maxSteps = 3;
		grid = new char[gridSize][gridSize];
		
		for (int i = 0; i < gridSize; i++)
			for (int j = 0; j < gridSize; j++)
				grid[i][j] = '.';
	}
	
	public String toString() {
		String r = "";
		for (int i = 0; i < gridSize; i++) {
			r = r + "|";
			for (int j = 0; j < gridSize; j++)
				r = r + grid[i][j];
			r = r + "|\n";
		}
		return r;
	}
	
	private void updateShipList(double n, ShipType shipType, boolean move) {
		int length;
		int i = 0;
		boolean validPosition = false;
		
		switch(shipType) {
			case TUG:
				length = 1;
				break;
			case SUBMARINE:
				length = 3;
				break;
			case AIRCRAFTCARRIER:
				length = 5;
				break;
			default:
				length = 0;
				break;
		}

		while (i < n && ! validPosition) {
			Direction randomDirection = Direction.getRandom();
			int randomX = random.nextInt(gridSize);
			int randomY = random.nextInt(gridSize);
			Ship ship = new Ship(randomDirection, length, randomX, randomY, gridSize, nonFreeGridPoints, shipType);
			validPosition = ship.isValidPosition;
			if (validPosition) {
				i++;
				nonFreeGridPoints.addAll(ship.ship_coords);
				ships.add(ship);
				if (move) {
					System.out.println("Moving ship with type " + shipType + " " + "to a new position (" + randomX + "," + randomY + ") and direction " + randomDirection.toChar());
					System.out.print("The new ship coordinates are: " );
					ship.ship_coords.forEach((p) -> System.out.print(p.toString()));
					System.out.println("");
				}
			}
		}
	}
	
	public void createShips(double n) {
		double tugNumber = 0.06 * Math.pow(n, 2);
		double submarineNumber = 0.03 * Math.pow(n, 2);
		double aircraftCarrierNumber = 0.02 * Math.pow(n, 2);
		updateShipList(tugNumber, ShipType.TUG, false);
		updateShipList(submarineNumber, ShipType.SUBMARINE, false);
		updateShipList(aircraftCarrierNumber, ShipType.AIRCRAFTCARRIER, false);
	}
	
	public void acceptChoice(int x, int y) {
		if (grid[x][y] == '.') {
			Pair pair = new Pair(x, y);
			boolean hitShip = false;
			boolean isThreaten = false;
			List<Ship> copyList = new ArrayList<Ship>(ships);
			breakLoop:
				for (Ship s : copyList) {
					hitShip = s.arrayListContains(s.ship_coords, pair);
					isThreaten = s.threaten(x, y, gridSize);
					if (hitShip) {
						s.increaseHits(s.type);
						if (s.isSunk) updateScore(s.type);
						break breakLoop;	
					}
					
					if (isThreaten && (s.type == ShipType.TUG || s.type == ShipType.SUBMARINE)) {
						ships.remove(s);
						for (Pair<Integer, Integer> coord : s.ship_coords) {
							if (s.arrayListContains(nonFreeGridPoints, coord)) {
								nonFreeGridPoints.remove(coord);
							}
						}
						updateShipList(1.0, s.type, true);
					}
				}
			if (hitShip) {
				grid[x][y] = 'B';
			} else {
				grid[x][y] = 'F';
			}
			maxSteps--;
		} else {
			System.out.println("You have already tried this point");
		}
		
	}
	
	private void updateScore(ShipType type) {
		switch(type) {
		case TUG:
			score += 1;
			break;
		case SUBMARINE:
			score += 3;
			break;
		case AIRCRAFTCARRIER:
			score += 5;
			break;
		}
	}
}
