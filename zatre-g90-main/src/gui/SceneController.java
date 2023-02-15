package gui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import taal.Taal;

/**
 * SceneController zet de gevraagde scene op de stage.
 */
public class SceneController {
    static SceneController obj = new SceneController();
    public Taal taal;

    Stage stage;


    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Het laden van FXML files en instellen als scene.
     *
     * @param naam Naam van het FXML bestand.
     */
    public void toonScene(String naam) {


        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource(naam + ".fxml"), Taal.getResourceBundle());

            Parent mainPane = loader.load();

            Scene scene = new Scene(mainPane);
            stage.setScene(scene);

            stage.setTitle("Zatre G90");

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Instellen van de stage.
     *
     * @param stage De in te stellen stage.
     */
    public void instellenStage(Stage stage) {

        this.stage = stage;
    }

    /**
     * Instance opvragen van object.
     *
     * @return Instance van object.
     */
    public static SceneController getInstance() {

        return obj;
    }

    public void setTaal(String lang) {
        this.taal = new Taal(lang);
    }
}
