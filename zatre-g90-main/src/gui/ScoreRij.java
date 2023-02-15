package gui;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * ScoreRij stelt de rijen van ScoreBord in.
 */
public class ScoreRij {
    private SimpleIntegerProperty x2;
    private SimpleIntegerProperty tien;
    private SimpleIntegerProperty elf;
    private SimpleIntegerProperty twaalf;
    private SimpleIntegerProperty bonus;
    private SimpleIntegerProperty totaal;

    /**
     * Grafisch instellen van de rijen met de passende score.
     *
     * @param rijnummer Kolom dewelke moet worden ingesteld.
     * @param sc        SpelController-object.
     */
    public ScoreRij(int rijnummer, SpelController sc) {
        this.x2 = new SimpleIntegerProperty(sc.guiDc.getSpelerScoreBladArray()[rijnummer][0]);
        this.tien = new SimpleIntegerProperty(sc.guiDc.getSpelerScoreBladArray()[rijnummer][1]);
        this.elf = new SimpleIntegerProperty(sc.guiDc.getSpelerScoreBladArray()[rijnummer][2]);
        this.twaalf = new SimpleIntegerProperty(sc.guiDc.getSpelerScoreBladArray()[rijnummer][3]);
        this.bonus = new SimpleIntegerProperty(sc.guiDc.getSpelerScoreBladArray()[rijnummer][4]);
        this.totaal = new SimpleIntegerProperty(sc.guiDc.getSpelerScoreBladArray()[rijnummer][5]);
    }


    public void setX2(int x2) {
        this.x2 = new SimpleIntegerProperty(x2);
    }

    public void setTien(int tien) {
        this.tien = new SimpleIntegerProperty(tien);
    }

    public void setElf(int elf) {
        this.elf = new SimpleIntegerProperty(elf);
    }

    public void setTwaalf(int twaalf) {
        this.twaalf = new SimpleIntegerProperty(twaalf);
    }

    public void setBonus(int bonus) {
        this.bonus = new SimpleIntegerProperty(bonus);
    }

    public void setTotaal(int totaal) {
        this.totaal = new SimpleIntegerProperty(totaal);
    }

    public Integer getX2() {
        return x2.get();
    }

    public Integer getTien() {
        return tien.get();
    }

    public Integer getElf() {
        return elf.get();
    }

    public Integer getTwaalf() {
        return twaalf.get();
    }

    public Integer getBonus() {
        return bonus.get();
    }

    public Integer getTotaal() {
        return totaal.get();
    }
}
