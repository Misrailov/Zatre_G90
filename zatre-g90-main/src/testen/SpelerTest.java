package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import domein.Speler;

/**
 * SpelerTest bevat testen voor de spelers.
 */
public class SpelerTest {

	private final String naamOK = "test123";
	private final int jaarOK = 2000;
	private final int aantalSpellen = 5;

// constructor testen
	@Test
	public void maakSpeler_alleParOK_creatieObject() {
		Speler s = new Speler(naamOK, jaarOK);

		Assertions.assertEquals(naamOK, s.getGebruikersnaam());
		Assertions.assertEquals(jaarOK, s.getJaar());
		Assertions.assertEquals(aantalSpellen, s.getAantalSpellen());
	}

//naam verkeerd
	@ParameterizedTest
	@ValueSource(strings = { ""," ", "   ", "krt" })
	void maakSpeler_fouteNaam_excp(String naam) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(naam, jaarOK));
	}

//jaar verkeerd
	@ParameterizedTest
	@ValueSource(ints = { 2100, 2017 })
	public void maakSpeler_foutJaar_excp(int jaar) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Speler(naamOK, jaar));
	}

//grensgeval
	@Test
	void maakSpeler_jaarGrens_creatieSpeler() {
		Assertions.assertDoesNotThrow(() -> new Speler(naamOK, 2016));
	}

	// equals testen
	@Test
	void equals_naamEnJaarGelijk_true() {
		Speler s1 = new Speler(naamOK, jaarOK);
		Speler s2 = new Speler(naamOK, jaarOK);
		Assertions.assertTrue(s1.equals(s2));
	}

	@Test
	void equals_naamGelijkJaarVerschillend_false() {
		Speler s1 = new Speler(naamOK, jaarOK);
		Speler s2 = new Speler(naamOK, jaarOK - 1);
		Assertions.assertFalse(s1.equals(s2));
	}

	@Test
	void equals_naamVerschillendJaarGelijk_false() {
		Speler s1 = new Speler(naamOK, jaarOK);
		Speler s2 = new Speler("testfout", jaarOK);
		Assertions.assertFalse(s1.equals(s2));
	}

	@Test
	void equals_naamVerschillendJaarVerschillend_false() {
		Speler s1 = new Speler(naamOK, jaarOK);
		Speler s2 = new Speler("testfout", jaarOK - 1);
		Assertions.assertFalse(s1.equals(s2));
	}


}
