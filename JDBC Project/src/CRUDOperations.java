import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.plaf.nimbus.State;

public class CRUDOperations {

    private static final String url = "jdbc:mysql://localhost:3306/krina";
    private static final String userName = "root";
    private static final String password = "Krina@1211";
    
    public static void createTable(){
        try (Connection conn = DriverManager.getConnection(url,userName, password)) {
            String query = "CREATE TABLE students(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, age INT NOT NULL, marks DOUBLE NOT NULL);" ;
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            statement.close();
            JOptionPane.showMessageDialog(null, "Student" + " Table has been created successfully", "System Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void AddRecord(){
        try (Connection conn = DriverManager.getConnection(url,userName, password)) {
            String query = String.format("insert into students(name, age, marks) values('%s', %o, %f)", "Neel", 26, 75.4);

            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected>0){
                System.out.println("Data inserted Successfully");
            }else{
                System.out.println("Data not inserted!");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdateRecord(){
        try (Connection conn = DriverManager.getConnection(url,userName, password)) {
            String query = String.format("update students set marks=%f where id=3", 80.6);
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected>0){
                System.out.println("Data Updated Successfully");
            }else{
                System.out.println("Data not updated!");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void InsertMultiple(){
        try (Connection conn = DriverManager.getConnection(url,userName, password)) {
            String query = "insert into students(name, age, marks) values(?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Ankita");
            preparedStatement.setInt(2, 46);
            preparedStatement.setDouble(3, 78.89);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected>0){
                System.out.println("Data Inserted Successfully");
            }else{
                System.out.println("Data not inserted!");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void BatchProcessingStatement(){
        try (Connection conn = DriverManager.getConnection(url,userName, password)) {
            Scanner sc = new Scanner(System.in);
            Statement statement = conn.createStatement();
            while(true){
                System.out.println("Enter name: ");
                String name = sc.next();
                System.out.println("Enter age: ");
                int age = sc.nextInt();
                System.out.println("Enter marks: ");
                double marks = sc.nextDouble();
                System.out.println("Insert more data? Y/N : ");
                String query = String.format("insert into students(name, age, marks) values('%s', %o, %f)", name, age, marks);
                statement.addBatch(query);

                String choice = sc.next();
                if (choice.toUpperCase().equals("N")){
                    break;
                }
            }

            int[] rowsAffected = statement.executeBatch();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void BatchProcessingPreparedStatement(){
        try (Connection conn = DriverManager.getConnection(url,userName, password)) {
            Scanner sc = new Scanner(System.in);
            String query = "insert into students(name, age, marks) values(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            while(true){
                System.out.println("Enter name: ");
                String name = sc.next();
                System.out.println("Enter age: ");
                int age = sc.nextInt();
                System.out.println("Enter marks: ");
                double marks = sc.nextDouble();
                System.out.println("Insert more data? Y/N : ");
                String choice = sc.next();
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setDouble(3, marks);

                if (choice.toUpperCase().equals("N")){
                    break;
                }
            }

            int[] rowsAffected = preparedStatement.executeBatch();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SelectTable(){
        try (Connection conn = DriverManager.getConnection(url,userName, password)) {
            String query = "select * from students;"; ;
            Statement statement = conn.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            while(resultset.next()){
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                int age = resultset.getInt("age");
                double marks = resultset.getDouble("marks");
                System.out.printf("%d | '%s' | %d | %f", id, name, age, marks);
                System.out.println();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   


    public static void main(String[] args) {
        System.out.println("Welcome to the application for student Database: ");
        System.out.println("Select from the following operations: ");
        Scanner sc = new Scanner(System.in);
        int Operation;
        do{
            System.out.println("1. Create table\n2.Add a record\n3.Update the record:\n4.Delete the record\n5.exit()");
            Operation = sc.nextInt();
            
            switch (Operation) {
                case 1:
                    createTable();
                    break;
                case 2:
                    AddRecord();
                    break;
                case 3:
                    UpdateRecord();
                    break;
                case 4:
                    InsertMultiple();
                    break;  
                case 5:
                    SelectTable();
                    break;
                case 6:
                    BatchProcessingStatement();
                    break;
                case 8:
                    BatchProcessingPreparedStatement();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid Input, please Retry!");
                    break;
            }

        }
        while(Operation!=7);
        sc.close();
    }
}
