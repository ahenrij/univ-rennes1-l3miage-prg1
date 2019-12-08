package fr.istic.prg1.list;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import fr.istic.prg1.list_util.Comparison;
import fr.istic.prg1.list_util.SmallSet;
import fr.istic.prg1.list_util.Iterator;
//import fr.istic.prg1.list_util.List;

/**
 * @author Henri Aïdasso, Lounys Bernard <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @version 1.0
 * @since 2019-10-26
 * 
 * Classe représentant un ensemble de nombres compris entre 0 et 32767
 */

public class MySet extends List<SubSet> {

	/**
	 * Borne supérieure pour les rangs des sous-ensembles.
	 */
	private static final int MAX_RANG = 128;
	
	/**
	 * Sous-ensemble de rang maximal à mettre dans le drapeau de la liste.
	 */
	private static final SubSet FLAG_VALUE = new SubSet(MAX_RANG, new SmallSet());
	
	/**
	 * Entrée standard.
	 */
	private static final Scanner standardInput = new Scanner(System.in);

	public MySet() {
		super();
		setFlag(FLAG_VALUE);
	}

	/**
	 * Fermer tout (actuellement juste l'entrée standard).
	 */
	public static void closeAll() {
		standardInput.close();
	}

	private static Comparison compare(int a, int b) {
		if (a < b) {
			return Comparison.INF;
		} else if (a == b) {
			return Comparison.EGAL;
		} else {
			return Comparison.SUP;
		}
	}

	/**
	 * Afficher à l'écran les entiers appartenant à this, dix entiers par ligne
	 * d'écran.
	 */
	public void print() {
		System.out.println(" [version corrigee de contenu]");
		this.print(System.out);
	}

