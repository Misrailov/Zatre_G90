package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import taal.Taal;

/**
 * SelecterenController leidt de selectie van spelers in goede banen.
 */
public class SelecterenController {
    SceneController sc = SceneController.getInstance();
    @FXML
    private TextField gebruikersnaam;

    @FXML
    private TextField geboortejaar;
    @FXML
    private Button Selecteren;
    @FXML
    private Text errorLabel;

    @FXML
    private Button KeerTerug;


    @FXML
    /**
     * Selecteren van spelers om het spel te kunnen starten plus GUI exception handling.
     */
    void selecteerPanel() {
        //selecteren van spelers om het spel te kunnen starten + GUI exception handling
        errorLabel.setText("");
        if (KeerTerug.isPressed()) {
            sc.toonScene("Menu");
        } else if (Selecteren.isPressed()) {

            try {
                int gbj = 0;
                if (gebruikersnaam.getText().isEmpty()) {
                    if (geboortejaar.getText().isEmpty() && gebruikersnaam.getText().isEmpty()) {
                        throw new Exception("gui.gebEnJaarLeeg");
                    } else {
                        throw new Exception("naamLeeg");
                    }
                } else if (geboortejaar.getText().isEmpty()) {
                    if (geboortejaar.getText().isEmpty() && gebruikersnaam.getText().isEmpty()) {
                        throw new Exception("gui.gebEnJaarLeeg");

                    } else if (geboortejaar.getText().isEmpty()) {
                        throw new Exception("gui.geboortejaarEmpty");
                    }
                } else {
                    try {
                        gbj = Integer.parseInt(geboortejaar.getText());
                    } catch (Exception e) {
                        throw new Exception("gui.gbjNotInt");
                    }

                    MenuController.dc.selecteerSpeler(gebruikersnaam.getText(), gbj);
                    errorLabel.setText(Taal.vertaal("gui.geselecteerd"));
                    errorLabel.setFill(Color.BLACK);
                }
            } catch (Exception e) {
                errorLabel.setText(Taal.vertaal(e.getMessage()));
                errorLabel.setFill(Color.RED);
            }
            gebruikersnaam.clear();
            geboortejaar.clear();
        }

    }
    public void deselecteren(){
        MenuController.dc.resetGeselecteerdeSpelers();

    }
}
