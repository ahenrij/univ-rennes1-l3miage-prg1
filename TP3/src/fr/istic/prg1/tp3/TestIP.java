package fr.istic.prg1.tp3;

/**
 * 
 * @author Henri Aïdasso, Lounys Bernard <ahenrij@gmail.com;lounys.bernard@gmail.com>
 * @since 2019-10-09
 * Classe de Test de InsertionPair
 * 
 */

import java.util.Scanner;

public class TestIP {
	
	public static void main(String[] args) {
		
		InsertionPair i = new InsertionPair();
		
		Scanner sc = new Scanner(System.in);
		i.createArray(sc);
		System.out.println(i);
	}
}
