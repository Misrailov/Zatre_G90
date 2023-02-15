package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Vak;
//Testen Correct
/**
 * VakTest bevat testen voor een vak.
 */
public class VakTest {
	
	private Vak vak;
	
	@BeforeEach
	public void before() {
		vak = new Vak();
	}
	
	@Test
	public void heeftSteen_True() {
		vak.setHeeftSteen(true);
		Assertions.assertTrue(vak.heeftSteen());
	}
	
	@Test
	public void heeftSteen_False() {
		vak.setHeeftSteen(false);
		Assertions.assertFalse(vak.heeftSteen());
	}
	
	@Test
	public void getCijfer_Hetzelfde() {
		vak.setWaarde(5);
		Assertions.assertEquals(5, vak.getWaarde());
	}
	
	@Test
	public void getType_Hetzelfde() {
		vak.setType(0);
		Assertions.assertEquals(0, vak.getType());
	}
	

}


