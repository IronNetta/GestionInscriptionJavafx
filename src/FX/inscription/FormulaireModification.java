package FX.inscription;

import club.Club;
import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.List;

public class FormulaireModification {
    public static Stage afficher(Stage parentStage, InscriptionController controller) {
        // Vérifiez que le contrôleur est valide

        // Création de la fenêtre
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Modifier une inscription");

        // Récupérer la liste des élèves pour sélection
        List<Personne> eleves = controller.model.listerEleves();
        if (eleves.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun élève à modifier.");
            alert.showAndWait();
        }

        // Liste déroulante pour sélectionner un élève
        ComboBox<Personne> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(eleves);
        comboBox.setPromptText("Sélectionnez un élève");

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

        // Champs de modification
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom");

        TextField txtPrenom = new TextField();
        txtPrenom.setPromptText("Prénom");

        TextField txtClub = new TextField();
        txtClub.setPromptText("Club");

        ComboBox<String> comboClubs = new ComboBox<>();
        comboClubs.getItems().addAll(controller.getClubs().stream()
                .map(Club::getClubName).toList());
        comboClubs.setPromptText("Choisir un club");

        Button btnAjouterClub = new Button("Ajouter un nouveau club");
        btnAjouterClub.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ajouter un Club");
            dialog.setHeaderText("Nouveau club");
            dialog.setContentText("Nom du club :");
            dialog.showAndWait().ifPresent(nomClub -> {
                if (controller.getClubs().stream().noneMatch(club -> club.getClubName().equalsIgnoreCase(nomClub))) {
                    controller.ajouterClub(nomClub);
                    comboClubs.getItems().add(nomClub);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Le club existe déjà.").showAndWait();
                }
            });
        });

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        CheckBox chkPaiement = new CheckBox("Paiement en cours");

        // Pré-remplir les champs lorsqu'un élève est sélectionné
        comboBox.setOnAction(event -> {
            Personne eleveSelectionne = comboBox.getValue();
            if (eleveSelectionne != null) {
                txtNom.setText(eleveSelectionne.getNom());
                txtPrenom.setText(eleveSelectionne.getPrenom());
                txtClub.setText(eleveSelectionne.getClub());
                txtEmail.setText(eleveSelectionne.getMail());
                chkPaiement.setSelected(eleveSelectionne.isPayemmentEnCours());
            }
        });

        // Bouton de soumission
        Button btnSubmit = new Button("Modifier");
        btnSubmit.setOnAction(event -> {
            Personne eleveSelectionne = comboBox.getValue();
            if (eleveSelectionne == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un élève à modifier.");
                alert.showAndWait();
                return;
            }

            // Mise à jour des informations de l'élève
            eleveSelectionne.setNom(txtNom.getText());
            eleveSelectionne.setPrenom(txtPrenom.getText());
            eleveSelectionne.setClub(txtClub.getText());
            eleveSelectionne.setClub(comboClubs.getValue());
            eleveSelectionne.setMail(txtEmail.getText());
            eleveSelectionne.setPayemmentEnCours(chkPaiement.isSelected());

            // Sauvegarde via le contrôleur
            controller.model.sauvegarder();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Élève modifié avec succès !");
            alert.showAndWait();

            // Fermer la fenêtre
            stage.close();
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Modifier une inscription"),
                comboBox,
                txtNom,
                txtPrenom,
                txtClub,
                comboClubs,
                btnAjouterClub,
                txtEmail,
                chkPaiement,
                btnSubmit
        );

        // Configuration de la scène
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}