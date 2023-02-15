package domein;

import exceptions.kansException;

import java.util.ArrayList;
import java.util.List;

/**
 * Domeincontroller is verantwoordelijk voor algemeen overzicht van het
 * programma en het uitdelen van verantwoordelijkheid aan andere klassen.
 */
public class DomeinController {
	private final SpelerRepo spelerRepository;
	private Spel spel;
	private List<Speler> geselecteerdeSpelers;

	/**
	 * Constructor voor aanmaken nieuwe spelerrepository en array van geselecteerde
	 * spelers.
	 */
	public DomeinController() {
		spelerRepository = new SpelerRepo();
		this.geselecteerdeSpelers = new ArrayList<>();
	}

	/**
	 * passthrough methode die een 2-dimensionale array van het type van elk vak van
	 * het speelbord doorgeeft
	 * 
	 * @return int[][]
	 */
	public int[][] getTypes() {
		return spel.getTypesSpelBord();
	}

	/**
	 * Roept de methode volgendeSpeler() aan uit Spel.
	 */
	public void volgendeSpeler() {
		// zet speler op de volgende positie, of terug op de eerste als het de laatste
		// uit de lijst is
		spel.volgendeSpeler();
	}

	/**
	 * passthrough methode die een 2-dimensionale array teruggeeft met een boolean
	 * om te bepalen of het vak al dan niet een steen heeft
	 * 
	 * @return boolean[][]
	 */

	public boolean[][] getHeeftSteenSpelbord() {
		return spel.getHeeftSteenSpelbord();
	}

	/**
	 * passthrough methode om de speleinde en beurteinde variabele in spel in te
	 * stellen
	 */
	public void spelEinde() {
		this.spel.setSpelEinde(true);
		this.spel.setBeurtEinde(true);
	}

	/**
	 * passthrough methode die de speleinde boolean uit spel ophaalt
	 *
	 * @return True als spel ten einde is | False als spel niet ten einde is
	 */
	public boolean isSpelEinde() {

		return spel.isSpelEinde();
	}

	/**
	 * Speler wordt gekozen en aantal speelkansen wordt aangepast in de database.
	 * Controle op aantal geselecteerde spelers (min 2, max 4).
	 * 
	 * @throws Exception als er te weinig speelkansen zijn
	 */
	public void startSpel() throws kansException {
		// speler wordt gekozen en de speelkansen worden aangepast in database

		// als huidige spelers correcte grootte heeft
		if (geselecteerdeSpelers.size() >= 2 && geselecteerdeSpelers.size() < 5) {
			// new spel gecreerd
			this.spel = new Spel(geselecteerdeSpelers);
			for (Speler s : geselecteerdeSpelers) {
				spelerRepository.spelerKansenAanpassen(s);
			}

		}

	}

	/**
	 * Roept de methode startbeurt() aan uit Spel.
	 */

	public String startBeurt() {
		return this.spel.startBeurt();
	}

	/**
	 * Maakt een speler en geeft deze door aan de voegSpelerToe() methode in
	 * SpelerRepo.
	 */
	public void registreer(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegSpelerToe(new Speler(gebruikersnaam, geboortejaar));

	}

	public int[][] getSpelerScoreBladArray() {
		return this.spel.getHuidigeSpeler().getSpelerScorebladArray();
	}

	/**
	 * Selecteren van spelers om het spel te spelen.
	 *
	 * @param gebruikersnaam De gebruikersnaam van de te selecteren speler.
	 * @param geboortejaar   Het geboortejaar van de te selecteren speler.
	 */
	public void selecteerSpeler(String gebruikersnaam, int geboortejaar) {
		if (spelerRepository.bestaatSpeler(gebruikersnaam, geboortejaar)) {
			if (!this.geselecteerdeSpelers
					.contains((spelerRepository.selecteerSpelerMapper(gebruikersnaam, geboortejaar)))) {
				if ((this.geselecteerdeSpelers.size() < 4)) {
					this.geselecteerdeSpelers.add(spelerRepository.selecteerSpelerMapper(gebruikersnaam, geboortejaar));
				} else
					throw new IllegalArgumentException("4Spelers");
			} else
				throw new IllegalArgumentException("alGeselecteerd");
		} else
			throw new IllegalArgumentException("bestaatNiet");
	}

	/**
	 * Geeft alle spelers in de database weer.
	 * 
	 * @return Geeft een list van strings weer met alle spelers.
	 */
	public List<String> geefAlleSpelers() {
		// retourneert alle spelers uit de darabase
		List<String> spelersNamen = new ArrayList<>();
		for (Speler s : spelerRepository.geefSpelers()) {
			spelersNamen.add(s.getGebruikersnaam());
		}
		return spelersNamen;
	}

	public int winnaarsSpeelKansen() {
		return spel.getWinnaar().getAantalSpellen();
	}

