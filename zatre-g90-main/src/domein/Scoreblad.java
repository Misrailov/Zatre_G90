package domein;

import java.util.ArrayList;
import java.util.List;


/**
 * Scoredblad vertegenwoordigt het scoreblad van Zatre.
 */

public class Scoreblad {
    private final List<ScoreRij> scoreBlad = new ArrayList<>();

    /**
     * Constructor voor scorerijen toevoegen aan scoreblad.
     */
    public Scoreblad() {
        // scoreblad 26 rijen aan(x) van 6 Scorerijen(y)
        for (int i = 0; i < 25; i++) {
            scoreBlad.add(new ScoreRij());
        }
        bonusTabel();
    }

    /**
     * Wijzigd waarden in het scoreblad.
     *
     * @param positie Positie in het scoreblad, van 0 tot 25.
     * @param waarde  De score.
     * @param tabel   De tabel, van 0 tot 5.
     */
    // deze methode wordt gebruikt om een waarde in de scoreblad te wijzigen.
    public void veranderWaarde(int positie, int waarde, int tabel) {
        // positie moet van 0 tot 25, tabel 0-5 dus x2/10/11/12...
        if ((positie < 0) || (positie > 25) || (tabel > 5) || (tabel < 0)) {
            throw new IllegalArgumentException("positie of tabel is incorrect");
        } else
            scoreBlad.get(positie).veranderWaarde(tabel, waarde);
    }

    /**
     * De bonustabel van het scoreblad, zorgt dat naargelang de positie in bonus vak het juiste cijfer er komt.
     */
    private void bonusTabel() {
        // zorgt dat naargelang de positie in bonus vak het juiste cijfer er komt,
        // eerste 4=3, tweede 4=4

        for (int i = 0; i < 25; i++) {
            if (this.getScoreblad().get(i).getWaarde(1) != 0 && this.getScoreblad().get(i).getWaarde(2) != 0
                    && this.getScoreblad().get(i).getWaarde(3) != 0) {
                if (i <= 3) {
                    veranderWaarde(i, 3, 4);
                }
                if ((i > 3) && (i <= 7)) {
                    veranderWaarde(i, 4, 4);

                }
                if ((i > 7) && (i <= 11)) {
                    veranderWaarde(i, 5, 4);
                }
                if ((i > 11) && (i <= 15)) {
                    veranderWaarde(i, 6, 4);
                }
                if ((i > 15) && (i <= 19)) {
                    veranderWaarde(i, 7, 4);
                }
                if (i > 21) {
                    veranderWaarde(i, 8, 4);
                }
            }

        }
    }

    /**
     * Berekent de totale score.
     *
     * @return Totale score.
     */
    public int totaalScore() {
        int totaal = 0;
        for (int rijNummer = 0; rijNummer < this.scoreBlad.size(); rijNummer++) {
            scoreBlad.get(rijNummer).totaalRij();
        }
        for (ScoreRij rij : this.scoreBlad) {
            totaal += rij.getWaarde(5);
        }
        return totaal;
    }

    /**
     * Geeft de scoreblad weer.
     *
     * @return
     * @return Scoreblad in CLI.
     * =======
     * Geeft het scoreblad weer.
     */
    public String toonScoreblad() {
        String Scoreblad = "| x2 | 10 | 11 | 12 | BON| SUM|";
        for (ScoreRij rij : this.scoreBlad) {
            rij.totaalRij();
            Scoreblad += "\n";
            for (int score = 0; score < 6; score++) {
                int waarde = rij.getWaarde(score);
                switch (score) {
                    case (0): {
                        if (waarde != 0)
                            Scoreblad += "|  x ";
                        else
                            Scoreblad += "|    ";
                        break;
                    }
                    case (1): {
                        if (waarde != 0)
                            Scoreblad += "|  " + waarde + " ";
                        else
                            Scoreblad += "|    ";
                        break;
                    }
                    case (2): {
                        if (waarde != 0)
                            Scoreblad += "|  " + waarde + " ";
                        else
                            Scoreblad += "|    ";
                        break;
                    }
                    case (3): {
                        if (waarde != 0)
                            Scoreblad += "|  " + waarde + " ";
                        else
                            Scoreblad += "|    ";
                        break;
                    }
                    case (4): {
                        if (rij.getWaarde(1) != 0 && rij.getWaarde(2) != 0 && rij.getWaarde(3) != 0) {
                            Scoreblad += "|  " + waarde + " ";
                        } else
                            Scoreblad += "|    ";
                        break;
                    }
                    case (5): {

                        Scoreblad += "|  " + waarde + " |";
                        break;
                    }

                }
            }
        }
        Scoreblad += "\n| TOTAALSCORE: " + this.totaalScore() + " |";
        return Scoreblad;
    }


