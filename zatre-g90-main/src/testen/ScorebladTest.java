package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Scoreblad;

/**
 * ScorebladTest bevat testen voor het scoreblad.
 */
class ScorebladTest {
	private Scoreblad sbl;
	
	@BeforeEach
	public void before() {	
		sbl = new Scoreblad();		
	}
	
	@Test 
	public void veranderWaarde_Exception() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> sbl.veranderWaarde(1, 5, 99));
	}
	
	@Test
	public void veranderWaarde_SuccesvolVeranderdPositie1() {
		sbl.veranderWaarde(2, 4, 1);
		Assertions.assertEquals(4, sbl.totaalScore());
	}
	
	@Test
	public void veranderWaarde_SuccesvolVeranderdPositie2Gedubbeld() {
		sbl.veranderWaarde(2, 4, 2);
		Assertions.assertEquals(8, sbl.totaalScore());
	}
	
	@Test
	public void veranderWaarde_SuccesvolVeranderdPositie3Getrippled() {
		sbl.veranderWaarde(2, 4, 3);
		Assertions.assertEquals(12 ,sbl.totaalScore());
	}
	
	@Test
	public void scoreDubbel() {
		sbl.veranderWaarde(0, 4, 1);
		sbl.scoreDubbel();
		Assertions.assertEquals(8, sbl.totaalScore());
	}

	
}
