package com.mikadifo.controllers;

import static com.mikadifo.controllers.GalleryController.currentUser;
import static com.mikadifo.controllers.WindowFactories.ACCOUNT;
import static com.mikadifo.controllers.WindowFactories.CHANGE_PASSWORD;
import static com.mikadifo.controllers.WindowFactories.LOGIN;
import static com.mikadifo.controllers.WindowFactories.SIGNUP;
import static com.mikadifo.controllers.WindowFactories.currentStage;
import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.table_statements.CityDB;
import com.mikadifo.models.table_statements.UserDB;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class SideBoxMenuController implements Initializable {

    @FXML
    private VBox sideBox;
    @FXML
    private VBox accountOptions;
    @FXML
    private VBox reportsOptions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onUserRolesReportAction(ActionEvent event) {
	
    }

    @FXML
    private void onCompletedQuizzesAction(ActionEvent event) {
    }

    @FXML
    private void onVisitedPlacesAction(ActionEvent event) {
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(MainController.class.getResource("/reports/VsitedPlacesReport.jasper"));
            DB_Connection conection = new DB_Connection();
            Map<String,Object> parameters = new HashMap<String,Object>();
            String login = GalleryController.currentUser.getLogin();
            URL image = MainController.class.getResource("/imgs/logo.png") ;
            parameters.put("PlacesForLogin", login);
            parameters.put("Image", image);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,conection.getConnection());
            JasperViewer.viewReport(jasperPrint,false);
            
        } catch (JRException ex) {
            Logger.getLogger(SideBoxMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void onAboutAction(ActionEvent event) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Acerca de");
	alert.setHeaderText("GACU");
	alert.setContentText("El objetivo de esta aplicacion es informar o dar a conocer"
		+ " la vasta diversidad cultural arquitectónica de la ciudad de Cuenca(Ecuador),"
		+ " compartir en ella información general, datos históricos y curiosos," 
		+ " ademas cuenta con una trivia donde se generan preguntas aletorias de"
		+ " los lugares que has visitado en la aplicacion."
	);
	alert.showAndWait();
    }


    @FXML
    private void onExitAction(ActionEvent event) {
        boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¿Estas seguro que desea salir?");
        if (isOk) System.exit(0);
    }

    @FXML
    private void onAccountAction(ActionEvent event) {
        AccountController account = (AccountController) ACCOUNT.createWindow();
        account.init(currentUser);
    }

    @FXML
    private void onCreateAccountAction(ActionEvent event) {
        SIGNUP.createWindow().init();
    }

    @FXML
    private void onChangePasswordAction(ActionEvent event) {
        CHANGE_PASSWORD.createWindow();;
    }

    @FXML
    private void onDeleteAccountAction(ActionEvent event) {
        boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¬øEsta seguro que desea eliminar la cuenta?");

        if (isOk) currentUser.delete();
        
    }

    @FXML
    private void onLogOutAction(ActionEvent event) {
         boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¿Estas seguro que desea cerrar la sesion?");
        if (isOk)  currentStage.close();
        
    }

   private boolean showAlert(Alert.AlertType alertType, String header, String message) {
	Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

	return alert.showAndWait().get() == ButtonType.OK;
    }
   
   

}
