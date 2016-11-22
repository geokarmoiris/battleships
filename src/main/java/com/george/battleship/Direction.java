package main.java.com.george.battleship;

public enum Direction {
	NORTH('N'),
	EAST('E'),
	SOUTH('S'),
	WEST('W');

	private final char direction;

	Direction(char direction) {
		this.direction = direction;
	}
	
    public static Direction getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
    
    public char toChar() {
        return this.direction;
     }
}
