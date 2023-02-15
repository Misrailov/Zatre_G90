package domein;

import taal.Taal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Speler vertegenwoordigt de werking en aanmaak van een speler.
 */
public class Speler {
	private String gebruikersnaam;
	private int jaar;
	private int aantalSpellen;
	private static final int AANTAL_SPEELKANSEN = 5;
	private Scoreblad scoreblad;
	private final List<Integer> persoonlijkePot = new ArrayList<>();
	private final int[][] zetten = { { -1, -1, -1 }, { -1, -1, -1 }, { -1, -1, -1 } }; // plaats voor 3 zetten, enkel
																						// bij 1e
	// beurt alle 3 gebruikt. 3 vakken:
	// , steen,verticaal,horizontaal

	/**
	 * Constructor voor instellen van een speler a.d.h.v. parameters.
	 *
	 * @param gebruikersnaam Gebruikersnaam van de speler.
	 * @param jaar           Geboortejaar van de speler.
	 * @param aantalSpellen  Het aantal spellen dat de speler nog kan spelen.
	 */
	public Speler(String gebruikersnaam, int jaar, int aantalSpellen) {
		this.setJaar(jaar);
		this.setGebruikersnaam(gebruikersnaam);
		this.setAantalSpellen(aantalSpellen);
	}

	/**
	 * Constructor voor Speler.
	 *
	 * @param gebruikersnaam Gebruikersnaam van de speler.
	 * @param geboortejaar   Geboortejaar van de speler.
	 */
	public Speler(String gebruikersnaam, int geboortejaar) {
		this(gebruikersnaam, geboortejaar, AANTAL_SPEELKANSEN);
	}

	/**
	 * Toevoegen van steentjes aan de persoonlijke pot van de speler.
	 *
	 * @param steen Waarde van de steen die moet worden toegevoegd aan de pot.
	 */
	public void voegSteenToe(int steen) {
		persoonlijkePot.add(steen);
	}

	public List<Integer> getPersoonlijkePot() {
		return persoonlijkePot;
	}

	/**
	 * geeft de persoonlijke pot van de speler terug in String vorm
	 * 
	 * @return String van steenwaardes in de persoonlijke pot van de speler
	 */
	public String toonSpelerPot() {
		StringBuilder spelerPot = new StringBuilder();
		for (Integer steen : persoonlijkePot) {
			spelerPot.append(steen);
		}
		return spelerPot.toString();
	}

	/**
	 * Steen uit de persoonlijke pot van de speler halen
	 *
	 * @param steen De steen die verwijderd moet worden uit de pot.
	 */
	public void neemSteen(int steen) {
		if (persoonlijkePot.contains(steen)) {
			persoonlijkePot.remove((Object) (steen));
		}
	}

	/**
	 * Bijhouden zetten van de speler tijdens een beurt.
	 *
	 * @param steen       De waarde van de geplaatste steen.
	 * @param x   De verticale positie op het spelbord.
	 * @param y De horizontale positie op het spelbord.
	 */
	public void voegZetToe(int steen, int x, int y) {
		for (int[] zet : this.getZetten()) {
			if (zet[0] == -1) {
				zet[0] = steen;
				zet[1] = x;
				zet[2] = y;
				break;
			}
		}
	}

	public String toString() {
		return String.format("%s%s%s - %s%s%d - %s%s%d%n", Taal.vertaal("gebruikersnaamdeclaratie"), ": ",
				this.getGebruikersnaam(), Taal.vertaal("geboortejaarsdeclaratie"), ": ", this.getJaar(),
				Taal.vertaal("speelkansendeclaratie"), ": ", this.getAantalSpellen());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speler other = (Speler) obj;
		return Objects.equals(gebruikersnaam, other.gebruikersnaam) && jaar == other.jaar;
	}

	public void setJaar(int jaar) {
		int currentyear = Calendar.getInstance().get(Calendar.YEAR);

		if (jaar < 1920 || jaar > currentyear) {
			throw new IllegalArgumentException("ongeldigeDatum");
		} else {
			if (jaar <= currentyear - 6) {
				this.jaar = jaar;
			} else
				throw new IllegalArgumentException("teJong");
		}
	}

	public void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam.isEmpty()) {
			throw new IllegalArgumentException("naamLeeg");
		} else {
			if (gebruikersnaam.length() >= 5) {
				this.gebruikersnaam = gebruikersnaam;
			} else
				throw new IllegalArgumentException("naamTekort");
		}

	}

	private void setAantalSpellen(int spel) {
		this.aantalSpellen = spel;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public int getJaar() {
		return jaar;
	}

	public int getAantalSpellen() {
		return aantalSpellen;
	}

	/**
	 * Het verminderen van het aantal speelkansen met 1 na starten van een spel.
	 */
	public void verminderSpeelKansen() {
		this.aantalSpellen--;
	}

	/**
	 * Nieuw scoreblad aanmaken voor de speler.
	 */
	public void nieuwScoreblad() {
		this.scoreblad = new Scoreblad();
	}

	public int[][] getSpelerScorebladArray() {
		int[][] scoreBladArray = new int[25][6];
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 6; j++) {
				scoreBladArray[i][j] = scoreblad.getScoreblad().get(i).getWaarde(j);
			}
		}
		return scoreBladArray;
	}

	/**
	 * Bij winst het aantal spellen van de speler vermeerderen met 2.
	 */
	public void winst() {
		this.aantalSpellen += 2;
	}

	public Scoreblad getScoreblad() {
		return this.scoreblad;
	}

	public int getTotaalScore() {
		return this.scoreblad.totaalScore();
	}


	public int[][] getZetten() {
		return zetten;
	}

	/**
	 * Steen terugzetten in de persoonlijke pot.
	 *
	 * @return De gewenste steen terugkrijgen.
	 */
	public int steenTerug() {
		int steen = this.persoonlijkePot.get(0);
		this.neemSteen(steen);
		return steen;
	}

	public void scoreDubbel() {
		this.getScoreblad().scoreDubbel();

	}

	public void score10(boolean score10) {
		this.getScoreblad().score10(score10);

	}

	public void score11(boolean score11) {
		this.getScoreblad().score11(score11);

	}

	public void score12(boolean score12) {
		this.getScoreblad().score12(score12);

	}

	/**
	 * Zorgt dat er in de ArrayList elk cijfer vervangen wordt door een -1
	 */
	public void resetZetten() {
		for (int i = 0; i < this.zetten.length; i++) {
			for (int j = 0; j < this.zetten[i].length; j++) {
				zetten[i][j] = -1;
			}
		}
	}
}
