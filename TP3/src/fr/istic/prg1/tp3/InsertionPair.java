package fr.istic.prg1.tp3;

/**
 * 
 * @author Henri Aïdasso, Lounys Bernard <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @version 1.0
 * @since 2019-10-01
 * 
 *        Classe pour insérer des paires
 */

import java.util.Scanner;

public class InsertionPair {

	private static final int SIZE_MAX = 10;

	/**
	 * nombre de pairs présents dans t, 0<= size <= SIZE_MAX
	 */
	private int size;
	private Pair[] array = new Pair[SIZE_MAX];

	public InsertionPair() {

		size = 0;
	}

	/**
	 * 
	 */
	public Pair[] toArray() {

		Pair[] tab = new Pair[size];
		for (int i = 0; i < size; i++) {
			tab[i] = array[i];
		}

		return tab;
	}

	/**
	 * @pre value != null
	 * @param value
	 * @return true si value est inséré, false sinon
	 */
	public boolean insert(Pair value) {

		// La valeur n'est pas -1 et le tableau n'est pas plein
		if ((value.getX() < 0) || (value.getY() < 0) || size == SIZE_MAX) {
			return false;
		}

		// On avance jusqu'au nombre >= value
		int i = 0;
		while (i < size && (array[i].compareTo(value) == -1)) {
			i++;
		}

		if (array[i] != null && array[i].equals(value)) {
			return false;
		}

		size++;

		// on decale tous les nombres > value d'un indice
		for (int j = size - 1; j > i; j--) {
			array[j] = array[j - 1];
		}

		array[i] = value;

		return true;
	}

	/**
	 * @pre scanner != null
	 * @param scanner
	 */
	public void createArray(Scanner scanner) {

		int last = 0, value = 0, i = 0;

		while (value >= 0) {

			last = value;
			value = scanner.nextInt();

			i++;
			if (i % 2 == 0) {
				insert(new Pair(last, value));
			}
		}
	}

	@Override
	public String toString() {

		String chaine = "";
		for (int i = 0; i < size; i++) {
			chaine += array[i] + "";
		}

		return chaine;
	}
}