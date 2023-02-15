package gui;

import exceptions.beurtException;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import taal.Taal;

/**
 * Bord is verantwoordelijk voor creatie van een grafisch spelbord.
 */

public class Bord {
    private boolean eersteBeurt;
    GridPane gridSpelBord;
    int[][] vakTypes;
    boolean[][] lijstHeeftSteen;
    SpelController sc;
    boolean middensteVakOpgeVuld = false;

    /**
     * Constructor voor bord.
     */
    public Bord(SpelController sc) {
        this.eersteBeurt = true;
        vakTypes = sc.guiDc.getTypes();
        this.sc = sc;
        lijstHeeftSteen = sc.guiDc.getHeeftSteenSpelbord();
    }

    public GridPane getGridSpelBord() {
        return gridSpelBord;
    }

    /**
     * Deze methode maakt het gridpane aan waar het spelbord in wordt weergegeven en
     * vult het met rectangles die de correcte afbeelding voor elk vak bevatten.
     */
    public void tekenBord() {
        boolean[][] lijstHeeftSteen = sc.guiDc.getHeeftSteenSpelbord(); // update lijst met vakken die stenen hebben

        this.gridSpelBord = new GridPane(); // aanmaak gridpane
        for (int x = 0; x < 15; x++) { // aanmaak rectangles in gridpane met for-loop
            for (int y = 0; y < 15; y++) {
                int vakType = vakTypes[x][y]; // type van vak bepalen
                boolean vakHeeftSteen = lijstHeeftSteen[x][y]; // controle of vak steen bevat
                if ((x == 7 && y == 7) && vakHeeftSteen) {
                    middensteVakOpgeVuld = true;
                } else if (x == 7 && y == 7) {
                    System.out.println(vakHeeftSteen);
                    middensteVakOpgeVuld = false;
                }

                Rectangle tile = new Rectangle(40, 40);
                if (!vakHeeftSteen) { // lege vakken tekenen
                    switch (vakType) {
                        case 0 -> {
                            tile.setFill(Color.BLACK);
                            tile.setStroke(Color.BLACK);
                        }
                        case 1 -> {
                            tile.setFill(Color.WHITE);
                            tile.setStroke(Color.BLACK);
                        }
                        case 2 -> {
                            tile.setFill(Color.GRAY);
                            tile.setStroke(Color.BLACK);
                        }
                        default -> {
                            sc.updateTekst("gui.foutTekenbord");
                            System.out.println(Taal.vertaal("gui.foutTekenbord"));
                        }
                    }
                } else if (vakHeeftSteen) {
                    // stenen weergeven in vakken die niet leeg zijn
                    zetSteenImage(sc.ophalenImage(sc.guiDc.getVakSteen(x, y)), tile);
                    tile.setStroke(Color.BLACK);
                }

                if (vakType == 1 || vakType == 2) { // vakken waar stenen kunnen geplaatst worden interactief maken
                    zetSteen(x, y, tile);

                }
                gridSpelBord.add(tile, x, y);

            }
        }
        gridSpelBord.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }


