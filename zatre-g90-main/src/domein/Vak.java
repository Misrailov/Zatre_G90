package domein;

/**
 * Vak vertegenwoordigt de werking van een vak op het spelbord.
 */
public class Vak {
    private int type; // 0 is muur, 1 is normaal vak, 2 is grijs vak
    private boolean heeftSteen;
    private int waarde;


    /**
     * Controle of een steen op het vakje staat.
     *
     * @return True als er een steen op staat | False als er geen steen op staat.
     */
    public boolean heeftSteen() {
        return heeftSteen;
    }

    public void setHeeftSteen(boolean heeftSteen) {
        this.heeftSteen = heeftSteen;
    }

    public int getWaarde() {
        return waarde;
    }

    public void setWaarde(int cijfer) {
        this.waarde = cijfer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
