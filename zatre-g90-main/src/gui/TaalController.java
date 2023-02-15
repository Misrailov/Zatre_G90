package gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * TaalController leidt de keuze van de gewenste taal in goede banen.
 */
public class TaalController {
    String lang;
    SceneController sc = SceneController.getInstance();

    @FXML
    private Button nl;

    @FXML
    private Button en;


    @FXML
    /**
     * Instellen van de taal in de GUI.
     */
    void SetTaal() {

        if (nl.isPressed()) {
//			sc.setTaal("nl");
            this.lang = "nl";

        } else if (en.isPressed()) {
//			sc.setTaal("en");
            this.lang = "en";
        }
        sc.setTaal(lang);
        sc.toonScene("Menu");
    }


}
