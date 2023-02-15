package gui;

import domein.DomeinController;
import exceptions.kansException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import taal.Taal;

import java.util.List;
import java.util.Objects;

/**
 * SpelController bevat de logica om alle functionaliteiten van het spel weer te
 * geven in 1 scherm.
 */
public class SpelController {
	gui.Bord bord;
	gui.ScoreBord sb;
	gui.PotController pc;
	BorderPane border;
	SceneController sc;
	DomeinController guiDc;

	/**
	 * Aanmaken van een nieuwe potcontroller, nieuw bord en een scorebord. Linken
	 * van de domeincontroller aan de spelcontroller. Opstarten van de logica voor
	 * het spel en de 1e beurt in de gelinkte domeincontroller.
	 *
	 * @param dc Domeincontroller-object.
	 * @throws Exception
	 */
	public SpelController(DomeinController dc) throws kansException {
		sc = SceneController.getInstance();
		this.guiDc = dc;
		guiDc.startSpel();
		guiDc.startBeurt();
		pc = new PotController();
		bord = new Bord(this);
		sb = new ScoreBord(this);

	}

	/**
	 * Verwijdert de steen die je net hebt geplaatst op het bord.
	 */
	public void verwijderLaatsteSteen() {
		pc.verwijderGeselecteerdeSteen();
		setGeSelecteerdeSteen(0);
		System.out.println(this.getGezetteTiles());

	}

	public void setGeSelecteerdeSteen(int steen) {
		pc.setGeselecteerdeSteen(steen);
	}

	public List<Rectangle> getGezetteTiles() {
		return pc.getGezetteTiles();
	}

	/**
	 * Roept updatePotInGui() aan uit PotController.
	 */
	public void updatePotInGui() {
		pc.updatePotInGui();
	}

	public int getSelecteerdeSteen() {
		return pc.getGeselecteerdeSteen();
	}

	/**
	 * Aanmaken van borderpane en functionaliteiten erop zetten.
	 */
	public void controller() {
		this.border = new BorderPane();
		border.setLeft(linkerkant());

		border.setCenter(midden());
		border.setRight(rechterkant());
		border.setBottom(berichtScherm("gui.extraInfo"));

	}

	/**
	 * Updaten van borderpane doorheen het spel.
	 */
	public void updateController() {
		this.border.setCenter(midden());
		this.border.setRight(rechterkant());

		this.border.setLeft(linkerkant());
	}

	public BorderPane getBorder() {
		return border;
	}

	/**
	 * Instellen van de linkerkant op de borderpane.
	 *
	 * @return Pane met functionaliteiten.
	 */
	public Pane linkerkant() {
		Pane linker = new Pane();
		sb.tekenScoreBord();
		// linker.setPrefSize(500, 300);
		linker.setMaxSize(500, 300);
		linker.getChildren().add(sb.getScoreBord());

		return linker;
	}

	/**
	 * Instellen van het midden op de borderpane.
	 *
	 * @return Pane met functionaliteiten.
	 */
	public Pane midden() {
		Pane midden = new Pane();
		// linker.setPrefSize(500, 300);

		bord.tekenBord();

		midden.getChildren().add(bord.getGridSpelBord());
		return midden;
	}

	/**
	 * Creert een vak waarin een bericht kan weergegeven worden.
	 * 
	 * @param bericht Dit zal het bericht zijn dat weergegeven wordt.
	 * @return Het vak waarin het bericht zit.
	 */
	public Pane berichtScherm(String bericht) {
		Pane berichtScherm = new Pane();
		String zin = Taal.vertaal(bericht);
		Text zinVak = new Text();
		zinVak.setText(zin);

		zinVak.setFont(Font.font("Helvetica", 30));
		berichtScherm.getChildren().add(zinVak);

		return berichtScherm;
	}

	/**
	 * Nuttige informatie wordt weergegeven om te helpen bij het spelen van het
	 * spel.
	 * 
	 * @param key Dit is de key die gaat gebruikt worden voor de vertaling van de
	 *            tekst.
	 */
	public void updateTekst(String key) {
		System.out.println("methode updateTekst met String key werkt");
		Pane berichtScherm = new Pane();
		String zin = Taal.vertaal(key);
		Text zinVak = new Text();
		zinVak.setText(zin);
		zinVak.setFill(Color.RED);

		zinVak.setFont(Font.font("Helvetica", 30));
		berichtScherm.getChildren().add(zinVak);

		this.border.setBottom(berichtScherm);
	}

