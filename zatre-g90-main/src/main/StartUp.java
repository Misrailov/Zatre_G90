package main;


import gui.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * StartUp leidt opstart van de console applicatie in goede banen.
 */
public class StartUp extends Application {
	/**
	 * Starten van de console applicatie.
	 */
	SceneController sc = SceneController.getInstance();

	@Override
	/**
	 * Starten van de grafische applicatie.
	 */
	public void start(Stage stage) {
		//

		sc.instellenStage(stage);
		sc.toonScene("TaalKeuze");
	}

	public static void main(String[] args) {

		launch(args);
		
	}

}
