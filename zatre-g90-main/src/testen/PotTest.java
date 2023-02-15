package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Pot;
import domein.Spel;
import domein.SpelBord;
import domein.Speler;

/**
 * PotTest bevat testen voor Pot.
 */
class PotTest {
	
	private Pot pot;
	private Pot pot1;
	private Pot pot2;
	private Spel spel;
	private SpelBord bord;

	@BeforeEach
	public void before() {
		pot = new Pot();
		pot1 = new Pot();
		pot2= new Pot();
		
	}
	
	@Test
	public void neemSteen_IsTelkensWillekeurig() {
		/*deze test kan soms falen want er bestaat
		een kleine kans dat de stenen per toeval gelijk zijn aan elkaar
		echter bewijst deze test nog altijd dat de aarde van willekeur werkt*/
		Assertions.assertNotEquals(pot.neemSteen(), pot.neemSteen());
	}
	
	@Test
	public void neemSteen_NeemtEffectiefSteenUitCollectie() {
		pot.neemSteen();
		Assertions.assertNotEquals(pot1.getStenenCollectie(), pot.getStenenCollectie());	
	}
	
	@Test
	public void steenInCollectie_SteekSteenTerugEnVergelijkMetOorsprongCollectie() {
		pot2.neemSteen();
		pot2.steenInCollectie(pot2.getGenomenSteen());	
		Assertions.assertEquals(pot1.getStenenCollectie(), pot2.getStenenCollectie());
	}

}
