package fr.istic.prg1.tree;

import java.util.Scanner;

//import fr.istic.prg1.tree_util.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author MickaÃ«l Foursov <foursov@univ-rennes1.fr>
 * @version 5.0
 * @since 2016-04-20
 * 
 *        Classe dÃ©crivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 * 
 */

public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x abscisse du point
	 * @param y ordonnÃ©e du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumÃ© dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) {

		Iterator<Node> it = this.iterator();
		int x1 = 0, y1 = 0, x2 = 255, y2 = 255;
		int middle = 0;
		boolean horizontal = true; // alternateur type de decoupe

		while (it.nodeType() != NodeType.LEAF) {
			if (horizontal) { // HORIZONTAL
				middle = (y2 + y1) / 2;
				if (y <= middle) {
					it.goLeft();
					y2 = middle;
				} else {
					it.goRight();
					y1 = middle + 1;
				}
			} else { // VERTICAL
				middle = (x2 + x1) / 2;
				if (x <= middle) {
					it.goLeft();
					x2 = middle;
				} else {
					it.goRight();
					x1 = middle + 1;
				}
			}
			horizontal = !horizontal;
		}
		return it.getValue().state == 1;
	}

	/**
	 * this devient identique Ã  image2.
	 *
	 * @param image2 image Ã  copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) {

		if (this != image2) {
			Iterator<Node> it1 = this.iterator();
			Iterator<Node> it2 = image2.iterator();

			it1.clear();
			affectAux(it1, it2);
		}
	}

	private static void affectAux(Iterator<Node> it1, Iterator<Node> it2) {

		it1.addValue(it2.getValue());

		if (it2.nodeType() == NodeType.DOUBLE) {

			it2.goLeft();
			it1.goLeft();
			affectAux(it1, it2);
			it2.goUp();
			it1.goUp();

			it2.goRight();
			it1.goRight();
			affectAux(it1, it2);
			it2.goUp();
			it1.goUp();
		}
	}

	/**
	 * this devient rotation de image2 Ã  180 degrÃ©s.
	 *
	 * @param image2 image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear();
		rotate180Aux(it1, it2);
	}

	private static void rotate180Aux(Iterator<Node> it1, Iterator<Node> it2) {

		it1.addValue(it2.getValue());

		if (it2.nodeType() == NodeType.DOUBLE) {

			it2.goRight();
			it1.goLeft();
			rotate180Aux(it1, it2);
			it2.goUp();
			it1.goUp();

			it2.goLeft();
			it1.goRight();
			rotate180Aux(it1, it2);
			it2.goUp();
			it1.goUp();
		}
	}

	/**
	 * this devient rotation de image2 Ã  90 degrÃ©s dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2 image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {

		System.out.println();
		System.out.println("-------------------------------------------------");
		System.out.println("Fonction non demandée");
		System.out.println("-------------------------------------------------");
		System.out.println();
	}

	/**
	 * this devient inverse vidÃ©o de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {

		Iterator<Node> it = this.iterator();
		videoInverseAux(it);
	}

	private static void videoInverseAux(Iterator<Node> it) {

		if (it.nodeType() == NodeType.LEAF) {
			it.setValue(Node.valueOf((it.getValue().state == 1) ? 0 : 1));
		} else {
			it.goLeft();
			videoInverseAux(it);
			it.goUp();
			it.goRight();
			videoInverseAux(it);
			it.goUp();
		}
	}

	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2 image Ã  agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear();
		mirrorVAux(it1, it2, true);
	}

	private static void mirrorVAux(Iterator<Node> it1, Iterator<Node> it2, boolean horizontal) {

		if (it2.nodeType() == NodeType.DOUBLE) {

			it1.addValue(Node.valueOf(2));

			it2.goLeft();
			if (horizontal) {
				it1.goRight();
			} else {
				it1.goLeft();
			}
			mirrorVAux(it1, it2, !horizontal);
			it1.goUp();
			it2.goUp();

			it2.goRight();
			if (horizontal) {
				it1.goLeft();
			} else {
				it1.goRight();
			}
			mirrorVAux(it1, it2, !horizontal);
			it1.goUp();
			it2.goUp();

		} else {
			it1.addValue(it2.getValue());
		}
	}

	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2 image Ã  agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear();
		mirrorHAux(it1, it2, true);
	}

	private static void mirrorHAux(Iterator<Node> it1, Iterator<Node> it2, boolean horizontal) {

		if (it2.nodeType() == NodeType.DOUBLE) {

			it1.addValue(Node.valueOf(2));

			it2.goRight();
			if (horizontal) {
				it1.goRight();
			} else {
				it1.goLeft();
			}
			mirrorHAux(it1, it2, !horizontal);
			it1.goUp();
			it2.goUp();

			it2.goLeft();
			if (horizontal) {
				it1.goLeft();
			} else {
				it1.goRight();
			}
			mirrorHAux(it1, it2, !horizontal);
			it1.goUp();
			it2.goUp();

		} else {
			it1.addValue(it2.getValue());
		}
	}

	/**
	 * this devient quart supÃ©rieur gauche de image2.
	 *
	 * @param image2 image Ã  agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		int i = 0; // goToTopLeft's node
		while (it2.nodeType() != NodeType.LEAF && i < 2) {
			it2.goLeft();
			i++;
		}

		it1.clear();
		affectAux(it1, it2);
	}

	/**
	 * Le quart supÃ©rieur gauche de this devient image2, le reste de this devient
	 * Ã©teint.
	 * 
	 * @param image2 image Ã  rÃ©duire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2) {

		Iterator<Node> it1 = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		it1.clear();

		int i = 0;
		while (i < 2) {
			it1.addValue(Node.valueOf(2));
			it1.goRight();
			it1.addValue(Node.valueOf(0));
			it1.goUp();
			it1.goLeft();
			i++;
		}

		zoomOutAux(it1, it2, 14);

		if (it1.getValue().state == 0) {
			it1.goRoot();
			it1.clear();
			it1.addValue(Node.valueOf(0));
		}
	}

	private static void zoomOutAux(Iterator<Node> it1, Iterator<Node> it2, int depth) {

		if (depth == 0) {
			// couleur dominante

			if (it2.getValue().state == 2) {
				it1.addValue(Node.valueOf(getDominantColor(it2)));
			} else {
				it1.addValue(it2.getValue());
			}

		} else {

			it1.addValue(it2.getValue());

			if (it2.nodeType() == NodeType.DOUBLE) {

				depth--;

				it1.goLeft();
				it2.goLeft();
				zoomOutAux(it1, it2, depth);
				int leftState = it1.getValue().state;
				it1.goUp();
				it2.goUp();

				it1.goRight();
				it2.goRight();
				zoomOutAux(it1, it2, depth);
				int rightState = it1.getValue().state;
				it1.goUp();
				it2.goUp();

				if ((leftState != 2) && leftState == rightState) {
					it1.clear();
					it1.addValue(Node.valueOf(leftState));
				}
			}
		}
	}

	/**
	 * @pre it.nodeType() == NodeType.DOUBLE
	 */
	private static int getDominantColor(Iterator<Node> it) {

		int black = 0;
		int white = 0;

		it.goLeft();
		if (it.getValue().state == 0) {
			black += 2;
		} else if (it.getValue().state == 1) {
			white += 2;
		}
		it.goUp();

		it.goRight();
		if (it.getValue().state == 0) {
			black += 2;
		} else if (it.getValue().state == 1) {
			white += 2;
		}
		it.goUp();

		return (white >= black) ? 1 : 0;
	}

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels allumÃ©s.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {

		Iterator<Node> it = this.iterator();
		Iterator<Node> it1 = image1.iterator();
		Iterator<Node> it2 = image2.iterator();

		it.clear();
		intersectionAux(it, it1, it2);
	}

	private static void intersectionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2) {

		if (it1.getValue().state == 0 || it2.getValue().state == 0) {
			it.addValue(Node.valueOf(0));
		} else if (it1.getValue().state == 1) {
			affectAux(it, it2);
		} else if (it2.getValue().state == 1) {
			affectAux(it, it1);
		} else {

			it.addValue(Node.valueOf(2));

			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			intersectionAux(it, it1, it2);
			int leftState = it.getValue().state;
			it.goUp();
			it1.goUp();
			it2.goUp();

			it.goRight();
			it1.goRight();
			it2.goRight();
			intersectionAux(it, it1, it2);
			int rightState = it.getValue().state;
			it.goUp();
			it1.goUp();
			it2.goUp();

			if (leftState == 0 && (leftState == rightState)) {
				it.clear();
				it.addValue(Node.valueOf(0));
			}
		}
	}

	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumÃ©s.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) {

		Iterator<Node> it = this.iterator();
		Iterator<Node> itOnImg1 = image1.iterator();
		Iterator<Node> itOnImg2 = image2.iterator();

		it.clear();
		unionAux(it, itOnImg1, itOnImg2);
	}

	private static void unionAux(Iterator<Node> it, Iterator<Node> itOnImg1, Iterator<Node> itOnImg2) {

		if (itOnImg1.getValue().state == 1 || itOnImg2.getValue().state == 1) {
			it.addValue(Node.valueOf(1));
		} else if (itOnImg1.getValue().state == 0 && itOnImg2.getValue().state == 0) {
			it.addValue(Node.valueOf(0));
		} else if (itOnImg1.getValue().state == 0) {
			affectAux(it, itOnImg2);
		} else if (itOnImg2.getValue().state == 0) {
			affectAux(it, itOnImg1);
		} else {
			it.addValue(Node.valueOf(2));

			int leftState = 2, rightState = 2;
			it.goLeft();
			itOnImg1.goLeft();
			itOnImg2.goLeft();
			unionAux(it, itOnImg1, itOnImg2);
			leftState = it.getValue().state;
			it.goUp();
			itOnImg1.goUp();
			itOnImg2.goUp();

			it.goRight();
			itOnImg1.goRight();
			itOnImg2.goRight();
			unionAux(it, itOnImg1, itOnImg2);
			rightState = it.getValue().state;
			it.goUp();
			itOnImg1.goUp();
			itOnImg2.goUp();

			if ((leftState == 1) && leftState == rightState) {
				it.clear();
				it.addValue(Node.valueOf(leftState));
			}
		}
	}

	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumÃ©s dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {

		Iterator<Node> it = this.iterator();
		return testDiagonalAux(it);
	}

	private static boolean testDiagonalAux(Iterator<Node> it) {

		if (it.nodeType() == NodeType.LEAF) {
			return it.getValue().state == 1;
		}

		// We could have used one variable, but for better read...
		boolean test = false;

		it.goLeft();
		if (it.getValue().state == 2) {
			it.goLeft();
			test = testDiagonalAux(it);
			it.goUp();
		} else {
			test = it.getValue().state == 1;
		}
		it.goUp();

		if (test) {
			it.goRight();
			if (it.getValue().state == 2) {
				it.goRight();
				test = testDiagonalAux(it);
				it.goUp();
			} else {
				test = it.getValue().state == 1;
			}
			it.goUp();
		}
		return test; // testTopLeft == true
	}

	/**
	 * @param x1 abscisse du premier point
	 * @param y1 ordonnÃ©e du premier point
	 * @param x2 abscisse du deuxiÃ¨me point
	 * @param y2 ordonnÃ©e du deuxiÃ¨me point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont reprÃ©sentÃ©s par
	 *         la mÃªme feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {

		Iterator<Node> it = this.iterator();
		int a1 = 0, b1 = 0, a2 = 255, b2 = 255, middleX = 0, middleY = 0;
		boolean horizontal = true, hasSameLeaf = true;

		while (it.nodeType() != NodeType.LEAF && hasSameLeaf) {

			if (horizontal) {

				middleY = (b2 + b1) / 2;

				if (y1 <= middleY && y2 <= middleY) {
					it.goLeft();
					b2 = middleY;
				} else if (y1 > middleY && y2 > middleY) {
					it.goRight();
					b1 = middleY + 1;
				} else {
					hasSameLeaf = false;
				}

			} else {

				middleX = (a2 + a1) / 2;

				if (x1 <= middleX && x2 <= middleX) {
					it.goLeft();
					a2 = middleX;
				} else if (x1 > middleX && x2 > middleX) {
					it.goRight();
					a1 = middleX + 1;
				} else {
					hasSameLeaf = false;
				}
			}

			horizontal = !horizontal;
		}

		return hasSameLeaf;
	}

	/**
	 * @param image2 autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumÃ©s
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {

		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();

		return isIncludedInAux(it, it2);
	}

	private static boolean isIncludedInAux(Iterator<Node> it, Iterator<Node> it2) {

		int state1 = it.getValue().state, state2 = it2.getValue().state;

		if ((state1 == 1 && state2 == 1) || (state1 == 2 && state2 == 1) || (state1 == 0 && state2 == 0)
				|| (state1 == 0 && state2 == 2)) {
			return true;
		} else if (state1 == 2 && state2 == 2) {

			it.goLeft();
			it2.goLeft();
			boolean isIncludedIn = isIncludedInAux(it, it2);
			it.goUp();
			it2.goUp();

			if (isIncludedIn) {
				it.goRight();
				it2.goRight();
				isIncludedIn = isIncludedInAux(it, it2);
				it.goUp();
				it2.goUp();
			}
			return isIncludedIn;
		} else {
			return false;
		}
	}

}
