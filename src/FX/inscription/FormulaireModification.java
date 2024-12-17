package FX.inscription;

import activite.Activite;
import club.Club;
import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.ArrayList;
import java.util.List;

public class FormulaireModification {
    public static Stage afficher(Stage parentStage, InscriptionController controller) {
        // Vérifiez que le contrôleur est valide
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Modifier une inscription");

        // Récupérer la liste des élèves pour sélection
        List<Personne> eleves = controller.model.listerEleves();
        if (eleves.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun élève à modifier.");
            alert.showAndWait();
            return null;
        }

        // Sélection de l'élève
        ComboBox<Personne> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(eleves);
        comboBox.setPromptText("Sélectionnez un élève");

        // Champs de modification
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom");

        TextField txtPrenom = new TextField();
        txtPrenom.setPromptText("Prénom");

        ComboBox<String> comboClubs = new ComboBox<>();
        comboClubs.getItems().addAll(controller.getClubs().stream()
                .map(Club::getClubName).toList());
        comboClubs.setPromptText("Choisir un club");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        CheckBox chkPaiement = new CheckBox("Paiement en cours");

        // Gestion des activités
        ListView<Activite> listActivites = new ListView<>();
        listActivites.setPrefHeight(150);

        List<Activite> activites = controller.activiteModel.getActivites();
        if (activites.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucune activité à modifier.");
            alert.showAndWait();
            return null;
        }

        ComboBox<Activite> comboActivites = new ComboBox<>();
        comboActivites.getItems().addAll(controller.activiteModel.getActivites());
        comboActivites.setPromptText("Choisir une activité");

        comboActivites.setCellFactory(lv -> new ListCell<Activite>() {
            @Override
            protected void updateItem(Activite item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });

        TextField txtDureeActivite = new TextField();
        txtDureeActivite.setPromptText("Durée de l'activité en heures");

        CheckBox chkLogementActivite = new CheckBox("Logement requis");
        CheckBox chkRepasActivite = new CheckBox("Repas inclus");
        CheckBox chkWeekendActivite = new CheckBox("Activité en weekend");

        // Ajouter une nouvelle activité
        Button btnAjouterActivite = new Button("Ajouter une activité");
        btnAjouterActivite.setOnAction(event -> {
            String nomActivite = String.valueOf(comboActivites.getValue());
            if (nomActivite == null || txtDureeActivite.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner une activité et renseigner tous les détails.").showAndWait();
                return;
            }
            try {
                int duree = Integer.parseInt(txtDureeActivite.getText());
                Activite nouvelleActivite = new Activite(
                        nomActivite,
                        duree,
                        chkLogementActivite.isSelected(),
                        chkRepasActivite.isSelected(),
                        chkWeekendActivite.isSelected()
                );
                listActivites.getItems().add(nouvelleActivite);

                // Réinitialiser les champs
                comboActivites.setValue(null);
                txtDureeActivite.clear();
                chkLogementActivite.setSelected(false);
                chkRepasActivite.setSelected(false);
                chkWeekendActivite.setSelected(false);
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Veuillez entrer une durée valide.").showAndWait();
            }
        });

        // Supprimer une activité
        Button btnSupprimerActivite = new Button("Supprimer l'activité sélectionnée");
        btnSupprimerActivite.setOnAction(event -> {
            Activite activiteSelectionnee = listActivites.getSelectionModel().getSelectedItem();
            if (activiteSelectionnee != null) {
                listActivites.getItems().remove(activiteSelectionnee);
            } else {
                new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une activité à supprimer.").showAndWait();
            }
        });

        // Pré-remplir les champs lorsqu'un élève est sélectionné
        comboBox.setOnAction(event -> {
            Personne eleveSelectionne = comboBox.getValue();
            if (eleveSelectionne != null) {
                txtNom.setText(eleveSelectionne.getNom());
                txtPrenom.setText(eleveSelectionne.getPrenom());
                comboClubs.setValue(eleveSelectionne.getClub());
                txtEmail.setText(eleveSelectionne.getMail());
                chkPaiement.setSelected(eleveSelectionne.isPayemmentEnCours());

                listActivites.getItems().setAll(eleveSelectionne.getActivites());
            }
        });

        comboActivites.setOnAction(event -> {
                Activite activiteSelection = comboActivites.getValue();
        if (activiteSelection != null ) {
            txtDureeActivite.setText(activiteSelection.getNom());
            chkLogementActivite.setSelected(activiteSelection.isLogement());
            chkRepasActivite.setSelected(activiteSelection.isRepasSoir());
            chkWeekendActivite.setSelected(activiteSelection.isEstWeekend());
        }

        });

        // Bouton de validation
        Button btnSubmit = new Button("Modifier");
        btnSubmit.setOnAction(event -> {
            Personne eleveSelectionne = comboBox.getValue();
            if (eleveSelectionne == null) {
                new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un élève.").showAndWait();
                return;
            }
            // Mise à jour des informations
            eleveSelectionne.setNom(txtNom.getText());
            eleveSelectionne.setPrenom(txtPrenom.getText());
            eleveSelectionne.setClub(comboClubs.getValue());
            eleveSelectionne.setMail(txtEmail.getText());
            eleveSelectionne.setPayemmentEnCours(chkPaiement.isSelected());

            // Mettre à jour les activités
            eleveSelectionne.setActivites(new ArrayList<>(listActivites.getItems()));

            // Sauvegarde via le contrôleur
            controller.model.sauvegarder();
            new Alert(Alert.AlertType.INFORMATION, "Élève modifié avec succès !").showAndWait();
            stage.close();
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Modifier une inscription"),
                comboBox,
                txtNom, txtPrenom, comboClubs, txtEmail, chkPaiement,
                new Label("Activités associées :"), listActivites,
                new Label("Ajouter une activité :"), comboActivites, txtDureeActivite,
                chkLogementActivite, chkRepasActivite, chkWeekendActivite,
                btnAjouterActivite, btnSupprimerActivite, btnSubmit
        );

        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}