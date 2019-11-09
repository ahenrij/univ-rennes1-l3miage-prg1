package fr.istic.prg1.tp3;

/**
 * 
 * @author Henri Aïdasso, Lounys Bernard <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @version 1.0
 * @since 2019-10-01
 * 
 *        Classe pour l'insertion d'entiers
 */

import java.util.Scanner;

public class InsertionInteger {

	private static final int SIZE_MAX = 10;

	private int size; // nombre d'entiers présents dans t, 0<= size <= SIZE_MAX
	private int[] array = new int[SIZE_MAX];

	public InsertionInteger() {

		size = 0;
	}

	/**
	 * @return tableau contenant les valeurs insérées
	 */
	public int[] toArray() {

		int[] tab = new int[size];
		for (int i = 0; i < size; i++) {
			tab[i] = array[i];	
		}

		return tab;
	}

	/**
	 * @return true si value est inséré, false sinon
	 */
	public boolean insert(int value) {

		// La valeur n'est pas -1 et le tableau n'est pas plein
		if (value < 0 || size == SIZE_MAX) {
			return false;
		}

		// On avance jusqu'au nombre >= value
		int i = 0;
		while (i < size && array[i] < value) {
			i++;	
		}

		if (array[i] == value) {
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

		int value = 0;

		while (value >= 0) {

			value = scanner.nextInt();
			insert(value);
		}
	}

	@Override
	public String toString() {

		String chaine = "";
		for (int i = 0; i < size; i++) {
			chaine += array[i] + " ";	
		}

		return chaine;
	}
}