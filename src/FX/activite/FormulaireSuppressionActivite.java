package FX.activite;

import activite.Activite;
import activite.ActiviteController;
import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personne.Personne;

import java.util.List;

public class FormulaireSuppressionActivite {
    public static Stage afficher(Stage parentStage, ActiviteController controller) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Supprimer une activité");

        List<Activite> activites = controller.listerActivites();
        if (activites.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucune activité à supprimer.");
            alert.showAndWait();
        } else {

            // Liste déroulante pour sélectionner une activite
            ComboBox<String> comboBox = new ComboBox<>();
            for (Activite activite : activites) {
                comboBox.getItems().add(activite.getNom());
            }
            comboBox.setPromptText("Sélectionnez un nom");

            // Bouton pour supprimer
            Button btnSupprimer = new Button("Supprimer");
            btnSupprimer.setOnAction(event -> {
                String nomSelectionne = comboBox.getValue();
                if (nomSelectionne != null) {
                    // Appeler la méthode supprimerActivite du contrôleur
                    controller.view.simulerEntreeNom(nomSelectionne);
                    controller.supprimerActivite(nomSelectionne); // Appeler la méthode qui supprimera l'activité

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Élève supprimé avec succès !");
                    alert.showAndWait();

                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un nom à supprimer.");
                    alert.showAndWait();
                }
            });

            VBox root = new VBox(10);
            root.getChildren().addAll(new Label("Supprimer un élève inscrit :"), comboBox, btnSupprimer);

            Scene scene = new Scene(root, 300, 200);
            stage.setScene(scene);
            stage.show();
        }
        return stage;
    }
}
