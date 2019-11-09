package fr.istic.prg1.list;

import fr.istic.prg1.list_util.SuperT;
import fr.istic.prg1.list_util.Iterator;

/**
 * @author Henri Aïdasso, Lounys Bernard <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @version 1.0
 * @since 2019-10-26
 * 
 * Classe pour manipuler une liste d'éléments de type T
 * 
 * (Représentation en double chainage par références)
 */

public class List <T extends SuperT> {
	
	private Element flag;
	
	public List() {
		flag = new Element();
		flag.left = flag;
		flag.right = flag;
	}
	
	/**
	 * Delete all values from the list
	 */
	public void clear() {
		flag.left = flag;
		flag.right = flag;
	}

	/**
	 * Set flag's value to v
	 * @param v
	 */
	public void setFlag(T v) {
		flag.value = v;
	}
	
	/**
	 * Add an element with value v to the list's head
	 * @param v
	 */
	public void addHead(T v) {
		Iterator<T> it  = iterator();
		it.addLeft(v);
	}
	
	/**
	 * Add an element with value v to the list's tail
	 * @param v
	 */
	public void addTail(T v) {
		Iterator<T> it  = iterator();
		it.goBackward();
		it.addLeft(v);
	}
	
	/**
	 * Current element pointing on the head value
	 * @return a brand new instance of ListIterator's class
	 */
	public Iterator<T> iterator() {
		return new ListIterator();
	}
	
	public boolean isEmpty() {
		return flag.right == flag;
	}
	
	
	private class Element {
		
		public T value;
		public Element left, right;
		public Element() {
			value = null;
			left = null;
			right = null;
		}
	}
	
	public class ListIterator implements Iterator <T> {
		
		private Element current;
		
		protected ListIterator() {	
			current = flag.right;
		}

		@Override
		public void goForward() {
			if (current.right == null) {
				throw new NullPointerException("Impossible d'avancer, voisin droit inexsitant");
			}
			current = current.right;
		}

		@Override
		public void goBackward() {
			if (current.left == null) {
				throw new NullPointerException("Impossible de reculer, voisin gauche inexsitant");
			}
			current  = current.left;
		}

		@Override
		public void restart() {
			current = flag.right;
		}

		@Override
		public boolean isOnFlag() {
			return current == flag;
		}

		@Override
		public void remove() {
			if (this.isOnFlag()) {
				throw new IllegalArgumentException("Impossible de supprimer le drapeau");
			} else {
				current.left.right = current.right;
				current.right.left = current.left;
				current = current.right;
			}
		}

		@Override
		public T getValue() {
			
			if (current == null) {
				throw new NullPointerException("Impossible de récuperer la valeur , element courant inexistant");
			}
			return current.value;
		}

		@Override
		public T nextValue() {
			this.goForward();
			return this.getValue();
		}

		@Override
		public void addLeft(T value) {
			Element el = new Element();
			Element leftNeighbor = current.left;
			
			el.value = value;
			el.left = leftNeighbor;
			el.right = current;
			
			leftNeighbor.right = el;
			current.left = el;
			current = el;
		}

		@Override
		public void addRight(T value) {
			goForward();
			addLeft(value);
		}

		@Override
		public void setValue(T value) {
			current.value = value;
		}
	}
}
