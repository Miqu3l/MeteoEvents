package controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador per a la interfície d'usuari de la llista d'usuaris.
 * Gestiona les accions dels botons "Guardar" i "Visualitzar" i proporciona
 * mètodes per obtenir i establir el panell de la llista d'usuaris.
 */
public class UserListController {

    /**
     * Panell de tipus AnchorPane que conté la llista d'usuaris.
     */
    @FXML
    private AnchorPane anch_user_list;

    /**
     * Mètode invocat quan es fa clic al botó de "Guardar".
     *
     * @param event l'esdeveniment de clic associat al botó.
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) {
        // Lògica per gestionar l'acció del botó "Guardar"
    }

    /**
     * Mètode invocat quan es fa clic al botó de "Visualitzar".
     *
     * @param event l'esdeveniment de clic associat al botó.
     */
    @FXML
    void onViewButtonClick(ActionEvent event) {
        // Lògica per gestionar l'acció del botó "Visualitzar"
    }

    /**
     * Obté el panell de la llista d'usuaris.
     *
     * @return l'AnchorPane que conté la llista d'usuaris.
     */
    public AnchorPane getAnch_user_list() {
        return anch_user_list;
    }

    /**
     * Estableix el panell de la llista d'usuaris.
     *
     * @param anch_user_list l'AnchorPane a assignar com a panell de la llista d'usuaris.
     */
    public void setAnch_user_list(AnchorPane anch_user_list) {
        this.anch_user_list = anch_user_list;
    }
}