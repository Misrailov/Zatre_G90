package gui;

import domein.DomeinController;
import exceptions.kansException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import taal.Taal;

/**
 * MenuController geeft een keuzemenu die bestaat uit: speler registreren,
 * selecteren en spel starten.
 */
public class MenuController {
	SceneController sc = SceneController.getInstance();
	public static DomeinController dc = new DomeinController();

	SpelController spelController;
	@FXML
	private Button Registreren;

	@FXML
	private Button Selecteren;

	@FXML
	private Button spelStart;

	@FXML
	private Text error;

	@FXML
	/**
	 * Keuzescherm om spelers te selecteren, registreren of het spel te starten.
	 */
	void keuzePanel() {
		// als Registreren wordt geklikt, dan wordt de scene getoond om een gebruiker te
		// registreren
		if (Registreren.isPressed()) {
			sc.toonScene("RegistreerGebruiker");

		}
		// als Selecteren wordt geklikt, dan wordt de scene getoond om gebruikers te
		// selecteren
		else if (Selecteren.isPressed()) {
			sc.toonScene("SelecterenGebruiker");
		}
		// als Start Spel wordt geklikt, dan wordt controle gedaan of er minstens 2
		// spelers zijn geselecteerd
		else if (spelStart.isPressed()) {
			if (MenuController.dc.aantalSpelers() < 2) {
				error.setText(Taal.vertaal("gui.minstensTweeSpelers"));
				error.setFill(Color.RED);

			}
			// als Selecteren wordt geklikt, dan wordt de scene getoond om gebruikers te
			// selecteren
			else if (Selecteren.isPressed()) {
				sc.toonScene("SelecterenGebruiker");
			}
			// als Start Spel wordt geklikt, dan wordt controle gedaan of er minstens 2
			// spelers zijn geselecteerd
			else if (spelStart.isPressed()) {

				if (MenuController.dc.aantalSpelers() < 2) {
					error.setText(Taal.vertaal("gui.minstensTweeSpelers"));
					error.setFill(Color.RED);
				}
				// Indien min. 2 spelers geselecteerd, wordt de scene getoond die het spel
				// weergeeft (spelbord, pot, scorebord)
				else {
					try {
						spelController = new SpelController(dc);
						System.out.println(Taal.vertaal("gui.spelGestart"));
						spelController.controller();
						sc.setScene(new Scene(spelController.getBorder(), 750, 750));
						sc.stage.setMaximized(true);
					} catch (kansException e) {
						error.setText(Taal.vertaal(e.getMessage()));
						error.setFill(Color.RED);
					}

				}

			}
		}

	}
}
