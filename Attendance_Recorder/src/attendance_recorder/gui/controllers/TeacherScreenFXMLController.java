/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.gui.controllers;

import attendance_recorder.be.Student;
import attendance_recorder.be.Teacher;
import attendance_recorder.be.Course;
import attendance_recorder.be.Date;
import attendance_recorder.bll.utility.languages.LangDanish;
import attendance_recorder.gui.model.AppModel;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author math2
 */
public class TeacherScreenFXMLController implements Initializable {

    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Course> courses;    
    private AppModel am;
    private Teacher currentUser;
    private Date currentDate;
    private final ToggleGroup group = new ToggleGroup();

    
    @FXML
    private BorderPane diagramPane;
    @FXML
    private TableView<Student> tableStudents;
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblAbsenceProcentage;
    @FXML
    private Label lblPresentStatus;
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<Course> btnCourseSelect;
    @FXML
    private Label lblCurrentUser;
    @FXML
    private TableColumn<Student, String> nameColumn;
    private TableColumn<Student, Number> absenceColumn;
    @FXML
    private MenuBar menuBarTeacher;
    @FXML
    private Menu menubarTeacher;    
    @FXML
    private Label firstNameLbl;
    @FXML
    private Label LastNameLbl;
    @FXML
    private Label absenceLbl;
    @FXML
    private Label statusLbl;
    @FXML
    private Label statisticsLbl;
  
    

    @FXML
    private RadioButton presentRadiobtn;
    @FXML
    private RadioButton absentRadioBtn;
    @FXML
    private TableView<Date> AbsenceTabel;
    @FXML
    private TableColumn<Date, String> dateColumn;
    @FXML
    private TableColumn<Date, Boolean> presensColumn;    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initColumns();
        

                
        am = AppModel.getAppModel();
        
        setCurrentUser(am.getCurrentTeacher());
        
        courses.addAll(am.getTeachersCourse(currentUser));
        
        
        
        

