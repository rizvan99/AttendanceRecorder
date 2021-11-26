/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.gui.controllers;

import attendance_recorder.be.Date;
import attendance_recorder.be.Student;
import attendance_recorder.be.Teacher;
import attendance_recorder.bll.DateBllManager;
import attendance_recorder.bll.utility.languages.Localizer;
import attendance_recorder.gui.model.AppModel;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author math2
 */
public class LoginScreenController implements Initializable {
    
    private AppModel model;
    private DateBllManager dbm;
    private Localizer localizer;

    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;    
    @FXML
    private ImageView imageView;
    @FXML
    private Label titleLbl;    
    @FXML
    private JFXButton transDanBtn;
    @FXML
    private Button transEngBtn;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label lblConnection;
    
    @FXML
    private Menu logoutMenu;
    @FXML
    private MenuItem logoutMenuItem;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        imageView.setImage(getImage());

        model = AppModel.getAppModel();
        dbm = new DateBllManager();
        localizer = new Localizer("src/attendance_recorder/resources/loginscreen_localization.txt");
        
        
        txtName.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                login();
            }
        });
        txtPassword.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                login();
            }
        });
        
        transDanBtn.setGraphic(new ImageView("/attendance_recorder/images/da.png"));
        transEngBtn.setGraphic(new ImageView("/attendance_recorder/images/en.png"));

        dbm.addCurrentDate(addCurrentDate());
  
        showConnection();
        
        translateText(model.getCurrentLanguage());

    }    
    
    @FXML
    private void handleLogin(ActionEvent event)
    {
        login();        
    }
    
    private void login() {
        String name = txtName.getText();
        String password = txtPassword.getText();
        
        if (name.isEmpty() || password.isEmpty()) {
            errorAlert("The input fields must be filled out");
            
        }
        else if (getVerifiedStudent(name, password)!=null) {
            model.setCurrentStudent(getVerifiedStudent(name, password));
            openStudentScreen(getVerifiedStudent(name, password));
            
        }
        else if (getVerifiedTeacher(name, password)!=null) {
            
            openTeacherScreen(getVerifiedTeacher(name, password));
        }
        else errorAlert("Name or password incorrect");
        
        
        txtPassword.clear();
    }
    
    private Student getVerifiedStudent(String name, String password)
    {
        List<Student> students = model.getAllStudents();

        for (Student student : students) {
            if (student.getProfileName().equals(name) && student.getPassword().equals(password)) {
                model.setCurrentStudent(student);
                return student;
            }
        }             
                             
        return null;      
    }
    
    private Teacher getVerifiedTeacher(String name, String password)
    {
        List<Teacher> teachers = model.getTeachers();

        for (Teacher teacher : teachers) {
            if (teacher.getProfileName().equals(name) && teacher.getPassword().equals(password)) {
                model.setCurrentTeacher(teacher);
                return teacher;
            }
        }        
        return null;
    }           
        
    private void errorAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);        
        alert.setTitle("Error Dialog");
        alert.setHeaderText("ERROR");
        alert.setContentText(String.format(message));
        alert.showAndWait();
    }
    
    private void openStudentScreen(Student student)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/attendance_recorder/gui/views/StudentScreenFXML.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage primStage = (Stage) btnLogin.getScene().getWindow();
            Stage stage = new Stage(); 
            stage.setTitle("Student Overview");
            stage.setScene(scene);
            StudentScreenFXMLController controller = fxmlLoader.getController();
            controller.setCurrentUser(student);
            stage.show();
            primStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void openTeacherScreen(Teacher teacher)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/attendance_recorder/gui/views/TeacherScreenFXML.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage primStage = (Stage) btnLogin.getScene().getWindow();
            Stage stage = new Stage();                       
            stage.setMaxHeight(500);
            stage.setMinHeight(500);
            stage.setMaxWidth(800);
            stage.setMinWidth(800);
            stage.setTitle("Teacher Overview");
            stage.setScene(scene);
            TeacherScreenFXMLController controller = fxmlLoader.getController();
            controller.setCurrentUser(teacher);
            stage.show();
            primStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
    
    
    private Image getImage(){
        Image logo = new Image("/attendance_recorder/images/EASVLogo.png");
        return logo;
    }

    @FXML
    private void handleClose(ActionEvent event) {
        System.exit(0);
    }
    
    private List<Date> addCurrentDate(){
        List<Date> dates = new ArrayList<>();
        List<Student> students = model.getAllStudents();
  
        for (Student student : students) {
            Date d = new Date(LocalDate.now().toString(), student.getId(), false, null);
            dates.add(d);
        }
        
        return dates;
    }
    
    @FXML
    private void handleDanTrans(ActionEvent event) {
        model.setCurrentLanguage(Localizer.Language.DANSK);        
        translateText(model.getCurrentLanguage());
    }

    @FXML
    private void handleEngTrans(ActionEvent event) {
        model.setCurrentLanguage(Localizer.Language.ENGLISH);
        translateText(model.getCurrentLanguage());
        
    }
    
    private void translateText(Localizer.Language language) {  
        
    txtName.setPromptText(localizer.translate("txtName", language));    
    txtPassword.setPromptText(localizer.translate("txtPassword", language));    
    btnLogin.setText(localizer.translate("btnLogin", language));      
    titleLbl.setText(localizer.translate("titleLbl", language));  
    logoutMenu.setText(localizer.translate("logoutMenu", language));
    logoutMenuItem.setText(localizer.translate("logoutMenuItem", language));
    lblConnection.setText(showConnection() == 0 ? localizer.translate("lblConnectionYes", language) : localizer.translate("lblConnectionNo", language));
    }
    

    public int showConnection()
    {
        int x = -1;
        try
        {
            Process process = java.lang.Runtime.getRuntime().exec("ping moodle.easv.dk");
            x = process.waitFor();
            if (x == 0)
            {
                lblConnection.setText("You are connected to EASV");
            } else
            {
                lblConnection.setText("You are not connected to EASV and cannot submit attendance");
            }
        } catch (IOException ex)
        {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }


    
    
}
