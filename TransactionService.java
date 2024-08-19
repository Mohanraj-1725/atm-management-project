package ATM_Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionService {
	public void deposit(int userId, double amount) {
        String updateBalanceQuery = "UPDATE users SET balance = balance + ? WHERE user_id = ?";
        String insertTransactionQuery = "INSERT INTO transactions (user_id, type, amount) VALUES (?, 'deposit', ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceQuery);
                 PreparedStatement insertTransactionStmt = conn.prepareStatement(insertTransactionQuery)) {
                updateBalanceStmt.setDouble(1, amount);
                updateBalanceStmt.setInt(2, userId);
                updateBalanceStmt.executeUpdate();

                insertTransactionStmt.setInt(1, userId);
                insertTransactionStmt.setDouble(2, amount);
                insertTransactionStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(int userId, double amount) {
        String updateBalanceQuery = "UPDATE users SET balance = balance - ? WHERE user_id = ?";
        String insertTransactionQuery = "INSERT INTO transactions (user_id, type, amount) VALUES (?, 'withdrawal', ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceQuery);
                 PreparedStatement insertTransactionStmt = conn.prepareStatement(insertTransactionQuery)) {
                // Check if the user has enough balance
                double currentBalance = getBalance(userId);
                if (currentBalance < amount) {
                    throw new SQLException("Insufficient balance");
                }

                updateBalanceStmt.setDouble(1, amount);
                updateBalanceStmt.setInt(2, userId);
                updateBalanceStmt.executeUpdate();

                insertTransactionStmt.setInt(1, userId);
                insertTransactionStmt.setDouble(2, amount);
                insertTransactionStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(int userId) {
        String query = "SELECT balance FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}


	