        btnCourseSelect.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            tableStudents.getItems().clear();
            showStudentsInClass(newValue);            
            });
        
        tableStudents.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                Student s = newValue;
                //AbsenceTabel.getItems().clear();
                if(s!=null){
                AbsenceTabel.setItems(getStudentDates(s));
                showIndividualStudentInformation(s);            
                diagramPane.setCenter(am.buildChart(s));
                }
                
        });
        
        AbsenceTabel.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)-> currentDate = newValue);
        

        //Sets both radio buttons in the same group, making only 1 active at a time
        presentRadiobtn.setToggleGroup(group);
        absentRadioBtn.setToggleGroup(group);
        
        btnCourseSelect.setItems(courses);
        tableStudents.setItems(students);        
        
        
        
    }
    
    private void initColumns()
    {
        nameColumn.setCellValueFactory(data -> {
            String name = data.getValue().getLastName() + ", " + data.getValue().getFirstName();
            return new SimpleStringProperty(name);
        });        
        
        dateColumn.setCellValueFactory(data -> {
            String date = data.getValue().getDate();
            return new SimpleStringProperty(date);
        });
        
        dateColumn.setCellFactory(tc -> new TableCell<Date, String>() {
            @Override
            protected void updateItem(String string, boolean empty) {
                super.updateItem(string, empty);
                if (!empty) {
                    int row = getIndex();
                    Date date = getTableView().getItems().get(row);
                    Label lbl = new Label(date.getDate());
                    setGraphic(lbl);     
                        if (date.isIsPresent()) {
                            setStyle("-fx-background-color: green");
                            setTooltip(null);
                        }
                        else {
                            setStyle("-fx-background-color: red");
                            if (date.getAbsenceNote() != null) {
                                if (!date.getAbsenceNote().trim().isEmpty()) {
                                    Tooltip tooltip = new Tooltip(date.getAbsenceNote());
                                    tooltip.setShowDelay(Duration.ZERO);
                                    tooltip.setHideDelay(Duration.ZERO);
                                    setTooltip(tooltip);
                                }
                            }
                        }
                }
                else {
                    setGraphic(null);
                    setStyle(null);
                }
            }
        });        
        
        presensColumn.setCellValueFactory(data -> {            
            boolean absence = data.getValue().isIsPresent();
             return new SimpleBooleanProperty(absence);
        });
        
        presensColumn.setCellFactory(tc -> new TableCell<Date, Boolean>() {
            @Override
            protected void updateItem(Boolean presence, boolean empty) {
                super.updateItem(presence, empty);
                if (!empty) {
                    int row = getIndex();
                    Date date = getTableView().getItems().get(row);
                    Label lbl = new Label(date.isIsPresent()+"");
                    setGraphic(lbl);     
                        if (date.isIsPresent()) {
                            setStyle("-fx-background-color: green");
                            setTooltip(null);
                        }
                        else {
                            setStyle("-fx-background-color: red");
                            if (date.getAbsenceNote() != null) {
                                if (!date.getAbsenceNote().trim().isEmpty()) {
                                    Tooltip tooltip = new Tooltip(date.getAbsenceNote());
                                    tooltip.setShowDelay(Duration.ZERO);
                                    tooltip.setHideDelay(Duration.ZERO);
                                    setTooltip(tooltip);
                                }
                            }
                        }
                }
                else {
                    setGraphic(null);
                    setStyle(null);
                }
            }
        });
    }
    
    public void setCurrentUser(Teacher teacher)
    {
        currentUser = teacher;
        courses = FXCollections.observableArrayList(currentUser.getCourses());
        lblCurrentUser.setText("Logged in as: " + currentUser.getFirstName() + " " + currentUser.getLastName());
    }
    
    private void showStudentsInClass(Course course)
    {
        students = FXCollections.observableArrayList(am.getStudentsInCourse(course));
        tableStudents.getSelectionModel().clearSelection();
        tableStudents.setItems(students);  
        showIndividualStudentInformation(null);
    }
    
    private void showIndividualStudentInformation(Student student)
    {
        if (student != null) {
        lblFirstName.setText(student.getFirstName());
        lblLastName.setText(student.getLastName());
        setAbsenceInformation(student);
        imageView.setImage(new Image(student.getImageFilePath())); //perhaps student should just hold Image
    }
        else {
        lblFirstName.setText("");
        lblLastName.setText("");
        lblAbsenceProcentage.setText("");        
        lblPresentStatus.setText("");
        imageView.setImage(null);
        }
    }

    @FXML
    private void handleLogout(ActionEvent event)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/attendance_recorder/gui/views/LoginScreenFXML.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage primStage = (Stage) menuBarTeacher.getScene().getWindow();
            Stage stage = new Stage();

            stage.setTitle("Attendance Login");
            stage.setScene(scene);
            stage.show();
            primStage.close();
        } catch (IOException ex)
        {
            Logger.getLogger(TeacherScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleAbout(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information about license");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setResizable(true);        
        alert.setHeaderText(null);
        String content = String.format("%s%n%s%n%s", "Default user image is courtesy of Font Awesome by Dave Gandy.", "License: https://creativecommons.org/licenses/by-sa/3.0/deed.en", "No changes have been to the image.");
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void HandleChangePresence(ActionEvent event) {
        
        LocalDate currentDate = LocalDate.parse(AbsenceTabel.getSelectionModel().getSelectedItem().getDate());
        Student student = tableStudents.getSelectionModel().getSelectedItem();
        String absenceNote = AbsenceTabel.getSelectionModel().getSelectedItem().getAbsenceNote();
        boolean presence = true;
        
        if(presentRadiobtn.isSelected()){
            presence = true;
            absenceNote = null;
        }
        else if(absentRadioBtn.isSelected()){
            presence = false;
        }
        else{
            System.out.println("ikke valgt");
        }
        
        
        Date updatedDate = new Date(currentDate.toString(), student.getId(), presence);
        updatedDate.setAbsenceNote(absenceNote);
        
        am.updatePresence(updatedDate);
        am.updateAbsenceNote(updatedDate);
        AbsenceTabel.getItems().clear();
        am.getStudentDates(tableStudents.getSelectionModel().getSelectedItem());
        diagramPane.setCenter(am.buildChart(tableStudents.getSelectionModel().getSelectedItem()));
        setAbsenceInformation(student);
        
        
    }
    
    private ObservableList<Date> getStudentDates(Student s){
        
        return am.getStudentDates(s);        
        
    }
    
    private void setAbsenceInformation(Student student) {
        lblAbsenceProcentage.setText(am.getAbsencePercentage() + "%");        
        
        if (am.isStudentPresentToday(student)) {
            lblPresentStatus.setText("PRESENT");
            lblPresentStatus.setTextFill(Color.GREEN);            
        }
        else {
            lblPresentStatus.setText("ABSENT");
            lblPresentStatus.setTextFill(Color.RED);
        }        
    }

    
    
    
    
    
}
