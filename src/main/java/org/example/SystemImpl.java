package org.example;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SystemImpl implements Initializable {

    private Stage primaryStage;
    @FXML
    private StackPane mainSwitch;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMainSwitch(Parent scene){
        if (scene instanceof StackPane){
            this.mainSwitch = (StackPane) scene;
        }
    }
//        hypersonic database

    @FXML
    private AnchorPane recordPane;
    @FXML
    private AnchorPane coursePane;
    @FXML
    private AnchorPane gradePane;
    @FXML
    private AnchorPane formPane;
    @FXML
    private AnchorPane analyticsPane;
    @FXML
    private AnchorPane formPane2;
    @FXML
    private Button clockOut;
    @FXML
    private Button viewTable;
    @FXML
    private Button recordButton;
    @FXML
    private Button courseButton;
    @FXML
    private Button gradeButton;
    @FXML
    private Button button2;
    @FXML
    private Button analyticsButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField lastName;
    @FXML
    private TextField studentId;
    @FXML
    private TextField studentIdOut;
    @FXML
    private ComboBox<String> courseField;
    @FXML
    private ComboBox<String> genderField;
    @FXML
    private TextField lecturerField;
    @FXML
    private ComboBox<String> courseModule;
    @FXML
    private ComboBox<String> laptopBrand;
    @FXML
    private TextField arrivalField;
    @FXML
    private TextField departureField;
    @FXML
    private DatePicker dateField;
    @FXML
    private DatePicker dateOut;
    
    @FXML
    private TableView<Student> RecordTable;
    @FXML
    private TableColumn<Student, String> serialColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> stuIdColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, String> courseColumn;
    @FXML
    private TableColumn<Student, String> lecturerColumn;
    @FXML
    private TableColumn<Student, String> moduleColumn;
    @FXML
    private TableColumn<Student, String> brandColumn;
    @FXML
    private TableColumn<Student, String> arrivalColumn;
    @FXML
    private TableColumn<Student, String> departureColumn;
    @FXML
    private TableColumn<Student, String> dateColumn;

    private AttendanceManager records = new AttendanceManager();
    private Map<String, Student> activeStudents = new HashMap<>();

    // Helper method for scene switching
    public void switchScene(ActionEvent event) {
            recordPane.setVisible(event.getSource() == recordButton);
            coursePane.setVisible(event.getSource() == courseButton);
            gradePane.setVisible(event.getSource() == gradeButton);
            formPane.setVisible(event.getSource() == button2);
            analyticsPane.setVisible(event.getSource() == analyticsButton);
            formPane2.setVisible(event.getSource() == clockOut);
            recordPane.setVisible(event.getSource() == viewTable);
    }

    public void initializeControls(){
        genderField.setItems(FXCollections.observableArrayList("Male", "Female"));
        laptopBrand.setItems(FXCollections.observableArrayList("HP", "Dell", "Apple", "Lenovo", "Asus", "Acer", "SamSung", "MicroSoft", "Toshiba"));
        courseField.setItems(FXCollections.observableArrayList("ADSE", "ACCP", "AI and Data Science", "AMSC", "ADM", "Cyber Security", "Short Term Course"));
        courseModule.setItems(FXCollections.observableArrayList("HTML", "CSS", "JavaScript", "Python", "TypeScript", "Java", "MongoDB", "" + "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                "" +
                ""));
    }
    @FXML
    public void genders(){
        genderField.show();
    }

    @FXML
    public void brands(){
        laptopBrand.show();
    }

    @FXML
    public void courses(){
        courseField.show();
    }

    @FXML
    private void handleSignIn(ActionEvent actionEvent){
        try{
            if (nameField.getText().isEmpty() || lastName.getText().isEmpty() || studentId.getText().isEmpty() || courseField.getValue().isEmpty() || genderField.getValue().isEmpty() || lecturerField.getText().isEmpty() || courseModule.getText().isEmpty() || laptopBrand.getValue().isEmpty() || arrivalField.getText().isEmpty() || dateField.getValue() == null){
                showAlert("Input Error", "Fields cannot be empty");
                return;
            }
            String stuId = studentId.getText();
            if (activeStudents.containsKey(stuId)){
                showAlert("Error", "Student is already signed in.");
                return;
            }
//            Parse arrival time
            LocalTime arrivalTime = parseTime(arrivalField.getText(), "Arrival Time");
//            Create new student object and add to the database
            Student student = new Student(nameField.getText(), lastName.getText(), stuId, genderField.getValue(), courseField.getValue(), lecturerField.getText(), courseModule.getText(), laptopBrand.getValue(), arrivalTime);
            records.addNewStudent(student);  // Insert new student record
            activeStudents.put(stuId, student); // Track the student as signed-in
            setupTableColumns();

            showAlert("Success", "Signed in successfully");
            clearFields();
            tableDatabase();
        }catch(Exception e){
            e.getMessage();
        }
    }

    @FXML
    private void handleSignOut(){
        try{
            // Validate input fields
            if (studentIdOut.getText().isEmpty() || departureField.getText().isEmpty() || dateOut.getValue() == null){
                showAlert("Input Error", "All Fields must be filled");
                return;
            }

            String stuNum = studentIdOut.getText();

            if(!activeStudents.containsKey(stuNum)){
                showAlert("Error", "This student is not signed in");
                return;
            }
            // Parse departure time
            LocalTime departureTime;
            try {
                departureTime = parseTime(departureField.getText(), "Departure Time");
            }catch (DateTimeParseException e){
                showAlert("Input Error", "Please enter the departure time in HH:MM format.");
                return;
            }


            // Update the student's departure time and save it to the database
            Student student = activeStudents.get(stuNum);
            student.setDepartureTime(departureTime);

            records.updateStudent(student); // Update the existing record
            activeStudents.remove(stuNum); // Remove the student from the active list
            setupTableColumns();

            showAlert("Success", "Successfully signed out");
            clearFields();
            tableDatabase();
        }catch (Exception e){
            e.getMessage();
        }
    }

    private LocalTime parseTime(String textTime, String fieldName){
        try{
            return LocalTime.parse(textTime); // expects time in HH:MM format
        } catch (DateTimeParseException e){
            showAlert("Input Error", fieldName + " must be in HH : MM format (09:13).");
            throw e;
        }
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields(){
        nameField.clear();
        lastName.clear();
        studentId.clear();
        studentIdOut.clear();
        courseField.getSelectionModel().clearSelection();
        genderField.getSelectionModel().clearSelection();
        lecturerField.clear();
        courseModule.clear();
        laptopBrand.getSelectionModel().clearSelection();
        arrivalField.clear();
        departureField.clear();
        dateField.setValue(null);
        dateOut.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initializeControls();
     setupTableColumns();
     tableDatabase();
    }

    private void setupTableColumns() {

        serialColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(RecordTable.getItems().indexOf(cellData.getValue()) + 1))
        );
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        stuIdColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("studentId"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("gender"));
        dateColumn.setCellValueFactory(cellData -> {
            LocalTime arrivalTime = cellData.getValue().getArrivalTime();
            return new SimpleStringProperty(arrivalTime != null ? arrivalTime.toString() : " ");
        });
        courseColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("course"));
        lecturerColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lecturer"));
        moduleColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("courseModule"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("laptopBrand"));
        arrivalColumn.setCellValueFactory(cellData -> {
            LocalTime arrivalTime = cellData.getValue().getArrivalTime();
            return new SimpleStringProperty(arrivalTime != null ? arrivalTime.toString() : " ");
        });
        departureColumn.setCellValueFactory(cellData -> {
            LocalTime departureTime = cellData.getValue().getDepartureTime();
            return new SimpleStringProperty(departureTime != null ? departureTime.toString() : " ");
        });

