package domein;

/**
 * SpelBord is verantwoordelijk voor de creatie van het spelbord.
 */

public class SpelBord {

	private final Vak[][] vakken = new Vak[15][15];

	/**
	 *Maakt een spelbord.
	 */
	public SpelBord() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				vakken[i][j] = new Vak();
				Vak vak = vakken[i][j];
				if ((i >= 1 && i < 14) && !((((i < 4) || i > 10) || i == 7) && ((j == 0) || (j == 14)))) {
					if (((j == i) || ((14 - i) == j))) {
						vak.setType(2);
					} else
						vak.setType(1);
				} else if (((j < 4 || j > 10) || j == 7)) {
					vak.setType(0);
				} else {
					vak.setType(1);
				}
				if (((i == 0) || (i == 14)) && ((j == 6) || (j == 8))) {
					vak.setType(2);
				}
				if (((j == 0) || (j == 14)) && ((i == 6) || (i == 8))) {
					vak.setType(2);
				}
			}
		}
	}

	/**
	 * Controleert of er een steen op de ingegeven positie van vak bevindt.
	 *
	 * @param x Verticale positie van vak.
	 * @param y Horizontale positie van vak.
	 * @return True als er een steen is op de aangegeven paramters, anders false.
	 */
	public boolean heeftSteen(int x, int y) {
		Vak vak = vakken[x][y];
		return vak.heeftSteen();
	}

	/**
	 * Controleert of het volledige spelbord leeg is.
	 *
	 * @return Een true als het bord leeg is, anders false.
	 */
	public boolean isLeeg() {
		boolean isLeeg = true;
		for (Vak[] vakLijst : vakken) {
			for (Vak vak : vakLijst) {
				if ((vak.heeftSteen())) {
					isLeeg = false;
				}
			}
		}
		return isLeeg;
	}

	/**
	 * Zet een steen op de aangegeven parameters.
	 *
	 * @param x     Verticale positie van vak.
	 * @param y     Horizontale positie van vak.
	 * @param steen Waarde steen.
	 */
	public void zetSteen(int x, int y, int steen) {
		Vak vak = vakken[x][y];
		if (!kanPlaatsen(x, y)) {
			throw new IllegalArgumentException("Hier kan je dit niet plaatsen");
		} else {
			vak.setWaarde(steen);
			vak.setHeeftSteen(true);
			this.tekenBord();
		}
	}

	/**
	 * Controleert of je een steen kan plaatsen op de aangegeven parameters.
	 *
	 * @param x Verticale positie van vak.
	 * @param y Horizontale positie van vak.
	 * @return Een true als je een steen kan plaatsen, anders false.
	 */
	public boolean kanPlaatsen(int x, int y) {
		Vak vak = vakken[x][y];
		// als vak muur is kan je niet plaatsen, als een vak grijs is en je krijgt geen
		// 10,11,12 dan kan je niet plaatsen
		if (this.isLeeg())
			return true;
		if (vak.getType() == 0) {
			throw new IllegalArgumentException("muurVak");
		} // getType=0 muur
		else if (vak.heeftSteen()) {
			throw new IllegalArgumentException("vakBezet");
		}
		else if (!heeftBuur(x, y)) {
			throw new IllegalArgumentException("geenBuurSteen"); // steen moet aan een bestaande steen liggen, tenzij
			// het de 1e beurt is.
		} else
			return true;

	}

	/**
	 * Bekijkt op de aangegeven locatie van een steen, buren zijn in zowel
	 * horizontale als verticale zin.
	 *
	 * @param x x-as
	 * @param y y-as
	 * @return Een true als er een buursteen is, anders false.
	 */
	public boolean heeftBuur(int x, int y) {
		if (x < 14) {
			if (this.heeftSteen(x + 1, y))
				return true;
		}
		if (x > 1) {
			if (this.heeftSteen(x - 1, y))
				return true;
		}
		if (y < 14) {
			if (this.heeftSteen(x, y + 1))
				return true;
		}
		if (y > 0) {
			if (this.heeftSteen(x, y - 1))
				return true;
		}
		return false;
	}
	/**
	 * haalt de steenwaarde op op het opgegeven vak
	 * @param x: x-Coordinaat
	 * @param y: y-coordinaat
	 * @return int met waarde van de steen
	 */
	public int getVakSteen(int x, int y) {
		return vakken[x][y].getWaarde();
	}
/**
 * haalt een 2-dimensionale array op met het typenummer van elk vak
 * @return 2-d array van integers met typenummers
 */
	public int[][] getTypesSpelbord() {
		int[][] lijstTypes = new int[15][15];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				lijstTypes[i][j] = vakken[i][j].getType();
			}
		}
		return lijstTypes;
	}
