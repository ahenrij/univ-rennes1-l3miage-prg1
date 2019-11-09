package fr.istic.prg1.tp3;

/**
 * 
 * @author Henri Aïdasso, Lounys Bernard <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @since 2019-10-09
 * Classe de Test de InsertionInteger
 * 
 */

import java.util.Scanner;

public class TestII {

	public static void main(String[] args) {
		
		InsertionInteger i = new InsertionInteger();
		
		Scanner sc = new Scanner(System.in);
		i.createArray(sc);
		System.out.println(i);
	}
}
