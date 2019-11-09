
import java.util.Scanner;

public class Exo1 {

	public static void main(String[] args) {
		
		int n = 0, a = 0;
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		
		while (n < 1 || n > 50) {
			System.out.print("Entrez la taille du tableau : ");
			n = sc.nextInt();
		}
		
		int[] T = new int[n];
		
		//initialiser le tableau
		remplirTableau(T, sc);
		
		//tri naif du tableau
		triNaif(T);
		afficheTab(T);
		
		System.out.print("Entrez la valeur recherchee : ");
		a = sc.nextInt();
		System.out.println("");
		
		if(rechercheDicho(T, a)) {
			System.out.println(a + " est dans le tableau");
		} else {
			System.out.println(a  + " n'est pas dans le tableau");
		}
		
		
	}
	
	public static void triNaif(int[] T) {
		
		final int n  = T.length;
		
		for (int i=0; i <= n-2; ++i) {
			
			int rangmin = i;
			
			for(int j=i+1; j<= n-1; ++j) {
				if(T[j] < T[rangmin]) {
					rangmin = j;
				}
			}
			int aux = T[i];
			T[i] = T[rangmin];
			T[rangmin] = aux;
		}
	}
	
	public static void remplirTableau(int[] T, Scanner sc) {
		
		for (int i = 0; i < T.length; ++i) {
			System.out.print("Entrez la valeur " + (i+1) + " : ");
			T[i] = sc.nextInt();
		}
	}
	
	public static void afficheTab(int[] T) {
		System.out.println("");
		for (int i=0; i<T.length; ++i) {
			System.out.print(T[i] + " ");
		}
		System.out.println("");
	}

	public static boolean rechercheDicho(int[] T, int a) {
		
		final int n = T.length;
		
		int deb = 0, fin = n-1, milieu = (deb+fin)/2;
		
		while (deb <= fin && a != T[milieu]) {
			if (a < T[milieu]) {
				fin = milieu- 1;
			} else if (a > T[milieu]) {
				deb = milieu + 1;
			}
			milieu = (deb+fin)/2;
		}
		
		return deb <= fin;
	}
}
