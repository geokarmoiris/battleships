package main.java.com.george.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ship {
	private Direction direction;
	private int length;
	private int bow_x_coord;
	private int bow_y_coord;
	private int hits;
	private int gridSize;
	protected ShipType type;
	protected boolean isSunk;
	private List<Pair<Integer, Integer>> nonFreePoints;
	private Pair bow_coord;
	List<Pair<Integer, Integer>> ship_coords = new ArrayList<Pair<Integer,Integer>>();
	boolean isValidPosition;
	
	public Ship(Direction dir, int len, int x, int y, int gr, List<Pair<Integer, Integer>> nonFree, ShipType shipType) {
		direction = dir;
		length = len;
		bow_x_coord = x;
		bow_y_coord = y;
		gridSize = gr;
		type = shipType;
		nonFreePoints = nonFree;
		bow_coord = new Pair(bow_x_coord, bow_y_coord);
		isValidPosition = placeShip(x, y);
		hits = 0;
		isSunk = false;
	}
		
	private void getShipCoords() {
		if (length != 0) 
			ship_coords.add(bow_coord);
		switch(direction) {
			case NORTH:
				for (int i = 1; i < length; i++) {
					Pair pair = new Pair(bow_x_coord + i, bow_y_coord);
					ship_coords.add(pair);
				}
				break;
			case SOUTH:
				for (int i = 1; i < length; i++) {
					Pair pair = new Pair(bow_x_coord - i, bow_y_coord);
					ship_coords.add(pair);
				}
				break;
			case EAST:
				for (int i = 1; i < length; i++) {
					Pair pair = new Pair(bow_x_coord, bow_y_coord - i);
					ship_coords.add(pair);
				}
				break;
			case WEST:
				for (int i = 1; i < length; i++) {
					Pair pair = new Pair(bow_x_coord, bow_y_coord + i);
					ship_coords.add(pair);
				}
				break;
		}
	}
	
	private boolean checkIfAllowed(int row, int column) {
		Pair pair = new Pair(row, column);
	    boolean areCorrect = checkIfIndexAllowed(row) && checkIfIndexAllowed(column) && ! arrayListContains(nonFreePoints, pair);
	    return areCorrect;
	}
	
	public boolean arrayListContains(List<Pair<Integer, Integer>> pairList, Pair<Integer, Integer> pair) {
		boolean x = false;
		breakLoop:
			for (Pair<Integer, Integer> p : pairList) {
				x = p.equals(pair);
				if (x) break breakLoop;
			}
		return x;
	}
	
	private boolean checkIfIndexAllowed(int index) {
	    return (index >= 0 && index <= gridSize);
	}
	
	private boolean placeShip(int x, int y) {
		boolean validPosition = false;
		getShipCoords();
		if (! ship_coords.isEmpty()) {
			breakLoop:
				for (Pair<Integer, Integer> pair : ship_coords) {
					if (checkIfAllowed(pair.getL(), pair.getR())) {
						validPosition = true;
					} else {
						validPosition = false;
						break breakLoop;
					}
				}
			return validPosition;
		} else {
			return validPosition;
		}
	}
	
	public void increaseHits(ShipType type) {
		hits++;
		if (hits == length) {
			switch(type) {
				case TUG:
					isSunk = true;
					System.out.println("You just got a smallboat!");
					break;
				case SUBMARINE:
					isSunk = true;
					System.out.println("You just got a submarine!");
					break;
				case AIRCRAFTCARRIER:
					isSunk = true;
					System.out.println("You just got a large ship!");
					break;
			}
		}
	}
	
	public boolean threaten(int x, int y, int gridSize) {
		boolean isNorth = false;
		boolean isSouth = false;
		boolean isEast = false;
		boolean isWest = false;
		
		if (x - 1 >= 0) {
			Pair north = new Pair(x - 1, y);
			isNorth = arrayListContains(ship_coords, north);
		}
		if (x + 1 <= gridSize) {
			Pair south = new Pair(x + 1, y);
			isSouth = arrayListContains(ship_coords, south);
		}
		if (y + 1 <= gridSize) {
			Pair east = new Pair(x, y + 1);
			isEast = arrayListContains(ship_coords, east);
		}
		if (y - 1 >= 0) {
			Pair west = new Pair(x, y - 1);
			isWest = arrayListContains(ship_coords, west);
		}
		
		return (isNorth || isSouth || isEast || isWest);

	}
	
}
