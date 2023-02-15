package domein;

import exceptions.beurtException;
import exceptions.kansException;

import java.util.Collections;
import java.util.List;

/**
 * Spel vertegenwoordigt de werking van het spel.
 */
public class Spel {
	private final List<Speler> spelers;
	public SpelBord sp;
	private boolean spelEinde;

	private final Pot pot;
	private boolean beurtEinde;

	private Speler huidigeSpeler;
	private Speler winnaar;
	private boolean eersteBeurt;

	private int spelerNr;

	/**
	 * Spelbord wordt aangemaakt, spelers worden doorgegeven en er wordt een nieuwe
	 * pot gemaakt.
	 *
	 * @param spelers Geselecteerde spelers aan het spel doorgeven.
	 * @throws Exception 
	 */
	public Spel(List<Speler> spelers) throws kansException {
		// constructor
		// spelers die worden meegegeven worden huidigespelers
		this.spelers = spelers;
		// spelers worden geshuffeld
		this.volgordeSpelers();
		for (Speler sp : spelers) {
			// spelerkansen worden verminderd, er wordt een nieuw scoreblad gemaakt voor een
			// Speler
			if(sp.getAantalSpellen()>0) {
			sp.verminderSpeelKansen();
			// er wordt een scoreblad gemaakt voor de speler
			sp.nieuwScoreblad();
		}else throw new kansException("kansTekort");}
		// een spelbord wordt gemaakt
		sp = new SpelBord();
		// een nieuw pot wordt gemaakt
		this.pot = new Pot();
		// speleinde is vals
		this.spelEinde = false;
		// de eerste
		this.setHuidigeSpeler(this.spelers.get(0));
		this.spelerNr = 0;
		this.eersteBeurt = true;

	}

	public int[][] getTypesSpelBord() {
		return sp.getTypesSpelbord();
	}

	public boolean[][] getHeeftSteenSpelbord() {
		return sp.getHeeftSteenSpelbord();
	}

	/**
	 * Roept neemSteen() aan uit Speler.
	 *
	 * @param speler Speler waarvan de steen ontnomen moet worden.
	 * @param steen  Steen dewelke ontnomen moet worden van de speler zijn pot.
	 */
	public void neemSteen(Speler speler, int steen) {
		// verwijdert steen uit de speler zijn collectie nadat steen genomen is
		speler.neemSteen(steen);
	}

	/**
	 * Plaatsen van gekozen steen op spelbord.
	 *
	 * @param steen De gekozen steen om te plaatsen.
	 * @param x     De verticale positie op het spelbord.
	 * @param y     De horizontale positie op het spelbord.
	 */

