package gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import taal.Taal;

/**
 * RegistreerController registreert speler en controleert grafische input.
 */
public class RegistreerController {

	SceneController sc = SceneController.getInstance();
	@FXML
	private TextField gebruikersnaam;

	@FXML
	private TextField geboortejaar;

	@FXML
	private Button Registreer;
	@FXML
	private Button KeerTerug;
	@FXML
	private Text errorLabel;

	@FXML
	
	/**
	 * Registreren van nieuwe spelers plus exception handling in GUI.
	 */
	void registreerPanel() {
		//registreren van nieuwe spelers + exception handling in GUI
		errorLabel.setText("");

		if (KeerTerug.isPressed()) {
			sc.toonScene("Menu");
		} else if (Registreer.isPressed()) {

			try {
				int gbj = 0;
				if (geboortejaar.getText().isEmpty()) {
					if (geboortejaar.getText().isEmpty() && gebruikersnaam.getText().isEmpty()) {
						throw new Exception("gui.gebEnJaarLeeg");
					} else {
						throw new Exception("gui.geboortejaarEmpty");
					}

				}else {
					try {
						gbj = Integer.parseInt(geboortejaar.getText());
					} catch (Exception e) {
						throw new Exception("gui.gbjNotInt");
					}
				
					MenuController.dc.registreer(gebruikersnaam.getText(), gbj);
					errorLabel.setText(Taal.vertaal("gui.geregistreerd"));
					errorLabel.setFill(Color.BLACK);}
			} catch (Exception e) {

				errorLabel.setText(Taal.vertaal(e.getMessage()));
				errorLabel.setFill(Color.RED);
			}

			gebruikersnaam.clear();
			geboortejaar.clear();
		}

	}

}
