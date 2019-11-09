import java.util.Scanner;

public class Boules {
	
	static int nombreBoules = 0; 

	public static void main(String[] args) {
		
		test();
	}
	
	public static void test() {
		
		Scanner sc = new Scanner(System.in);
		
		while(nombreBoules <= 0) {
			Ecriture.ecrireString("Entrez la taille du tableau : ");
			nombreBoules = sc.nextInt();
		}
		
		char[] tableauBoules;
		
		Ecriture.ecrireString("Suite des " + nombreBoules + " boules : ");
		
		tableauBoules = lireTableauBoules();
		
		int r = 0, s = 0, t = nombreBoules - 1;
		
		while(s <= t) {
			switch (tableauBoules[s]) {
			
				case 'v': 
					echange(r, s, tableauBoules);
					++r; 
					++s;
					break;
				case 'b': 
					++s;
					break;
				case 'r': 
					
					echange(s, t, tableauBoules);
					--t;
					
					break;
				default :
					Ecriture.ecrireString("Erreur : s = " + s + ", boule = " + tableauBoules[s]);
					System.exit(0);
			}
			photo(r, s, t, tableauBoules);
		}
		
		Ecriture.ecrireString("résultat du tri : ");
		ecrireTableauBoules(tableauBoules);
		Ecriture.ecrireStringln("");
	}

	public static char[] lireTableauBoules() {
		
		char[] tab = new char[nombreBoules];
		
		int i=0;
		
		while( i < nombreBoules) {
			
			char c  = Lecture.lireChar();
			
			if (c != '\n') {
				tab[i] = c;
				++i;	
			}
		}
		
		return tab;
	}
	
	public static void ecrireTableauBoules(char[] tab) {
		/*
		 *  effet : Le contenu de tab est affiché à l'écran
		 */
		
		for (int i=0; i < nombreBoules; i++) {
			Ecriture.ecrireChar(tab[i]);
		}
	}
	
	public static void echange(int i, int j, char[] tab) {
		char c = tab[i];
		tab[i] = tab[j];
		tab[j] = c;
	}
	
	public static void photo(int r, int s, int t, char[] tab)  {
		
		Ecriture.ecrireStringln("r  = " + r + " s = " + s + " t = " + t);
		ecrireTableauBoules(tab);
		Ecriture.ecrireStringln("");
	}
}
