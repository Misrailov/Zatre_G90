package testen;

import domein.*;
import org.junit.jupiter.api.BeforeEach;
import taal.Taal;

import java.util.List;

/**
 * SpelTest bevat testen voor de werking van het spel.
 */
class SpelTest {

	private Spel spel;
	private Speler s1;
	private Speler s2;
	private Pot pot;
	private SpelBord spelbord;
	private DomeinController dc;
	public static Taal taal;
	private List<Speler> spelers;

	@BeforeEach
	public void before() {
		s1 = new Speler("tester1", 2001);
		s2 = new Speler("tester2", 2001);
		spelers.add(s1);
		spelers.add(s2);
		spel = new Spel(spelers);
		pot = new Pot();
		spelbord = new SpelBord();
		dc = new DomeinController();
		taal = new Taal("nl");
	}


}
