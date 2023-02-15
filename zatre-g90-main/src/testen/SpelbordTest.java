package testen;

import domein.SpelBord;
import domein.Vak;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * SpelbordTest bevat testen voor het spelbord.
 */
class SpelbordTest {

	private SpelBord spelBord;
	private Vak vak;
	private SpelBord sb;
	private SpelBord sb1;

	@BeforeEach
	public void before() {
		sb = new SpelBord();
		vak = new Vak();
	}
	//eerste kijken of we ergens kunnen plaatsen
	@Test
	public void kanPlaatsen_True() {
		Assertions.assertTrue(sb.kanPlaatsen(4, 5));
	}
	
	@Test
	public void kanPlaatsen_Exception() {
		sb.zetSteen(4, 5, 2);
		Assertions.assertThrows(IllegalArgumentException.class,() -> sb.kanPlaatsen(4, 5));
	}
	//dan zetten we daar een steen en
	//controleren we of die steen correct gezet werd
	@Test
	public void zetSteen_heeftSteen() {
		sb.zetSteen(4, 5, 2);
		Assertions.assertTrue(sb.heeftSteen(4, 5));
	}
	
	@Test
	public void heeftBuur_False() {
		sb.zetSteen(4, 5, 2);
		Assertions.assertFalse(sb.heeftBuur(4, 5));
	}
	
	@Test 
	public void heeftBuur_True() {
		sb.zetSteen(4, 5, 2);
		sb.zetSteen(4, 4, 2);
		Assertions.assertTrue(sb.heeftBuur(4, 5));
	}

}
