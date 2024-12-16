package FX.inscription;

import inscription.InscriptionController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import personne.Personne;

import java.util.List;

public class FormulaireSuppression {
    public static Stage afficher(Stage parentStage, InscriptionController controller) {

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Supprimer un élève inscrit");

        // Récupérer la liste des élèves
        List<Personne> eleves = controller.model.listerEleves();
        if (eleves.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun élève à supprimer.");
            alert.showAndWait();
        }

        // Liste déroulante pour sélectionner un élève par nom
        ComboBox<String> comboBox = new ComboBox<>();
        for (Personne eleve : eleves) {
            comboBox.getItems().add(eleve.getNom());
        }
        comboBox.setPromptText("Sélectionnez un nom");

        // Bouton pour supprimer
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setOnAction(event -> {
            String nomSelectionne = comboBox.getValue();
            if (nomSelectionne != null) {
                // Appeler la méthode supprimerEleve du contrôleur
                controller.view.simulerEntreeNom(nomSelectionne); // Simuler l'entrée de nom dans la vue
                controller.supprimerEleve(); // Appeler la méthode qui supprimera l'élève

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Élève supprimé avec succès !");
                alert.showAndWait();

                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un nom à supprimer.");
                alert.showAndWait();
            }
        });

        // Layout principal
        VBox root = new VBox(10);
        root.getChildren().addAll(new Label("Supprimer un élève inscrit :"), comboBox, btnSupprimer);

        // Configuration de la scène
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}

