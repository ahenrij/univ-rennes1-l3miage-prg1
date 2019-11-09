package fr.istic.prg1.tp3;

/**
 * 
 * @author Henri Aïdasso, Lounys Bernard
 *         <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @version 1.0
 * @since 2019-10-01
 * 
 *        Classe de la suite des fourmis
 */

public class Fourmis {

	/**
	 * @param ui un terme de la suite des fourmis
	 * @pre ui.length() > 0
	 * @return le terme suivant de la suite des fourmis
	 */
	public static String next(String ui) {

		int nbOccurrence = 0, pos = 0;
		String nombre = "", next = "";

		while (pos < ui.length()) {

			if (nombre.equals(ui.charAt(pos) + "")) {

				nbOccurrence++;
			} else {

				if (!nombre.isEmpty()) {
					next += nbOccurrence + nombre;	
				}
				nombre = ui.charAt(pos) + "";
				nbOccurrence = 1;
			}
			pos++;
		}

		next += nbOccurrence + nombre;

		return next;
	}

	public static void main(String args[]) {

		String now = "1";

		for (int i = 0; i <= 10; ++i) {

			System.out.println(now);
			now = next(now);
		}
	}
}