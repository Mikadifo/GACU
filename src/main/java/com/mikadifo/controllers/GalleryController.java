package com.mikadifo.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import static com.mikadifo.controllers.WindowFactories.*;
import com.mikadifo.models.Roles;
import com.mikadifo.models.function_calls.AllImagesByPlace;
import com.mikadifo.models.function_calls.RandomImgForCategory;
import com.mikadifo.models.function_calls.RandomImgForPlaceByCategory;
import com.mikadifo.models.table_statements.ImageDB;
import com.mikadifo.models.table_statements.PlaceDB;
import com.mikadifo.models.table_statements.RoleDB;
import com.mikadifo.models.table_statements.UserDB;
import com.mikadifo.models.table_statements.User_PlaceDB;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author MIKADIFO
 */
public class GalleryController implements Initializable, Window {

    public static UserDB currentUser;
    private ObservableList<VBox> imgBoxes;
    private List<RandomImgForPlaceByCategory> placesToShow;
    private List<AllImagesByPlace> imgsToShow;
    private boolean isOnPlaces;
    private int selectedCategoryId;
    private static String roleName;
    private HamburgerBackArrowBasicTransition hamburgerTransition;

    @FXML
    private Button btnExit;
    @FXML
    private Button btnTrivia;
    @FXML
    private AnchorPane logedPane;
    @FXML
    private AnchorPane guestPane;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Button btnAccount;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignup;
    @FXML
    private ScrollPane rootScroll;
    @FXML
    private FlowPane imagesFlowPane;
    @FXML
    private Button backButton;
    @FXML
    private JFXDrawer sideDrawer;
    @FXML
    private JFXHamburger hamburgerMenu;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initHamburgerMenu();
        imgBoxes = FXCollections.observableArrayList();
    }

    public void init(Roles role, UserDB user) {
        currentUser = user;
        init();
    }

    @Override
    public void init() {
        currentScene.getStylesheets().add("/styles/gallery.css");
        currentStage.show();
        loadByRole();
        showCategories(); //show when de stage is shown
    }

    private void loadByRole() {
        if (currentUser == null) {
            btnTrivia.setDisable(true);
            guestPane.setVisible(true);
            rootPane.setTop(guestPane);
            logedPane.setVisible(false);
        } else {
            RoleDB role = new RoleDB();
            role.setId(currentUser.getRoleId());
            role.selectById();
            role = role.getRole();

            btnTrivia.setDisable(false);
            rootPane.setTop(logedPane);
            logedPane.setVisible(true);
            roleName = role.getName();
        }
    }

    private void showCategories() {
        clearImgs();
        addImgViewers();

        imagesFlowPane.prefWidthProperty().bind(Bindings.add(-5, rootScroll.widthProperty()));
        imagesFlowPane.prefHeightProperty().bind(Bindings.add(-5, rootScroll.heightProperty()));

        imagesFlowPane.setHgap(20);
        imagesFlowPane.setVgap(20);
        imagesFlowPane.getChildren().addAll(imgBoxes);

        backButton.setVisible(false);
        isOnPlaces = false;
    }

    private void initHamburgerMenu() {
        loadSideBoxMenu();
        hamburgerTransition = new HamburgerBackArrowBasicTransition(hamburgerMenu);
        hamburgerTransition.setRate(-1.0);
        sideDrawer.setVisible(false);
    }

    private void loadSideBoxMenu() {
        try {
            VBox sidePane = FXMLLoader.load(MainController.class.getResource("/com/mikadifo/views/SideBoxMenu.fxml"));
            sideDrawer.setSidePane(sidePane);
        } catch (IOException e) {
            Logger.getLogger(GalleryController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void addImgViewers() {
        new RandomImgForCategory()
                .selectAll()
                .forEach(categoryConsumer);
    }

    private EventHandler<MouseEvent> categoryEventHandler = (event) -> {
        VBox box = (VBox) event.getSource();
        selectedCategoryId = Integer.parseInt(box.getId());
        showPlaces(selectedCategoryId);
        backButton.setVisible(true);
    };

    private Consumer<RandomImgForCategory> categoryConsumer = (record) -> {
        VBox imageBox = new VBox();
        ImageView imgView = new ImageView(getImage(record.getRandomImage()));
        //imgView.setPreserveRatio(true);
        imgView.setFitWidth(300); //record.getWidth();
        imgView.setFitHeight(300);
        imgView.setSmooth(true);
        imgView.setCache(true);

        imageBox.getChildren().add(imgView);
        imageBox.getChildren().add(new Label(record.getCategoryName()));
        imageBox.setOnMouseClicked(categoryEventHandler);
        imageBox.setId(String.valueOf(record.getCategoryId()));

        imgBoxes.add(imageBox);
    };

    private Image getImage(byte... imageBytes) {
        imageBytes = Base64.getDecoder().decode(imageBytes);
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(imageBytes);

        return new Image(arrayInputStream);
    }

    private void showPlaces(int categoryPressed) {
        clearImgs();
        addImgViewersPlaces(categoryPressed);

        imagesFlowPane.prefWidthProperty().bind(Bindings.add(-5, rootScroll.widthProperty()));
        imagesFlowPane.prefHeightProperty().bind(Bindings.add(-5, rootScroll.heightProperty()));

        imagesFlowPane.setHgap(20);
        imagesFlowPane.setVgap(20);
        imagesFlowPane.getChildren().addAll(imgBoxes);

        isOnPlaces = true;
    }

    private void clearImgs() {
        imgBoxes.clear();
        imagesFlowPane.getChildren().clear();
    }

    private void addImgViewersPlaces(int categoryPressed) {
        placesToShow = new RandomImgForPlaceByCategory(categoryPressed)
                .selectAll();
        placesToShow.forEach(placeConsumer);
    }

    private EventHandler<MouseEvent> placeEventHandler = (event) -> {
        VBox box = (VBox) event.getSource();
        backButton.setVisible(true);
        PlaceDB place = new PlaceDB();
        placesToShow.stream()
                .filter(pl -> pl.getPlaceId() == Integer.parseInt(box.getId()))
                .forEach(item -> {
                    place.setId(item.getPlaceId());
                    place.setInfo(item.getPlaceInfo());
                    place.setName(item.getPlaceName());
                });

        DescriptionsController placeDescriptions = (DescriptionsController) DESCRIPTIONS.createWindow();
        placeDescriptions.init(place);

        if (currentUser != null) {
            insertVisited(place.getId());
        }

        if (DescriptionsController.imagesButtonIsPressed) {
            showImages(place.getId());
        }
    };

    private void insertVisited(int placeId) {
        User_PlaceDB visitedPlace = new User_PlaceDB();

        visitedPlace.setUserId(currentUser.getId());
        visitedPlace.setPlaceId(placeId);

        visitedPlace.insert();
    }

    private void showImages(int placePressed) {
        clearImgs();
        addImgViewersImages(placePressed);

        imagesFlowPane.prefWidthProperty().bind(Bindings.add(-5, rootScroll.widthProperty()));
        imagesFlowPane.prefHeightProperty().bind(Bindings.add(-5, rootScroll.heightProperty()));

        imagesFlowPane.setHgap(20);
        imagesFlowPane.setVgap(20);
        imagesFlowPane.getChildren().addAll(imgBoxes);

        isOnPlaces = false;
    }

    private void addImgViewersImages(int placePressed) {
        imgsToShow = new AllImagesByPlace(placePressed)
                .selectAll();
        imgsToShow.forEach(imgConsumer);
    }

    private EventHandler<MouseEvent> imgEventHandler = (event) -> {
        VBox box = (VBox) event.getSource();
        backButton.setVisible(true);
        ImageDB imgs = new ImageDB();
        imgsToShow.stream()
                .filter(img -> img.getImageId() == Integer.parseInt(box.getId()))
                .forEach(item -> {
                    imgs.setId(item.getImageId());
                    imgs.setImage(item.getImage());
                    imgs.setDescription(item.getImage_description());
                    imgs.setAuthor(item.getImage_author());
                });

        DescriptionsController imgDescriptions = (DescriptionsController) DESCRIPTIONS.createWindow();
        imgDescriptions.init(imgs);
    };

    private Consumer<AllImagesByPlace> imgConsumer = (record) -> {
        VBox imageBox = new VBox();
        ImageView imgView = new ImageView(getImage(record.getImage()));
        imgView.setFitWidth(200); //record.getWidth();
        imgView.setFitHeight(200);
        imgView.setSmooth(true);
        imgView.setCache(true);

        imageBox.getChildren().add(imgView);
        imageBox.setOnMouseClicked(imgEventHandler);
        imageBox.setId(String.valueOf(record.getImageId()));

        imgBoxes.add(imageBox);
    };

    private Consumer<RandomImgForPlaceByCategory> placeConsumer = (record) -> {
        VBox imageBox = new VBox();
        ImageView imgView = new ImageView(getImage(record.getImage()));
        imgView.setFitWidth(300); //record.getWidth();
        imgView.setFitHeight(300);
        imgView.setSmooth(true);
        imgView.setCache(true);

        imageBox.getChildren().add(imgView);
        imageBox.getChildren().add(new Label(record.getPlaceName()));
        imageBox.setOnMouseClicked(placeEventHandler);
        imageBox.setId(String.valueOf(record.getPlaceId()));

        imgBoxes.add(imageBox);
    };

    @FXML
    private void onExitAction(ActionEvent event) {
        boolean isOk = showAlert(Alert.AlertType.CONFIRMATION, null, "¿Estas seguro que desea salir?");

        if (isOk) {
            System.exit(0);
        }
    }

    @FXML
    private void onTriviaAction(ActionEvent event) {
        TriviaController trivia = (TriviaController) TRIVIA.createWindow();
        trivia.init(currentUser);
    }

    @FXML
    private void onAccountAction(ActionEvent event) {
        AccountController account = (AccountController) ACCOUNT.createWindow();
        account.init(currentUser);
    }

    @FXML
    private void onLoginAction(ActionEvent event) {
        LOGIN.createWindow().init();
    }

    @FXML
    private void onSignupAction(ActionEvent event) {
        SIGNUP.createWindow().init();
    }

    private boolean showAlert(Alert.AlertType alertType, String header, String message) {
        Alert alert = new Alert(alertType);

        alert.setHeaderText(header);
        alert.setTitle(null);
        alert.setContentText(message);

        return alert.showAndWait().get() == ButtonType.OK;
    }

    @FXML
    private void onBackAction(ActionEvent event) {
        if (isOnPlaces) {
            showCategories();
        } else {
            showPlaces(selectedCategoryId);
        }
    }

    @FXML
    private void onHamburgerMenu(MouseEvent event) {
        hamburgerTransition.setRate(hamburgerTransition.getRate() * -1);
        hamburgerTransition.play();

        if (sideDrawer.isOpened()) {
            sideDrawer.close();
            sideDrawer.setVisible(false); //delay missing
        } else {
            sideDrawer.setVisible(true);
            sideDrawer.open();
        }
    }

    @FXML
    private void onAboutAction(ActionEvent event) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Acerca de");
	alert.setHeaderText("GACU");
	alert.setContentText("El objetivo de esta aplicacion es informar o dar a conocer"
		+ " la vasta diversidad cultural arquitect√≥nica de la ciudad de Cuenca(Ecuador),"
		+ " compartir en ella informaci√≥n general, datos hist√≥ricos y curiosos," 
		+ " ademas cuenta con una trivia donde se generan preguntas aletorias de"
		+ " los lugares que has visitado en la aplicacion."
	);

	alert.showAndWait();
    }

}
