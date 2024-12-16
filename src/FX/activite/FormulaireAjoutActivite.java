package FX.activite;

import activite.Activite;
import activite.ActiviteController;
import inscription.InscriptionController;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.util.List;


public class FormulaireAjoutActivite {
    public static Stage afficher(Stage parentStage, ActiviteController controller) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Ajouter une nouvelle activité");

        // Création des champs du formulaire
        Label labelNom = new Label("Nom de l'activité :");
        TextField textNom = new TextField();

        Button btnAjouter = new Button("Enregistrer");
        Button btnAnnuler = new Button("Annuler");

        // Layout du formulaire
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(labelNom, 0, 0);
        grid.add(textNom, 1, 0);
        grid.add(btnAjouter, 0, 1);
        grid.add(btnAnnuler, 1, 1);

        // Action du bouton Ajouter
        btnAjouter.setOnAction(event -> {
            String nom = textNom.getText().trim();
            if (nom.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le nom de l'activité ne peut pas être vide.");
                alert.showAndWait();
            } else {
                // Vérifier si l'activité existe déjà
                if (controller.getNomsActivites().stream()
                        .noneMatch(a -> a.equalsIgnoreCase(nom))) {
                    controller.ajouterNomActivite(nom);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Activité '" + nom + "' ajoutée avec succès !");
                    alert.showAndWait();

                    stage.close(); // Fermer la fenêtre
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attention");
                    alert.setHeaderText(null);
                    alert.setContentText("Cette activité existe déjà.");
                    alert.showAndWait();
                }
            }
        });

        // Action du bouton Annuler
        btnAnnuler.setOnAction(event -> stage.close());

        // Affichage de la scène
        Scene scene = new Scene(grid, 300, 150);
        stage.setScene(scene);
        stage.showAndWait();
        return stage;
    }
}