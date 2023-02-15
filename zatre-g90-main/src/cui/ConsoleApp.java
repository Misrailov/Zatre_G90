package cui;

import domein.DomeinController;
import exceptions.beurtException;
import exceptions.kansException;
import taal.Taal;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * ConsoleApp bevat de logica om de applicatie in console te kunnen starten.
 */
public class ConsoleApp {
	public Scanner input;
	public int antwoord;
	public DomeinController dc;
	public static Taal taal;

	/**
	 * Constructor van ConsoleApp.
	 *
	 * @param dc Domeincontroller die gebruikt wordt.
	 */
	public ConsoleApp(DomeinController dc) {
		this.dc = dc;
		input = new Scanner(System.in);
	}

	/**
	 * Methode om de applicatie te starten.
	 */
	public void start() {
		taalKeuze();
		toonNamen();
		menu();
	}

	/**
	 * Instellen van de gewenste taal voor de hele applicatie.
	 */
	public void taalKeuze() {
		// kiest de taal
		try {
			input = new Scanner(System.in);
			System.out.println("Taalkeuze - Language choice:");
			System.out.println("1) Nederlands\n2) English");

			switch (input.nextInt()) {
			case 1 -> taal = new Taal("nl");
			case 2 -> taal = new Taal("en");
			default ->
				throw new IllegalArgumentException("Gelieve 1 of 2 te selecteren - Please choose option 1 or 2.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() != null ? e.getMessage()
					: "Dit is geen geldige keuze - This is not a valid choice.");
			taalKeuze();
		}
	}

	/**
	 * Weergeven van alle spelers die in de databank zitten.
	 */
	public void toonNamen() { // toont alle spelers in databank
		System.out.printf("%n%s%n===============%n%n%s%n", Taal.vertaal("huidigeSpelers"), dc.geefAlleSpelers());
	}

	/**
	 * Menu om het spel te starten, spelers te selecteren of te registreren.
	 */
	public void menu() {
		// menu om spel te starten, spelers te selecteren,etc...

		input = new Scanner(System.in);
		System.out.println(Taal.vertaal("menuOpties"));
		try {
			int keuze = input.nextInt();
			switch (keuze) {
			case 0 ->
				// eindigt spel
				System.exit(0);
			case 1 -> {
				// registreer speler
				registreerSpeler();
				menu();
			}
			case 2 -> {
				// selecteert spelers
				selecteerSpelers();
				menu();
			}
			case 3 ->
				// start het spel
				startSpel();
			default -> {
				// bij verkeerde input, maak een geldige keuze en terug naar main menu
				System.out.println(Taal.vertaal("geldigeKeuze"));
				menu();
			}
			}

		} catch (InputMismatchException e) {
			System.out.println(Taal.vertaal("geenInt"));
			menu();
		} catch (Exception e) {
			System.out.println(Taal.vertaal(e.getMessage()));
			menu();
		}
	}

	/**
	 * Registeren van een nieuwe speler.
	 */
	public void registreerSpeler() {
		int jaar;
		System.out.println(Taal.vertaal("gebruikersnaam") + ": ");
		String gebruikersnaam = input.next();
		System.out.println(Taal.vertaal("geboortejaar") + ": ");
		try {
			try {
				jaar = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine();
				throw new InputMismatchException("geenInt");
			}
			dc.registreer(gebruikersnaam, jaar);
			System.out.printf("%s%s%n%s%d%n%s%d%n", Taal.vertaal("gebruikersnaamdeclaratie") + ": ", dc.getSpelerNaam(),
					Taal.vertaal("geboortejaarsdeclaratie") + ": ", dc.getSpelerJaar(),
					Taal.vertaal("speelkansendeclaratie") + ": ", dc.getSpelerAantalSpellen());
		} catch (Exception e) {
			System.out.println(Taal.vertaal(e.getMessage()));
			System.out.println(Taal.vertaal("registreren"));
			antwoord = input.nextInt();
			switch (antwoord) {
			case 1: {
				this.registreerSpeler();
			}
			case 0: {
				return;
			}
			default: {
				System.out.println(Taal.vertaal("geldigeKeuze"));
				return;
			}

			}
		}
	}

	/**
	 * Selecteren van een speler om spel te starten.
	 */
	public void selecteerSpelers() {
		input = new Scanner(System.in);
		System.out.println(Taal.vertaal("gebruikersnaam"));
		String gebruikersnaam = input.next();

		System.out.println(Taal.vertaal("geboortejaar"));
		int geboortejaar = input.nextInt();
		try {
			dc.selecteerSpeler(gebruikersnaam, geboortejaar);
			System.out.printf("%s: %n%s%n", Taal.vertaal("huidigeSpelers"), dc.geselecteerdeSpelers());

		} catch (Exception IllegalArgumentException) {
			System.out.println(IllegalArgumentException.getMessage());
			if (IllegalArgumentException.getMessage().equals(Taal.vertaal("4Spelers"))) {
				return;
			}
			System.out.println(Taal.vertaal("spelerSelectie2"));
			do {
				try {
					antwoord = input.nextInt();
				} catch (InputMismatchException e) {
					System.out.println(Taal.vertaal("geenInt"));
				}
				switch (antwoord) {
				case 1: {

					this.selecteerSpelers();

				}

				case 0: {
					return;
				}
				default: {
					System.out.println(Taal.vertaal("geldigeKeuze"));
					return;
				}
				}
			} while (antwoord != 1 && antwoord != 0);
		}
		System.out.println(Taal.vertaal("spelerSelectie2"));
		do {
			antwoord = input.nextInt();

			switch (antwoord) {
			case 1: {
				this.selecteerSpelers();
			}
			case 0: {
				return;
			}
			default: {
				System.out.println(Taal.vertaal("geldigeKeuze"));
				return;
			}
			}
		} while (antwoord != 1 && antwoord != 0);
	}

	/**
	 * Starten van het spel.
	 */
	public void startSpel() {
		try {
			dc.startSpel(); // alles klaarzetten en aanmaken

			int steen;

			System.out.println(dc.startBeurt());
			System.out.printf("%n%s%s", Taal.vertaal("huidigeSpeler"), dc.getSpelerNaam());
			System.out.println("Pot: " + dc.getPot());
			System.out.println(Taal.vertaal("eersteZet"));// 1e zet is speciaal, dus speciale boodschap en stappen

			steen = input.nextInt();
			dc.eersteZet(steen);
			while (!dc.isSpelEinde()) {
				System.out.println(dc.startBeurt());
				System.out.printf("%n%s%s", Taal.vertaal("huidigeSpeler"), dc.getSpelerNaam());
				System.out.println("Pot: " + dc.getPot());
				System.out.println(Taal.vertaal("zet"));
				// alle volgende zetten hierna
				this.speelBeurt();
			}
			System.out.println(Taal.vertaal("spelEinde"));
			dc.bepaalWinnaar();
			System.out.println(
					Taal.vertaal("winnaarIs") + dc.winnaarsNaam() + Taal.vertaal("scoreVan") + dc.winnaarsScore());
			this.menu();

		} catch (beurtException e) {
			String error = e.getMessage();
			String[] waardes = error.split(",");
			String steen = waardes[0];
			String h = waardes[1];
			String v = waardes[2];
			System.out.printf("%s %s(%s,%s)", Taal.vertaal("beurtFout"), steen, h, v);
			{
			}
		} catch (IllegalArgumentException e) {
			System.out.println(Taal.vertaal(e.getMessage()));
		} catch (Exception e) {
			System.out.println(Taal.vertaal(e.getMessage()));
		}
	}

	/**
	 * Spelen van een beurt.
	 */
	public void speelBeurt() {
		while (!dc.isEindeBeurt()) {// alle volgende zetten hierna
			System.out.println(dc.getPot());
			this.speelZet();
		}
		System.out.println(dc.toonScoreblad());
	}

	/**
	 * Het zetten van een steentje op het spelbord.
	 */
	private void speelZet() {
		try {
			input.nextLine();
			System.out.println(Taal.vertaal("volgendeZet"));
			String zet = input.next();
			dc.speelZet(zet);

		} catch (beurtException e) {
			String error = e.getMessage();
			String[] waardes = error.split(",");
			String steen = waardes[0];
			String h = waardes[1];
			String v = waardes[2];
			System.out.printf("%s %s(%s,%s)", Taal.vertaal("beurtFout"), steen, h, v);
			{
			}
		} catch (kansException e) {
			System.out.println(Taal.vertaal(e.getMessage()));
			this.speelZet();
		}
	}

}