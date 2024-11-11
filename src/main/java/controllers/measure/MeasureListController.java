package controllers.measure;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la visualització de la llista de mesures.
 * Gestiona les accions dels botons "Guardar" i "Visualitzar" i proporciona
 * mètodes per obtenir i establir el panell de la llista de mesures.
 */
public class MeasureListController {

    /** Panell de tipus AnchorPane que conté la llista de mesures. */
    @FXML
    private AnchorPane anch_user_list;

    /** Panell on es mostrarà la llista de mesures. */
    @FXML
    private ListView<?> list_measures;

    /**
     * Mètode invocat quan es fa clic al botó de "Guardar".
     * Permet gestionar l'acció de guardar les dades de les mesures.
     *
     * @param event l'esdeveniment de clic associat al botó de "Guardar".
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) {
        // Lògica per gestionar l'acció del botó "Guardar"
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Visualitzar".
     * Permet gestionar l'acció per visualitzar els detalls de la mesura seleccionada.
     *
     * @param event l'esdeveniment de clic associat al botó de "Visualitzar".
     */
    @FXML
    void onViewButtonClick(ActionEvent event) {
        // Lògica per gestionar l'acció del botó "Visualitzar"
    }

    /**
     * Obté el panell de la llista de mesures.
     *
     * @return l'AnchorPane que conté la llista de mesures.
     */
    public AnchorPane getAnch_user_list() {
        return anch_user_list;
    }

    /**
     * Estableix el panell de la llista de mesures.
     *
     * @param anch_user_list l'AnchorPane a assignar com a panell de la llista de mesures.
     */
    public void setAnch_user_list(AnchorPane anch_user_list) {
        this.anch_user_list = anch_user_list;
    }
}