import FX.LayoutManager;
import FX.LogicLayout;
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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import personne.Personne;

import java.util.Objects;

public class Main extends Application {

    Inscription model = new Inscription();
    InscriptionView view = new InscriptionView();
    ActiviteView activiteView = new ActiviteView();
    Activite activiteModel = new Activite();
    ActiviteController activiteController = new ActiviteController(activiteModel, activiteView);
    InscriptionController controller = new InscriptionController(model, view,activiteModel, activiteController);

    LayoutManager layoutManager = new LayoutManager();
    LogicLayout logicLayout = new LogicLayout(controller,activiteController,layoutManager);

    @Override
    public void start(Stage primaryStage) {
        model.charger();
        activiteModel.charger();

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Kinomichi_Logo.gif")));
        primaryStage.getIcons().add(logo);

        VBox rootTable = layoutManager.createTableViewLayout();
        VBox rootButtons = layoutManager.createButtonsLayout();
        VBox rootActivityButtons = layoutManager.createButtonsActivityLayout();

        GridPane root = new GridPane();
        root.setHgap(50);
        root.setVgap(20);

        root.add(rootButtons, 0, 0);
        root.add(rootActivityButtons, 1, 0);

        VBox rootPrincipal = new VBox(10);
        rootPrincipal.getChildren().addAll(rootTable, root);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        Scene scene = new Scene(rootPrincipal, screenBounds.getWidth() - 50, screenBounds.getHeight() - 50);
        primaryStage.setTitle("Gestion des Inscriptions");
        primaryStage.setScene(scene);
        primaryStage.show();

        logicLayout.configureEvents(primaryStage);
        mettreAJourTable();
    }

    private void mettreAJourTable() {
        ObservableList<Personne> personnes = FXCollections.observableArrayList(controller.model.listerEleves());
        layoutManager.getTableView().setItems(personnes);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

