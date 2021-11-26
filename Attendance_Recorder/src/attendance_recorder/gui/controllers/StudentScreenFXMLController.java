/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.gui.controllers;

import attendance_recorder.be.Date;
import attendance_recorder.be.Student;
import attendance_recorder.gui.model.AppModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author math2
 */
public class StudentScreenFXMLController implements Initializable {

    private Student currentUser;
    private Date selectedDate;
    private AppModel am;
    private final ToggleGroup group = new ToggleGroup();
    private boolean isConnected;

    @FXML
    private Label lblWelcome;
    @FXML
    private AnchorPane studentPane;
    @FXML
    private BorderPane diagramPane;
    @FXML
    private ImageView imgLogo;
    @FXML
    private MenuBar menubarStudent;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblAbsence;
    @FXML
    private MenuItem menuItemDiagram;
    @FXML
    private MenuItem menuItemProfile;
    
    @FXML
    private JFXRadioButton radioAbsent;
    @FXML
    private JFXRadioButton radioPresent;
    @FXML
    private JFXButton btnSubmit;
    @FXML
    private Label lblConnection;
    @FXML
    private JFXButton btnAbsenceNote;
    @FXML
    private JFXDatePicker JFXcalender;
    @FXML
    private TextArea txtAbsenceNote;
    @FXML
    private Label lblDatePresence;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        am = AppModel.getAppModel();
        currentUser = am.getCurrentStudent();
        List<Date> dates = am.getStudentDates(currentUser);
        colorCalendarDaysByPresenceStatus(dates);

        imgLogo.setImage(getImage());
        menuItemDiagram.setDisable(false);
        menuItemProfile.setDisable(true);

        radioAbsent.setToggleGroup(group);
        radioPresent.setToggleGroup(group);

        checkIfConnected();

        setCurrentUser(currentUser);
    }

    private void colorCalendarDaysByPresenceStatus(List<Date> dates) {

        Background absent = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
        Background present = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);

        JFXcalender.setDayCellFactory(dp -> new DateCell() {

            {
                addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    for (Date date : dates) {
                        if (date.getDate().equals(getItem().toString())) {
                            if (date.isIsPresent()) {
                                Platform.runLater(() -> {
                                    setBackground(present);

                                });

                            } else if (!date.isIsPresent()) {
                                Platform.runLater(() -> {
                                    setBackground(absent);
                                    if (date.getAbsenceNote() != null) {
                                        if (!date.getAbsenceNote().trim().isEmpty()) {
                                            tooltip.setText(date.getAbsenceNote());
                                            setTooltip(tooltip);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

            }

            {
                addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                    for (Date date : dates) {
                        if (date.getDate().equals(getItem().toString())) {
                            if (date.isIsPresent()) {
                                Platform.runLater(() -> {
                                    setBackground(present);
                                });

                            } else if (!date.isIsPresent()) {
                                Platform.runLater(() -> {
                                    setBackground(absent);
                                    if (date.getAbsenceNote() != null) {
                                        if (!date.getAbsenceNote().trim().isEmpty()) {
                                            tooltip.setText(date.getAbsenceNote());
                                            setTooltip(tooltip);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

            }

            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                for (Date date : dates) {
                    if (date.getDate().equals(item.toString())) {
                        if (date.isIsPresent()) {
                            setBackground(present);
                        } else if (!date.isIsPresent()) {
                            setBackground(absent);
                            if (date.getAbsenceNote() != null) {
                                if (!date.getAbsenceNote().trim().isEmpty()) {
                                    tooltip.setText(date.getAbsenceNote());
                                    setTooltip(tooltip);
                                }
                            }
                        }

                    }
                }
            }

        });
    }

    @FXML
    private void handleChart(ActionEvent event) {

        diagramPane.setCenter(am.buildChart(currentUser));
        menuItemProfile.setDisable(false);
        menuItemDiagram.setDisable(true);
    }

    @FXML
    private void handleMainView(ActionEvent event) {

        studentPane.setVisible(true);
        diagramPane.setCenter(studentPane);
        menuItemProfile.setDisable(true);
        menuItemDiagram.setDisable(false);
    }

    @FXML
    private void handleClose(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/attendance_recorder/gui/views/LoginScreenFXML.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage primStage = (Stage) menubarStudent.getScene().getWindow();
            Stage stage = new Stage();

            stage.setTitle("Attendance Login");
            stage.setScene(scene);
            stage.show();
            primStage.close();

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(StudentScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Image getImage() {
        Image logo = new Image("/attendance_recorder/images/EASVLogo.png");
        return logo;
    }

    @FXML
    private void handleAbsence(ActionEvent event) {

        if (selectedDate == null) {
            showErrorAlert("Select a date.");
            return;
        }
        if (radioPresent.isSelected()) {
            selectedDate.setIsPresent(true);
            txtAbsenceNote.clear(); //remember to also clear message in database
            selectedDate.setAbsenceNote(null);
        } else if (radioAbsent.isSelected()) {
            selectedDate.setIsPresent(false);
        } else {
            showErrorAlert("Select Present or Absent");
            return;
        }

        updateAllData();
    }

    @FXML
    private void addEditAbsenceNote(ActionEvent event) {

        Date date = selectedDate;
        if (date == null) {
            showErrorAlert("Select a date.");
            return;
        }
        if (date.isIsPresent() == true) {
            showErrorAlert("You are/were present this day.");
            return;
        }
        date.setAbsenceNote(txtAbsenceNote.getText());
        am.updateAbsenceNote(date);
        am.getStudentDates(currentUser);
    }

    @FXML
    private void selectDate(ActionEvent event) {

        txtAbsenceNote.clear();
        lblDatePresence.setText("");
        selectedDate = null;

        List<Date> dates = am.getStudentDates(currentUser);
        for (Date date : dates) {
            if (JFXcalender.getValue().toString().equals(date.getDate())) {
                selectedDate = date;
                txtAbsenceNote.setText(selectedDate.getAbsenceNote());
                setAbsenceLabelText();
            }
        }

    }

    private void setAbsenceLabelText() {
        if (selectedDate.isIsPresent() == true) {
            lblDatePresence.setText("PRESENT");
            lblDatePresence.setTextFill(Color.GREEN);
        } else if (selectedDate.isIsPresent() == false) {
            lblDatePresence.setText("ABSENT");
            lblDatePresence.setTextFill(Color.RED);
        }
    }

    private void checkIfConnected() {
        try {
            Process process = java.lang.Runtime.getRuntime().exec("ping moodle.easv.dk");
            int x = process.waitFor();
            if (x == 0) {
                isConnected = true;
            } else {
                isConnected = false;
                radioAbsent.setDisable(true);
                radioPresent.setDisable(true);
                btnSubmit.setDisable(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(StudentScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateAllData() {

        am.updatePresence(selectedDate);
        am.updateAbsenceNote(selectedDate);
        am.getStudentDates(currentUser);
        setAbsenceLabelText();
        lblAbsence.setText((am.getAbsencePercentage() + "%"));
    }

    public void setCurrentUser(Student student) {
        lblWelcome.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy");
        lblDate.setText(localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " + localDate.format(dateFormatter));
        lblAbsence.setText("Your total absence is " + am.getAbsencePercentage() + "%");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("ERROR");
        alert.setContentText(String.format(message));
        alert.showAndWait();
    }

}
