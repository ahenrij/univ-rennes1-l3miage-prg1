
import java.util.Scanner;

public class Exo2 {

	public static void main(String[] args) {

		int n = -1;

		Scanner sc = new Scanner(System.in);

		/*
		 * while (n < 0) { System.out.print("Entrez un entier positif : "); n =
		 * sc.nextInt(); } //
		 */

		for (int i = 1; i < 101; ++i) {
			if (estMysterieuxFoursov(i)) {
				System.out.println(i + " est un nombre mysterieux");
			} else {
				System.out.println(i + " n'est pas un nombre mysterieux");
			}
		}

		/*
		 * if (estMysterieux(n)) { System.out.println(n + " est un nombre mysterieux");
		 * } else { System.out.println(n + " n'est pas un nombre mysterieux"); } //
		 */

	}

	public static boolean estMysterieux(int n) {

		// concatenation de l'ecriture de n*n et n*n*n
		String a = String.valueOf(n * n) + String.valueOf(n * n * n);

		// tableau qui compte l'occurrence des nombres 0 à 9 dans l'ecriture
		int[] occurrence = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		for (int i = 0; i < a.length(); ++i) {

			int nombre = Integer.parseInt(a.charAt(i) + "");
			occurrence[nombre]++;

			if (occurrence[nombre] > 1)
				return false;
		}

		for (int i = 0; i < occurrence.length; ++i) {
			if (occurrence[i] != 1)
				return false;
		}

		return true;
	}

	public static boolean estMysterieuxFoursov(int n) {

		int a = n * n, b = a * n;

		// tableau qui marque la présence unique des nombres 0 à 9 dans l'ecriture
		boolean[] existOnce = { false, false, false, false, false, false, false, false, false, false };

		boolean checkResult = doCheck(a, existOnce);
		if (!checkResult) return false;
		
		checkResult = doCheck(b, existOnce);
		if (!checkResult) return false;

		for (int i = 0; i < existOnce.length; ++i) {
			if (!existOnce[i])
				return false;
		}

		return true;
	}
	
	
	/*
	 * Verifier si les chiffres de l'ecriture de a existe une fois
	 * @param a : nombre, existOnce: tableau de verification
	 * effet : met existOnce[i] à true si i existe une fois dans l'ecriture de a
	 * resulat : faux si un chiffre existe deux fois et vrai sinon
	 */
	public static boolean doCheck(int a, boolean[] existOnce) {
		
		while (a != 0) {

			int reste = a % 10;

			if (!existOnce[reste]) {
				existOnce[reste] = true;
			} else {
				return false;
			}
			a = a / 10;
		}
		
		return true;
	}
}
