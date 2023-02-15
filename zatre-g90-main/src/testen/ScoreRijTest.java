package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.ScoreRij;

/**
 * ScoreRijTest bevat testen voor ScoreRij.
 */
class ScoreRijTest {
	
	private ScoreRij scoreRij;

	@BeforeEach
	public void before() {
		scoreRij = new ScoreRij();
	}
	
	@Test
	public void veranderWaarde_Hetzelfde() {
		scoreRij.veranderWaarde(1, 5);
		Assertions.assertEquals(5, scoreRij.getWaarde(1));
	}
	
	@Test
	public void getwaarde_Hetzelfde() {
		scoreRij.veranderWaarde(2, 5);
		Assertions.assertEquals(5, scoreRij.getWaarde(2));
	}
	
	@Test
	public void isDoorKruist_GevalNul() {
		Assertions.assertEquals(0, scoreRij.getWaarde(0));
	}
	
	@Test
	public void isDoorKruist_GevalVakHetzelfde() {	
		scoreRij.veranderWaarde(1, 5);
		Assertions.assertEquals(5, scoreRij.getWaarde(1));
	}
	
	@Test
	public void isDoorKruist_GevalMinusOne() {
		scoreRij.veranderWaarde(3, -1);
		Assertions.assertEquals(-1, scoreRij.getWaarde(3));
	}

}