	/**
	 * Retourneert alle momenteel geselecteerde spelers in de cui.
	 *
	 * @return String van namen van geselecteerde spelers.
	 */
	public String geselecteerdeSpelers() {
		// retourneert alle huidigespelers in de cui die geselecteerd zijn
		String namen = "";
		if (geselecteerdeSpelers.isEmpty() || geselecteerdeSpelers.get(0) == null) {
			namen = "";
		} else {
			for (Speler s : geselecteerdeSpelers) {
				namen += s.toString();
			}
		}
		return namen;
	}

	public String getSpelerNaam() {
		return spel.getHuidigeSpeler().getGebruikersnaam();
	}

	public int getSpelerJaar() {
		return this.spel.getHuidigeSpeler().getJaar();
	}

	public int getSpelerAantalSpellen() {
		return this.spel.getHuidigeSpeler().getAantalSpellen();
	}

	/**
	 * Roept zetSteen() aan in Spel.
	 *
	 * @param steen Waarde van de steen.
	 * @param x     Verticale positie van gekozen vak
	 * @param y     Horizontale positie van gekozen vak
	 */
	public void zetSteen(int steen, int x, int y) {
		this.spel.zetSteen(steen, x, y);
	}

	/**
	 * Maakt een string aan met de representatie van het Scoreblad voor in de console
	 * @return De representatie van het scoreblad
	 */

	public String toonScoreblad() {
		return String.format("%s%n%s", this.spel.getHuidigeSpeler().getGebruikersnaam(),
				this.spel.getHuidigeSpeler().getScoreblad().toonScoreblad());
	}

	/**
	 * Roept isBeurtEinde() aan uit Spel.
	 *
	 * @return boolean met de waarde (true=beurt ten einde | false=beurt niet ten
	 *         einde).
	 */
	public boolean isEindeBeurt() {
		return spel.isBeurtEinde();
	}

	public List<Integer> getSpelerPersoonlijkePot() {
		return spel.getHuidigeSpeler().getPersoonlijkePot();
	}


	/**
	 * Geeft de persoonlijke pot weer van de speler aan de beurt (cui).
	 *
	 * @return Een string van de stenen die de speler in zijn bezit heeft.
	 */
	public String getPot() {
		String stenen = "";
		for (int steen : this.getSpelerPersoonlijkePot()) {
			stenen += steen + ",";
		}
		return stenen;
	}

	/**
	 * Roept stenenTerug() aan uit Spel.
	 */
	public void stenenTerug() {
		spel.stenenTerug();
	}

	/**
	 * Retourneert het aantal geselecteerde spelers.
	 *
	 * @return Aantal geselecteerde spelers.
	 */
	public int aantalSpelers() {
		return this.geselecteerdeSpelers.size();
	}

	/**
	 * Retourneert een steen dat op een specifiek vak staat
	 * 
	 * @param verticaal   de verticale positie op het spelbord
	 * @param horizontaal de horizontale positie op het spelbord
	 * @return retourneert de waarde van de steen
	 */
	public int getVakSteen(int verticaal, int horizontaal) {
		return spel.getVakSteen(verticaal, horizontaal);
	}

	/**
	 * bepaalt de winnaar van het spel en past zijn speelkansen aan.
	 */
	public void bepaalWinnaar() {
		spel.bepaalWinnaar();
		spel.getWinnaar().winst();
		spelerRepository.spelerKansenAanpassen(spel.getWinnaar());
	}

	/**
	 * haalt de naam van de winnaar op
	 * 
	 * @return de naam van de winnaar
	 */
	public String winnaarsNaam() {
		return this.spel.getWinnaar().getGebruikersnaam();
	}

	/**
	 * haalt de score van de winnaar op uit zijn scoreblad
	 * 
	 * @return de score van de winnnaar
	 */
	public int winnaarsScore() {
		return this.spel.getWinnaar().getScoreblad().totaalScore();
	}

	/**
	 * roept de gepaste methode aan na een zet is ingegeven
	 * 
	 * @param zet door de speler ingegeven parameters van de zet
	 */
	public void speelZet(String zet) {
		if (zet.contentEquals("0")) {
			this.stenenTerug();
		} else if (zet.contentEquals("9")) {
			this.spelEinde();
		} else {
			String[] zetdelen = zet.split(",");
			int steen = Integer.parseInt(zetdelen[0]);
			int x = Integer.parseInt(zetdelen[1]);
			int y = Integer.parseInt(zetdelen[2]);
			this.zetSteen(steen, x, y);
		}

	}

	/**
	 * roept de gepaste methode aan voor de eerste zet van het spel
	 * 
	 * @param steen: de steenwaarde ingegeven door de gebruiker
	 */
	public void eersteZet(int steen) {
		if (steen == 0) {
			this.stenenTerug();
		} else {
			this.zetSteen(steen, 7, 7);

		}

	}

	/**
	 * verwijdert alle geselecteerde spelers uit de geselecteerdeSpelers array
	 */
	public void resetGeselecteerdeSpelers() {
		this.geselecteerdeSpelers = new ArrayList<>();
	}
}
