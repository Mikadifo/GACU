package com.mikadifo.controllers;

import com.mikadifo.models.DB_Connection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(MainController.class.getResource("/reports/UserRolesReport.jasper"));
            DB_Connection conection = new DB_Connection();
            Map<String,Object> parameters = new HashMap<String,Object>();
            String role = "admin";
            URL image = MainController.class.getResource("/imgs/logo.png") ;
            parameters.put("RoleName", role);
            parameters.put("Image", image);     
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,conection.getConnection());
            JasperViewer.viewReport(jasperPrint,false);
            
        } catch (JRException ex) {
            Logger.getLogger(SideBoxMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
 
}
