import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class createDB {
    public static void main(String[] args) throws Exception {
       
        try {
            String url = "jdbc:mysql://localhost:3306/";

            String databaseName = "Krina";
            String userName = "root";
            String password = "Krina@1211";
    
            Connection connection = DriverManager.getConnection(url,userName, password);
    
            String sql = "CREATE DATABASE " + databaseName;
    
            Statement statement = connection.createStatement();
        
            statement.close();
            JOptionPane.showMessageDialog(null, databaseName + " Database has been created successfully", "System Message", JOptionPane.INFORMATION_MESSAGE);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
