package main.java.com.george.battleship;

public class Pair<L,R> {
    private L l;
    private R r;
    
    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }
    public L getL() { return l; }
    public R getR() { return r; }
    public void setL(L l) { this.l = l; }
    public void setR(R r) { this.r = r; }
    
    public String toString() { return "(" + l + ", " + r + ")"; }
    
    public boolean equals(Object o) {
      if (!(o instanceof Pair)) return false;
      Pair pairo = (Pair) o;
      return this.l.equals(pairo.getL()) && this.r.equals(pairo.getR());
    }
}