    public List<ScoreRij> getScoreblad() {
        // retourneert een lijst met 26 items van Scorerij waarin elk 6 getallen zitten
        return scoreBlad;
    }

    /**
     * Geeft een dubele score
     */
    public void scoreDubbel() {
        int i = this.volgendeLeeg(0);
        this.veranderWaarde(i, 1, 0);
        System.out.println("x2 gescoord");
    }

    /**
     * Geeft de volgende lege rij, anders maakt een nieuwe rij en geeft rijnummer terug.
     *
     * @param kolom Nummer kolom.
     * @return Maakt een nieuwe rij in de scoreblad als er geen lege is.
     */
    private int volgendeLeeg(int kolom) {
        for (int rijnummer = 0; rijnummer < this.scoreBlad.size(); rijnummer++) { // itereren over rijen van scoreblad
            if (this.scoreBlad.get(rijnummer).getWaarde(kolom) == 0)
                return rijnummer; // als kolom i leeg is in rij, rijnummer teruggeven
        }
        this.scoreBlad.add(new ScoreRij()); // na volledig doorlopen nog geen return= alle rijen gevuld. Nieuwe rij
        // toevoegen.
        return this.scoreBlad.size() - 1; // rijnummer van nieuwe rij teruggeven
    }

    /**
     * Zoekt de scores van 10 en vult ze in, in de scoreblad
     *
     * @param score10 True als er al 10 gescoord is, anders false
     */
    public void score10(boolean score10) {
        if (!score10) { // als er nog geen 10 gescoord is
            int i = this.volgendeLeeg(1); // zoek volgende lege vak 10 (index 1)
            this.veranderWaarde(i, 1, 1); // verander de waarde van gegeven vak in 1.
        } else { // als er wel al 10 gescoord is
            int i = this.volgendeLeeg(1) - 1; // zoek eerste vak voor het lege (laatst ingevulde)
            this.veranderWaarde(i, this.scoreBlad.get(i).getWaarde(1) + 1, 1); // haal de waarde op uit dit vak, en voeg
            // er 1 bij toe.
        }
        System.out.println("10 gescoord.");
    }

    /**
     * Zoekt de scores van 11 en vult ze in, in het scoreblad
     *
     * @param score11 True als er al 11 gescoord is, anders false
     */
    public void score11(boolean score11) {
        if (!score11) { // zie score10, zelfde werkwijze
            int i = this.volgendeLeeg(2);
            this.veranderWaarde(i, 1, 2);
        } else {
            int i = this.volgendeLeeg(2) - 1;
            this.veranderWaarde(i, this.scoreBlad.get(i).getWaarde(2) + 1, 2);
        }
        System.out.println("11 gescoord.");
    }

    /**
     * Zoekt de scores van 12 en vult ze in, in het scoreblad
     *
     * @param score12 True als er al 12 gescoord is, anders false
     */
    public void score12(boolean score12) {
        if (!score12) { // zie score10, zelfde werkwijze
            int i = this.volgendeLeeg(3);
            this.veranderWaarde(i, 1, 3);
        } else {
            int i = this.volgendeLeeg(3) - 1;
            this.veranderWaarde(i, this.scoreBlad.get(i).getWaarde(3) + 1, 3);
        }
        System.out.println("12 gescoord.");
    }
}
