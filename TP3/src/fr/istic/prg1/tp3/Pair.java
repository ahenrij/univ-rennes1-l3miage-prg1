package fr.istic.prg1.tp3;

/**
 * 
 * @author Henri Aïdasso, Lounys Bernard <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @version 1.0
 * @since 2019-10-01
 * 
 *        Classe représentant des doublets non modifiables
 */

public class Pair implements Comparable<Pair> {
	
	private int x, y;
	
	public Pair() {}
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	/**
	 * @return 0 si les valeurs sont églales, -1 si this < d et 1 sinon
	 */
	@Override
	public int compareTo(Pair d) {
		
		if(this.equals(d)) return 0;
		
		boolean isLess = (this.getX() < d.getX()) || ((this.getX() == d.getX()) && (this.getY() < d.getY()));
		if(isLess) return -1;
		
		return 1;
	}

	@Override
	public Pair clone() {
	    return new Pair(this.x, this.y);
	}

	@Override
	public String toString() {
	    return this.x + " " + this.y + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Pair)) {
			return false;
		}
		
		Pair p = (Pair) obj;
		
		return (this.getX() == p.getX()) && (this.getY() == p.getY());
	}
}