	public void zetSteen(int steen, int x, int y) {
		// speler zet steen als de condities correct zijn dan wordt deze steen geplaatst
		// op het spelbord en word deze steen uit collectie gehaald
		if (this.getHuidigeSpeler().getPersoonlijkePot().contains(steen)) {
			try {

				sp.zetSteen(x, y, steen);
				this.getHuidigeSpeler().voegZetToe(steen, x, y);

			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
			neemSteen(this.getHuidigeSpeler(), steen);
			if (this.getHuidigeSpeler().getPersoonlijkePot().size() == 0) {
				this.controleBeurt();

			}

		} else
			throw new IllegalArgumentException("steenNietInPot");
	}

	/**
	 * Eindcontrole of de steentjes op een legale plaats liggen op het spelbord.
	 */
	private void controleBeurt() {
		boolean controle = true;
		String error = "";
		for (int[] zet : this.getHuidigeSpeler().getZetten()) { // voor elke gezette steen
			int steen = zet[0];
			int x = zet[1];
			int y = zet[2];
			// controle x || controle y geen serie groter dan 12.
			if (steen != -1) {
				if (sp.waarden(x, y, steen, false) > 12 || sp.waarden(x, y, steen, true) > 12) {
					error += steen + ",";
					error += x + ",";
					error += y;
					controle = false;
					break;
				}
				// controle dat als op grijs vak geplaatst er
				// minstens een 10 gescoord is.
				if (sp.getVakType(x, y) == 2) {
					if (sp.waarden(x, y, steen, false) < 10 && sp.waarden(x, y, steen, true) < 10) {
						error += steen + ",";
						error += x + ",";
						error += y;
						controle = false;
						break;
					}
				}
			}
		}
		// zodra 1 zet niet voldoet for loop stoppen

		if (!controle) { // controle gefaald: zetten ongedaan maken en stenen terug in persoonlijke pot
			for (int[] zet : this.getHuidigeSpeler().getZetten()) {
				if (zet[0] >= 1 && zet[0] <= 6) {
					sp.verwijderSteen(zet[1], zet[2]);
					this.getHuidigeSpeler().voegSteenToe(zet[0]);
				}
			}
			this.getHuidigeSpeler().resetZetten();
			throw new beurtException(error); // na terugzetten error throwen om boodschap te kunnen weergeven
			// met incorrecte zet
		} else {
			this.beurtEinde = true;// gebeurt enkel als controle ok is.
			this.updateScore();
			this.getHuidigeSpeler().resetZetten();
		}

	}

	/**
	 * haalt de steen in het vak met opgegeven coordinaten op.
	 * 
	 * @param x : x-coördinaat
	 * @param y : y-coördinaat
	 * @return waarde van de steen als int
	 */
	public int getVakSteen(int x, int y) {
		return sp.getVakSteen(x, y);
	}

	/**
	 * Updaten van scores in het scorebord na elke beurt.
	 */
	private void updateScore() {
		int puntY = -1;
		int puntX = -1;
		boolean score10 = false;
		boolean score11 = false;
		boolean score12 = false;
		for (int[] zet : this.getHuidigeSpeler().getZetten()) { // voor elke gezette steen

			int steen = zet[0];
			int x = zet[1];
			int y = zet[2];
			if (steen != -1) {
				for (int i = 10; i <= 12; i++) {
					if (x != puntX) {
						if (sp.waarden(x, y, steen, true) == i) {
							if (sp.dubbels(x, y, true)) {
								this.getHuidigeSpeler().scoreDubbel();
							}
							if (i == 10) {
								this.getHuidigeSpeler().score10(score10);
								score10 = true;
								if (eersteBeurt) {
									this.eersteBeurt = false;
								}
							} else if (i == 11) {
								this.getHuidigeSpeler().score11(score11);
								score11 = true;
								if (eersteBeurt) {
									this.eersteBeurt = false;
								}
							} else if (i == 12) {
								this.getHuidigeSpeler().score12(score12);
								score12 = true;
								if (eersteBeurt) {
									this.eersteBeurt = false;
								}
							}
							puntX = x;
						}

					}
					if (y != puntY) {
						if (sp.waarden(x, y, steen, false) == i) {
							if (sp.dubbels(x, y, false)) {
								this.getHuidigeSpeler().scoreDubbel();
							}
							if (i == 10) {
								this.getHuidigeSpeler().score10(score10);
								score10 = true;
								if (eersteBeurt) {
									this.eersteBeurt = false;
								}
							} else if (i == 11) {
								this.getHuidigeSpeler().score11(score11);
								score11 = true;
								if (eersteBeurt) {
									this.eersteBeurt = false;
								}
							} else if (i == 12) {
								this.getHuidigeSpeler().score12(score12);
								if (eersteBeurt) {
									this.eersteBeurt = false;
								}
							}
							puntY = y;
						}
					}

				}
			}
		}
	}

	/**
	 * Persoonlijke steencollectie aanmaken.
	 *
	 * @param speler      De speler die de steentjes krijgt.
	 * @param eersteBeurt Dient als controle om te kijken of het de eerste beurt is
	 *                    van het spel.
	 */
	public void maakSpelerPot(Speler speler, boolean eersteBeurt) {
		// eersteZet true =3 stenen, eersteZet false = 2 stenen
		for (int i = 0; i < (eersteBeurt ? 3 : 2); i++) {
			if (this.getPot().getStenenCollectie().size() > 0) {
				speler.voegSteenToe(getPot().neemSteen());
			} else {
				this.spelEinde = true;
			}
		}
	}

	/**
	 * Starten van de beurt. volgende speler wordt geselecteert en methodes om
	 * persoonlijke pot aan te maken en te tonen en bord te tonen aangeroepen.
	 */
	public String startBeurt() {
		this.beurtEinde = false;
		if (eersteBeurt) {
			spelerNr = 0;
			this.setHuidigeSpeler(spelers.get(0));
		} else
			this.volgendeSpeler();
		Speler speler = this.getHuidigeSpeler();
		speler.resetZetten();
		if (sp.isLeeg()) {
			if (speler.getPersoonlijkePot().size() == 0) {
				maakSpelerPot(speler, true); // boolean om aan te tonen dat het 1e beurt is.
			}
		} else {
			if (speler.getPersoonlijkePot().size() == 0) {
				maakSpelerPot(speler, false);
			}
		}
		tekenBord();

		return speler.toonSpelerPot();
	}

	/**
	 * Plaatst spelers in een willekeurige volgorde.
	 */
	public void volgordeSpelers() {
		Collections.shuffle(this.spelers);
	}

	public Pot getPot() {
		return pot;
	}

	/**
	 * Roept tekenBord() aan uit SpelBord.
	 */
	public void tekenBord() {
		sp.tekenBord();
	}

	/**
	 * Retourneert of een beurt ten einde is.
	 *
	 * @return boolean met de waarde (true=beurt ten einde | false=beurt niet ten
	 *         einde).
	 */
	public boolean isBeurtEinde() {
		return beurtEinde;
	}

	/**
	 * Selecteert de volgende speler die aan de beurt is. De eerste speler wordt
	 * opnieuw geselecteerd wanneer de laatste speler in de lijst zijn beurt heeft
	 * afgewerkt.
	 */
	public void volgendeSpeler() {
		spelerNr++;
		spelerNr = spelerNr % this.spelers.size();
		this.setHuidigeSpeler(this.spelers.get(spelerNr));
	}

	public Speler getHuidigeSpeler() {
		return this.huidigeSpeler;
	}

	/**
	 * Roept steenInCollectie() aan uit Pot.
	 */
	public void stenenTerug() {
		while (this.getHuidigeSpeler().getPersoonlijkePot().size() > 0) {
			int steen = this.getHuidigeSpeler().steenTerug();
			if (steen >= 1 && steen <= 6)
				this.pot.steenInCollectie(steen);
		}
		this.controleBeurt();
	}

	public List<Speler> getSpelers() {
		return spelers;
	}

	public boolean isSpelEinde() {
		return spelEinde;
	}

	public void setHuidigeSpeler(Speler huidigeSpeler) {
		this.huidigeSpeler = huidigeSpeler;
	}

	public void setSpelEinde(boolean spelEinde) {
		this.spelEinde = spelEinde;
	}

	/**
	 * Bepaalt de winnaar volgens hoogste score in het scoreblad
	 */
	public void bepaalWinnaar() {
		winnaar = this.huidigeSpeler;
		for (Speler speler : spelers) {
			if (speler.getScoreblad().totaalScore() > winnaar.getScoreblad().totaalScore()) {
				winnaar = speler;
			}
		}
	}

	public Speler getWinnaar() {
		return winnaar;
	}

	public void setBeurtEinde(boolean beurtEinde) {
		this.beurtEinde = beurtEinde;
	}
}