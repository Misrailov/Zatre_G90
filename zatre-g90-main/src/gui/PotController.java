package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import taal.Taal;

import java.util.ArrayList;
import java.util.List;

/**
 * PotController is verantwoordelijk voor grafische voorstelling van persoonlijke pot van de speler.
 */
public class PotController {
    private int geselecteerdeSteen = 0;
    private Rectangle geselecteerdeTile;
    private List<Rectangle> gezetteTiles;
    private final List<Rectangle> tiles = new ArrayList<>();
    GridPane huidigeSpeler;
    GridPane spelersCollectieGrid;
    GridPane pot;
    SpelController sc;

    public int getGeselecteerdeSteen() {
        return geselecteerdeSteen;
    }

    /**
     * Grafische weergave van de persoonlijke pot van de speler, welke speler aan de beurt is en volgende beurt functionaliteit.
     *
     * @param sc SpelController-object.
     * @return Grafische weergave van speel beurt functionaliteiten.
     */
    public void SpelerPot(SpelController sc) {
        this.sc = sc;
        pot = new GridPane();
        //pot.setPrefSize(800, 800);
        pot.setPadding(new Insets(5));

        huidigeSpeler = new GridPane();

        //spelerNaam weergeven met zin
        String spelerNaam = sc.guiDc.getSpelerNaam();
        String naamZin = Taal.vertaal("gui.spelernaam") + spelerNaam;
        Text naamText = new Text();
        naamText.setText(naamZin);
        naamText.setFont(Font.font(15));
        huidigeSpeler.add(new TextFlow(naamText), 0, 0);


        pot.add(huidigeSpeler, 1, 0);
        pot.setVgap(100);
        this.spelersCollectieGrid();
        pot.add(this.getSpelersCollectieGrid(), 1, 3);
        HBox Buttons = new HBox();
        Buttons.getChildren().addAll(stenenTerugButton(), eindeSpelButton());
        pot.add(Buttons, 1, 5);


        pot.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }

    public GridPane getSpelersCollectieGrid() {
        return spelersCollectieGrid;
    }

    public GridPane getPot() {
        return pot;
    }

    /**
     * Buttoncontroller om stenen terug in persoonlijke pot te doen.
     *
     * @return Button om stenen terug in persoonlijke pot te doen.
     */
    public Button stenenTerugButton() {
        Button stenenTerug = new Button(Taal.vertaal("gui.stenenTerugButton"));
        stenenTerug.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {

                try {
                    sc.guiDc.stenenTerug();
                    sc.guiDc.startBeurt();
                    sc.updateController();
                } catch (Exception e) {
                    sc.guiDc.stenenTerug();
                    sc.guiDc.startBeurt();
                    sc.updateController();
                }


            }
        });
        return stenenTerug;
    }

    /**
     * Creert een knop waarmee je het spel kan eindigen.
     *
     * @return Deze knop waarmee je het spel kan eindigen.
     */

    public Button eindeSpelButton() {
        Button eindeSpelButton = new Button(Taal.vertaal("gui.spelEindigenButton"));
        eindeSpelButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    sc.guiDc.spelEinde();
                    sc.guiDc.bepaalWinnaar();
                    sc.winnaarScherm();

                    System.out.println(sc.guiDc.isSpelEinde());
                    System.out.println("Het spel is beëindigd.");
                    System.out.println(
                            Taal.vertaal("winnaarSpel") + sc.guiDc.winnaarsNaam() + Taal.vertaal("scoreWinnaar") + sc.guiDc.winnaarsScore());
                    //nieuw scherm openen met winnaar etc. en huidig scherm sluiten
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        return eindeSpelButton;
    }

    /**
     * Grafische weergave van de speler zijn stenen.
     *
     * @return Collectie van persoonlijke stenen.
     */
    public void spelersCollectieGrid() {
        //maakt een collectie met de huidige speler zijn collectie;
        spelersCollectieGrid = new GridPane();
        List<Integer> spelerCollectie = sc.guiDc.getSpelerPersoonlijkePot();
        for (int i = 0; i < spelerCollectie.size(); i++) {

            int steen = spelerCollectie.get(i);
            if (steen >= 1 && steen <= 6) {
                Rectangle tile = new Rectangle(80, 80);
                tile.setFill(Color.GRAY);
                tile.setStroke(Color.WHITE);

                tiles.add(tile);

                spelersCollectieGrid.add(tile, i, 0);
                selecteerSteen(tile, steen);
            }


        }

    }

    /**
     * Functionaliteit om steen te selecteren uit persoonlijke pot.
     *
     * @param tile  De grafische steen in de persoonlijke pot die interactief is.
     * @param steen Steen die nodig is voor setGeselecteerdeSteen().
     */
    public void selecteerSteen(Rectangle tile, int steen) {

        tile.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.PRIMARY) {
                setGeselecteerdeSteen(steen);
                System.out.println(geselecteerdeSteen);
                if (geselecteerdeSteen > 0) {
                    stenenDeselecteren(tiles);
                }
                tile.setOpacity(0.2);
                geselecteerdeTile = tile;
            }
        });
        if (steen > 0) {
            sc.zetSteen(sc.ophalenImage(steen), tile);
        }
    }


    /**
     * Als er een steen genomen wordt, moet deze ook van het scherm af gehaald worden.
     */
    public void verwijderGeselecteerdeSteen() {
        spelersCollectieGrid.getChildren().remove(geselecteerdeTile);

        if (gezetteTiles == null) {
            gezetteTiles = new ArrayList<>();
            gezetteTiles.add(geselecteerdeTile);
        } else {
            gezetteTiles.add(geselecteerdeTile);
        }

    }

    public List<Rectangle> getGezetteTiles() {
        return gezetteTiles;
    }


    /**
     * Persoonlijke pot grafisch updaten in GUI.
     */
    public void updatePotInGui() {
        pot.getChildren().remove(getSpelersCollectieGrid());
        GridPane resetSpelersCollectieGrid = new GridPane();

        List<Integer> spelerCollectie = sc.guiDc.getSpelerPersoonlijkePot();
        for (int i = 0; i < sc.guiDc.getSpelerPersoonlijkePot().size(); i++) {

            int steen = spelerCollectie.get(i);
            if (steen < 0) {
                steen = 1;
            }
            Rectangle tile = new Rectangle(80, 80);
            tile.setFill(Color.GRAY);
            tile.setStroke(Color.WHITE);
            StackPane sp = new StackPane(tile);
            tiles.add(tile);
            StackPane.setAlignment(tile, Pos.TOP_RIGHT);
            resetSpelersCollectieGrid.add(sp, i, 0);
            selecteerSteen(tile, steen);


        }
        pot.add(resetSpelersCollectieGrid, 1, 3);
//		updateNaamInGui();
    }

    public void setGeselecteerdeSteen(int geselecteerdeSteen) {
        this.geselecteerdeSteen = geselecteerdeSteen;
    }

    /**
     * Helderheid van niet-geselecteerde stenen opnieuw normaal zetten.
     *
     * @param tiles Lijst van stenen.
     */
    public void stenenDeselecteren(List<Rectangle> tiles) {
        for (Rectangle tile : tiles) {
            tile.setOpacity(1);
        }
    }
}
//