    /**
     * Zetten van steen op spelbord.
     *
     * @param x    X-co�rdinaat van positie op spelbord.
     * @param y    Y-co�rdinaat van positie op spelbord.
     * @param tile Het vakje waarop geklikt werd om steen te zetten.
     */
    public void zetSteen(int x, int y, Rectangle tile) {
        tile.setOnMouseClicked(mouseEvent -> {

            int geselecteerdeSteen = sc.getSelecteerdeSteen();
            if (eersteBeurt && (x == 7 && y == 7)) {
                try {
                    sc.guiDc.zetSteen(geselecteerdeSteen, 7, 7);

                    this.eersteBeurt = false;
                    sc.verwijderLaatsteSteen();
                    zetSteenImage(sc.ophalenImage(sc.guiDc.getVakSteen(x, y)), tile);
                    middensteVakOpgeVuld= true;
                } catch (Exception e) {
                    sc.updatePotInGui();
                    sc.updateTekst(e.getMessage(), "gui.foutZetSteen");
                    System.out.println(e.getMessage() + Taal.vertaal("gui.foutZetSteen"));
                    resetSpelBord();
                    sc.updateController();
                }
            } else if (eersteBeurt) {
                sc.updateTekst("gui.plaatsSteenMidden");
                System.out.println(Taal.vertaal("gui.plaatsSteenMidden"));
            } else {
                try {
                    sc.guiDc.zetSteen(geselecteerdeSteen, x, y);
                    sc.verwijderLaatsteSteen();
                    zetSteenImage(sc.ophalenImage(sc.guiDc.getVakSteen(x, y)), tile);
                    if (sc.guiDc.isEindeBeurt()) {
                        System.out.println(sc.guiDc.getSpelerNaam());
                        sc.guiDc.startBeurt();
                        sc.updateController();
                        sc.updateTekst(sc.guiDc.getSpelerNaam(), "gui.naStartBeurt");
                        System.out.println(sc.guiDc.getSpelerNaam() + Taal.vertaal("gui.naStartBeurt"));

                    }
                } catch(beurtException e){
                	System.out.println(middensteVakOpgeVuld);
                    if (!eersteBeurt && !middensteVakOpgeVuld) {
                        this.eersteBeurt = true;
                    }

                    sc.updatePotInGui();
                    sc.updateTekst(e.getMessage(), "gui.foutZetSteen");
                    System.out.println(e.getMessage() + Taal.vertaal("gui.foutZetSteen"));
                    resetSpelBord();
                    sc.updateController();
                }
                catch (IllegalArgumentException e) {
                    System.out.println(middensteVakOpgeVuld);
                    if (!eersteBeurt && !middensteVakOpgeVuld) {
                        this.eersteBeurt = true;
                    }

                    sc.updatePotInGui();
                    sc.updateTekst(e.getMessage(), "geenBuurSteen");
                    System.out.println(e.getMessage() + Taal.vertaal("geenBuurSteen"));
                    resetSpelBord();
                    sc.updateController();
                }
            }

        });
    }

    /**
     * Spelbord naar juiste toestand terugbrengen na verkeerde zet.
     *
     */
    public void resetSpelBord() {
        GridPane resetSpelBordGridpane = new GridPane();
        int[][] lijstTypes = sc.guiDc.getTypes();
        boolean[][] lijstHeeftSteen = sc.guiDc.getHeeftSteenSpelbord();

        // resetSpelBordGridpane.setPrefSize(800,800);
        resetSpelBordGridpane.setPadding(new Insets(5));
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                int vak = lijstTypes[i][j];
                boolean vakHeeftSteen = lijstHeeftSteen[i][j];

                Rectangle tile = new Rectangle(40, 40);
                tile.setFill(Color.GRAY);
                tile.setStroke(Color.WHITE);
                if (vak == 2 && !vakHeeftSteen) {
                    tile.setFill(Color.GRAY);
                    tile.setStroke(Color.WHITE);
                } else if (vak == 0) {
                    tile.setFill(Color.WHITE);
                    tile.setStroke(Color.WHITE);
                } else if (!vakHeeftSteen) {
                    tile.setFill(Color.WHITE);
                    tile.setStroke(Color.BLACK);
                }
                Text text = new Text();
                text.setFont(Font.font(30));
                resetSpelBordGridpane.add(new StackPane(tile, text), i, j);

            }
        }
        this.gridSpelBord = resetSpelBordGridpane;

    }

    /**
     * Zetten van een grafische steen op het spelbord.
     *
     * @param img  De image van het steentje dat op het spelbord komt.
     * @param tile Het vakje op het spelbord dat geklikt werd.
     */
    public void zetSteenImage(Image img, Rectangle tile) {
        tile.setFill(new ImagePattern(img));
        tile.setStroke(Color.BLACK);

    }


}