	// //////////////////////////////////////////////////////////////////////////////
	// //////////// Appartenance, Ajout, Suppression, Cardinal
	// ////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * Ajouter à this toutes les valeurs saisies par l'utilisateur et afficher
	 * le nouveau contenu (arrêt par lecture de -1).
	 */
	public void add() {
		System.out.println(" valeurs a ajouter (-1 pour finir) : ");
		this.add(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}

	/**
	 * Ajouter à this toutes les valeurs prises dans is.
	 * C'est une fonction auxiliaire pour add() et restore().
	 * 
	 * @param is flux d'entrée.
	 */
	public void add(InputStream is) {

		Scanner sc = (is == System.in) ? standardInput : new Scanner(is);
		int value;

		while ((value = readValue(sc, -1)) != -1) {
			this.addNumber(value);
		}
		
		if (sc != standardInput) sc.close();
	}

	/**
	 * Ajouter value à this.
	 * 
	 * @param value valeur à ajouter.
	 */
	public void addNumber(int value) {

		int rank = value / 256;

		if (rank >= 0 && rank < MAX_RANG) { // Thanks to readValue, not needed normally

			Iterator<SubSet> it = this.iterator();

			while (it.getValue().rank < rank) {
				it.goForward();
			}

			if (it.getValue().rank == rank) {
				it.getValue().set.add(value % 256);
			} else {
				SmallSet set = new SmallSet();
				set.add(value % 256);
				it.addLeft(new SubSet(rank, set));
			}
		}
	}

	/**
	 * Supprimer de this toutes les valeurs saisies par l'utilisateur et afficher le
	 * nouveau contenu (arrêt par lecture de -1).
	 */
	public void remove() {
		System.out.println("  valeurs a supprimer (-1 pour finir) : ");
		this.remove(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}

	/**
	 * Supprimer de this toutes les valeurs prises dans is.
	 * 
	 * @param is flux d'entrée
	 */
	public void remove(InputStream is) {

		Scanner sc = (is == System.in) ? standardInput : new Scanner(is);
		int value;

		while ((value = readValue(sc, -1)) != -1) {
			this.removeNumber(value);
		}
		
		if (sc != standardInput) sc.close();
	}

	/**
	 * Supprimer value de this.
	 * 
	 * @param value valeur à supprimer
	 */
	public void removeNumber(int value) {

		int rank = value / 256;

		if (rank >= 0 && rank < MAX_RANG) { // Not needed anymore

			Iterator<SubSet> it = this.iterator();

			while (it.getValue().rank < rank) {
				it.goForward();
			}

			if ((it.getValue().rank == rank) && (it.getValue().set.contains(value % 256))) {
				it.getValue().set.remove(value % 256);
				if (it.getValue().set.isEmpty()) {
					it.remove();
				}
			}
		}
	}

	/**
	 * @return taille de l'ensemble this
	 */
	public int size() {

		int size = 0;
		Iterator<SubSet> it = this.iterator();

		while (!it.isOnFlag()) {
			size += it.getValue().set.size();
			it.goForward();
		}

		return size;
	}

	/**
	 * @return true si le nombre saisi par l'utilisateur appartient à this, false
	 *         sinon
	 */
	public boolean contains() {
		System.out.println(" valeur cherchee : ");
		int value = readValue(standardInput, 0);
		return this.contains(value);
	}

	/**
	 * @param value valeur à tester
	 * @return true si valeur appartient à l'ensemble, false sinon
	 */
	public boolean contains(int value) {

		int rank = value / 256;
		if (rank < 0 || rank >= MAX_RANG) { // Not needed anymore normally
			return false;
		}

		Iterator<SubSet> it = this.iterator();
		while (it.getValue().rank < rank) {
			it.goForward();
		}

		return (it.getValue().rank == rank) && (it.getValue().set.contains(value % 256));
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////// Difference, DifferenceSymetrique, Intersection, Union ///////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * This devient la différence de this et set2.
	 * 
	 * @param set2 deuxième ensemble
	 */
	public void difference(MySet set2) {

		if (this == set2) {

			this.clear();
		} else {

			Iterator<SubSet> it1 = this.iterator();
			Iterator<SubSet> it2 = set2.iterator();

			while (!it1.isOnFlag()) {

				switch (compare(it2.getValue().rank, it1.getValue().rank)) {

				case INF:
					it2.goForward();
					break;
				case EGAL:
					it1.getValue().set.difference(it2.getValue().set);
					if (it1.getValue().set.isEmpty()) {
						it1.remove();
					} else {
						it1.goForward();
					}
					it2.goForward();
					break;
				case SUP:
					it1.goForward();
				}
			}
		}
	}

	/**
	 * This devient la différence symétrique de this et set2.
	 * 
	 * @param set2 deuxième ensemble
	 */
	public void symmetricDifference(MySet set2) {

		if (this == set2) {

			this.clear();
		} else {

			Iterator<SubSet> it1 = this.iterator();
			Iterator<SubSet> it2 = set2.iterator();

			while (!it2.isOnFlag()) {

				switch (compare(it2.getValue().rank, it1.getValue().rank)) {

				case INF:
					it1.addLeft(it2.getValue().clone());
					it2.goForward();
					break;
				case EGAL:
					it1.getValue().set.symmetricDifference(it2.getValue().set);
					if (it1.getValue().set.isEmpty()) {
						it1.remove();
					} else {
						it1.goForward();
					}
					it2.goForward();
					break;
				case SUP:
					it1.goForward();
				}
			}
		}
	}

	/**
	 * This devient l'intersection de this et set2.
	 * 
	 * @param set2 deuxième ensemble
	 */
	public void intersection(MySet set2) {

		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		while (!it1.isOnFlag()) {

			switch (compare(it2.getValue().rank, it1.getValue().rank)) {

			case INF:
				it2.goForward();
				break;
			case EGAL:
				it1.getValue().set.intersection(it2.getValue().set);
				if (it1.getValue().set.isEmpty()) {
					it1.remove();
				} else {
					it1.goForward();
				}
				break;
			case SUP:
				it1.remove();
			}
		}
	}

	/**
	 * This devient l'union de this et set2.
	 * 
	 * @param set2 deuxième ensemble
	 */
	public void union(MySet set2) {

		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		while (!it1.isOnFlag() || !it2.isOnFlag()) {

			switch (compare(it2.getValue().rank, it1.getValue().rank)) {

			case INF:
				it1.addLeft(it2.getValue().clone());
				it2.goForward();
				break;
			case EGAL:
				it1.getValue().set.union(it2.getValue().set.clone());
				it1.goForward();
				it2.goForward();
				break;
			case SUP:
				if (!it1.isOnFlag()) {
					it1.goForward();
				} else {
					it1.addLeft(it2.getValue().clone());
					it1.goForward();
					it2.goForward();
				}
			}
		}
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////// Egalité, Inclusion ////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * @param o deuxième ensemble
	 * 
	 * @return true si les ensembles this et o sont égaux, false sinon
	 */
	@Override
	public boolean equals(Object o) {
		boolean b = true;
		if (this == o) {
			b = true;
		} else if (o == null) {
			b = false;
		} else if (!(o instanceof MySet)) {
			b = false;
		} else {

			MySet set2 = (MySet) o;

			Iterator<SubSet> it1 = this.iterator();
			Iterator<SubSet> it2 = set2.iterator();

			while (!it1.isOnFlag() && b) {

				if (it1.getValue().rank == it2.getValue().rank) {

					b = it1.getValue().set.equals(it2.getValue().set);
					it1.goForward();
					it2.goForward();
				} else {

					b = false;
				}
			}

			b = b && it2.isOnFlag();

		}
		return b;
	}

	/**
	 * @param set2 deuxième ensemble
	 * @return true si this est inclus dans set2, false sinon
	 */
	public boolean isIncludedIn(MySet set2) {

		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();

		while (!it1.isOnFlag()) {

			switch (compare(it1.getValue().rank, it2.getValue().rank)) {

			case INF:
				return false;
			case EGAL:
				if (!it1.getValue().set.isIncludedIn(it2.getValue().set)) {
					return false;
				}
				it1.goForward();
				it2.goForward();
				break;
			case SUP:
				it2.goForward();
			}
		}

		return true;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// //////// Rangs, Restauration, Sauvegarde, Affichage //////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * Afficher les rangs présents dans this.
	 */
	public void printRanks() {
		System.out.println(" [version corrigee de rangs]");
		this.printRanksAux();
	}

	private void printRanksAux() {
		int count = 0;
		System.out.println(" Rangs presents :");
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			System.out.print("" + it.getValue().rank + "  ");
			count = count + 1;
			if (count == 10) {
				System.out.println();
				count = 0;
			}
			it.goForward();
		}
		if (count > 0) {
			System.out.println();
		}
	}

	/**
	 * Créer this à partir d'un fichier choisi par l'utilisateur contenant une
	 * séquence d'entiers positifs terminée par -1 (cf f0.ens, f1.ens, f2.ens,
	 * f3.ens et f4.ens).
	 */
	public void restore() {
		String fileName = readFileName();
		InputStream inFile;
		try {
			inFile = new FileInputStream(fileName);
			System.out.println(" [version corrigee de restauration]");
			this.clear();
			this.add(inFile);
			inFile.close();
			System.out.println(" nouveau contenu :");
			this.printNewState();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("fichier " + fileName + " inexistant");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier " + fileName);
		}
	}

	/**
	 * Sauvegarder this dans un fichier d'entiers positifs terminé par -1.
	 */
	public void save() {
		System.out.println(" [version corrigee de sauvegarde]");
		OutputStream outFile;
		try {
			outFile = new FileOutputStream(readFileName());
			this.print(outFile);
			outFile.write("-1\n".getBytes());
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("pb ouverture fichier lors de la sauvegarde");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier");
		}
	}

	/**
	 * @return l'ensemble this sous forme de chaîne de caractères.
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int count = 0;
		SubSet subSet;
		int startValue;
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			subSet = it.getValue();
			startValue = subSet.rank * 256;
			for (int i = 0; i < 256; ++i) {
				if (subSet.set.contains(i)) {
					String number = String.valueOf(startValue + i);
					int numberLength = number.length();
					for (int j = 6; j > numberLength; --j) {
						number += " ";
					}
					result.append(number);
					++count;
					if (count == 10) {
						result.append("\n");
						count = 0;
					}
				}
			}
			it.goForward();
		}
		if (count > 0) {
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * Imprimer this dans outFile.
	 * 
	 * @param outFile flux de sortie
	 */
	private void print(OutputStream outFile) {
		try {
			String string = this.toString();
			outFile.write(string.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Afficher l'ensemble avec sa taille et les rangs présents.
	 */
	private void printNewState() {
		this.print(System.out);
		System.out.println(" Nombre d'elements : " + this.size());
		this.printRanksAux();
	}

	/**
	 * @param scanner
	 * @param min     valeur minimale possible
	 * @return l'entier lu au clavier (doit être entre min et 32767)
	 */
	private static int readValue(Scanner scanner, int min) {
		int value = scanner.nextInt();
		while (value < min || value > 32767) {
			System.out.println("valeur incorrecte");
			value = scanner.nextInt();
		}
		return value;
	}

	/**
	 * @return nom de fichier saisi psar l'utilisateur
	 */
	private static String readFileName() {
		System.out.print(" nom du fichier : ");
		String fileName = standardInput.next();
		return fileName;
	}
}
