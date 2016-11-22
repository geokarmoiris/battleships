package main.java.com.george.battleship;

public enum ShipType {
	TUG('T'),
	SUBMARINE('S'),
	AIRCRAFTCARRIER('A');

	private final char type;

	ShipType(char type) {
		this.type = type;
	}
	
    public char toChar() {
        return this.type;
     }
}
