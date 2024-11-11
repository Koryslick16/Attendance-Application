package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AttendanceManager implements IStudent{

    @Override
    public void addNewStudent(Student record) {
        String INSERT_QUERY = "INSERT INTO records (First_Name, Last_Name, StudentID, Course, Gender, Lecturer, Course_Module, LapTop_Brand, Date, Arrival_Time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().connect();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {

            pstmt.setString(1, record.getName());
            pstmt.setString(2, record.getLastName());
            pstmt.setString(3, record.getStudentId());
            pstmt.setString(4, record.getCourse());
            pstmt.setString(5, record.getGender());
            pstmt.setString(6, record.getLecturer());
            pstmt.setString(7, record.getCourseModule());
            pstmt.setString(8, record.getLaptopBrand());
            pstmt.setDate(9, record.getDateMarked());
            pstmt.setTime(10, Time.valueOf(record.getArrivalTime()));

            int work = pstmt.executeUpdate();
            if(work==1) {
                System.out.println("Student has successfully signed in.");
            }else{
                System.out.println("Sign in failed, try again later");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void updateStudent(Student record){
        String UPDATE_QUERY = "UPDATE records SET Departure_Time = ? WHERE StudentID = ? AND Date = ?";
        try(Connection connect = DatabaseConnection.getInstance().connect();
            PreparedStatement pstmt = connect.prepareStatement(UPDATE_QUERY)){

            pstmt.setTime(1, Time.valueOf(record.getDepartureTime()));
            pstmt.setString(2, record.getStudentId());
            pstmt.setDate(3, record.getDateMarked());

            int work = pstmt.executeUpdate();
            if (work == 1){
                System.out.println("Student has successfully signed out.");
            }else {
                System.out.println("Sign out failed, try again later.");
            }
        }catch (SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
        }
    }


    @Override
    public void ManageRecord() {
        try {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Automated reset at: " + LocalDate.now());
//            ManageAttendance.markAllAbsent();
//            manager.printAttendance();
            }, 0, 24, TimeUnit.HOURS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
