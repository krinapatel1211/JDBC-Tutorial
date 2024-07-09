import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionHandling {

    public static boolean isSufficient(Connection connection, int account_number, double amount){
        String query = "select balance from accounts where account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, account_number);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()){
                double current_balance = resultset.getDouble("balance");
                if (amount>current_balance){
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/transactions";
            String userName = "root";
            String password = "Krina@1211";
            try {
                Connection connection = DriverManager.getConnection(url,userName, password);
                connection.setAutoCommit(false);

                String debit_query = "Update accounts set balance = balance - ? where account_number = ?";
                String credit_query = "Update accounts set balance = balance + ? where account_number = ?";
                
                PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);
                PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);
                
                System.out.println("Enter the amount to deduct: ");
                Scanner sc = new Scanner(System.in);
                double amount = sc.nextDouble();
                System.out.println("Enter account number: ");
                int account_number = sc.nextInt();

                debitPreparedStatement.setDouble(1, amount);
                debitPreparedStatement.setInt(2, account_number);
                creditPreparedStatement.setDouble(1, amount);
                creditPreparedStatement.setInt(2, 101);
                
                int rowsAffected1 = debitPreparedStatement.executeUpdate();
                int rowsAffected2 = creditPreparedStatement.executeUpdate();
                
                if (isSufficient(connection, account_number, amount)){
                    connection.commit();
                    System.out.println("Transaction Successfull!");
                }
                else{
                    connection.rollback();
                    System.out.println("Transaction Failed5");
                }
                
            } 
            catch (Exception e) {
                // TODO: handle exception
            }
            
           
    }
}
