package controllers.event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.model.Event;
import model.crud.CrudEvent;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de gestió per als administradors.
 * Gestiona les interaccions amb els botons per a esborrar, crear o modificar i visualitzar
 * esdeveniments en l'aplicació.
 *
 * @version 1.0
 */
public class EventManagementController {

    /**
     * Missatge d'error estàndard en cas de fallada durant la petició.
     */
    private static final String ERROR = "S'ha produït un error.";

    /**
     * Etiqueta per mostrar l'ID de gestió de l'esdeveniment.
     */
    @FXML
    private Label lbl_event_management_id;

    /**
     * Etiqueta per mostrar la resposta relacionada amb la gestió de l'esdeveniment.
     */
    @FXML
    private Label lbl_event_response;

    /**
     * Camp de text per introduir o modificar l'adreça de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_address;

    /**
     * Camp de text per introduir o modificar la capacitat màxima de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_capacity;

    /**
     * Camp de text per introduir o modificar la ciutat on es durà a terme l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_city;

    /**
     * Camp de text per introduir o modificar la descripció de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_description;

    /**
     * Camp de text per introduir o modificar el nom de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_name;

    /**
     * Camp de text per introduir o modificar el nom de l'organitzador de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_organizer;

    /**
     * Camp de text per introduir o modificar la data de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_date;

    /**
     * Camp de text per introduir o modificar l'hora final de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_endtime;

    /**
     * Camp de text per introduir o modificar l'hora inicial de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_initialtime;

    /**
     * Camp de text per introduir o modificar el codi postal de la ubicació de l'esdeveniment.
     */
    @FXML
    private TextField txt_event_management_zipcode;


    private Event event;
    private CrudEvent crudEvent;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        crudEvent = new CrudEvent();
        event = new Event();
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws Exception {
        lbl_event_response.setText("");

        if (this.event != null) {
            String response = crudEvent.deleteEvent(this.event.getId());
            lbl_event_response.setText(response);
            cleanEvent();
        } else {
            lbl_event_response.setText(ERROR);
        }
    }

    /**
     * Gestiona l'esdeveniment del botó per crear un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) throws Exception {
        lbl_event_response.setText("");
        saveEvent();
        String response = crudEvent.createEvent(this.event);
        lbl_event_response.setText(response);
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un esdeveniment.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModifyButtonClick(ActionEvent event) throws Exception {
        lbl_event_response.setText("");
        saveEvent();
        if (this.event != null) {
            String response = crudEvent.updateEvent(this.event);
            lbl_event_response.setText(response);
        } else {
            lbl_event_response.setText(ERROR);
        }
    }

    /**
     * Estableix l'esdeveniment actual i inicialitza els camps de la interfície d'usuari
     * amb les dades de l'esdeveniment proporcionat.
     *
     * @param event L'esdeveniment que es vol gestionar.
     */
    public void setEvent(Event event) {
        this.event = event;
        initializeEvent(event);
    }

    /**
     * Inicialitza els camps de la interfície d'usuari amb la informació de l'esdeveniment.
     *
     * @param event L'esdeveniment que es vol visualitzar.
     */
    private void initializeEvent(Event event) {

        System.out.println(event);
        System.out.println(event.getCodiPostal());
        System.out.println(event.getHoraFi());

        lbl_event_management_id.setText(event.getId());
        txt_event_management_name.setText(event.getNom());
        txt_event_management_description.setText(event.getDescripcio());
        txt_event_management_organizer.setText(event.getOrganitzador());
        txt_event_management_address.setText(event.getDireccio());
        txt_event_management_zipcode.setText(event.getCodiPostal());
        txt_event_management_city.setText(event.getPoblacio());
        txt_event_management_capacity.setText(String.valueOf(event.getAforament()));
        txt_event_management_initialtime.setText(event.getHoraInici());
        txt_event_management_endtime.setText(event.getHoraFi());
        txt_event_management_date.setText(event.getDataEsde(txt_event_management_date.getText()));
    }

    /**
     * Neteja tots els camps de la interfície d'usuari.
     */
    private void cleanEvent() {
        lbl_event_management_id.setText("");
        txt_event_management_name.setText("");
        txt_event_management_description.setText("");
        txt_event_management_organizer.setText("");
        txt_event_management_address.setText("");
        txt_event_management_zipcode.setText("");
        txt_event_management_city.setText("");
        txt_event_management_capacity.setText("");
        txt_event_management_initialtime.setText("");
        txt_event_management_endtime.setText("");
        txt_event_management_date.setText("");
    }

    /**
     * Guarda tots els camps a l'objecte esdeveniment.
     */
    public void saveEvent() {
        event.setNom(txt_event_management_name.getText());
        event.setDescripcio(txt_event_management_description.getText());
        event.setOrganitzador(txt_event_management_organizer.getText());
        event.setDireccio(txt_event_management_address.getText());
        event.setCodiPostal(txt_event_management_zipcode.getText());
        event.setPoblacio(txt_event_management_city.getText());
        if(txt_event_management_capacity.getText().isEmpty()){
            event.setAforament(0);
        }else{
            event.setAforament(Integer.parseInt(txt_event_management_capacity.getText()));
        }
        event.setHoraInici(txt_event_management_initialtime.getText());
        event.setHoraFi(txt_event_management_endtime.getText());
        event.setDataEsde(txt_event_management_date.getText());
    }
}