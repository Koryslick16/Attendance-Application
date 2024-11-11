package org.example;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class Student {
    private int id;
    private SimpleStringProperty name;
    private SimpleStringProperty lastName;
    private SimpleStringProperty studentId;
    private SimpleStringProperty course;
    private SimpleStringProperty lecturer;
    private SimpleStringProperty courseModule;
    private SimpleStringProperty laptopBrand;
    private SimpleStringProperty gender;
    private LocalDate dateMarked;
    private SimpleObjectProperty<LocalTime> arrivalTime;
    private SimpleObjectProperty<LocalTime> departureTime;
    private boolean isPresent;

    public Student(String name, String lastName, String studentId, String course, String gender, String lecturer, String module, String brand, Date date, LocalTime arrivalTime, LocalTime departureTime) {
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.studentId = new SimpleStringProperty(studentId);
        this.course = new SimpleStringProperty(course);
        this.gender = new SimpleStringProperty(gender);
        this.lecturer = new SimpleStringProperty(lecturer);
        this.courseModule = new SimpleStringProperty(module);
        this.laptopBrand = new SimpleStringProperty(brand);
        this.dateMarked = date.toLocalDate();
        this.arrivalTime = new SimpleObjectProperty<>(arrivalTime);
        this.departureTime = new SimpleObjectProperty<>(departureTime);
        this.isPresent = false;
    }

    // Alternative constructors for different use cases
    public Student(String name, String lastName, String studentId, String gender, String course, String lecturer, String courseModule, String laptopBrand, LocalTime arrivalTime) {
        this(name, lastName, studentId, course, gender, lecturer, courseModule, laptopBrand, Date.valueOf(LocalDate.now()), arrivalTime, null);
    }

    public Student(String studentId, LocalTime departureTime) {
        this.studentId = new SimpleStringProperty(studentId);
        this.departureTime = new SimpleObjectProperty<>(departureTime);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getStudentId() {
        return studentId.get();
    }

    public void setStudentId(String studentId) {
        this.studentId = new SimpleStringProperty(studentId);
    }

    public String getCourse() {
        return course.get();
    }

    public void setCourse(String course) {
        this.course = new SimpleStringProperty(course);
    }

    public String getLecturer() {
        return lecturer.get();
    }

    public void setLecturer(String lecturer) {
        this.lecturer = new SimpleStringProperty(lecturer);
    }

    public String getCourseModule() {
        return courseModule.get();
    }

    public void setCourseModule(String courseModule) {
        this.courseModule = new SimpleStringProperty(courseModule);
    }

    public String getLaptopBrand() {
        return laptopBrand.get();
    }

    public void setLaptopBrand(String laptopBrand) {
        this.laptopBrand = new SimpleStringProperty(laptopBrand);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender = new SimpleStringProperty(gender);
    }

    public Date getDateMarked() {
        return Date.valueOf(dateMarked);
    }

    public void setDateMarked(LocalDate dateMarked) {
        this.dateMarked = dateMarked;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty studentIdProperty() {
        return studentId;
    }

    public SimpleStringProperty courseProperty() {
        return course;
    }

    public SimpleStringProperty lecturerProperty() {
        return lecturer;
    }

    public SimpleStringProperty courseModuleProperty() {
        return courseModule;
    }

    public SimpleStringProperty laptopBrandProperty() {
        return laptopBrand;
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime.get();
    }

    public SimpleObjectProperty<LocalTime> arrivalTimeProperty() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public LocalTime getDepartureTime() {
        return departureTime.get();
    }

    public SimpleObjectProperty<LocalTime> departureTimeProperty() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime.set(departureTime);
    }

    public void setPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

}
