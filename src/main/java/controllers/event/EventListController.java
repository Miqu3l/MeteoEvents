package controllers.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la visualització i gestió de la llista d'esdeveniments.
 * Permet realitzar accions per desar i visualitzar els esdeveniments de la llista.
 */
public class EventListController {

    /** Panell principal de l'àrea de la llista d'esdeveniments. */
    @FXML
    private AnchorPane anch_user_list;

    /** Botó per desar els canvis en la llista d'esdeveniments. */
    @FXML
    private Button btn_user_list_save;

    /** Botó per visualitzar els detalls dels esdeveniments de la llista. */
    @FXML
    private Button btn_user_list_view;

    /** Panell on es mostrarà la llista d'esdeveniments. */
    @FXML
    private ListView<?> list_events;

    /**
     * Mètode invocat quan es fa clic al botó de "Guardar".
     * Gestiona l'acció per desar els canvis en la llista d'esdeveniments.
     *
     * @param event l'esdeveniment de clic associat al botó de "Guardar".
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) {
        // Lògica per gestionar l'acció de desar la llista d'esdeveniments
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Visualitzar".
     * Gestiona l'acció per visualitzar els detalls dels esdeveniments seleccionats.
     *
     * @param event l'esdeveniment de clic associat al botó de "Visualitzar".
     */
    @FXML
    void onViewButtonClick(ActionEvent event) {
        // Lògica per gestionar l'acció de visualitzar els detalls de l'esdeveniment
    }
}