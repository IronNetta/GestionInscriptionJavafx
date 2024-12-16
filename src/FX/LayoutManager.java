package FX;

import inscription.Inscription;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import personne.Personne;

import java.util.Objects;

public class LayoutManager {

    private TableView<Personne> tableView;
    private Button btnAjouter, btnChercher, btnModifier, btnSupprimer;
    private Button btnAjouterActivite, btnChercherActivite, btnModifierActivite, btnSupprimerActivite;


    public LayoutManager() {
        this.tableView = new TableView<>();
        this.btnAjouter = new Button("Ajouter une inscription");
        this.btnChercher = new Button("Rechercher un élève par son nom");
        this.btnModifier = new Button("Modifier une inscription");
        this.btnSupprimer = new Button("Supprimer une inscription");
        this.btnAjouterActivite = new Button("Ajouter une Activite");
        this.btnChercherActivite = new Button("Rechercher une Activite");
        this.btnModifierActivite = new Button("Modifier une Activite");
        this.btnSupprimerActivite = new Button("Supprimer une Activite");
    }

    public VBox createTableViewLayout() {
        // Configuration des colonnes du tableau
        TableColumn<Personne, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Personne, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Personne, Integer> clubCol = new TableColumn<>("Club");
        clubCol.setCellValueFactory(new PropertyValueFactory<>("club"));

        TableColumn<Personne, Integer> mailCol = new TableColumn<>("Mail");
        mailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));

        TableColumn<Personne, Integer> PayementCol = new TableColumn<>("Payement En Cours");
        PayementCol.setCellValueFactory(new PropertyValueFactory<>("payemmentEnCours"));

        tableView.getColumns().addAll(nomCol, prenomCol, clubCol, mailCol, PayementCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox rootTable = new VBox(10);
        rootTable.getChildren().add(tableView);

        return rootTable;
    }

    public VBox createButtonsLayout() {
        VBox rootButtons = new VBox(10);
        rootButtons.getChildren().addAll(btnAjouter, btnChercher, btnModifier, btnSupprimer);
        return rootButtons;
    }

    public Button getBtnAjouter() {
        return btnAjouter;
    }

    public Button getBtnChercher() {
        return btnChercher;
    }

    public Button getBtnModifier() {
        return btnModifier;
    }

    public Button getBtnSupprimer() {
        return btnSupprimer;
    }

    public TableView<Personne> getTableView() {
        return tableView;
    }

    public VBox createButtonsActivityLayout(){
        VBox rootButtons = new VBox(10);
        rootButtons.getChildren().addAll(btnAjouterActivite, btnChercherActivite, btnModifierActivite, btnSupprimerActivite);
        return rootButtons;
    }

    public Button getBtnAjouterActivite() {
        return btnAjouterActivite;
    }

    public Button getBtnChercherActivite() {
        return btnChercherActivite;
    }

    public Button getBtnModifierActivite() {
        return btnModifierActivite;
    }

    public Button getBtnSupprimerActivite() {
        return btnSupprimerActivite;
    }
}