	/**
	 * Nuttige informatie wordt weergegeven om te helpen bij het spelen van het
	 * spel.
	 * 
	 * @param extraInfo Dit is extra informatie zoals spelernaam.
	 * @param key       Dit is de key die gaat gebruikt worden voor de vertaling van
	 *                  de tekst.
	 */

	public void updateTekst(String extraInfo, String key) {
		System.out.println("methode updateTekst met String key werkt en extraInfo werkt");
		Pane berichtScherm = new Pane();
		String zin = "";
		if (key.equals("gui.foutZetSteen")) {
			String error = extraInfo;
			String[] waardes = error.split(",");
			String steen = waardes[0];
			String x = waardes[1];
			String y = waardes[2];
			extraInfo = String.format("%s %s%s,%s(%s,%s)", Taal.vertaal(key), Taal.vertaal("steen"), steen,
					Taal.vertaal("coordinaten"), x, y);
			zin = extraInfo;
		} else {
			zin = Taal.vertaal(key);
		}
		Text zinVak = new Text();
		zinVak.setText(zin);
		zinVak.setFill(Color.RED);

		zinVak.setFont(Font.font("Helvetica", 30));
		berichtScherm.getChildren().add(zinVak);

		this.border.setBottom(berichtScherm);
	}

	/**
	 * Instellen van de rechterkant op de borderpane.
	 *
	 * @return Pane met functionaliteiten.
	 */
	public Pane rechterkant() {
		Pane rechter = new Pane();
		// rechter.setPrefSize(500, 300);
		pc.SpelerPot(this);
		rechter.getChildren().add(pc.getPot());
		return rechter;
	}

	/**
	 * Ophalen van image uit map.
	 *
	 * @param steen Steentje waarvan de image moet worden gezocht.
	 * @return Een grafische image van het steentje
	 */
	public Image ophalenImage(int steen) {
		return new Image(getClass().getResource("/images/d" + steen + ".jpg").toExternalForm());

	}

	/**
	 * Instellen van het vakje met de grafische steen.
	 *
	 * @param img  Image verkregen uit ophalenImage().
	 * @param tile Vakje waarop de image moet worden gezet.
	 */
	public void zetSteen(Image img, Rectangle tile) {
		tile.setFill(new ImagePattern(img));

	}

	/**
	 * Creert en toont een scherm waarbij de winnaar wordt weergegeven met de nodige
	 * informatie.
	 */
	public void winnaarScherm() {
		GridPane winnaarScherm = new GridPane();
		String winnaarTekst = Taal.vertaal("winnaarSpel") + guiDc.winnaarsNaam() + Taal.vertaal("scoreWinnaar")
				+ guiDc.winnaarsScore() + ", " + Taal.vertaal("speelkansendeclaratie") + " "
				+ guiDc.winnaarsSpeelKansen();
		Text winnaarTekstVak = new Text();
		winnaarTekstVak.setText(winnaarTekst);
		winnaarTekstVak.setFont(Font.font("Helvetica", 50));
		TextFlow winnaarTextFlow = new TextFlow(winnaarTekstVak);
		winnaarTextFlow.setTextAlignment(TextAlignment.CENTER);
		winnaarScherm.add(winnaarTextFlow, 0, 0);

		GridPane table = new GridPane();
		while (!Objects.equals(guiDc.getSpelerNaam(), guiDc.winnaarsNaam())) {
			guiDc.volgendeSpeler();
		}
		sb.tekenScoreBord();
		table.add(sb.getScoreBord(), 0, 1);
		System.out.println(guiDc.toonScoreblad());
		winnaarScherm.add(table, 0, 1);
		table.setAlignment(Pos.CENTER);

		Button hoofdmenu = new Button(Taal.vertaal("gui.menuknop"));
		hoofdmenu.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				try {
					guiDc.resetGeselecteerdeSpelers();
					sc.toonScene("Menu");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});

		winnaarScherm.add(hoofdmenu, 0, 3);
		this.border.getChildren().removeAll(this.border.getChildren());
		this.border.setTop(winnaarScherm);

	}

}