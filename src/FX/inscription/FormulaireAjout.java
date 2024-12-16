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

public class FormulaireAjout {

    public static Stage afficher(Stage parentStage, InscriptionController controller) {
        // Création de la fenêtre
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Ajouter une inscription");

        // Champs du formulaire
        TextField txtNom = new TextField();
        txtNom.setPromptText("Nom");

        TextField txtPrenom = new TextField();
        txtPrenom.setPromptText("Prénom");

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

        // Liste déroulante pour sélectionner des activités
        ComboBox<String> comboActivites = new ComboBox<>();
        comboActivites.getItems().addAll(controller.getActivites().stream()
                .map(Activite::getNom).toList());
        comboActivites.setPromptText("Choisir une activité");

        // Zone pour configurer les détails spécifiques à une activité
        TextField txtDureeActivite = new TextField();
        txtDureeActivite.setPromptText("Durée de l'activité en heures");
        CheckBox chkLogementActivite = new CheckBox("Logement requis pour l'activité");
        CheckBox chkRepasActivite = new CheckBox("Repas du soir inclus pour l'activité");
        CheckBox chkWeekendActivite = new CheckBox("Activité en weekend");

        // Liste des activités ajoutées pour cette inscription
        ListView<Activite> listActivitesAjoutees = new ListView<>();

        Button btnAjouterActivite = new Button("Ajouter cette activité");
        btnAjouterActivite.setOnAction(event -> {
            String nomActivite = comboActivites.getValue();
            if (nomActivite == null || txtDureeActivite.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner une activité et renseigner tous les détails.").showAndWait();
                return;
            }
            try {
                int duree = Integer.parseInt(txtDureeActivite.getText());
                boolean logement = chkLogementActivite.isSelected();
                boolean repas = chkRepasActivite.isSelected();
                boolean weekend = chkWeekendActivite.isSelected();

                Activite activite = new Activite(nomActivite, duree, logement, repas, weekend);
                listActivitesAjoutees.getItems().add(activite);

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

        // Bouton de soumission
        Button btnSubmit = new Button("Ajouter");
        btnSubmit.setOnAction(event -> {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String club = comboClubs.getValue();
            String email = txtEmail.getText();
            boolean paiement = chkPaiement.isSelected();

            if (nom.isEmpty() || prenom.isEmpty() || club == null || email.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Tous les champs doivent être remplis !").showAndWait();
            } else {
                Personne nouvelEleve = new Personne(nom, prenom, club, email, paiement);

                // Ajouter toutes les activités configurées
                listActivitesAjoutees.getItems().forEach(nouvelEleve::ajouterActivite);

                controller.ajouterEleve(nouvelEleve);

                new Alert(Alert.AlertType.INFORMATION, "Élève ajouté avec succès !").showAndWait();

                stage.close();
            }
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(
                new Label("Ajouter une inscription"),
                txtNom, txtPrenom, comboClubs, btnAjouterClub, txtEmail, chkPaiement,
                new Label("Activité :"), comboActivites, txtDureeActivite, chkLogementActivite, chkRepasActivite, chkWeekendActivite,
                btnAjouterActivite, new Label("Activités ajoutées :"), listActivitesAjoutees,
                btnSubmit
        );

        // Configuration de la scène
        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}

