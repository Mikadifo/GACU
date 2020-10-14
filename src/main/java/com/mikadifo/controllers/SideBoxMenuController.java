package com.mikadifo.controllers;

import com.mikadifo.models.DB_Connection;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
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
	
    }

    @FXML
    private void onCompletedQuizzesAction(ActionEvent event) {
    }

    @FXML
    private void onVisitedPlacesAction(ActionEvent event) {
//        System.out.println(GalleryController.currentUser.getLogin());
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(MainController.class.getResource("/reports/ReportUserPlaces_1.jasper"));
            DB_Connection conection = new DB_Connection();
            Map<String,Object> parameters = new HashMap<String,Object>();
            String login = GalleryController.currentUser.getLogin();
            URL image = MainController.class.getResource("/imgs/logo.png") ;
            parameters.put("PlacesForLogin", login);
            parameters.put("Image", image);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters,conection.getConnection());
            JasperViewer.viewReport(jp,false);
            
           

        } catch (JRException ex) {
            Logger.getLogger(SideBoxMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}
