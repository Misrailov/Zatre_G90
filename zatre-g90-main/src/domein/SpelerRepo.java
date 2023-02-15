package domein;

import persistentie.SpelerMapper;

import java.util.List;

/**
 * SpelerRepo is verantwoordelijk voor de controle en wijziging van spelers.
 */
public class SpelerRepo {
    private final SpelerMapper map;

    /**
     * Constructor voor aanmaak nieuwe spelermapper.
     */
    public SpelerRepo() {
        map = new SpelerMapper();
    }

    /**
     * Roept voegSpelerToe() aan uit SpelerMapper na controle dat speler nog niet bestaat.
     *
     * @param speler De speler die geregistreerd moet worden.
     */
    public void voegSpelerToe(Speler speler) {

        if ((bestaatSpeler(speler.getGebruikersnaam(), speler.getJaar()))) {
            throw new IllegalArgumentException("bestaatAl");
        } else {
            map.voegSpelerToe(speler);
        }
    }

    /**
     * Roept slaAantalSpellenOp() aan uit SpelerMapper.
     *
     * @param s De speler waarvan het aantal spellen moet gewijzigd worden.
     */
    public void spelerKansenAanpassen(Speler s) {
        map.slaAantalSpellenOp(s);
    }

    /**
     * Controleren of de speler gekend is in de database.
     *
     * @param gebruikersnaam De gebruikersnaam van de te controleren speler.
     * @param geboortejaar   Het geboortejaar van de te controleren speler.
     * @return Boolean retourneert true als de speler bestaat of false als de speler niet bestaat.
     */
    public boolean bestaatSpeler(String gebruikersnaam, int geboortejaar) {

        Speler speler = selecteerSpelerMapper(gebruikersnaam, geboortejaar);
        return speler != null && speler.getGebruikersnaam().equals(gebruikersnaam) && speler.getJaar() == geboortejaar;

    }


    /**
     * Roept geefSpeler() op uit SpelerMapper.
     *
     * @param gebruikersnaam Gebruikersnaam van de gewenste speler.
     * @param geboorteJaar   Geboortejaar van de gewenste speler.
     * @return
     */
    public Speler selecteerSpelerMapper(String gebruikersnaam, int geboorteJaar) {
        return map.geefSpeler(gebruikersnaam, geboorteJaar);
    }

    /**
     * Roept geefSpelers() aan uit SpelerMapper.
     *
     * @return Geeft een lijst terug van alle geregistreerde spelers in de database.
     */
    public List<Speler> geefSpelers() {

        return map.geefSpelers();

    }

}
