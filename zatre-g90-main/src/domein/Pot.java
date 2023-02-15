package domein;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pot is verantwoordelijk voor de creatie van 121 stenen die in het spel gebruikt worden.
 */

public class Pot {
    //algemene collectie van stenen die op het bord komt
    private final List<Integer> stenenCollectie;
    int genomenSteen;

    /**
     * Constructor voor maken van de collectie van stenen.
     */
    public Pot() {
        //maakt de collectie van stenen die op het bord komt
        stenenCollectie = new ArrayList<Integer>();
        for (int i = 2; i <= 6; i++) {
            for (int j = 0; j < 20; j++) {
                stenenCollectie.add(i);
            }
        }
        for (int i = 0; i < 21; i++) {
            stenenCollectie.add(1);
        }
    }

    /**
     * Neemt een steen uit de collectie met een willekeurige waarde.
     *
     * @return Net genomen steen.
     */
    //Speler neemt een willekeurige steen hiermee
    public int neemSteen() {
        //u neemt een willekeurig steen uit de collectie, genomensteen wordt deze steen, deze steen wordt uit stenencollectie gehaald
        SecureRandom sr = new SecureRandom();
        int willekeurigGetal = sr.nextInt(stenenCollectie.size());
        genomenSteen = stenenCollectie.get(willekeurigGetal);
        stenenCollectie.remove(willekeurigGetal);
        return genomenSteen;
    }

    /**
     * Legt een steen terug in de collectie.
     *
     * @param steen Het steen dat teruggelegd wordt.
     */
    //een speler mag een steen terugleggen, hiervoor is deze methode
    public void steenInCollectie(int steen) {
        stenenCollectie.add(steen);
        Collections.sort(stenenCollectie);
    }

    public int getGenomenSteen() {
        return genomenSteen;
    }

    public List<Integer> getStenenCollectie() {
        Collections.sort(stenenCollectie);
        return stenenCollectie;
    }


}
