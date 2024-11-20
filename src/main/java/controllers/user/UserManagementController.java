package controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import model.crud.CrudUser;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controlador per a la gestió dels esdeveniments de la vista de gestió dels treballadors
 * per als usuaris administradors. En aquesta classe es gestionen les interaccions amb els
 * botons per a esborrar, crear o modificar i visualitzar dels treballadors que treballen a l'empresa.
 *
 * @author Miguel Rodríguez Garriga
 * @version 1.0
 */
public class UserManagementController {

    /**
     * Constant amb el missatge d'error quan es produeix un problema durant la petició.
     */
    private static final String ERROR = "S'ha produït un error.";

    /**
     * Etiqueta que mostra l'ID de l'usuari gestionat.
     */
    @FXML
    private Label lbl_user_management_id;

    /**
     * Etiqueta que mostra la resposta de la base de dades.
     */
    @FXML
    private Label lbl_user_response;

    /**
     * Camp de text per introduir o visualitzar l'adreça de l'usuari.
     */
    @FXML
    private TextField txt_user_management_description;

    /**
     * Camp de text per introduir o visualitzar la data de naixement de l'usuari.
     */
    @FXML
    private TextField txt_user_management_birth;

    /**
     * Camp de text per introduir o visualitzar la ciutat de residència de l'usuari.
     */
    @FXML
    private TextField txt_user_management_city;

    /**
     * Camp de text per introduir o visualitzar l'adreça de correu electrònic de l'usuari.
     */
    @FXML
    private TextField txt_user_management_email;

    /**
     * Camp de text per introduir o visualitzar el nom de l'usuari.
     */
    @FXML
    private TextField txt_user_management_name;

    /**
     * Camp de text per introduir o visualitzar la contrasenya de l'usuari.
     */
    @FXML
    private TextField txt_user_management_password;

    /**
     * Camp de text per introduir o visualitzar el número de telèfon de l'usuari.
     */
    @FXML
    private TextField txt_user_management_phone;

    /**
     * Camp de text per introduir o visualitzar el sexe de l'usuari.
     */
    @FXML
    private TextField txt_user_management_sex;

    /**
     * Camp de text per introduir o visualitzar el tipus d'usuari.
     */
    @FXML
    private TextField txt_user_management_type;

    /**
     * Camp de text per introduir o visualitzar el nom d'usuari o identificador d'accés.
     */
    @FXML
    private TextField txt_user_management_username;

    /**
     * Botó per la gestió d'esborrament.
     */
    @FXML
    private Button btn_user_management_delete;

    /**
     * Botó per la gestió de creació.
     */
    @FXML
    private Button btn_user_management_save;

    private User user;
    private CrudUser crudUser;

    /**
     * Mètode que s'executa en crear el controlador.
     */
    @FXML
    protected void initialize() throws Exception {
        crudUser = new CrudUser();
        user = new User();
    }

    /**
     * Gestiona l'esdeveniment del botó per esborrar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onDeleteButtonClick(ActionEvent event) throws Exception {
        lbl_user_response.setText("");

        if(user != null){
            String response = crudUser.deleteUser(user.getID());
            lbl_user_response.setText(response);
            cleanUser();
        }else{
            lbl_user_response.setText(ERROR);
        }
    }

    /**
     * Gestiona l'esdeveniment del botó per crear un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onSaveButtonClick(ActionEvent event) throws Exception {
        lbl_user_response.setText("");
        saveUser();
        String response = crudUser.createUser(user);
        lbl_user_response.setText(response);
    }

    /**
     * Gestiona l'esdeveniment del botó per modificar un treballador.
     *
     * @param event L'esdeveniment del botó.
     */
    @FXML
    void onModifyButtonClick(ActionEvent event) throws Exception {
        lbl_user_response.setText("");
        saveUser();
        if(user != null){
            String response = crudUser.updateUser(user);
            lbl_user_response.setText(response);
        }else{
            lbl_user_response.setText(ERROR);
        }
    }

    /**
     * Estableix l'usuari actual i inicialitza els camps de la interfície d'usuari
     * amb les dades de l'usuari proporcionat.
     *
     * @param user L'usuari que es vol gestionar.
     */
    public void setUser(User user) {
        this.user = user;
        initializeUser(user);
    }

    /**
     * Inicialitza els camps de la interfície d'usuari amb la informació de l'usuari.
     *
     * @param user L'usuari que es vol visualitzar.
     */
    private void initializeUser(User user) {
        lbl_user_management_id.setText(user.getID());
        txt_user_management_username.setText(user.getNom_usuari());
        txt_user_management_password.setText(user.getContrasenya());
        txt_user_management_name.setText(user.getNom_c());
        txt_user_management_email.setText(user.getEmail());
        txt_user_management_phone.setText(Integer.toString(user.getTelefon()));
        txt_user_management_city.setText(user.getPoblacio());
        if (user.getData_naixement() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(user.getData_naixement());
            txt_user_management_birth.setText(date);
        } else {
            txt_user_management_birth.setText("00/00/0000");
        }
        txt_user_management_sex.setText(user.getSexe());
        txt_user_management_type.setText(user.getFuncional_id());
        txt_user_management_description.setText(user.getDescripcio());
    }

    /**
     * Neteja tots els camps de la interfície d'usuari.
     */
    private void cleanUser() {
        lbl_user_management_id.setText("");
        txt_user_management_username.setText("");
        txt_user_management_password.setText("");
        txt_user_management_name.setText("");
        txt_user_management_email.setText("");
        txt_user_management_phone.setText("");
        txt_user_management_city.setText("");
        txt_user_management_birth.setText("");
        txt_user_management_sex.setText("");
        txt_user_management_type.setText("");
        txt_user_management_description.setText("");
    }

    /**
     * Guarda tots els camps a l'objecte usuari.
     */
    public void saveUser(){
        user.setNom_c(txt_user_management_name.getText());
        user.setFuncional_id(txt_user_management_type.getText());
        user.setNom_usuari(txt_user_management_username.getText());
        user.setContrasenya(txt_user_management_password.getText());
        user.setData_naixement(new Date());
        user.setUltima_connexio(null);
        user.setSexe(txt_user_management_sex.getText());
        user.setPoblacio(txt_user_management_city.getText());
        user.setEmail(txt_user_management_email.getText());
        if(txt_user_management_phone.getText().isEmpty()){
            user.setTelefon(0);
        }else{
            user.setTelefon(Integer.parseInt(txt_user_management_phone.getText()));
        }
        user.setDescripcio(txt_user_management_description.getText());
    }

    /**
     * Configura el CrudUser, útil per injectar mocks en prova.
     *
     * @param crudUser la instància de CrudEvent a injectar.
     */
    public void setCrudUser(CrudUser crudUser) {
        this.crudUser = crudUser;
    }

    /**
     * Mètode que retorna l'objecte crudUser.
     *
     * @return l'objecte `CrudUser`.
     */
    public CrudUser getCrudUser(){
        return this.crudUser;
    }

    /**
     * Obté el botó d'eliminació per a la gestió d'usuaris.
     *
     * @return el botó btn_user_management_delete
     */
    public Button getBtn_user_management_delete() {
        return btn_user_management_delete;
    }

    /**
     * Obté el botó de desament per a la gestió d'usuaris.
     *
     * @return el botó btn_user_management_save
     */
    public Button getBtn_user_management_save() {
        return btn_user_management_save;
    }
}