//        tableDatabase();
    }


    private ObservableList<Student> tableDatabase(){
        String TABLE_Query = "SELECT * FROM records";
        ObservableList<Student> records = FXCollections.observableArrayList();
        
        try(Connection connect = DatabaseConnection.getInstance().connect();
            Statement pstmt = connect.createStatement();
            ResultSet rs = pstmt.executeQuery(TABLE_Query)){

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String studentId = rs.getString("studentid");
                String course = rs.getString("course");
                String gender = rs.getString("gender");
                String lecturer = rs.getString("lecturer");
                String courseModule = rs.getString("course_module");
                String laptopBrand = rs.getString("laptop_brand");
                Date dateNow = Date.valueOf(rs.getString("date"));
                LocalTime arrivalTime = rs.getString("arrival_time") != null
                        ? LocalTime.parse(rs.getString("arrival_time"))
                        : null;
                LocalTime departureTime = rs.getString("departure_time") != null
                        ? LocalTime.parse(rs.getString("departure_time"))
                        : null;
                // Add the record to the list
                records.add(new Student(firstName, lastName, studentId, course, gender, lecturer,
                        courseModule, laptopBrand, dateNow, arrivalTime, departureTime));
            }
            RecordTable.setItems(records);

        }catch (SQLException e) {
            // Show specific SQL error message for debugging
            showAlert("Error", "Database error: " + e.getMessage());
        } catch (Exception ev) {
            // Catch any other exceptions and show the error
            showAlert("Error", "Unexpected error: " + ev.getMessage());
        }
        return records;
    }

    private void countGender(){
//        String COUNT_QUERY = SELECT COUNT(*) FROM records GROUP BY Gender
    }


}
