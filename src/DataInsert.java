import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;

public class DataInsert {

    public static void main(String[] args) {
        // 接続文字列、ユーザID、パスワード
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "user";
        String password = "user";
  
        String City = "Tokyo";
        int TempLo = 6;
        int TempHi = 10;
        float Prcp = 0.6f;
        LocalDate ldate = LocalDate.of(2010, 12, 10);
        Date date = Date.valueOf(ldate);
  
        //INSERT文の作成
        String sql = "INSERT INTO weather (city, temp_lo, temp_hi, prcp, date) "
                    + "VALUES (?, ?, ?, ?, ?) ";
        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            try {
                stmt.setString(1, City);
                stmt.setInt(2, TempLo);
                stmt.setInt(3, TempHi);
                stmt.setFloat(4, Prcp);
                stmt.setDate(5, date);
                stmt.execute();
                conn.commit();
                System.out.println("commit!");
            } catch(Exception ex) {
                conn.rollback();
                System.out.println("rollback!");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
