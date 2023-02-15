package domein;

/**
 * ScoreRij betrekt het Scoreblad in horizontale zin, elk rij heeft 6 vakken.
 */

public class ScoreRij {
    private final int[] rij;
    // elke rij heeft 6 vakken.
    /*
     * rij[0]=x2 vak rij[1]=10 vak rij[2]=11 vak rij[3]=12 vak rij[4]=bonus vak
     * rij[5]=totaal vak
     */

    /**
     * Initialiseert rij.
     */
    public ScoreRij() {
        this.rij = new int[]{0, 0, 0, 0, 0, 0};
    }


    /**
     * Wijzigt de waarde van een positie in de rij.
     *
     * @param positie Positie van 0 tot 5 in rij.
     * @param waarde  Waarde van positie in rij.
     */
    public void veranderWaarde(int positie, int waarde) {
        // waarde van de positie wordt gewijzigd, positie moet 0-5 zijn
        if ((positie > 5) || (positie < 0)) {
            throw new IllegalArgumentException("positie moet 0-5 zijn");
        } else
            this.rij[positie] = waarde;
    }

    public int getWaarde(int positie) {
        // moet van 0-5 zijn
        return this.rij[positie];
    }


    /**
     * Berekent en vult de totale score
     */
    public void totaalRij() {
        // retourneert het totaal van de cijfers
        rij[5] = (rij[0] + 1) * (rij[1] + 2 * rij[2] + 3 * rij[3] + rij[4]);
    }

}
