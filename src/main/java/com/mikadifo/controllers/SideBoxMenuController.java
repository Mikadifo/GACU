package com.mikadifo.controllers;

import com.mikadifo.models.DB_Connection;
import com.mikadifo.models.table_statements.RoleDB;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
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

    private TextInputDialog dialog = new TextInputDialog();
    private RoleDB roleDB;
    JasperReport jasperReport;
    DB_Connection conection = new DB_Connection();
    Map<String, Object> parameters = new HashMap<String, Object>();
    URL image = MainController.class.getResource("/imgs/logo.png");
    String login;
    JasperPrint jasperPrint;
    

    @FXML
    private VBox sideBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onUserRolesReportAction(ActionEvent event) {
        dialog.setTitle("Roles");
        dialog.setContentText("Ingrese el rol que desea ver:");
        Optional<String> result = dialog.showAndWait();
        boolean resultIsValid = result.map(i -> i.matches("[A-Za-z_]{3,254}")).get();
        if (resultIsValid) {
                try {
                    jasperReport = (JasperReport) JRLoader.loadObject(MainController.class.getResource("/reports/UserRolesReport.jasper"));
                    parameters.put("RoleName", result.get());
                    parameters.put("Image", image);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conection.getConnection());
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (JRException ex) {
                    Logger.getLogger(SideBoxMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("El campo de texto solo puede contener letras y '_'");
            alert.showAndWait();

        }

    }

    @FXML
    private void onCompletedQuizzesAction(ActionEvent event) {
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(MainController.class.getResource("/reports/quizzReport.jasper"));
            login = GalleryController.currentUser.getLogin();
            parameters.put("Login", login);
            parameters.put("Image", image);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conection.getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ex) {
            Logger.getLogger(SideBoxMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onVisitedPlacesAction(ActionEvent event) {
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(MainController.class.getResource("/reports/visitedPlacesReport.jasper"));
            login = GalleryController.currentUser.getLogin();
            parameters.put("PlacesForLogin", login);
            parameters.put("Image", image);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conection.getConnection());
            JasperViewer.viewReport(jasperPrint, false);            
        } catch (JRException ex) {
            Logger.getLogger(SideBoxMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