/**
 * geeft een 2-d array met booleans of een vak al dan niet een steen heeft
 * @return 2-d array met booleans(vak.heeftsteen)
 */
	public boolean[][] getHeeftSteenSpelbord() {
		boolean[][] lijstHeeftSteen = new boolean[15][15];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				lijstHeeftSteen[i][j] = vakken[i][j].heeftSteen();
			}
		}
		return lijstHeeftSteen;
	}

	/**
	 * Tekent de spelbord in CLI tot een Zatre bord.
	 */
	public void tekenBord() {
		for (int i = 0; i < 15; i++) {
			System.out.println();
			for (int j = 0; j < 15; j++) {
				Vak vak = this.vakken[i][j];
				if (vak.getType() == 2 && !vak.heeftSteen()) {
					System.out.print("[.]");
					// (char)9632
				} else if (vak.getType() == 0 && !vak.heeftSteen()) {
					System.out.print(" . ");

				} else if (!vak.heeftSteen()) {
					System.out.print("[_]");
					// (char)9633
				}
				if (vak.heeftSteen()) {
					System.out.print("[" + vak.getWaarde() + "]");
				}
			}
		}
		System.out.println("\n");
	}

	/**
	 * Berekent de totale som van een lijn stenen op de spelbord.
	 *
	 * @param x Verticale positie van vak.
	 * @param y Horizontale positie van vak.
	 * @param i Bepaalt of de lijn stenen op bord horizontaal of verticaal is.
	 * @return Som van de scores van de aangegeven lijn.
	 */
	public int waarden(int x, int y, int steen, boolean verticaal) {
		int som = 0;
		som += steen;
		if (!verticaal) {

			int h = x - 1;
			if (h >= 0) {
				while (this.vakken[h][y].heeftSteen()) {
					som += this.vakken[h][y].getWaarde();
					h--;
					if (h < 0)
						break;
				}
			}
			h = x + 1;
			if (h < 15) {
				while (this.vakken[h][y].heeftSteen()) {
					som += this.vakken[h][y].getWaarde();
					h++;
					if (h > 14)
						break;
				}
			}
			return som;
		} else {
			int v = y - 1;
			if (v >= 0) {
				while (this.vakken[x][v].heeftSteen()) {
					som += this.vakken[x][v].getWaarde();
					v--;
					if (v < 0)
						break;
				}
			}
			v = y + 1;
			if (v < 15) {
				while (this.vakken[x][v].heeftSteen()) {
					som += this.vakken[x][v].getWaarde();
					v++;
					if (v > 14)
						break;
				}
			}
			return som;
		}
	}

	/**
	 * Verwijdert een steen uit de bord.
	 *
	 * @param x Horizontale as.
	 * @param y Verticale as.
	 */
	public void verwijderSteen(int x, int y) {
		if (x >= 0 && y >= 0 && x < 15 && y < 15) {
			this.vakken[x][y].setHeeftSteen(false);
			this.vakken[x][y].setWaarde(0);
		}

	}
/**
 * geeft het vaktype voor gegeven parameters
 * @param x x-as
 * @param y y-as
 * @return vaktype als int
 */
	public int getVakType(int x, int y) {
		return this.vakken[x][y].getType();
	}

	/**
	 * Bekijkt of je een dubbele score krijgt door te controleren of je een steen op
	 * een grijze vak hebt.
	 *
	 * @param x Verticale positie van vak.
	 * @param y Horizontale positie van vak.
	 * @param i Bepaalt of de lijn stenen op bord horizontaal of verticaal is.
	 * @return Een true of false.
	 */
	public boolean dubbels(int x, int y, boolean verticaal) {
		if (this.getVakType(x, y) == 2)
			return true;
		if (!verticaal) {
			int h = x - 1;
			if (h >= 0) {
				while (this.vakken[h][y].heeftSteen()) {
					if (this.getVakType(h, y) == 2)
						return true;
					h--;
					if (h < 0)
						break;
				}
			}
			h = x + 1;
			if (h < 15) {
				while (this.vakken[h][y].heeftSteen()) {
					if (this.getVakType(h, y) == 2)
						return true;
					h++;
					if (h > 14)
						break;

				}
			}
		} else {
			int v = y - 1;
			if (v >= 0) {
				while (this.vakken[x][v].heeftSteen()) {
					if (this.getVakType(x, v) == 2)
						return true;
					v--;
					if (v < 0)
						break;
				}
			}
			v = y + 1;
			if (v < 15) {
				while (this.vakken[x][v].heeftSteen()) {
					if (this.getVakType(x, v) == 2)
						return true;
					v++;
					if (v > 14)
						break;
				}
			}
		}
		return false;
	}
}
