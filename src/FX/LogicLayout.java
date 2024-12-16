package FX;

import FX.activite.FormulaireAjoutActivite;
import FX.activite.FormulaireModificationActivite;
import FX.activite.FormulaireSuppressionActivite;
import FX.activite.RechercheActivite;
import FX.inscription.FormulaireAjout;
import FX.inscription.FormulaireModification;
import FX.inscription.FormulaireSuppression;
import FX.inscription.RechercheEleve;
import activite.Activite;
import activite.ActiviteController;
import activite.ActiviteView;
import inscription.Inscription;
import inscription.InscriptionController;
import inscription.InscriptionView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import personne.Personne;

public class LogicLayout {

    private final InscriptionController controller;
    private final ActiviteController activiteController;
    private final LayoutManager layoutManager;

    public LogicLayout(InscriptionController controller, ActiviteController activiteController, LayoutManager layoutManager) {
        this.controller = controller;
        this.activiteController = activiteController;
        this.layoutManager = layoutManager;
    }

    public void configureEvents(Stage primaryStage) {
        layoutManager.getBtnAjouter().setOnAction(event -> ouvrirFormulaireAjout(primaryStage));
        layoutManager.getBtnChercher().setOnAction(event -> ouvrirRechercheEleve(primaryStage));
        layoutManager.getBtnModifier().setOnAction(event -> ouvrirModificationInscription(primaryStage));
        layoutManager.getBtnSupprimer().setOnAction(event -> ouvrirSuppressionInscription(primaryStage));
        layoutManager.getBtnAjouterActivite().setOnAction(event ->ouvrirFormulaireAjoutActivite(primaryStage));
        layoutManager.getBtnChercherActivite().setOnAction(event ->ouvrirRechercheActivite(primaryStage));
        layoutManager.getBtnModifierActivite().setOnAction(event ->ouvrirFormulaireModificationActivite(primaryStage));
        layoutManager.getBtnSupprimerActivite().setOnAction(event ->ouvrirFormulaireSuppressionActivite(primaryStage));
    }

    private void mettreAJourTable() {
        ObservableList<Personne> personnes = FXCollections.observableArrayList(controller.model.listerEleves());
        layoutManager.getTableView().setItems(personnes);
    }

    private void ouvrirFormulaireAjout(Stage parentStage) {
        Stage formulaireStage = FormulaireAjout.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            controller.model.charger(); // Recharger les données du modèle
            mettreAJourTable(); // Rafraîchir le tableau
        });
    }

    private void ouvrirRechercheEleve(Stage parentStage) {
        RechercheEleve.afficher(parentStage, controller);
    }

    private void ouvrirModificationInscription(Stage parentStage) {
        Stage formulaireStage = FormulaireModification.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            controller.model.charger();
            mettreAJourTable();
        });
    }

    private void ouvrirSuppressionInscription(Stage parentStage) {
        Stage formulaireStage = FormulaireSuppression.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            controller.model.charger();
            mettreAJourTable();
        });
    }

    private void ouvrirFormulaireAjoutActivite(Stage parentStage) {
        Stage formulaireStage = FormulaireAjoutActivite.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            controller.model.charger();
            mettreAJourTable();
        });
    }

    private void ouvrirRechercheActivite(Stage parentStage) {
        Stage formulaireStage = RechercheActivite.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            controller.model.charger();
            mettreAJourTable();
        });
    }

    private void ouvrirFormulaireModificationActivite(Stage parentStage) {
        Stage formulaireStage = FormulaireModificationActivite.afficher(parentStage, controller);
        formulaireStage.setOnHidden(event -> {
            controller.model.charger();
            mettreAJourTable();
        });
    }

    private void ouvrirFormulaireSuppressionActivite(Stage parentStage) {
        Stage formulaireStage = FormulaireSuppressionActivite.afficher(parentStage, activiteController);
        formulaireStage.setOnHidden(event -> {
            controller.model.charger();
            mettreAJourTable();
        });
    }
}

