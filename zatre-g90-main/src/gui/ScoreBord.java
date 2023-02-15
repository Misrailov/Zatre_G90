package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import taal.Taal;

/**
 * ScoreBord geeft een grafisch overzicht van de scores.
 */
public class ScoreBord  {

    private GridPane ScoreBord;
    private ObservableList<ScoreRij> scoreLijst;

    private SpelController sc;

    /**
     * Dit is de constructor van Scorebord, hierbij wordt de SpelController doorgegeven en wordt een blanco ScoreLijst aangemaakt.
     * @param sc SpelController
     */
    public ScoreBord(SpelController sc) {
        this.sc = sc;
        scoreLijst = FXCollections.observableArrayList();
        for (int i = 0; i < 25; i++) {
            scoreLijst.add(new ScoreRij(i, sc));
        }
    }

    /**
     * Grafische weergave van scorebord.
     *
     * @return Grafische weergave van scorebord in vbox pane.
     */
    @SuppressWarnings("unchecked")
    public void tekenScoreBord() {
        ScoreBord = new GridPane();
        final Label label = new Label(Taal.vertaal("ScoreBlad") + ": " + sc.guiDc.getSpelerNaam());
        label.setFont(new Font("Arial", 20));
        TableView<ScoreRij> table = new TableView<>();
        table.setEditable(true);
        TableColumn<ScoreRij, Integer> x2Kolom = new TableColumn<>("x2");
        TableColumn<ScoreRij, Integer> tien = new TableColumn<>("10(1pt)");
        TableColumn<ScoreRij, Integer> elf = new TableColumn<>("11(2pt)");
        TableColumn<ScoreRij, Integer> twaalf = new TableColumn<>("12(4pt)");
        TableColumn<ScoreRij, Integer> bonus = new TableColumn<>("Bonus");
        TableColumn<ScoreRij, Integer> totaal = new TableColumn<>(Taal.vertaal("Totaal"));

        x2Kolom.setCellValueFactory(new PropertyValueFactory<>("x2"));
        tien.setCellValueFactory(new PropertyValueFactory<>("tien"));
        elf.setCellValueFactory(new PropertyValueFactory<>("Elf"));
        twaalf.setCellValueFactory(new PropertyValueFactory<>("Twaalf"));
        bonus.setCellValueFactory(new PropertyValueFactory<>("Bonus"));
        totaal.setCellValueFactory(new PropertyValueFactory<>("Totaal"));

        this.UpdateScore();
        table.setItems(getScoreLijst());
        table.getColumns().addAll(x2Kolom, tien, elf, twaalf, bonus, totaal);

        ScoreBord.setPadding(new Insets(5));

        ScoreBord.getChildren().addAll(label, table);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    /**
     * Update de score op het scoreblad met de correcte waarden
     */
    public void UpdateScore() {
        for (int i = 0; i < 25; i++) {

            this.scoreLijst.get(i).setX2(sc.guiDc.getSpelerScoreBladArray()[i][0]);
            this.scoreLijst.get(i).setTien(sc.guiDc.getSpelerScoreBladArray()[i][1]);
            this.scoreLijst.get(i).setElf(sc.guiDc.getSpelerScoreBladArray()[i][2]);
            this.scoreLijst.get(i).setTwaalf(sc.guiDc.getSpelerScoreBladArray()[i][3]);
            this.scoreLijst.get(i).setBonus(sc.guiDc.getSpelerScoreBladArray()[i][4]);
            this.scoreLijst.get(i).setTotaal(sc.guiDc.getSpelerScoreBladArray()[i][5]);

        }

    }

    public ObservableList<ScoreRij> getScoreLijst() {
        return scoreLijst;
    }

    @SuppressWarnings("exports")
    public GridPane getScoreBord() {
        return this.ScoreBord;
    }



}
