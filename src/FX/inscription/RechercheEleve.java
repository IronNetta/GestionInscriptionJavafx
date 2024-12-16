package FX.inscription;

import activite.Activite;
import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.List;

public class RechercheEleve {
    public static void afficher(Stage parentStage, InscriptionController controller) {
        // Vérifiez que le contrôleur est valide
        if (controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le contrôleur est null. Impossible de continuer.");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Rechercher un élève inscrit");

        // Récupérer la liste des élèves
        List<Personne> eleves = controller.model.listerEleves();
        if (eleves.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun élève disponible.");
            alert.showAndWait();
            return;
        }

        // Liste déroulante pour sélectionner un élève
        ComboBox<Personne> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(eleves);
        comboBox.setPromptText("Sélectionnez un élève");

        // Afficher les noms des élèves dans la liste
        comboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Personne personne, boolean empty) {
                super.updateItem(personne, empty);
                setText(empty || personne == null ? null : personne.getNom());
            }
        });
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Personne personne, boolean empty) {
                super.updateItem(personne, empty);
                setText(empty || personne == null ? null : personne.getNom());
            }
        });

        // Champs pour afficher les informations (désactivés)
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom");
        txtNom.setEditable(false);

        TextField txtPrenom = new TextField();
        txtPrenom.setPromptText("Prénom");
        txtPrenom.setEditable(false);

        TextField txtClub = new TextField();
        txtClub.setPromptText("Club");
        txtClub.setEditable(false);

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setEditable(false);

        CheckBox chkPaiement = new CheckBox("Paiement en cours");
        chkPaiement.setDisable(true);

        // Liste des activités associées
        ListView<Activite> listViewActivites = new ListView<>();
        listViewActivites.setPrefHeight(150);

        comboBox.setOnAction(event -> {
            Personne eleveSelectionne = comboBox.getValue();
            if (eleveSelectionne != null) {
                // Remplir les champs avec les informations de l'élève
                txtNom.setText(eleveSelectionne.getNom());
                txtPrenom.setText(eleveSelectionne.getPrenom());
                txtClub.setText(eleveSelectionne.getClub());
                txtEmail.setText(eleveSelectionne.getMail());
                chkPaiement.setSelected(eleveSelectionne.isPayemmentEnCours());

                // Mettre à jour la liste des activités associées
                listViewActivites.getItems().clear();
                listViewActivites.getItems().addAll(eleveSelectionne.getActivites());
            }
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Rechercher un élève"),
                comboBox,
                new Label("Informations de l'élève :"),
                txtNom,
                txtPrenom,
                txtClub,
                txtEmail,
                chkPaiement,
                new Label("Activités associées :"),
                listViewActivites
        );

        // Configuration de la scène
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }
}
